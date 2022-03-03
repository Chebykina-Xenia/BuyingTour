package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.database.Database;
import ru.netology.page.CardPaymentPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyingTourTest {

    //подключаем ALLURE
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        //очищаем таблицы БД
        Database.cleanDatabase();
        //открываем сайт
        open("http://localhost:8080/");
        //кликаем по кнопке КУПИТЬ
        var mainPage = new MainPage();
        mainPage.openCardPaymentPage();
    }

    //проверяем отправку формы — операция одобрена банком (статус APPROVED)
    //тест успешно проходит
    @Test
    void sendingFormOperationApprovedTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(1, 5, 3);
        //проверяем сообщение
        cardPaymentPage.checkMessage("Успешно", "Операция одобрена Банком.");

        //проверка передачи статуса одобрено
        var statusSQL = Database.getStatus();
        //сравниваем фактический и ожидаемый результат
        assertEquals("APPROVED", statusSQL);

        //проверяем, появилась ли запись в табл
        var countSQL = Database.getCountOrderEntity();
        //сравниваем фактический и ожидаемый результат
        assertEquals("1", countSQL);
    }

    //проверяем отправку формы — операция НЕ одобрена банком (статус DECLINED)
    //тест падает, т.к. баг
    @Test
    void sendingFormOperationDeclinedTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(0, 3, 7);
        //проверяем сообщения
        cardPaymentPage.checkMessage("Ошибка", "Ошибка! Банк отказал в проведении операции.");

        //проверяем статус в БД
        var statusSQL = Database.getStatus();
        //сравниваем фактический и ожидаемый результат
        assertEquals("DECLINED", statusSQL);
    }

    //отправка пустой формы
    //тест проходит успешно
    @Test
    void sendingEmptyFormTest() {
        var cardPaymentPage = new CardPaymentPage();
        //кликаем по кнопке продолжить
        cardPaymentPage.clicButton();
        //проверка сообщений под полями
        //поле номер карты
        cardPaymentPage.messageUnderFields(0, "Неверный формат");
        //поле месяц
        cardPaymentPage.messageUnderFields(1, "Неверный формат");
        //поле год
        cardPaymentPage.messageUnderFields(2, "Неверный формат");
        //поле владелец
        cardPaymentPage.messageUnderFields(3, "Поле обязательно для заполнения");
        //поле cvc
        cardPaymentPage.messageUnderFields(4, "Неверный формат");
    }

    //Передача данных в БД — проверка передачи суммы
    //тест падает, т.к. передаётся неправильная сумма
    @Test
    void transferringAmountDatabaseApprovedTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(1, 5, 3);
        //проверяем сообщение
        cardPaymentPage.checkMessage("Успешно", "Операция одобрена Банком.");

        //проверка суммы в БД
        var amountSQL = Database.getAmount();
        //сравниваем фактический и ожидаемый результат
        assertEquals("45000", amountSQL);
    }
}
