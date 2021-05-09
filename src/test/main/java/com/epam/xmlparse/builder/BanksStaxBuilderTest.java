package main.java.com.epam.xmlparse.builder;

import main.java.com.epam.xmlparse.entity.Bank;
import main.java.com.epam.xmlparse.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Set;


public class BanksStaxBuilderTest {
    private static Logger logger = LogManager.getLogger();
    private BanksStaxBuilder staxBuilder;
    private final String PATH_TO_XML_FILE = "./src/test/resources/TestBanks.xml";
    private final String WRONG_PATH_TO_XML_FILE = "./src/test/resources/TestBank.xml";
    private final int QUANTITY_IF_ENTITY = 16;

    @BeforeTest
    public void before(){
        logger.info("Beginning BanksStaxBuilder testing");
        staxBuilder = new BanksStaxBuilder();
    }
    @Test
    public void testBuildSetBanks() throws CustomException {
        staxBuilder.buildSetBanks(PATH_TO_XML_FILE);
        Assert.assertEquals(staxBuilder.getBanks().size(), QUANTITY_IF_ENTITY);
    }

    @Test(expectedExceptions = CustomException.class)
    public void testBuildSetBanksException() throws CustomException{
        staxBuilder.buildSetBanks(WRONG_PATH_TO_XML_FILE);
    }
    @Test
    public void testIsNullInBank() throws CustomException{
        boolean isNull = false;
        try {
            Set<Bank> banks = staxBuilder.getBanks();
            for (Bank b : banks){
                b.hashCode();
            }
        } catch (NullPointerException e){
            isNull = true;
            throw new CustomException("Filed in Bank can`t be initialize by null " + e.getMessage());
        }
        Assert.assertFalse(isNull);
    }

    @AfterTest
    public void after(){
        logger.info("Finish BanksStaxBuilder testing");
        staxBuilder = null;
    }
}