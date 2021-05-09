package main.java.com.epam.xmlparse.main;

import main.java.com.epam.xmlparse.builder.AbstractBankBuilder;
import main.java.com.epam.xmlparse.builder.BanksBuilderFactory;
import main.java.com.epam.xmlparse.entity.Bank;
import main.java.com.epam.xmlparse.exception.CustomException;
import main.java.com.epam.xmlparse.validator.ValidatorXml;
import java.util.Set;

public class MainMain {
    public static void main(String[] args) {
        AbstractBankBuilder abstractBankBuilder;
        Set<Bank> banks;
        ValidatorXml validatorXml = new ValidatorXml();
        try {
            if (validatorXml.isValid("./src/main/resources/Banks.xml", "./src/main/resources/Banks.xsd")) {
                BanksBuilderFactory banksBuilderFactory = new BanksBuilderFactory();
                abstractBankBuilder = banksBuilderFactory.createBankBuilder("staX");
                abstractBankBuilder.buildSetBanks("./src/main/resources/Banks.xml");
                banks = abstractBankBuilder.getBanks();

            }
        } catch (CustomException w) {
            System.out.println(w.getMessage());
        }
    }
}
