# itexto-java-mautic
Biblioteca de integração com o Mautic desenvolvida pela itexto

## Introdução

Esta biblioteca ainda é um trabalho inicial desenvolvido pela itexto com o objetivo de, a médio e longo prazo, termos uma opção real de integração com o Mautic usando a plataforma Java.

Este é o primeiro release público da biblioteca, e deve ser usado por sua própria conta e risco.

## Autenticação com o Mautic

O primeiro release desta biblioteca se baseia na autenticação do tipo Basic HTTP do Mautic.
Futuras versões irão incluir suporte ao protocolo OAuth2, provido pela ferramenta.

## Ambiente de desenvolvimento

No diretório docs você encontrará um arquivo do Docker Compose que inicia todo o ambiente de desenvolvimento contra o
qual você deverá realizar os seus testes, no caso, envolvendo uma instalaço limpa do Mautic.

Após executar o comando docker-compose up nesta pasta, acesse o endereço http://localhost:8080 e configure sua instalação
do Mautic.

Crie seu usuário administrador e, na sequência, lembre-se de habilitar a API de sua instalação do Mautic e também 
a autenticação do tipo Basic HTTP, que é a única suportada neste primeiro release da biblioteca.
