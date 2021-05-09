package main.java.com.epam.xmlparse.validator;

import main.java.com.epam.xmlparse.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class ValidatorXmlTest {
    private ValidatorXml validator;
    private static Logger logger = LogManager.getLogger();
    private final String VALID_XML = "./src/test/resources/TestBanks.xml";
    private final String EMPTY_XML = "./src/test/resources/EmptyTestBanks.xml";
    private final String NOT_CLOSED_TAG_XML = "./src/test/resources/NotClosedTagTestBanks.xml";
    private final String XML_WRONG_PATH = "./src/test/resources/EmptyTestBank.xml";
    private final String WRONG_XML = "./src/test/resources/WrongTestBanks.xml";
    private final String XML_SCHEMA = "./src/test/resources/TestBanks.xsd";

    @BeforeTest
    public void before(){
        validator = new ValidatorXml();
        logger.trace("Start validator testing");
    }

    @Test
    public void testIsValid() throws CustomException {
//      correct xml
        boolean isValid = validator.isValid(VALID_XML, XML_SCHEMA);
        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsNotValid() throws CustomException{
//      <account-id> filed - '8K00PINK0FLOYD04' is not a valid value for 'NCName'.
        boolean isValid = validator.isValid(WRONG_XML, XML_SCHEMA);
        Assert.assertFalse(isValid);
    }

    @Test (expectedExceptions = CustomException.class)
    public void testIsValidException() throws CustomException{
//      CustomException for reason - wrong path to file
        validator.isValid(XML_WRONG_PATH, XML_SCHEMA);
    }

    @Test
    public void testIsValidEmpty() throws CustomException{
//      xml is not valid Premature end of file.
        boolean isValid = validator.isValid(EMPTY_XML, XML_SCHEMA);
        Assert.assertFalse(isValid);
    }

    @Test
    public void testIsValidNotClosedTag() throws CustomException{
//      xml is not valid The end-tag for element type "sure-name" must end with a '>' delimiter.
        boolean isValid = validator.isValid(NOT_CLOSED_TAG_XML, XML_SCHEMA);
        Assert.assertFalse(isValid);
    }
    @AfterTest
    public void after(){
        validator = null;
        logger.trace("Finish validator testing");
    }
}