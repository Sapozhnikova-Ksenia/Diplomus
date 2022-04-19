package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditForm {
    private SelenideElement numberCard = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement numberCardErrorMessage = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement monthCard = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement monthCardErrorMessage = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement yearCard = $(byText("Год")).parent().$(".input__control");
    private SelenideElement yearCardErrorMessage = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement nameAndSurnameCard = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement nameAndSurnameCardErrorMessage = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement cvvCode = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement cvvCodeErrorMessage = $(byText("CVC/CVV")).parent().$(".input__sub");
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private SelenideElement notificationOK = $(".notification_status_ok");
    private final SelenideElement notificationError = $(".notification_status_error");
    private final SelenideElement notificationErrorCloseButton = $(".notification_status_error").$(".notification__closer");
    private final SelenideElement inputInvalid = $(".input__sub");

    public CreditForm() {
        SelenideElement heading = $$("h3").find(text("Кредит по данным карты"));
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

    // Получение текста сообщения об ошибке под полем ввода
    public String getInputInvalidMessage() {
        return inputInvalid.getText();
    }


    ///////////////////// Поле Номер карты/////////////////////

    // Видимость сообщения об ошибке под полем ввода номера карты
    public void messageUnderCardNumberFieldIsVisible() {
        numberCardErrorMessage.shouldBe(visible);
    }

    // Не видимость сообщения об ошибке под полем ввода номера карты
    public void messageUnderCardNumberFieldIsHidden() {
        numberCardErrorMessage.shouldBe(hidden);
    }

    // Получение текста сообщения об ошибке под полем ввода номера карты
    public String getMessageUnderCardNumberField() {
        return numberCardErrorMessage.getText();
    }

    ///////////////////// Поле Месяц /////////////////////

    // Видимость сообщения об ошибке под полем ввода месяца карты
    public void messageUnderMonthFieldIsVisible() {
        monthCardErrorMessage.shouldBe(visible);
    }

    // Не видимость сообщения об ошибке под полем ввода месяца карты
    public void messageUnderMonthFieldIsHidden() {
        monthCardErrorMessage.shouldBe(hidden);
    }

    // Получение текста сообщения об ошибке под полем ввода месяца карты
    public String getMessageUnderMonthField() {
        return monthCardErrorMessage.getText();
    }

    ///////////////////// Поле Год /////////////////////

    // Видимость сообщения об ошибке под полем ввода года карты
    public void messageUnderYearFieldIsVisible() {
        yearCardErrorMessage.shouldBe(visible);
    }

    // Не видимость сообщения об ошибке под полем ввода года карты
    public void messageUnderYearFieldIsHidden() {
        yearCardErrorMessage.shouldBe(hidden);
    }

    // Получение текста сообщения об ошибке под полем ввода года карты
    public String getMessageUnderYearField() {
        return yearCardErrorMessage.getText();
    }
    ///////////////////// Поле Владелец/////////////////////

    // Видимость сообщения об ошибке под полем ввода Владелец
    public void messageUnderHolderFieldIsVisible() {
        nameAndSurnameCardErrorMessage.shouldBe(visible);
    }

    // Не видимость сообщения об ошибке под полем ввода Владелец
    public void messageUnderHolderFieldIsHidden() {
        nameAndSurnameCardErrorMessage.shouldBe(hidden);
    }

    // Получение текста сообщения об ошибке под полем ввода Владелец
    public String getMessageUnderCardHolderField() {
        return nameAndSurnameCardErrorMessage.getText();
    }

    ///////////////////// Поле CVV/////////////////////

    // Видимость сообщения об ошибке под полем ввода CVV
    public void messageUnderCVVFieldIsVisible() {
        cvvCodeErrorMessage.shouldBe(visible);
    }

    // Не видимость сообщения об ошибке под полем ввода CVV
    public void messageUnderCVVFieldIsHidden() {
        cvvCodeErrorMessage.shouldBe(hidden);
    }

    // Получение текста сообщения об ошибке под полем ввода CVV
    public String getMessageUnderCVVField() {
        return cvvCodeErrorMessage.getText();
    }

}

