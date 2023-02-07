package sistemaCRUD;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class TesteLogin {
    public static void main(String[] args) throws FileNotFoundException {
        Usuario usuario = new Usuario("arthur","arthur@email.com","arthur123");
        Conexao.inserirUsuario(usuario);



}}