package com.estudo.loja.entity;


import com.estudo.loja.enums.FormaPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private LocalDateTime dataVenda;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    private BigDecimal valorTotal;

    private BigDecimal valorPago;

    private BigDecimal troco;

    @OneToMany(
            mappedBy = "caixa",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemCaixa> itens = new ArrayList<>();
}