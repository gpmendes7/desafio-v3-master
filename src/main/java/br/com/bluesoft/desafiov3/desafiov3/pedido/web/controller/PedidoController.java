package br.com.bluesoft.desafiov3.desafiov3.pedido.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import br.com.bluesoft.desafiov3.desafiov3.pedido.business.PedidoService;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.Pedido;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.exception.EstoqueVazioException;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.form.PedidoFormulario;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.view.PedidoView;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/novo-pedido")
    public PedidoView novoPedido(@RequestBody PedidoFormulario pedidoFormulario) throws EstoqueVazioException {
        return new PedidoView(pedidoService.novoPedido(pedidoFormulario));
    }

    @GetMapping("/listar-todos-pedidos")
    public List<Pedido> listarPedidos() {
        return pedidoService.listarTodos();
    }
    
    @GetMapping("/listar-quantidade-pedidos-forma-pagamento")
    public ResponseEntity<?> listarQuantidadeDePedidosPorFormaDePagamento() {
        return ResponseEntity.ok(pedidoService.listarQuantidadeDePedidosPorFormaDePagamento());
    }

    @GetMapping("/obterPedidoPorId/{pedidoId}")
    public ResponseEntity<?> obterPedidoPorId(@PathVariable Long pedidoId) {
        final Pedido pedido = pedidoService.buscarPedido(pedidoId);

        if (pedido == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new PedidoView(pedido));
    }

    @DeleteMapping(value = "/{pedidoId}")
    public void deletarPedido(@PathVariable Long pedidoId) {
        pedidoService.deletarPedido(pedidoId);
    }

}
