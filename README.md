[![CircleCI](https://circleci.com/gh/fkshiba/NYTimes.svg?style=svg)](https://circleci.com/gh/fkshiba/NYTimes)

# NYTimes
Aplicativo android que consome API de artigos do NY Times

## Descrição da Solução

Desenvolveu-se uma aplicação Android e a linguagem escolhida para o desenvolvimento foi o Java.

Para o client Android foi escolhido o padrão de arquitetura Model - View - ViewModel (MVVM) utilizando-se o padrão Observer/Subscriber para a comunicação entre as camadas. O MVVM provê uma maior separação das responsabilidades do projeto através de três camadas principais. Este desacoplamento visa deixar o projeto mais ortogonal. Trazendo assim, mais facilidade para a manutenção e para os testes.
Ao contrário do MVP, muito famoso na comunidade Android, no MVVM a camada de lógica de apresentação não precisa conhecer a camada de apresentação.

Na API de apresentação dos artigos mais populares não havia suporte a paginação por parte do backend. Sendo assim, o filtro pelos 10 primeiros artigos foi feito pela aplicação; 

### Bibliotecas

* Android Support Library
* Android Architecture Components (ViewModel)
* Retrofit 2
* RxJava 2
* RxAndroid
* RxRelay
* Picasso
* Mockito

## TODO

* Melhorias na UI da aplicação
* Teste da camada de API
* Testes de UI
* UI Reativa
* CD / Fastlane

