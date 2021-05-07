package main.java.com.epam.xmlparse.type;

public enum DepositType {
    ON_DEMAND_DEPOSIT("on demand deposit"),
    SETTLEMENT_DEPOSIT("settlement deposit"),
    TERM_DEPOSIT("term deposit"),
    CUMULATIVE_DEPOSIT("cumulative deposit"),
    SAVING_DEPOSIT("savings deposit"),
    METAL_DEPOSIT("metal deposit");

    private static final DepositType[] enums = values();
    private String value;

    DepositType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DepositType isPresent(String value){
        DepositType depositType = null;
        for (DepositType enm : enums){
            if (enm.getValue().equals(value)){
                depositType = enm;
                return depositType;
            }
        }
        return depositType;
    }
}
