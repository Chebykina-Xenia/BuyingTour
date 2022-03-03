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

//тесты для проверки невалидных значений в поле ГОД
public class InvalidYearTest {
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
    void sendingInvalidYearEmptyTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidYear(1, 2, null);
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверный формат");
    }

    //вводим одну цифру
    //тест успешно проходит
    @Test
    void sendingInvalidYear1NumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidYear(1, 5, DataHelper.generateNumber(1));
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверный формат");
    }

    //вводим год меньше текущего
    //тест успешно проходит
    @Test
    void sendingInvalidYearLessCurrentYearTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidYear(1, 5, DataHelper.getYearSysdate(-14, "yy"));
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Истёк срок действия карты");
    }

    //вводим год больше текущего на 5 лет
    //тест успешно проходит
    @Test
    void sendingInvalidYearMoreCurrentYearOn5Test() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidYear(1, 5, DataHelper.getYearSysdate(80, "yy"));
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Неверно указан срок действия карты");
    }

    //вводим больше двух цифр
    //тест успешно проходит
    @Test
    void sendingInvalidYearMore2NumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidYear(1, 4, "225");
        assertEquals("22", cardPaymentPage.getValueFild(2));
    }

    //вводим русские буквы
    //тест успешно проходит
    @Test
    void sendingInvalidYearRUTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidYear(1, 5, DataHelper.generateRU());
        //кликать на кнопку не надо, проверяем сразу данные в поле вводятся или нет
        assertEquals("", cardPaymentPage.getValueFild(2));
    }

    //вводим латинские буквы
    //тест успешно проходит
    @Test
    void sendingInvalidYearENTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidYear(1, 5, DataHelper.generateStringEN(4));
        //кликать на кнопку не надо, проверяем сразу данные в поле вводятся или нет
        assertEquals("", cardPaymentPage.getValueFild(2));
    }

    //вводим символы
    //тест успешно проходит
    @Test
    void sendingInvalidYearSymbolTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidYear(1, 5, DataHelper.generateRU());
        //кликать на кнопку не надо, проверяем сразу данные в поле вводятся или нет
        assertEquals("", cardPaymentPage.getValueFild(2));
    }
}
