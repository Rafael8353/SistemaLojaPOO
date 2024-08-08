package main;

import model.Loja;
import gui.TelaInicial;

public class Main {
    public static void main(String[] args) {
        // Inicializa a loja
        Loja loja = new Loja();
        // Testa a conexão com o banco de dados
        DatabaseConnection.testarConexao();
        // Cria a tela inicial
        TelaInicial telaInicial = new TelaInicial(loja);
        telaInicial.setVisible(true);
    }
}