
package br.com.cloudtech.maquinas.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

@Entity
@Table(name = "producoes")
public class Producao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "maquina_id", nullable = false)
    private Maquina maquina;

    @Column(nullable = false)
    private Double consumoKw;

    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime horarioAcionamento;

    @Column(nullable = false)
    private Integer pecasProduzidas;

    // Construtor vazio utilizado pelo JPA
    public Producao() {
    }

    // Construtor para registrar uma produção
    public Producao(Maquina maquina, Double consumoKw,
            LocalTime horarioAcionamento,
            Integer pecasProduzidas) {
        this.maquina = maquina;
        this.consumoKw = consumoKw;
        this.horarioAcionamento = horarioAcionamento;
        this.pecasProduzidas = pecasProduzidas;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public Double getConsumoKw() {
        return consumoKw;
    }

    public void setConsumoKw(Double consumoKw) {
        this.consumoKw = consumoKw;
    }

    public LocalTime getHorarioAcionamento() {
        return horarioAcionamento;
    }

    public void setHorarioAcionamento(LocalTime horarioAcionamento) {
        this.horarioAcionamento = horarioAcionamento;
    }

    public Integer getPecasProduzidas() {
        return pecasProduzidas;
    }

    public void setPecasProduzidas(Integer pecasProduzidas) {
        this.pecasProduzidas = pecasProduzidas;
    }
}
