# CloudTech — API de Monitoramento de Máquinas

Avaliação Somativa de Backend, DevOps e Computação em Nuvem — SENAI 2026.

API REST Java 21, Spring Boot, Maven, Spring Data JPA e PostgreSQL 16. Projeto separado da API de Biblioteca.

## Executar localmente
```powershell
docker compose up -d
.\mvnw.cmd clean verify
.\mvnw.cmd spring-boot:run
```
API: http://localhost:8080. PostgreSQL local: porta 5434. As credenciais locais do Compose são apenas para desenvolvimento. Em hospedagem, usar variáveis DB_URL (JDBC com TLS), DB_USER, DB_PASSWORD e PORT. Não publicar senhas reais.

## Endpoints

| Método | Rota | Resultado esperado |
|---|---|---|
| GET | / | 200 |
| GET | /status | 200 |
| GET | /versao | 200 |
| GET | /maquinas | 200 |
| GET | /maquinas/{id} | 200 ou 404 |
| POST | /maquinas/cadastro | 201 |
| PUT | /maquinas/{id} | 200 ou 404 |
| DELETE | /maquinas/{id} | 204, 404 ou 409 se houver produção vinculada |
| GET | /producao | 200 |
| POST | /producao | 201 |

Cadastro de máquina:
```json
{"nome":"CNC Demo","tipo":"CNC","setor":"Usinagem","status":"em operação"}
```

Registro de produção (substituir maquinaId pelo ID retornado no cadastro):
```json
{"maquinaId":1,"consumoKw":12.5,"horarioAcionamento":"08:30","pecasProduzidas":150}
```

## CI/CD
GitHub Actions executa Maven verify com PostgreSQL isolado, publica os resultados JUnit, constrói a imagem Docker e valida as rotas da aplicação empacotada. A publicação Render é configurada separadamente pelo Blueprint render.yaml. Nenhuma implantação é considerada concluída antes de verificar o endereço público.

## Segurança e uso
API acadêmica de demonstração, sem autenticação. Não inserir dados pessoais, industriais reais ou confidenciais no ambiente público. O banco de demonstração deve ser separado de outros projetos. Não compartilhar credenciais.
