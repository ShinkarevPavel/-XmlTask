package main.java.com.epam.xmlparse.handler;

import main.java.com.epam.xmlparse.actions.BanksSaxParser;
import main.java.com.epam.xmlparse.entity.Banks;

public class MainHandler {
    public static void main(String[] args) {

        BanksSaxParser banksSaxParser = new BanksSaxParser();
        Banks banks = banksSaxParser.parseBySax("./src/resources/Banks.xml");

        System.out.println(banks.toString());
    }
}
