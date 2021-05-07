package main.java.com.epam.xmlparse.builder;

import main.java.com.epam.xmlparse.entity.Bank;
import main.java.com.epam.xmlparse.exception.CustomException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractBankBuilder {
    protected Set<Bank> banks;

    public AbstractBankBuilder() {
        this.banks = new HashSet<>();
    }

    public AbstractBankBuilder(Set<Bank> banks) {
        this.banks = new HashSet<>();
    }

    public Set<Bank> getBanks() {
        return banks;
    }

    abstract public void buildSetBanks(String path) throws CustomException;
}
