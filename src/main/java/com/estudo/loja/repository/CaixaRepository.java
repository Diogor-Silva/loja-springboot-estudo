package com.estudo.loja.repository;

import com.estudo.loja.entity.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {
}
