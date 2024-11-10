# 6. Requisitos de Implementação

## Introdução

Esta seção fornece instruções detalhadas sobre como configurar e executar o **Sistema de Gerenciamento de Clínicas e Hospitais** em um ambiente de desenvolvimento. Inclui os pré-requisitos, passos para configuração, execução do projeto e dicas para solução de problemas.

## 6.1 Pré-requisitos

Antes de iniciar a configuração, certifique-se de que o seu ambiente atenda aos seguintes requisitos:

### 6.1.1 Software Necessário

- **Java Development Kit (JDK) 11 ou superior**: Necessário para compilar e executar o projeto Java.
- **Apache Maven 3.6 ou superior**: Utilizado para gerenciamento de dependências e construção do projeto.
- **Banco de Dados Relacional**: MySQL 8.0 ou PostgreSQL 12.0 (ou superior) para armazenamento de dados.
- **IDE Java (Opcional, mas recomendado)**: IntelliJ IDEA, Eclipse ou NetBeans para facilitar o desenvolvimento.
- **Git**: Para clonar o repositório do projeto, se necessário.

### 6.1.2 Recursos do Sistema

- **Memória RAM**: Mínimo de 4 GB.
- **Espaço em Disco**: Pelo menos 500 MB disponíveis para o projeto e dependências.
- **Sistema Operacional**: Windows 10 ou superior, macOS Catalina ou superior, Linux Ubuntu 18.04 ou superior.

## 6.2 Configuração do Ambiente

### 6.2.1 Instalação do JDK

1. **Download do JDK**:
   - Acesse o site oficial da Oracle ([https://www.oracle.com/java/technologies/javase-jdk11-downloads.html](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)) ou utilize o OpenJDK ([https://jdk.java.net/11/](https://jdk.java.net/11/)).

2. **Instalação**:
   - Siga as instruções de instalação para o seu sistema operacional.

3. **Configuração da Variável de Ambiente**:
   - Configure a variável `JAVA_HOME` apontando para o diretório de instalação do JDK.
   - Adicione `%JAVA_HOME%/bin` ao `PATH` do sistema.

4. **Verificação da Instalação**:
   - Abra o terminal ou prompt de comando e execute:

     ```bash
     java -version
     ```

     - A saída deve exibir a versão do Java instalada.

### 6.2.2 Instalação do Apache Maven

1. **Download do Maven**:
   - Baixe a versão mais recente do Maven em [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi).

2. **Instalação**:
   - Extraia o arquivo em um diretório de sua preferência.

3. **Configuração da Variável de Ambiente**:
   - Configure a variável `MAVEN_HOME` apontando para o diretório de instalação do Maven.
   - Adicione `%MAVEN_HOME%/bin` ao `PATH` do sistema.

4. **Verificação da Instalação**:
   - No terminal, execute:

     ```bash
     mvn -version
     ```

     - Deve exibir a versão do Maven instalada.

### 6.2.3 Instalação do Banco de Dados

#### MySQL

1. **Download e Instalação**:
   - Baixe o MySQL Community Server em [https://dev.mysql.com/downloads/mysql/](https://dev.mysql.com/downloads/mysql/).
   - Siga as instruções de instalação.

2. **Configuração**:
   - Defina uma senha para o usuário `root` durante a instalação.
   - Certifique-se de que o serviço do MySQL está em execução.

#### PostgreSQL (Alternativa)

1. **Download e Instalação**:
   - Baixe o PostgreSQL em [https://www.postgresql.org/download/](https://www.postgresql.org/download/).
   - Siga as instruções de instalação.

2. **Configuração**:
   - Defina uma senha para o usuário `postgres`.
   - Certifique-se de que o serviço do PostgreSQL está em execução.

### 6.2.4 Instalação da IDE (Opcional)

- **IntelliJ IDEA**:
  - Baixe em [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/).
  - Recomenda-se a versão Community (gratuita).

- **Eclipse**:
  - Baixe em [https://www.eclipse.org/downloads/](https://www.eclipse.org/downloads/).

- **NetBeans**:
  - Baixe em [https://netbeans.apache.org/download/index.html](https://netbeans.apache.org/download/index.html).

## 6.3 Configuração do Projeto

### 6.3.1 Clonando o Repositório

1. **Usando Git**:
   - Abra o terminal e execute:

     ```bash
     git clone https://github.com/seu-usuario/hospital-management-system.git
     ```

2. **Download do Código-Fonte**:
   - Se não utilizar Git, baixe o código-fonte como um arquivo ZIP pelo GitHub e extraia em um diretório.

### 6.3.2 Importando o Projeto na IDE

1. **Abrir a IDE**:
   - Inicie a sua IDE Java preferida.

2. **Importar Projeto Maven**:
   - Selecione a opção para importar um projeto existente.
   - Navegue até o diretório `hospital-management-system/` e selecione o arquivo `pom.xml`.

3. **Configuração Automática**:
   - A IDE deve detectar o projeto Maven e configurar o classpath e as dependências automaticamente.

### 6.3.3 Configuração do Banco de Dados

1. **Criar o Banco de Dados**:
   - Abra o terminal ou um cliente SQL (como MySQL Workbench ou pgAdmin).
   - Execute o seguinte comando para criar o banco de dados:

     ```sql
     CREATE DATABASE hospital_db;
     ```

2. **Criar Usuário (Opcional)**:
   - Para maior segurança, crie um usuário específico para a aplicação:

     ```sql
     CREATE USER 'hospital_user'@'localhost' IDENTIFIED BY 'sua_senha';
     GRANT ALL PRIVILEGES ON hospital_db.* TO 'hospital_user'@'localhost';
     FLUSH PRIVILEGES;
     ```

### 6.3.4 Configuração do Arquivo `application.properties`

1. **Localizar o Arquivo**:
   - Navegue até `src/main/resources/application.properties`.

2. **Editar as Propriedades**:
   - Configure as informações de conexão com o banco de dados:

     ```properties
     # Configurações do Banco de Dados
     database.driver=com.mysql.cj.jdbc.Driver
     database.url=jdbc:mysql://localhost:3306/hospital_db?useSSL=false&serverTimezone=UTC
     database.username=hospital_user
     database.password=sua_senha

     # Configurações de Log
     logging.level.root=INFO
     logging.file.name=logs/application.log
     ```

   - **Nota**: Se estiver usando PostgreSQL, ajuste o driver e a URL adequadamente.

### 6.3.5 Executando Scripts SQL

1. **Criar as Tabelas**:
   - No terminal, execute:

     ```bash
     mysql -u hospital_user -p hospital_db < sql/schema.sql
     ```

2. **Inserir Dados Iniciais**:
   - Execute:

     ```bash
     mysql -u hospital_user -p hospital_db < sql/data.sql
     ```

## 6.4 Compilação e Execução do Projeto

### 6.4.1 Compilação com Maven

1. **Limpar e Compilar**:
   - No diretório raiz do projeto, execute:

     ```bash
     mvn clean compile
     ```

   - Isso limpará quaisquer arquivos antigos e compilará o projeto.

### 6.4.2 Executando a Aplicação

1. **Via Maven**:
   - Execute:

     ```bash
     mvn exec:java -Dexec.mainClass="com.hospital.Main"
     ```

2. **Via IDE**:
   - Localize a classe `Main.java` em `src/main/java/com/hospital/Main.java`.
   - Clique com o botão direito e selecione "Run" ou "Debug".

### 6.4.3 Executando Testes

1. **Executar Todos os Testes**:
   - No terminal, execute:

     ```bash
     mvn test
     ```

2. **Visualizar Relatórios de Teste**:
   - Os relatórios são gerados em `target/surefire-reports`.

## 6.5 Executando a Aplicação Web (Se Aplicável)

Se o sistema inclui uma interface web, siga as etapas abaixo:

### 6.5.1 Configuração do Servidor de Aplicação

1. **Apache Tomcat**:
   - Baixe o Tomcat em [https://tomcat.apache.org/download-90.cgi](https://tomcat.apache.org/download-90.cgi).
   - Extraia e configure o Tomcat.

2. **Deploy da Aplicação**:
   - Empacote a aplicação como um arquivo WAR:

     ```bash
     mvn clean package
     ```

   - O arquivo WAR será gerado em `target/hospital-management-system.war`.
   - Copie o WAR para o diretório `webapps/` do Tomcat.

3. **Iniciar o Tomcat**:
   - Navegue até o diretório `bin/` do Tomcat e execute:

     - No Windows:

       ```bash
       startup.bat
       ```

     - No Linux/macOS:

       ```bash
       ./startup.sh
       ```

4. **Acessar a Aplicação**:
   - Abra o navegador e acesse `http://localhost:8080/hospital-management-system`.

## 6.6 Dicas para Solução de Problemas

### 6.6.1 Erros de Conexão com o Banco de Dados

- **Mensagem de Erro**: `Communications link failure` ou `Access denied for user`.

- **Soluções**:
  - Verifique se o banco de dados está em execução.
  - Confirme se as credenciais no `application.properties` estão corretas.
  - Teste a conexão com o banco de dados usando um cliente SQL.

### 6.6.2 Erros de Compilação

- **Mensagem de Erro**: `Symbol not found` ou `Cannot find symbol`.

- **Soluções**:
  - Certifique-se de que todas as dependências foram baixadas com `mvn clean install`.
  - Verifique se a versão do JDK é compatível.
  - Limpe o cache do Maven excluindo a pasta `.m2/repository`.

### 6.6.3 Problemas com Dependências

- **Mensagem de Erro**: `Could not resolve dependencies`.

- **Soluções**:
  - Verifique sua conexão com a internet.
  - Execute `mvn dependency:resolve` para forçar a resolução das dependências.

### 6.6.4 Porta em Uso

- **Mensagem de Erro**: `Address already in use`.

- **Soluções**:
  - Verifique se outra instância do servidor está usando a mesma porta.
  - Altere a porta padrão do Tomcat no arquivo `server.xml`.

## 6.7 Considerações de Segurança

- **Credenciais Sensíveis**:
  - Não compartilhe o arquivo `application.properties` com credenciais reais em repositórios públicos.
  - Utilize variáveis de ambiente ou ferramentas de gerenciamento de configurações seguras.

- **Atualizações de Segurança**:
  - Mantenha as dependências atualizadas para proteger contra vulnerabilidades conhecidas.

## 6.8 Documentação e Recursos Adicionais

### 6.8.1 Gerando a Documentação do Código

- **Javadoc**:
  - Execute:

    ```bash
    mvn javadoc:javadoc
    ```

  - A documentação será gerada em `target/site/apidocs`.

### 6.8.2 Manual do Usuário

- **Localização**:
  - O manual do usuário está disponível em `docs/manual_usuario.pdf`.

- **Conteúdo**:
  - Instruções detalhadas sobre como utilizar cada funcionalidade do sistema.

### 6.8.3 Diagramas e Modelos

- **Diagrama de Classes**:
  - Disponível em `docs/diagrama_classes.png`.

- **Diagramas de Fluxo**:
  - Incluídos na documentação para auxiliar na compreensão dos processos.

## 6.9 Contribuindo para o Projeto

### 6.9.1 Configuração do Ambiente de Desenvolvimento

- **Branching Model**:
  - Utilize o modelo de branching GitFlow para organizar o desenvolvimento.

- **Padrões de Código**:
  - Siga as convenções de código Java e as diretrizes do projeto.

### 6.9.2 Enviando Pull Requests

1. **Fork do Repositório**:
   - Faça um fork do projeto no GitHub.

2. **Criar uma Branch**:
   - Para novas funcionalidades:

     ```bash
     git checkout -b feature/nome-da-feature
     ```

   - Para correções de bugs:

     ```bash
     git checkout -b bugfix/nome-do-bug
     ```

3. **Commit e Push**:
   - Faça commits claros e descritivos.
   - Envie as mudanças para o seu fork:

     ```bash
     git push origin nome-da-branch
     ```

4. **Pull Request**:
   - Abra um pull request no repositório original, descrevendo as alterações feitas.

### 6.9.3 Relatando Problemas

- **Issues no GitHub**:
  - Abra uma nova issue com detalhes sobre o problema encontrado.
  - Inclua passos para reproduzir o erro, mensagens de erro e ambiente.

## 6.10 Boas Práticas

### 6.10.1 Manutenção do Código

- **Comentários**:
  - Comente partes complexas do código para facilitar a compreensão.

- **Refatoração**:
  - Refatore o código quando necessário para melhorar a legibilidade e desempenho.

### 6.10.2 Testes

- **Cobertura de Testes**:
  - Escreva testes unitários para novas funcionalidades.
  - Utilize ferramentas como o JaCoCo para medir a cobertura.

### 6.10.3 Logs e Monitoramento

- **Níveis de Log**:
  - Ajuste os níveis de log no `log4j2.xml` conforme necessário (INFO, DEBUG, ERROR).

- **Análise de Logs**:
  - Monitore os arquivos de log para identificar e resolver problemas rapidamente.

## 6.11 Atualizações Futuras

- **Planejamento**:
  - Consulte o roadmap do projeto para entender as funcionalidades planejadas.

- **Participação**:
  - Participe das discussões e contribua com ideias para aprimorar o sistema.

---

Seguindo estas instruções, você deverá ser capaz de configurar e executar o **Sistema de Gerenciamento de Clínicas e Hospitais** com sucesso em seu ambiente de desenvolvimento. Se tiver alguma dúvida ou enfrentar problemas, não hesite em buscar assistência através dos canais de suporte do projeto.

Boa sorte e bom desenvolvimento!
