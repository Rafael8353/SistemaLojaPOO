package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Loja;

public class TelaInicial extends JFrame {
    private Loja loja; // Referência ao objeto Loja que contém dados e lógica da loja

    // Construtor da classe, que configura a interface gráfica inicial
    public TelaInicial(Loja loja) {
        this.loja = loja; // Recebe o objeto Loja

        // Configurações básicas da janela
        setTitle("Tela Inicial"); // Define o título da janela
        setSize(800, 400); // Define o tamanho da janela
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define o comportamento ao fechar a janela
        setLayout(new BorderLayout()); // Define o layout da janela como BorderLayout

        // Criação e configuração do rótulo de título
        JLabel lblTitulo = new JLabel("Software de Gerenciamento de Loja de Eletrônicos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24)); // Define a fonte e o tamanho do título
        add(lblTitulo, BorderLayout.NORTH); // Adiciona o rótulo ao topo da janela

        // Criação e configuração do painel para botões
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Define o layout do painel como GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Define o espaçamento interno dos botões
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz com que os botões se estiquem horizontalmente
        gbc.gridx = 0; // Define a posição inicial das colunas
        gbc.gridy = 0; // Define a posição inicial das linhas

        // Configura a fonte dos botões
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        // Criação e configuração do botão para gerenciar produtos
        JButton btnProduto = new JButton("Gerenciar Produtos");
        btnProduto.setFont(buttonFont); // Define a fonte do botão
        btnProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProdutoGUI(loja).setVisible(true); // Abre a janela de gerenciamento de produtos
            }
        });
        gbc.gridy = 1; // Define a posição vertical do botão
        panel.add(btnProduto, gbc); // Adiciona o botão ao painel

        // Criação e configuração do botão para gerenciar clientes
        JButton btnCliente = new JButton("Gerenciar Clientes");
        btnCliente.setFont(buttonFont); // Define a fonte do botão
        btnCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteGUI(loja).setVisible(true); // Abre a janela de gerenciamento de clientes
            }
        });
        gbc.gridy = 2; // Define a posição vertical do botão
        panel.add(btnCliente, gbc); // Adiciona o botão ao painel

        // Criação e configuração do botão para gerenciar pedidos
        JButton btnPedido = new JButton("Gerenciar Pedidos");
        btnPedido.setFont(buttonFont); // Define a fonte do botão
        btnPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PedidoGUI(loja).setVisible(true); // Abre a janela de gerenciamento de pedidos
            }
        });
        gbc.gridy = 3; // Define a posição vertical do botão
        panel.add(btnPedido, gbc); // Adiciona o botão ao painel

        // Adiciona o painel com os botões ao centro da janela
        add(panel, BorderLayout.CENTER);
    }
}
