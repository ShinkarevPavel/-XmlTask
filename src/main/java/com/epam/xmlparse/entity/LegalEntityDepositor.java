package main.java.com.epam.xmlparse.entity;

public class LegalEntityDepositor extends AbstractDepositor{

    private String UNP;

    public LegalEntityDepositor(){}

    public LegalEntityDepositor(String name, String sureName, String UNP) {
        super(name, sureName);
        this.UNP = UNP;
    }

    public String getUNP() {
        return UNP;
    }

    public void setUNP(String UNP) {
        this.UNP = UNP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        };
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!(o instanceof AbstractDepositor)){
            return false;
        }
        if (!super.equals(o)) return false;
        LegalEntityDepositor that = (LegalEntityDepositor) o;
        return UNP == that.UNP;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = result * prime + super.hashCode();
        result = result * prime + this.UNP.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("LegalEntityDepositor { ")
                .append(super.toString())
                .append(" UNP=")
                .append(this.UNP)
                .append("};")
                .toString();
    }
}
