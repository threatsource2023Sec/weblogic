package weblogic.xml.jaxp;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public final class WebLogicSAXParser extends SAXParser {
   private SAXParser saxParser = null;
   private boolean disabledEntityResolutionRegistry = false;

   protected WebLogicSAXParser(SAXParserFactory factory, boolean disableER) {
      this.disabledEntityResolutionRegistry = disableER;

      try {
         this.saxParser = factory.newSAXParser();
      } catch (ParserConfigurationException var4) {
         throw new FactoryConfigurationError("WebLogicSAXParser cannot be created." + var4.getMessage());
      } catch (SAXException var5) {
         throw new FactoryConfigurationError("WebLogicSAXParser cannot be created." + var5.getMessage());
      }
   }

   public Parser getParser() throws SAXException {
      WebLogicParser p = new WebLogicParser(this.saxParser, this.disabledEntityResolutionRegistry);
      return p;
   }

   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      return this.saxParser.getProperty(name);
   }

   public XMLReader getXMLReader() throws SAXException {
      WebLogicXMLReader reader = new WebLogicXMLReader(this.saxParser);
      return reader;
   }

   public boolean isNamespaceAware() {
      return this.saxParser.isNamespaceAware();
   }

   public boolean isValidating() {
      return this.saxParser.isValidating();
   }

   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
      this.saxParser.setProperty(name, value);
   }
}
