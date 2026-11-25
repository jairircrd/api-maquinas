package br.com.cloudtech.maquinas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class SistemaController {

    // Página inicial
    @GetMapping("/")
    public String inicio() {
        return "API de Monitoramento de Máquinas";
    }

    // Verificar status da aplicação
    @GetMapping("/status")
    public Map<String, String> verificarStatus() {
        return Map.of(
                "status", "online",
                "mensagem", "Aplicação de monitoramento em execução");
    }

    // Consultar versão da aplicação
    @GetMapping("/versao")
    public String versao() {
        return "1.0.0";
    }
}