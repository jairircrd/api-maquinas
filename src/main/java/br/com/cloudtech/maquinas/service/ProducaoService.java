
package br.com.cloudtech.maquinas.service;

import br.com.cloudtech.maquinas.dto.ProducaoRequest;
import br.com.cloudtech.maquinas.model.Maquina;
import br.com.cloudtech.maquinas.model.Producao;
import br.com.cloudtech.maquinas.repository.ProducaoRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducaoService {

    private final ProducaoRepository producaoRepository;
    private final MaquinaService maquinaService;

    public ProducaoService(
            ProducaoRepository producaoRepository,
            MaquinaService maquinaService) {

        this.producaoRepository = producaoRepository;
        this.maquinaService = maquinaService;
    }

    // Registrar dados de produção
    public Producao registrar(ProducaoRequest dados) {

        Maquina maquina = maquinaService.buscarPorId(
                dados.getMaquinaId());

        Producao producao = new Producao(
                maquina,
                dados.getConsumoKw(),
                dados.getHorarioAcionamento(),
                dados.getPecasProduzidas());

        return producaoRepository.save(producao);
    }

    // Listar todos os registros de produção
    public List<Producao> listarTodas() {
        return producaoRepository.findAll();
    }
}
