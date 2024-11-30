package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

public class BarbeiroDAO {
    public void cadastrarBarbeiroEConta(String nomeBarbeiro, String especialidade, String telefone, String email,
            String senha) {
        String senhaCripto = BCrypt.hashpw(senha, BCrypt.gensalt());

        String comandoSqlConta = "INSERT INTO Conta (Email, Senha) VALUES (?, ?)";
        String comandoSqlBarbeiro = "INSERT INTO Barbeiros (Nome_Barbeiro, Especialidade, Telefone, Id_Conta) VALUES (?, ?, ?, ?)";

        try (Connection connect = ConectaDB.getConnection()) {
            // Inserindo a conta
            try (PreparedStatement comandaSqlConta = connect.prepareStatement(comandoSqlConta,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                comandaSqlConta.setString(1, email);
                comandaSqlConta.setString(2, senhaCripto);
                comandaSqlConta.executeUpdate();

                // Recupera o ID gerado para a conta
                try (ResultSet generatedKeys = comandaSqlConta.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idConta = generatedKeys.getInt(1);

                        // Inserindo barbeiro associado à conta
                        try (PreparedStatement comandaSqlBarbeiro = connect.prepareStatement(comandoSqlBarbeiro)) {
                            comandaSqlBarbeiro.setString(1, nomeBarbeiro);
                            comandaSqlBarbeiro.setString(2, especialidade);
                            comandaSqlBarbeiro.setString(3, telefone);
                            comandaSqlBarbeiro.setInt(4, idConta);
                            comandaSqlBarbeiro.executeUpdate();
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Barbeiro e conta cadastrados com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar barbeiro e conta: " + e.getMessage());
        }
    }

    public void excluirBarbeiro(int idBarbeiro) throws SQLException {
        String comandoSQL = "DELETE FROM Barbeiros WHERE Id_Profissional = ?";

        try (Connection connect = ConectaDB.getConnection();
                PreparedStatement comandaSQL = connect.prepareStatement(comandoSQL)) {
            comandaSQL.setInt(1, idBarbeiro);
            comandaSQL.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> obterBarbeiros() {
        List<String> barbeiros = new ArrayList<>();
        String sql = "SELECT Nome_barbeiro FROM Barbeiros";

        try (Connection conn = ConectaDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                barbeiros.add(rs.getString("Nome_barbeiro"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return barbeiros;
    }

    public int obterIdBarbeiro(String nomeBarbeiro) {
        // Defina a consulta SQL que irá buscar o ID pelo nome
        String sql = "SELECT Id_Profissional FROM Barbeiros WHERE Nome_Barbeiro = ?";
        
        try (Connection connect = ConectaDB.getConnection(); // Supondo que você tenha um método Conexao para obter a conexão
             PreparedStatement ps = connect.prepareStatement(sql)) {
            
            // Define o parâmetro da consulta, que é o nome do barbeiro
            ps.setString(1, nomeBarbeiro);
            
            // Executa a consulta
            ResultSet rs = ps.executeQuery();
            
            // Verifica se encontrou um barbeiro com o nome fornecido
            if (rs.next()) {
                // Retorna o ID do barbeiro encontrado
                return rs.getInt("Id_Profissional");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Caso não encontre o barbeiro, retorna -1 (valor inválido)
        return -1;
    }

}
