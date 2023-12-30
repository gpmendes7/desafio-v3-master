package br.com.bluesoft.desafiov3.desafiov3.pedido.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.FormaPagamento;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.ItemPedido;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.Pedido;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.exception.EstoqueVazioException;
import br.com.bluesoft.desafiov3.desafiov3.pedido.repository.PedidoRepository;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.form.ItemPedidoFormulario;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.form.PedidoFormulario;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final MovimentoEstoqueService movimentoEstoqueService;

    private List<ItemPedido> itens = new ArrayList<>();

    public PedidoService(PedidoRepository pedidoRepository, MovimentoEstoqueService movimentoEstoqueService) {
        this.pedidoRepository = pedidoRepository;
        this.movimentoEstoqueService = movimentoEstoqueService;
    }
    
    // da roll back no banco caso seja lancada uma EstoqueVazioException
    @Transactional(rollbackFor = EstoqueVazioException.class)
    public Pedido novoPedido(PedidoFormulario pedidoFormulario) throws EstoqueVazioException {
        Pedido pedido = new Pedido();
        pedido.setFormaPagamento(pedidoFormulario.getFormaPagamento());
        pedido.setRetiradaNaLoja(pedidoFormulario.isRetiradaNaLoja());

        for (ItemPedidoFormulario item : pedidoFormulario.getItens()) {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setDescricaoProduto(item.getDescricaoProduto());
            itemPedido.setQuantidade(item.getQuantidade());
            itemPedido.setPedido(pedido);
            itens.add(itemPedido);
            pedido.adicionarItem(itemPedido);
        }

        final Pedido pedidoCriado = pedidoRepository.salvarPedido(pedido);
        		 
        movimentoEstoqueService.movimentarEstoquePedido(pedidoCriado, pedidoFormulario.isSimularFalha());
      
        return pedidoCriado;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.listarTodos();
    }

    public Map<FormaPagamento, Long> listarQuantidadeDePedidosPorFormaDePagamento() {
        final List<Pedido> todosOsPedidos = pedidoRepository.listarTodos();
        return agruparPedidoPorFormaDePagamento(todosOsPedidos);
    }

    private Map<FormaPagamento, Long> agruparPedidoPorFormaDePagamento(List<Pedido> todosOsPedidos) {
    	Map<FormaPagamento, Long> quantidadePedidosPorFormaPagamento = new HashMap<>();
    	
    	for(Pedido pedido: todosOsPedidos) {
    		Long quantidadePedidoPorFormaPagamento = quantidadePedidosPorFormaPagamento.get(pedido.getFormaPagamento());
    		if(quantidadePedidoPorFormaPagamento == null) {
    			quantidadePedidosPorFormaPagamento.put(pedido.getFormaPagamento(), 1L);
    		} else {
    			quantidadePedidosPorFormaPagamento.put(pedido.getFormaPagamento(), quantidadePedidoPorFormaPagamento + 1);
    		}
    		
    	}
    	
        return quantidadePedidosPorFormaPagamento;
    }

    public Pedido buscarPedido(Long pedidoId) {
        return pedidoRepository.buscarPedido(pedidoId);
    }

    public void deletarPedido(Long pedidoId) {
        final Pedido pedido = pedidoRepository.buscarPedido(pedidoId);
        pedidoRepository.deletarPedido(pedido);
    }
}
