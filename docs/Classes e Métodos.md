# 4. Classes e Métodos

## Visão Geral

O sistema de gerenciamento de clínicas e hospitais foi desenvolvido em Java, seguindo uma arquitetura em camadas que separa claramente as responsabilidades de cada componente. As principais camadas são:

- **Model (Modelo)**: Classes que representam as entidades do sistema.
- **DAO (Data Access Object)**: Classes responsáveis pela interação com o banco de dados.
- **Service (Serviço)**: Implementa a lógica de negócio do sistema.
- **Controller (Controlador)**: Lida com a comunicação entre a interface do usuário (ou API) e a camada de serviço.
- **Util (Utilitários)**: Fornece classes auxiliares e funções utilitárias.
- **Exception (Exceções)**: Define exceções personalizadas para tratamento de erros.

A seguir, apresentamos uma descrição detalhada das principais classes e métodos de cada camada, incluindo suas responsabilidades e interações.

---

## 4.1 Camada Model (Modelo)

As classes da camada Model representam as entidades fundamentais do sistema. Elas contêm atributos que refletem os dados armazenados e métodos básicos para acesso e manipulação desses dados.

### 4.1.1 Classe `Paciente`

**Responsabilidade**: Representa um paciente no sistema, armazenando informações pessoais e médicas.

**Principais Atributos**:

- `int id`: Identificador único do paciente.
- `String nome`: Nome completo.
- `Date dataNascimento`: Data de nascimento.
- `String genero`: Gênero do paciente.
- `String cpf`: Número de identificação (CPF).
- `String endereco`: Endereço residencial.
- `String telefone`: Número de telefone.
- `String email`: Endereço de e-mail.
- `String historicoMedico`: Histórico médico detalhado.
- `List<String> alergias`: Lista de alergias conhecidas.
- `List<String> medicamentosEmUso`: Lista de medicamentos que o paciente está utilizando.

**Principais Métodos**:

- `getters` e `setters` para cada atributo.
- `String getIdade()`: Calcula e retorna a idade atual do paciente.
- `void adicionarAlergia(String alergia)`: Adiciona uma alergia à lista.
- `void removerAlergia(String alergia)`: Remove uma alergia da lista.
- `void adicionarMedicamento(String medicamento)`: Adiciona um medicamento à lista.
- `void removerMedicamento(String medicamento)`: Remove um medicamento da lista.

**Interações**:

- Interage com `Consulta` para vincular consultas ao paciente.
- Utilizada por `PacienteDAO` para operações de persistência.

---

### 4.1.2 Classe `Medico`

**Responsabilidade**: Representa um médico, armazenando informações pessoais, profissionais e disponibilidade.

**Principais Atributos**:

- `int id`: Identificador único do médico.
- `String nome`: Nome completo.
- `String crm`: Número de registro profissional.
- `String especializacao`: Especialização médica.
- `String endereco`: Endereço residencial ou profissional.
- `String telefone`: Contato telefônico.
- `String email`: Endereço de e-mail.
- `List<HorarioDisponivel> disponibilidade`: Lista de horários disponíveis para consultas.

**Principais Métodos**:

- `getters` e `setters` para cada atributo.
- `void adicionarDisponibilidade(HorarioDisponivel horario)`: Adiciona um horário à lista de disponibilidade.
- `void removerDisponibilidade(HorarioDisponivel horario)`: Remove um horário da lista.

**Interações**:

- Interage com `Consulta` para vincular consultas ao médico.
- Utilizada por `MedicoDAO` para operações de persistência.

---

### 4.1.3 Classe `Consulta`

**Responsabilidade**: Representa uma consulta médica agendada ou realizada.

**Principais Atributos**:

- `int id`: Identificador único da consulta.
- `Paciente paciente`: Paciente que será atendido.
- `Medico medico`: Médico responsável pelo atendimento.
- `Date dataHora`: Data e hora da consulta.
- `String motivo`: Motivo da consulta.
- `String observacoes`: Observações e anotações médicas.

**Principais Métodos**:

- `getters` e `setters` para cada atributo.
- `boolean isAgendada()`: Verifica se a consulta está agendada ou já foi realizada.
- `void adicionarObservacao(String observacao)`: Adiciona uma observação à consulta.

**Interações**:

- Associa `Paciente` e `Medico` por meio de objetos dessas classes.
- Utilizada por `ConsultaDAO` para operações de persistência.

---

### 4.1.4 Classe `Prescricao`

**Responsabilidade**: Representa uma prescrição médica emitida durante uma consulta.

**Principais Atributos**:

- `int id`: Identificador único da prescrição.
- `Consulta consulta`: Consulta na qual a prescrição foi emitida.
- `List<MedicamentoPrescrito> medicamentos`: Lista de medicamentos prescritos, incluindo dosagens e instruções.

**Principais Métodos**:

- `getters` e `setters` para cada atributo.
- `void adicionarMedicamento(MedicamentoPrescrito medicamento)`: Adiciona um medicamento à prescrição.
- `void removerMedicamento(MedicamentoPrescrito medicamento)`: Remove um medicamento da prescrição.

**Interações**:

- Associada a uma `Consulta`.
- Utiliza `MedicamentoPrescrito` para detalhar cada medicamento.
- Persistida através de `PrescricaoDAO`.

---

### 4.1.5 Classe `MedicamentoPrescrito`

**Responsabilidade**: Representa um medicamento prescrito, incluindo detalhes como dosagem e instruções.

**Principais Atributos**:

- `int id`: Identificador único.
- `String nome`: Nome do medicamento.
- `String dosagem`: Dosagem prescrita.
- `String instrucoes`: Instruções de uso.

**Principais Métodos**:

- `getters` e `setters` para cada atributo.

**Interações**:

- Utilizada dentro de `Prescricao`.

---

### 4.1.6 Classe `Estoque`

**Responsabilidade**: Gerencia o estoque de medicamentos e materiais médicos.

**Principais Atributos**:

- `Map<String, Integer> itens`: Mapa de itens (nome do medicamento/material) e suas quantidades em estoque.

**Principais Métodos**:

- `void adicionarItem(String nome, int quantidade)`: Adiciona quantidade de um item ao estoque.
- `void removerItem(String nome, int quantidade)`: Remove quantidade de um item do estoque.
- `int verificarQuantidade(String nome)`: Retorna a quantidade disponível de um item.
- `List<String> listarItensBaixoEstoque()`: Retorna uma lista de itens com estoque abaixo do nível crítico.

**Interações**:

- Utilizada por `EstoqueService` para lógica de negócio.
- Pode interagir com `MedicamentoPrescrito` para atualizar o estoque após uma prescrição.

---

## 4.2 Camada DAO (Data Access Object)

As classes DAO são responsáveis pela comunicação com o banco de dados, executando operações de persistência.

### 4.2.1 Classe `PacienteDAO`

**Responsabilidade**: Realiza operações de CRUD (Create, Read, Update, Delete) para objetos `Paciente`.

**Principais Métodos**:

- `void inserir(Paciente paciente)`: Insere um novo paciente no banco de dados.
- `Paciente buscarPorId(int id)`: Recupera um paciente pelo ID.
- `List<Paciente> buscarTodos()`: Retorna uma lista de todos os pacientes.
- `void atualizar(Paciente paciente)`: Atualiza os dados de um paciente existente.
- `void deletar(int id)`: Remove um paciente do banco de dados.

**Interações**:

- Utiliza `DatabaseUtil` para obter conexões com o banco de dados.
- Chamado por `PacienteService` para acessar dados.

---

### 4.2.2 Classe `MedicoDAO`

**Responsabilidade**: Realiza operações de CRUD para objetos `Medico`.

**Principais Métodos**:

- `void inserir(Medico medico)`: Insere um novo médico no banco de dados.
- `Medico buscarPorId(int id)`: Recupera um médico pelo ID.
- `List<Medico> buscarTodos()`: Retorna uma lista de todos os médicos.
- `void atualizar(Medico medico)`: Atualiza os dados de um médico existente.
- `void deletar(int id)`: Remove um médico do banco de dados.

**Interações**:

- Interage com o banco de dados através de `DatabaseUtil`.
- Chamado por `MedicoService`.

---

### 4.2.3 Classe `ConsultaDAO`

**Responsabilidade**: Gerencia operações de persistência para `Consulta`.

**Principais Métodos**:

- `void inserir(Consulta consulta)`: Insere uma nova consulta.
- `Consulta buscarPorId(int id)`: Busca uma consulta pelo ID.
- `List<Consulta> buscarPorPaciente(int pacienteId)`: Busca consultas de um paciente específico.
- `List<Consulta> buscarPorMedico(int medicoId)`: Busca consultas de um médico específico.
- `void atualizar(Consulta consulta)`: Atualiza os dados de uma consulta.
- `void deletar(int id)`: Remove uma consulta.

**Interações**:

- Utiliza `DatabaseUtil` para conexões.
- Interage com `Paciente` e `Medico` para obter informações relacionadas.

---

### 4.2.4 Classe `PrescricaoDAO`

**Responsabilidade**: Gerencia a persistência de `Prescricao`.

**Principais Métodos**:

- `void inserir(Prescricao prescricao)`: Insere uma nova prescrição.
- `Prescricao buscarPorId(int id)`: Recupera uma prescrição pelo ID.
- `List<Prescricao> buscarPorConsulta(int consultaId)`: Recupera prescrições de uma consulta específica.
- `void atualizar(Prescricao prescricao)`: Atualiza uma prescrição.
- `void deletar(int id)`: Remove uma prescrição.

**Interações**:

- Interage com `ConsultaDAO` para garantir a integridade referencial.
- Utiliza `DatabaseUtil` para acesso ao banco.

---

## 4.3 Camada Service (Serviço)

As classes de serviço contêm a lógica de negócio do sistema, aplicando regras e validações.

### 4.3.1 Classe `PacienteService`

**Responsabilidade**: Gerencia as operações relacionadas a pacientes, aplicando regras de negócio.

**Principais Métodos**:

- `void cadastrarPaciente(Paciente paciente)`: Valida e cadastra um novo paciente.
- `Paciente obterPaciente(int id)`: Recupera um paciente, aplicando regras de acesso.
- `List<Paciente> listarPacientes()`: Lista todos os pacientes.
- `void atualizarPaciente(Paciente paciente)`: Valida e atualiza os dados de um paciente.
- `void removerPaciente(int id)`: Verifica se o paciente pode ser removido e realiza a exclusão.

**Interações**:

- Chama `PacienteDAO` para operações de persistência.
- Pode lançar `BusinessException` em caso de violação de regras de negócio.

---

### 4.3.2 Classe `MedicoService`

**Responsabilidade**: Gerencia operações relacionadas aos médicos.

**Principais Métodos**:

- `void cadastrarMedico(Medico medico)`: Valida e cadastra um novo médico.
- `Medico obterMedico(int id)`: Recupera informações de um médico.
- `List<Medico> listarMedicos()`: Lista todos os médicos.
- `void atualizarMedico(Medico medico)`: Atualiza dados do médico.
- `void removerMedico(int id)`: Remove um médico após verificar dependências.

**Interações**:

- Chama `MedicoDAO` para persistência.
- Interage com `ConsultaService` para verificar agendamentos.

---

### 4.3.3 Classe `ConsultaService`

**Responsabilidade**: Gerencia o agendamento e registro de consultas.

**Principais Métodos**:

- `void agendarConsulta(Consulta consulta)`: Valida disponibilidade e agenda uma consulta.
- `Consulta obterConsulta(int id)`: Recupera detalhes de uma consulta.
- `List<Consulta> listarConsultasPorPaciente(int pacienteId)`: Lista consultas de um paciente.
- `void atualizarConsulta(Consulta consulta)`: Atualiza informações da consulta.
- `void cancelarConsulta(int id)`: Cancela uma consulta agendada.

**Interações**:

- Verifica disponibilidade de `Medico` e `Paciente`.
- Chama `ConsultaDAO` para persistência.
- Pode enviar notificações (via `NotificationService`, se existente).

---

### 4.3.4 Classe `PrescricaoService`

**Responsabilidade**: Gerencia a emissão e registro de prescrições médicas.

**Principais Métodos**:

- `void emitirPrescricao(Prescricao prescricao)`: Valida e registra uma nova prescrição.
- `Prescricao obterPrescricao(int id)`: Recupera uma prescrição específica.
- `List<Prescricao> listarPrescricoesPorPaciente(int pacienteId)`: Lista prescrições de um paciente.
- `void atualizarPrescricao(Prescricao prescricao)`: Atualiza os detalhes de uma prescrição.
- `void removerPrescricao(int id)`: Remove uma prescrição existente.

**Interações**:

- Chama `PrescricaoDAO` para persistência.
- Interage com `EstoqueService` para verificar disponibilidade de medicamentos.

---

### 4.3.5 Classe `EstoqueService`

**Responsabilidade**: Gerencia o estoque de medicamentos, monitorando níveis e reposições.

**Principais Métodos**:

- `void adicionarItemEstoque(String nome, int quantidade)`: Adiciona itens ao estoque.
- `void consumirItemEstoque(String nome, int quantidade)`: Consome itens do estoque.
- `int verificarQuantidadeEstoque(String nome)`: Verifica quantidade disponível.
- `List<String> obterItensBaixoEstoque()`: Retorna lista de itens com estoque crítico.

**Interações**:

- Utiliza `Estoque` para manipulação dos dados.
- Pode enviar alertas de baixo estoque (via `NotificationService`).

---

## 4.4 Camada Controller (Controlador)

Os controladores lidam com a interação entre a interface do usuário ou API e a camada de serviço.

### 4.4.1 Classe `PacienteController`

**Responsabilidade**: Controla as operações relacionadas a pacientes.

**Principais Métodos**:

- `String cadastrarPaciente(HttpServletRequest request)`: Recebe dados do paciente, chama `PacienteService` e retorna uma resposta.
- `String visualizarPaciente(int id)`: Obtém detalhes de um paciente e os apresenta.
- `String atualizarPaciente(HttpServletRequest request)`: Atualiza informações do paciente.
- `String removerPaciente(int id)`: Remove um paciente do sistema.

**Interações**:

- Chama `PacienteService` para lógica de negócio.
- Processa dados de entrada e saída para a interface.

---

### 4.4.2 Classe `MedicoController`

**Responsabilidade**: Controla operações relacionadas aos médicos.

**Principais Métodos**:

- `String cadastrarMedico(HttpServletRequest request)`: Registra um novo médico.
- `String visualizarMedico(int id)`: Apresenta detalhes de um médico.
- `String atualizarMedico(HttpServletRequest request)`: Atualiza dados do médico.
- `String removerMedico(int id)`: Remove um médico do sistema.

**Interações**:

- Chama `MedicoService`.
- Interage com a interface para entrada e saída de dados.

---

### 4.4.3 Classe `ConsultaController`

**Responsabilidade**: Gerencia as operações de agendamento e visualização de consultas.

**Principais Métodos**:

- `String agendarConsulta(HttpServletRequest request)`: Recebe dados para agendamento e processa a solicitação.
- `String visualizarConsulta(int id)`: Exibe detalhes de uma consulta.
- `String atualizarConsulta(HttpServletRequest request)`: Atualiza informações da consulta.
- `String cancelarConsulta(int id)`: Processa o cancelamento de uma consulta.

**Interações**:

- Chama `ConsultaService`.
- Pode interagir com `PacienteController` e `MedicoController` para obter informações adicionais.

---

## 4.5 Camada Util (Utilitários)

Contém classes auxiliares que fornecem funcionalidades de suporte.

### 4.5.1 Classe `DatabaseUtil`

**Responsabilidade**: Gerencia conexões com o banco de dados.

**Principais Métodos**:

- `Connection getConnection()`: Estabelece e retorna uma conexão com o banco de dados.
- `void closeConnection(Connection conn)`: Fecha uma conexão existente.
- `void closeResources(Statement stmt, ResultSet rs)`: Fecha recursos utilizados nas operações.

**Interações**:

- Utilizada por todas as classes DAO.

---

### 4.5.2 Classe `DateUtil`

**Responsabilidade**: Fornece métodos para manipulação de datas.

**Principais Métodos**:

- `Date stringToDate(String dataString)`: Converte uma `String` para um objeto `Date`.
- `String dateToString(Date data)`: Converte um objeto `Date` para `String` formatada.
- `int calcularIdade(Date dataNascimento)`: Calcula a idade a partir da data de nascimento.

**Interações**:

- Utilizada por classes que lidam com datas, como `Paciente` e `Consulta`.

---

### 4.5.3 Classe `ValidationUtil`

**Responsabilidade**: Contém métodos para validação de dados de entrada.

**Principais Métodos**:

- `boolean isCPFValido(String cpf)`: Verifica se o CPF é válido.
- `boolean isEmailValido(String email)`: Verifica se o e-mail tem um formato válido.
- `boolean isDataFutura(Date data)`: Verifica se a data informada é futura.
- `void validarPaciente(Paciente paciente)`: Executa várias validações nos dados do paciente.

**Interações**:

- Utilizada pelas classes de serviço antes de operações críticas.

---

## 4.6 Camada Exception (Exceções)

Define exceções personalizadas para tratamento de erros específicos.

### 4.6.1 Classe `DataAccessException`

**Responsabilidade**: Exceção lançada em caso de erros ao acessar o banco de dados.

**Principais Construtores**:

- `DataAccessException(String mensagem)`: Cria uma exceção com uma mensagem específica.
- `DataAccessException(String mensagem, Throwable causa)`: Cria uma exceção com mensagem e causa.

**Interações**:

- Lançada pelas classes DAO.
- Capturada pelas classes de serviço ou controladores.

---

### 4.6.2 Classe `BusinessException`

**Responsabilidade**: Exceção lançada quando ocorre uma violação de regras de negócio.

**Principais Construtores**:

- `BusinessException(String mensagem)`: Cria uma exceção com uma mensagem específica.
- `BusinessException(String mensagem, Throwable causa)`: Cria uma exceção com mensagem e causa.

**Interações**:

- Lançada pelas classes de serviço.
- Capturada pelos controladores para apresentar mensagens ao usuário.

---

## 4.7 Fluxo de Interações

Para ilustrar as interações entre as classes, vamos descrever o fluxo de uma operação comum: **Agendamento de uma Consulta**.

1. **O usuário (paciente ou atendente) solicita o agendamento de uma consulta** através da interface do usuário.

2. **`ConsultaController.agendarConsulta(HttpServletRequest request)`**:
   - Recebe os dados da solicitação.
   - Extrai informações como pacienteId, medicoId, data e hora desejadas.

3. **`ConsultaService.agendarConsulta(Consulta consulta)`**:
   - Recebe o objeto `Consulta` com os dados preenchidos.
   - **Validações**:
     - Verifica se o paciente existe (`PacienteService.obterPaciente(int id)`).
     - Verifica se o médico existe e está disponível (`MedicoService.obterMedico(int id)`).
     - Verifica se o horário não está ocupado (`ConsultaDAO.buscarPorMedicoEData(medicoId, dataHora)`).
   - **Regras de Negócio**:
     - Não permitir agendamento em datas passadas.
     - Limitar o número de consultas por dia para um médico.
   - Em caso de sucesso, prossegue para persistência.
   - Em caso de erro, lança `BusinessException`.

4. **`ConsultaDAO.inserir(Consulta consulta)`**:
   - Executa a operação de inserção no banco de dados.
   - Em caso de falha na conexão ou execução, lança `DataAccessException`.

5. **Resposta ao Usuário**:
   - Se tudo ocorrer bem, o controlador retorna uma mensagem de sucesso.
   - Se ocorrer uma exceção, o controlador captura e apresenta uma mensagem de erro amigável.

---

## 4.8 Diagrama de Classes

Para uma melhor visualização das relações entre as classes, consulte o diagrama de classes disponível em `docs/diagrama_classes.png`.

---

## Conclusão

A organização das classes e métodos foi cuidadosamente planejada para garantir a coesão e o baixo acoplamento entre os componentes do sistema. Cada classe possui responsabilidades bem definidas, facilitando a manutenção e a escalabilidade do sistema. As interações entre as classes seguem os princípios da orientação a objetos, promovendo reutilização e flexibilidade.

