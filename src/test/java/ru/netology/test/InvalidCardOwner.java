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

//тесты для проверки невалидных значений в поле ВЛАДЕЛЕЦ
public class InvalidCardOwner {
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
    void sendingInvalidCardOwnerEmptyTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                null,
                DataHelper.generateCvc());
        //проверяем сообщение
        cardPaymentPage.checkCardOwnerSubMessage("Поле обязательно для заполнения");
    }

    //вводи в поле одну букву на латинице
    //тест падает, т.к. баг
    @Test
    void sendingInvalidCardOwner1Test() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateStringEN(1),
                DataHelper.generateCvc());
        //проверяем сообщение
        cardPaymentPage.checkCardOwnerSubMessage("Диапазон значения в поле должен быть от 2 до 40 букв");
    }

    //вводи в поле 41 букву на латинице
    //тест падает, т.к. баг
    @Test
    void sendingInvalidCardOwner41Test() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateStringEN(41),
                DataHelper.generateCvc());
        //проверяем сообщение
        cardPaymentPage.checkCardOwnerSubMessage("Диапазон значения в поле должен быть от 2 до 40 букв");
    }

    //вводим русский буквы
    //тест не проходит, т.к. баг
    @Test
    void sendingInvalidCardOwnerRUTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateRU(),
                DataHelper.generateCvc());
        //проверяем сообщение
        cardPaymentPage.checkCardOwnerSubMessage("Неверный формат");
    }

    //вводим цифры
    //тест не проходит, т.к. баг
    @Test
    void sendingInvalidCardOwnerNumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(0, "YY"),
                DataHelper.generateNumber(10),
                DataHelper.generateCvc());
        //проверяем сообщение
        cardPaymentPage.checkCardOwnerSubMessage("Неверный формат");
    }
}

