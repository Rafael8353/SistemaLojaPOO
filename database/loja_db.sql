-- Cria o banco de dados
CREATE DATABASE loja_db;

-- Usa o banco de dados
USE loja_db;

-- Tabela de Clientes
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Tabela de Produtos
CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco_custo DECIMAL(10, 2) NOT NULL,
    preco_venda DECIMAL(10, 2) NOT NULL
);

-- Tabela de Pedidos
CREATE TABLE pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

-- Tabela de Produtos em Pedidos (relacionamento muitos-para-muitos)
CREATE TABLE pedidos_produtos (
    pedido_id INT,
    produto_id INT,
    PRIMARY KEY (pedido_id, produto_id),
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
);
-- Inserir Clientes
INSERT INTO clientes (nome, email) VALUES 
('João Silva', 'joao.silva@example.com'),
('Maria Oliveira', 'maria.oliveira@example.com'),
('Pedro Santos', 'pedro.santos@example.com');

-- Inserir Produtos
INSERT INTO produtos (nome, preco_custo, preco_venda) VALUES 
('Computador', 1500.00, 2000.00),
('Smartphone', 500.00, 700.00),
('Televisão', 1200.00, 1500.00);

-- Inserir Pedidos
INSERT INTO pedidos (cliente_id) VALUES 
(1),
(2),
(1);

-- Inserir Produtos em Pedidos
INSERT INTO pedidos_produtos (pedido_id, produto_id) VALUES 
(1, 1), -- Pedido 1 com Computador
(1, 2), -- Pedido 1 com Smartphone
(2, 3), -- Pedido 2 com Televisão
(3, 2); -- Pedido 3 com Smartphone



