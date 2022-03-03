# Инструкция для запуска автотестов
На компьютере у вас должен быть установлен **Docker Desktop** и **IntelliJ IDEA Ultimate**.

+ Открываем проект, который склонировали к себе на компьютер в IntelliJ IDEA Ultimate.
+ В терминале запускаем команду: docker-compose up -d.
+ Запускаем программу командой: java -jar aqa-shop.jar. 
+ Двойное нажатие по клавиже Ctrl.
+ В открывшемся терминале вводим команду: gradlew clean test.
+ Для генерации отчёта: двойное нажатие по клавиже Ctrl — вводим команду gradlew allureReport.

## Документация: 
+ [План автоматизации тестирования](https://github.com/Chebykina-Xenia/BuyingTour/blob/master/docs/Plan.md)
+ [Отчёт по ручному тестированию](https://github.com/Chebykina-Xenia/BuyingTour/blob/master/docs/Report.md)
+ [Итоги автоматизированного тестирования](https://github.com/Chebykina-Xenia/BuyingTour/blob/master/docs/Summary.md)

