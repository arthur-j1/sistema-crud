package sistemaCRUD;

//import java_BancoDeDados.src.sistemaCRUD.Usuario;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Conexao {

    private static Properties getProperties() {
        try{
            Properties prop = new Properties();
            FileInputStream file = new FileInputStream("C://Users/mayem/curso_java/java_BancoDeDados/" +
                    "src/sistemaCRUD/properties/conexaoCRUD.properties");
            prop.load(file);
            return prop;
        }catch (Exception e){
            System.out.println("erro ao gerar conexao. "+e.getMessage());
            return null;
        }
    }

    public static Connection getConexao() {

        try {
            Properties prop = new Properties();
            FileInputStream file = new FileInputStream("C://Users/mayem/curso_java/java_BancoDeDados/" +
                    "src/sistemaCRUD/properties/conexaoCRUD.properties");
            prop.load(file);
            final String url = prop.getProperty("banco.url");
            final String usuario = prop.getProperty("banco.usuario");
            final String senha = prop.getProperty("banco.senha");

            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException | IOException e) {
            System.out.println("Ocorreu um erro na conexão com o banco de dados. " + e.getMessage());
            return null;

        }
    }

    public static String consultaUsuario(Usuario usuario) {
        try{
            String sql = "Select * from usuarios where email = ?";
            Connection conexao = Conexao.getConexao();
            assert conexao != null;
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1,usuario.getEmail());
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()){
                return String.format("Email: %s \nNome: %s",
                        resultado.getString("email"),resultado.getString("nome"));
            }else {
                return "Usuario não encontrado no sistema";
        }}catch (Exception e){
            return "Erro ao consultar usuário. "+e.getMessage();
        }

    }

    public static boolean inserirUsuario(Usuario usuario) {
        try {
            String sql = "select email,senha from usuarios where email = ? ";
            Connection conexao = Conexao.getConexao();
            assert conexao != null;

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getEmail());

            ResultSet resultado = stmt.executeQuery();

            if(resultado.next()){
                System.out.printf("Usuario %s já está cadastrado no sistema\n",usuario.getEmail());
                return false;
            }else{
                sql = "insert into usuarios(nome,senha,email) values (?,?,?)";
                stmt = conexao.prepareStatement(sql);
                stmt.setString(1,usuario.getNome());
                stmt.setString(2,usuario.getSenha());
                stmt.setString(3,usuario.getEmail());
                stmt.execute();
                System.out.printf("Usuario %s adicionado com sucesso\n",usuario.getEmail());
                return true;
            }


        } catch (SQLException e) {
            System.out.println("Erro. " + e.getMessage());
            return false;
        }


    }

    public static boolean removerUsuario(Usuario usuario) {
        try {
            String sql = "delete from usuarios where email = ?";
            Connection conexao = Conexao.getConexao();
            assert conexao != null;
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getEmail());

            stmt.execute();
            System.out.printf("Usuario %s removido com sucesso",usuario.getNome());
            return true;

        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao remover usuario. " + e.getMessage());
            return false;
        }
    }


    public static void login(Usuario usuario){
        try {
            String sql = "select email,senha from usuarios where email = ?";
            Connection conexao = Conexao.getConexao();
            assert conexao != null;
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1,usuario.getEmail());
            ResultSet resultado =  stmt.executeQuery();
            if(resultado.next()){

                String emailUser = resultado.getString("email");
                String senhaUser = resultado.getString("senha");
                if (usuario.getEmail().equals(emailUser) && usuario.getSenha().equals(senhaUser)) {
                    System.out.println("Login autorizado");

                }else{
                    System.out.println("Login não autorizado");
                }
            }else{
                System.out.println("Usuário não encontrado no sistema");
            }




        }catch (Exception erro){
            System.out.println("Login não autorizado. "+erro.getMessage());
        }
    }


    public static void login(){
        try {
            Connection conexao = Conexao.getConexao();
            Scanner entrada = new Scanner(System.in);

            System.out.print("Digite seu email: ");
            String email = entrada.nextLine().strip().toLowerCase();
            System.out.print("Digite sua senha: ");
            String senha = entrada.nextLine().strip();

            String sql = "select nome,email,senha from usuarios where email = ?";
            assert conexao != null;
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1,email);
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                String emailUser = resultado.getString("email");
                String senhaUser = resultado.getString("senha");

                if(emailUser == email && senhaUser == senha){
                    System.out.println("Login autorizado");
                }else{
                    System.out.println("Senha incorreta, tente novamente.");
                }
            }else{
                System.out.println("Email não cadastrado no sistema");
            }

        }catch(Exception e){
            System.out.println("Houve um erro ao realizar o login. "+e.getMessage());
        }


    }
    public static void atualizarEmailUsuario(Usuario usuario,String novoEmail) {
        try{
            String sql = "update usuarios set email = ? where email = ?";
            Connection conexao = Conexao.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(2,usuario.getEmail());
            usuario.setEmail(novoEmail);
            novoEmail = usuario.getEmail();
            stmt.setString(1,usuario.getEmail());

            stmt.execute();

            String sql1 = "select email from usuarios where email = ?";
            PreparedStatement stmt1 = conexao.prepareStatement(sql1);
            stmt1.setString(1,novoEmail);
            ResultSet resultado = stmt1.executeQuery();
            if(resultado.next()){

                String email = resultado.getString(1);
                System.out.println("Email atualizado com sucesso para "+email);
            }else{
                System.out.println("Usuario informado não está cadastrado no sistema");
            }


        }catch (Exception e){
            System.out.println("Erro ao atualizar registro. "+e.getMessage());
        }



    }

    public static void atualizarSenhaUsuario(Usuario usuario,String novaSenha) {
        try {
            String sql = "update usuarios set senha = ? where email = ?";
            Connection conexao = Conexao.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(2, usuario.getSenha());
            usuario.setSenha(novaSenha);
            novaSenha = usuario.getEmail();
            stmt.setString(1, usuario.getSenha());

            stmt.execute();

            String sql1 = "select email from usuarios where email = ?";
            PreparedStatement stmt1 = conexao.prepareStatement(sql1);
            stmt1.setString(1, usuario.getEmail());
            ResultSet resultado = stmt1.executeQuery();
            if (resultado.next()) {

                System.out.println("Senha atualizada com sucesso.");

            } else {
                System.out.println("Usuario informado não está cadastrado no sistema");
            }


        } catch (Exception e) {
            System.out.println("Erro ao atualizar registro. " + e.getMessage());
        }

    }






}