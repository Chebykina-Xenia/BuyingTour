package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class BuyingTourTest {
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
    //private SelenideElement cardowner = $(byText("Владелец")).doubleClick();
    //находим поле CVC/CVV
    private SelenideElement cvc = $("[class='input__control'][placeholder='999']");
    //находим кнопку продолжить
    private SelenideElement button = $(byText("Купить"));

    //проверяем отправку формы — операция одобрена
    @Test
    void sendingFormOperationApproved() {
        //заполняем поле Номер карты
        numberCards.setValue(DataHelper.getCardNumberApproved());
        //заполняем поле месяц
        month.setValue(DataHelper.generateMonth());
        //заполняем поле год
       // year.setValue(DataHelper.generateYear());
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        //заполняем поле владелец
        SelenideElement cardowner = $(byText("Владелец")).doubleClick();
        cardowner.setValue(DataHelper.generateCardowner("en"));
        //заполняем поле cvc
        cvc.setValue(DataHelper.generateCvc("en"));
           }

}
