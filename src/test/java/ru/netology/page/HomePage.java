package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class HomePage {
    private final SelenideElement buttonBuyCard = $$("button").find(exactText("Купить"));
    private final SelenideElement buttonBuyCredit = $$("button").find(exactText("Купить в кредит"));

    public HomePage() {
        SelenideElement heading = $(".heading_size_l");
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
