
package br.com.cloudtech.maquinas.service;

import br.com.cloudtech.maquinas.model.Maquina;
import br.com.cloudtech.maquinas.repository.MaquinaRepository;
import br.com.cloudtech.maquinas.repository.ProducaoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MaquinaService {

    private final MaquinaRepository maquinaRepository;
    private final ProducaoRepository producaoRepository;

    public MaquinaService(
            MaquinaRepository maquinaRepository,
            ProducaoRepository producaoRepository) {

        this.maquinaRepository = maquinaRepository;
        this.producaoRepository = producaoRepository;
    }

    // Cadastrar máquina
    public Maquina cadastrar(Maquina maquina) {
        return maquinaRepository.save(maquina);
    }

    // Listar todas as máquinas
    public List<Maquina> listarTodas() {
        return maquinaRepository.findAll();
    }

    // Buscar máquina pelo ID
    public Maquina buscarPorId(Long id) {
        return maquinaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Máquina não encontrada."));
    }

    // Atualizar máquina
    public Maquina atualizar(Long id, Maquina maquinaAtualizada) {
        Maquina maquina = buscarPorId(id);

        maquina.setNome(maquinaAtualizada.getNome());
        maquina.setTipo(maquinaAtualizada.getTipo());
        maquina.setSetor(maquinaAtualizada.getSetor());
        maquina.setStatus(maquinaAtualizada.getStatus());

        return maquinaRepository.save(maquina);
    }

    // Excluir máquina
    public void excluir(Long id) {
        Maquina maquina = buscarPorId(id);

        boolean possuiProducao = producaoRepository.existsByMaquina_Id(id);

        if (possuiProducao) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir uma máquina com registros de produção.");
        }

        maquinaRepository.delete(maquina);
    }
}
