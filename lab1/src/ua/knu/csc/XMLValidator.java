package ua.knu.csc;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

// This validator validates that an XML document is well-formed and valid (i.e. syntax-check and validates against the specified XSD).
public class XMLValidator {

    public static boolean validate(String xmlFileLocation, String xsdFileLocation) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File xsdFile = new File(xsdFileLocation);
        try {
            Schema schema = schemaFactory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(xmlFileLocation);
            validator.validate(source);
        } catch (SAXException | IOException e) {
            System.err.println("[validator]: " + e.getMessage());
            return false;
        }

        System.out.println("[validator]: " + xmlFileLocation + " is valid.");
        return true;
    }
}
