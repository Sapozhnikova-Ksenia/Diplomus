package ru.netology.data;

public class DataGenerator {

    public static final String approvedCardNumber = ("4444 4444 4444 4441");
    public static final String declinedCardNumber = ("4444 4444 4444 4442");

    public static Card getApprovedCard() {
        return new Card(approvedCardNumber,
                "12",
                "23",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getDeclinedCard() {
        return new Card(declinedCardNumber,
                "12",
                "23",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getUnknownCard() {
        return new Card("1234567890123456",
                "12",
                "23",
                "Kseniya Sapojok",
                "123"
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
                "12",
                "23",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getCardWithLatinAndKirillicAndSupersimbolCardNumber() {
        return new Card("aasdкняг+-*/4444",
                "12",
                "23",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getCardWithEmptyCardNumber() {
        return new Card("",
                "12",
                "23",
                "Kseniya Sapojok",
                "123"
        );
    }

    ///////////////////////////////////////////////////////////
    public static Card getCardWithEmptyMonth() {
        return new Card(approvedCardNumber,
                "",
                "23",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getCardWithDontFullMonth() {
        return new Card(approvedCardNumber,
                "1",
                "23",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getCardWithMoreLimitMonth() {
        return new Card(approvedCardNumber,
                "13",
                "23",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getCardWithDublZeroInMonth() {
        return new Card(approvedCardNumber,
                "00",
                "23",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getCardWithOldMonthButCurrentYear() {
        return new Card(approvedCardNumber,
                "01",
                "22",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getCardWithLatinAndKirillicAndSymbolInMonthField() {
        return new Card(approvedCardNumber,
                "DfЖы+-",
                "22",
                "Kseniya Sapojok",
                "123"
        );
    }
///////////////////////////////////////////////////////////

    public static Card getCardWithEmptyYear() {
        return new Card(approvedCardNumber,
                "06",
                "",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getCardWithDontFullYear() {
        return new Card(approvedCardNumber,
                "06",
                "2",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getCardWithMoreLimitYear() {
        return new Card(approvedCardNumber,
                "06",
                "26",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getCardWithOldYear() {
        return new Card(approvedCardNumber,
                "06",
                "21",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getCardWithDublZeroInYear() {
        return new Card(approvedCardNumber,
                "06",
                "00",
                "Kseniya Sapojok",
                "123"
        );
    }

    public static Card getCardWithLatinAndKirillicAndSymbolInYearField() {
        return new Card(approvedCardNumber,
                "06",
                "DfЖы+-",
                "Kseniya Sapojok",
                "123"
        );
    }

    ///////////////////////////////////////////////////////////
    public static Card getCardWithEmptyNameAndSurname() {
        return new Card(approvedCardNumber,
                "06",
                "22",
                "",
                "123"
        );
    }

    public static Card getCardWithOverLimitNumberOfLetters() {
        return new Card(approvedCardNumber,
                "06",
                "23",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaayf",
                "123"
        );
    }

    public static Card getCardWithUnderLimitNumberOfLetters() {
        return new Card(approvedCardNumber,
                "06",
                "23",
                "a",
                "123"
        );
    }

    public static Card getCardWithWrittenNameButDontWrittenSurnameBecauseDontUseSpace() {
        return new Card(approvedCardNumber,
                "06",
                "23",
                "KseniyaSapojok",
                "123"
        );
    }

    public static Card getCardWithWrittenKirillicSymbol() {
        return new Card(approvedCardNumber,
                "06",
                "23",
                "Ксенечка Сапожок",
                "123"
        );
    }

    public static Card getWrittenLatinicLetterButWithUseNumberAndSymbol() {
        return new Card(approvedCardNumber,
                "06",
                "23",
                "Ksen19+ ,,Sap0j0k",
                "123"
        );
    }

///////////////////////////////////////////////////////////

    public static Card getCardWithEmptyCVV() {
        return new Card(approvedCardNumber,
                "12",
                "23",
                "Kseniya Sapojok",
                ""
        );
    }

    public static Card getCardWithUnderLimitNumberOfCVV() {
        return new Card(approvedCardNumber,
                "06",
                "23",
                "Kseniya Sapojok",
                "1"
        );
    }

    public static Card getCardWithWrittenFullZero() {
        return new Card(approvedCardNumber,
                "06",
                "23",
                "Kseniya Sapojok",
                "000"
        );
    }

    public static Card getCardWrittenLatinicAndKirillicLetterWithUseSymbol() {
        return new Card(approvedCardNumber,
                "06",
                "23",
                "Kseniya Sapojok",
                "ЙQ+"
        );
    }

}
