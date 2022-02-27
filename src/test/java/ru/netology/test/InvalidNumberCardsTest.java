package ru.netology.test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//тесты для проверки ввода невалидных значений в поле НОМЕР КАРТЫ
public class InvalidNumberCardsTest {
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

    //поле оставляем пустым
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumberEmpty() {
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        //выводится сообщение под полем НОМЕР КАРТЫ
        messageBelow.shouldHave(exactText("Неверный формат"));
    }

    //вводим 5 цифр
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumber5number() {
        numberCards.setValue("55545");
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        //выводится сообщение под полем НОМЕР КАРТЫ
        messageBelow.shouldHave(exactText("Неверный формат"));
    }

    //вводим буквы на кириллице
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumberRU() {
        numberCards.setValue("тест");
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        //поле НОМЕР КАРТЫ остаётся пустым
        //сравниваем ожидаемый и фактический результат
        assertEquals("", numberCards.getValue());
    }

    //вводим буквы на латинеце
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumberEN() {
        numberCards.setValue("test");
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        assertEquals("", numberCards.getValue());
    }

    //вводим символы
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumberSymbols() {
        numberCards.setValue("%^%");
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        assertEquals("", numberCards.getValue());
    }

    //вводим карту, которая не заведена разработчиками
    //тест падает, т.к. баг
    @Test
    void sendingInvalidCardNumber() {
        numberCards.setValue("1515121216161414");
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        $("[class='notification__title']").shouldHave(exactText("Ошибка"), Duration.ofSeconds(30));
        $(".notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! Банк отказал в проведении операции."));
    }

    //вводим количество цифр больше 16
    //тест успешно проходит
    @Test
    void sendingInvalidCardNumber17() {
        numberCards.setValue("15151212161614140");
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        cvc.setValue(DataHelper.generateCvc("en"));

        assertEquals("1515 1212 1616 1414", numberCards.getValue());
    }
}
