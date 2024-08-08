package gui;

import model.Cliente;
import model.Loja;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import main.DatabaseConnection;

public class ClienteGUI extends JFrame {
    private Loja loja;
    private JTextField tfNome;
    private JTextField tfEmail;
    private JButton btnAdicionar;
    private JButton btnEditar;
    private JButton btnRemover;
    private JButton btnImportar;
    private JButton btnExportar;
    private JComboBox<Cliente> cbClientes;

    // Construtor que inicializa a interface gráfica e recebe um objeto Loja
    public ClienteGUI(Loja loja) {
        this.loja = loja; // Referência à loja para manipulação de clientes
        setTitle("Gerenciar Clientes"); // Título da janela
        setSize(800, 500); // Aumenta o tamanho da janela para acomodar os novos botões
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

        // Campos de texto para nome e e-mail
        tfNome = new JTextField(20);
        tfEmail = new JTextField(20);

        // Ajusta o tamanho da fonte dos campos
        Font fontCampo = new Font("Arial", Font.PLAIN, 18);
        tfNome.setFont(fontCampo);
        tfEmail.setFont(fontCampo);

        // Ajusta o tamanho da fonte dos rótulos
        Font fontRotulo = new Font("Arial", Font.BOLD, 18);

        // Rótulo e campo de texto para o nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(fontRotulo);
        panelCampos.add(lblNome, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCampos.add(tfNome, gbc);

        // Rótulo e campo de texto para o e-mail
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setFont(fontRotulo);
        panelCampos.add(lblEmail, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCampos.add(tfEmail, gbc);

        // Painel para o combo box e os botões
        JPanel panelComboBoxBotoes = new JPanel();
        panelComboBoxBotoes.setLayout(new GridBagLayout()); // Usando GridBagLayout para organização flexível
        panelComboBoxBotoes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem em torno do painel

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(10, 10, 10, 10); // Espaçamento entre componentes
        gbc2.anchor = GridBagConstraints.CENTER;

        // ComboBox para exibir os clientes existentes
        cbClientes = new JComboBox<>();
        cbClientes.setPreferredSize(new Dimension(300, 30)); // Define o tamanho preferido do combo box
        cbClientes.setFont(fontCampo);

        // Botão para adicionar cliente
        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setFont(new Font("Arial", Font.BOLD, 16));
        btnAdicionar.setPreferredSize(new Dimension(120, 40));

        // Botão para editar cliente selecionado
        btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Arial", Font.BOLD, 16));
        btnEditar.setPreferredSize(new Dimension(120, 40));

        // Botão para remover cliente selecionado
        btnRemover = new JButton("Remover");
        btnRemover.setFont(new Font("Arial", Font.BOLD, 16));
        btnRemover.setPreferredSize(new Dimension(120, 40));

        // Botão para importar clientes
        btnImportar = new JButton("Importar Clientes");
        btnImportar.setFont(new Font("Arial", Font.BOLD, 16));
        btnImportar.setPreferredSize(new Dimension(180, 40));

        // Botão para exportar clientes
        btnExportar = new JButton("Exportar Clientes");
        btnExportar.setFont(new Font("Arial", Font.BOLD, 16));
        btnExportar.setPreferredSize(new Dimension(180, 40));

        // Adiciona o combo box e botões ao painel
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        panelComboBoxBotoes.add(cbClientes, gbc2);

        gbc2.gridx = 1;
        gbc2.gridy = 0;
        panelComboBoxBotoes.add(btnAdicionar, gbc2);

        gbc2.gridx = 2;
        gbc2.gridy = 0;
        panelComboBoxBotoes.add(btnEditar, gbc2);

        gbc2.gridx = 3;
        gbc2.gridy = 0;
        panelComboBoxBotoes.add(btnRemover, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 1;
        panelComboBoxBotoes.add(btnImportar, gbc2);

        gbc2.gridx = 1;
        gbc2.gridy = 1;
        panelComboBoxBotoes.add(btnExportar, gbc2);

        // Adiciona os painéis à janela principal
        add(panelCampos, BorderLayout.NORTH);
        add(panelComboBoxBotoes, BorderLayout.CENTER);

        // Adiciona ação ao botão Adicionar
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarCliente(); // Chama o método para adicionar cliente
            }
        });

        // Adiciona ação ao botão Editar
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCliente(); // Chama o método para editar cliente
            }
        });

        // Adiciona ação ao botão Remover
        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerCliente(); // Chama o método para remover cliente
            }
        });

        // Adiciona ação ao botão Importar
        btnImportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importarClientes(); // Chama o método para importar clientes
            }
        });

        // Adiciona ação ao botão Exportar
        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarClientes(); // Chama o método para exportar clientes
            }
        });

        setVisible(true); // Torna a janela visível
        atualizarComboBoxes(); // Inicializa a lista de clientes no combo box
    }

    // Atualiza os itens do combo box com os clientes da loja
    private void atualizarComboBoxes() {
        cbClientes.removeAllItems(); // Remove todos os itens existentes
        for (Cliente cliente : loja.getClientes()) { // Percorre a lista de clientes da loja
            cbClientes.addItem(cliente); // Adiciona cada cliente ao combo box
        }
    }

    // Método para adicionar um cliente ao banco de dados
    private void adicionarCliente() {
        try (Connection conn = DatabaseConnection.getConnection()) { // Conecta ao banco de dados
            String nome = tfNome.getText(); // Obtém o nome do campo de texto
            String email = tfEmail.getText(); // Obtém o email do campo de texto
            String sql = "INSERT INTO clientes (nome, email) VALUES (?, ?)"; // SQL para inserir um novo cliente
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nome); // Define o parâmetro nome
                pstmt.setString(2, email); // Define o parâmetro email
                int rowsAffected = pstmt.executeUpdate(); // Executa a inserção

                if (rowsAffected > 0) { // Verifica se a inserção foi bem-sucedida
                    JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    loja.carregarClientes(); // Recarrega a lista de clientes da loja
                    atualizarComboBoxes(); // Atualiza o combo box
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar cliente. Nenhuma linha foi afetada.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao adicionar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para editar o cliente selecionado no combo box
    private void editarCliente() {
        Cliente cliente = (Cliente) cbClientes.getSelectedItem(); // Obtém o cliente selecionado
        if (cliente != null) {
            try (Connection conn = DatabaseConnection.getConnection()) { // Conecta ao banco de dados
                String nome = tfNome.getText(); // Obtém o novo nome
                String email = tfEmail.getText(); // Obtém o novo email
                String sql = "UPDATE clientes SET nome = ?, email = ? WHERE id = ?"; // SQL para atualizar o cliente
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, nome); // Define o parâmetro nome
                    pstmt.setString(2, email); // Define o parâmetro email
                    pstmt.setInt(3, cliente.getId()); // Define o parâmetro id do cliente
                    int rowsAffected = pstmt.executeUpdate(); // Executa a atualização

                    if (rowsAffected > 0) { // Verifica se a atualização foi bem-sucedida
                        JOptionPane.showMessageDialog(this, "Cliente editado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        loja.carregarClientes(); // Recarrega a lista de clientes da loja
                        atualizarComboBoxes(); // Atualiza o combo box
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao editar cliente. Nenhuma linha foi afetada.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao editar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum cliente selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para remover o cliente selecionado no combo box
    private void removerCliente() {
        Cliente cliente = (Cliente) cbClientes.getSelectedItem(); // Obtém o cliente selecionado
        if (cliente != null) {
            try (Connection conn = DatabaseConnection.getConnection()) { // Conecta ao banco de dados
                String sql = "DELETE FROM clientes WHERE id = ?"; // SQL para deletar o cliente
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, cliente.getId()); // Define o parâmetro id do cliente
                    int rowsAffected = pstmt.executeUpdate(); // Executa a remoção

                    if (rowsAffected > 0) { // Verifica se a remoção foi bem-sucedida
                        JOptionPane.showMessageDialog(this, "Cliente removido com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        loja.carregarClientes(); // Recarrega a lista de clientes da loja
                        atualizarComboBoxes(); // Atualiza o combo box
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado para remoção.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao remover cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum cliente selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para importar clientes de um arquivo CSV
    private void importarClientes() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(",");
                    if (dados.length == 2) {
                        String nome = dados[0];
                        String email = dados[1];
                        adicionarCliente(nome, email); // Adiciona o cliente ao banco de dados
                    }
                }
                loja.carregarClientes(); // Recarrega a lista de clientes da loja
                atualizarComboBoxes(); // Atualiza o combo box
                JOptionPane.showMessageDialog(this, "Clientes importados com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao importar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método auxiliar para adicionar cliente com nome e e-mail
    private void adicionarCliente(String nome, String email) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO clientes (nome, email) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setString(2, email);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para exportar clientes para um arquivo CSV
    private void exportarClientes() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
                for (Cliente cliente : loja.getClientes()) {
                    bw.write(cliente.getNome() + "," + cliente.getEmail());
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(this, "Clientes exportados com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
