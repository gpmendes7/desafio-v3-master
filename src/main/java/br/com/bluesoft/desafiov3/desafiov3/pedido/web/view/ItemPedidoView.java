package br.com.bluesoft.desafiov3.desafiov3.pedido.web.view;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.ItemPedido;

public class ItemPedidoView {

    private Long id;
    private Long pedidoId;
    private String descricaoProduto;
    private Double quantidade;

    public ItemPedidoView(ItemPedido itemPedido) {
        this.id = itemPedido.getId();
        this.pedidoId = itemPedido.getPedidoId();
        this.quantidade = itemPedido.getQuantidade();
        this.descricaoProduto = itemPedido.getDescricaoProduto();
    }

    public ItemPedidoView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
