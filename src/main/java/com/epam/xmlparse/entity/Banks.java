package main.java.com.epam.xmlparse.entity;

import java.util.Set;

public class Banks {
    private Set<Bank> banks;


    public Set<Bank> getBanks() {
        return banks;
    }

    public void setBanks(Set<Bank> banks) {
        this.banks = banks;
    }

    @Override
    public String toString() {
        return "Banks{" + "banks=" + banks + '}';
    }
}
