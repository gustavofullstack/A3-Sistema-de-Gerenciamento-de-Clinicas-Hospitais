# 4. Classes e Métodos (Atualizado)

## Visão Geral

O sistema de gerenciamento de clínicas e hospitais foi desenvolvido em Java, utilizando o framework **Spring Boot** com **JPA/Hibernate** para persistência de dados. O projeto segue uma arquitetura em camadas, promovendo a separação de responsabilidades e facilitando a manutenção e escalabilidade.

As principais camadas e seus componentes são:

- **Model (Modelo)**: Classes que representam as entidades do sistema, incluindo anotações JPA para mapeamento objeto-relacional.
- **Repository (Repositório)**: Interfaces que estendem `JpaRepository`, fornecendo métodos para operações CRUD no banco de dados.
- **Service (Serviço)**: Implementa a lógica de negócio, aplicando regras e validações.
- **Controller (Controlador)**: Exposição de endpoints REST para interação com clientes ou interface do usuário.
- **DTO (Data Transfer Object)**: Classes para transferência de dados entre camadas, especialmente para entrada e saída de dados em APIs.

---

## 4.1 Camada Model (Modelo)

As classes da camada Model representam as entidades do sistema e utilizam anotações do JPA para mapeamento para o banco de dados relacional.

### 4.1.1 Classe `Paciente`

**Responsabilidade**: Representa um paciente no sistema, armazenando informações pessoais, contato e histórico médico.

**Código Fonte Simplificado**:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Embedded
    private Endereco endereco;

    @Embedded
    private Contato contato;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoMedico> historicoMedico;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consulta> consultas;
}
```

**Principais Atributos**:

- `Long id`: Identificador único do paciente.
- `String nome`: Nome completo.
- `Date dataNascimento`: Data de nascimento.
- `Genero genero`: Enum representando o gênero (MASCULINO, FEMININO, OUTRO).
- `Endereco endereco`: Dados de endereço (classe embutida).
- `Contato contato`: Informações de contato (classe embutida).
- `List<HistoricoMedico> historicoMedico`: Histórico médico do paciente.
- `List<Consulta> consultas`: Consultas associadas ao paciente.

**Interações**:

- Relacionamento **1:N** com `HistoricoMedico` e `Consulta`.
- Utiliza `Endereco` e `Contato` como objetos embutidos.
- Persistência gerenciada pelo JPA/Hibernate.

---

### 4.1.2 Classe `Medico`

**Responsabilidade**: Representa um médico, incluindo informações profissionais, contato e disponibilidade.

**Código Fonte Simplificado**:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String numeroRegistro;

    @Enumerated(EnumType.STRING)
    private Especializacao especializacao;

    @Embedded
    private Endereco endereco;

    @Embedded
    private Contato contato;

    @OneToMany(mappedBy = "medico")
    private List<Disponibilidade> disponibilidades;

    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas;
}
```

**Principais Atributos**:

- `Long id`: Identificador único do médico.
- `String nome`: Nome completo.
- `String numeroRegistro`: Número de registro profissional (CRM).
- `Especializacao especializacao`: Especialização médica (enum).
- `Endereco endereco`: Dados de endereço.
- `Contato contato`: Informações de contato.
- `List<Disponibilidade> disponibilidades`: Horários disponíveis para atendimento.
- `List<Consulta> consultas`: Consultas realizadas pelo médico.

**Interações**:

- Relacionamento **1:N** com `Disponibilidade` e `Consulta`.
- Utiliza `Endereco` e `Contato` como objetos embutidos.
- Especializações definidas pelo enum `Especializacao`.

---

### 4.1.3 Classe `Consulta`

**Responsabilidade**: Representa uma consulta médica, ligando um paciente a um médico em uma data e hora específicas.

**Código Fonte Simplificado**:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;

    private String motivoConsulta;

    private String observacoesMedicas;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @OneToMany(mappedBy = "consulta")
    private List<Prescricao> prescricoes;

    @OneToMany(mappedBy = "consulta")
    private List<Exame> exames;
}
```

**Principais Atributos**:

- `Long id`: Identificador único da consulta.
- `Date dataHora`: Data e hora da consulta.
- `String motivoConsulta`: Motivo da consulta.
- `String observacoesMedicas`: Observações do médico.
- `Paciente paciente`: Paciente atendido.
- `Medico medico`: Médico responsável.
- `List<Prescricao> prescricoes`: Prescrições emitidas na consulta.
- `List<Exame> exames`: Exames solicitados na consulta.

**Interações**:

- Relacionamento **N:1** com `Paciente` e `Medico`.
- Relacionamento **1:N** com `Prescricao` e `Exame`.
- Centraliza a relação entre pacientes, médicos, prescrições e exames.

---

### 4.1.4 Classe `Prescricao`

**Responsabilidade**: Representa uma prescrição médica emitida durante uma consulta.

**Código Fonte Simplificado**:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Prescricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicamento;

    private String dosagem;

    private String instrucoesUso;

    @ManyToOne
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;
}
```

**Principais Atributos**:

- `Long id`: Identificador único da prescrição.
- `String medicamento`: Nome do medicamento.
- `String dosagem`: Dosagem prescrita.
- `String instrucoesUso`: Instruções de uso.
- `Consulta consulta`: Consulta na qual a prescrição foi emitida.

**Interações**:

- Relacionamento **N:1** com `Consulta`.
- Cada prescrição está associada a uma única consulta.

---

### 4.1.5 Classe `Exame`

**Responsabilidade**: Representa um exame solicitado durante uma consulta.

**Código Fonte Simplificado**:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeExame;

    private String resultado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExame;

    @ManyToOne
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;
}
```

**Principais Atributos**:

- `Long id`: Identificador único do exame.
- `String nomeExame`: Nome do exame.
- `String resultado`: Resultado do exame.
- `Date dataExame`: Data de realização do exame.
- `Consulta consulta`: Consulta associada ao exame.

**Interações**:

- Relacionamento **N:1** com `Consulta`.
- Os exames estão vinculados à consulta em que foram solicitados.

---

### 4.1.6 Classe `HistoricoMedico`

**Responsabilidade**: Mantém o histórico de condições de saúde, tratamentos e alergias de um paciente.

**Código Fonte Simplificado**:

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HistoricoMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String condicao;

    private String tratamento;

    private String alergias;

    private String medicamentosEmUso;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
}
```

**Principais Atributos**:

- `Long id`: Identificador único do histórico.
- `String condicao`: Condições de saúde.
- `String tratamento`: Tratamentos realizados.
- `String alergias`: Alergias conhecidas.
- `String medicamentosEmUso`: Medicamentos em uso.
- `Paciente paciente`: Paciente associado.

**Interações**:

- Relacionamento **N:1** com `Paciente`.
- Permite rastrear o histórico médico ao longo do tempo.

---

### 4.1.7 Classe `Endereco`

**Responsabilidade**: Encapsula os dados de endereço.

**Código Fonte Simplificado**:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Endereco {

    private String rua;
    private String cep;
    private String bairro;
    private String numero;
    private String cidade;
    private String complemento;
}
```

**Principais Atributos**:

- `String rua`
- `String cep`
- `String bairro`
- `String numero`
- `String cidade`
- `String complemento`

**Interações**:

- Utilizada como classe embutida em `Paciente` e `Medico`.
- Não possui identidade própria no banco de dados.

---

### 4.1.8 Classe `Contato`

**Responsabilidade**: Armazena informações de contato.

**Código Fonte Simplificado**:

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Contato {

    private String telefone;
    private String email;
}
```

**Principais Atributos**:

- `String telefone`
- `String email`

**Interações**:

- Utilizada como classe embutida em `Paciente` e `Medico`.

---

### 4.1.9 Classe `Disponibilidade`

**Responsabilidade**: Gerencia os horários disponíveis dos médicos para atendimento.

**Código Fonte Simplificado**:

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Disponibilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date inicio;

    private Date fim;

    private boolean disponivel;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;
}
```

**Principais Atributos**:

- `Long id`: Identificador único da disponibilidade.
- `Date inicio`: Início do período disponível.
- `Date fim`: Fim do período disponível.
- `boolean disponivel`: Indica se o horário está disponível.
- `Medico medico`: Médico associado.

**Interações**:

- Relacionamento **N:1** com `Medico`.
- Utilizada para verificar a disponibilidade dos médicos no agendamento de consultas.

---

### 4.1.10 Enum `Especializacao`

**Responsabilidade**: Define as especializações médicas disponíveis.

**Código Fonte Simplificado**:

```java
public enum Especializacao {
    CARDIOLOGIA,
    PEDIATRIA,
    ORTOPEDIA,
    GINECOLOGIA,
    DERMATOLOGIA,
    NEUROLOGIA,
    // Outros...
}
```

**Interações**:

- Utilizada em `Medico` para especificar a área de atuação.

---

### 4.1.11 Enum `Genero`

**Responsabilidade**: Representa o gênero do paciente.

**Código Fonte Simplificado**:

```java
public enum Genero {
    MASCULINO,
    FEMININO,
    OUTRO
}
```

**Interações**:

- Utilizada em `Paciente`.

---

## 4.2 Camada Repository (Repositório)

As interfaces de repositório estendem `JpaRepository`, fornecendo métodos padrão para operações CRUD. Exemplos:

### 4.2.1 Interface `PacienteRepository`

```java
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByNomeContaining(String nome);
}
```

**Responsabilidade**:

- Fornece métodos para interagir com o banco de dados em relação aos pacientes.
- Métodos customizados podem ser adicionados conforme necessário.

---

### 4.2.2 Interface `MedicoRepository`

```java
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findByEspecializacao(Especializacao especializacao);
}
```

**Responsabilidade**:

- Gerencia operações relacionadas aos médicos.
- Permite buscar médicos por especialização.

---

## 4.3 Camada Service (Serviço)

Implementa a lógica de negócio, aplicando validações e regras antes de interagir com a camada de persistência.

### 4.3.1 Classe `PacienteService`

**Responsabilidade**:

- Gerencia operações relacionadas a pacientes.
- Aplica validações de negócio.

**Principais Métodos**:

- `Paciente salvar(Paciente paciente)`: Valida e salva um paciente.
- `Paciente buscarPorId(Long id)`: Retorna um paciente pelo ID.
- `List<Paciente> buscarTodos()`: Retorna todos os pacientes.
- `void excluir(Long id)`: Remove um paciente.

**Interações**:

- Utiliza `PacienteRepository` para persistência.
- Pode lançar exceções personalizadas em caso de erros.

---

### 4.3.2 Classe `MedicoService`

**Responsabilidade**:

- Gerencia operações relacionadas aos médicos.

**Principais Métodos**:

- `Medico salvar(Medico medico)`: Salva um médico após validações.
- `Medico buscarPorId(Long id)`: Retorna um médico pelo ID.
- `List<Medico> buscarTodos()`: Lista todos os médicos.
- `void excluir(Long id)`: Remove um médico.

**Interações**:

- Utiliza `MedicoRepository`.

---

### 4.3.3 Classe `ConsultaService`

**Responsabilidade**:

- Gerencia o agendamento e registro de consultas.

**Principais Métodos**:

- `Consulta agendarConsulta(Consulta consulta)`: Agenda uma nova consulta após validações.
- `Consulta buscarPorId(Long id)`: Retorna detalhes de uma consulta.
- `void cancelarConsulta(Long id)`: Cancela uma consulta existente.

**Interações**:

- Verifica disponibilidade do médico (`Disponibilidade`).
- Utiliza `ConsultaRepository`.

---

## 4.4 Camada Controller (Controlador)

Exposição de endpoints REST para interação com o sistema.

### 4.4.1 Classe `PacienteController`

**Responsabilidade**:

- Define endpoints para operações com pacientes.

**Principais Endpoints**:

- `POST /pacientes`: Cadastra um novo paciente.
- `GET /pacientes/{id}`: Recupera informações de um paciente.
- `GET /pacientes`: Lista todos os pacientes.
- `PUT /pacientes/{id}`: Atualiza informações de um paciente.
- `DELETE /pacientes/{id}`: Remove um paciente.

**Interações**:

- Utiliza `PacienteService`.

---

### 4.4.2 Classe `MedicoController`

**Responsabilidade**:

- Define endpoints para operações com médicos.

**Principais Endpoints**:

- `POST /medicos`: Cadastra um novo médico.
- `GET /medicos/{id}`: Recupera informações de um médico.
- `GET /medicos`: Lista todos os médicos.
- `PUT /medicos/{id}`: Atualiza informações de um médico.
- `DELETE /medicos/{id}`: Remove um médico.

**Interações**:

- Utiliza `MedicoService`.

---

### 4.4.3 Classe `ConsultaController`

**Responsabilidade**:

- Gerencia endpoints relacionados a consultas.

**Principais Endpoints**:

- `POST /consultas`: Agenda uma nova consulta.
- `GET /consultas/{id}`: Detalhes de uma consulta.
- `GET /consultas`: Lista consultas com filtros opcionais.
- `PUT /consultas/{id}`: Atualiza uma consulta.
- `DELETE /consultas/{id}`: Cancela uma consulta.

**Interações**:

- Utiliza `ConsultaService`.

---

## 4.5 Camada DTO (Data Transfer Object)

Os DTOs são utilizados para transferir dados entre as camadas, especialmente para entrada e saída de dados nas APIs, garantindo que apenas as informações necessárias sejam expostas.

### 4.5.1 Classe `PacienteDTO`

**Responsabilidade**:

- Representa os dados do paciente para transferência.

**Principais Atributos**:

- `Long id`
- `String nome`
- `Date dataNascimento`
- `Genero genero`
- `Endereco endereco`
- `Contato contato`

**Interações**:

- Utilizada nos controladores para receber e enviar dados.

---

### 4.5.2 Classe `ConsultaDTO`

**Responsabilidade**:

- Representa os dados da consulta.

**Principais Atributos**:

- `Long id`
- `Long pacienteId`
- `Long medicoId`
- `Date dataHora`
- `String motivoConsulta`
- `String observacoesMedicas`

**Interações**:

- Facilita a transferência de dados sem expor entidades completas.

---

## 4.6 Fluxo de Interações Atualizado

### Caso de Uso: Agendamento de Consulta

1. **Cliente**: Envia uma requisição `POST` para `/consultas` com os dados da consulta no corpo da requisição.

2. **`ConsultaController.agendarConsulta(@RequestBody ConsultaDTO consultaDTO)`**:

   - Recebe o DTO e o converte em uma entidade `Consulta`.
   - Chama o `ConsultaService` para processar o agendamento.

3. **`ConsultaService.agendarConsulta(Consulta consulta)`**:

   - Valida os dados da consulta.
   - Verifica se o médico está disponível (`Disponibilidade`).
   - Salva a consulta utilizando o `ConsultaRepository`.

4. **`ConsultaRepository.save(consulta)`**:

   - Persiste a consulta no banco de dados.

5. **Resposta**:

   - Retorna o `ConsultaDTO` com os dados da consulta agendada e o status HTTP adequado.

---

## 4.7 Diagrama de Classes

Segue o diagrama de classes atualizado, representando as entidades e seus relacionamentos.

![Diagrama de Classes](docs/diagrama_classes.png)

**Nota**: O diagrama de classes está disponível no diretório `docs/diagrama_classes.png`.

---

## 4.8 Considerações sobre os Relacionamentos

- **Paciente e Medico**:

  - Ambos possuem endereço e contato como objetos embutidos (`@Embeddable`), evitando a criação de tabelas separadas para essas informações.

- **Consulta**:

  - Atua como ponto central, relacionando pacientes, médicos, prescrições e exames.

- **Disponibilidade**:

  - Permite gerenciar os horários de atendimento dos médicos, essencial para o agendamento de consultas.

- **HistoricoMedico**:

  - Fornece um registro completo das condições e tratamentos do paciente ao longo do tempo.

- **Prescricao e Exame**:

  - Associados a uma consulta específica, facilitando o rastreamento de tratamentos e diagnósticos.

---

## 4.9 Exemplo de Implementação de Métodos

### 4.9.1 `PacienteService`

```java
@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente salvar(Paciente paciente) {
        // Validações personalizadas
        if (paciente.getNome() == null || paciente.getNome().isEmpty()) {
            throw new BusinessException("O nome do paciente é obrigatório.");
        }
        // Outras validações...

        return pacienteRepository.save(paciente);
    }

    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado."));
    }

    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    public void excluir(Long id) {
        Paciente paciente = buscarPorId(id);
        pacienteRepository.delete(paciente);
    }
}
```

---

## 4.10 Exceções Personalizadas

### 4.10.1 `BusinessException`

**Responsabilidade**:

- Exceção para erros de validação e regras de negócio.

**Código Fonte Simplificado**:

```java
public class BusinessException extends RuntimeException {
    public BusinessException(String mensagem) {
        super(mensagem);
    }
}
```

### 4.10.2 `NotFoundException`

**Responsabilidade**:

- Exceção para recursos não encontrados.

**Código Fonte Simplificado**:

```java
public class NotFoundException extends RuntimeException {
    public NotFoundException(String mensagem) {
        super(mensagem);
    }
}
```

---

## 4.11 Conclusão

A modelagem das classes reflete as necessidades funcionais do sistema, garantindo que todas as informações essenciais sejam armazenadas e gerenciadas de forma eficiente. A utilização de anotações JPA facilita o mapeamento objeto-relacional, enquanto a separação em camadas promove uma arquitetura limpa e organizada.

A compreensão das responsabilidades de cada classe e de seus métodos é fundamental para o desenvolvimento e manutenção do sistema, permitindo que novos desenvolvedores se integrem rapidamente ao projeto e contribuam de forma efetiva.