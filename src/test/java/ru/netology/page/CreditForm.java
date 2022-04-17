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
    private SelenideElement monthCard = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearCard = $(byText("Год")).parent().$(".input__control");
    private SelenideElement nameAndSurnameCard = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvvCode = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement codeCardError = cvvCode.parent().parent().$(byText("Неверный формат"));
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private SelenideElement notificationOK = $(".notification_status_ok");
    private final SelenideElement notificationError = $(".notification_status_error");
    private final SelenideElement notificationErrorCloseButton = $(".notification_status_error").$(".notification__closer");
    private final SelenideElement inputInvalid = $(".input__sub");


    public void fillCreditForm(Card card) {
        numberCard.setValue(card.getNumber());
        monthCard.setValue(card.getMonth());
        yearCard.setValue(card.getYear());
        nameAndSurnameCard.setValue(card.getHolder());
        cvvCode.setValue(card.getCvc());
        continueButton.click();
    }

    // Ожидание появления всплывающего окна об успехе операции
    public void notificationOkIsVisibleInCredit() {
        // Железо слабое, поэтому период ожидания увеличен
        notificationOK.shouldBe(visible, Duration.ofSeconds(30));
    }

    // Ожидание появления всплывающего окна об отказе в проведении операции
    public void notificationErrorIsVisibleInCredit() {
        // Железо слабое, поэтому период ожидания увеличен
        notificationError.shouldBe(visible, Duration.ofSeconds(30));
    }

    //нажатие кнопки закрытия окна об ошибке
    public void closeNotificationWindowInCredit (){
        notificationErrorCloseButton.click();
    }

    //окно об успехе операции не появляется/невидимое
    public void notificationOkIsHiddenInCredit() {
        notificationOK.shouldBe(hidden);
    }

    // Получение текста сообщения об ошибке под полем ввода
    public String getInputInvalidMessageInCredit() {
        return inputInvalid.getText();
    }
}


