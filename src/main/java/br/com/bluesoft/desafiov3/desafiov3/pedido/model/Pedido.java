package br.com.bluesoft.desafiov3.desafiov3.pedido.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "retirada_na_loja")
    private boolean retiradaNaLoja;

    @Column(name = "forma_pagamento")
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Size(min=1, max=50)
    private List<ItemPedido> itens = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isRetiradaNaLoja() {
        return retiradaNaLoja;
    }

    public void setRetiradaNaLoja(boolean retiradaNaLoja) {
        this.retiradaNaLoja = retiradaNaLoja;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(this.itens);
    }
    
    public void adicionarItem(ItemPedido item) { 
        this.itens.add(item); 
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens.clear();
        if(itens != null) {
        	this.itens.addAll(itens);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        Pedido pedido = (Pedido) o;
        return Objects.equals(getId(), pedido.getId());
    }

    @Override
    public String toString() {
        return "Pedido{" +
               "id=" + id +
               ", retiradaNaLoja=" + retiradaNaLoja +
               ", formaPagamento=" + formaPagamento +
               ", itens=" + itens +
               '}';
    }
}
