package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseConnection {
    // URL de conexão com o banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/loja_db";
    // Nome de usuário para acessar o banco de dados
    private static final String USER = "root";
    // Senha para acessar o banco de dados
    private static final String PASSWORD = "password";

    // Método estático para obter uma conexão com o banco de dados
    public static Connection getConnection() {
        try {
            // Registra o driver JDBC para MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Cria e retorna uma conexão com o banco de dados
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Captura exceção se o driver JDBC não for encontrado
            e.printStackTrace();
            throw new RuntimeException("Driver de banco de dados não encontrado.", e);
        } catch (SQLException e) {
            // Captura exceção se houver um erro ao conectar ao banco de dados
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar ao banco de dados.", e);
        }
    }

    // Método para testar a conexão com o banco de dados
    public static void testarConexao() {
        try (Connection conexao = getConnection();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1")) {
            // Executa uma consulta simples para verificar a conexão
            if (rs.next()) {
                // Se a consulta retornar um resultado, a conexão está funcionando
                System.out.println("Conexão com o banco de dados bem-sucedida!");
            } else {
                // Se não houver resultados, a conexão falhou
                System.out.println("Falha na conexão com o banco de dados.");
            }
        } catch (Exception e) {
            // Captura qualquer exceção que possa ocorrer durante o teste de conexão
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados.");
        }
    }
    
}
