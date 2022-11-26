package weblogic.xml.jaxp;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import weblogic.xml.util.Debug;
import weblogic.xml.util.XMLConstants;

public class RegistrySAXParser extends SAXParser implements XMLConstants {
   private static final boolean debug = Boolean.getBoolean("weblogic.xml.debug");
   static Debug.DebugFacility dbg;
   private SAXParser saxParser;
   private SAXFactoryProperties saxFactoryProperties;
   private boolean isXIncludeAware;
   private Schema schema;
   private RegistryParser parser;
   private RegistryXMLReader reader;
   private SAXFactoryProperties initSaxFactoryProperties;

   protected RegistrySAXParser(SAXParserFactory factory, SAXFactoryProperties saxFactoryProperties) {
      this(factory, saxFactoryProperties, false, (Schema)null);
   }

   protected RegistrySAXParser(SAXParserFactory factory, SAXFactoryProperties saxFactoryProperties, boolean isXIncludeAware, Schema schema) {
      this.isXIncludeAware = false;
      this.schema = null;
      this.saxFactoryProperties = saxFactoryProperties;
      this.initSaxFactoryProperties = (SAXFactoryProperties)saxFactoryProperties.clone();

      try {
         if (factory != null) {
            this.saxParser = factory.newSAXParser();
         }
      } catch (ParserConfigurationException var6) {
         throw new FactoryConfigurationError("WebLogicSAXParser cannot be created which satisfies the requested configuration." + var6.getMessage());
      } catch (SAXException var7) {
         throw new FactoryConfigurationError("WebLogicSAXParser cannot be created." + var7.getMessage());
      }

      this.parser = new RegistryParser(this.saxParser, this.saxFactoryProperties);
      this.reader = new RegistryXMLReader(this.saxParser, this.saxFactoryProperties);
   }

   public Parser getParser() throws SAXException {
      return this.parser;
   }

   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      Object property = this.saxFactoryProperties.getProperty(name);
      if (property == null && this.saxParser != null) {
         return this.saxParser.getProperty(name);
      } else if (property == null) {
         throw new SAXNotRecognizedException("You have specified a custom parser in the console. Properties for such parsers are set at the parse time.");
      } else {
         return property;
      }
   }

   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
      this.saxFactoryProperties.setProperty(name, value);
      if (this.saxParser != null) {
         this.saxParser.setProperty(name, value);
      }

   }

   public XMLReader getXMLReader() throws SAXException {
      return this.reader;
   }

   public boolean isNamespaceAware() {
      return this.saxParser == null ? this.saxFactoryProperties.get("Namespaceaware") : this.saxParser.isNamespaceAware();
   }

   public boolean isValidating() {
      return this.saxParser == null ? this.saxFactoryProperties.get("Validating") : this.saxParser.isValidating();
   }

   public boolean isXIncludeAware() {
      return this.saxParser == null ? this.saxFactoryProperties.get("XIncludeAware") : this.saxParser.isXIncludeAware();
   }

   public Schema getSchema() {
      return this.saxParser == null ? (Schema)this.saxFactoryProperties.getProperty("Schema") : this.saxParser.getSchema();
   }

   public void reset() {
      this.saxFactoryProperties.copyFrom(this.initSaxFactoryProperties);
      this.parser.reset();
      this.reader.reset();
   }

   static {
      dbg = XMLContext.dbg;
   }
}
