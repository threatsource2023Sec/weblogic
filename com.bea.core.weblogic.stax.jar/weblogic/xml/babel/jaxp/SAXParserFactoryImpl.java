package weblogic.xml.babel.jaxp;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class SAXParserFactoryImpl extends SAXParserFactory {
   public SAXParserFactoryImpl() {
      this.setValidating(false);
      this.setNamespaceAware(true);
   }

   public SAXParser newSAXParser() throws ParserConfigurationException {
      return new SAXParserImpl(this.isNamespaceAware(), this.isValidating());
   }

   public void setFeature(String name, boolean value) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
      throw new SAXNotRecognizedException("Feature: " + name);
   }

   public boolean getFeature(String name) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
      throw new SAXNotRecognizedException("Feature: " + name);
   }
}
