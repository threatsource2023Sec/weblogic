package weblogic.xml.jaxp;

import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Parser;
import weblogic.xml.XMLLogger;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;
import weblogic.xml.sax.XMLInputSource;
import weblogic.xml.util.Debug;

class ParserCreationHelper {
   static Debug.DebugFacility dbg;
   private XMLInputSource inputSource;
   private RegistryEntityResolver registry;

   public ParserCreationHelper(RegistryEntityResolver registry) {
      this.registry = registry;
   }

   public SAXParserFactory getCustomSAXParserFactory(XMLInputSource inputSource) {
      String publicId = inputSource.getPublicIdInternal();
      String systemId = inputSource.getDoctypeSystemIdInternal();
      String rootElementTag = inputSource.getRootTagInternal();
      this.inputSource = inputSource;
      SAXParserFactory factory = null;

      try {
         factory = this.registry.getSAXParserFactory(publicId, systemId, rootElementTag);
      } catch (XMLRegistryException var7) {
         XMLLogger.logXMLRegistryException(var7.getMessage());
      }

      return factory;
   }

   public SAXParserFactory getDefaultSAXParserFactory() {
      SAXParserFactory fact = null;

      try {
         fact = this.registry.getSAXParserFactory();
      } catch (XMLRegistryException var3) {
         XMLLogger.logXMLRegistryException(var3.getMessage());
      }

      if (fact == null) {
         fact = new WebLogicSAXParserFactory();
         dbg.println(2, "Could not get SAXParserFactory from the registry. Instantiating WebLogicSAXParserFactory");
      }

      return (SAXParserFactory)fact;
   }

   public Parser getCustomParser(XMLInputSource inputSource) {
      String publicId = inputSource.getPublicIdInternal();
      String systemId = inputSource.getDoctypeSystemIdInternal();
      String rootElementTag = inputSource.getRootTagInternal();
      this.inputSource = inputSource;
      Parser parser = null;

      try {
         parser = this.registry.getParser(publicId, systemId, rootElementTag);
      } catch (XMLRegistryException var7) {
         if (dbg.areDebuggingAt(2)) {
            var7.printStackTrace();
         }

         XMLLogger.logXMLRegistryException(var7.getMessage());
      }

      return parser;
   }

   static {
      dbg = XMLContext.dbg;
   }
}
