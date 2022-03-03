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
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        cardPaymentPage.inputFormInvalidCardNumbers(1, 4, 5, null);
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверный формат");
    }

    //вводим 5 цифр
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumber5numberTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCardNumbers(1, 4, 5, DataHelper.generateNumber(5));
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверный формат");
    }

    //вводим буквы на кириллице
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumberRUTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCardNumbers(1, 4, 5, DataHelper.generateRU());
        //кликать на кнопку не надо, проверяем сразу данные в поле вводятся или нет
        assertEquals("", cardPaymentPage.getValueFild(0));
    }

    //вводим буквы на латинеце
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumberENTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCardNumbers(1, 4, 5, DataHelper.generateStringEN(5));
        //кликать на кнопку не надо, проверяем сразу данные в поле вводятся или нет
        assertEquals("", cardPaymentPage.getValueFild(0));
    }

    //вводим символы
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumberSymbolsTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCardNumbers(1, 4, 5, DataHelper.generateSymbols());
        //кликать на кнопку не надо, проверяем сразу данные в поле вводятся или нет
        assertEquals("", cardPaymentPage.getValueFild(0));
    }

    //вводим карту, которая не заведена разработчиками
    //тест падает, т.к. баг
    @Test
    void sendingInvalidCardNumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCardNumbers(1, 4, 5, DataHelper.generateNumber(16));
        cardPaymentPage.clicButton();
        cardPaymentPage.checkMessage("Ошибка", "Ошибка! Банк отказал в проведении операции.");
    }

    //вводим количество цифр больше 16
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumber17Test() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCardNumbers(1, 4, 5, "15151212161614140");
        assertEquals("1515 1212 1616 1414", cardPaymentPage.getValueFild(0));
    }
}
