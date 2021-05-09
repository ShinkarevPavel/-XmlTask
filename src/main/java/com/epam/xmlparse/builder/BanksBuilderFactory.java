package main.java.com.epam.xmlparse.builder;

import main.java.com.epam.xmlparse.builder.type.BuilderType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CancellationException;


public class BanksBuilderFactory {
    private static Logger logger = LogManager.getLogger();

    public AbstractBankBuilder createBankBuilder(String typeBuilder) {
        BuilderType builderType = BuilderType.valueOf(typeBuilder.toUpperCase());
        switch (builderType) {
            case DOM:
                logger.info("DOM was chose");
                return new BanksDomBuilder();
            case SAX:
                logger.info("SAX was chose");
                return new BanksSaxBuilder();
            case STAX:
                logger.info("STAX was chose");
                return new BanksStaxBuilder();
            default:
                throw new CancellationException("There is no " + typeBuilder + " builder");
        }
    }
}
