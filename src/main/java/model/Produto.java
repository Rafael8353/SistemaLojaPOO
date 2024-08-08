package model;

public class Produto {
    private int id; // Identificador único do produto
    private String nome; // Nome do produto
    private double precoCusto; // Preço de custo do produto
    private double precoVenda; // Preço de venda do produto

    // Construtor que aceita quatro parâmetros
    public Produto(int id, String nome, double precoCusto, double precoVenda) {
        this.id = id; // Inicializa o identificador do produto
        this.nome = nome; // Inicializa o nome do produto
        this.precoCusto = precoCusto; // Inicializa o preço de custo do produto
        this.precoVenda = precoVenda; // Inicializa o preço de venda do produto
    }

    // Métodos getters e setters para acessar e modificar os atributos do produto

    public int getId() {
        return id; // Retorna o identificador do produto
    }

    public void setId(int id) {
        this.id = id; // Define o identificador do produto
    }

    public String getNome() {
        return nome; // Retorna o nome do produto
    }

    public void setNome(String nome) {
        this.nome = nome; // Define o nome do produto
    }

    public double getPrecoCusto() {
        return precoCusto; // Retorna o preço de custo do produto
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto; // Define o preço de custo do produto
    }

    public double getPrecoVenda() {
        return precoVenda; // Retorna o preço de venda do produto
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda; // Define o preço de venda do produto
    }

    // Sobrescreve o método toString para fornecer uma representação textual do produto
    @Override
    public String toString() {
        return nome + " - R$" + String.format("%.2f", precoVenda); // Formata a string para exibir o nome e o preço de venda com duas casas decimais
    }
}
