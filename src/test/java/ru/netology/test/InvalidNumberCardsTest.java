
package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.database.Database;
import ru.netology.page.CardPaymentPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.*;

//тесты для проверки ввода невалидных значений в поле НОМЕР КАРТЫ
public class InvalidNumberCardsTest {
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

    //поле оставляем пустым
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumberEmptyTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputAndsendingForm(null,
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkCardNumberSubMessage("Неверный формат");
    }

    //вводим 5 цифр
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumber5numberTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputAndsendingForm(DataHelper.generateNumber(5),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkCardNumberSubMessage("Неверный формат");
    }

    //вводим буквы на кириллице
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumberRUTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputAndsendingForm(DataHelper.generateRU(),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkCardNumberSubMessage("Неверный формат");
    }

    //вводим буквы на латинеце
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumberENTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputAndsendingForm(DataHelper.generateStringEN(5),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkCardNumberSubMessage("Неверный формат");
    }

    //вводим символы
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumberSymbolsTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputAndsendingForm(DataHelper.generateSymbols(),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkCardNumberSubMessage("Неверный формат");
    }

    //вводим карту, которая не заведена разработчиками
    //тест падает, т.к. баг
    @Test
    void sendingInvalidCardNumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputAndsendingForm(DataHelper.generateNumber(16),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkMessage("Ошибка", "Ошибка! Банк отказал в проведении операции.");
    }

    //вводим количество цифр больше 16
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumber17Test() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputAndsendingForm(DataHelper.generateNumber(17),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkMessage("Ошибка", "Ошибка! Банк отказал в проведении операции.");
    }
}


