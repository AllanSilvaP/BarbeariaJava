package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//model
import model.Produto;

public class EstoqueDAO {
    public void addEstoque (String nome, int quantidade, double precoCompra, double precoVenda, String dataEntrada) throws SQLException {
        String comandoSQL = "INSERT INTO Estoque (Nome_Produto, Quantidade_estocado, Preco_Compra, Preco_Venda, Data_Entrada) VALUES (?,?,?,?,?);";

        try (Connection connect = ConectaDB.getConnection(); PreparedStatement comandaSQL = connect.prepareStatement(comandoSQL)){
            comandaSQL.setString(1, nome);
            comandaSQL.setInt(2, quantidade);
            comandaSQL.setDouble(3, precoCompra);
            comandaSQL.setDouble(4, precoVenda);
            comandaSQL.setString(5, dataEntrada);
            comandaSQL.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirEstoque (int idProduto) throws SQLException{
        String comandoSQL = "DELETE FROM Estoque WHERE Id_Produto = ?";

        try (Connection connect = ConectaDB.getConnection(); PreparedStatement comandaSQL = connect.prepareStatement(comandoSQL)){
            comandaSQL.setInt(1, idProduto);
            comandaSQL.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Produto> listarEstoque() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String comandoSQL = "SELECT * FROM Estoque"; 
        
        try (Connection connect = ConectaDB.getConnection(); PreparedStatement comandaSQL = connect.prepareStatement(comandoSQL); 
             ResultSet pegaProduto = comandaSQL.executeQuery()) { 
             
            // Iterar sobre os resultados
            while (pegaProduto.next()) {
                produtos.add(new Produto(
                    pegaProduto.getInt("Id_Produto"),
                    pegaProduto.getString("Nome_Produto"),
                    pegaProduto.getInt("Quantidade_estocado"),
                    pegaProduto.getDouble("Preco_Compra"),
                    pegaProduto.getDouble("Preco_Venda"),
                    pegaProduto.getString("Data_Entrada")
                ));
            }
        }
        return produtos;
    }
    
    
}