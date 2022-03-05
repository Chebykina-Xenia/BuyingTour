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
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                null,
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkYearSubMessage("Неверный формат");
    }

    //вводим одну цифру
    //тест успешно проходит
    @Test
    void sendingInvalidYear1NumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.generateNumber(1),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkYearSubMessage("Неверный формат");
    }

    //вводим год меньше текущего
    //тест успешно проходит
    @Test
    void sendingInvalidYearLessCurrentYearTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(-14, "yy"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkYearSubMessage("Истёк срок действия карты");
    }

    //вводим год больше текущего на 5 лет
    //тест успешно проходит
    @Test
    void sendingInvalidYearMoreCurrentYearOn5Test() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.getYearSysdate(80, "yy"),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkYearSubMessage("Неверно указан срок действия карты");
    }

    //вводим больше двух цифр
    //тест успешно проходит
    @Test
    void sendingInvalidYearMore2NumberTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.generateNumber(3),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkYearSubMessageVisible();
    }

    //вводим русские буквы
    //тест успешно проходит
    @Test
    void sendingInvalidYearRUTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.generateRU(),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkYearSubMessage("Неверный формат");
    }

    //вводим латинские буквы
    //тест успешно проходит
    @Test
    void sendingInvalidYearENTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.generateStringEN(10),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkYearSubMessage("Неверный формат");
    }

    //вводим символы
    //тест успешно проходит
    @Test
    void sendingInvalidYearSymbolTest() {
        var cardPaymentPage = new CardPaymentPage();
        //заполняем форму
        cardPaymentPage.inputAndsendingForm(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getYearSysdate(3, "MM"),
                DataHelper.generateSymbols(),
                DataHelper.generateCardowner(),
                DataHelper.generateCvc());
        cardPaymentPage.checkYearSubMessage("Неверный формат");
    }
}

