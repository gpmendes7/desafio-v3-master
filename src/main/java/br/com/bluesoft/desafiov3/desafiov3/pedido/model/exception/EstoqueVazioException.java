package br.com.bluesoft.desafiov3.desafiov3.pedido.model.exception;

public class EstoqueVazioException extends Exception {

    public EstoqueVazioException() {
        super("Estoque está vazio!");
    }
}
