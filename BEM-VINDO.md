## Leia antes de começar o desafio ##  
- Crie uma branch com seu nome e commit suas alterações nela;
- Abaixo, disponibilizamos um vídeo curto explicando o desafio descrito abaixo: https://drive.google.com/file/d/1tOqjKcx4sxe8d_2S7_KfQM2VxU5Z5WZd/view?usp=sharing
- Quando finalizar o projeto, execute o comando mvn clean, compacte a pasta e compartilhe um link Google Drive com o desafio para avaliação (compartilhe o link respondendo a última questão do nosso questionário vinculado a essas instruções);
- Além do projeto em zip, mande também o seu perfil do Github e do linkedin (Obs.: não enviar o projeto para o Github e pode informar esses dados na mesma pergunta onde irá compartilhar o arquivo).
- Procuramos seguir boas práticas de programação. Sendo assim, mesmo que seja uma aplicação simples, aplique todas as boas práticas que você usaria em um sistema real. Aplique Clean Code e princípios SOLID;
- Seja objetivo no código, não precisamos que você mostre tudo que sabe mas que faça bem feito o que desenvolver;
- *Utilizar a versão do java já existente no projeto. Quando falamos no vídeo para usar java 8, é apenas para indicar a utilização de Optional, Lambda, Streams e afins. Não mudar a versão do java do projeto.* 

## Desafio ## 
Imagine que um desenvolvedor da sua equipe está saindo de férias e deixou algumas "buchas" pra você resolver...

Ele te mandou um e-mail com o seguinte texto:

**Bugs**:

1) O DBA está reportando que os itens do pedido de compras estão sendo criados no banco com a coluna de pedido_id null. Precisamos resolver.

2) Cliente está reclamando que não consegue excluir pedido. Devemos corrigir, porque o cliente pode remover um pedido.

3) Quando buscamos todos os pedidos, muitas querys estão sendo executadas no banco de produção. 
   Ao rodar o teste "PedidoControllerTest.listarTodosOsPedidos" 7 queries são executadas, sendo que temos somente 6 pedidos no nosso script de inserts.
Precisamos corrigir este problema ao buscar todos os pedidos, mas se possível, não afetarmos a boa perfomance de busca de somente um pedido.
Se não tiver jeito, vamos priorizar corrigir esta questão de muitas querys!
	(Hoje em dia quando buscamos somente um pedido, não vem os itens, o que é bom, porque não precisamos dos itens sempre que buscarmos um único pedido).

4) De vez em quando, por algum motivo de integração, estamos criando um pedido sem movimentar o estoque! Isso não pode acontecer. Caso dê algum problema na hora de movimentar o estoque não devemos criar o pedido!
	Obs: Por favor, sem apagar o código que simula a falha ou fazer algo como (  if(!simularFalha) movimentoEstoqueService.movimentarEstoquePedido }. Finja que este boolean não existe.
	Para simular este problema basta no json de pedido colocar o atributo simularFalha como true

**Melhoria pedida pelo analista**:

Não devemos criar um pedido com mais de 50 itens. Não cabe no caminhão! Por favor, limitar um pedido para no máximo 50 itens.

**Melhorias no código**:

1) Um desenvolvedor mais sênior comentou que existe uma forma mais fácil de saber a quantidade movimentada, na classe MovimentoEstoqueService utilizando o java 8. Se souber, me ajuda.

2) Por favor, revise as classes Pedido, PedidoController e PedidoService e me dê sugestões de melhoria, mas se quiser já implementar fique a vontade.

3) Um desenvolvedor mais sênior revisou o nosso código e disse que a classe Pedido não está encapsulando corretamente os itens. Não entendi o que ele quis dizer com isso. Se entender, corrigir por favor =D

**Teste de unidade**:

1) Criar o corpo do método "agruparPedidoPorFormaDePagamento" do PedidoService e seu respectivo teste de unidade. **É essencial que você crie o teste de unidade desse método.** 

## Para testar o projeto ##
Ao subir o projeto a url do mesmo fica em: localhost:8080/pedidos

Perceba que temos um script com alguns inserts, em "data.sql" que o Spring Boot irá executar automáticamente ao subir a aplicação.

Abaixo deixo o JSON que uso para criar um pedido, caso queria testar pelo Postman ou similar. Porém deixamos uma classe de teste, de integração, para facilitar.
```
{
	"retiradaNaLoja": true,
	"formaPagamento": "CREDITO",
	"simularFalha": false,
	"itens" : [{
		"descricaoProduto": "Coca-Cola",
		"quantidade": 1000
	}, {
		"descricaoProduto": "Lasanha-congelada",
		"quantidade": 3
	}, {
		"descricaoProduto": "Água",
		"quantidade": 3
	}, {
		"descricaoProduto": "Banana",
		"quantidade": 3
	}, {
		"descricaoProduto": "Maça",
		"quantidade": 3
	}, {
		"descricaoProduto": "Veja multi uso",
		"quantidade": 3
	}, {
		"descricaoProduto": "Cebola",
		"quantidade": 3
	}, {
		"descricaoProduto": "Alho",
		"quantidade": 3
	}]
}
```

```
Para verificar o banco, acesse a url http://localhost:8080/h2-console com os seguintes parametros:

Driver class: org.h2.Driver
JDBC url: jdbc:h2:mem:testdb
User name: sa
Password:
```
<img width="594" alt="image" src="https://user-images.githubusercontent.com/386430/185471341-d12b90ba-0eaf-4ec0-93e8-b31a5b4b3084.png">
