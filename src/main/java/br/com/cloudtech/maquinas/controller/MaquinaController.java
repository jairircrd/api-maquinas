
package br.com.cloudtech.maquinas.controller;

import br.com.cloudtech.maquinas.model.Maquina;
import br.com.cloudtech.maquinas.service.MaquinaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maquinas")
public class MaquinaController {

    private final MaquinaService maquinaService;

    public MaquinaController(MaquinaService maquinaService) {
        this.maquinaService = maquinaService;
    }

    // Listar todas as máquinas
    @GetMapping
    public ResponseEntity<List<Maquina>> listarTodas() {
        return ResponseEntity.ok(maquinaService.listarTodas());
    }

    // Consultar uma máquina pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Maquina> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(maquinaService.buscarPorId(id));
    }

    // Cadastrar uma máquina
    @PostMapping("/cadastro")
    public ResponseEntity<Maquina> cadastrar(@RequestBody Maquina maquina) {
        Maquina novaMaquina = maquinaService.cadastrar(maquina);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novaMaquina);
    }

    // Atualizar uma máquina
    @PutMapping("/{id}")
    public ResponseEntity<Maquina> atualizar(
            @PathVariable Long id,
            @RequestBody Maquina maquina) {

        return ResponseEntity.ok(
                maquinaService.atualizar(id, maquina));
    }

    // Excluir uma máquina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        maquinaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
