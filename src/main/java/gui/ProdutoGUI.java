package gui;

import model.Produto;
import model.Loja;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import main.DatabaseConnection;

public class ProdutoGUI extends JFrame {
    private Loja loja;
    private JTextField tfNome;
    private JTextField tfPrecoCusto;
    private JTextField tfPrecoVenda;
    private JButton btnAdicionar;
    private JButton btnEditar;
    private JButton btnRemover;
    private JComboBox<Produto> cbProdutos;

    // Construtor que inicializa a interface gráfica e recebe um objeto Loja
    public ProdutoGUI(Loja loja) {
        this.loja = loja; // Referência à loja para manipulação de produtos
        setTitle("Gerenciar Produtos"); // Título da janela
        setSize(800, 400); // Define o tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao fechar a janela
        setLayout(new BorderLayout()); // Define o layout da janela

        // Painel para os campos de entrada de dados
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridBagLayout()); // Usando GridBagLayout para organização flexível
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem em torno do painel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Campos de texto para nome, preço de custo e preço de venda
        tfNome = new JTextField(20);
        tfPrecoCusto = new JTextField(20);
        tfPrecoVenda = new JTextField(20);

        // Ajusta o tamanho da fonte dos campos
        Font fontCampo = new Font("Arial", Font.PLAIN, 18);
        tfNome.setFont(fontCampo);
        tfPrecoCusto.setFont(fontCampo);
        tfPrecoVenda.setFont(fontCampo);

        // Ajusta o tamanho da fonte dos rótulos
        Font fontRotulo = new Font("Arial", Font.BOLD, 18);

        // Rótulo e campo de texto para o nome do produto
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(fontRotulo);
        panelCampos.add(lblNome, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCampos.add(tfNome, gbc);

        // Rótulo e campo de texto para o preço de custo
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblPrecoCusto = new JLabel("Preço Custo:");
        lblPrecoCusto.setFont(fontRotulo);
        panelCampos.add(lblPrecoCusto, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCampos.add(tfPrecoCusto, gbc);

        // Rótulo e campo de texto para o preço de venda
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblPrecoVenda = new JLabel("Preço Venda:");
        lblPrecoVenda.setFont(fontRotulo);
        panelCampos.add(lblPrecoVenda, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panelCampos.add(tfPrecoVenda, gbc);

        // Painel para o combo box e os botões
        JPanel panelComboBoxBotoes = new JPanel();
        panelComboBoxBotoes.setLayout(new GridBagLayout()); // Usando GridBagLayout para organização flexível
        panelComboBoxBotoes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem em torno do painel

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(10, 10, 10, 10); // Espaçamento entre componentes
        gbc2.anchor = GridBagConstraints.CENTER;

        // ComboBox para exibir os produtos existentes
        cbProdutos = new JComboBox<>();
        cbProdutos.setPreferredSize(new Dimension(300, 30)); // Define o tamanho preferido do combo box
        cbProdutos.setFont(fontCampo);

        // Botão para adicionar produto
        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setFont(new Font("Arial", Font.BOLD, 16));
        btnAdicionar.setPreferredSize(new Dimension(120, 40));

        // Botão para editar produto selecionado
        btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Arial", Font.BOLD, 16));
        btnEditar.setPreferredSize(new Dimension(120, 40));

        // Botão para remover produto selecionado
        btnRemover = new JButton("Remover");
        btnRemover.setFont(new Font("Arial", Font.BOLD, 16));
        btnRemover.setPreferredSize(new Dimension(120, 40));

        // Adiciona o combo box e botões ao painel
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        panelComboBoxBotoes.add(cbProdutos, gbc2);

        gbc2.gridx = 1;
        gbc2.gridy = 0;
        panelComboBoxBotoes.add(btnAdicionar, gbc2);

        gbc2.gridx = 2;
        gbc2.gridy = 0;
        panelComboBoxBotoes.add(btnEditar, gbc2);

        gbc2.gridx = 3;
        gbc2.gridy = 0;
        panelComboBoxBotoes.add(btnRemover, gbc2);

        // Adiciona os painéis à janela principal
        add(panelCampos, BorderLayout.NORTH);
        add(panelComboBoxBotoes, BorderLayout.CENTER);

        // Adiciona ação ao botão Adicionar
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarProduto(); // Chama o método para adicionar produto
            }
        });

        // Adiciona ação ao botão Editar
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarProduto(); // Chama o método para editar produto
            }
        });

        // Adiciona ação ao botão Remover
        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerProduto(); // Chama o método para remover produto
            }
        });

        setVisible(true); // Torna a janela visível
        atualizarComboBoxes(); // Inicializa a lista de produtos no combo box
    }

    // Atualiza os itens do combo box com os produtos da loja
    private void atualizarComboBoxes() {
        cbProdutos.removeAllItems(); // Remove todos os itens existentes
        for (Produto produto : loja.getProdutos()) { // Percorre a lista de produtos da loja
            cbProdutos.addItem(produto); // Adiciona cada produto ao combo box
        }
    }

    // Método para adicionar um produto ao banco de dados
    private void adicionarProduto() {
        try (Connection conn = DatabaseConnection.getConnection()) { // Conecta ao banco de dados
            String nome = tfNome.getText(); // Obtém o nome do campo de texto
            double precoCusto = Double.parseDouble(tfPrecoCusto.getText()); // Converte o preço de custo para double
            double precoVenda = Double.parseDouble(tfPrecoVenda.getText()); // Converte o preço de venda para double
            String sql = "INSERT INTO produtos (nome, preco_custo, preco_venda) VALUES (?, ?, ?)"; // SQL para inserir um novo produto
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nome); // Define o parâmetro nome
                pstmt.setDouble(2, precoCusto); // Define o parâmetro preço de custo
                pstmt.setDouble(3, precoVenda); // Define o parâmetro preço de venda
                int rowsAffected = pstmt.executeUpdate(); // Executa a inserção

                if (rowsAffected > 0) { // Verifica se a inserção foi bem-sucedida
                    JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    loja.carregarProdutos(); // Recarrega a lista de produtos da loja
                    atualizarComboBoxes(); // Atualiza o combo box
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar produto. Nenhuma linha foi afetada.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao adicionar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao converter os preços. Verifique os valores inseridos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para editar o produto selecionado no combo box
    private void editarProduto() {
        Produto produto = (Produto) cbProdutos.getSelectedItem(); // Obtém o produto selecionado
        if (produto != null) {
            try (Connection conn = DatabaseConnection.getConnection()) { // Conecta ao banco de dados
                String nome = tfNome.getText(); // Obtém o novo nome
                double precoCusto = Double.parseDouble(tfPrecoCusto.getText()); // Converte o novo preço de custo
                double precoVenda = Double.parseDouble(tfPrecoVenda.getText()); // Converte o novo preço de venda
                String sql = "UPDATE produtos SET nome = ?, preco_custo = ?, preco_venda = ? WHERE id = ?"; // SQL para atualizar o produto
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, nome); // Define o parâmetro nome
                    pstmt.setDouble(2, precoCusto); // Define o parâmetro preço de custo
                    pstmt.setDouble(3, precoVenda); // Define o parâmetro preço de venda
                    pstmt.setInt(4, produto.getId()); // Define o parâmetro id
                    int rowsAffected = pstmt.executeUpdate(); // Executa a atualização

                    if (rowsAffected > 0) { // Verifica se a atualização foi bem-sucedida
                        JOptionPane.showMessageDialog(this, "Produto editado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        loja.carregarProdutos(); // Atualiza a lista de produtos no modelo Loja
                        atualizarComboBoxes(); // Atualiza a interface com os produtos mais recentes
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao editar produto. Nenhuma linha foi afetada.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao editar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao converter os preços. Verifique os valores inseridos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum produto selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para remover o produto selecionado no combo box
    private void removerProduto() {
        Produto produto = (Produto) cbProdutos.getSelectedItem(); // Obtém o produto selecionado
        if (produto != null) {
            try (Connection conn = DatabaseConnection.getConnection()) { // Conecta ao banco de dados
                String sql = "DELETE FROM produtos WHERE id = ?"; // SQL para deletar o produto
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, produto.getId()); // Define o parâmetro id do produto
                    int rowsAffected = pstmt.executeUpdate(); // Executa a remoção

                    if (rowsAffected > 0) { // Verifica se a remoção foi bem-sucedida
                        JOptionPane.showMessageDialog(this, "Produto removido com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        loja.carregarProdutos(); // Recarrega a lista de produtos da loja
                        atualizarComboBoxes(); // Atualiza o combo box
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhum produto encontrado para remoção.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao remover produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum produto selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}
