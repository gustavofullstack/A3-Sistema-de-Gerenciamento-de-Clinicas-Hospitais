# 2. Estrutura de Arquivos e Pastas

A seguir, apresentamos um mapa detalhado da organização interna do projeto, explicando a função de cada arquivo e diretório. O projeto foi desenvolvido em Java, seguindo as melhores práticas de organização e estruturação de projetos Java.

## Estrutura Geral do Projeto

```plaintext
hospital-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── hospital/
│   │   │           ├── Main.java
│   │   │           ├── model/
│   │   │           │   ├── Paciente.java
│   │   │           │   ├── Medico.java
│   │   │           │   ├── Consulta.java
│   │   │           │   ├── Especializacao.java
│   │   │           │   ├── Prescricao.java
│   │   │           │   ├── Medicamento.java
│   │   │           │   ├── Estoque.java
│   │   │           │   └── Faturamento.java
│   │   │           ├── dao/
│   │   │           │   ├── PacienteDAO.java
│   │   │           │   ├── MedicoDAO.java
│   │   │           │   ├── ConsultaDAO.java
│   │   │           │   ├── PrescricaoDAO.java
│   │   │           │   ├── MedicamentoDAO.java
│   │   │           │   ├── EstoqueDAO.java
│   │   │           │   └── FaturamentoDAO.java
│   │   │           ├── service/
│   │   │           │   ├── PacienteService.java
│   │   │           │   ├── MedicoService.java
│   │   │           │   ├── ConsultaService.java
│   │   │           │   ├── PrescricaoService.java
│   │   │           │   ├── EstoqueService.java
│   │   │           │   └── FaturamentoService.java
│   │   │           ├── controller/
│   │   │           │   ├── PacienteController.java
│   │   │           │   ├── MedicoController.java
│   │   │           │   ├── ConsultaController.java
│   │   │           │   ├── PrescricaoController.java
│   │   │           │   ├── EstoqueController.java
│   │   │           │   └── FaturamentoController.java
│   │   │           ├── util/
│   │   │           │   ├── DatabaseUtil.java
│   │   │           │   ├── DateUtil.java
│   │   │           │   └── ValidationUtil.java
│   │   │           └── exception/
│   │   │               ├── DataAccessException.java
│   │   │               └── BusinessException.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── log4j2.xml
│   └── test/
│       └── java/
│           └── com/
│               └── hospital/
│                   ├── model/
│                   │   └── PacienteTest.java
│                   ├── service/
│                   │   └── PacienteServiceTest.java
│                   └── dao/
│                       └── PacienteDAOTest.java
├── lib/
│   └── (bibliotecas externas)
├── sql/
│   ├── schema.sql
│   └── data.sql
├── docs/
│   ├── readme.md
│   ├── diagrama_classes.png
│   └── manual_usuario.pdf
├── pom.xml
├── .gitignore
└── README.md
```

## Descrição Detalhada dos Diretórios e Arquivos

### Diretório `hospital-management-system/`

- **Descrição**: Diretório raiz do projeto que contém todos os subdiretórios e arquivos relacionados ao sistema de gerenciamento hospitalar.

### Diretório `src/`

- **Descrição**: Contém todo o código-fonte do projeto, incluindo código principal e testes.

#### Diretório `src/main/`

- **Descrição**: Contém o código-fonte principal da aplicação.

##### Diretório `src/main/java/`

- **Descrição**: Contém todos os arquivos Java do projeto, organizados por pacote.

###### Diretório `com/hospital/`

- **Descrição**: Pacote base do projeto.

####### Arquivo `Main.java`

- **Descrição**: Classe principal que inicializa a aplicação. Contém o método `main` que inicia o sistema.

###### Diretório `model/`

- **Descrição**: Contém as classes que representam as entidades do sistema (Modelo de Dados).

- **`Paciente.java`**: Classe que representa um paciente, incluindo atributos como nome, data de nascimento, gênero, número de identificação, endereço, informações de contato, histórico médico, alergias e medicamentos em uso.

- **`Medico.java`**: Classe que representa um médico, com atributos como nome, especialização, número de registro, endereço, informações de contato e disponibilidade.

- **`Consulta.java`**: Classe que representa uma consulta médica, incluindo data, hora, paciente, médico responsável, motivo da consulta e observações médicas.

- **`Especializacao.java`**: Classe que representa uma especialização médica, permitindo categorizar os médicos por suas áreas de especialização.

- **`Prescricao.java`**: Classe que representa uma prescrição médica, incluindo medicamentos prescritos, dosagens e instruções de uso.

- **`Medicamento.java`**: Classe que representa um medicamento disponível no estoque, com informações como nome, fabricante, data de validade e quantidade em estoque.

- **`Estoque.java`**: Classe que gerencia o estoque de medicamentos e materiais médicos, incluindo métodos para monitorar o consumo e gerar alertas de reabastecimento.

- **`Faturamento.java`**: Classe que representa o faturamento dos procedimentos médicos, incluindo informações sobre procedimentos realizados, valores, pagamentos recebidos e contas a pagar e receber.

###### Diretório `dao/`

- **Descrição**: Contém as classes de Acesso a Dados (Data Access Objects), responsáveis por interagir com o banco de dados.

- **`PacienteDAO.java`**: Classe responsável por operações CRUD (Create, Read, Update, Delete) relacionadas aos pacientes.

- **`MedicoDAO.java`**: Classe responsável por operações CRUD relacionadas aos médicos.

- **`ConsultaDAO.java`**: Classe responsável por operações CRUD relacionadas às consultas.

- **`PrescricaoDAO.java`**: Classe responsável por operações CRUD relacionadas às prescrições médicas.

- **`MedicamentoDAO.java`**: Classe responsável por operações CRUD relacionadas aos medicamentos.

- **`EstoqueDAO.java`**: Classe responsável por operações CRUD relacionadas ao estoque de medicamentos.

- **`FaturamentoDAO.java`**: Classe responsável por operações CRUD relacionadas ao faturamento e pagamentos.

###### Diretório `service/`

- **Descrição**: Contém as classes de Serviço que implementam a lógica de negócio do sistema.

- **`PacienteService.java`**: Implementa a lógica de negócio relacionada aos pacientes, como validação de dados e regras específicas.

- **`MedicoService.java`**: Implementa a lógica de negócio relacionada aos médicos, incluindo disponibilidade e especializações.

- **`ConsultaService.java`**: Gerencia a lógica de agendamento de consultas, evitando conflitos de horário e verificando a disponibilidade dos médicos.

- **`PrescricaoService.java`**: Implementa a lógica de emissão de prescrições, garantindo que as informações estejam corretas e completas.

- **`EstoqueService.java`**: Gerencia o estoque de medicamentos, monitorando níveis e gerando alertas de reabastecimento.

- **`FaturamentoService.java`**: Implementa a lógica relacionada ao faturamento, como cálculo de valores e registro de pagamentos.

###### Diretório `controller/`

- **Descrição**: Contém as classes Controladoras que interagem com a interface do usuário ou API, recebendo entradas e retornando respostas.

- **`PacienteController.java`**: Controla as operações relacionadas aos pacientes, como cadastro, atualização e consulta de informações.

- **`MedicoController.java`**: Controla as operações relacionadas aos médicos, incluindo gestão de disponibilidade e especializações.

- **`ConsultaController.java`**: Gerencia as operações de agendamento, visualização e cancelamento de consultas.

- **`PrescricaoController.java`**: Controla a emissão e o histórico de prescrições médicas.

- **`EstoqueController.java`**: Gerencia as operações relacionadas ao estoque, como entrada e saída de medicamentos.

- **`FaturamentoController.java`**: Controla as operações de faturamento e pagamentos, incluindo geração de faturas e recibos.

###### Diretório `util/`

- **Descrição**: Contém classes utilitárias e auxiliares que suportam o funcionamento do sistema.

- **`DatabaseUtil.java`**: Fornece métodos para conexão e interação com o banco de dados, como abrir e fechar conexões.

- **`DateUtil.java`**: Fornece métodos para manipulação e formatação de datas e horários.

- **`ValidationUtil.java`**: Contém métodos para validação de dados de entrada, garantindo integridade e consistência.

###### Diretório `exception/`

- **Descrição**: Contém classes de exceção personalizadas para tratamento de erros específicos.

- **`DataAccessException.java`**: Exceção lançada em caso de erros de acesso ao banco de dados.

- **`BusinessException.java`**: Exceção lançada em caso de violação de regras de negócio.

##### Diretório `src/main/resources/`

- **Descrição**: Contém recursos não compilados, como arquivos de configuração e propriedades.

- **`application.properties`**: Arquivo de configuração da aplicação, incluindo parâmetros como URL do banco de dados, credenciais, configurações de log, etc.

- **`log4j2.xml`**: Arquivo de configuração do framework de logging Log4j2, definindo níveis de log e formatos de saída.

#### Diretório `src/test/`

- **Descrição**: Contém o código de teste da aplicação, incluindo testes unitários e de integração.

##### Diretório `src/test/java/`

- **Descrição**: Contém as classes de teste Java, organizadas de forma semelhante ao código-fonte principal.

###### Diretório `com/hospital/`

- **Descrição**: Pacote base para testes.

####### Diretório `model/`

- **`PacienteTest.java`**: Classe que contém testes unitários para a classe `Paciente.java`, verificando a correta criação e manipulação de objetos paciente.

####### Diretório `service/`

- **`PacienteServiceTest.java`**: Classe que testa os métodos de negócio da classe `PacienteService.java`.

####### Diretório `dao/`

- **`PacienteDAOTest.java`**: Classe que testa as operações de acesso a dados relacionadas aos pacientes.

### Diretório `lib/`

- **Descrição**: Contém bibliotecas externas necessárias para o funcionamento do projeto que não são gerenciadas por um sistema de build como Maven ou Gradle.

- **Exemplos de arquivos**: JDBC drivers, bibliotecas de logging, frameworks de teste.

### Diretório `sql/`

- **Descrição**: Contém scripts SQL utilizados para criação e inicialização do banco de dados.

- **`schema.sql`**: Script que define a estrutura do banco de dados, incluindo criação de tabelas, índices e restrições.

- **`data.sql`**: Script que insere dados iniciais no banco de dados, como especializações médicas padrão.

### Diretório `docs/`

- **Descrição**: Contém a documentação do projeto.

- **`readme.md`**: Documentação adicional detalhando aspectos específicos do projeto.

- **`diagrama_classes.png`**: Diagrama UML das classes do sistema, mostrando as relações entre elas.

- **`manual_usuario.pdf`**: Manual do usuário final, explicando como utilizar o sistema.

### Arquivo `pom.xml`

- **Descrição**: Arquivo de configuração do Maven, especificando as dependências do projeto, plugins e configurações de build.

- **Detalhes**:
  - **Dependências**: Especifica bibliotecas externas necessárias, como JDBC, Log4j2, JUnit.
  - **Plugins**: Configura plugins para compilação, testes e empacotamento da aplicação.

### Arquivo `.gitignore`

- **Descrição**: Arquivo que especifica quais arquivos e diretórios devem ser ignorados pelo sistema de controle de versão Git.

- **Conteúdo Comum**:
  - Diretórios de compilação (`/target`, `/build`).
  - Arquivos de configuração da IDE (`.idea/`, `*.iml`).
  - Arquivos de log (`*.log`).
  - Credenciais ou configurações sensíveis.

### Arquivo `README.md`

- **Descrição**: Arquivo principal de documentação do projeto, fornecendo uma visão geral, instruções de instalação, configuração e uso da aplicação.

- **Conteúdo**:
  - **Introdução**: Breve descrição do projeto.
  - **Pré-requisitos**: Softwares e dependências necessárias.
  - **Instalação**: Passos para configurar o ambiente e instalar a aplicação.
  - **Execução**: Instruções para executar o sistema.
  - **Contribuição**: Guia para contribuir com o projeto.
  - **Licença**: Informações sobre a licença do projeto.

---

Essa estrutura detalhada garante uma organização clara e modular do projeto, facilitando o desenvolvimento, manutenção e escalabilidade do sistema de gerenciamento de clínicas ou hospitais. Cada arquivo e diretório tem uma função específica, contribuindo para a robustez e eficiência da aplicação.
