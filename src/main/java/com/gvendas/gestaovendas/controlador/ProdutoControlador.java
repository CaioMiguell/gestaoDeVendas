package com.gvendas.gestaovendas.controlador;

import com.gvendas.gestaovendas.dto.produto.ProdutoRequestDTO;
import com.gvendas.gestaovendas.dto.produto.ProdutoResponseDTO;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.servico.ProdutoServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Produto")
@RequestMapping("/caregoria{codigoCategoria}/produto")
public class ProdutoControlador {

    @Autowired
    private ProdutoServico produtoServico;

    @ApiOperation(value = "Listar", nickname = "listarTodas")
    @GetMapping
    public List<ProdutoResponseDTO> listarTodas(@PathVariable Long codigoCategoria) {
        return produtoServico.listarTodos(codigoCategoria).stream()
                .map(produto -> ProdutoResponseDTO.converterParaProdutoDTO(produto))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Listar por codigo", nickname = "buscarPorCodigo")
    @GetMapping("/{codigo}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorCodigo(@PathVariable Long codigoCategoria,
                                                              @PathVariable Long codigo) {
        Optional<Produto> produto = produtoServico.buscarPorCodigo(codigo, codigoCategoria);
        return produto.isPresent() ? ResponseEntity
                .ok(ProdutoResponseDTO.converterParaProdutoDTO(produto.get()))
                : ResponseEntity.notFound().build();

    }

    @ApiOperation(value = "Salvar", nickname = "salvarProduto")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvar(@PathVariable Long codigoCategoria,
                                                     @Valid @RequestBody ProdutoRequestDTO produto) {
        Produto produtoSalvo = produtoServico.salvar(codigoCategoria,
                produto.converterParaEntidade(codigoCategoria));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoResponseDTO.converterParaProdutoDTO(produtoSalvo));
    }

    @ApiOperation(value = "Autalizar", nickname = "autalizarProduto")
    @PutMapping("/{codigoProduto}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long codigoCategoria,
                                             @PathVariable Long codigoProduto,
                                             @Valid @RequestBody ProdutoRequestDTO produto) {
        Produto ProdutoAtualizar = produtoServico.atualizar(codigoProduto, codigoCategoria,
                produto.converterParaEntidade(codigoCategoria, codigoProduto));

        return ResponseEntity.ok(ProdutoResponseDTO.converterParaProdutoDTO(ProdutoAtualizar));
    }

    @ApiOperation(value = "Deletar", nickname = "deletarProduto")
    @DeleteMapping("/{codigoProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long codigoCategoria, @PathVariable Long codigoProduto) {
        produtoServico.deletar(codigoCategoria, codigoProduto);
    }

}
