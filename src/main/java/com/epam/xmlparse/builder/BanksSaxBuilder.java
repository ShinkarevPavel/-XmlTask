package main.java.com.epam.xmlparse.builder;

import main.java.com.epam.xmlparse.entity.Bank;
import main.java.com.epam.xmlparse.exception.CustomException;
import main.java.com.epam.xmlparse.handler.BanksHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class BanksSaxBuilder extends AbstractBankBuilder{

    private static Logger logger = LogManager.getLogger();
    private BanksHandler banksHandler;

    public BanksSaxBuilder(){
        banksHandler = new BanksHandler();
    }

    public Set<Bank> getBanks(){
        super.banks = banksHandler.getBanks();
        return banks;
    }


    public void buildSetBanks(String path) throws CustomException{
        File file = new File(path);
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = saxParserFactory.newSAXParser();
            logger.info("SaxParser was get");
        } catch (Exception e){
            throw new CustomException("Error saxParser opening " + e.getMessage());
        }

        try {
            saxParser.parse(file, banksHandler);
            logger.info("Successful. Parsing was created");
        } catch (SAXException e) {
            throw new CustomException("Parsing error " + e.toString());
        } catch (IOException e) {
            throw new CustomException("IO parsing error " + e.toString());
        }
    }
}
