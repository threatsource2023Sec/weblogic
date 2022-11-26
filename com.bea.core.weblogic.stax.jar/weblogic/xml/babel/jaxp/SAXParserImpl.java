package weblogic.xml.babel.jaxp;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderAdapter;
import weblogic.xml.babel.parsers.SAXDriver;

public class SAXParserImpl extends SAXParser {
   private boolean namespaces = true;
   private boolean validation = false;
   private XMLReader reader = null;

   protected SAXParserImpl() {
   }

   protected SAXParserImpl(boolean namespaces, boolean validation) throws ParserConfigurationException {
      XMLReader reader = new SAXDriver();

      try {
         reader.setFeature("http://xml.org/sax/features/namespaces", namespaces);
      } catch (SAXException var6) {
         throw new ParserConfigurationException("Cannot set namespace awareness to " + namespaces);
      }

      try {
         reader.setFeature("http://xml.org/sax/features/validation", validation);
      } catch (SAXException var5) {
         throw new ParserConfigurationException("Cannot set validation to " + validation);
      }

      this.namespaces = namespaces;
      this.validation = validation;
      this.reader = reader;
   }

   public Parser getParser() {
      return new XMLReaderAdapter(this.reader);
   }

   public XMLReader getXMLReader() throws SAXException {
      return this.reader;
   }

   public boolean isNamespaceAware() {
      return this.namespaces;
   }

   public boolean isValidating() {
      return this.validation;
   }

   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
      throw new SAXNotRecognizedException("Feature: " + name);
   }

   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      throw new SAXNotRecognizedException("Feature: " + name);
   }
}
