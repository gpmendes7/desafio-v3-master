package br.com.bluesoft.desafiov3.desafiov3.pedido.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.FormaPagamento;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.form.ItemPedidoFormulario;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.form.PedidoFormulario;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.view.ItemPedidoView;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.view.PedidoView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void novoPedido() throws Exception {
        //cria o formulário que será enviado via json
        URI uri = new URI("/pedidos/novo-pedido");
        PedidoFormulario form = criarFormularioComUmItem();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(form);

        //faz a requisição e já garante 200 como resposta.
        final ResultActions resultActions = mockMvc
            .perform(MockMvcRequestBuilders
                         .post(uri)
                         .content(json)
                         .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));

        //transforma a resposta em objeto para verificar se o mesmo foi criado corretamente
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        PedidoView pedidoCriado = objectMapper.readValue(contentAsString, PedidoView.class);

        assertEquals(form.getFormaPagamento(), pedidoCriado.getFormaPagamento());
        assertTrue(pedidoCriado.isRetiradaNaLoja());

        final List<ItemPedidoView> itensCriados = pedidoCriado.getItens();
        assertEquals(1, itensCriados.size());

        final ItemPedidoView itemPedido = itensCriados.get(0);
        assertEquals(10, itemPedido.getQuantidade());

        //garantindo que o item pertence ao pedido
        assertEquals(pedidoCriado.getId(), itemPedido.getPedidoId());
    }

    @Test
    public void listarTodosOsPedidos() throws Exception {
        URI uri = new URI("/pedidos/listar-todos-pedidos");

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));
    }
    
    @Test
    public void listarQuantidadeDePedidosPorFormaDePagamento() throws Exception {
        URI uri = new URI("/pedidos/listar-quantidade-pedidos-forma-pagamento");

        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));
        
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        
        contentAsString = contentAsString.substring(1, contentAsString.length()-1);           
        String[] valores = contentAsString.split(",");              
        Map<String,Long> quantidadePedidosPorFormaPagamento = new HashMap<>();               

        for(String par : valores)                        
        {
            String[] entrada = par.split(":"); 
            String chave = entrada[0].trim().substring(1, entrada[0].trim().length()-1);         
            quantidadePedidosPorFormaPagamento.put(chave, Long.parseLong(entrada[1].trim()));         
        }
         
        assertEquals(3L, quantidadePedidosPorFormaPagamento.get("CREDITO"));
        assertEquals(2L, quantidadePedidosPorFormaPagamento.get("DEBITO"));
        assertEquals(2L, quantidadePedidosPorFormaPagamento.get("CHEQUE"));
    }

    @Test
    public void deletarPedido() throws Exception {
        //vamos deletar o pedido de id 1
        URI uri = new URI("/pedidos/1");

        //faz a requisição e já garante 200 como resposta.
        mockMvc
            .perform(MockMvcRequestBuilders
                         .delete(uri))
            .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));

        //agora iremos buscar o pedido pelo id e não tem que vir nada.
        URI uriBuscaPedido = new URI("/pedidos/obterPedidoPorId/1");

        mockMvc
            .perform(MockMvcRequestBuilders
                         .get(uriBuscaPedido))
            .andExpect(MockMvcResultMatchers.status().is(Response.SC_NO_CONTENT));
    }

    private PedidoFormulario criarFormularioComUmItem() {
        PedidoFormulario form = new PedidoFormulario();
        form.setRetiradaNaLoja(true);
        form.setFormaPagamento(FormaPagamento.CREDITO);
        form.setSimularFalha(false);

        ItemPedidoFormulario item = new ItemPedidoFormulario();
        item.setQuantidade(10);
        item.setDescricaoProduto("Coca-Cola");

        List<ItemPedidoFormulario> itemFormulario = new ArrayList<>();
        itemFormulario.add(item);

        form.setItens(itemFormulario);
        return form;
    }

}