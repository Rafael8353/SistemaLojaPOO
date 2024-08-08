package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.DatabaseConnection;

public class Loja {
    private List<Cliente> clientes = new ArrayList<>(); // Lista de clientes da loja
    private List<Produto> produtos = new ArrayList<>(); // Lista de produtos da loja
    private List<Pedido> pedidos = new ArrayList<>(); // Lista de pedidos da loja
    private int proximoIdProduto = 1; // Identificador para o próximo produto
    private int proximoIdCliente = 1; // Identificador para o próximo cliente
    private int proximoIdPedido = 1; // Identificador para o próximo pedido

    // Construtor que inicializa a loja e carrega os dados
    public Loja() {
        carregarDados(); // Carrega os dados dos clientes, produtos e pedidos
    }

    // Carrega todos os dados da loja
    private void carregarDados() {
        carregarClientes(); // Carrega clientes do banco de dados
        carregarProdutos(); // Carrega produtos do banco de dados
        carregarPedidos();  // Carrega pedidos do banco de dados
    }

    // Carrega os clientes do banco de dados
    public void carregarClientes() {
        clientes.clear(); // Limpa a lista atual de clientes
        try (Connection conn = DatabaseConnection.getConnection()) { // Estabelece conexão com o banco de dados
            String sql = "SELECT * FROM clientes"; // Consulta SQL para selecionar todos os clientes
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) { // Executa a consulta
                while (rs.next()) { // Itera sobre os resultados
                    int id = rs.getInt("id"); // Obtém o ID do cliente
                    String nome = rs.getString("nome"); // Obtém o nome do cliente
                    String email = rs.getString("email"); // Obtém o e-mail do cliente
                    clientes.add(new Cliente(id, nome, email)); // Adiciona o cliente à lista
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Exibe erros de SQL
        }
    }

    // Carrega os produtos do banco de dados
    public void carregarProdutos() {
        produtos.clear(); // Limpa a lista atual de produtos
        try (Connection conn = DatabaseConnection.getConnection()) { // Estabelece conexão com o banco de dados
            String sql = "SELECT * FROM produtos"; // Consulta SQL para selecionar todos os produtos
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) { // Executa a consulta
                while (rs.next()) { // Itera sobre os resultados
                    int id = rs.getInt("id"); // Obtém o ID do produto
                    String nome = rs.getString("nome"); // Obtém o nome do produto
                    double precoCusto = rs.getDouble("preco_custo"); // Obtém o preço de custo do produto
                    double precoVenda = rs.getDouble("preco_venda"); // Obtém o preço de venda do produto
                    Produto produto = new Produto(id, nome, precoCusto, precoVenda); // Cria o produto
                    produtos.add(produto); // Adiciona o produto à lista
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Exibe erros de SQL
        }
    }

    // Carrega os pedidos do banco de dados
    public void carregarPedidos() {
        pedidos.clear(); // Limpa a lista atual de pedidos
        try (Connection conn = DatabaseConnection.getConnection()) { // Estabelece conexão com o banco de dados
            String sql = "SELECT * FROM pedidos"; // Consulta SQL para selecionar todos os pedidos
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) { // Executa a consulta
                while (rs.next()) { // Itera sobre os resultados
                    int id = rs.getInt("id"); // Obtém o ID do pedido
                    int clienteId = rs.getInt("cliente_id"); // Obtém o ID do cliente associado ao pedido
                    Cliente cliente = null;

                    // Encontra o cliente associado ao pedido
                    for (Cliente c : clientes) {
                        if (c.getId() == clienteId) {
                            cliente = c;
                            break;
                        }
                    }

                    List<Produto> produtosPedido = new ArrayList<>();
                    String sqlProdutos = "SELECT produto_id FROM pedidos_produtos WHERE pedido_id = ?"; // Consulta SQL para obter IDs dos produtos do pedido
                    try (PreparedStatement pstmtProdutos = conn.prepareStatement(sqlProdutos)) {
                        pstmtProdutos.setInt(1, id); // Define o ID do pedido
                        try (ResultSet rsProdutos = pstmtProdutos.executeQuery()) { // Executa a consulta
                            while (rsProdutos.next()) { // Itera sobre os resultados
                                int produtoId = rsProdutos.getInt("produto_id"); // Obtém o ID do produto
                                // Encontra o produto associado ao pedido
                                for (Produto p : produtos) {
                                    if (p.getId() == produtoId) {
                                        produtosPedido.add(p); // Adiciona o produto à lista de produtos do pedido
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (cliente != null) {
                        Pedido pedido = new Pedido(id, cliente, produtosPedido); // Cria o pedido
                        pedidos.add(pedido); // Adiciona o pedido à lista
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Exibe erros de SQL
        }
    }

    // Obtém a lista de clientes
    public List<Cliente> getClientes() {
        return clientes;
    }

    // Obtém a lista de produtos
    public List<Produto> getProdutos() {
        return produtos;
    }

    // Obtém a lista de pedidos
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    // Gera um novo ID para um produto
    public int gerarNovoIdProduto() {
        return proximoIdProduto++; // Retorna o próximo ID e incrementa o contador
    }

    // Gera um novo ID para um cliente
    public int gerarNovoIdCliente() {
        return proximoIdCliente++; // Retorna o próximo ID e incrementa o contador
    }

    // Gera um novo ID para um pedido
    public int gerarNovoIdPedido() {
        return proximoIdPedido++; // Retorna o próximo ID e incrementa o contador
    }

    // Adiciona um novo produto à lista de produtos
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    // Remove um produto da lista de produtos
    public void removerProduto(Produto produto) {
        produtos.remove(produto);
    }

    // Adiciona um novo cliente à lista de clientes
    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    // Remove um cliente da lista de clientes
    public void removerCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    // Adiciona um novo pedido à lista de pedidos
    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    // Remove um pedido da lista de pedidos
    public void removerPedido(Pedido pedido) {
        pedidos.remove(pedido);
    }

    // Exporta a lista de clientes para um arquivo CSV
    public void exportarClientesParaCSV(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ID,Nome,Email"); // Escreve o cabeçalho
            writer.newLine();
            for (Cliente cliente : clientes) {
                writer.write(cliente.getId() + "," + cliente.getNome() + "," + cliente.getEmail()); // Escreve os dados do cliente
                writer.newLine();
            }
            System.out.println("Clientes exportados com sucesso para " + filePath);
        } catch (IOException e) {
            e.printStackTrace(); // Exibe erros de IO
            System.out.println("Erro ao exportar clientes para CSV: " + e.getMessage());
        }
    }

    // Importa a lista de clientes a partir de um arquivo CSV
    public void importarClientesDeCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            reader.readLine(); // Ignora o cabeçalho
            while ((linha = reader.readLine()) != null) { // Lê cada linha do arquivo
                String[] campos = linha.split(","); // Divide a linha em campos
                int id = Integer.parseInt(campos[0]); // Obtém o ID
                String nome = campos[1]; // Obtém o nome
                String email = campos[2]; // Obtém o e-mail
                clientes.add(new Cliente(id, nome, email)); // Adiciona o cliente à lista
            }
            System.out.println("Clientes importados com sucesso de " + filePath);
        } catch (IOException e) {
            e.printStackTrace(); // Exibe erros de IO
            System.out.println("Erro ao importar clientes de CSV: " + e.getMessage());
        }
    }

    // Exporta a lista de produtos para um arquivo CSV
    public void exportarProdutosParaCSV(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ID,Nome,PrecoCusto,PrecoVenda"); // Escreve o cabeçalho
            writer.newLine();
            for (Produto produto : produtos) {
                writer.write(produto.getId() + "," + produto.getNome() + "," + produto.getPrecoCusto() + "," + produto.getPrecoVenda()); // Escreve os dados do produto
                writer.newLine();
            }
            System.out.println("Produtos exportados com sucesso para " + filePath);
        } catch (IOException e) {
            e.printStackTrace(); // Exibe erros de IO
            System.out.println("Erro ao exportar produtos para CSV: " + e.getMessage());
        }
    }

    // Importa a lista de produtos a partir de um arquivo CSV
    public void importarProdutosDeCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            reader.readLine(); // Ignora o cabeçalho
            while ((linha = reader.readLine()) != null) { // Lê cada linha do arquivo
                String[] campos = linha.split(","); // Divide a linha em campos
                int id = Integer.parseInt(campos[0]); // Obtém o ID
                String nome = campos[1]; // Obtém o nome
                double precoCusto = Double.parseDouble(campos[2]); // Obtém o preço de custo
                double precoVenda = Double.parseDouble(campos[3]); // Obtém o preço de venda
                produtos.add(new Produto(id, nome, precoCusto, precoVenda)); // Adiciona o produto à lista
            }
            System.out.println("Produtos importados com sucesso de " + filePath);
        } catch (IOException e) {
            e.printStackTrace(); // Exibe erros de IO
            System.out.println("Erro ao importar produtos de CSV: " + e.getMessage());
        }
    }

    // Exporta a lista de pedidos para um arquivo CSV
    public void exportarPedidosParaCSV(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ID,ClienteID,ProdutoIDs"); // Escreve o cabeçalho
            writer.newLine();
            for (Pedido pedido : pedidos) {
                StringBuilder produtoIds = new StringBuilder();
                for (Produto produto : pedido.getProdutos()) {
                    if (produtoIds.length() > 0) {
                        produtoIds.append(";"); // Se houver mais de um produto, separa os IDs com ponto e vírgula
                    }
                    produtoIds.append(produto.getId()); // Adiciona o ID do produto
                }
                writer.write(pedido.getId() + "," + pedido.getCliente().getId() + "," + produtoIds); // Escreve os dados do pedido
                writer.newLine();
            }
            System.out.println("Pedidos exportados com sucesso para " + filePath);
        } catch (IOException e) {
            e.printStackTrace(); // Exibe erros de IO
            System.out.println("Erro ao exportar pedidos para CSV: " + e.getMessage());
        }
    }

    // Importa a lista de pedidos a partir de um arquivo CSV
    public void importarPedidosDeCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            reader.readLine(); // Ignora o cabeçalho
            while ((linha = reader.readLine()) != null) { // Lê cada linha do arquivo
                String[] campos = linha.split(","); // Divide a linha em campos
                int id = Integer.parseInt(campos[0]); // Obtém o ID do pedido
                int clienteId = Integer.parseInt(campos[1]); // Obtém o ID do cliente
                String[] produtoIds = campos[2].split(";"); // Obtém os IDs dos produtos

                Cliente cliente = null;
                // Encontra o cliente associado ao pedido
                for (Cliente c : clientes) {
                    if (c.getId() == clienteId) {
                        cliente = c;
                        break;
                    }
                }

                List<Produto> produtosPedido = new ArrayList<>();
                // Encontra os produtos associados ao pedido
                for (String produtoIdStr : produtoIds) {
                    int produtoId = Integer.parseInt(produtoIdStr);
                    for (Produto p : produtos) {
                        if (p.getId() == produtoId) {
                            produtosPedido.add(p); // Adiciona o produto à lista de produtos do pedido
                            break;
                        }
                    }
                }

                if (cliente != null) {
                    Pedido pedido = new Pedido(id, cliente, produtosPedido); // Cria o pedido
                    pedidos.add(pedido); // Adiciona o pedido à lista
                }
            }
            System.out.println("Pedidos importados com sucesso de " + filePath);
        } catch (IOException e) {
            e.printStackTrace(); // Exibe erros de IO
            System.out.println("Erro ao importar pedidos de CSV: " + e.getMessage());
        }
    }
}
