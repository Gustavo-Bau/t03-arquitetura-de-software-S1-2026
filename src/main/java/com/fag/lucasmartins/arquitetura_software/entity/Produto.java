package com.fag.lucasmartins.arquitetura_software.entity;

import com.fag.lucasmartins.arquitetura_software.exception.ProdutoException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double preco;
    private int estoque;
    private double precoFinal;

    // Constructors
    public Produto() {}

    public Produto(String nome, double preco, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.precoFinal = calcularPrecoFinal();
        validarProduto();
    }

    // Business logic
    private void validarProduto() {
        if (nome != null && nome.toLowerCase().contains("premium") && preco < 100.0) {
            throw new ProdutoException("Produtos Premium não podem custar menos de R$ 100,00.");
        }
    }

    private double calcularPrecoFinal() {
        if (estoque >= 50) {
            return preco - (preco * 0.10);
        }
        return preco;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public double getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(double precoFinal) {
        this.precoFinal = precoFinal;
    }
}