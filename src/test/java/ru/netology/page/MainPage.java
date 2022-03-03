package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

//главная страница для покупки тура
public class MainPage {

    //заголовок — Путешествие дня
    private SelenideElement heading = $(byText("Путешествие дня"));
    //кнопка купить
    private SelenideElement buttonBuy = $(byText("Купить"));

    public MainPage() {
        //проверяем — видно ли заголовк на странице
        heading.shouldBe(visible);
    }

    //кликаем по кнопке КУПИТЬ и возвращаем страницу ОПЛАТА КАРТОЙ
    public CardPaymentPage openCardPaymentPage() {
        //кликаем по кнопке КУПИТЬ
        buttonBuy.click();
        return new CardPaymentPage();
    }
}
