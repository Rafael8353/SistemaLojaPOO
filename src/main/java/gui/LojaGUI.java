package gui;

import model.Loja;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LojaGUI extends JFrame {
    // Referência para o objeto Loja, que contém a lógica e dados da loja
    private Loja loja;

    // Construtor da classe, que configura a interface gráfica
    public LojaGUI(Loja loja) {
        this.loja = loja; // Recebe o objeto Loja
        setTitle("Gerenciamento de Loja"); // Define o título da janela
        setSize(800, 600); // Define o tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define o comportamento ao fechar a janela
        setLocationRelativeTo(null); // Centraliza a janela na tela

        // Configuração do painel de botões
        JPanel painelBotoes = new JPanel();
        JButton botaoClientes = new JButton("Gerenciar Clientes"); // Botão para gerenciar clientes
        JButton botaoProdutos = new JButton("Gerenciar Produtos"); // Botão para gerenciar produtos
        JButton botaoPedidos = new JButton("Gerenciar Pedidos"); // Botão para gerenciar pedidos

        // Adiciona ouvintes de eventos para os botões
        botaoClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTelaClientes(); // Exibe a tela de clientes quando o botão é clicado
            }
        });

        botaoProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTelaProdutos(); // Exibe a tela de produtos quando o botão é clicado
            }
        });

        botaoPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTelaPedidos(); // Exibe a tela de pedidos quando o botão é clicado
            }
        });

        // Adiciona os botões ao painel
        painelBotoes.add(botaoClientes);
        painelBotoes.add(botaoProdutos);
        painelBotoes.add(botaoPedidos);

        // Adiciona o painel de botões à parte superior da janela
        add(painelBotoes, BorderLayout.NORTH);

        // Exibe a tela de clientes por padrão quando a aplicação é iniciada
        mostrarTelaClientes();
    }

    // Exibe a tela de gerenciamento de clientes
    private void mostrarTelaClientes() {
        ClienteGUI clienteGUI = new ClienteGUI(loja); // Cria uma nova instância da tela de clientes
        setContentPane(clienteGUI); // Substitui o conteúdo da janela pela tela de clientes
        revalidate(); // Revalida o layout da janela para aplicar as mudanças
    }

    // Exibe a tela de gerenciamento de produtos
    private void mostrarTelaProdutos() {
        ProdutoGUI produtoGUI = new ProdutoGUI(loja); // Cria uma nova instância da tela de produtos
        setContentPane(produtoGUI); // Substitui o conteúdo da janela pela tela de produtos
        revalidate(); // Revalida o layout da janela para aplicar as mudanças
    }

    // Exibe a tela de gerenciamento de pedidos
    private void mostrarTelaPedidos() {
        PedidoGUI pedidoGUI = new PedidoGUI(loja); // Cria uma nova instância da tela de pedidos
        setContentPane(pedidoGUI); // Substitui o conteúdo da janela pela tela de pedidos
        revalidate(); // Revalida o layout da janela para aplicar as mudanças
    }
}
