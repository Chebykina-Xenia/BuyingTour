package ru.netology.test;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
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
        //открываем сайт
        open("http://localhost:8080/");
        //кликаем по кнопке КУПИТЬ
        $(byText("Купить")).click();
    }

    //находим поле Номер карты
    private SelenideElement numberCards = $("[class='input__control'][placeholder='0000 0000 0000 0000']");
    //находим Месяц
    private SelenideElement month = $("[class='input__control'][placeholder='08']");
    //находим поле Год
    private SelenideElement year = $("[class='input__control'][placeholder='22']");
    //находим поле Владелец
    private SelenideElement cardOwner = $(byText("Владелец")).parent().$("input");
    //находим поле CVC/CVV
    private SelenideElement cvc = $("[class='input__control'][placeholder='999']");
    //находим кнопку продолжить
    private SelenideElement button = $(byText("Продолжить"));
    //сообщение об ошибке под полем
    private SelenideElement messageBelow = $("span.input__top~.input__sub");

    //оставляем поле пустым
    //тест не проходит, т.к. баг
    @Test
    void sendingInvalidCVCEmpty() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        button.click();

        messageBelow.shouldHave(exactText("Неверный формат"));
    }

    //вводим одно число
    //тест успешно проходит
    @Test
    void sendingInvalidCVC1Number() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue("3");
        button.click();

        messageBelow.shouldHave(exactText("Неверный формат"));
    }

    //вводим больше 3 цифр
    //тест успешно проходит
    @Test
    void sendingInvalidCVCMore3Number() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue("5625");

        assertEquals("562", cvc.getValue());
    }

    //вводим русский буквы
    //тест успешно проходит
    @Test
    void sendingInvalidCVCRU() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue("ппп");

        assertEquals("", cvc.getValue());
    }

    //вводим буквы на латынице
    //тест успешно проходит
    @Test
    void sendingInvalidCVCEN() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue("jjp");

        assertEquals("", cvc.getValue());
    }

    //вводим символы
    //тест успешно проходит
    @Test
    void sendingInvalidCVCSymbols() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue("$$");

        assertEquals("", cvc.getValue());
    }

    //вводим текущий год и месяц меньше текущего
    //тест успешно проходит
    @Test
    void sendingInvalidMonth() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.getYearSysdate(-1, "MM"));
        year.setValue(DataHelper.getYearSysdate(0, "yy"));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        messageBelow.shouldHave(exactText("Неверно указан срок действия карты"));
    }

}
