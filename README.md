# Controle de portaria com JAVA
Essa é uma aplicação desktop que utiliza duas tecnologias consolidadas no mercado, sendo elas JAVA e MYSQL.
Foi utilizado a IDE APACHE NETBEANS 18 e o gerenciador de banco de dados WORKBENCH 8.0 CE. A escolha do netbeans foi dada pelo fato de netbeans ja trazer LIB como SWING para o agilidade no desenvolvimento de frames e customização de frames através de herança,
já WORKBENCH facilita visualmente em testes e trás a opção de menos código.
## Quais métodos foram utilizados
Em JAVA na versão 20 foi utilizado o paradigma de orientação a objetos assim como métodos DTO DAO para a comunicação entre backend e frontend o qual tem interação com usuários, no frontend foi utilizado a LIB SWING para o uso de campos para serem preenchidos e os mesmo com validação, sim, do lado do cliente temos essa validação para não ter inconsistência de dados no backend, no backend utilizamos WORKBENCH para o gerenciamento dos dados e para conectar o backend e o frontend utilizamos a conexão jdbc com conector .jar na IDE APACHE NETBEANS para utilizar o mysql 8 que estará rodando como serviço no OS.
### Requisitos para funcionar 
Como já foi mencionado foi utilizado a ide apache netbeans 18 com java você utilizará a ide para gerar a BUILD do projeto antes certifique que a janela de login está escolhida como a principal para inicializar a aplicação, você terá que utilizarar workbench com mysql 8 ou mysql cli sem a necessidade de interface gráfica 
#### Instalação
Após gerar a build do projeto é só copiar a pasta gerada chamada de DIST da pasta do projet e dentro dela terá um arquivo .jar com o java devidamente instalado
é só executar o workbench criar os dados de login que será utilizado para validar o usuário e executar o jar

##### Imagens do projeto
![login](https://github.com/LeandroMeca/-concierge-control/assets/83671192/0ebc34e0-da83-4de2-93ae-ec43ed03e586)
![menu](https://github.com/LeandroMeca/-concierge-control/assets/83671192/dda2fdee-704e-4e1a-b4d7-f1faa4b58c22)
![database](https://github.com/LeandroMeca/-concierge-control/assets/83671192/3bfd85e7-f6bc-4ea7-a4b6-1296ae4e24c4)
![login2](https://github.com/LeandroMeca/-concierge-control/assets/83671192/eb0ac1b5-62f8-4225-98a9-f207d6ffff19)
![database2](https://github.com/LeandroMeca/-concierge-control/assets/83671192/cf9a48eb-6e96-48aa-ae4f-301560f21465)
