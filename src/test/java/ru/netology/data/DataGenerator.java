package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataGenerator {

    public static final String approvedCardNumber = ("4444 4444 4444 4441");
    public static final String declinedCardNumber = ("4444 4444 4444 4442");

    private static final Faker faker = new Faker();

    public static String generateUnknownCard() {
        return faker.numerify("#### #### #### ####");
    }

    public static String generateMonth() {
        return LocalDate.now().plusMonths(faker.number().numberBetween(0, 13)).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear() {
        return LocalDate.now().plusYears(faker.number().numberBetween(1, 4)).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateHolder() {
        return (faker.name().firstName() + " " + faker.name().lastName());
    }


    public static String generateCVC() {
        return faker.numerify("###");
    }

    public static Card getApprovedCard() {
        return new Card(approvedCardNumber,
                generateMonth(),
                generateYear(),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getDeclinedCard() {
        return new Card(declinedCardNumber,
                generateMonth(),
                generateYear(),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getUnknownCard() {
        return new Card(generateUnknownCard(),
                generateMonth(),
                generateYear(),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getFullEmptyField() {
        return new Card("",
                "",
                "",
                "",
                ""
        );
    }

    public static Card getCardWithDontFullCardNumber() {
        return new Card("444444444444444",
                generateMonth(),
                generateYear(),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardWithLatinAndKirillicAndSupersimbolCardNumber() {
        return new Card("aasdкняг+-*/4444",
                generateMonth(),
                generateYear(),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardWithEmptyCardNumber() {
        return new Card("",
                generateMonth(),
                generateYear(),
                generateHolder(),
                generateCVC()
        );
    }

    ///////////////////////////////////////////////////////////
    public static Card getCardWithEmptyMonth() {
        return new Card(approvedCardNumber,
                "",
                generateYear(),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardWithDontFullMonth() {
        return new Card(approvedCardNumber,
                "1",
                generateYear(),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardWithMoreLimitMonth() {
        return new Card(approvedCardNumber,
                "13",
                generateYear(),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardWithDublZeroInMonth() {
        return new Card(approvedCardNumber,
                "00",
                generateYear(),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardWithOldMonthButCurrentYear() {
        return new Card(approvedCardNumber,
                "01",
                generateYear(),
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardWithLatinAndKirillicAndSymbolInMonthField() {
        return new Card(approvedCardNumber,
                "DfЖы+-",
                generateYear(),
                generateHolder(),
                generateCVC()
        );
    }
///////////////////////////////////////////////////////////

    public static Card getCardWithEmptyYear() {
        return new Card(approvedCardNumber,
                generateMonth(),
                "",
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardWithDontFullYear() {
        return new Card(approvedCardNumber,
                generateMonth(),
                "2",
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardWithMoreLimitYear() {
        return new Card(approvedCardNumber,
                generateMonth(),
                "26",
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardWithOldYear() {
        return new Card(approvedCardNumber,
                generateMonth(),
                "21",
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardWithDublZeroInYear() {
        return new Card(approvedCardNumber,
                generateMonth(),
                "00",
                generateHolder(),
                generateCVC()
        );
    }

    public static Card getCardWithLatinAndKirillicAndSymbolInYearField() {
        return new Card(approvedCardNumber,
                generateMonth(),
                "DfЖы+-",
                generateHolder(),
                generateCVC()
        );
    }

    ///////////////////////////////////////////////////////////
    public static Card getCardWithEmptyNameAndSurname() {
        return new Card(approvedCardNumber,
                generateMonth(),
                generateYear(),
                "",
                generateCVC()
        );
    }

    public static Card getCardWithOverLimitNumberOfLetters() {
        return new Card(approvedCardNumber,
                generateMonth(),
                generateYear(),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaayf",
                generateCVC()
        );
    }

    public static Card getCardWithUnderLimitNumberOfLetters() {
        return new Card(approvedCardNumber,
                generateMonth(),
                generateYear(),
                "a",
                generateCVC()
        );
    }

    public static Card getCardWithWrittenNameButDontWrittenSurnameBecauseDontUseSpace() {
        return new Card(approvedCardNumber,
                generateMonth(),
                generateYear(),
                "KseniyaSapojok",
                generateCVC()
        );
    }

    public static Card getCardWithWrittenKirillicSymbol() {
        return new Card(approvedCardNumber,
                generateMonth(),
                generateYear(),
                "Ксенечка Сапожок",
                generateCVC()
        );
    }

    public static Card getWrittenLatinicLetterButWithUseNumberAndSymbol() {
        return new Card(approvedCardNumber,
                generateMonth(),
                generateYear(),
                "Ksen19+ ,,Sap0j0k",
                generateCVC()
        );
    }

///////////////////////////////////////////////////////////

    public static Card getCardWithEmptyCVV() {
        return new Card(approvedCardNumber,
                generateMonth(),
                generateYear(),
                generateHolder(),
                ""
        );
    }

    public static Card getCardWithUnderLimitNumberOfCVV() {
        return new Card(approvedCardNumber,
                generateMonth(),
                generateYear(),
                generateHolder(),
                "1"
        );
    }

    public static Card getCardWithWrittenFullZero() {
        return new Card(approvedCardNumber,
                generateMonth(),
                generateYear(),
                generateHolder(),
                "000"
        );
    }

    public static Card getCardWrittenLatinicAndKirillicLetterWithUseSymbol() {
        return new Card(approvedCardNumber,
                generateMonth(),
                generateYear(),
                generateHolder(),
                "ЙQ+"
        );
    }

}
