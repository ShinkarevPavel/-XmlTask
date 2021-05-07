package main.java.com.epam.xmlparse.main;

import main.java.com.epam.xmlparse.builder.AbstractBankBuilder;
import main.java.com.epam.xmlparse.builder.BanksBuilderFactory;
import main.java.com.epam.xmlparse.exception.CustomException;
import main.java.com.epam.xmlparse.validator.ValidatorXml;

public class MainMain {
    public static void main(String[] args) {
//        BanksBuilderFactory banksBuilderFactory = new BanksBuilderFactory();
//        AbstractBankBuilder abstractBankBuilder = banksBuilderFactory.createBankBuilder("staX");
//        try {
//            abstractBankBuilder.buildSetBanks("./src/resources/Banks.xml");
//        } catch (CustomException e) {
//            System.out.println("Main");
//        }
//        System.out.println(abstractBankBuilder.getBanks());




        ValidatorXml validatorXml = new ValidatorXml();
        System.out.println(validatorXml.isValid("./src/resources/Banks.xml", "./src/resources/Banks.xsd"));
    }
}
