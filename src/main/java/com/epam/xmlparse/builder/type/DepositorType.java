package main.java.com.epam.xmlparse.builder.type;

public enum DepositorType {
    NAME("name"),
    SURENAME("sure-name"),
    AGE("age"),
    UNP("UNP");
    private String value;
    private static DepositorType[] enums = values();

    DepositorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DepositorType isPresent(String value){
        DepositorType depositorType = null;
        for (DepositorType emn :enums){
            if (emn.getValue().equals(value)){
                depositorType = emn;
                return depositorType;
            }
        }
        return depositorType;
    }
}
