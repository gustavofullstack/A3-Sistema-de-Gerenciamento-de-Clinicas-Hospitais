# Caso de Uso: Sistema de Gerenciamento de Clínica Médica

## Introdução

Este documento detalha os casos de uso do sistema de gerenciamento de uma clínica médica, baseado no código fornecido. O sistema permite o cadastro e gerenciamento de pacientes, médicos, consultas, endereços e contatos, seguindo as melhores práticas de desenvolvimento e arquitetura de software.

O objetivo é criar um sistema robusto, escalável e confiável, que atenda às necessidades operacionais da clínica, garantindo a integridade dos dados e facilitando a interação entre pacientes, médicos e administradores.

## Sumário

1. **Atores**
   - 1.1. Administrador da Clínica
   - 1.2. Médico
   - 1.3. Paciente
2. **Casos de Uso**
   - 2.1. Gerenciamento de Pacientes
     - 2.1.1. Cadastrar Paciente
     - 2.1.2. Consultar Paciente
     - 2.1.3. Atualizar Paciente
     - 2.1.4. Deletar Paciente
     - 2.1.5. Gerenciar Endereços do Paciente
     - 2.1.6. Gerenciar Contatos do Paciente
   - 2.2. Gerenciamento de Médicos
     - 2.2.1. Cadastrar Médico
     - 2.2.2. Consultar Médico
     - 2.2.3. Atualizar Médico
     - 2.2.4. Deletar Médico
     - 2.2.5. Gerenciar Endereços do Médico
     - 2.2.6. Gerenciar Contatos do Médico
   - 2.3. Gerenciamento de Consultas
     - 2.3.1. Agendar Consulta
     - 2.3.2. Consultar Consulta
     - 2.3.3. Atualizar Consulta
     - 2.3.4. Deletar Consulta
3. **Requisitos Não Funcionais**
4. **Considerações Técnicas**
5. **Conclusão**

---

## 1. Atores

### 1.1. Administrador da Clínica

Responsável por gerenciar o sistema, incluindo cadastro, atualização e exclusão de pacientes, médicos e consultas. Tem acesso total às funcionalidades administrativas.

### 1.2. Médico

Profissional de saúde que realiza consultas, prescreve medicamentos e solicita exames. Pode consultar suas próprias informações e as de seus pacientes, além de gerenciar suas consultas.

### 1.3. Paciente

Indivíduo que recebe atendimento médico na clínica. Pode consultar suas próprias informações e agendar consultas.

---

## 2. Casos de Uso

### 2.1. Gerenciamento de Pacientes

#### 2.1.1. Cadastrar Paciente

**Descrição**: Permite ao administrador cadastrar um novo paciente no sistema, incluindo informações pessoais, endereços e contatos.

**Atores Envolvidos**: Administrador da Clínica

**Pré-condições**:

- O administrador está autenticado no sistema.
- As informações obrigatórias do paciente estão disponíveis.

**Fluxo Principal**:

1. O administrador seleciona "Cadastrar Paciente" no sistema.
2. O sistema exibe um formulário para inserir os dados pessoais do paciente:
   - Nome
   - CPF
   - Data de Nascimento
   - Gênero
3. O administrador preenche o formulário com as informações necessárias.
4. O sistema valida os dados inseridos:
   - Verifica se o CPF já está cadastrado.
   - Valida formatos e campos obrigatórios.
5. O administrador insere os endereços do paciente:
   - Rua
   - CEP
   - Bairro
   - Número
   - Cidade
   - Complemento (opcional)
6. O administrador insere os contatos do paciente:
   - Telefone
   - E-mail
7. O sistema salva as informações no banco de dados.
8. O sistema confirma o cadastro com uma mensagem de sucesso.

**Fluxos Alternativos**:

- **4a.** Se os dados são inválidos:
  - O sistema exibe mensagens de erro específicas.
  - Retorna ao passo 3 para correção.
- **5a.** Se o paciente não possui endereços no momento:
  - O administrador pode optar por cadastrar endereços posteriormente.
- **6a.** Se o paciente não possui contatos no momento:
  - O administrador pode optar por cadastrar contatos posteriormente.

**Pós-condições**:

- O paciente é registrado no sistema com um ID único.
- As informações podem ser consultadas e atualizadas posteriormente.

**Regras de Negócio**:

- O CPF deve ser único no sistema.
- Os campos obrigatórios não podem estar vazios.

---

#### 2.1.2. Consultar Paciente

**Descrição**: Permite ao administrador ou médico consultar as informações detalhadas de um paciente.

**Atores Envolvidos**: Administrador da Clínica, Médico

**Pré-condições**:

- O ator está autenticado no sistema.
- O paciente está cadastrado no sistema.

**Fluxo Principal**:

1. O ator seleciona "Consultar Paciente".
2. O sistema solicita o identificador do paciente (ID ou CPF).
3. O ator fornece o identificador.
4. O sistema recupera as informações do paciente:
   - Dados pessoais
   - Endereços
   - Contatos
   - Histórico médico
   - Consultas anteriores e futuras
5. O sistema exibe as informações ao ator.

**Fluxos Alternativos**:

- **3a.** Se o paciente não é encontrado:
  - O sistema informa que o paciente não existe.
  - O ator pode tentar novamente ou cancelar a operação.

**Pós-condições**:

- As informações do paciente são apresentadas ao ator.

---

#### 2.1.3. Atualizar Paciente

**Descrição**: Permite ao administrador atualizar as informações de um paciente existente.

**Atores Envolvidos**: Administrador da Clínica

**Pré-condições**:

- O administrador está autenticado no sistema.
- O paciente está cadastrado no sistema.

**Fluxo Principal**:

1. O administrador seleciona "Atualizar Paciente".
2. O sistema solicita o ID do paciente.
3. O administrador fornece o ID.
4. O sistema exibe as informações atuais do paciente.
5. O administrador modifica os dados necessários.
6. O sistema valida os novos dados.
7. O sistema atualiza as informações no banco de dados.
8. O sistema confirma a atualização com uma mensagem de sucesso.

**Fluxos Alternativos**:

- **6a.** Se os novos dados são inválidos:
  - O sistema exibe mensagens de erro.
  - Retorna ao passo 5 para correção.

**Pós-condições**:

- As informações do paciente são atualizadas no sistema.

---

#### 2.1.4. Deletar Paciente

**Descrição**: Permite ao administrador remover um paciente do sistema.

**Atores Envolvidos**: Administrador da Clínica

**Pré-condições**:

- O administrador está autenticado no sistema.
- O paciente está cadastrado no sistema.
- O paciente não possui consultas futuras agendadas.

**Fluxo Principal**:

1. O administrador seleciona "Deletar Paciente".
2. O sistema solicita o ID do paciente.
3. O administrador fornece o ID.
4. O sistema verifica se o paciente possui consultas futuras.
5. Se não houver impedimentos, o sistema remove o paciente do banco de dados.
6. O sistema confirma a exclusão com uma mensagem de sucesso.

**Fluxos Alternativos**:

- **4a.** Se o paciente possui consultas futuras:
  - O sistema informa que o paciente não pode ser deletado.
  - O administrador deve cancelar as consultas antes de prosseguir.

**Pós-condições**:

- O paciente é removido do sistema.
- Suas informações não estão mais disponíveis.

---

#### 2.1.5. Gerenciar Endereços do Paciente

**Descrição**: Permite adicionar, atualizar ou remover endereços associados ao paciente.

##### a) Adicionar Endereço

**Fluxo Principal**:

1. O administrador seleciona "Adicionar Endereço ao Paciente".
2. O sistema solicita o ID do paciente.
3. O administrador fornece o ID.
4. O sistema verifica a existência do paciente.
5. O administrador insere os dados do novo endereço.
6. O sistema valida os dados.
7. O sistema associa o endereço ao paciente e salva.
8. O sistema confirma a adição.

##### b) Atualizar Endereço

**Fluxo Principal**:

1. O administrador seleciona "Atualizar Endereço do Paciente".
2. O sistema solicita o ID do paciente e do endereço.
3. O administrador fornece os IDs.
4. O sistema verifica a existência e associação dos dados.
5. O administrador modifica os dados do endereço.
6. O sistema valida as alterações.
7. O sistema atualiza o endereço.
8. O sistema confirma a atualização.

##### c) Deletar Endereço

**Fluxo Principal**:

1. O administrador seleciona "Deletar Endereço do Paciente".
2. O sistema solicita o ID do paciente e do endereço.
3. O administrador fornece os IDs.
4. O sistema verifica a existência e associação.
5. O sistema remove o endereço.
6. O sistema confirma a exclusão.

---

#### 2.1.6. Gerenciar Contatos do Paciente

Semelhante ao gerenciamento de endereços, permite adicionar, atualizar ou remover contatos associados ao paciente.

---

### 2.2. Gerenciamento de Médicos

#### 2.2.1. Cadastrar Médico

**Descrição**: Permite ao administrador cadastrar um novo médico no sistema, incluindo informações pessoais, especialização, endereços e contatos.

**Atores Envolvidos**: Administrador da Clínica

**Pré-condições**:

- O administrador está autenticado no sistema.
- As informações obrigatórias do médico estão disponíveis.

**Fluxo Principal**:

1. O administrador seleciona "Cadastrar Médico".
2. O sistema exibe um formulário para inserir os dados pessoais:
   - Nome
   - CPF
   - Data de Nascimento
   - Gênero
   - Número de Registro
   - Especialização
3. O administrador preenche o formulário.
4. O sistema valida os dados:
   - Verifica se o CPF e número de registro já estão cadastrados.
   - Valida formatos e campos obrigatórios.
5. O administrador insere os endereços do médico.
6. O administrador insere os contatos do médico.
7. O sistema salva as informações no banco de dados.
8. O sistema confirma o cadastro.

**Fluxos Alternativos**:

- **4a.** Se os dados são inválidos:
  - O sistema exibe mensagens de erro.
  - Retorna ao passo 3 para correção.

**Pós-condições**:

- O médico é registrado com um ID único.
- As informações podem ser consultadas e atualizadas.

---

#### 2.2.2. Consultar Médico

**Descrição**: Permite ao administrador ou médico consultar as informações detalhadas de um médico.

**Atores Envolvidos**: Administrador da Clínica, Médico

**Pré-condições**:

- O ator está autenticado.
- O médico está cadastrado.

**Fluxo Principal**:

1. O ator seleciona "Consultar Médico".
2. O sistema solicita o ID ou CPF do médico.
3. O ator fornece o identificador.
4. O sistema recupera as informações:
   - Dados pessoais
   - Especialização
   - Endereços
   - Contatos
   - Consultas associadas
5. O sistema exibe as informações.

---

#### 2.2.3. Atualizar Médico

**Descrição**: Permite ao administrador atualizar as informações de um médico existente.

**Atores Envolvidos**: Administrador da Clínica

**Pré-condições**:

- O administrador está autenticado.
- O médico está cadastrado.

**Fluxo Principal**:

1. O administrador seleciona "Atualizar Médico".
2. O sistema solicita o ID do médico.
3. O administrador fornece o ID.
4. O sistema exibe as informações atuais.
5. O administrador modifica os dados necessários.
6. O sistema valida as alterações.
7. O sistema atualiza as informações.
8. O sistema confirma a atualização.

---

#### 2.2.4. Deletar Médico

**Descrição**: Permite ao administrador remover um médico do sistema.

**Atores Envolvidos**: Administrador da Clínica

**Pré-condições**:

- O administrador está autenticado.
- O médico está cadastrado.
- O médico não possui consultas futuras agendadas.

**Fluxo Principal**:

1. O administrador seleciona "Deletar Médico".
2. O sistema solicita o ID do médico.
3. O administrador fornece o ID.
4. O sistema verifica se o médico possui consultas futuras.
5. Se não houver impedimentos, o sistema remove o médico.
6. O sistema confirma a exclusão.

**Fluxos Alternativos**:

- **4a.** Se o médico possui consultas futuras:
  - O sistema informa que não pode prosseguir.
  - O administrador deve realocar ou cancelar as consultas.

---

#### 2.2.5. Gerenciar Endereços do Médico

Semelhante ao gerenciamento de endereços do paciente.

---

#### 2.2.6. Gerenciar Contatos do Médico

Semelhante ao gerenciamento de contatos do paciente.

---

### 2.3. Gerenciamento de Consultas

#### 2.3.1. Agendar Consulta

**Descrição**: Permite ao administrador ou médico agendar uma nova consulta entre um paciente e um médico.

**Atores Envolvidos**: Administrador da Clínica, Médico

**Pré-condições**:

- O paciente e o médico estão cadastrados.
- O horário está disponível.

**Fluxo Principal**:

1. O ator seleciona "Agendar Consulta".
2. O sistema exibe um formulário:
   - Data e hora
   - Motivo
   - ID do paciente
   - ID do médico
3. O ator preenche o formulário.
4. O sistema valida:
   - Existência do paciente e médico.
   - Disponibilidade do médico.
5. O sistema salva a consulta.
6. O sistema confirma o agendamento.

**Fluxos Alternativos**:

- **4a.** Se o médico não está disponível:
  - O sistema informa a indisponibilidade.
  - O ator pode escolher outro horário ou médico.

**Pós-condições**:

- A consulta é registrada.
- Paciente e médico são notificados.

---

#### 2.3.2. Consultar Consulta

**Descrição**: Permite ao administrador, médico ou paciente consultar detalhes de uma consulta.

**Atores Envolvidos**: Administrador da Clínica, Médico, Paciente

**Pré-condições**:

- O ator está autenticado.
- A consulta está cadastrada.

**Fluxo Principal**:

1. O ator seleciona "Consultar Consulta".
2. O sistema solicita o ID da consulta.
3. O ator fornece o ID.
4. O sistema recupera os detalhes:
   - Data e hora
   - Motivo
   - Observações
   - Paciente e médico envolvidos
5. O sistema exibe as informações.

---

#### 2.3.3. Atualizar Consulta

**Descrição**: Permite ao administrador ou médico atualizar informações de uma consulta.

**Atores Envolvidos**: Administrador da Clínica, Médico

**Pré-condições**:

- O ator está autenticado.
- A consulta está cadastrada.

**Fluxo Principal**:

1. O ator seleciona "Atualizar Consulta".
2. O sistema solicita o ID da consulta.
3. O ator fornece o ID.
4. O sistema exibe as informações atuais.
5. O ator modifica os dados necessários.
6. O sistema valida as alterações:
   - Disponibilidade do médico, se data/hora foram alteradas.
7. O sistema atualiza a consulta.
8. O sistema confirma a atualização.

**Fluxos Alternativos**:

- **6a.** Se o médico não está disponível:
  - O sistema informa a indisponibilidade.
  - Retorna ao passo 5.

---

#### 2.3.4. Deletar Consulta

**Descrição**: Permite ao administrador ou médico cancelar uma consulta.

**Atores Envolvidos**: Administrador da Clínica, Médico

**Pré-condições**:

- O ator está autenticado.
- A consulta está cadastrada.

**Fluxo Principal**:

1. O ator seleciona "Deletar Consulta".
2. O sistema solicita o ID da consulta.
3. O ator fornece o ID.
4. O sistema verifica a existência.
5. O sistema remove a consulta.
6. O sistema confirma o cancelamento.

**Pós-condições**:

- A consulta é removida.
- Paciente e médico são notificados.

---

## 3. Requisitos Não Funcionais

- **Segurança**: Proteção dos dados sensíveis, uso de autenticação e autorização adequadas.
- **Usabilidade**: Interface intuitiva, com feedbacks claros e consistentes.
- **Performance**: Respostas rápidas, mesmo com grande volume de dados.
- **Escalabilidade**: Capacidade de suportar aumento de usuários e operações.
- **Confiabilidade**: Sistema estável, com tratamento adequado de erros.
- **Manutenibilidade**: Código limpo, modular e documentado, facilitando atualizações futuras.

---

## 4. Considerações Técnicas

- **Arquitetura**: MVC, separando responsabilidades entre Controladores, Serviços e Repositórios.
- **Tecnologias**:
  - **Spring Boot**: Framework para desenvolvimento rápido em Java.
  - **Spring Data JPA**: Facilita operações com o banco de dados.
  - **Hibernate**: Implementação do JPA.
  - **MySQL**: Banco de dados relacional.
- **Validações**:
  - Uso de anotações como `@NotNull`, `@Size`, `@PastOrPresent`.
  - Tratamento de exceções com `GlobalExceptionHandler`.
- **DTOs**: Uso de objetos de transferência de dados para evitar exposição direta das entidades.
- **Transações**: Controle de transações com `@Transactional` para garantir consistência.
- **Boas Práticas**:
  - Nomes significativos para classes e métodos.
  - Separação de camadas e responsabilidades.
  - Tratamento adequado de exceções.

---

## 5. Conclusão

Este documento apresenta uma visão detalhada dos casos de uso do sistema de gerenciamento de uma clínica médica, garantindo que todas as funcionalidades essenciais sejam cobertas e implementadas seguindo as melhores práticas.

O sistema proporcionará uma gestão eficiente e segura dos processos da clínica, melhorando a qualidade do atendimento aos pacientes e facilitando o trabalho dos profissionais de saúde.
