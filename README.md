# API Blog Java

Tecnologias/Frameworks utilizados:

- Java 8
- Spring Boot
- Spring Data
- Spring Security (Para implementação do token JWT)
- Apache Maven
- AWS S3 (Para armazenamento das fotos)
- PostgresSQL 11

## Iniciando a aplicação 
- Para iniciar a aplicação de forma local é necessário ter uma instância do postgres 11 rodando na máquina e ter um banco de dados já criado de nome "db_appl_blog" para que a aplicação possa se conectar. Toda a estrutura de DDL é criada automaticamente na primeira vez que se iniciar a aplicação.

- Tendo criado o banco e revisado as credenciais no arquivo "application-dev.properties" colocando-as de acordo com as da
instância local do postgres, basta digitar o comando abaixo no terminal dentro da pasta "artefatos" do repositório:

```sh
java -jar blog-java.jar
```

## Funcionamento Básico
- No repositório existe uma collection do Postman com todos os endpoints apontados e já configurados.
- Existe o app Swagger da aplicação disponível em localhost:8080/appl/swagger-ui/index.html
- No Swagger é possível ver detalhadamente como cada endpoint está configurado, payloads e respostas.
- Ao criar uma nova foto o arquivo deve ser convertido para base64 e enviado no json como mostrado no json abaixo:

```json
    {
    "name":"Evento 6",
    "date":"2021-12-31 10:00:00",
    "albumId":"3",
    "data":"/9j/4AAQSkZJRgY1ul17pqhOVvhld4t7NHqzoqvhSLDfLDKU [...]"
	}
```
- Obs: Ao converter uma imagem para o formato base64 colar todo o conteúdo no campo "data" do json para o correto funcionamento.
- Para logar e gerar um token válido basta executar a requisição "Auth" na collection que vai ser gerado um token com o usuário admin para ser utilizado como Bearer Token nas outras requisições.
- Através deste token é possível criar outros usuários



