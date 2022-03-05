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
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                null,
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkMonthSubMessage("Неверный формат");
    }

    //вводим одно число
    //тест успешно проходит
    @Test
    void sendingInvalidMonth1NumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.generateNumber(1),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkMonthSubMessage("Неверный формат");
    }

    //вводим число 13
    //тест успешно проходит
    @Test
    void sendingInvalidMonth13Test() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.generateNumber(13),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkMonthSubMessage("Неверно указан срок действия карты");
    }

    //вводим число 00
    //тест успешно проходит
    @Test
    void sendingInvalidMonth00Test() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                "00",
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkMonthSubMessage("Неверно указан срок действия карты");
    }

    //вводим больше 2 цифр
    //тест успешно проходит
    @Test
    void sendingInvalidMonthMore3NumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.generateNumber(3),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkMonthSubMessage("Неверный формат");
    }

    //вводим русский буквы
    //тест успешно проходит
    @Test
    void sendingInvalidMonthRUTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.generateRU(),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkMonthSubMessage("Неверный формат");
    }

    //вводим буквы на латынице
    //тест успешно проходит
    @Test
    void sendingInvalidMonthENTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.generateStringEN(4),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkMonthSubMessage("Неверный формат");
    }

    //вводим символы
    //тест успешно проходит
    @Test
    void sendingInvalidMonthSymbolsTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.generateSymbols(),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkMonthSubMessage("Неверный формат");
    }

    //вводим текущий год и месяц меньше текущего
    //тест успешно проходит
    @Test
    void sendingInvalidMonth() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(-2, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkMonthSubMessage("Неверно указан срок действия карты");
    }
}