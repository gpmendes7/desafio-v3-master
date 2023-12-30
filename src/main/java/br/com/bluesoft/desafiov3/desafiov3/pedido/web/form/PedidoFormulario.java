package br.com.bluesoft.desafiov3.desafiov3.pedido.web.form;

import java.util.List;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.FormaPagamento;

public class PedidoFormulario {

    private boolean retiradaNaLoja;
    private boolean simularFalha;
    private FormaPagamento formaPagamento;
    private List<ItemPedidoFormulario> itens;

    public boolean isRetiradaNaLoja() {
        return retiradaNaLoja;
    }

    public boolean isSimularFalha() {
        return simularFalha;
    }

    public void setSimularFalha(boolean simularFalha) {
        this.simularFalha = simularFalha;
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

    public List<ItemPedidoFormulario> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoFormulario> itens) {
        this.itens = itens;
    }
}
