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
        cardPaymentPage.inputFormInvalidCVC(1, 4, 5, null);
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверный формат");
    }

    //вводим одно число
    //тест успешно проходит
    @Test
    void sendingInvalidCVC1NumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCVC(1, 4, 5, DataHelper.generateNumber(1));
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверный формат");
    }

    //вводим больше 3 цифр
    //тест успешно проходит
    @Test
    void sendingInvalidCVCMore3NumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCVC(1, 4, 5, "1526");
        assertEquals("152", cardPaymentPage.getValueFild(4));
    }

    //вводим русский буквы
    //тест проходит успешно
    @Test
    void sendingInvalidCVCRUTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCVC(1, 4, 5, DataHelper.generateRU());
        //кликать на кнопку не надо, проверяем сразу данные в поле вводятся или нет
        assertEquals("", cardPaymentPage.getValueFild(4));
    }

    //вводим буквы на латынице
    //тест успешно проходит
    @Test
    void sendingInvalidCVCENTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCVC(1, 4, 5, DataHelper.generateStringEN(5));
        //кликать на кнопку не надо, проверяем сразу данные в поле вводятся или нет
        assertEquals("", cardPaymentPage.getValueFild(4));
    }

    //вводим символы
    //тест успешно проходит
    @Test
    void sendingInvalidCVCSymbolsTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCVC(1, 4, 5, DataHelper.generateSymbols());
        //кликать на кнопку не надо, проверяем сразу данные в поле вводятся или нет
        assertEquals("", cardPaymentPage.getValueFild(4));
    }

    //вводим текущий год и месяц меньше текущего
    //тест успешно проходит
    @Test
    void sendingInvalidMonthTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCVC(1, -1, 0, DataHelper.generateCvc());
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверно указан срок действия карты");
    }

}
