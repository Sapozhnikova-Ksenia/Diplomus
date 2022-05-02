package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardForm {
    private final SelenideElement numberCard = $(byText("Номер карты")).parent().$(".input__control");
    private final SelenideElement numberCardErrorMessage = $(byText("Номер карты")).parent().$(".input__sub");
    private final SelenideElement monthCard = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement monthCardErrorMessage = $(byText("Месяц")).parent().$(".input__sub");
    private final SelenideElement yearCard = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement yearCardErrorMessage = $(byText("Год")).parent().$(".input__sub");
    private final SelenideElement nameAndSurnameCard = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement nameAndSurnameCardErrorMessage = $(byText("Владелец")).parent().$(".input__sub");
    private final SelenideElement cvvCode = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement cvvCodeErrorMessage = $(byText("CVC/CVV")).parent().$(".input__sub");
    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private final SelenideElement notificationOK = $(".notification_status_ok");
    private final SelenideElement notificationError = $(".notification_status_error");
    private final SelenideElement notificationErrorCloseButton = $(".notification_status_error").$(".notification__closer");

    public CardForm() {
        SelenideElement heading = $$("h3").find(text("Оплата по карте"));
        heading.shouldBe(visible);
    }

    public void fillForm(Card card) {
        numberCard.setValue(card.getNumber());
        monthCard.setValue(card.getMonth());
        yearCard.setValue(card.getYear());
        nameAndSurnameCard.setValue(card.getHolder());
        cvvCode.setValue(card.getCvc());
        continueButton.click();
    }

    // Ожидание появления всплывающего окна об успехе операции
    public void notificationOkIsVisible() {
        // Железо слабое, поэтому период ожидания увеличен
        notificationOK.shouldBe(visible, Duration.ofSeconds(30));
    }

    // Ожидание появления всплывающего окна об отказе в проведении операции
    public void notificationErrorIsVisible() {
        // Железо слабое, поэтому период ожидания увеличен
        notificationError.shouldBe(visible, Duration.ofSeconds(30));
    }

    //нажатие кнопки закрытия окна об ошибке
    public void closeNotificationWindow() {
        notificationErrorCloseButton.click();
    }

    //окно об успехе операции не появляется/невидимое
    public void notificationOkIsHidden() {
        notificationOK.shouldBe(hidden);
    }


    ///////////////////// Поле Номер карты/////////////////////

    // Видимость сообщения и текста ошибки под полем ввода номера карты
    public void messageUnderCardNumberField(String message) {
        numberCardErrorMessage.shouldHave(text(message)).shouldBe(visible);
    }


    ///////////////////// Поле Месяц /////////////////////

    // Видимость сообщения и текста ошибки под полем ввода месяца карты
    public void messageUnderMonthField(String message) {
        monthCardErrorMessage.shouldHave(text(message)).shouldBe(visible);
    }

    ///////////////////// Поле Год /////////////////////

    // Видимость сообщения и текста ошибки под полем ввода года карты
    public void messageUnderYearField(String message) {
        yearCardErrorMessage.shouldHave(text(message)).shouldBe(visible);
    }

    ///////////////////// Поле Владелец /////////////////////

    // Видимость сообщения и текста ошибки под полем ввода Владелец
    public void messageUnderHolderField(String message) {
        nameAndSurnameCardErrorMessage.shouldHave(text(message)).shouldBe(visible);
    }

    // Не видимость сообщения об ошибке под полем ввода Владелец
    public void messageUnderHolderFieldIsHidden() {
        nameAndSurnameCardErrorMessage.shouldBe(hidden);
    }

    ///////////////////// Поле CVV/////////////////////

    // Видимость сообщения и текста ошибки под полем ввода CVV
    public void messageUnderCVVField(String message) {
        cvvCodeErrorMessage.shouldHave(text(message)).shouldBe(visible);
    }

}