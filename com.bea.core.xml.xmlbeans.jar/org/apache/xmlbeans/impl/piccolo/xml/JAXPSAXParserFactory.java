package org.apache.xmlbeans.impl.piccolo.xml;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.xmlbeans.impl.piccolo.util.FactoryServiceFinder;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public class JAXPSAXParserFactory extends SAXParserFactory {
   private Map featureMap = new HashMap();
   private static Boolean TRUE = new Boolean(true);
   private static Boolean FALSE = new Boolean(false);
   private Piccolo nvParser = new Piccolo();
   private SAXParserFactory validatingFactory;
   private static final String VALIDATING_PROPERTY = "org.apache.xmlbeans.impl.piccolo.xml.ValidatingSAXParserFactory";
   private static Class validatingFactoryClass = findValidatingFactory();
   private ParserConfigurationException pendingValidatingException = null;
   private ParserConfigurationException pendingNonvalidatingException = null;
   private boolean validating = false;
   private boolean namespaceAware = false;

   public static SAXParserFactory newInstance() {
      return new JAXPSAXParserFactory();
   }

   public JAXPSAXParserFactory() {
      try {
         if (validatingFactoryClass != null) {
            this.validatingFactory = (SAXParserFactory)validatingFactoryClass.newInstance();
            this.validatingFactory.setNamespaceAware(false);
            this.validatingFactory.setValidating(true);
         }
      } catch (Exception var2) {
         this.validatingFactory = null;
      }

      this.setNamespaceAware(false);
   }

   public boolean getFeature(String name) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
      return this.validating && this.validatingFactory != null ? this.validatingFactory.getFeature(name) : this.nvParser.getFeature(name);
   }

   public SAXParser newSAXParser() throws ParserConfigurationException, SAXException {
      if (this.validating) {
         if (this.validatingFactory == null) {
            throw new ParserConfigurationException("XML document validation is not supported");
         } else if (this.pendingValidatingException != null) {
            throw this.pendingValidatingException;
         } else {
            return this.validatingFactory.newSAXParser();
         }
      } else if (this.pendingNonvalidatingException != null) {
         throw this.pendingNonvalidatingException;
      } else {
         return new JAXPSAXParser(new Piccolo(this.nvParser));
      }
   }

   public void setFeature(String name, boolean enabled) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
      this.featureMap.put(name, enabled ? TRUE : FALSE);
      if (this.validatingFactory != null) {
         if (this.pendingValidatingException != null) {
            this.reconfigureValidating();
         } else {
            try {
               this.validatingFactory.setFeature(name, enabled);
            } catch (ParserConfigurationException var4) {
               this.pendingValidatingException = var4;
            }
         }
      }

      if (this.pendingNonvalidatingException != null) {
         this.reconfigureNonvalidating();
      }

      if (this.validating && this.pendingValidatingException != null) {
         throw this.pendingValidatingException;
      } else if (!this.validating && this.pendingNonvalidatingException != null) {
         throw this.pendingNonvalidatingException;
      }
   }

   public void setNamespaceAware(boolean awareness) {
      super.setNamespaceAware(awareness);
      this.namespaceAware = awareness;

      try {
         this.nvParser.setFeature("http://xml.org/sax/features/namespaces", awareness);
         this.nvParser.setFeature("http://xml.org/sax/features/namespace-prefixes", !awareness);
      } catch (SAXNotSupportedException var3) {
         this.pendingNonvalidatingException = new ParserConfigurationException("Error setting namespace feature: " + var3.toString());
      } catch (SAXNotRecognizedException var4) {
         this.pendingNonvalidatingException = new ParserConfigurationException("Error setting namespace feature: " + var4.toString());
      }

      if (this.validatingFactory != null) {
         this.validatingFactory.setNamespaceAware(awareness);
      }

   }

   public void setValidating(boolean value) {
      super.setValidating(value);
      this.validating = value;
   }

   private static Class findValidatingFactory() {
      String javah;
      try {
         javah = System.getProperty("org.apache.xmlbeans.impl.piccolo.xml.ValidatingSAXParserFactory");
         if (javah != null) {
            return Class.forName(javah);
         }
      } catch (Exception var8) {
      }

      String factory;
      try {
         javah = System.getProperty("java.home");
         factory = javah + File.separator + "lib" + File.separator + "jaxp.properties";
         File f = new File(factory);
         if (f.exists()) {
            Properties props = new Properties();
            props.load(new FileInputStream(f));
            String validatingClassName = props.getProperty("org.apache.xmlbeans.impl.piccolo.xml.ValidatingSAXParserFactory");
            if (validatingClassName != null) {
               return Class.forName(validatingClassName);
            }
         }
      } catch (Exception var7) {
      }

      try {
         Enumeration enumValue = FactoryServiceFinder.findServices("javax.xml.parsers.SAXParserFactory");

         while(enumValue.hasMoreElements()) {
            try {
               factory = (String)enumValue.nextElement();
               if (!factory.equals("org.apache.xmlbeans.impl.piccolo.xml.Piccolo")) {
                  return Class.forName(factory);
               }
            } catch (ClassNotFoundException var6) {
            }
         }
      } catch (Exception var9) {
      }

      try {
         return Class.forName("org.apache.crimson.jaxp.SAXParserFactoryImpl");
      } catch (ClassNotFoundException var5) {
         return null;
      }
   }

   private void reconfigureValidating() {
      if (this.validatingFactory != null) {
         try {
            Iterator iter = this.featureMap.entrySet().iterator();

            while(iter.hasNext()) {
               Map.Entry entry = (Map.Entry)iter.next();
               this.validatingFactory.setFeature((String)entry.getKey(), (Boolean)entry.getValue());
            }
         } catch (ParserConfigurationException var3) {
            this.pendingValidatingException = var3;
         } catch (SAXNotRecognizedException var4) {
            this.pendingValidatingException = new ParserConfigurationException(var4.toString());
         } catch (SAXNotSupportedException var5) {
            this.pendingValidatingException = new ParserConfigurationException(var5.toString());
         }

      }
   }

   private void reconfigureNonvalidating() {
      try {
         Iterator iter = this.featureMap.entrySet().iterator();

         while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            this.nvParser.setFeature((String)entry.getKey(), (Boolean)entry.getValue());
         }
      } catch (SAXNotRecognizedException var3) {
         this.pendingNonvalidatingException = new ParserConfigurationException(var3.toString());
      } catch (SAXNotSupportedException var4) {
         this.pendingNonvalidatingException = new ParserConfigurationException(var4.toString());
      }

   }

   static class JAXPSAXParser extends SAXParser {
      Piccolo parser;

      JAXPSAXParser(Piccolo parser) {
         this.parser = parser;
      }

      public Parser getParser() {
         return this.parser;
      }

      public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
         return this.parser.getProperty(name);
      }

      public XMLReader getXMLReader() {
         return this.parser;
      }

      public boolean isNamespaceAware() {
         return this.parser.fNamespaces;
      }

      public boolean isValidating() {
         return false;
      }

      public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
         this.parser.setProperty(name, value);
      }
   }
}
