package model;

// A classe Cliente representa um cliente na loja e estende Pessoa
public class Cliente extends Pessoa {

    // Construtor da classe Cliente que inicializa os atributos herdados
    public Cliente(int id, String nome, String email) {
        super(id, nome, email); // Chama o construtor da classe pai (Pessoa)
    }

    // Implementação do método abstrato da classe Pessoa
    @Override
    public String getTipoPessoa() {
        return "Cliente"; // Retorna a string "Cliente" para identificar o tipo de pessoa
    }
}
