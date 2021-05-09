package main.java.com.epam.xmlparse.handler;

import main.java.com.epam.xmlparse.entity.*;
import main.java.com.epam.xmlparse.builder.type.BankType;
import main.java.com.epam.xmlparse.builder.type.DepositType;
import main.java.com.epam.xmlparse.builder.type.DepositorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class BanksHandler extends DefaultHandler {
    static Logger logger = LogManager.getLogger();

    private AbstractDepositor depositor;
    private Set<Bank> banksSet;
    private Bank currentBank;
    private BankType currentBankType;
    private DepositorType currentDepositorType;

    public BanksHandler() {
        this.banksSet = new HashSet<>();
    }

    public Set<Bank> getBanks() {
        return this.banksSet;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (BankType.isPresent(qName) != null){
            this.currentBankType = BankType.isPresent(qName);
            logger.info("Valid bank tag was found");
        }
        if (DepositorType.isPresent(qName) != null){
            this.currentDepositorType = DepositorType.isPresent(qName);
            logger.info("Valid depositor tag was found");
        }
        switch (qName){
            case "bank":
                this.currentBank = new Bank();
                bankAttributesBuilder(attributes);
                logger.info("Bank was created");
                break;
            case "person-depositor":
                this.depositor = new PersonDepositor();
                logger.info("PersonDepositor was initialized");
                break;
            case "legal-depositor":
                this.depositor = new LegalEntityDepositor();
                logger.info("LegalEntityDepositor was initialized");
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName){
            case "bank":
                this.banksSet.add(this.currentBank);
                logger.info("Bank added to collection");
                break;
            case "person-depositor":
                this.currentBank.setDepositor(this.depositor);
                logger.info("PersonDepositor was added to bank");
                break;
            case "legal-depositor":
                this.currentBank.setDepositor(this.depositor);
                logger.info("LegalEntityDepositor was added to bank");
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).trim();
        if (this.currentBankType != null) {
            switch (this.currentBankType) {
                case TYPE:
                    this.currentBank.setType(DepositType.isPresent(data));
                    logger.info("Type of deposit was set " + data);
                    break;
                case AMOUNT_OF_DEPOSIT:
                    this.currentBank.setAmount_on_deposit(Long.parseLong(data));
                    logger.info("Amount on deposit was set " + data);
                    break;
                case TIME_CONSTRAINTS:
                    LocalDateTime ldt = LocalDateTime.parse(data);
                    this.currentBank.setConstraints(ldt);
                    logger.info("Time constraints was set " + ldt);
                    break;
                case PROFITABILITY:
                    this.currentBank.setProfitability(Double.parseDouble(data));
                    logger.info("Profitability was set " + data);
                    break;
                case ACCOUNT_ID:
                    this.currentBank.setAccount_id(data);
                    logger.info("Account id was set " + data);
                    break;
            }
            this.currentBankType = null;
            logger.info("currentType was set to null");
        }

        if (this.currentDepositorType != null){
            switch (this.currentDepositorType){
                case NAME:
                    this.depositor.setName(data);
                    logger.info("Depositor name was initialized " + data);
                    break;
                case SURENAME:
                    this.depositor.setSureName(data);
                    logger.info("Depositor sureName was initialized " + data);
                    break;
                case UNP:
                    ((LegalEntityDepositor)this.depositor).setUNP(data);
                    logger.info("Depositor UNP was initialized " + data);
                    break;
                case AGE:
                    ((PersonDepositor)this.depositor).setAge(Integer.parseInt(data));
                    logger.info("Depositor age was initialized " + data);
                    break;
            }
            this.currentDepositorType = null;
            logger.info("DepositorType was set to null");
        }
    }

    private void bankAttributesBuilder(Attributes attr){
        if (attr.getLength() == 1){
            this.currentBank.setName(attr.getValue(0));
            logger.info("Bank name was initialized " + attr.getValue(0));
            this.currentBank.setCountry("unknown");
            logger.info("Bank country was initialized -> unknown");
        } else {
            int count = 0;
            while (count < attr.getLength()){
                BankType bankType = BankType.isPresent(attr.getQName(count));
                switch (bankType){
                    case NAME:
                        this.currentBank.setName(attr.getValue(count));
                        logger.info("Bank name was initialized " + attr.getValue(count));
                        break;
                    case COUNTRY:
                        this.currentBank.setCountry(attr.getValue(count));
                        logger.info("Bank country was initialized " + attr.getValue(count));
                        break;
                }
                count++;
            }
        }
    }
}
