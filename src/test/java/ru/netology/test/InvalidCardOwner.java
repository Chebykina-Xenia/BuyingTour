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

//тесты для проверки невалидных значений в поле ВЛАДЕЛЕЦ
public class InvalidCardOwner {
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
    void sendingInvalidCardOwnerEmpty() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        messageBelow.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    //вводи в поле одну букву на латинице
    //тест падает, т.к. баг
    @Test
    void sendingInvalidCardOwner1() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue("G");
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        messageBelow.shouldHave(exactText("Диапазон значения в поле должен быть от 2 до 40 букв"));
    }

    //вводи в поле 41 букву на латинице
    //тест падает, т.к. баг
    @Test
    void sendingInvalidCardOwner41() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue("TestsTestsTestsTestsTestsTestsTestsTestsT");
        cvc.setValue(DataHelper.generateCvc("en"));
        button.click();

        messageBelow.shouldHave(exactText("Диапазон значения в поле должен быть от 2 до 40 букв"));
    }

    //вводим русский буквы
    //тест не проходит, т.к. баг
    @Test
    void sendingInvalidCardOwnerRU() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue("Иван");
        cvc.setValue(DataHelper.generateCvc("en"));

        assertEquals("", cardOwner.getValue());
    }

    //вводим символы
    //тест не проходит, т.к. баг
    @Test
    void sendingInvalidCardOwnerSymbol() {
        numberCards.setValue(DataHelper.getCardNumberApproved());
        month.setValue(DataHelper.generateMonth());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        cardOwner.setValue("%^&");
        cvc.setValue(DataHelper.generateCvc("en"));

        assertEquals("", cardOwner.getValue());
    }
}
