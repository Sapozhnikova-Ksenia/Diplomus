package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.page.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestForCreditCard {

    @BeforeEach
    void setUp() {
        //Configuration.headless = true;
        open("http://localhost:8080/");
    }

    //валидная карта
    @Test
    @DisplayName("+ OK With Approved Credit Card")
    public void shouldBeOKWithApprovedCreditCard() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getApprovedCard());
        creditForm.notificationOkIsVisibleInCredit();
    }

    //невалидная карта
    @Test
    @DisplayName("- Declined with declined credit card   BUG !!!")
    public void shouldBeDeclinedWithDeclinedCreditCard() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getDeclinedCard());
        creditForm.notificationErrorIsVisibleInCredit();
    }

    //неизвестная карта
    @Test
    @DisplayName("- Declined with unknown credit card   BUG !!!")
    public void shouldBeDeclinedWithUnknownCreditCard() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getUnknownCard());
        creditForm.notificationErrorIsVisibleInCredit();
        creditForm.closeNotificationWindowInCredit();
        creditForm.notificationOkIsHiddenInCredit();
    }

    //отправка пустой формы!!!!!!!!!!!!!!!!!!!!!!!!!!! как прописать по каждому комменту под полями
    //ожидаю что поля обязательны для заполнения, по факту поле владелец так,а остальные поля  неверный формат
    // ------------------------------------------------------------------------------------для ДОП РАЗБОРА
    @Test
    @DisplayName("- Declined with Full Empty Field in credit ")
    public void shouldBeDeclinedWithFullEmptyFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getFullEmptyField());
        assertEquals("Поле обязательно для заполнения", creditForm.getInputInvalidMessageInCredit());

    }

////////////////////Card

    //некорректное сообщение об ошибке при пустом поле ввода карты
    @Test
    @DisplayName("- Incorrect warning with empty Card Number Field In Credit")
    public void shouldShowWarningUnderCardNumberFieldWithEmptyCardNumberFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithEmptyCardNumber());
        assertEquals("Поле обязательно для заполнения", creditForm.getInputInvalidMessageInCredit());
    }

    //сообщение об ошибке при неполном заполнении поля ввода карты
    @Test
    @DisplayName("+ Correct warning with don't full Card Number Field In Credit")
    public void shouldShowWarningUnderCardNumberFieldWithDontFullCardNumberFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithDontFullCardNumber());
        assertEquals("Неверный формат", creditForm.getInputInvalidMessageInCredit());
    }

    //сообщение об ошибке при заполнении поля ввода карты латиницей, кириллицей и символами
    @Test
    @DisplayName("+ Correct warning with uncorrect simbol in Card Number Field In Credit")
    public void shouldShowWarningUnderCardNumberFieldWithUncorrectSimbolInCardNumberFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithLatinAndKirillicAndSupersimbolCardNumber());
        assertEquals("Неверный формат", creditForm.getInputInvalidMessageInCredit());
    }

////////////////////Month

    //некорректное сообщение об ошибке при пустом поле ввода месяца
    @Test
    @DisplayName("- Incorrect warning with empty Month Field In Credit")
    public void shouldShowWarningUnderMonthFieldWithEmptyMonthFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithEmptyMonth());
        assertEquals("Поле обязательно для заполнения", creditForm.getInputInvalidMessageInCredit());
    }

    //сообщение о неполном вводе месяца (1 цифра)
    @Test
    @DisplayName("+ Correct warning with don't full Month Field In Credit")
    public void shouldShowWarningUnderMonthFieldWithDontFullMonthFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithDontFullMonth());
        assertEquals("Неверный формат", creditForm.getInputInvalidMessageInCredit());
    }

    //введение значения в поле месяц выше допустимого (например 13)
    @Test
    @DisplayName("+ Correct warning with more limit Month Field In Credit")
    public void shouldShowWarningUnderMonthFieldWithOverFullMonthFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithMoreLimitMonth());
        assertEquals("Неверно указан срок действия карты", creditForm.getInputInvalidMessageInCredit());
    }

    //появление всплывающего окна об одобрении банком при вводе в поле месяц 00, ожидается ошибка БАГ
    @Test
    @DisplayName("- Correct warning with DublZero Month Field In Credit  BUG !!! ")
    public void shouldShowWarningUnderMonthFieldWithDublZeroInMonthFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithDublZeroInMonth());
        assertEquals("Неверно указан срок действия карты", creditForm.getInputInvalidMessageInCredit());
        creditForm.notificationOkIsHiddenInCredit();
    }

    //введение устаревшего (минувшего) значения в поле месяц при  значении текущего года (например 01)
    @Test
    @DisplayName("- Correct warning with Old Month But Current Year In Credit")
    public void shouldShowWarningUnderMonthFieldWithOldMonthButCurrentYearInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithOldMonthButCurrentYear());
        assertEquals("Истёк срок действия карты", creditForm.getInputInvalidMessageInCredit());
    }

    //При введении символа, букв латиницы, кириллицы - ввод в поле Месяца не осуществляется, при отправке
    // формы должен появляться текст о неверном формате
    @Test
    @DisplayName("+ Correct warning with empty Month Field if in field will be latin kirillic and symbol In Credit")
    public void shouldShowWarningUnderMonthFieldWithEmptyMonthFieldIfInFieldWillBeLatinAndKirillicAndSymbolInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithLatinAndKirillicAndSymbolInMonthField());
        assertEquals("Неверный формат", creditForm.getInputInvalidMessageInCredit());
    }

////////////////////Year

    //некорректное сообщение об ошибке при пустом поле ввода года
    @Test
    @DisplayName("- Incorrect warning with empty Year Field In Credit")
    public void shouldShowWarningUnderYearFieldWithEmptyYearFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithEmptyYear());
        assertEquals("Поле обязательно для заполнения", creditForm.getInputInvalidMessageInCredit());
    }

    //сообщение о неполном вводе года (1 цифра)
    @Test
    @DisplayName("+ Correct warning with don't full Year Field In Credit")
    public void shouldShowWarningUnderYearFieldWithDontFullYearFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithDontFullYear());
        assertEquals("Неверный формат", creditForm.getInputInvalidMessageInCredit());
    }

    //введение значения в поле года выше допустимого (например 26)
    @Test
    @DisplayName("- UnCorrect warning with more limit Year Field In Credit   BUG !!!")
    public void shouldShowWarningUnderYearFieldWithOverFullYearFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithMoreLimitYear());
        assertEquals("Неверно указан срок действия карты", creditForm.getInputInvalidMessageInCredit());
    }

    //введение устаревшего (минувшего) значения в поле года (например 21)
    @Test
    @DisplayName("+ Correct warning with Old Year In Credit")
    public void shouldShowWarningUnderYearFieldWithOldYearInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithOldYear());
        assertEquals("Истёк срок действия карты", creditForm.getInputInvalidMessageInCredit());
    }

    //введение нулевого значения в поле года (00)
    @Test
    @DisplayName("- Correct warning with DublZero Year Field In Credit")
    public void shouldShowWarningUnderYearFieldWithDublZeroInYearFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithDublZeroInYear());
        assertEquals("Неверно указан срок действия карты", creditForm.getInputInvalidMessageInCredit());
        creditForm.notificationOkIsHiddenInCredit();
    }

    //При введении символа, букв латиницы, кириллицы - ввод в поле Год не осуществляется, при отправке
    // формы должен появляться текст о неверном формате
    @Test
    @DisplayName("+ Correct warning with empty Year Field if in field will be latin kirillic and symbol In Credit")
    public void shouldShowWarningUnderYearFieldWithEmptyYearFieldIfInFieldWillBeLatinAndKirillicAndSymbolInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithLatinAndKirillicAndSymbolInYearField());
        assertEquals("Неверный формат", creditForm.getInputInvalidMessageInCredit());
    }

////////////////////Name & Surname

    //корректное сообщение об ошибке при пустом поле ввода владельца карты
    @Test
    @DisplayName("+ Correct warning with empty Name & Surname Field In Credit")
    public void shouldShowWarningUnderFieldNameAndSurnameWithEmptyNameAndSurnameFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithEmptyNameAndSurname());
        assertEquals("Поле обязательно для заполнения", creditForm.getInputInvalidMessageInCredit());
    }

    //введение количества букв в поле владельца карты выше допустимого (например 71)
    @Test
    @DisplayName("- Dont have warning with more limit letter in Name & Surname In Credit   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithOverLimitNumberOfLettersInNameAndSurnameFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithOverLimitNumberOfLetters());
        assertEquals("Неверный формат", creditForm.getInputInvalidMessageInCredit());
    }

    //введение количества букв в поле владельца карты ниже допустимого (например 1)
    @Test
    @DisplayName("- Dont have warning with under limit letter in Name & Surname In Credit   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithUnderLimitNumberOfLettersInNameAndSurnameFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithUnderLimitNumberOfLetters());
        assertEquals("Неверный формат", creditForm.getInputInvalidMessageInCredit());
    }

    //введение допустимого количества букв в поле владельца карты без использования пробела, таким образом получая ввод
    // только имени, но формально без фамилии (KseniyaSapojok)
    @Test
    @DisplayName("- Dont have warning with written Name but dont written Surname because dont use space In Credit   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithWrittenNameButDontWrittenSurnameBecauseDontUseSpaceInNameAndSurnameFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithWrittenNameButDontWrittenSurnameBecauseDontUseSpace());
        assertEquals("Неверный формат", creditForm.getInputInvalidMessageInCredit());
    }

    //введение допустимого количества букв в поле владельца карты кириллицей (KseniyaSapojok)
    @Test
    @DisplayName("- Dont have warning with written Name & Surname in kirillic symbol In Credit  BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithWrittenKirillicSymbolInNameAndSurnameFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithWrittenKirillicSymbol());
        assertEquals("Неверный формат", creditForm.getInputInvalidMessageInCredit());
    }

    //введение допустимого количества букв в поле владельца карты латинницей, но с использованием числовых и символьных
    // выражений (Ksen19+ ,,Sap0j0k)
    @Test
    @DisplayName("- Dont have warning with written Name & Surname in latin letter but with use numbers and symbol In Credit   BUG !!!")
    public void shouldShowWarningUnderNameAndSurnameFieldWithWrittenLatinicLetterButWithUseNumberAndSymbolInNameAndSurnameFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.WrittenLatinicLetterButWithUseNumberAndSymbol());
        assertEquals("Неверный формат", creditForm.getInputInvalidMessageInCredit());
    }

////////////////////CVV

    //некорректное сообщение об ошибке при отправке пустого поля CVV
    // всплывает две ошибки, у CVV ошибка не та, а ошибка про поле для заполнения всплывает у поля владелец,
    // хотя оно заполнено-------------------------------------------------------------------для ДОП РАЗБОРА
    @Test
    @DisplayName("+ - Incorrect warning with empty CVV Field In Credit  BUG !!!")
    public void shouldShowWarningWithEmptyCVVInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithEmptyCVV());
        assertEquals("Поле обязательно для заполнения", creditForm.getInputInvalidMessageInCredit());

    }

    //введение количества цифр в поле CVV ниже допустимого (например 1)
    @Test
    @DisplayName("+ Correct warning with under limit number in CVV Field In Credit ")
    public void shouldShowWarningUnderCVVFieldWithUnderLimitNumberInCVVFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithUnderLimitNumberOfCVV());
        assertEquals("Неверный формат", creditForm.getInputInvalidMessageInCredit());
    }

    //введение нулевого значения в поле CVV (000)
    @Test
    @DisplayName("- Dont have warning with written FullZero in CVV Field In Credit   BUG !!!")
    public void shouldShowWarningUnderCVVFieldWithWrittenFullZeroInCVVFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWithWrittenFullZero());
        assertEquals("Неверно указан CVC/CVV код", creditForm.getInputInvalidMessageInCredit());
        creditForm.notificationOkIsHiddenInCredit();
    }

    //введение допустимого количества символов в поле CVV, но с использованием латинницы, кириллицы и символов
    // (ЙQ+)
    //поскольку любые символы и буквы (кроме цифр)- не вводятся в поле, оно остается пустым
    @Test
    @DisplayName("+ Correct warning with written CVV in latin and Kirillic letter and with use symbol In Credit")
    public void shouldShowWarningUnderCVVFieldWithWrittenLatinicAndKirillicLetterWithUseSymbolInCVVFieldInCredit() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillCreditForm(DataGenerator.getCardWrittenLatinicAndKirillicLetterWithUseSymbol());
        assertEquals("Поле обязательно для заполнения", creditForm.getInputInvalidMessageInCredit());
    }

}

