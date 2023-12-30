package br.com.bluesoft.desafiov3.desafiov3.pedido.web.view;

import java.util.List;
import java.util.stream.Collectors;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.FormaPagamento;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.Pedido;

public class PedidoView {

    private Long id;
    private boolean retiradaNaLoja;
    private FormaPagamento formaPagamento;
    public List<ItemPedidoView> itens;

    public PedidoView(Pedido pedido) {
        this.id = pedido.getId();
        this.retiradaNaLoja = pedido.isRetiradaNaLoja();
        this.formaPagamento = pedido.getFormaPagamento();
        this.itens = pedido
            .getItens()
            .stream()
            .map(ItemPedidoView::new)
            .collect(Collectors.toList());
    }

    public PedidoView() {
    }

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

    public List<ItemPedidoView> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoView> itens) {
        this.itens = itens;
    }
}
