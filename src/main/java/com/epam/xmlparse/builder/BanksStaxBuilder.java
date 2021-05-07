package main.java.com.epam.xmlparse.builder;

import main.java.com.epam.xmlparse.entity.AbstractDepositor;
import main.java.com.epam.xmlparse.entity.Bank;
import main.java.com.epam.xmlparse.entity.LegalEntityDepositor;
import main.java.com.epam.xmlparse.entity.PersonDepositor;
import main.java.com.epam.xmlparse.exception.CustomException;
import main.java.com.epam.xmlparse.type.BankType;
import main.java.com.epam.xmlparse.type.DepositType;
import main.java.com.epam.xmlparse.type.DepositorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

public class BanksStaxBuilder extends AbstractBankBuilder{
    private static Logger logger = LogManager.getLogger();
    private XMLInputFactory inputFactory;
    private Bank currentBank;

    public BanksStaxBuilder() {
        this.inputFactory = XMLInputFactory.newInstance();
    }

    public Set<Bank> getBanks() {
        return banks;
    }

    public void buildSetBanks(String path) throws CustomException {
        String currentTag;
        try (FileInputStream fileInputStream = new FileInputStream(new File(path))) {
            XMLStreamReader streamReader = inputFactory.createXMLStreamReader(fileInputStream);
            logger.info("XMLreader was created");
            while (streamReader.hasNext()) {
                int type = streamReader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    currentTag = streamReader.getLocalName();
                    if (currentTag.equals("bank")) {
                        currentBank = new Bank();
                        bankBuilder(streamReader);
                        super.banks.add(currentBank);
                        logger.info("Bank was added to collection");
                    }
                }
            }
        } catch (IOException e) {
            throw new CustomException("Issue with provided with file or path to file " + e.getMessage());
        } catch (XMLStreamException w) {
            throw new CustomException("Stax parsing error " + w.getMessage());
        }
    }

    private Bank bankBuilder(XMLStreamReader reader) throws XMLStreamException {
        bankAttributeBuilder(reader);
        String name;
        AbstractDepositor depositor;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    logger.info("Bank element was found");
                    name = reader.getLocalName();
                    if (BankType.isPresent(name) != null) {
                        switch (BankType.isPresent(name)) {
                            case TYPE:
                                String data = getXMLText(reader);
                                if (DepositType.isPresent(data) != null) {
                                    this.currentBank.setType(DepositType.isPresent(data));
                                    logger.info("Type field was initialized");
                                }
                                break;
                            case ACCOUNT_ID:
                                this.currentBank.setAccount_id(getXMLText(reader));
                                logger.info("Account-id field was initialized");
                                break;
                            case PROFITABILITY:
                                this.currentBank.setProfitability(Double.parseDouble(getXMLText(reader)));
                                logger.info("Profitability field was initialized");
                                break;
                            case TIME_CONSTRAINTS:
                                LocalDateTime ldt = LocalDateTime.parse(getXMLText(reader));
                                this.currentBank.setConstraints(ldt);
                                logger.info("Time_constraints field was initialized");
                                break;
                            case AMOUNT_OF_DEPOSIT:
                                this.currentBank.setAmount_on_deposit(Long.parseLong(getXMLText(reader)));
                                logger.info("Amount-of-deposit field was initialized");
                                break;
                            case PERSON_DEPOSITOR:
                                depositor = new PersonDepositor();
                                depositorBuilder(reader, depositor, name);
                                this.currentBank.setDepositor(depositor);
                                logger.info("Person depositor field was initialized");
                                break;
                            case LEGAL_DEPOSITOR:
                                depositor = new LegalEntityDepositor();
                                this.currentBank.setDepositor(depositorBuilder(reader, depositor, name));
                                logger.info("Legal depositor field was initialized");
                                break;
                        }
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equals("bank")) {
                        logger.info("Bank was created");
                        return currentBank;
                    }
            }
        }
        throw new XMLStreamException("Unknown tag into bank class");
    }

    private AbstractDepositor depositorBuilder(XMLStreamReader reader, AbstractDepositor depositor, String tag) throws XMLStreamException {
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    logger.info("Depositor element was found");
                    String name = reader.getLocalName();
                    switch (DepositorType.isPresent(name)) {
                        case NAME:
                            depositor.setName(getXMLText(reader));
                            logger.info("Name field of depositor was initialized");
                            break;
                        case SURENAME:
                            depositor.setSureName(getXMLText(reader));
                            logger.info("Surename field of depositor was initialized");
                            break;
                        case AGE:
                            ((PersonDepositor) depositor).setAge(Integer.parseInt(getXMLText(reader)));
                            logger.info("Age field of depositor was initialized");
                            break;
                        case UNP:
                            ((LegalEntityDepositor) depositor).setUNP(getXMLText(reader));
                            logger.info("UNP field of depositor was initialized");
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equals(tag)) {
                        logger.info("Depositor was created");
                        return depositor;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown tag into depositor class");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        logger.info("method getXMLText was called");
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
            logger.info("Text from current tag was received " + text);
        }
        return text;
    }

    private void bankAttributeBuilder(XMLStreamReader reader) {
        if (reader.getAttributeCount() == 1) {
            currentBank.setName(reader.getAttributeValue(0));
            logger.info("Bank name field was initialized " + reader.getAttributeValue(0));
            currentBank.setCountry("unknown");
            logger.info("Bank country field was initialized by default value");
        } else {
            int count = 0;
            while (count < reader.getAttributeCount()) {
                switch (BankType.isPresent(reader.getAttributeLocalName(count))) {
                    case NAME:
                        currentBank.setName(reader.getAttributeValue(count));
                        logger.info("Bank name field was initialized " + reader.getAttributeValue(count));
                        break;
                    case COUNTRY:
                        currentBank.setCountry(reader.getAttributeValue(count));
                        logger.info("Bank country field was initialized " + reader.getAttributeValue(count));
                        break;
                }
                count++;
            }
        }
    }
}
