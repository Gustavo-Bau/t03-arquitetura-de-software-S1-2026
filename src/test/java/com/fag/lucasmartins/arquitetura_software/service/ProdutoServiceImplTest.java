package com.fag.lucasmartins.arquitetura_software.service;

import com.fag.lucasmartins.arquitetura_software.dto.ProdutoRequestDTO;
import com.fag.lucasmartins.arquitetura_software.dto.ProdutoResponseDTO;
import com.fag.lucasmartins.arquitetura_software.entity.Produto;
import com.fag.lucasmartins.arquitetura_software.exception.ProdutoException;
import com.fag.lucasmartins.arquitetura_software.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarProduto_Success() {
        ProdutoRequestDTO request = new ProdutoRequestDTO();
        request.setNome("Produto Teste");
        request.setPreco(50.0);
        request.setEstoque(10);

        Produto produto = new Produto("Produto Teste", 50.0, 10);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponseDTO response = produtoService.cadastrarProduto(request);

        assertNotNull(response);
        assertEquals("Produto cadastrado com sucesso!", response.getMensagem());
        assertEquals("Produto Teste", response.getNome());
        assertEquals(50.0, response.getPreco());
        assertEquals(10, response.getEstoque());
        assertEquals(50.0, response.getPrecoFinal()); // Sem desconto
    }

    @Test
    void testCadastrarProduto_WithDiscount() {
        ProdutoRequestDTO request = new ProdutoRequestDTO();
        request.setNome("Produto Teste");
        request.setPreco(100.0);
        request.setEstoque(60);

        Produto produto = new Produto("Produto Teste", 100.0, 60);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponseDTO response = produtoService.cadastrarProduto(request);

        assertNotNull(response);
        assertEquals(90.0, response.getPrecoFinal()); // Com desconto de 10%
    }

    @Test
    void testCadastrarProduto_PremiumValidation() {
        ProdutoRequestDTO request = new ProdutoRequestDTO();
        request.setNome("Produto Premium");
        request.setPreco(50.0);
        request.setEstoque(10);

        assertThrows(ProdutoException.class, () -> produtoService.cadastrarProduto(request));
    }
}