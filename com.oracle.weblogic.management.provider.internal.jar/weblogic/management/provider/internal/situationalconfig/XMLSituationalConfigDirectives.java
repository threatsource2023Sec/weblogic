package weblogic.management.provider.internal.situationalconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import weblogic.management.utils.TimestampParser;
import weblogic.management.utils.situationalconfig.SituationalConfigDirectives;
import weblogic.utils.XXEUtils;

public class XMLSituationalConfigDirectives implements SituationalConfigDirectives {
   private static final String DOMAIN_ELEMENT_NAME = "domain";
   private static final String JMS_ELEMENT_NAME = "weblogic-jms";
   private static final String JDBC_ELEMENT_NAME = "jdbc-data-source";
   private static final String WLDF_ELEMENT_NAME = "weblogic-diagnostics";
   private static final String LOADING_POLICY_ELEMENT_NAME = "loading-policy";
   private static final String EXPIRATION_ELEMENT_NAME = "expiration";
   private long expiration;
   private String loadingPolicy;

   public XMLSituationalConfigDirectives(String situationalConfigFileName) throws ParserConfigurationException, SAXException, IOException {
      this(new File(situationalConfigFileName));
   }

   public XMLSituationalConfigDirectives(File situationalConfigFile) throws IOException, ParserConfigurationException, SAXException {
      this((InputStream)(new FileInputStream(situationalConfigFile)));
   }

   public XMLSituationalConfigDirectives(InputStream inputStream) throws IOException, SAXException, ParserConfigurationException {
      this.expiration = 0L;
      this.loadingPolicy = "Failed";
      this.parseXml(inputStream);
   }

   public String toString() {
      return super.toString() + "[expiration=" + this.expiration + ", loadingPolicy=" + this.loadingPolicy + "]";
   }

   private void parseXml(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
      SitCfgSAXHandler modeHandler = new SitCfgSAXHandler();
      SAXParserFactory saxParserFactory = XXEUtils.createSAXParserFactoryInstance();
      saxParserFactory.setNamespaceAware(true);
      SAXParser parser = saxParserFactory.newSAXParser();

      try {
         parser.parse(new InputSource(inputStream), modeHandler);
      } catch (StopProcessingSAXException var10) {
      }

      String xmlexpiration = modeHandler.getXmlExpiration();
      if (xmlexpiration == null) {
         this.expiration = -1L;
      } else {
         try {
            this.expiration = Long.parseLong(xmlexpiration);
         } catch (NumberFormatException var9) {
            try {
               this.expiration = TimestampParser.toMillis(xmlexpiration);
            } catch (Exception var8) {
               throw new IOException("Invalid: " + xmlexpiration);
            }
         }
      }

      this.loadingPolicy = modeHandler.getXmlLoadingPolicy();
   }

   public long getExpiration() {
      return this.expiration;
   }

   public String getLoadingPolicy() {
      return this.loadingPolicy;
   }

   private static class StopProcessingSAXException extends SAXException {
      private StopProcessingSAXException() {
      }

      // $FF: synthetic method
      StopProcessingSAXException(Object x0) {
         this();
      }
   }

   private static class SitCfgSAXHandler extends DefaultHandler {
      private LinkedList orderedElements;
      private String xmlLoadingPolicy;
      private String xmlExpiration;

      private SitCfgSAXHandler() {
         this.orderedElements = new LinkedList();
      }

      public String getXmlLoadingPolicy() {
         return this.xmlLoadingPolicy;
      }

      public String getXmlExpiration() {
         return this.xmlExpiration;
      }

      public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
         this.orderedElements.addFirst(localName);
      }

      public void characters(char[] ch, int start, int length) throws SAXException {
         if (this.orderedElements.size() > 1) {
            String parent = (String)this.orderedElements.get(1);
            if (parent.equals("domain") || parent.equals("weblogic-jms") || parent.equals("jdbc-data-source") || parent.equals("weblogic-diagnostics")) {
               String curElementName = (String)this.orderedElements.getFirst();
               if (curElementName.equals("loading-policy")) {
                  this.xmlLoadingPolicy = (new String(ch, start, length)).trim();
                  if (this.xmlExpiration != null) {
                     throw new StopProcessingSAXException();
                  }
               } else if (curElementName.equals("expiration")) {
                  this.xmlExpiration = (new String(ch, start, length)).trim();
                  if (this.xmlLoadingPolicy != null) {
                     throw new StopProcessingSAXException();
                  }
               }
            }
         }

      }

      public void endElement(String uri, String localName, String qName) throws SAXException {
         this.orderedElements.removeFirst();
      }

      // $FF: synthetic method
      SitCfgSAXHandler(Object x0) {
         this();
      }
   }
}
