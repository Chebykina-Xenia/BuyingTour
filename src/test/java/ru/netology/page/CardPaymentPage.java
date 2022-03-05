package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

//страница оплата по карте
public class CardPaymentPage {

    //заголовок — ОПЛАТА ПО КАРТЕ
    private SelenideElement textTitle = $(byText("Оплата по карте"));
    //номер карты
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    //месяц
    private SelenideElement monthField = $("[placeholder='08']");
    //Год
    private SelenideElement yearField = $("[placeholder='22']");
    //Владелец
    private SelenideElement cardOwnerField = $(byText("Владелец")).parent().$("input");
    //CVC/CVV
    private SelenideElement cvcField = $("[placeholder='999']");
    //кнопка продолжить
    private SelenideElement proceedButton = $(byText("Продолжить"));

    //вывод сообщения (успех или ошибка)
    private SelenideElement finalMessage = $("[class='notification__title']");
    //описание сообщения
    private SelenideElement finalMessageDescription = $(".notification__content");

    //сообщения под полями
    private SelenideElement cardNumberSub = cardNumberField.parent().parent().$(".input__sub");
    private SelenideElement monthSub = monthField.parent().parent().$(".input__sub");
    private SelenideElement yearSub = yearField.parent().parent().$(".input__sub");
    private SelenideElement cardOwnerSub = cardOwnerField.parent().parent().$(".input__sub");
    private SelenideElement cvcSub = cvcField.parent().parent().$(".input__sub");

    public CardPaymentPage() {
        //проверяем — видно ли заголовок ОПЛАТА ПО КАРТЕ
        textTitle.shouldBe(visible);
    }

    //ЗАПОЛНЕНИЕ ВСЕХ ПОЛЕЙ И ОТПРАВКА ФОРМЫ
    public void inputAndsendingForm(String dateNumberCard, String dateMonth, String dateYear, String dateCardOwner, String dateCVC) {
        cardNumberField.setValue(dateNumberCard);
        monthField.setValue(dateMonth);
        yearField.setValue(dateYear);
        cardOwnerField.setValue(dateCardOwner);
        cvcField.setValue(dateCVC);
        proceedButton.click();
    }

    //ПРОВЕРЯЕМ СООБЩЕНИЕ
    public void checkMessage(String expectedText, String expectedMessageDescription) {
        finalMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(30));
        finalMessageDescription.shouldBe(visible).shouldHave(exactText(expectedMessageDescription));
    }

    //ПРОВЕРКА СООБЩЕНИЙ ПОД ПОЛЕМ
    public void checkCardNumberSubMessage(String expectedMessage){
        cardNumberSub.shouldHave(text(expectedMessage));
    }

    public void checkMonthSubMessage(String expectedMessage){
        monthSub.shouldHave(text(expectedMessage));
    }

    public void checkYearSubMessage(String expectedMessage){
        yearSub.shouldHave(text(expectedMessage));
    }


    public void checkYearSubMessageVisible(){
        yearSub.shouldBe(visible);
    }
    public void checkCardOwnerSubMessage(String expectedMessage){
        cardOwnerSub.shouldHave(text(expectedMessage));
    }

    public void checkCVCSubMessage(String expectedMessage){
        cvcSub.shouldHave(text(expectedMessage));
    }
    //ПРОВЕРКА СООБЩЕНИЙ ПОД ПОЛЕМ
    //public void messageUnderField(String expectedMessageUnderField) {
    //  messageBelow.shouldHave(exactText(expectedMessageUnderField));
    //}



    //ПРОВЕРКА ДАННЫХ В ПОЛЕ
    //  public String getValueFild(int key) {
    //    String value = null;
    //  if (key == 0) {
    //    value = numberCards.getValue();
    //  }
//        if (key == 1) {
    //          value = month.getValue();
    //    }
    //  if (key == 2) {
    //  value = year.getValue();
    //}
    //     if (key == 3) {
    //       value = cardOwner.getValue();
    // }
//        if (key == 4) {
    //          value = cvc.getValue();
    //    }
    //  return value;
    //}

    //ЗАПОЛНЕНИЕ невалидными значениями поле владелец
    //  public void inputFormInvalidCardOwner(int key, int numberPlusMonthMonth, int numberPlusMonthYear, String nameCardOwner) {
//        numberCards.setValue(DataHelper.getCardNumber(key));
    //      month.setValue(DataHelper.getYearSysdate(numberPlusMonthMonth, "MM"));
    //    year.setValue(DataHelper.getYearSysdate(numberPlusMonthYear, "yy"));
    //       cardOwner.setValue(nameCardOwner);
    //     cvc.setValue(DataHelper.generateCvc());
    // }

    //ЗАПОЛНЕНИЕ невалидными значениями поле CVC
//    public void inputFormInvalidCVC(int key, int numberPlusMonthMonth, int numberPlusMonthYear, String numberCVC) {
    //      numberCards.setValue(DataHelper.getCardNumber(key));
    //    month.setValue(DataHelper.getYearSysdate(numberPlusMonthMonth, "MM"));
    //  year.setValue(DataHelper.getYearSysdate(numberPlusMonthYear, "yy"));
    //      cardOwner.setValue(DataHelper.generateCardowner());
    //    cvc.setValue(numberCVC);
    //}

    //ЗАПОЛНЕНИЕ невалидными значениями поле месяц
    //  public void inputFormInvalidMonth(int key, int numberPlusMonthYear, String numberMonth) {
    //    numberCards.setValue(DataHelper.getCardNumber(key));
    //  month.setValue(numberMonth);
    //  year.setValue(DataHelper.getYearSysdate(numberPlusMonthYear, "yy"));
    // cardOwner.setValue(DataHelper.generateCardowner());
    //      cvc.setValue(DataHelper.generateCvc());
    // }

    //ЗАПОЛНЕНИЕ невалидными значениями поле номер карты
    //  public void inputFormInvalidCardNumbers(int key, int numberPlusMonthMonth, int numberPlusMonthYear, String setCardNymbers) {
    //      numberCards.setValue(setCardNymbers);
    //    month.setValue(DataHelper.getYearSysdate(numberPlusMonthMonth, "MM"));
    //   year.setValue(DataHelper.getYearSysdate(numberPlusMonthYear, "yy"));
    // cardOwner.setValue(DataHelper.generateCardowner());
    //     cvc.setValue(DataHelper.generateCvc());
    //}

    //ЗАПОЛНЕНИЕ невалидными значениями поле год
//    public void inputFormInvalidYear(int key, int numberPlusMonthMonth, String numberYear) {
    //      numberCards.setValue(DataHelper.getCardNumber(key));
    //    month.setValue(DataHelper.getYearSysdate(numberPlusMonthMonth, "MM"));
    //  year.setValue(numberYear);
    //cardOwner.setValue(DataHelper.generateCardowner());
    // cvc.setValue(DataHelper.generateCvc());
    //}

}



