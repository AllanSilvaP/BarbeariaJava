package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

public class ClienteDAO {

    public void cadastrarClienteEConta(String nomeCliente, String data, String telefone, String email, String senha) {
        String senhaCripto = BCrypt.hashpw(senha, BCrypt.gensalt());
    
        String coamndoSQLCliente = "INSERT INTO Clientes (Nome, Data_Registro, Telefone, Email) VALUES (?, ?, ?, ?)";
        String comandoSqlConta = "INSERT INTO Conta (Email, Senha) VALUES (?, ?)";
    
        try (Connection connect = ConectaDB.getConnection()) {
            try (PreparedStatement comandaSqlBarbeiro = connect.prepareStatement(coamndoSQLCliente)) {
                comandaSqlBarbeiro.setString(1, nomeCliente);
                comandaSqlBarbeiro.setString(2, data);
                comandaSqlBarbeiro.setString(3, telefone);
                comandaSqlBarbeiro.setString(4, email);
                comandaSqlBarbeiro.executeUpdate();
            }
    
            // Inserindo a conta na tabela Conta
            try (PreparedStatement comandaSqlConta = connect.prepareStatement(comandoSqlConta)) {
                comandaSqlConta.setString(1, email);
                comandaSqlConta.setString(2, senhaCripto);
                comandaSqlConta.executeUpdate();
            }
    
            System.out.println("Cliente e conta cadastrados com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao cadastrar barbeiro e conta: " + e.getMessage());
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
                    System.out.println("Email não encontrado.");  // Debug
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}