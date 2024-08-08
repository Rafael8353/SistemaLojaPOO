package gui;

import model.Pedido;
import model.Cliente;
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

public class PedidoGUI extends JFrame {
    // Declaração dos componentes da interface gráfica e da loja
    private Loja loja;
    private JComboBox<Cliente> cbClientes;
    private JComboBox<Produto> cbProdutos;
    private JTextField tfQuantidade;
    private JButton btnAdicionar;
    private JButton btnEditar;
    private JButton btnRemover;
    private JComboBox<Pedido> cbPedidos;
    private DefaultComboBoxModel<Pedido> pedidoModel;

    // Construtor da classe PedidoGUI
    public PedidoGUI(Loja loja) {
        this.loja = loja;  // Inicializa a loja
        setTitle("Gerenciar Pedidos");  // Define o título da janela
        setSize(800, 600);  // Define o tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Define o comportamento ao fechar a janela
        setLayout(new BorderLayout());  // Define o layout da janela

        // Painel para os campos de entrada
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridBagLayout());  // Usa o layout GridBag para o painel
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Define margens ao redor do painel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Define margens internas
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Define o preenchimento horizontal
        gbc.anchor = GridBagConstraints.CENTER;  // Define o ancoramento no centro

        // Configuração do JComboBox para selecionar clientes
        cbClientes = new JComboBox<>();
        cbClientes.setPreferredSize(new Dimension(200, 30));  // Define o tamanho preferido
        cbClientes.setFont(new Font("Arial", Font.PLAIN, 18));  // Define a fonte

        // Configuração do JComboBox para selecionar produtos
        cbProdutos = new JComboBox<>();
        cbProdutos.setPreferredSize(new Dimension(200, 30));  // Define o tamanho preferido
        cbProdutos.setFont(new Font("Arial", Font.PLAIN, 18));  // Define a fonte

        // Campo de texto para a quantidade
        tfQuantidade = new JTextField(10);
        tfQuantidade.setFont(new Font("Arial", Font.PLAIN, 18));  // Define a fonte

        // Fonte para os rótulos
        Font fontRotulo = new Font("Arial", Font.BOLD, 18);

        // Adiciona o rótulo e o JComboBox de clientes ao painel
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(fontRotulo);
        panelCampos.add(lblCliente, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCampos.add(cbClientes, gbc);

        // Adiciona o rótulo e o JComboBox de produtos ao painel
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblProduto = new JLabel("Produto:");
        lblProduto.setFont(fontRotulo);
        panelCampos.add(lblProduto, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCampos.add(cbProdutos, gbc);

        // Adiciona o rótulo e o campo de texto para quantidade ao painel
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setFont(fontRotulo);
        panelCampos.add(lblQuantidade, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panelCampos.add(tfQuantidade, gbc);

        // Painel para os botões e a lista de pedidos
        JPanel panelBotoesLista = new JPanel();
        panelBotoesLista.setLayout(new GridBagLayout());  // Usa o layout GridBag
        panelBotoesLista.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Define margens ao redor do painel

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(10, 10, 10, 10);  // Define margens internas
        gbc2.anchor = GridBagConstraints.CENTER;  // Define o ancoramento no centro

        // Botão para adicionar pedidos
        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setFont(new Font("Arial", Font.BOLD, 16));  // Define a fonte do botão
        btnAdicionar.setPreferredSize(new Dimension(120, 40));  // Define o tamanho preferido

        // Botão para editar pedidos
        btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Arial", Font.BOLD, 16));  // Define a fonte do botão
        btnEditar.setPreferredSize(new Dimension(120, 40));  // Define o tamanho preferido

        // Botão para remover pedidos
        btnRemover = new JButton("Remover");
        btnRemover.setFont(new Font("Arial", Font.BOLD, 16));  // Define a fonte do botão
        btnRemover.setPreferredSize(new Dimension(120, 40));  // Define o tamanho preferido

        // Modelo e JComboBox para exibir pedidos
        pedidoModel = new DefaultComboBoxModel<>();
        cbPedidos = new JComboBox<>(pedidoModel);
        cbPedidos.setPreferredSize(new Dimension(300, 200));  // Define o tamanho preferido
        JScrollPane scrollPane = new JScrollPane(cbPedidos);  // Adiciona o JComboBox a um JScrollPane
        scrollPane.setPreferredSize(new Dimension(300, 200));  // Define o tamanho preferido do JScrollPane

        // Adiciona a lista de pedidos e os botões ao painel
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        panelBotoesLista.add(scrollPane, gbc2);

        gbc2.gridx = 1;
        gbc2.gridy = 0;
        panelBotoesLista.add(btnAdicionar, gbc2);

        gbc2.gridx = 2;
        gbc2.gridy = 0;
        panelBotoesLista.add(btnEditar, gbc2);

        gbc2.gridx = 3;
        gbc2.gridy = 0;
        panelBotoesLista.add(btnRemover, gbc2);

        // Adiciona os painéis ao layout principal
        add(panelCampos, BorderLayout.NORTH);  // Adiciona o painel de campos ao topo
        add(panelBotoesLista, BorderLayout.CENTER);  // Adiciona o painel de botões e lista ao centro

        // Define os listeners para os botões
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarPedido();  // Chama o método para adicionar pedidos
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarPedido();  // Chama o método para editar pedidos
            }
        });

        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerPedido();  // Chama o método para remover pedidos
            }
        });

        setVisible(true);  // Torna a janela visível
        atualizarComboBoxes();  // Atualiza os JComboBox com os dados mais recentes
    }

    // Método para atualizar os JComboBox com clientes, produtos e pedidos
    private void atualizarComboBoxes() {
        cbClientes.removeAllItems();
        for (Cliente cliente : loja.getClientes()) {
            cbClientes.addItem(cliente);
        }

        cbProdutos.removeAllItems();
        for (Produto produto : loja.getProdutos()) {
            cbProdutos.addItem(produto);
        }

        pedidoModel.removeAllElements();
        for (Pedido pedido : loja.getPedidos()) {
            pedidoModel.addElement(pedido);
        }
    }

    // Método para adicionar um novo pedido
    private void adicionarPedido() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Cliente cliente = (Cliente) cbClientes.getSelectedItem();  // Obtém o cliente selecionado
            Produto produto = (Produto) cbProdutos.getSelectedItem();  // Obtém o produto selecionado
           

            String sql = "INSERT INTO pedidos (cliente_id) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, cliente.getId());  // Define o ID do cliente
                int rowsAffected = pstmt.executeUpdate();  // Executa a inserção

                if (rowsAffected > 0) {  // Verifica se a inserção foi bem-sucedida
                    try (var generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {  // Verifica se a chave gerada está disponível
                            int pedidoId = generatedKeys.getInt(1);  // Obtém o ID do pedido

                            // Insere o produto relacionado ao pedido
                            sql = "INSERT INTO pedidos_produtos (pedido_id, produto_id) VALUES (?, ?)";
                            try (PreparedStatement pstmtProduto = conn.prepareStatement(sql)) {
                                pstmtProduto.setInt(1, pedidoId);
                                pstmtProduto.setInt(2, produto.getId());
                                pstmtProduto.executeUpdate();
                            }

                            // Mostra uma mensagem de sucesso e atualiza a interface
                            JOptionPane.showMessageDialog(this, "Pedido adicionado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            loja.carregarPedidos();
                            atualizarComboBoxes();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar pedido. Nenhuma linha foi afetada.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao adicionar pedido: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao converter a quantidade. Verifique o valor inserido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para editar um pedido existente
    private void editarPedido() {
        Pedido pedido = (Pedido) cbPedidos.getSelectedItem();
        if (pedido != null) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                Cliente cliente = (Cliente) cbClientes.getSelectedItem();  // Obtém o cliente selecionado
                Produto produto = (Produto) cbProdutos.getSelectedItem();  // Obtém o produto selecionado
                

                String sql = "UPDATE pedidos SET cliente_id = ? WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, cliente.getId());  // Define o novo ID do cliente
                    pstmt.setInt(2, pedido.getId());  // Define o ID do pedido a ser atualizado
                    int rowsAffected = pstmt.executeUpdate();  // Executa a atualização

                    if (rowsAffected > 0) {  // Verifica se a atualização foi bem-sucedida
                        // Remove os produtos antigos associados ao pedido
                        sql = "DELETE FROM pedidos_produtos WHERE pedido_id = ?";
                        try (PreparedStatement pstmtDelete = conn.prepareStatement(sql)) {
                            pstmtDelete.setInt(1, pedido.getId());
                            pstmtDelete.executeUpdate();
                        }

                        // Insere os novos produtos associados ao pedido
                        sql = "INSERT INTO pedidos_produtos (pedido_id, produto_id) VALUES (?, ?)";
                        try (PreparedStatement pstmtProduto = conn.prepareStatement(sql)) {
                            pstmtProduto.setInt(1, pedido.getId());
                            pstmtProduto.setInt(2, produto.getId());
                            pstmtProduto.executeUpdate();
                        }

                        // Mostra uma mensagem de sucesso e atualiza a interface
                        JOptionPane.showMessageDialog(this, "Pedido atualizado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        loja.carregarPedidos();
                        atualizarComboBoxes();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao atualizar pedido. Nenhuma linha foi afetada.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao atualizar pedido: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao converter a quantidade. Verifique o valor inserido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum pedido selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para remover um pedido existente
    private void removerPedido() {
        Pedido pedido = (Pedido) cbPedidos.getSelectedItem();
        if (pedido != null) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                // Remove os produtos associados ao pedido
                String sql = "DELETE FROM pedidos_produtos WHERE pedido_id = ?";
                try (PreparedStatement pstmtDeleteProdutos = conn.prepareStatement(sql)) {
                    pstmtDeleteProdutos.setInt(1, pedido.getId());
                    pstmtDeleteProdutos.executeUpdate();
                }

                // Remove o pedido
                sql = "DELETE FROM pedidos WHERE id = ?";
                try (PreparedStatement pstmtDeletePedido = conn.prepareStatement(sql)) {
                    pstmtDeletePedido.setInt(1, pedido.getId());
                    int rowsAffected = pstmtDeletePedido.executeUpdate();

                    if (rowsAffected > 0) {  // Verifica se a remoção foi bem-sucedida
                        // Mostra uma mensagem de sucesso e atualiza a interface
                        JOptionPane.showMessageDialog(this, "Pedido removido com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        loja.carregarPedidos();
                        atualizarComboBoxes();
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhum pedido encontrado para remoção.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao remover pedido: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum pedido selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}
