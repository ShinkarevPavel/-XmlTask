package main.java.com.epam.xmlparse.type;

public enum BankType {
    NAME("name"),
    COUNTRY("country"),
    TYPE("type"),
    PERSON_DEPOSITOR("person-depositor"),
    LEGAL_DEPOSITOR("legal-depositor"),
    ACCOUNT_ID("account-id"),
    AMOUNT_OF_DEPOSIT("amount-of-deposit"),
    PROFITABILITY("profitability"),
    TIME_CONSTRAINTS("time-constraint");
    private String value;
    private static final BankType[] enums = values();

    BankType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BankType isPresent(String value){
        BankType bankType = null;
        for (BankType emn : enums){
            if (emn.getValue().equals(value)){
                bankType = emn;
                return bankType;
            }
        }
        return bankType;
    }
}
