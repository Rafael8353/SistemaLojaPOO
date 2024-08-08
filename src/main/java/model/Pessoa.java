package model;

// A classe abstrata Pessoa representa uma entidade com nome e email
public abstract class Pessoa implements Gerenciavel {
    private int id; // Identificador único da pessoa
    private String nome; // Nome da pessoa
    private String email; // Email da pessoa

    // Construtor para inicializar uma nova Pessoa com id, nome e email
    public Pessoa(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    // Obtém o ID da pessoa
    @Override
    public int getId() {
        return id;
    }

    // Define o ID da pessoa
    @Override
    public void setId(int id) {
        this.id = id;
    }

    // Obtém o nome da pessoa
    public String getNome() {
        return nome;
    }

    // Define o nome da pessoa
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Obtém o email da pessoa
    public String getEmail() {
        return email;
    }

    // Define o email da pessoa
    public void setEmail(String email) {
        this.email = email;
    }

    // Método abstrato para ser implementado pelas subclasses
    public abstract String getTipoPessoa();

    // Método sobrescrito para representar a pessoa como uma String
    @Override
    public String toString() {
        return nome;
    }
}
