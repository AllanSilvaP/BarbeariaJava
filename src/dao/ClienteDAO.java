package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

public class ClienteDAO {

    public void cadastrarClienteEConta(String nomeCliente, String data, String telefone, String email, String senha) {
        String senhaCripto = BCrypt.hashpw(senha, BCrypt.gensalt());

        String comandoSqlConta = "INSERT INTO Conta (Email, Senha) VALUES (?, ?)";
        String comandoSQLCliente = "INSERT INTO Clientes (Nome, Data_Registro, Telefone, Id_Conta) VALUES (?, ?, ?, ?)";

        try (Connection connect = ConectaDB.getConnection()) {
            // Inserindo a conta
            try (PreparedStatement comandaSqlConta = connect.prepareStatement(comandoSqlConta, PreparedStatement.RETURN_GENERATED_KEYS)) {
                comandaSqlConta.setString(1, email);
                comandaSqlConta.setString(2, senhaCripto);
                comandaSqlConta.executeUpdate();

                // Recupera o ID gerado para a conta
                try (ResultSet generatedKeys = comandaSqlConta.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idConta = generatedKeys.getInt(1);

                        // Inserindo cliente associado à conta
                        try (PreparedStatement comandaSqlCliente = connect.prepareStatement(comandoSQLCliente)) {
                            comandaSqlCliente.setString(1, nomeCliente);
                            comandaSqlCliente.setString(2, data);
                            comandaSqlCliente.setString(3, telefone);
                            comandaSqlCliente.setInt(4, idConta);
                            comandaSqlCliente.executeUpdate();
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Cliente e conta cadastrados com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente e conta: " + e.getMessage());
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
                    JOptionPane.showMessageDialog(null,"Email não encontrado.");  // Debug
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}