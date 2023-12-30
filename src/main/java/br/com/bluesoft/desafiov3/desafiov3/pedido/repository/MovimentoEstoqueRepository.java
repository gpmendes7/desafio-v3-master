package br.com.bluesoft.desafiov3.desafiov3.pedido.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.MovimentoEstoque;

@Repository
public class MovimentoEstoqueRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public MovimentoEstoque salvarMovimentoEstoque(MovimentoEstoque movimentoEstoque) {
        entityManager.persist(movimentoEstoque);
        return movimentoEstoque;
    }

}
