# Controle de portaria com JAVA
Essa é uma aplicação desktop que utiliza duas tecnologia consolidada no mercado sendo elas JAVA e MYSQL
foi utilizado a IDE APACHE NETBEANS 18 e o gerenciador de banco de dados WORKBENCH 8.0 CE, a escolha do netbeans foi dada pelo 
fato de netbeans ja trazer LIB como SWING para o agilidade no desenvolvimento de frames e customização de frames através de herança,
já WORKBENCH facilita visualmente em testes e trás a opção de menos código.
## Quais métodos foram utilizados
Em JAVA na versão 20 foi utilizado o paradigma de orientaçao a objetos assim como métodos DTO DAO para a comunicação entre backend e
frontend o qual tem interação com usuario, no frontend foi utilizado a LIB SWING para o uso de campos para serem preenchidos e os 
mesmo com validação, sim do lado do cliente temos essa validação para não ter inconsistência de dados no backend, no backend 
utilizamos WORKBENCH para o gerenciamento dos dados e para conectar o backend e o frontend utilizamos a conexão jdbc com conector .jar
na IDE APACHE NETBEANS para utilizar o mysql 8 que estará rodando como serviço no OS.
### Requisitos para funcionar 
Como já foi mencionado foi utilizado a ide apache netbeans 18 com java você utilizará a ide para gerar a BUILD do projeto antes certifique que a janela de login está escolhida como a principal para inicializar a aplicação, você terá que utilizarar workbench com mysql 8 ou mysql cli sem a nescessidade de interface gráfica 
#### Instalação
Após gerar a build do projeto é só copiar a pasta gerada chamada de DIST da pasta do projet e dentro dela terá um arquivo .jar com o java devidamente instalado
é só executar o workbench criar os dados de login que será utilizado para validar o usuário e executar o jar
