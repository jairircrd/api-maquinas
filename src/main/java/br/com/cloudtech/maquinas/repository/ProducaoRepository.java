
package br.com.cloudtech.maquinas.repository;

import br.com.cloudtech.maquinas.model.Producao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducaoRepository extends JpaRepository<Producao, Long> {

    boolean existsByMaquina_Id(Long maquinaId);

}
