package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

public class ContaDAO {

    public void salvarConta(String Email, String Senha) {
        Senha = Senha.trim();
        String senhaCripto = BCrypt.hashpw(Senha, BCrypt.gensalt());

        String comandoSql = "INSERT INTO Conta (Email, Senha) VALUES (?,?)";

        try (Connection connect = ConectaDB.getConnection();
                PreparedStatement comandaSql = connect.prepareStatement(comandoSql)) {
            comandaSql.setString(1, Email);
            comandaSql.setString(2, senhaCripto);
            comandaSql.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public boolean autenticarConta(String Email, String Senha) {
        String comandoSql = "SELECT Senha FROM Conta WHERE Email = ?";
    
        try (Connection connect = ConectaDB.getConnection(); 
             PreparedStatement comandaSql = connect.prepareStatement(comandoSql)) {
            
            comandaSql.setString(1, Email);
    
            try (ResultSet resultadoPesquisa = comandaSql.executeQuery()) {
                if (resultadoPesquisa.next()) {
                    String senhaCripto = resultadoPesquisa.getString("Senha");
                    boolean resultado = BCrypt.checkpw(Senha, senhaCripto);
                    return resultado;
                } else {
                    JOptionPane.showMessageDialog(null, "Email não encontrado.");  // Debug
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}