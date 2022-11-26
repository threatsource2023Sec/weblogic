package weblogic.xml.jaxp;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class WebLogicSAXParserFactory extends SAXParserFactory {
   static boolean allow_external_dtd = !"false".equalsIgnoreCase(System.getProperty("weblogic.xml.jaxp.allow.externalDTD"));
   private SAXParserFactory factory = null;
   private boolean disabledEntityResolutionRegistry = false;
   String[] factories = new String[]{"javax.xml.parsers.SAXParserFactory"};

   public WebLogicSAXParserFactory() {
      this.factory = (SAXParserFactory)Utils.getDelegate(this.factories);

      try {
         this.factory.setFeature("http://xml.org/sax/features/external-general-entities", allow_external_dtd);
         this.factory.setFeature("http://xml.org/sax/features/external-parameter-entities", allow_external_dtd);
      } catch (Exception var2) {
      }

   }

   public SAXParser newSAXParser() throws ParserConfigurationException, SAXException {
      WebLogicSAXParser saxParser = null;
      boolean isContextCLSet = false;
      Thread thread = Thread.currentThread();
      ClassLoader cl = thread.getContextClassLoader();

      try {
         if (cl != this.getClass().getClassLoader()) {
            isContextCLSet = true;
            thread.setContextClassLoader(this.getClass().getClassLoader());
         }

         saxParser = new WebLogicSAXParser(this.factory, this.disabledEntityResolutionRegistry);
      } finally {
         if (isContextCLSet) {
            thread.setContextClassLoader(cl);
         }

      }

      return saxParser;
   }

   public void setFeature(String name, boolean value) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
      this.factory.setFeature(name, value);
   }

   public boolean getFeature(String name) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
      return this.factory.getFeature(name);
   }

   public boolean isDisabledEntityResolutionRegistry() {
      return this.disabledEntityResolutionRegistry;
   }

   public void setDisabledEntityResolutionRegistry(boolean val) {
      this.disabledEntityResolutionRegistry = val;
   }

   public void setNamespaceAware(boolean awareness) {
      this.factory.setNamespaceAware(awareness);
   }

   public boolean isNamespaceAware() {
      return this.factory.isNamespaceAware();
   }

   public void setValidating(boolean validating) {
      this.factory.setValidating(validating);
   }

   public boolean isValidating() {
      return this.factory.isValidating();
   }

   public boolean isXIncludeAware() throws UnsupportedOperationException {
      return this.factory.isXIncludeAware();
   }

   public void setXIncludeAware(boolean isXIncludeAware) throws UnsupportedOperationException {
      this.factory.setXIncludeAware(isXIncludeAware);
   }

   public Schema getSchema() throws UnsupportedOperationException {
      return this.factory.getSchema();
   }

   public void setSchema(Schema schema) throws UnsupportedOperationException {
      this.factory.setSchema(schema);
   }
}
