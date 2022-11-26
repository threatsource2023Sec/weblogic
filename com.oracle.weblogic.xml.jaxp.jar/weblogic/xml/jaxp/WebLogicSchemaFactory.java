package weblogic.xml.jaxp;

import java.io.File;
import java.net.URL;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import weblogic.utils.Debug;

public class WebLogicSchemaFactory extends SchemaFactory {
   private static final boolean debug = Boolean.getBoolean("weblogic.xml.debug");
   private SchemaFactory delegate;
   String[] factories = new String[]{"javax.xml.validation.SchemaFactory"};

   public WebLogicSchemaFactory() {
      this.delegate = (SchemaFactory)Utils.getDelegate(this.factories);
      if (debug) {
         Debug.say("WebLogicSchemaFactory is delegating to " + this.delegate.getClass());
      }

   }

   public boolean isSchemaLanguageSupported(String schemaLanguage) {
      return this.delegate.isSchemaLanguageSupported(schemaLanguage);
   }

   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      return this.delegate.getFeature(name);
   }

   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
      this.delegate.setFeature(name, value);
   }

   public void setProperty(String name, Object object) throws SAXNotRecognizedException, SAXNotSupportedException {
      this.delegate.setProperty(name, object);
   }

   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      return this.delegate.getProperty(name);
   }

   public void setErrorHandler(ErrorHandler errorHandler) {
      this.delegate.setErrorHandler(errorHandler);
   }

   public ErrorHandler getErrorHandler() {
      return this.delegate.getErrorHandler();
   }

   public void setResourceResolver(LSResourceResolver resourceResolver) {
      this.delegate.setResourceResolver(resourceResolver);
   }

   public LSResourceResolver getResourceResolver() {
      return this.delegate.getResourceResolver();
   }

   public Schema newSchema(Source schema) throws SAXException {
      return this.delegate.newSchema(schema);
   }

   public Schema newSchema(File schema) throws SAXException {
      return this.delegate.newSchema(schema);
   }

   public Schema newSchema(URL schema) throws SAXException {
      return this.delegate.newSchema(schema);
   }

   public Schema newSchema(Source[] schemas) throws SAXException {
      return this.delegate.newSchema(schemas);
   }

   public Schema newSchema() throws SAXException {
      return this.delegate.newSchema();
   }
}
