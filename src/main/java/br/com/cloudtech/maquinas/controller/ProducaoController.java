
package br.com.cloudtech.maquinas.controller;

import br.com.cloudtech.maquinas.dto.ProducaoRequest;
import br.com.cloudtech.maquinas.model.Producao;
import br.com.cloudtech.maquinas.service.ProducaoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producao")
public class ProducaoController {

    private final ProducaoService producaoService;

    public ProducaoController(ProducaoService producaoService) {
        this.producaoService = producaoService;
    }

    // Listar registros de produção
    @GetMapping
    public ResponseEntity<List<Producao>> listarTodas() {
        return ResponseEntity.ok(
                producaoService.listarTodas());
    }

    // Registrar dados de produção
    @PostMapping
    public ResponseEntity<Producao> registrar(
            @RequestBody ProducaoRequest dados) {

        Producao producao = producaoService.registrar(dados);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(producao);
    }
}
