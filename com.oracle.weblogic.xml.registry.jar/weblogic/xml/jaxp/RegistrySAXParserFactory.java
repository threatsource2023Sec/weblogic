package weblogic.xml.jaxp;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import weblogic.xml.XMLLogger;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;
import weblogic.xml.util.Debug;

public class RegistrySAXParserFactory extends SAXParserFactory {
   static Debug.DebugFacility dbg;
   private SAXParserFactory factory = null;
   private SAXFactoryProperties factoryProperties;

   public RegistrySAXParserFactory() {
      try {
         RegistryEntityResolver registry = new RegistryEntityResolver();
         boolean hasCustomParserEntries = registry.hasDocumentSpecificParserEntries();
         this.factoryProperties = new SAXFactoryProperties();
         if (!hasCustomParserEntries) {
            try {
               this.factory = registry.getSAXParserFactory();
            } catch (XMLRegistryException var4) {
               XMLLogger.logXMLRegistryException(var4.getMessage());
            }

            if (this.factory == null) {
               this.factory = new WebLogicSAXParserFactory();
            }
         }

      } catch (XMLRegistryException var5) {
         XMLLogger.logXMLRegistryException(var5.getMessage());
         throw new FactoryConfigurationError("Failed to find SAXParserFactory. " + var5.getMessage());
      }
   }

   public SAXParser newSAXParser() {
      RegistrySAXParser saxParser = new RegistrySAXParser(this.factory, (SAXFactoryProperties)this.factoryProperties.clone());
      return saxParser;
   }

   public boolean getFeature(String name) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
      Boolean feature = this.factoryProperties.getFeature(name);
      if (feature == null && this.factory != null) {
         return this.factory.getFeature(name);
      } else if (feature == null) {
         throw new SAXNotRecognizedException("You have specified a document-specific parser in the console. Features for such parsers are set at the parse time.");
      } else {
         return feature;
      }
   }

   public void setFeature(String name, boolean value) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
      if (this.factory != null) {
         this.factory.setFeature(name, value);
      }

      this.factoryProperties.setFeature(name, value);
   }

   public boolean isNamespaceAware() {
      return this.factory == null ? this.factoryProperties.get("Namespaceaware") : this.factory.isNamespaceAware();
   }

   public boolean isValidating() {
      return this.factory == null ? this.factoryProperties.get("Validating") : this.factory.isValidating();
   }

   public void setNamespaceAware(boolean awareness) {
      this.factoryProperties.put("Namespaceaware", awareness);
      if (this.factory != null) {
         this.factory.setNamespaceAware(awareness);
      }

   }

   public void setValidating(boolean validating) {
      this.factoryProperties.put("Validating", validating);
      if (this.factory != null) {
         this.factory.setValidating(validating);
      }

   }

   public boolean isXIncludeAware() {
      return this.factory == null ? this.factoryProperties.get("XIncludeAware") : this.factory.isXIncludeAware();
   }

   public void setXIncludeAware(boolean isXIncludeAware) {
      this.factoryProperties.put("XIncludeAware", isXIncludeAware);
      if (this.factory != null) {
         this.factory.setXIncludeAware(isXIncludeAware);
      }

   }

   public Schema getSchema() {
      return this.factory == null ? (Schema)this.factoryProperties.getProperty("Schema") : this.factory.getSchema();
   }

   public void setSchema(Schema schema) {
      this.factoryProperties.setProperty("Schema", schema);
      if (this.factory != null) {
         this.factory.setSchema(schema);
      }

   }

   static {
      dbg = XMLContext.dbg;
   }
}
