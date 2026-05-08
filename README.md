## **🚀 VAREJO API - PROJETO**
Gerenciamento de Inventário, Prevenção de Ruptura e Inteligência de Reposição

---

## 📑 **Sobre o Projeto**
O **Varejo API** é uma solução de alta performance baseada em **Microsserviços** desenvolvida para resolver o maior gargalo do varejo moderno: a **falta de visibilidade de estoque**. Através de uma arquitetura **Event-Driven (EDA)**, o sistema garante que o dado circule entre o catálogo e o estoque com baixa latência e alta consistência.

O grande diferencial deste projeto é a **Gestão Preditiva**: o sistema não apenas conta produtos, mas analisa dados logísticos como **Lead Time** (tempo de entrega) e **Shelf Life** (validade) para evitar que a gôndola fique vazia ou que produtos vençam no depósito.

---

## 🏗️ **Arquitetura de Microsserviços**
Cada serviço possui seu próprio banco de dados **PostgreSQL**, garantindo o isolamento (**Database per Service**) e a resiliência do ecossistema.



### 1. **Catalog Service** 📦
Responsável pelo **Master Data** dos produtos.
* **Entidades:** `Product` e `SupplyDetails` (@Embedded).
* **Regras:** Centraliza SKU, Categoria, Preços e Parâmetros Logísticos.
* **Mensageria:** Publica no tópico `catalog-events` sempre que um item novo é cadastrado.

### 2. **Inventory Service** 🏪
O motor **transacional** e de auditoria do sistema.
* **Entidades:** `Stock` (Saldo Atual) e `StockMovement` (Histórico de Movimentação).
* **Estratégia:** Separação física entre **Gôndola** (disponível para venda) e **Depósito** (armazenagem interna).

---

## 🛠️ **Tech Stack**

* **Linguagem:** `Java 21` (LTS)
* **Framework:** `Spring Boot 3.2.x`
* **Persistência:** `Spring Data JPA` + `PostgreSQL`
* **Mensageria:** `Apache Kafka` (Comunicação Assíncrona)
* **Integração:** `Spring Cloud OpenFeign` (Comunicação Síncrona)
* **Produtividade:** `Lombok`, `Jakarta Validation`
* **Infraestrutura:** `Docker` & `Docker Compose`

---

## 📡 **Fluxo de Eventos (Kafka)**

O sistema utiliza o padrão de **Event-Carried State Transfer** para reduzir o acoplamento:

| Tópico | Ação (Action) | Destinatário | Propósito |
| :--- | :--- | :--- | :--- |
| `catalog-events` | **PRODUCT_CREATED** | Inventory Service | **Inicializar** estoque zero para o novo SKU. |
| `inventory-events` | **LOW_STOCK** | Intelligence Service | **Alertar** risco de ruptura iminente. |
| `sales-events` | **SALE_COMPLETED** | Inventory Service | **Baixar** saldo real e registrar movimentação. |
| `loss-events` | **LOSS_REGISTERED** | Inventory Service | **Ajustar** perdas (quebras/vencimentos). |

---

## 📋 **Endpoints Principais**

### **Inventory Service**

#### 🔹 **StockController** (Gestão de Saldo)
* `POST /api/v1/stock` ➡️ **Cria** o registro inicial de estoque para um produto.
* `GET /api/v1/stock/{productId}` ➡️ **Consulta** saldo detalhado (Gôndola vs Depósito).
* `PATCH /api/v1/stock/{productId}/safety-stock` ➡️ **Ajusta** o estoque mínimo de segurança.

#### 🔹 **StockMovementController** (Rastreabilidade)
* `POST /api/v1/movements/{stockId}/entry` ➡️ **Registra** entrada de mercadoria vinda do fornecedor.
* `POST /api/v1/movements/{stockId}/loss` ➡️ **Registra** perda de mercadoria.
* `POST /api/v1/movements/{stockId}/transfer` ➡️ **Reposição** interna (Depósito ➡️ Gôndola).
* `GET /api/v1/movements/{productId}` ➡️ **Extrato** completo de auditoria para fins fiscais e gerenciais.

---

## 🧠 **Regras de Negócio de Ouro**

1.  **Auditoria Imutável:** Nenhuma quantidade no `Stock` é alterada sem a criação de um respectivo registro no `StockMovement`.
2.  **Ruptura Zero:** O sistema dispara alertas automáticos sempre que o `TotalStock` atinge o `MinimumSafetyStock`.
3.  **Segurança de Transação:** Ao mover itens para a gôndola, o sistema valida a disponibilidade no depósito de forma atômica, evitando inconsistências.

---

## 🚀 **Como Executar o Projeto**

```bash
# 1. Clone o repositório
git clone [https://github.com/eubrunoo07/smart-retail.git](https://github.com/eubrunoo07/smart-retail.git)

# 2. Suba a Infraestrutura (PostgreSQL e Kafka)
docker-compose up -d

# 3. Execute o microsserviço (Exemplo: Inventory)
cd inventory-service
./mvnw spring-boot:run
