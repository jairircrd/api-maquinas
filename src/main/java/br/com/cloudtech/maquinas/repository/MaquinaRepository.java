package br.com.cloudtech.maquinas.repository;

import br.com.cloudtech.maquinas.model.Maquina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaquinaRepository extends JpaRepository<Maquina, Long> {
    
}