package main.java.com.epam.xmlparse.validator;

import main.java.com.epam.xmlparse.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class ValidatorXml {

    private static Logger logger = LogManager.getLogger();

    public boolean isValid(String xmlPath, String xsdPath) throws CustomException{
        boolean flag = false;
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(xsdPath);
        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(xmlPath);
            validator.validate(source);
            flag = true;
            logger.info("xml is valid");
        }catch (SAXException e){
            logger.error("xml is not valid " + e.getMessage());
        }catch (IOException w){
            throw new CustomException("Issue with provided path " + w.getMessage());
        }
        return flag;
    }
}
