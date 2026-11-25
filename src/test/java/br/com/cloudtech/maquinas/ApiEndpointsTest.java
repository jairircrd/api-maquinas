
package br.com.cloudtech.maquinas;

import br.com.cloudtech.maquinas.model.Maquina;
import br.com.cloudtech.maquinas.repository.MaquinaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ApiEndpointsTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private MaquinaRepository maquinaRepository;

        @Test
        void deveRetornarPaginaInicial() throws Exception {
                mockMvc.perform(get("/"))
                                .andExpect(status().isOk())
                                .andExpect(content().string(
                                                "API de Monitoramento de Máquinas"));
        }

        @Test
        void deveRetornarStatusOnline() throws Exception {
                mockMvc.perform(get("/status"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value("online"))
                                .andExpect(jsonPath("$.mensagem")
                                                .value("Aplicação de monitoramento em execução"));
        }

        @Test
        void deveRetornarVersao() throws Exception {
                mockMvc.perform(get("/versao"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("1.0.0"));
        }

        @Test
        void deveListarMaquinas() throws Exception {
                mockMvc.perform(get("/maquinas"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());
        }

        @Test
        void deveCadastrarMaquina() throws Exception {
                mockMvc.perform(post("/maquinas/cadastro")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                    "nome": "Máquina CNC Teste",
                                                    "tipo": "CNC",
                                                    "setor": "Usinagem",
                                                    "status": "em operação"
                                                }
                                                """))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").isNumber())
                                .andExpect(jsonPath("$.nome")
                                                .value("Máquina CNC Teste"));
        }

        @Test
        void deveRetornar404ParaMaquinaInexistente() throws Exception {
                mockMvc.perform(get("/maquinas/999999999"))
                                .andExpect(status().isNotFound());
        }

        @Test
        void deveAtualizarMaquina() throws Exception {

                Maquina maquina = maquinaRepository.save(
                                new Maquina(
                                                "Máquina Antiga",
                                                "CNC",
                                                "Usinagem",
                                                "parada"));

                mockMvc.perform(put("/maquinas/" + maquina.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                    "nome": "Máquina Atualizada",
                                                    "tipo": "CNC",
                                                    "setor": "Produção",
                                                    "status": "em operação"
                                                }
                                                """))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nome")
                                                .value("Máquina Atualizada"));
        }

        @Test
        void deveExcluirMaquina() throws Exception {

                Maquina maquina = maquinaRepository.save(
                                new Maquina(
                                                "Máquina para Excluir",
                                                "CNC",
                                                "Usinagem",
                                                "parada"));

                mockMvc.perform(delete("/maquinas/" + maquina.getId()))
                                .andExpect(status().isNoContent());
        }

        @Test
        void deveListarProducoes() throws Exception {
                mockMvc.perform(get("/producao"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());
        }

        @Test
        void deveRegistrarProducao() throws Exception {

                Maquina maquina = maquinaRepository.save(
                                new Maquina(
                                                "Máquina CNC Produção",
                                                "CNC",
                                                "Usinagem",
                                                "em operação"));

                mockMvc.perform(post("/producao")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                        "maquinaId": %d,
                                                        "consumoKw": 12.5,
                                                        "horarioAcionamento": "08:30",
                                                        "pecasProduzidas": 150
                                                }
                                                """.formatted(maquina.getId())))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.consumoKw").value(12.5))
                                .andExpect(jsonPath("$.horarioAcionamento").value("08:30"))
                                .andExpect(jsonPath("$.pecasProduzidas").value(150))
                                .andExpect(jsonPath("$.maquina.id").value(maquina.getId()));
        }

        @Test
        void naoDeveExcluirMaquinaComProducaoVinculada() throws Exception {

                Maquina maquina = maquinaRepository.save(
                                new Maquina(
                                                "Máquina com Histórico",
                                                "CNC",
                                                "Usinagem",
                                                "em operação"));

                // Registra uma produção vinculada à máquina
                mockMvc.perform(post("/producao")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                    "maquinaId": %d,
                                                    "consumoKw": 12.5,
                                                    "horarioAcionamento": "08:30",
                                                    "pecasProduzidas": 150
                                                }
                                                """.formatted(maquina.getId())))
                                .andExpect(status().isCreated());

                // Tenta excluir a máquina que possui histórico
                mockMvc.perform(delete("/maquinas/" + maquina.getId()))
                                .andExpect(status().isConflict());
        }
}
