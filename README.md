# SistemaLocacaoVeiculos

Sistema de locação de veículos desenvolvido em Java para a disciplina de Programação Orientada a Objetos (POO/DPO). O projeto possui uma interface gráfica em Swing para acesso de clientes e gerentes, cadastro e consulta de clientes e veículos e realização de locações. Os dados são acessados por meio da API REST do Supabase.

## 1. Descrição do sistema

O sistema permite:

- autenticação de clientes por CNH e senha;
- autenticação de gerentes por e-mail e senha;
- cadastro de clientes;
- consulta de clientes cadastrados pela área do gerente;
- cadastro de veículos;
- consulta e edição de veículos;
- realização de locações;
- seleção de forma de pagamento;
- cálculo do valor da locação conforme quantidade de dias;
- aplicação de desconto para pagamento via PIX;
- atualização do status do veículo após uma locação;
- consulta de veículos e de locações por meio das classes de domínio e repositórios.

A aplicação é iniciada pela classe `Main`, que abre a `TelaPrincipal`.

---

## 2. Tecnologias e estrutura utilizadas

O código presente no projeto utiliza:

- **Java 21**;
- **Java Swing** para a interface gráfica;
- **Gson 2.14.0** para serialização e desserialização JSON;
- **HTTP Client do Java** (`java.net.http`) para comunicação com a API REST;
- **Supabase** como serviço utilizado pelos repositórios para persistência dos dados;
- **Git** para controle de versão.

A estrutura principal do código está organizada nos seguintes pacotes:

```text
src/
├── config/
├── enums/
├── exception/
├── InterfaceGrafica/
├── model/
├── pagamento/
├── repository/
└── Main.java
```

---

## 3. Instruções para executar o projeto

### Pré-requisitos

É necessário ter:

1. **Java 11 ou superior**, pois o projeto utiliza recursos da API de comunicação HTTP disponíveis a partir do Java 11 para acesso ao banco de dados;
2. uma IDE Java, como o IntelliJ IDEA, ou um ambiente capaz de compilar Java;
3. acesso à configuração do Supabase utilizada pelo projeto;
4. o arquivo `lib/gson-2.14.0.jar`, que já está presente no projeto.

O projeto possui a biblioteca Gson 2.14.0 configurada no módulo `POO`.

### Execução pelo IntelliJ IDEA

1. Abra a pasta `SistemaLocacaoVeiculos` no IntelliJ IDEA.
2. Verifique se o projeto está utilizando **Java 11 ou superior**.
3. Verifique se a biblioteca `lib/gson-2.14.0.jar` está disponível no projeto.
4. Confira a classe `src/config/SupabaseConfig.java`.
5. A configuração possui a URL do projeto Supabase e uma chave `ANON_KEY`. O próprio código informa que essa chave deve ser substituída pela chave pública/anon correspondente antes do commit final.
6. Execute a classe:

```text
Main
```

A classe `Main` cria e exibe a `TelaPrincipal` por meio do `java.awt.EventQueue`.

### Compilação pela linha de comando

A partir da pasta raiz do projeto, com o JDK 21 disponível:

```bash
javac -cp lib/gson-2.14.0.jar -d out $(find src -name "*.java")
```

No Windows PowerShell, uma alternativa é utilizar:

```powershell
$arquivos = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName }
javac -cp "lib/gson-2.14.0.jar" -d out $arquivos
```

Depois, execute:

```bash
java -cp "out;lib/gson-2.14.0.jar" Main
```

A aplicação depende do acesso ao Supabase para as operações realizadas pelos repositórios. O código do projeto utiliza os endpoints REST correspondentes a `cliente`, `gerente`, `veiculo` e `locacao`.

---

## 4. Classes principais

### Camada de domínio (`model`)

| Classe | Responsabilidade observada no código |
|---|---|
| `Cliente` | Representa o cliente, armazenando id, nome, telefone, CNH, categoria da CNH, CEP e senha. |
| `Gerente` | Representa o gerente, armazenando id, nome, e-mail e senha. |
| `Veiculo` | Representa um veículo, com modelo, marca, placa, ano, tipo, status e valor diário. Também calcula o valor da locação. |
| `Carro` | Especialização de `Veiculo`. Seu construtor define `tipoVeiculo` como `"carro"`. |
| `Moto` | Especialização de `Veiculo`. Seu construtor define `tipoVeiculo` como `"moto"`. |
| `Locacao` | Representa uma locação, contendo cliente, veículo, datas, quantidade de dias, valor total e forma de pagamento. |
| `Locadora` | Centraliza regras de autenticação, cadastro, consulta, disponibilidade, validação da possibilidade de locação e realização da locação. |

### Pagamento (`pagamento`)

| Classe/interface | Responsabilidade observada no código |
|---|---|
| `FormaPagamento` | Interface que define `realizarPagamento(double valor)` e `getNome()`. |
| `PagamentoCartao` | Implementa `FormaPagamento` e mantém o valor recebido. |
| `PagamentoDinheiro` | Implementa `FormaPagamento` e mantém o valor recebido. |
| `PagamentoPix` | Implementa `FormaPagamento` e aplica desconto de 10% sobre o valor recebido. |

### Persistência (`repository`)

| Classe | Responsabilidade observada no código |
|---|---|
| `ClienteRepository` | Consulta, autentica, cadastra e edita clientes por meio da API REST. |
| `GerenteRepository` | Consulta, autentica, cadastra e edita gerentes por meio da API REST. |
| `VeiculoRepository` | Consulta, cadastra e edita veículos por meio da API REST. |
| `LocacaoRepository` | Consulta, cadastra e edita locações por meio da API REST. |

### Interface gráfica (`InterfaceGrafica`)

As principais telas são:

- `TelaPrincipal`: tela inicial de autenticação e acesso ao cadastro de cliente;
- `TelaCadastroCliente`: formulário de cadastro de cliente;
- `TelaCliente`: área do cliente, com acesso à consulta de veículos e realização de locação;
- `TelaGerente`: área do gerente, com consulta de clientes, consulta/edição de veículos e cadastro de veículos;
- `TelaVeiculos`: consulta e edição dos dados dos veículos;
- `TelaLocacao`: formulário de realização da locação;
- `TelaLoginGerente`: tela de login de gerente.

---

## 5. Regras de negócio

- Cliente entra no sistema com **CNH e senha**.
- Gerente entra no sistema com **e-mail e senha**.
- Não é permitido cadastrar cliente com **nome ou CNH vazios**, nem CNH já cadastrada.
- Não é permitido cadastrar veículo com **placa vazia** ou placa já cadastrada.
- O tipo de veículo cadastrado pela interface é **carro** ou **moto**.
- A locação exige um período com **mais de zero dias**.
- Categoria de CNH `A` permite moto, `B` permite carro e `AB` permite os tipos tratados pelo sistema.
- Só é possível alugar veículo com status **`disponivel`**.
- O valor da locação é calculado pelo **valor diário × quantidade de dias**.
- Pagamento via **PIX aplica 10% de desconto**; cartão e dinheiro mantêm o valor recebido.
- Após a locação, o veículo passa para o status **`indisponivel`**.

---

## 6. Checklist de conceitos observados no código

> O checklist abaixo foi preenchido somente com conceitos que podem ser identificados diretamente nos arquivos do projeto. Quando um conceito não aparece de forma comprovável no código, ele não é apresentado como implementado.

| Conceito | Situação | Onde aparece |
|---|---|---|
| Classe | ☑ Utilizado | `Cliente`, `Gerente`, `Veiculo`, `Locacao`, `Locadora`, repositórios e telas. |
| Objeto/instância | ☑ Utilizado | Exemplos: `new Cliente()`, `new Veiculo()`, `new Locacao()`, `new PagamentoPix()`, `new TelaPrincipal()`. |
| Atributos | ☑ Utilizado | Os modelos possuem atributos privados, como `nome`, `cnh`, `placa`, `valorDiario` e `valorTotal`. |
| Métodos | ☑ Utilizado | Getters, setters, cálculos, autenticação, cadastro, consulta e realização de locação. |
| Construtor | ☑ Utilizado | `Cliente` e `Gerente` possuem construtor sem argumentos e construtor com dados; `Veiculo`, `Locacao`, `Carro` e `Moto` também possuem construtores. |
| Sobrecarga de construtores | ☑ Utilizado | `Cliente` possui construtor vazio e construtor completo; `Gerente` possui construtor vazio e construtor completo. |
| Encapsulamento | ☑ Utilizado | Atributos dos modelos são `private` e são acessados por getters/setters. |
| Herança | ☑ Utilizado | `Carro extends Veiculo` e `Moto extends Veiculo`. As telas também utilizam classes Swing por herança, como `TelaPrincipal extends JFrame`. |
| Interface | ☑ Utilizado | `FormaPagamento` define o contrato para as formas de pagamento. |
| Implementação de interface | ☑ Utilizado | `PagamentoCartao`, `PagamentoDinheiro` e `PagamentoPix` implementam `FormaPagamento`. |
| Polimorfismo | ☑ Utilizado | `Locadora.realizarLocacao` recebe `FormaPagamento` e pode trabalhar com `PagamentoPix`, `PagamentoCartao` ou `PagamentoDinheiro`. |
| Sobrescrita (`@Override`) | ☑ Utilizado | As três classes de pagamento sobrescrevem os métodos definidos em `FormaPagamento`. |
| Abstração | ☑ Utilizado | A interface `FormaPagamento` abstrai a operação de pagamento das implementações concretas. |
| Enumeração | ☑ Utilizado | `CategoriaCNH` possui `A`, `B` e `AB`. `StatusVeiculo` possui `DISPONIVEL` e `ALUGADO`. |
| Exceção personalizada | ☑ Utilizado | `exception.LocacaoException extends Exception` é utilizada pela `Locadora` e tratada na `TelaLocacao`. |
| Tratamento de exceções | ☑ Utilizado | Existem blocos `try/catch` nos repositórios, na `Locadora` e nas telas. |
| Coleções | ☑ Utilizado | Uso de `List`, `ArrayList` e `Map` em consultas, alterações e montagem de dados. |
| Persistência/acesso a dados | ☑ Utilizado | Os quatro repositories utilizam `HttpClient` e a API REST do Supabase. |
| Serialização JSON | ☑ Utilizado | `Gson` é utilizado para `toJson` e `fromJson`. |
| Interface gráfica | ☑ Utilizado | As telas utilizam Swing (`JFrame`, `JPanel`, `JButton`, `JTable`, entre outros). |
| Separação por responsabilidades | ☑ Utilizado | O projeto separa modelos, repositórios, pagamento, exceções, enums e interface gráfica em pacotes distintos. |

Nota:
StatusVeiculo é declarado como enum, mas o atributo statusVeiculo de Veiculo é uma String. O projeto utiliza essa String porque o status é enviado e recebido pela API REST do Supabase como valor textual, com valores como "disponivel" e "indisponivel".
