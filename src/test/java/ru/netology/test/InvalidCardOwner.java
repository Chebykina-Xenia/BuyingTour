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
        cardPaymentPage.inputFormInvalidCardOwner(1, 3, 4, null);
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Поле обязательно для заполнения");
    }

    //вводи в поле одну букву на латинице
    //тест падает, т.к. баг
    @Test
    void sendingInvalidCardOwner1Test() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCardOwner(1, 3, 5, DataHelper.generateStringEN(1));
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Диапазон значения в поле должен быть от 2 до 40 букв");
    }

    //вводи в поле 41 букву на латинице
    //тест падает, т.к. баг
    @Test
    void sendingInvalidCardOwner41Test() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCardOwner(1, 3, 5, DataHelper.generateStringEN(41));
        cardPaymentPage.clicButton();
        cardPaymentPage.messageUnderField("Диапазон значения в поле должен быть от 2 до 40 букв");
    }

    //вводим русский буквы
    //тест не проходит, т.к. баг
    @Test
    void sendingInvalidCardOwnerRUTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCardOwner(1, 3, 4, DataHelper.generateRU());
        assertEquals("", cardPaymentPage.getValueFild(3));
    }

    //вводим цифры
    //тест не проходит, т.к. баг
    @Test
    void sendingInvalidCardOwnerNumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        cardPaymentPage.inputFormInvalidCardOwner(1, 3, 4, DataHelper.generateNumber(5));
        assertEquals("", cardPaymentPage.getValueFild(3));
    }
}
