package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {
    private SelenideElement heading = $(".heading_size_l");
    private SelenideElement buttonBuyCard = $("[class='button button_size_m button_theme_alfa-on-white']");
    private SelenideElement buttonBuyCredit = $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']");

    public HomePage() {
        heading.shouldBe(visible);
    }

    public CardForm buyWithCard() {
        buttonBuyCard.click();
        return new CardForm();

    }

    public CreditForm buyWithCredit() {
        buttonBuyCredit.click();
        return new CreditForm();

    }

}
