# Diagrama de Classes

Abaixo está o diagrama de classes representando todas as classes do sistema, utilizando a sintaxe do **Mermaid**. Este diagrama inclui todas as classes, seus atributos e os relacionamentos entre elas.

Você pode renderizar este diagrama utilizando ferramentas compatíveis com Mermaid, como extensões para VSCode, editores online como o [Mermaid Live Editor](https://mermaid.live/), ou diretamente em plataformas que suportam Mermaid.

```mermaid
classDiagram
    %% Definição das Classes

    class Paciente {
        Long id
        String nome
        Date dataNascimento
        Genero genero
        Endereco endereco
        Contato contato
    }

    class Medico {
        Long id
        String nome
        String numeroRegistro
        Especializacao especializacao
        Endereco endereco
        Contato contato
    }

    class Consulta {
        Long id
        Date dataHora
        String motivoConsulta
        String observacoesMedicas
    }

    class Prescricao {
        Long id
        String medicamento
        String dosagem
        String instrucoesUso
    }

    class Exame {
        Long id
        String nomeExame
        String resultado
        Date dataExame
    }

    class HistoricoMedico {
        Long id
        String condicao
        String tratamento
        String alergias
        String medicamentosEmUso
    }

    class Disponibilidade {
        Long id
        Date inicio
        Date fim
        boolean disponivel
    }

    class Endereco {
        String rua
        String cep
        String bairro
        String numero
        String cidade
        String complemento
    }

    class Contato {
        String telefone
        String email
    }

    class Especializacao {
        <<enumeration>>
        +CARDIOLOGIA
        +PEDIATRIA
        +ORTOPEDIA
        +GINECOLOGIA
        +DERMATOLOGIA
        +NEUROLOGIA
        // Outros valores...
    }

    class Genero {
        <<enumeration>>
        +MASCULINO
        +FEMININO
        +OUTRO
    }

    %% Relações entre as Classes

    %% Composição: Paciente e Medico possuem Endereco e Contato embutidos
    Paciente *-- Endereco : "endereco"
    Paciente *-- Contato : "contato"
    Medico *-- Endereco : "endereco"
    Medico *-- Contato : "contato"

    %% Associação: Paciente e HistoricoMedico
    Paciente "1" o-- "0..*" HistoricoMedico : "historicoMedico"

    %% Associação: Paciente e Consulta
    Paciente "1" o-- "0..*" Consulta : "consultas"

    %% Associação: Medico e Disponibilidade
    Medico "1" o-- "0..*" Disponibilidade : "disponibilidades"

    %% Associação: Medico e Consulta
    Medico "1" o-- "0..*" Consulta : "consultas"

    %% Associação: Consulta e Prescricao
    Consulta "1" o-- "0..*" Prescricao : "prescricoes"

    %% Associação: Consulta e Exame
    Consulta "1" o-- "0..*" Exame : "exames"

    %% Associação: Consulta com Paciente e Medico
    Consulta "*" --> "1" Paciente : "paciente"
    Consulta "*" --> "1" Medico : "medico"

    %% Associação: Prescricao e Consulta
    Prescricao "*" --> "1" Consulta : "consulta"

    %% Associação: Exame e Consulta
    Exame "*" --> "1" Consulta : "consulta"

    %% Associação: HistoricoMedico e Paciente
    HistoricoMedico "*" --> "1" Paciente : "paciente"

    %% Associação: Disponibilidade e Medico
    Disponibilidade "*" --> "1" Medico : "medico"

    %% Enumerações: Especializacao e Genero
    Medico --> Especializacao : "especializacao"
    Paciente --> Genero : "genero"
```

## Explicação do Diagrama

- **Paciente**:
  - Contém informações pessoais e médicas do paciente.
  - Possui composição com as classes `Endereco` e `Contato`, indicando que essas informações estão embutidas na classe `Paciente`.
  - Relaciona-se com `HistoricoMedico` (1 para muitos) e `Consulta` (1 para muitos).

- **Medico**:
  - Armazena dados profissionais e de contato do médico.
  - Possui composição com `Endereco` e `Contato`.
  - Relaciona-se com `Disponibilidade` (1 para muitos) e `Consulta` (1 para muitos).
  - Usa a enumeração `Especializacao` para definir sua área médica.

- **Consulta**:
  - Representa uma consulta médica entre um paciente e um médico.
  - Relaciona-se com `Paciente` e `Medico` (muitos para um).
  - Possui relacionamentos com `Prescricao` e `Exame` (um para muitos).

- **Prescricao**:
  - Armazena informações sobre medicamentos prescritos durante uma consulta.
  - Relaciona-se com `Consulta` (muitos para um).

- **Exame**:
  - Registra exames solicitados em uma consulta.
  - Relaciona-se com `Consulta` (muitos para um).

- **HistoricoMedico**:
  - Mantém o histórico médico do paciente.
  - Relaciona-se com `Paciente` (muitos para um).

- **Disponibilidade**:
  - Gerencia os horários disponíveis de um médico.
  - Relaciona-se com `Medico` (muitos para um).

- **Endereco** e **Contato**:
  - Classes embutidas que armazenam informações de endereço e contato.
  - Utilizadas tanto por `Paciente` quanto por `Medico` através de composição.

- **Especializacao** e **Genero**:
  - Enumerações que definem especializações médicas e gêneros, respectivamente.
  - Utilizadas em `Medico` e `Paciente` como tipos de atributos.

## Como Interpretar o Diagrama

- **Classes**: Representadas como caixas contendo o nome da classe e seus atributos.
- **Relacionamentos**:
  - **Composição (\*--):** Indica que uma classe é parte integrante de outra (ex.: `Paciente` e `Endereco`).
  - **Agregação (o--):** Representa um relacionamento onde uma classe é uma coleção de outras (ex.: `Paciente` possui uma coleção de `Consulta`).
  - **Associação (--\>):** Indica que uma classe utiliza ou depende de outra (ex.: `Consulta` se associa a `Paciente`).
- **Multiplicidade**:
  - Os números próximos às setas indicam quantidades mínimas e máximas de objetos que participam do relacionamento (ex.: `Paciente "1" o-- "0..*" Consulta` significa que um paciente pode ter zero ou mais consultas).

## Observações

- As **enumerações** `Especializacao` e `Genero` estão representadas com o estereótipo `<<enumeration>>` e listam seus valores possíveis.
- As classes embutidas (`Endereco` e `Contato`) não possuem relações independentes no banco de dados; elas são incorporadas nas tabelas de `Paciente` e `Medico`.
- As setas direcionais indicam a navegabilidade e a dependência entre as classes, facilitando o entendimento dos fluxos de dados no sistema.

## Utilização do Diagrama

Este diagrama serve como referência para desenvolvedores e arquitetos entenderem a estrutura do sistema, as entidades envolvidas e como elas se relacionam. É uma ferramenta essencial para:

- **Desenvolvimento**: Auxilia na implementação correta das classes e seus relacionamentos.
- **Manutenção**: Facilita a identificação de componentes para correções ou melhorias.
- **Documentação**: Proporciona uma visão clara da arquitetura do sistema para novos membros da equipe ou partes interessadas.

---

Com este diagrama, você tem uma representação visual completa das classes e relações que compõem o sistema de gerenciamento de clínicas e hospitais, conforme detalhado nas seções anteriores.