package sistemaCRUD;

import java.util.Scanner;

public class Usuario {
    private String nome,senha,email;

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        Scanner entrada = new Scanner(System.in);
        if(!email.contains("@") || !email.contains(".com") || email.contains(" ")){
            while(!email.contains("@")||!email.contains(".com")||email.contains(" ")){

                System.out.print("Digite um email válido: ");
                email = entrada.nextLine().toLowerCase().strip();

            }
            this.email = email.toLowerCase().strip();
        }else {
            this.email = email.toLowerCase().strip();
        }
    }


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


    public void setSenha(String senha) {

        this.senha = senha.strip();
    }


    public String toString() {
        return String.format("Usuario{nome:%s email:s}",getNome(),getEmail());
    }

    public Usuario(String nome, String email, String senha) {
        Scanner entrada = new Scanner(System.in);
        if(!email.contains("@") || !email.contains(".com") || email.contains(" ")){
            while(!email.contains("@") || !email.contains(".com") || email.contains(" ")){

                System.out.print("Digite um email válido para "+nome+": ");
                email = entrada.nextLine().toLowerCase().strip();

            }
            this.email = email;
        }else {
            this.email = email.toLowerCase();
        }

        if(nome.contains(" ")){
            nome = nome.replace(" ","");
            this.nome = nome.strip();

        }else{
            this.nome = nome.strip();
        }
        if(senha.length()>=4&&!senha.contains(" ")){
            this.senha = senha.strip();

        }else{
            while(senha.length()<4||senha.contains(" ")){

            System.out.print("Senha com menos de 4 caracteres ou contem espaço, digite uma nova senha:");
            senha = entrada.nextLine().strip();
            }
            this.senha = senha;
        }
    }



}