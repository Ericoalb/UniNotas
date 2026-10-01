# CRUD UniNotas - Etapa 1

Este projeto implementa um CRUD básico para a entidade `Faculdade` utilizando Java nativo (`com.sun.net.httpserver`). Os dados são armazenados temporariamente em memória (usando Coleções), cumprindo os requisitos da atividade de não utilizar banco de dados ou frameworks externos, como o Spring Boot.

## Arquitetura e Funcionamento

O projeto foi estruturado seguindo o padrão em três camadas para separar responsabilidades:

* **Controller (`FaculdadeController`):** Camada de entrada. É responsável por abrir o servidor HTTP, receber as requisições (GET, POST, PUT, DELETE) vindas do Postman, extrair os dados em formato JSON e enviá-los para a camada de serviço.
* **Service (`FaculdadeService`):** Camada de regras de negócio. Aplica a lógica da aplicação, servindo como uma ponte inteligente. Ela recebe os comandos do Controller, processa o que for necessário e solicita ao Repository que altere os dados.
* **Repository (`FaculdadeRepository`):** Camada de dados. Gerencia o acesso e a persistência das informações. Neste projeto, opera salvando, buscando e excluindo os objetos `Faculdade` diretamente de um `ArrayList` em memória.

---

## Pré-requisitos
* **Java JDK** instalado.
* **Postman** (ou Insomnia) para a realização dos testes das requisições HTTP.
* Uma IDE (como Eclipse, IntelliJ ou VS Code) para compilar e executar o código.

---

## Como Executar
1. Faça o clone deste repositório na sua máquina local:
   `git clone https://github.com/Ericoalb/UniNotas`
2. Abra a pasta raiz do projeto na sua IDE.
3. Navegue até o arquivo `Main.java` e execute-o (opção *Run*).
4. O servidor será iniciado e exibirá a mensagem `"Servidor rodando!"` no terminal. A API estará pronta para receber requisições na porta `8080`.

---

## Testes no Postman (Comandos CRUD)

**Atenção ao formato:** O projeto utiliza um leitor de texto simplificado para processar as requisições. Para que os testes funcionem perfeitamente, certifique-se de configurar o Postman para a aba **Body ➔ raw ➔ JSON** e de enviar as chaves estritamente na ordem e formato indicados abaixo.

### 1. Cadastrar Faculdade
* **Método:** `POST`
* **URL:** `http://localhost:8080/faculdades`
* **Body:**
```json
{
  "name": "nome",
  "cnpj": "0000000",
  "id": 0
}

2. Listar Faculdades
Método: GET

URL: http://localhost:8080/faculdades

Body: (Deixe a aba Body vazia / none)

3. Atualizar Faculdade
Método: PUT

URL: http://localhost:8080/faculdades

Body: (Mantenha o ID da faculdade que você deseja alterar)

JSON
{
  "name": "UFAL - Campus Atualizado",
  "cnpj": "9999999999",
  "id": 0
}
4. Excluir Faculdade
Método: DELETE

URL: http://localhost:8080/faculdades

Body: (Envie apenas a chave ID para apagar o registro correspondente)

JSON
{
  "id": 0
}