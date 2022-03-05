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

//тесты для проверки невалидных значений в поле CVC
public class InvalidCVCTest {
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
    //тест не проходит, т.к. баг
    @Test
    void sendingInvalidCVCEmptyTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                null);
        cardPaymentPage.checkCVCSubMessage("Неверный формат");
    }

    //вводим одно число
    //тест успешно проходит
    @Test
    void sendingInvalidCVC1NumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateNumber(1));
        cardPaymentPage.checkCVCSubMessage("Неверный формат");
    }

    //вводим больше 3 цифр
    //тест успешно проходит
    @Test
    void sendingInvalidCVCMore3NumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateNumber(5));
        cardPaymentPage.checkMessage("Успешно", "Операция одобрена Банком.");
    }

    //вводим русский буквы
    //тест проходит успешно
    @Test
    void sendingInvalidCVCRUTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateRU());
        cardPaymentPage.checkCVCSubMessage("Неверный формат");
    }

    //вводим буквы на латынице
    //тест успешно проходит
    @Test
    void sendingInvalidCVCENTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateStringEN(5));
        cardPaymentPage.checkCVCSubMessage("Неверный формат");
    }

    //вводим символы
    //тест успешно проходит
    @Test
    void sendingInvalidCVCSymbolsTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateSymbols());
        cardPaymentPage.checkCVCSubMessage("Неверный формат");
    }

    //вводим текущий год и месяц меньше текущего
    //тест успешно проходит
    @Test
    void sendingInvalidMonthTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(-2, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkCVCSubMessage("Неверно указан срок действия карты");
    }

}

