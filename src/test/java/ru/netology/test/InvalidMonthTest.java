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

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

//тесты для проверки невалидных значений в поле месяц
public class InvalidMonthTest {
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

    //оставляем поле пустым
    //тест успешно проходит
    @Test
    void sendingInvalidMonthEmptyTst() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidMonth(1, 2, null);
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверный формат");
    }

    //вводим одно число
    //тест успешно проходит
    @Test
    void sendingInvalidMonth1NumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidMonth(1, 5, DataHelper.generateNumber(1));
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверный формат");
    }

    //вводим число 13
    //тест успешно проходит
    @Test
    void sendingInvalidMonth13Test() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidMonth(1, 5, "13");
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверно указан срок действия карты");
    }

    //вводим число 00
    //тест успешно проходит
    @Test
    void sendingInvalidMonth00Test() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidMonth(1, 15, "00");
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверно указан срок действия карты");
    }

    //вводим больше 2 цифр
    //тест успешно проходит
    @Test
    void sendingInvalidMonthMore3NumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidMonth(1, 4, "123");
        assertEquals("12", cardPaymentPage.getValueFild(1));
    }

    //вводим русский буквы
    //тест успешно проходит
    @Test
    void sendingInvalidMonthRUTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidMonth(1, 5, DataHelper.generateRU());
        //кликать на кнопку не надо, проверяем сразу данные в поле вводятся или нет
        assertEquals("", cardPaymentPage.getValueFild(1));
    }

    //вводим буквы на латынице
    //тест успешно проходит
    @Test
    void sendingInvalidMonthENTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidMonth(1, 5, DataHelper.generateStringEN(5));
        //кликать на кнопку не надо, проверяем сразу данные в поле вводятся или нет
        assertEquals("", cardPaymentPage.getValueFild(1));
    }

    //вводим символы
    //тест успешно проходит
    @Test
    void sendingInvalidMonthSymbolsTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidMonth(1, 6, DataHelper.generateSymbols());
        //кликать на кнопку не надо, проверяем сразу данные в поле вводятся или нет
        assertEquals("", cardPaymentPage.getValueFild(1));
    }

    //вводим текущий год и месяц меньше текущего
    //тест успешно проходит
    @Test
    void sendingInvalidMonth() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidMonth(1, 0, DataHelper.getYearSysdate(-2, "MM"));
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверно указан срок действия карты");
    }
}
