package weblogic.xml.jaxp;

import java.io.IOException;
import java.util.Locale;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.SAXParser;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;
import weblogic.xml.util.Debug;

public class WebLogicParser implements Parser {
   static Debug.DebugFacility dbg;
   private final ChainingEntityResolver chainingEntityResolver = new ChainingEntityResolver();
   private Parser parser;
   private boolean entityResolverDisabled;

   public WebLogicParser(SAXParser saxParser, boolean disableER) {
      try {
         this.parser = saxParser.getParser();
         this.entityResolverDisabled = disableER;
         if (!disableER) {
            this.chainingEntityResolver.pushEntityResolver(new RegistryEntityResolver());
            this.parser.setEntityResolver(this.chainingEntityResolver);
         }

      } catch (SAXException var4) {
         throw new FactoryConfigurationError(var4, "Error getting a parser from a factory " + var4.getMessage());
      } catch (XMLRegistryException var5) {
         throw new FactoryConfigurationError(var5, "Could not access XML Registry");
      }
   }

   public void parse(InputSource source) throws SAXException, IOException {
      this.parser.parse(source);
   }

   public void parse(String systemId) throws SAXException, IOException {
      this.parser.parse(systemId);
   }

   public void setDocumentHandler(DocumentHandler handler) {
      this.parser.setDocumentHandler(handler);
   }

   public void setDTDHandler(DTDHandler handler) {
      this.parser.setDTDHandler(handler);
   }

   public void setEntityResolver(EntityResolver resolver) {
      if (dbg.areDebuggingAt(2)) {
         int nresolvers = this.chainingEntityResolver.getResolverCount();
         dbg.assertion(nresolvers == 1 || nresolvers == 2);
      }

      if (this.chainingEntityResolver.getResolverCount() == 2) {
         this.chainingEntityResolver.popEntityResolver();
      }

      this.chainingEntityResolver.pushEntityResolver(resolver);
      if (this.entityResolverDisabled) {
         this.enableEntityResolver();
      }

   }

   private synchronized void enableEntityResolver() {
      if (this.entityResolverDisabled) {
         this.parser.setEntityResolver(this.chainingEntityResolver);
         this.entityResolverDisabled = false;
      }
   }

   public void setErrorHandler(ErrorHandler handler) {
      this.parser.setErrorHandler(handler);
   }

   public void setLocale(Locale locale) throws SAXException {
      this.parser.setLocale(locale);
   }

   static {
      dbg = XMLContext.dbg;
   }
}
