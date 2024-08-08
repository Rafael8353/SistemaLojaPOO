package model;

import java.util.List;

// A classe Pedido representa um pedido feito por um cliente e implementa a interface Gerenciavel
public class Pedido implements Gerenciavel {
    private int id; // Identificador único do pedido
    private Cliente cliente; // Cliente que fez o pedido
    private List<Produto> produtos; // Lista de produtos incluídos no pedido

    // Construtor para inicializar um novo pedido com os atributos fornecidos
    public Pedido(int id, Cliente cliente, List<Produto> produtos) {
        this.id = id; // Define o identificador do pedido
        this.cliente = cliente; // Define o cliente associado ao pedido
        this.produtos = produtos; // Define a lista de produtos do pedido
    }

    // Obtém o identificador único do pedido
    @Override
    public int getId() {
        return id; // Retorna o identificador do pedido
    }

    // Define o identificador único do pedido
    @Override
    public void setId(int id) {
        this.id = id; // Define o identificador do pedido
    }

    // Obtém o cliente associado ao pedido
    public Cliente getCliente() {
        return cliente; // Retorna o cliente do pedido
    }

    // Obtém a lista de produtos incluídos no pedido
    public List<Produto> getProdutos() {
        return produtos; // Retorna a lista de produtos do pedido
    }

    // Sobrescreve o método toString para retornar uma representação textual do pedido
    @Override
    public String toString() {
        // Retorna uma string com o número do pedido e o nome do cliente, se disponível
        return "Pedido #" + id + " - Cliente: " + (cliente != null ? cliente.getNome() : "Desconhecido");
    }
}
