package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.page.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestForNormalCard {

    @BeforeEach
    void setUp() {
        Configuration.headless = true;
        open("http://localhost:8080/");
    }


    //валидная карта
    @Test
    @DisplayName("+ OK With Approved Card")
    public void shouldBeOKWithApprovedCard() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getApprovedCard());
        cardForm.notificationOkIsVisible();
    }

    //невалидная карта
    @Test
    @DisplayName("- Declined with declined card   BUG !!!")
    public void shouldBeDeclinedWithDeclinedCard() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getDeclinedCard());
        cardForm.notificationErrorIsVisible();
    }

    //неизвестная карта
    @Test
    @DisplayName("- Declined with unknown card   BUG !!! ")
    public void shouldBeDeclinedWithUnknownCard() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getUnknownCard());
        cardForm.notificationErrorIsVisible();
        cardForm.closeNotificationWindow();
        cardForm.notificationOkIsHidden();
    }

    //отправка пустой формы!!!!!!!!!!!!!!!!!!!!!!!!!!! как прописать по каждому комменту под полями
    //ожидаю что поля обязательны для заполнения, по факту поле владелец так,а остальные поля  неверный формат
    // ------------------------------------------------------------------------------------для ДОП РАЗБОРА
    @Test
    @DisplayName("- Declined with Full Empty Field ")
    public void shouldBeDeclinedWithFullEmptyField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getFullEmptyField());
        assertEquals("Поле обязательно для заполнения", cardForm.getInputInvalidMessage());

    }

////////////////////Card

    //некорректное сообщение об ошибке при пустом поле ввода карты
    @Test
    @DisplayName("- Incorrect warning with empty Card Number Field")
    public void shouldShowWarningUnderCardNumberFieldWithEmptyCardNumberField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithEmptyCardNumber());
        assertEquals("Поле обязательно для заполнения", cardForm.getInputInvalidMessage());
    }

    //сообщение об ошибке при неполном заполнении поля ввода карты
    @Test
    @DisplayName("+ Correct warning with don't full Card Number Field")
    public void shouldShowWarningUnderCardNumberFieldWithDontFullCardNumberField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithDontFullCardNumber());
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
    }

    //сообщение об ошибке при заполнении поля ввода карты латиницей, кириллицей и символами
    @Test
    @DisplayName("+ Correct warning with uncorrect simbol in Card Number Field")
    public void shouldShowWarningUnderCardNumberFieldWithUncorrectSimbolInCardNumberField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithLatinAndKirillicAndSupersimbolCardNumber());
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
    }

////////////////////Month

    //некорректное сообщение об ошибке при пустом поле ввода месяца
    @Test
    @DisplayName("- Incorrect warning with empty Month Field")
    public void shouldShowWarningUnderMonthFieldWithEmptyMonthField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithEmptyMonth());
        assertEquals("Поле обязательно для заполнения", cardForm.getInputInvalidMessage());
    }

    //сообщение о неполном вводе месяца (1 цифра)
    @Test
    @DisplayName("+ Correct warning with don't full Month Field")
    public void shouldShowWarningUnderMonthFieldWithDontFullMonthField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithDontFullMonth());
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
    }

    //введение значения в поле месяц выше допустимого (например 13)
    @Test
    @DisplayName("+ Correct warning with more limit Month Field")
    public void shouldShowWarningUnderMonthFieldWithOverFullMonthField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithMoreLimitMonth());
        assertEquals("Неверно указан срок действия карты", cardForm.getInputInvalidMessage());
    }

    //появление всплывающего окна об одобрении банком при вводе в поле месяц 00, ожидается ошибка БАГ
    @Test
    @DisplayName("- Correct warning with DublZero Month Field  BUG !!! ")
    public void shouldShowWarningUnderMonthFieldWithDublZeroInMonthField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithDublZeroInMonth());
        assertEquals("Неверно указан срок действия карты", cardForm.getInputInvalidMessage());
        cardForm.notificationOkIsHidden();
    }

    //введение устаревшего (минувшего) значения в поле месяц при  значении текущего года (например 01)
    @Test
    @DisplayName("- Correct warning with Old Month But Current Year")
    public void shouldShowWarningUnderMonthFieldWithOldMonthButCurrentYear() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithOldMonthButCurrentYear());
        assertEquals("Истёк срок действия карты", cardForm.getInputInvalidMessage());
    }

    //При введении символа, букв латиницы, кириллицы - ввод в поле Месяца не осуществляется, при отправке
    // формы должен появляться текст о неверном формате
    @Test
    @DisplayName("+ Correct warning with empty Month Field if in field will be latin kirillic and symbol")
    public void shouldShowWarningUnderMonthFieldWithEmptyMonthFieldIfInFieldWillBeLatinAndKirillicAndSymbol() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithLatinAndKirillicAndSymbolInMonthField());
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
    }

////////////////////Year

    //некорректное сообщение об ошибке при пустом поле ввода года
    @Test
    @DisplayName("- Incorrect warning with empty Year Field")
    public void shouldShowWarningUnderYearFieldWithEmptyYearField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithEmptyYear());
        assertEquals("Поле обязательно для заполнения", cardForm.getInputInvalidMessage());
    }

    //сообщение о неполном вводе года (1 цифра)
    @Test
    @DisplayName("+ Correct warning with don't full Year Field")
    public void shouldShowWarningUnderYearFieldWithDontFullYearField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithDontFullYear());
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
    }

    //введение значения в поле года выше допустимого (например 26)
    @Test
    @DisplayName("- UnCorrect warning with more limit Year Field   BUG !!!")
    public void shouldShowWarningUnderYearFieldWithOverFullYearField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithMoreLimitYear());
        assertEquals("Неверно указан срок действия карты", cardForm.getInputInvalidMessage());
    }

    //введение устаревшего (минувшего) значения в поле года (например 21)
    @Test
    @DisplayName("+ Correct warning with Old Year")
    public void shouldShowWarningUnderYearFieldWithOldYear() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithOldYear());
        assertEquals("Истёк срок действия карты", cardForm.getInputInvalidMessage());
    }

    //введение нулевого значения в поле года (00)
    @Test
    @DisplayName("- Correct warning with DublZero Year Field ")
    public void shouldShowWarningUnderYearFieldWithDublZeroInYearField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithDublZeroInYear());
        assertEquals("Неверно указан срок действия карты", cardForm.getInputInvalidMessage());
        cardForm.notificationOkIsHidden();
    }

    //При введении символа, букв латиницы, кириллицы - ввод в поле Год не осуществляется, при отправке
    // формы должен появляться текст о неверном формате
    @Test
    @DisplayName("+ Correct warning with empty Year Field if in field will be latin kirillic and symbol")
    public void shouldShowWarningUnderYearFieldWithEmptyYearFieldIfInFieldWillBeLatinAndKirillicAndSymbol() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithLatinAndKirillicAndSymbolInYearField());
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
    }

////////////////////Name & Surname

    //корректное сообщение об ошибке при пустом поле ввода владельца карты
    @Test
    @DisplayName("+ Correct warning with empty Name & Surname Field")
    public void shouldShowWarningUnderFieldNameAndSurnameWithEmptyNameAndSurnameField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithEmptyNameAndSurname());
        assertEquals("Поле обязательно для заполнения", cardForm.getInputInvalidMessage());
    }

    //введение количества букв в поле владельца карты выше допустимого (например 71)
    @Test
    @DisplayName("- Dont have warning with more limit letter in Name & Surname   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithOverLimitNumberOfLettersInNameAndSurnameField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithOverLimitNumberOfLetters());
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
    }

    //введение количества букв в поле владельца карты ниже допустимого (например 1)
    @Test
    @DisplayName("- Dont have warning with under limit letter in Name & Surname   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithUnderLimitNumberOfLettersInNameAndSurnameField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithUnderLimitNumberOfLetters());
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
    }

    //введение допустимого количества букв в поле владельца карты без использования пробела, таким образом получая ввод
    // только имени, но формально без фамилии (KseniyaSapojok)
    @Test
    @DisplayName("- Dont have warning with written Name but dont written Surname because dont use space   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithWrittenNameButDontWrittenSurnameBecauseDontUseSpaceInNameAndSurnameField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithWrittenNameButDontWrittenSurnameBecauseDontUseSpace());
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
    }

    //введение допустимого количества букв в поле владельца карты кириллицей (KseniyaSapojok)
    @Test
    @DisplayName("- Dont have warning with written Name & Surname in kirillic symbol   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithWrittenKirillicSymbolInNameAndSurnameField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithWrittenKirillicSymbol());
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
    }

    //введение допустимого количества букв в поле владельца карты латинницей, но с использованием числовых и символьных
    // выражений (Ksen19+ ,,Sap0j0k)
    @Test
    @DisplayName("- Dont have warning with written Name & Surname in latin letter but with use numbers and symbol   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithWrittenLatinicLetterButWithUseNumberAndSymbolInNameAndSurnameField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.WrittenLatinicLetterButWithUseNumberAndSymbol());
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
    }

////////////////////CVV

    //некорректное сообщение об ошибке при отправке пустого поля CVV
    // всплывает две ошибки, у CVV ошибка не та, а ошибка про поле для заполнения всплывает у поля владелец,
    // хотя оно заполнено-------------------------------------------------------------------для ДОП РАЗБОРА
    @Test
    @DisplayName("+ - Incorrect warning with empty CVV Field  BUG !!!")
    public void shouldShowWarningWithEmptyCVV() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithEmptyCVV());
        assertEquals("Поле обязательно для заполнения", cardForm.getInputInvalidMessage());
    }

    //введение количества цифр в поле CVV ниже допустимого (например 1)
    @Test
    @DisplayName("+ Correct warning with under limit number in CVV Field ")
    public void shouldShowWarningUnderCVVFieldWithUnderLimitNumberInCVVField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithUnderLimitNumberOfCVV());
        assertEquals("Неверный формат", cardForm.getInputInvalidMessage());
    }

    //введение нулевого значения в поле CVV (000)
    @Test
    @DisplayName("- Dont have warning with written FullZero in CVV Field    BUG !!!")
    public void shouldShowWarningUnderCVVFieldWithWrittenFullZeroInCVVField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWithWrittenFullZero());
        assertEquals("Неверно указан CVC/CVV код", cardForm.getInputInvalidMessage());
        cardForm.notificationOkIsHidden();
    }

    //введение допустимого количества символов в поле CVV, но с использованием латинницы, кириллицы и символов
    // (ЙQ+)
    //поскольку любые символы и буквы (кроме цифр)- не вводятся в поле, оно остается пустым
    @Test
    @DisplayName("+ Correct warning with written CVV in latin and Kirillic letter and with use symbol")
    public void shouldShowWarningUnderCVVFieldWithWrittenLatinicAndKirillicLetterWithUseSymbolInCVVField() {
        var homePage = new HomePage();
        var cardForm = homePage.buyWithCard();
        cardForm.fillForm(DataGenerator.getCardWrittenLatinicAndKirillicLetterWithUseSymbol());
        assertEquals("Поле обязательно для заполнения", cardForm.getInputInvalidMessage());
    }

}
