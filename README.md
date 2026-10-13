# Sistema de Agendamento de Transferências Financeiras (BFF)

Aplicação Back-End desenvolvida para o cadastro, cálculo automatizado de taxas e listagem de extratos de transferências financeiras entre contas de origem e destino, utilizando arquitetura RESTful e banco de dados em memória.

---

## 🛠️ Ferramentas e Versões Utilizadas

O ambiente de desenvolvimento e execução foi configurado e validado com as seguintes ferramentas e versões:

* **Java Development Kit (JDK):** Versão 11.0.32
* **Apache Maven:** Versão 3.10.0
* **IDE Eclipse:** Versão 2026-09 / 4.41.0 (Recomendado para o desenvolvimento do Backend)
* **Spring Boot:** Versão 2.7.18
* **Banco de Dados:** H2 Database (Em memória)

---

## 🏛️ Decisões Arquiteturais e Técnicas

* **Arquitetura em Camadas (Back-End):** O back-end foi estruturado seguindo o padrão RESTful em camadas (`Controller`, `Service`, `Repository` e `Entity`). Essa separação reduz o acoplamento, centraliza as regras de negócio e facilita a testabilidade unitária.
* **Isolamento da Regra de Negócio:** A lógica complexa de cálculo de taxas foi isolada no componente `CalculadoraTaxasService`, permitindo manutenção simplificada e validação isolada por testes unitários com JUnit 4.
* **Validação Rigorosa de Dados:** Utilização do `Bean Validation` no Spring Boot (`javax.validation`) e de `Reactive Forms` no Angular para garantir que contas de origem e destino possuam exatamente 10 dígitos e que valores monetários sejam válidos.
* **Tratamento Global de Exceções:** Implementação de um `@RestControllerAdvice` para interceptar e padronizar códigos de erro HTTP (`400 Bad Request` para falhas de validação e `422 Unprocessable Entity` para regras de negócio não atendidas, como prazos superiores a 50 dias).

---

## 📊 Tabela de Taxas Aplicáveis

O cálculo da taxa de agendamento varia conforme a diferença em dias entre a data de cadastro e a data da transferência:

| Dias de Antecedência | Taxa Fixa | Percentual Aplicado | Fórmula de Cálculo |
| :--- | :--- | :--- | :--- |
| **0 (Mesmo dia)** | R$ 3,00 | 2,5% | R$ 3,00 + (Valor $\times$ 0,025) |
| **1 a 10 dias** | R$ 12,00 | 0% | R$ 12,00 fixos |
| **11 a 20 dias** | R$ 0,00 | 8,2% | Valor $\times$ 0,082 |
| **21 a 30 dias** | R$ 0,00 | 6,9% | Valor $\times$ 0,069 |
| **31 a 40 dias** | R$ 0,00 | 4,7% | Valor $\times$ 0,047 |
| **41 a 50 dias** | R$ 0,00 | 1,7% | Valor $\times$ 0,017 |
| **> 50 dias** | - | - | **Bloqueado** (Sem taxa aplicável) |

---

## 📁 Estrutura do Repositório

```text
transferencias-app-bff/
├── backend/
│   ├── pom.xml
│   └── src/main/java/br/com/transferenciasapp/
│       ├── controller/
│       ├── dto/
│       ├── entity/
│       ├── exception/
│       ├── repository/
│       └── service/
└── README.md

🔌 Contrato da API REST
A API expõe recursos RESTful na porta 8080 com suporte a CORS habilitado para o frontend Angular (http://localhost:4200).

1. Cadastrar Agendamento de Transferência
Método e Endpoint: POST /v1/transferencias/agendar

Descrição: Realiza a validação dos dados de entrada, calcula a taxa de acordo com os dias de antecedência, persiste o agendamento no banco H2 e retorna o registro gerado.

📥 Payload de Requisição (JSON)

{
  "contaOrigem": "1234567890",
  "contaDestino": "0987654321",
  "valor": 1000.00,
  "dataTransferencia": "2026-10-20"
}

📋 Descrição dos Campos de Entrada

Campo,Tipo,Validação / Regra,Descrição
contaOrigem,String,Obrigatório / Exatamente 10 dígitos (^\d{10}$),Número da conta de origem
contaDestino,String,Obrigatório / Exatamente 10 dígitos (^\d{10}$),Número da conta de destino
valor,BigDecimal,Obrigatório / Maior que zero (> 0.01),Valor monetário da transferência
dataTransferencia,LocalDate,Obrigatório / Formato AAAA-MM-DD (Data atual ou futura),Data planejada para a transferência

📤 Respostas HTTP
201 Created (Sucesso): Retorna o objeto completo criado com o cálculo de taxas aplicado.

{
  "id": 1,
  "contaOrigem": "1234567890",
  "contaDestino": "0987654321",
  "valor": 1000.00,
  "taxa": 12.00,
  "valorTotal": 1012.00,
  "dataTransferencia": "2026-10-20",
  "dataAgendamento": "2026-10-12"
}

400 Bad Request (Erro de Validação): Retornado quando há falhas nas anotações de Bean Validation (ex: contas com menos de 10 dígitos ou valor nulo/negativo).

422 Unprocessable Entity (Erro de Regra de Negócio): Retornado quando a regra de negócio rejeita a operação (ex: agendamento superior a 50 dias ou data no passado).

2. Listar Extrato de Transferências
Método e Endpoint: GET /v1/transferencias/listar

Descrição: Retorna a listagem completa de todas as transferências agendadas e persistidas no banco H2, ordenadas de forma decrescente pela data de agendamento e pelo identificador (id).

📤 Resposta HTTP (200 OK)
Retorna uma matriz JSON contendo os registros de transferências:

[
  {
    "id": 2,
    "contaOrigem": "1111111111",
    "contaDestino": "2222222222",
    "valor": 500.00,
    "taxa": 3.00,
    "valorTotal": 503.00,
    "dataTransferencia": "2026-10-12",
    "dataAgendamento": "2026-10-12"
  },
  {
    "id": 1,
    "contaOrigem": "1234567890",
    "contaDestino": "0987654321",
    "valor": 1000.00,
    "taxa": 12.00,
    "valorTotal": 1012.00,
    "dataTransferencia": "2026-10-20",
    "dataAgendamento": "2026-10-12"
  }
]
