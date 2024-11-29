package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

public class BarbeiroDAO {
    public void cadastrarBarbeiroEConta(String nomeBarbeiro, String especialidade, String telefone, String email, String senha) {
        String senhaCripto = BCrypt.hashpw(senha, BCrypt.gensalt());
    
        String comandoSqlBarbeiro = "INSERT INTO Barbeiros (Nome_Barbeiro, Especialidade, Telefone, Email) VALUES (?, ?, ?, ?)";
        String comandoSqlConta = "INSERT INTO Conta (Email, Senha) VALUES (?, ?)";
    
        try (Connection connect = ConectaDB.getConnection()) {
            // Inserindo o barbeiro na tabela Barbeiros
            try (PreparedStatement comandaSqlBarbeiro = connect.prepareStatement(comandoSqlBarbeiro)) {
                comandaSqlBarbeiro.setString(1, nomeBarbeiro);
                comandaSqlBarbeiro.setString(2, especialidade);
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
    
            System.out.println("Barbeiro e conta cadastrados com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao cadastrar barbeiro e conta: " + e.getMessage());
        }
    }

    public void excluirBarbeiro (int idBarbeiro) throws SQLException{
        String comandoSQL = "DELETE FROM Barbeiros WHERE Id_Profissional = ?";

        try (Connection connect = ConectaDB.getConnection(); PreparedStatement comandaSQL = connect.prepareStatement(comandoSQL)){
            comandaSQL.setInt(1, idBarbeiro);
            comandaSQL.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
