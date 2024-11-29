package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaDB {
    private static final String url = "jdbc:mysql://db-barbearia-aguia-real.c7miwyc2szll.us-east-2.rds.amazonaws.com:3306/Barbearia";
    private static final String username = "Allan";
    private static final String password = "Banco.top";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new SQLException("Erro de conexao com o banco de dados");
        }
    }
}
