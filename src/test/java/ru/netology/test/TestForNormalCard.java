package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DBHelper;
import ru.netology.data.DataGenerator;
import ru.netology.page.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestForNormalCard {

    @BeforeEach
    void setUp() {
        Configuration.headless = true;
        open("http://localhost:8080/");
        DBHelper.cleanData();
    }


    //валидная карта
    @Test
    @DisplayName("+1 OK With Approved Card")
    public void shouldBeOKWithApprovedCard() {
        // Отрываем начальную страницу
        var homePage = new HomePage();
        // Переходим на страницу покупки по карте
        var cardForm = homePage.buyWithCard();
        // Заполняем поля таблицы валидными данными и жмём ПРОДОЛЖИТЬ
        cardForm.fillForm(DataGenerator.getApprovedCard());
        // Проверка появления всплывающего окна одобрения
        cardForm.notificationOkIsVisible();
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        // Проверка статуса операции в БД
        assertEquals("APPROVED", DBHelper.getPaymentStatus());
    }

    //невалидная карта
    @Test
    @DisplayName("-2 Declined with declined card   BUG !!!")
    public void shouldBeDeclinedWithDeclinedCard() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getDeclinedCard());
        cardForm.notificationErrorIsVisible();
        cardForm.messageUnderCardNumberFieldIsVisible();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertEquals("DECLINED", DBHelper.getPaymentStatus());
    }

    //неизвестная карта
    @Test
    @DisplayName("-3 Declined with unknown card   BUG !!! ")
    public void shouldBeDeclinedWithUnknownCard() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getUnknownCard());
        cardForm.notificationErrorIsVisible();
        cardForm.closeNotificationWindow();
        cardForm.notificationOkIsHidden();
        cardForm.messageUnderCardNumberFieldIsVisible();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        // Проверка НЕ появления записи в БД
        assertNull(DBHelper.getPaymentStatus());
    }

    //отправка пустой формы
    @Test
    @DisplayName("-4 Declined with Full Empty Field   ISSUES !!  ")
    public void shouldBeDeclinedWithFullEmptyField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getFullEmptyField());
        cardForm.messageUnderCardNumberFieldIsVisible();
        assertEquals("Поле обязательно для заполнения", cardForm.getMessageUnderCardNumberField());
        cardForm.messageUnderMonthFieldIsVisible();
        assertEquals("Поле обязательно для заполнения", cardForm.getMessageUnderMonthField());
        cardForm.messageUnderYearFieldIsVisible();
        assertEquals("Поле обязательно для заполнения", cardForm.getMessageUnderYearField());
        cardForm.messageUnderHolderFieldIsVisible();
        assertEquals("Поле обязательно для заполнения", cardForm.getMessageUnderCardHolderField());
        cardForm.messageUnderCVVFieldIsVisible();
        assertEquals("Поле обязательно для заполнения", cardForm.getMessageUnderCVVField());
        assertNull(DBHelper.getPaymentStatus());
    }

////////////////////Card

    //некорректное сообщение об ошибке при пустом поле ввода карты
    @Test
    @DisplayName("- 5 Incorrect warning with empty Card Number Field   ISSUES !!")
    public void shouldShowWarningUnderCardNumberFieldWithEmptyCardNumberField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithEmptyCardNumber());
        cardForm.messageUnderCardNumberFieldIsVisible();
        assertEquals("Поле обязательно для заполнения", cardForm.getInputInvalidMessage());
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //сообщение об ошибке при неполном заполнении поля ввода карты
    @Test
    @DisplayName("+ 6 Correct warning with don't full Card Number Field")
    public void shouldShowWarningUnderCardNumberFieldWithDontFullCardNumberField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithDontFullCardNumber());
        cardForm.messageUnderCardNumberFieldIsVisible();
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //сообщение об ошибке при заполнении поля ввода карты латиницей, кириллицей и символами
    @Test
    @DisplayName("+ 7 Correct warning with uncorrect simbol in Card Number Field")
    public void shouldShowWarningUnderCardNumberFieldWithUncorrectSimbolInCardNumberField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithLatinAndKirillicAndSupersimbolCardNumber());
        cardForm.messageUnderCardNumberFieldIsVisible();
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

////////////////////Month

    //некорректное сообщение об ошибке при пустом поле ввода месяца
    @Test
    @DisplayName("- 8 Incorrect warning with empty Month Field   ISSUES !!")
    public void shouldShowWarningUnderMonthFieldWithEmptyMonthField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithEmptyMonth());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsVisible();
        assertEquals("Поле обязательно для заполнения", cardForm.getInputInvalidMessage());
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //сообщение о неполном вводе месяца (1 цифра)
    @Test
    @DisplayName("+ 9 Correct warning with don't full Month Field")
    public void shouldShowWarningUnderMonthFieldWithDontFullMonthField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithDontFullMonth());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsVisible();
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение значения в поле месяц выше допустимого (например 13)
    @Test
    @DisplayName("+ 10 Correct warning with more limit Month Field")
    public void shouldShowWarningUnderMonthFieldWithOverFullMonthField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithMoreLimitMonth());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsVisible();
        assertEquals("Неверно указан срок действия карты", cardForm.getInputInvalidMessage());
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //появление всплывающего окна об одобрении банком при вводе в поле месяц 00, ожидается ошибка БАГ
    @Test
    @DisplayName("- 11 Correct warning with DublZero Month Field  BUG !!! ")
    public void shouldShowWarningUnderMonthFieldWithDublZeroInMonthField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithDublZeroInMonth());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsVisible();
        assertEquals("Неверно указан срок действия карты", cardForm.getInputInvalidMessage());
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        cardForm.notificationOkIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение устаревшего (минувшего) значения в поле месяц при  значении текущего года (например 01)
    @Test
    @DisplayName("- 12 Correct warning with Old Month But Current Year  ISSUES !!")
    public void shouldShowWarningUnderMonthFieldWithOldMonthButCurrentYear() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithOldMonthButCurrentYear());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsVisible();
        assertEquals("Истёк срок действия карты", cardForm.getInputInvalidMessage());
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //При введении символа, букв латиницы, кириллицы - ввод в поле Месяца не осуществляется, при отправке
    // формы должен появляться текст о неверном формате
    @Test
    @DisplayName("+ 13 Correct warning with empty Month Field if in field will be latin kirillic and symbol")
    public void shouldShowWarningUnderMonthFieldWithEmptyMonthFieldIfInFieldWillBeLatinAndKirillicAndSymbol() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithLatinAndKirillicAndSymbolInMonthField());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsVisible();
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

////////////////////Year

    //некорректное сообщение об ошибке при пустом поле ввода года
    @Test
    @DisplayName("- 14 Incorrect warning with empty Year Field  ISSUES !! ")
    public void shouldShowWarningUnderYearFieldWithEmptyYearField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithEmptyYear());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsVisible();
        assertEquals("Поле обязательно для заполнения", cardForm.getInputInvalidMessage());
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //сообщение о неполном вводе года (1 цифра)
    @Test
    @DisplayName("+ 15 Correct warning with don't full Year Field")
    public void shouldShowWarningUnderYearFieldWithDontFullYearField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithDontFullYear());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsVisible();
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение значения в поле года выше допустимого (например 26)
    @Test
    @DisplayName("- 16 UnCorrect warning with more limit Year Field   BUG !!!")
    public void shouldShowWarningUnderYearFieldWithOverFullYearField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithMoreLimitYear());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsVisible();
        assertEquals("Неверно указан срок действия карты", cardForm.getInputInvalidMessage());
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение устаревшего (минувшего) значения в поле года (например 21)
    @Test
    @DisplayName("+ 17 Correct warning with Old Year")
    public void shouldShowWarningUnderYearFieldWithOldYear() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithOldYear());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsVisible();
        assertEquals("Истёк срок действия карты", cardForm.getInputInvalidMessage());
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение нулевого значения в поле года (00)
    @Test
    @DisplayName("- 18 Uncorrect warning with DublZero Year Field   ISSUES !! ")
    public void shouldShowWarningUnderYearFieldWithDublZeroInYearField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithDublZeroInYear());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsVisible();
        assertEquals("Неверно указан срок действия карты", cardForm.getInputInvalidMessage());
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        cardForm.notificationOkIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //При введении символа, букв латиницы, кириллицы - ввод в поле Год не осуществляется, при отправке
    // формы должен появляться текст о неверном формате
    @Test
    @DisplayName("+ 19 Correct warning with empty Year Field if in field will be latin kirillic and symbol")
    public void shouldShowWarningUnderYearFieldWithEmptyYearFieldIfInFieldWillBeLatinAndKirillicAndSymbol() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithLatinAndKirillicAndSymbolInYearField());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsVisible();
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

////////////////////Name & Surname

    //корректное сообщение об ошибке при пустом поле ввода владельца карты
    @Test
    @DisplayName("+ 20 Correct warning with empty Name & Surname Field")
    public void shouldShowWarningUnderFieldNameAndSurnameWithEmptyNameAndSurnameField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithEmptyNameAndSurname());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsVisible();
        assertEquals("Поле обязательно для заполнения", cardForm.getInputInvalidMessage());
        cardForm.messageUnderCVVFieldIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение количества букв в поле владельца карты выше допустимого (например 71)
    @Test
    @DisplayName("- 21 Dont have warning with more limit letter in Name & Surname   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithOverLimitNumberOfLettersInNameAndSurnameField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithOverLimitNumberOfLetters());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsVisible();
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
        cardForm.messageUnderCVVFieldIsHidden();
        cardForm.notificationOkIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение количества букв в поле владельца карты ниже допустимого (например 1)
    @Test
    @DisplayName("- 22 Dont have warning with under limit letter in Name & Surname   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithUnderLimitNumberOfLettersInNameAndSurnameField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithUnderLimitNumberOfLetters());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsVisible();
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
        cardForm.messageUnderCVVFieldIsHidden();
        cardForm.notificationOkIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение допустимого количества букв в поле владельца карты без использования пробела, таким образом получая ввод
    // только имени, но формально без фамилии (KseniyaSapojok)
    @Test
    @DisplayName("- 23 Dont have warning with written Name but dont written Surname because dont use space   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithWrittenNameButDontWrittenSurnameBecauseDontUseSpaceInNameAndSurnameField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithWrittenNameButDontWrittenSurnameBecauseDontUseSpace());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsVisible();
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
        cardForm.messageUnderCVVFieldIsHidden();
        cardForm.notificationOkIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение допустимого количества букв в поле владельца карты кириллицей (Ксенечка Сапожок)
    @Test
    @DisplayName("- 24 Dont have warning with written Name & Surname in kirillic symbol   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithWrittenKirillicSymbolInNameAndSurnameField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithWrittenKirillicSymbol());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsVisible();
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
        cardForm.messageUnderCVVFieldIsHidden();
        cardForm.notificationOkIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение допустимого количества букв в поле владельца карты латинницей, но с использованием числовых и символьных
    // выражений (Ksen19+ ,,Sap0j0k)
    @Test
    @DisplayName("- 25 Dont have warning with written Name & Surname in latin letter but with use numbers and symbol   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithWrittenLatinicLetterButWithUseNumberAndSymbolInNameAndSurnameField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getWrittenLatinicLetterButWithUseNumberAndSymbol());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsVisible();
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
        cardForm.messageUnderCVVFieldIsHidden();
        cardForm.notificationOkIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

////////////////////CVV

    //некорректное сообщение об ошибке при отправке пустого поля CVV
    // всплывает две ошибки
    @Test
    @DisplayName("- 26 Incorrect warning with empty CVV Field  BUG !!!")
    public void shouldShowWarningWithEmptyCVV() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithEmptyCVV());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsVisible();
        assertEquals("Поле обязательно для заполнения", cardForm.getMessageUnderCVVField());
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение количества цифр в поле CVV ниже допустимого (например 1)
    @Test
    @DisplayName("+ 27 Correct warning with under limit number in CVV Field ")
    public void shouldShowWarningUnderCVVFieldWithUnderLimitNumberInCVVField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithUnderLimitNumberOfCVV());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsVisible();
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение нулевого значения в поле CVV (000)
    @Test
    @DisplayName("- 28 Dont have warning with written FullZero in CVV Field    BUG !!!")
    public void shouldShowWarningUnderCVVFieldWithWrittenFullZeroInCVVField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithWrittenFullZero());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsVisible();
        assertEquals("Неверно указан CVC/CVV код", cardForm.getInputInvalidMessage());
        cardForm.notificationOkIsHidden();
        assertNull(DBHelper.getPaymentStatus());
    }

    //введение допустимого количества символов в поле CVV, но с использованием латинницы, кириллицы и символов
    // (ЙQ+)
    //поскольку любые символы и буквы (кроме цифр)- не вводятся в поле, оно остается пустым
    @Test
    @DisplayName("- 29 Uncorrect warning with written CVV in latin and Kirillic letter and with use symbol ")
    public void shouldShowWarningUnderCVVFieldWithWrittenLatinicAndKirillicLetterWithUseSymbolInCVVField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWrittenLatinicAndKirillicLetterWithUseSymbol());
        cardForm.messageUnderCardNumberFieldIsHidden();
        cardForm.messageUnderMonthFieldIsHidden();
        cardForm.messageUnderYearFieldIsHidden();
        cardForm.messageUnderHolderFieldIsHidden();
        cardForm.messageUnderCVVFieldIsVisible();
        assertEquals("Поле обязательно для заполнения", cardForm.getInputInvalidMessage());
        assertNull(DBHelper.getPaymentStatus());
    }

}
