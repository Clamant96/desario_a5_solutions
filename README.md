# DESAFIO A5 Solutions

## INCIALIZANDO PROJETO
- Requisitos do sistema: 
- Java 8
- MySql
- Maven

## FONFIGURACAO DE SENHA DE BANCO
- Ao baixar o projeto, importe em formato Maven.
- Abra o projeto e dentro da pasta src/main/resources/application.properties.
- Abrir o arquivo 'application.properties', e dentro da TAG: 'SENHA-BANCO', substitua pela senha de seu banco local.
- Agora execute o projeto normalmente

## ENDPOINTS
- POST | CADASTRO DE NOVO USUARIO: http://localhost:8080/usuarios/cadastrar
{
    "usuario": "Clamant",
    "senha": "123456"
}
- POST | LOGIN: http://localhost:8080/usuarios/usuario
{
    "usuario": "Clamant",
    "senha": "123456"
}
- POST| CADASTRO DE UM NOVO FILME: http://localhost:8080/locadora
{
    "titulo": "Godzilla",
    "diretor": "Gareth Edwards",
    "estoque": "5"
}
- GET | ALUGAR FILME: http://localhost:8080/locadora/produto_pedido/{token}/{titulo}


