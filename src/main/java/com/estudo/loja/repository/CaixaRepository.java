package com.estudo.loja.repository;

import com.estudo.loja.entity.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {

    boolean existsByClienteId(Long clienteId);

    long countByDataVendaGreaterThanEqualAndDataVendaLessThan(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    @Query("""
            SELECT COALESCE(SUM(c.valorTotal), 0)
            FROM Caixa c
            WHERE c.dataVenda >= :inicio
              AND c.dataVenda < :fim
            """)
    BigDecimal somarValorTotalDoPeriodo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    @Query("""
            SELECT DISTINCT c
            FROM Caixa c
            LEFT JOIN FETCH c.cliente
            LEFT JOIN FETCH c.itens i
            LEFT JOIN FETCH i.produto
            ORDER BY c.dataVenda DESC
            """)
    List<Caixa> listarComDetalhes();
}