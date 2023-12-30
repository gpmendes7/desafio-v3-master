package br.com.bluesoft.desafiov3.desafiov3.pedido.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.ItemPedido;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.Pedido;

@Repository
public class PedidoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Pedido salvarPedido(Pedido pedido) {
        entityManager.persist(pedido);
        return pedido;
    }

    public List<Pedido> listarTodos() {
    	// uso de join fetch para resolver problema das n+1 consultas       
        String sql = "select distinct p from Pedido p join fetch p.itens";
    	  	    		    
        final Query query = entityManager.createQuery(sql);
        return query.getResultList();
    }

    public Pedido buscarPedido(Long pedidoId) {
        String sql = " from Pedido p where p.id = :pedidoId ";

        final Query query = entityManager.createQuery(sql);

        query.setParameter("pedidoId", pedidoId);
        try {
            return (Pedido) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Transactional
    public void deletarPedido(Pedido pedido) {
    	// remove os itens de pedido associado ao pedido
    	for(ItemPedido item : pedido.getItens()) {
    		entityManager.remove(item);
    	}
        entityManager.remove(pedido);
    }

}
