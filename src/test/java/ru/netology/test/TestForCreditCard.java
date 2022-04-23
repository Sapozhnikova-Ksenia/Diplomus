package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.data.DataGenerator;
import ru.netology.page.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestForCreditCard {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

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
        var creditForm = homePage.buyWithCredit();
        // Заполняем поля таблицы валидными данными и жмём ПРОДОЛЖИТЬ
        creditForm.fillForm(DataGenerator.getApprovedCard());
        // Проверка появления всплывающего окна одобрения
        creditForm.notificationOkIsVisible();
        creditForm.messageUnderCardNumberFieldIsHidden();
        creditForm.messageUnderMonthFieldIsHidden();
        creditForm.messageUnderYearFieldIsHidden();
        creditForm.messageUnderHolderFieldIsHidden();
        creditForm.messageUnderCVVFieldIsHidden();
        // Проверка статуса операции в БД
        assertEquals("APPROVED", DBHelper.getCreditStatus());
    }

    //невалидная карта
    @Test
    @DisplayName("-2 Declined with declined card   BUG !!!")
    public void shouldBeDeclinedWithDeclinedCard() {
        var homePage = new HomePage();
        var creditForm = homePage.buyWithCredit();
        creditForm.fillForm(DataGenerator.getDeclinedCard());
        creditForm.notificationErrorIsVisible();
        creditForm.messageUnderCardNumberFieldIsVisible();
        creditForm.messageUnderMonthFieldIsHidden();
        creditForm.messageUnderYearFieldIsHidden();
        creditForm.messageUnderHolderFieldIsHidden();
        creditForm.messageUnderCVVFieldIsHidden();
        assertEquals("DECLINED", DBHelper.getCreditStatus());
    }

//    //неизвестная карта
//    @Test
//    @DisplayName("-3 Declined with unknown card   BUG !!! ")
//    public void shouldBeDeclinedWithUnknownCard() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getUnknownCard());
//        creditForm.notificationErrorIsVisible();
//        creditForm.closeNotificationWindow();
//        creditForm.notificationOkIsHidden();
//        creditForm.messageUnderCardNumberFieldIsVisible();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        // Проверка НЕ появления записи в БД
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //отправка пустой формы
//    @Test
//    @DisplayName("-4 Declined with Full Empty Field   ISSUES !!  ")
//    public void shouldBeDeclinedWithFullEmptyField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getFullEmptyField());
//        creditForm.messageUnderCardNumberFieldIsVisible();
//        assertEquals("Поле обязательно для заполнения", creditForm.getMessageUnderCardNumberField());
//        creditForm.messageUnderMonthFieldIsVisible();
//        assertEquals("Поле обязательно для заполнения", creditForm.getMessageUnderMonthField());
//        creditForm.messageUnderYearFieldIsVisible();
//        assertEquals("Поле обязательно для заполнения", creditForm.getMessageUnderYearField());
//        creditForm.messageUnderHolderFieldIsVisible();
//        assertEquals("Поле обязательно для заполнения", creditForm.getMessageUnderCardHolderField());
//        creditForm.messageUnderCVVFieldIsVisible();
//        assertEquals("Поле обязательно для заполнения", creditForm.getMessageUnderCVVField());
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//////////////////////Card
//
//    //некорректное сообщение об ошибке при пустом поле ввода карты
//    @Test
//    @DisplayName("- 5 Incorrect warning with empty Card Number Field   ISSUES !!")
//    public void shouldShowWarningUnderCardNumberFieldWithEmptyCardNumberField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithEmptyCardNumber());
//        creditForm.messageUnderCardNumberFieldIsVisible();
//        assertEquals("Поле обязательно для заполнения", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //сообщение об ошибке при неполном заполнении поля ввода карты
//    @Test
//    @DisplayName("+ 6 Correct warning with don't full Card Number Field")
//    public void shouldShowWarningUnderCardNumberFieldWithDontFullCardNumberField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithDontFullCardNumber());
//        creditForm.messageUnderCardNumberFieldIsVisible();
//        assertEquals("Неверный формат", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //сообщение об ошибке при заполнении поля ввода карты латиницей, кириллицей и символами
//    @Test
//    @DisplayName("+ 7 Correct warning with uncorrect simbol in Card Number Field")
//    public void shouldShowWarningUnderCardNumberFieldWithUncorrectSimbolInCardNumberField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithLatinAndKirillicAndSupersimbolCardNumber());
//        creditForm.messageUnderCardNumberFieldIsVisible();
//        assertEquals("Неверный формат", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//////////////////////Month
//
//    //некорректное сообщение об ошибке при пустом поле ввода месяца
//    @Test
//    @DisplayName("- 8 Incorrect warning with empty Month Field   ISSUES !!")
//    public void shouldShowWarningUnderMonthFieldWithEmptyMonthField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithEmptyMonth());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsVisible();
//        assertEquals("Поле обязательно для заполнения", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //сообщение о неполном вводе месяца (1 цифра)
//    @Test
//    @DisplayName("+ 9 Correct warning with don't full Month Field")
//    public void shouldShowWarningUnderMonthFieldWithDontFullMonthField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithDontFullMonth());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsVisible();
//        assertEquals("Неверный формат", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение значения в поле месяц выше допустимого (например 13)
//    @Test
//    @DisplayName("+ 10 Correct warning with more limit Month Field")
//    public void shouldShowWarningUnderMonthFieldWithOverFullMonthField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithMoreLimitMonth());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsVisible();
//        assertEquals("Неверно указан срок действия карты", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //появление всплывающего окна об одобрении банком при вводе в поле месяц 00, ожидается ошибка БАГ
//    @Test
//    @DisplayName("- 11 Correct warning with DublZero Month Field  BUG !!! ")
//    public void shouldShowWarningUnderMonthFieldWithDublZeroInMonthField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithDublZeroInMonth());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsVisible();
//        assertEquals("Неверно указан срок действия карты", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        creditForm.notificationOkIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение устаревшего (минувшего) значения в поле месяц при  значении текущего года (например 01)
//    @Test
//    @DisplayName("- 12 Correct warning with Old Month But Current Year  ISSUES !!")
//    public void shouldShowWarningUnderMonthFieldWithOldMonthButCurrentYear() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithOldMonthButCurrentYear());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsVisible();
//        assertEquals("Истёк срок действия карты", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //При введении символа, букв латиницы, кириллицы - ввод в поле Месяца не осуществляется, при отправке
//    // формы должен появляться текст о неверном формате
//    @Test
//    @DisplayName("+ 13 Correct warning with empty Month Field if in field will be latin kirillic and symbol")
//    public void shouldShowWarningUnderMonthFieldWithEmptyMonthFieldIfInFieldWillBeLatinAndKirillicAndSymbol() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithLatinAndKirillicAndSymbolInMonthField());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsVisible();
//        assertEquals("Неверный формат", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//////////////////////Year
//
//    //некорректное сообщение об ошибке при пустом поле ввода года
//    @Test
//    @DisplayName("- 14 Incorrect warning with empty Year Field  ISSUES !! ")
//    public void shouldShowWarningUnderYearFieldWithEmptyYearField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithEmptyYear());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsVisible();
//        assertEquals("Поле обязательно для заполнения", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //сообщение о неполном вводе года (1 цифра)
//    @Test
//    @DisplayName("+ 15 Correct warning with don't full Year Field")
//    public void shouldShowWarningUnderYearFieldWithDontFullYearField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithDontFullYear());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsVisible();
//        assertEquals("Неверный формат", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение значения в поле года выше допустимого (например 26)
//    @Test
//    @DisplayName("- 16 UnCorrect warning with more limit Year Field   BUG !!!")
//    public void shouldShowWarningUnderYearFieldWithOverFullYearField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithMoreLimitYear());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsVisible();
//        assertEquals("Неверно указан срок действия карты", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение устаревшего (минувшего) значения в поле года (например 21)
//    @Test
//    @DisplayName("+ 17 Correct warning with Old Year")
//    public void shouldShowWarningUnderYearFieldWithOldYear() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithOldYear());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsVisible();
//        assertEquals("Истёк срок действия карты", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение нулевого значения в поле года (00)
//    @Test
//    @DisplayName("- 18 Uncorrect warning with DublZero Year Field   ISSUES !! ")
//    public void shouldShowWarningUnderYearFieldWithDublZeroInYearField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithDublZeroInYear());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsVisible();
//        assertEquals("Неверно указан срок действия карты", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        creditForm.notificationOkIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //При введении символа, букв латиницы, кириллицы - ввод в поле Год не осуществляется, при отправке
//    // формы должен появляться текст о неверном формате
//    @Test
//    @DisplayName("+ 19 Correct warning with empty Year Field if in field will be latin kirillic and symbol")
//    public void shouldShowWarningUnderYearFieldWithEmptyYearFieldIfInFieldWillBeLatinAndKirillicAndSymbol() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithLatinAndKirillicAndSymbolInYearField());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsVisible();
//        assertEquals("Неверный формат", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//////////////////////Name & Surname
//
//    //корректное сообщение об ошибке при пустом поле ввода владельца карты
//    @Test
//    @DisplayName("+ 20 Correct warning with empty Name & Surname Field")
//    public void shouldShowWarningUnderFieldNameAndSurnameWithEmptyNameAndSurnameField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithEmptyNameAndSurname());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsVisible();
//        assertEquals("Поле обязательно для заполнения", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderCVVFieldIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение количества букв в поле владельца карты выше допустимого (например 71)
//    @Test
//    @DisplayName("- 21 Dont have warning with more limit letter in Name & Surname   BUG !!!")
//    public void shouldShowWarningUnderNameAndSurnameFieldWithOverLimitNumberOfLettersInNameAndSurnameField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithOverLimitNumberOfLetters());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsVisible();
//        assertEquals("Неверный формат", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderCVVFieldIsHidden();
//        creditForm.notificationOkIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение количества букв в поле владельца карты ниже допустимого (например 1)
//    @Test
//    @DisplayName("- 22 Dont have warning with under limit letter in Name & Surname   BUG !!!")
//    public void shouldShowWarningUnderNameAndSurnameFieldWithUnderLimitNumberOfLettersInNameAndSurnameField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithUnderLimitNumberOfLetters());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsVisible();
//        assertEquals("Неверный формат", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderCVVFieldIsHidden();
//        creditForm.notificationOkIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение допустимого количества букв в поле владельца карты без использования пробела, таким образом получая ввод
//    // только имени, но формально без фамилии (KseniyaSapojok)
//    @Test
//    @DisplayName("- 23 Dont have warning with written Name but dont written Surname because dont use space   BUG !!!")
//    public void shouldShowWarningUnderNameAndSurnameFieldWithWrittenNameButDontWrittenSurnameBecauseDontUseSpaceInNameAndSurnameField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithWrittenNameButDontWrittenSurnameBecauseDontUseSpace());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsVisible();
//        assertEquals("Неверный формат", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderCVVFieldIsHidden();
//        creditForm.notificationOkIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение допустимого количества букв в поле владельца карты кириллицей (Ксенечка Сапожок)
//    @Test
//    @DisplayName("- 24 Dont have warning with written Name & Surname in kirillic symbol   BUG !!!")
//    public void shouldShowWarningUnderNameAndSurnameFieldWithWrittenKirillicSymbolInNameAndSurnameField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithWrittenKirillicSymbol());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsVisible();
//        assertEquals("Неверный формат", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderCVVFieldIsHidden();
//        creditForm.notificationOkIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение допустимого количества букв в поле владельца карты латинницей, но с использованием числовых и символьных
//    // выражений (Ksen19+ ,,Sap0j0k)
//    @Test
//    @DisplayName("- 25 Dont have warning with written Name & Surname in latin letter but with use numbers and symbol   BUG !!!")
//    public void shouldShowWarningUnderNameAndSurnameFieldWithWrittenLatinicLetterButWithUseNumberAndSymbolInNameAndSurnameField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getWrittenLatinicLetterButWithUseNumberAndSymbol());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsVisible();
//        assertEquals("Неверный формат", creditForm.getInputInvalidMessage());
//        creditForm.messageUnderCVVFieldIsHidden();
//        creditForm.notificationOkIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//////////////////////CVV
//
//    //некорректное сообщение об ошибке при отправке пустого поля CVV
//    // всплывает две ошибки
//    @Test
//    @DisplayName("- 26 Incorrect warning with empty CVV Field  BUG !!!")
//    public void shouldShowWarningWithEmptyCVV() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithEmptyCVV());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsVisible();
//        assertEquals("Поле обязательно для заполнения", creditForm.getMessageUnderCVVField());
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение количества цифр в поле CVV ниже допустимого (например 1)
//    @Test
//    @DisplayName("+ 27 Correct warning with under limit number in CVV Field ")
//    public void shouldShowWarningUnderCVVFieldWithUnderLimitNumberInCVVField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithUnderLimitNumberOfCVV());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsVisible();
//        assertEquals("Неверный формат", creditForm.getInputInvalidMessage());
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение нулевого значения в поле CVV (000)
//    @Test
//    @DisplayName("- 28 Dont have warning with written FullZero in CVV Field    BUG !!!")
//    public void shouldShowWarningUnderCVVFieldWithWrittenFullZeroInCVVField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWithWrittenFullZero());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsVisible();
//        assertEquals("Неверно указан CVC/CVV код", creditForm.getInputInvalidMessage());
//        creditForm.notificationOkIsHidden();
//        assertNull(DBHelper.getCreditStatus());
//    }
//
//    //введение допустимого количества символов в поле CVV, но с использованием латинницы, кириллицы и символов
//    // (ЙQ+)
//    //поскольку любые символы и буквы (кроме цифр)- не вводятся в поле, оно остается пустым
//    @Test
//    @DisplayName("- 29 Incorrect warning with written CVV in latin and Kirillic letter and with use symbol ")
//    public void shouldShowWarningUnderCVVFieldWithWrittenLatinicAndKirillicLetterWithUseSymbolInCVVField() {
//        var homePage = new HomePage();
//        var creditForm = homePage.buyWithCredit();
//        creditForm.fillForm(DataGenerator.getCardWrittenLatinicAndKirillicLetterWithUseSymbol());
//        creditForm.messageUnderCardNumberFieldIsHidden();
//        creditForm.messageUnderMonthFieldIsHidden();
//        creditForm.messageUnderYearFieldIsHidden();
//        creditForm.messageUnderHolderFieldIsHidden();
//        creditForm.messageUnderCVVFieldIsVisible();
//        assertEquals("Поле обязательно для заполнения", creditForm.getInputInvalidMessage());
//        assertNull(DBHelper.getCreditStatus());
//    }
}