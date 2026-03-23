package com.fag.lucasmartins.arquitetura_software.service;

import com.fag.lucasmartins.arquitetura_software.dto.ProdutoRequestDTO;
import com.fag.lucasmartins.arquitetura_software.dto.ProdutoResponseDTO;
import com.fag.lucasmartins.arquitetura_software.entity.Produto;
import com.fag.lucasmartins.arquitetura_software.exception.ProdutoException;
import com.fag.lucasmartins.arquitetura_software.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO request) {
        try {
            Produto produto = new Produto(request.getNome(), request.getPreco(), request.getEstoque());
            produtoRepository.save(produto);

            ProdutoResponseDTO response = new ProdutoResponseDTO();
            response.setMensagem("Produto cadastrado com sucesso!");
            response.setNome(produto.getNome());
            response.setEstoque(produto.getEstoque());
            response.setPreco(produto.getPreco());
            response.setPrecoFinal(produto.getPrecoFinal());

            return response;
        } catch (ProdutoException e) {
            throw e; // Re-throw to be handled by controller
        }
    }
}