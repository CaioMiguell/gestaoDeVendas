package com.gvendas.gestaovendas.controlador;

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

@RestController
@Api(tags = "Produto")
@RequestMapping("/caregoria{codigoCategoria}/produto")
public class ProdutoControlador {

    @Autowired
    private ProdutoServico produtoServico;

    @ApiOperation(value = "Listar", nickname = "listarTodas")
    @GetMapping
    public List<Produto> listarTodas(@PathVariable Long codigoCategoria) {
        return produtoServico.listarTodos(codigoCategoria);
    }

    @ApiOperation(value = "Listar por codigo", nickname = "buscarPorCodigo")
    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Produto>> buscarPorCodigo(@PathVariable Long codigoCategoria,
                                                             @PathVariable Long codigo) {
        Optional<Produto> produto = produtoServico.buscarPorCodigo(codigo, codigoCategoria);
        return produto.isPresent() ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();

    }

    @ApiOperation(value = "Salvar", nickname = "salvarProduto")
    @PostMapping
    public ResponseEntity<Produto> salvar(@PathVariable Long codigoCategoria,
                                          @Valid @RequestBody Produto produto) {
        Produto produtoSalvo = produtoServico.salvar(codigoCategoria, produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @ApiOperation(value = "Autalizar", nickname = "autalizarProduto")
    @PutMapping("/{codigoProduto}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long codigoCategoria,
                                             @PathVariable Long codigoProduto,
                                             @Valid @RequestBody Produto produto) {
        return ResponseEntity.ok(produtoServico.atualizar(codigoProduto, codigoCategoria, produto));
    }

    @ApiOperation(value = "Deletar", nickname = "deletarProduto")
    @DeleteMapping("/{codigoProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long codigoCategoria,@PathVariable Long codigoProduto) {
        produtoServico.deletar(codigoCategoria,codigoProduto);
    }

}
