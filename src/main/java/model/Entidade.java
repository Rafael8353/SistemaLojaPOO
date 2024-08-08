package model;

// A interface Entidade define um contrato para entidades com um identificador único
public interface Entidade {
    // Método para obter o identificador da entidade
    int getId();

    // Método para definir o identificador da entidade
    void setId(int id);
}
