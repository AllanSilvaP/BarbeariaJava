package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Servico; // Presumindo que você tenha uma classe Servico no seu pacote model

public class ServicoDAO {
    
    // Método para adicionar um novo serviço ao banco de dados
    public void adicionarServico(String nome, String descricao, String duracao, double preco) throws SQLException {
        String sql = "INSERT INTO Servicos (Nome_servico, Descricao, Duracao, preco) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConectaDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setString(3, duracao);
            stmt.setDouble(4, preco);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao adicionar serviço: " + e.getMessage());
        }
    }
    
    // Método para excluir um serviço do banco de dados
    public void excluirServico(int id) throws SQLException {
        String sql = "DELETE FROM Servicos WHERE Id_Servico = ?";
        
        try (Connection conn = ConectaDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao excluir serviço: " + e.getMessage());
        }
    }
    
    // Método para alterar um serviço existente no banco de dados
    public void alterarServico(int id, String nome, String descricao, String duracao, double preco) throws SQLException {
        String sql = "UPDATE Servicos SET Nome_servico = ?, Descricao = ?, Duracao = ?, Preco = ? WHERE Id_Servico = ?";
        
        try (Connection conn = ConectaDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setString(3, duracao);
            stmt.setDouble(4, preco);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar serviço: " + e.getMessage());
        }
    }
    
    // Método para listar todos os serviços cadastrados no banco de dados
    public List<Servico> listarServicos() throws SQLException {
        String sql = "SELECT * FROM Servicos";
        List<Servico> servicos = new ArrayList<>();
        
        try (Connection conn = ConectaDB.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Servico servico = new Servico(
                    rs.getInt("Id_Servico"),
                    rs.getString("Nome_servico"),
                    rs.getString("Descricao"),
                    rs.getString("Duracao"),
                    rs.getDouble("Preco")
                );
                servicos.add(servico);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar serviços: " + e.getMessage());
        }
        
        return servicos;
    }
    
    // Método para buscar um serviço por seu ID
    public Servico buscarServicoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Servicos WHERE Id_Servico = ?";
        Servico servico = null;
        
        try (Connection conn = ConectaDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    servico = new Servico(
                        rs.getInt("Id_Servico"),
                        rs.getString("Nome_servico"),
                        rs.getString("Descricao"),
                        rs.getString("Duracao"),
                        rs.getDouble("Preco")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar serviço: " + e.getMessage());
        }
        
        return servico;
    }
}
