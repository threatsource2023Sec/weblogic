package weblogic.xml.jaxp;

import java.io.IOException;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.SAXParser;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;
import weblogic.xml.util.Debug;

public class WebLogicXMLReader implements XMLReader {
   static Debug.DebugFacility dbg;
   private final ChainingEntityResolver chainingEntityResolver = new ChainingEntityResolver();
   private XMLReader xmlReader;

   public WebLogicXMLReader() {
   }

   public WebLogicXMLReader(SAXParser saxParser) {
      try {
         this.xmlReader = saxParser.getXMLReader();
         this.chainingEntityResolver.pushEntityResolver(new RegistryEntityResolver());
         this.xmlReader.setEntityResolver(this.chainingEntityResolver);
      } catch (SAXException var3) {
         throw new FactoryConfigurationError(var3, "Error getting a parser from a factory " + var3.getMessage());
      } catch (XMLRegistryException var4) {
         throw new FactoryConfigurationError(var4, "Could not access XML Registry");
      }
   }

   public ContentHandler getContentHandler() {
      return this.xmlReader.getContentHandler();
   }

   public DTDHandler getDTDHandler() {
      return this.xmlReader.getDTDHandler();
   }

   public EntityResolver getEntityResolver() {
      return this.chainingEntityResolver.peekEntityResolver();
   }

   public ErrorHandler getErrorHandler() {
      return this.xmlReader.getErrorHandler();
   }

   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      return this.xmlReader.getFeature(name);
   }

   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      return this.xmlReader.getProperty(name);
   }

   public void parse(InputSource source) throws SAXException, IOException {
      dbg.assertion(this.xmlReader != null);
      this.xmlReader.parse(source);
   }

   public void parse(String systemId) throws SAXException, IOException {
      dbg.assertion(this.xmlReader != null);
      this.xmlReader.parse(systemId);
   }

   public void setContentHandler(ContentHandler handler) {
      this.xmlReader.setContentHandler(handler);
   }

   public void setDTDHandler(DTDHandler handler) {
      this.xmlReader.setDTDHandler(handler);
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
   }

   public void setErrorHandler(ErrorHandler handler) {
      this.xmlReader.setErrorHandler(handler);
   }

   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
      this.xmlReader.setFeature(name, value);
   }

   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
      this.xmlReader.setProperty(name, value);
   }

   static {
      dbg = XMLContext.dbg;
   }
}
