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
    //тест успешно проходит
    @Test
    void sendingInvalidYearEmpty() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        //выводится сообщение под полем ГОД
        messageBelow.shouldHave(exactText("Неверный формат"));
    }

    //вводим одну цифру
    //тест успешно проходит
    @Test
    void sendingInvalidYear1Number() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue("1");
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        messageBelow.shouldHave(exactText("Неверный формат"));
    }

    //вводим год меньше текущего
    //тест успешно проходит
    @Test
    void sendingInvalidYearLessCurrentYear() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(DataHelper.getYearSysdate(-14, "yy"));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        messageBelow.shouldHave(exactText("Истёк срок действия карты"));
    }

    //вводим год больше текущего на 5 лет
    //тест успешно проходит
    @Test
    void sendingInvalidYearMoreCurrentYearOn5() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(DataHelper.getYearSysdate(80, "yy"));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        messageBelow.shouldHave(exactText("Неверно указан срок действия карты"));
    }

    //вводим больше двух цифр
    //тест успешно проходит
    @Test
    void sendingInvalidYearMore2Number() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue("225");
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));

        assertEquals("22", year.getValue());
    }

    //вводим русские буквы
    //тест успешно проходит
    @Test
    void sendingInvalidYearRU() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue("ооо");
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));

        assertEquals("", year.getValue());
    }

    //вводим латинские буквы
    //тест успешно проходит
    @Test
    void sendingInvalidYearEN() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue("hhh");
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));

        assertEquals("", year.getValue());
    }

    //вводим символы
    //тест успешно проходит
    @Test
    void sendingInvalidYearSymbol() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue("%%");
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));

        assertEquals("", year.getValue());
    }
}
