package com.estudo.loja.service;

import com.estudo.loja.dto.CaixaRequest;
import com.estudo.loja.dto.ItemCaixaRequest;
import com.estudo.loja.entity.Caixa;
import com.estudo.loja.entity.Cliente;
import com.estudo.loja.entity.ItemCaixa;
import com.estudo.loja.entity.Produto;
import com.estudo.loja.enums.FormaPagamento;
import com.estudo.loja.repository.CaixaRepository;
import com.estudo.loja.repository.ClienteRepository;
import com.estudo.loja.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CaixaService {

    private final CaixaRepository caixaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public CaixaService(
            CaixaRepository caixaRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository
    ) {
        this.caixaRepository = caixaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Caixa finalizarVenda(CaixaRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente com id " + request.getClienteId() + " não encontrado"
                ));

        if (request.getItens() == null || request.getItens().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A venda deve possuir pelo menos um produto"
            );
        }

        Caixa caixa = new Caixa();
        caixa.setCliente(cliente);
        caixa.setDataVenda(LocalDateTime.now());
        caixa.setFormaPagamento(request.getFormaPagamento());

        List<ItemCaixa> itens = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemCaixaRequest itemRequest : request.getItens()) {
            Produto produto = produtoRepository.findById(itemRequest.getProdutoId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Produto com id " + itemRequest.getProdutoId() + " não encontrado"
                    ));

            if (itemRequest.getQuantidade() == null || itemRequest.getQuantidade() <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "A quantidade do produto deve ser maior que zero"
                );
            }

            if (produto.getQuantidadeEstoque() < itemRequest.getQuantidade()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Estoque insuficiente para o produto " + produto.getNome()
                );
            }

            BigDecimal subtotal = produto.getPreco()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantidade()));

            ItemCaixa item = new ItemCaixa();
            item.setCaixa(caixa);
            item.setProduto(produto);
            item.setQuantidade(itemRequest.getQuantidade());
            item.setValorUnitario(produto.getPreco());
            item.setSubtotal(subtotal);

            itens.add(item);
            valorTotal = valorTotal.add(subtotal);

            produto.setQuantidadeEstoque(
                    produto.getQuantidadeEstoque() - itemRequest.getQuantidade()
            );
        }

        caixa.setItens(itens);
        caixa.setValorTotal(valorTotal);

        calcularPagamento(caixa, request);

        return caixaRepository.save(caixa);
    }

    private void calcularPagamento(Caixa caixa, CaixaRequest request) {
        if (request.getFormaPagamento() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Forma de pagamento é obrigatória"
            );
        }

        if (request.getFormaPagamento() == FormaPagamento.DINHEIRO) {
            if (request.getValorPago() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Valor pago é obrigatório para pagamento em dinheiro"
                );
            }

            if (request.getValorPago().compareTo(caixa.getValorTotal()) < 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Valor pago é insuficiente"
                );
            }

            caixa.setValorPago(request.getValorPago());
            caixa.setTroco(request.getValorPago().subtract(caixa.getValorTotal()));
        } else {
            caixa.setValorPago(caixa.getValorTotal());
            caixa.setTroco(BigDecimal.ZERO);
        }
    }

    public List<Caixa> listar() {
        return caixaRepository.findAll();
    }
}
