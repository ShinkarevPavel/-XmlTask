package main.java.com.epam.xmlparse.builder;

import main.java.com.epam.xmlparse.entity.*;
import main.java.com.epam.xmlparse.builder.type.BankType;
import main.java.com.epam.xmlparse.builder.type.DepositType;
import main.java.com.epam.xmlparse.builder.type.DepositorType;
import main.java.com.epam.xmlparse.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class BanksDomBuilder extends AbstractBankBuilder {
    static final Logger logger = LogManager.getLogger();
    private Document doc;
    private AbstractDepositor abstractDepositor;
    private Bank bank;

    public BanksDomBuilder() {
    }

    public void buildSetBanks(String path) throws CustomException {
        try {
            doc = buildDocument(path);
            logger.info("Document was created");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new CustomException("Error with xml file parsing" + e.getMessage());
        }
        Node banksNode = doc.getFirstChild();
        NodeList banksTag = banksNode.getChildNodes();
        Node bankNode = null;
        for (int i = 0; i < banksTag.getLength(); i++) {
            if (banksTag.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if (banksTag.item(i).getNodeName().equals("bank")) {
                bankNode = banksTag.item(i);
                logger.info("Banks tags were read");
            }
            if (bankNode != null) {
                super.banks.add(bankCreator(bankNode));
                logger.info("Bank was added to banks Set");
            }
        }
    }

    private Bank bankCreator(Node bankNode) {
        this.bank = new Bank();
        bankAtrReader(bankNode);
        NodeList bankTags = bankNode.getChildNodes();
        for (int j = 0; j < bankTags.getLength(); j++) {
            if (bankTags.item(j).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            String data = bankTags.item(j).getTextContent();
            BankType bankType = BankType.isPresent(bankTags.item(j).getNodeName());
            if (bankType != null) {
                switch (bankType) {
                    case NAME:
                        this.bank.setName(data);
                        logger.info("Name field has been initialized " + data);
                        break;
                    case PERSON_DEPOSITOR:
                        PersonDepositor personDepositor = new PersonDepositor();
                        Node depositorNOde = bankTags.item(j);
                        if (depositorNOde != null) {
                            abstractDepositor = buildDepositor(depositorNOde, personDepositor);
                            logger.info("PersonDepositor was created");
                        }
                        this.bank.setDepositor(abstractDepositor);
                        logger.info("PersonDepositor was added to bank");
                        break;
                    case LEGAL_DEPOSITOR:
                        LegalEntityDepositor legalEntityDepositor = new LegalEntityDepositor();
                        Node legalDepositorNode = bankTags.item(j);
                        if (legalDepositorNode != null) {
                            abstractDepositor = buildDepositor(legalDepositorNode, legalEntityDepositor);
                            logger.info("LegalEntityDepositor was created");
                        }
                        this.bank.setDepositor(abstractDepositor);
                        logger.info("LegalEntityDepositor was added to bank");
                        break;
                    case COUNTRY:
                        this.bank.setCountry(data);
                        logger.info("Country field has been initialized " + data);
                        break;
                    case TYPE:
                        DepositType depositType = DepositType.isPresent(data);
                        if (depositType != null) {
                            this.bank.setType(depositType);
                            logger.info("Type field has been initialized " + depositType);
                        } else {
                            logger.error("Issue with provided enum data from xml file");
                        }
                        break;
                    case ACCOUNT_ID:
                        this.bank.setAccount_id(data);
                        logger.info("Account-id field has been initialized " + data);
                        break;
                    case AMOUNT_OF_DEPOSIT:
                        this.bank.setAmount_on_deposit(Long.parseLong(data));
                        logger.info("Amount og deposit field has been initialized " + data);
                        break;
                    case PROFITABILITY:
                        this.bank.setProfitability(Double.parseDouble(data));
                        logger.info("Profitability field has been initialized " + data);
                        break;
                    case TIME_CONSTRAINTS:
                        LocalDateTime ldt = LocalDateTime.parse(data);
                        logger.info("Time-constraint field has been initialized " + ldt);
                        this.bank.setConstraints(ldt);
                        break;
                    default:
                        logger.error("there is no that bank field");
                }
            }
        }
        logger.info("New Bank was created");
        return this.bank;
    }

    private Bank bankAtrReader(Node bankNode) {
        if (bankNode.hasAttributes()) {
            NamedNodeMap attr = bankNode.getAttributes();
            for (int i = 0; i < attr.getLength(); i++) {
                if (attr.getLength() == 1) {
                    this.bank.setName(attr.item(i).getNodeValue());
                    logger.info("Bank name was initialized " + attr.item(i).getNodeName());
                    this.bank.setCountry("unknown");
                    logger.info("Bank country was initialized -> unknown");
                } else {

                    switch (BankType.isPresent(attr.item(i).getNodeName())) {
                        case NAME:
                            this.bank.setName(attr.item(i).getNodeValue());
                            logger.info("Bank name was initialized " + attr.item(i).getNodeName());
                            break;
                        case COUNTRY:
                            this.bank.setCountry(attr.item(i).getNodeValue());
                            logger.info("Bank country was initialized " + attr.item(i).getNodeName());
                            break;
                    }
                }
            }
        }
        return this.bank;
    }

    private AbstractDepositor buildDepositor(Node depositorNOde, AbstractDepositor abstractDepositor) {
        NodeList depositorTags = depositorNOde.getChildNodes();
        for (int i = 0; i < depositorTags.getLength(); i++) {
            if (depositorTags.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            String depositorData = depositorTags.item(i).getTextContent();
            DepositorType depositorType = DepositorType.isPresent(depositorTags.item(i).getNodeName());
            switch (depositorType) {
                case NAME:
                    abstractDepositor.setName(depositorData);
                    logger.info("depositor name was initialized " + depositorData);
                    break;
                case SURENAME:
                    abstractDepositor.setSureName(depositorData);
                    logger.info("depositor sureName was initialized " + depositorData);
                    break;
                case UNP:
                    ((LegalEntityDepositor) abstractDepositor).setUNP(depositorData);
                    logger.info("depositor UNP was initialized " + depositorData);
                    break;
                case AGE:
                    ((PersonDepositor) abstractDepositor).setAge(Integer.parseInt(depositorData));
                    logger.info("depositor age was initialized " + depositorData);
                    break;
            }
        }
        return abstractDepositor;
    }

    private Document buildDocument(String path) throws SAXException, IOException, ParserConfigurationException {
        File file = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        doc = dbf.newDocumentBuilder().parse(file);
        logger.info("Xml file was parsed");
        return doc;
    }
}
