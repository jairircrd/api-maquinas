
package br.com.cloudtech.maquinas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public class ProducaoRequest {

    private Long maquinaId;

    private Double consumoKw;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioAcionamento;

    private Integer pecasProduzidas;

    public ProducaoRequest() {
    }

    public Long getMaquinaId() {
        return maquinaId;
    }

    public void setMaquinaId(Long maquinaId) {
        this.maquinaId = maquinaId;
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
