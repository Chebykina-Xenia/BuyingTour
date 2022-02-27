package ru.netology.test;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

//тесты для проверки невалидных значений в поле месяц
public class InvalidMonthTest {
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
    void sendingInvalidMonthEmpty() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        //выводится сообщение под полем МЕСЯЦ
        messageBelow.shouldHave(exactText("Неверный формат"));
    }

    //вводим одно число
    //тест успешно проходит
    @Test
    void sendingInvalidMonth1Number() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue("2");
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        //выводится сообщение под полем МЕСЯЦ
        messageBelow.shouldHave(exactText("Неверный формат"));
    }

    //вводим число 13
    //тест успешно проходит
    @Test
    void sendingInvalidMonth13() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue("13");
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        //выводится сообщение под полем МЕСЯЦ
        messageBelow.shouldHave(exactText("Неверно указан срок действия карты"));
    }

    //вводим число 00
    //тест падает, т.к. баг
    @Test
    void sendingInvalidMonth00() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue("00");
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        //выводится сообщение под полем МЕСЯЦ
        messageBelow.shouldHave(exactText("Неверно указан срок действия карты"));
    }

    //вводим больше 2 цифр
    //тест успешно проходит
    @Test
    void sendingInvalidMonthMore3Number() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue("125");
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));

        assertEquals("12", month.getValue());
    }

    //вводим русский буквы
    //тест успешно проходит
    @Test
    void sendingInvalidMonthRU() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue("ппп");
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));

        assertEquals("", month.getValue());
    }

    //вводим буквы на латынице
    //тест успешно проходит
    @Test
    void sendingInvalidMonthEN() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue("ttt");
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));

        assertEquals("", month.getValue());
    }

    //вводим символы
    //тест успешно проходит
    @Test
    void sendingInvalidMonthSymbols() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue("@%");
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));

        assertEquals("", month.getValue());
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
