# verificate_fingerprint

verificate_finger
Сборка: gradle kt


### Логика: с фронта приходит одно изображение и с сервера несколько если все совпадает то приходит сообщение success а если нет то error.


##### > Инициализируйте [Spring проект](https://start.spring.io/)
##### >> Склонируйте проект в Spring проект
##### >>> Настройте Структуру проекта - File -> Project Structure -> Project


## Настройка проекта описана в файле application.properties

![alt text](https://www.mdpi.com/sensors/sensors-15-07807/article_deploy/html/images/sensors-15-07807-g008.png)

## Убедитесь что пути проставлены правильно
```java
dataSource.setUrl("jdbc:sqlite:C:/Users/rikitwiki/IdeaProjects/verificate_fingerprint/fingerprints.db"); // Set the path to your SQLite file here
```
