package ru.netology.test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.database.Database;

import java.time.Duration;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private SelenideElement cardOwner = $(byText("Владелец")).parent().$("input");
    //находим поле CVC/CVV
    private SelenideElement cvc = $("[class='input__control'][placeholder='999']");
    //находим кнопку продолжить
    private SelenideElement button = $(byText("Продолжить"));
    //коллекция сообщений под полями
    private ElementsCollection messageBelows = $$("span.input__top~.input__sub");
    //сообщение об ошибке под полем
    private SelenideElement messageBelow = $("span.input__top~.input__sub");

    //проверяем отправку формы — операция одобрена банком (статус APPROVED)
    //тест успешно проходит
    @Test
    void sendingFormOperationApproved() {
        //заполняем поле Номер карты
        numberCards.setValue(DataHelper.getCardNumberApproved());
        //заполняем поле месяц
        month.setValue(DataHelper.generateMonth());
        //заполняем поле год
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        //заполняем поле владелец
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        //заполняем поле cvc
        cvc.setValue(DataHelper.generateCvc("en"));
        //кликаем по кнопке продолжить
        button.click();

        //проверка успешной отправки формы
        $("[class='notification__title']").shouldHave(exactText("Успешно"), Duration.ofSeconds(30));
        $(".notification__content").shouldBe(visible).shouldHave(exactText("Операция одобрена Банком."));
    }

    //проверяем отправку формы — операция НЕ одобрена банком (статус DECLINED)
    //тест падает, т.к. баг
    @Test
    void sendingFormOperationDeclined() {
        //заполняем поле Номер карты
        numberCards.setValue(DataHelper.getCardNumberDeclined());
        //заполняем поле месяц
        month.setValue(DataHelper.generateMonth());
        //заполняем поле год
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        //заполняем поле владелец
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        //заполняем поле cvc
        cvc.setValue(DataHelper.generateCvc("en"));
        //кликаем по кнопке продолжить
        button.click();

        //проверка успешной отправки формы
        $("[class='notification__title']").shouldHave(exactText("Ошибка"), Duration.ofSeconds(30));
        $(".notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! Банк отказал в проведении операции."));
    }

    //отправка пустой формы
    //тест проходит успешно
    @Test
    void sendingEmptyForm() {
        //кликаем по кнопке продолжить
        button.click();
        //проверяем сообщение по полем Номер карты
        messageBelows.get(0).shouldHave(exactText("Неверный формат"));
        //проверяем сообщение под полем месяц
        messageBelows.get(1).shouldHave(exactText("Неверный формат"));
        //проверяем сообщение под полем год
        messageBelows.get(2).shouldHave(exactText("Неверный формат"));
        //проверяем сообщение под полем владелец
        messageBelows.get(3).shouldHave(exactText("Поле обязательно для заполнения"));
        //проверяем сообщение под полем CVC/CVV
        messageBelows.get(4).shouldHave(exactText("Неверный формат"));
    }

    //Передача данных в БД — проверка передачи статуса одобрено
    //тест проходит успешно
    @Test
    void transferringStatusDatabaseApproved() {
        //заполняем поле Номер карты
        numberCards.setValue(DataHelper.getCardNumberApproved());
        //заполняем поле месяц
        month.setValue(DataHelper.generateMonth());
        //заполняем поле год
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        //заполняем поле владелец
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        //заполняем поле cvc
        cvc.setValue(DataHelper.generateCvc("en"));
        //кликаем по кнопке продолжить
        button.click();

        $("[class='notification__title']").shouldHave(exactText("Успешно"), Duration.ofSeconds(30));
        var statusSQL = Database.getStatus();
        //сравниваем фактический и ожидаемый результат
        assertEquals("APPROVED", statusSQL);
        //очищаем таблицы
        Database.cleanDatabase();
    }

    //Передача данных в БД — проверка передачи суммы
    //тест падает, т.к. передаётся неправильная сумма
    @Test
    void transferringAmountDatabaseApproved() {
        //заполняем поле Номер карты
        numberCards.setValue(DataHelper.getCardNumberApproved());
        //заполняем поле месяц
        month.setValue(DataHelper.generateMonth());
        //заполняем поле год
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        //заполняем поле владелец
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        //заполняем поле cvc
        cvc.setValue(DataHelper.generateCvc("en"));
        //кликаем по кнопке продолжить
        button.click();

        $("[class='notification__title']").shouldHave(exactText("Успешно"), Duration.ofSeconds(30));
        var amountSQL = Database.getAmount();
        //сравниваем фактический и ожидаемый результат
        assertEquals("45000", amountSQL);
        //очищаем таблицы
        Database.cleanDatabase();
    }

    //Передача данных в БД — проверка передачи статуса НЕ одобрено
    //тест падает, т.к. баг - сообщение выдаёт успех, а не ошибка
    @Test
    void transferringStatusDatabaseDeclined() {
        //заполняем поле Номер карты
        numberCards.setValue(DataHelper.getCardNumberDeclined());
        //заполняем поле месяц
        month.setValue(DataHelper.generateMonth());
        //заполняем поле год
        year.setValue(Integer.toString(DataHelper.generateYear2("en")));
        //заполняем поле владелец
        cardOwner.setValue(DataHelper.generateCardowner("en"));
        //заполняем поле cvc
        cvc.setValue(DataHelper.generateCvc("en"));
        //кликаем по кнопке продолжить
        button.click();

        $("[class='notification__title']").shouldHave(exactText("Ошибка"), Duration.ofSeconds(30));
        var statusSQL = Database.getStatus();
        //сравниваем фактический и ожидаемый результат
        assertEquals("DECLINED", statusSQL);
        //очищаем таблицы
        Database.cleanDatabase();
    }
}
