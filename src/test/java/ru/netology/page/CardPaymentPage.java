package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

//страница оплата по карте
public class CardPaymentPage {

    //заголовок — ОПЛАТА ПО КАРТЕ
    private SelenideElement title = $(byText("Оплата по карте"));
    //номер карты
    private SelenideElement numberCards = $("[class='input__control'][placeholder='0000 0000 0000 0000']");
    //месяц
    private SelenideElement month = $("[class='input__control'][placeholder='08']");
    //Год
    private SelenideElement year = $("[class='input__control'][placeholder='22']");
    //Владелец
    private SelenideElement cardOwner = $(byText("Владелец")).parent().$("input");
    //CVC/CVV
    private SelenideElement cvc = $("[class='input__control'][placeholder='999']");
    //кнопка продолжить
    private SelenideElement button = $(byText("Продолжить"));

    //вывод сообщения (успех или ошибка)
    private SelenideElement message = $("[class='notification__title']");
    //описание сообщения
    private SelenideElement messageDescription = $(".notification__content");

    //коллекция сообщений под полями
    private ElementsCollection messageBelows = $$("span.input__top~.input__sub");
    //сообщение об ошибке под полем
    private SelenideElement messageBelow = $("span.input__top~.input__sub");

    public CardPaymentPage() {
        //проверяем — видно ли заголовок ОПЛАТА ПО КАРТЕ
        title.shouldBe(visible);
    }

    //ЗАПОЛНЕНИЕ ВСЕХ ПОЛЕЙ И ОТПРАВКА ФОРМЫ
    public void inputAndsendingForm(int key, int numberPlusMonthMonth, int numberPlusMonthYear) {
        numberCards.setValue(DataHelper.getCardNumber(key));
        month.setValue(DataHelper.getYearSysdate(numberPlusMonthMonth, "MM"));
        year.setValue(DataHelper.getYearSysdate(numberPlusMonthYear, "yy"));
        cardOwner.setValue(DataHelper.generateCardowner());
        cvc.setValue(DataHelper.generateCvc());
        button.click();
    }

    //ПРОВЕРЯЕМ СООБЩЕНИЕ
    public void checkMessage(String expectedText, String expectedMessageDescription) {
        message.shouldHave(exactText(expectedText), Duration.ofSeconds(30));
        messageDescription.shouldBe(visible).shouldHave(exactText(expectedMessageDescription));
    }

    //КЛИКАЕМ ПО КНОПКЕ ПРОДОЛЖИТЬ
    public void clicButton() {
        button.click();
    }

    //ПРОВЕРКА СООБЩЕНИЙ ПОД ПОЛЕМ КОЛЛЕКЦИЯ
    public void messageUnderFields(int numberField, String expectedMessageUnderField) {
        messageBelows.get(numberField).shouldHave(exactText(expectedMessageUnderField));
    }

    //ПРОВЕРКА СООБЩЕНИЙ ПОД ПОЛЕМ
    public void messageUnderField(String expectedMessageUnderField) {
        messageBelow.shouldHave(exactText(expectedMessageUnderField));
    }


    //ПРОВЕРКА ДАННЫХ В ПОЛЕ
    public String getValueFild(int key) {
        String value = null;
        if (key == 0) {
            value = numberCards.getValue();
        }
        if (key == 1) {
            value = month.getValue();
        }
        if (key == 2) {
            value = year.getValue();
        }
        if (key == 3) {
            value = cardOwner.getValue();
        }
        if (key == 4) {
            value = cvc.getValue();
        }
        return value;
    }

    //ЗАПОЛНЕНИЕ невалидными значениями поле владелец
    public void inputFormInvalidCardOwner(int key, int numberPlusMonthMonth, int numberPlusMonthYear, String nameCardOwner) {
        numberCards.setValue(DataHelper.getCardNumber(key));
        month.setValue(DataHelper.getYearSysdate(numberPlusMonthMonth, "MM"));
        year.setValue(DataHelper.getYearSysdate(numberPlusMonthYear, "yy"));
        cardOwner.setValue(nameCardOwner);
        cvc.setValue(DataHelper.generateCvc());
    }

    //ЗАПОЛНЕНИЕ невалидными значениями поле CVC
    public void inputFormInvalidCVC(int key, int numberPlusMonthMonth, int numberPlusMonthYear, String numberCVC) {
        numberCards.setValue(DataHelper.getCardNumber(key));
        month.setValue(DataHelper.getYearSysdate(numberPlusMonthMonth, "MM"));
        year.setValue(DataHelper.getYearSysdate(numberPlusMonthYear, "yy"));
        cardOwner.setValue(DataHelper.generateCardowner());
        cvc.setValue(numberCVC);
    }

    //ЗАПОЛНЕНИЕ невалидными значениями поле месяц
    public void inputFormInvalidMonth(int key, int numberPlusMonthYear, String numberMonth) {
        numberCards.setValue(DataHelper.getCardNumber(key));
        month.setValue(numberMonth);
        year.setValue(DataHelper.getYearSysdate(numberPlusMonthYear, "yy"));
        cardOwner.setValue(DataHelper.generateCardowner());
        cvc.setValue(DataHelper.generateCvc());
    }

    //ЗАПОЛНЕНИЕ невалидными значениями поле номер карты
    public void inputFormInvalidCardNumbers(int key, int numberPlusMonthMonth, int numberPlusMonthYear, String setCardNymbers) {
        numberCards.setValue(setCardNymbers);
        month.setValue(DataHelper.getYearSysdate(numberPlusMonthMonth, "MM"));
        year.setValue(DataHelper.getYearSysdate(numberPlusMonthYear, "yy"));
        cardOwner.setValue(DataHelper.generateCardowner());
        cvc.setValue(DataHelper.generateCvc());
    }

    //ЗАПОЛНЕНИЕ невалидными значениями поле год
    public void inputFormInvalidYear(int key, int numberPlusMonthMonth, String numberYear) {
        numberCards.setValue(DataHelper.getCardNumber(key));
        month.setValue(DataHelper.getYearSysdate(numberPlusMonthMonth, "MM"));
        year.setValue(numberYear);
        cardOwner.setValue(DataHelper.generateCardowner());
        cvc.setValue(DataHelper.generateCvc());
    }

}



