package weblogic.xml.jaxp;

import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;
import weblogic.xml.util.XMLConstants;

public class WebLogicDocumentBuilderFactory extends DocumentBuilderFactory implements XMLConstants {
   static Boolean allow_external_dtd = new Boolean("true".equalsIgnoreCase(System.getProperty("weblogic.xml.jaxp.allow.externalDTD")));
   private boolean disabledEntityResolutionRegistry = false;
   private DocumentBuilderFactory delegate = (DocumentBuilderFactory)AccessController.doPrivileged(new PrivilegedAction() {
      public Object run() {
         return Utils.getDelegate(WebLogicDocumentBuilderFactory.this.factories);
      }
   });
   String[] factories = new String[]{"javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl", "weblogic.apache.xerces.jaxp.DocumentBuilderFactoryImpl"};

   public WebLogicDocumentBuilderFactory() {
      if (!allow_external_dtd) {
         try {
            this.setAttribute("http://xml.org/sax/features/external-general-entities", allow_external_dtd);
            this.setAttribute("http://xml.org/sax/features/external-parameter-entities", allow_external_dtd);
         } catch (Exception var2) {
         }

      }
   }

   public Object getAttribute(String name) throws IllegalArgumentException {
      boolean isContextCLSet = false;
      Thread thread = Thread.currentThread();
      ClassLoader cl = thread.getContextClassLoader();

      Object var5;
      try {
         if (cl != this.getClass().getClassLoader()) {
            isContextCLSet = true;
            thread.setContextClassLoader(this.getClass().getClassLoader());
         }

         var5 = this.delegate.getAttribute(name);
      } finally {
         if (isContextCLSet) {
            thread.setContextClassLoader(cl);
         }

      }

      return var5;
   }

   public boolean isCoalescing() {
      return this.delegate.isCoalescing();
   }

   public boolean isDisabledEntityResolutionRegistry() {
      return this.disabledEntityResolutionRegistry;
   }

   public boolean isExpandEntityReferences() {
      return this.delegate.isExpandEntityReferences();
   }

   public boolean isIgnoringComments() {
      return this.delegate.isIgnoringComments();
   }

   public boolean isIgnoringElementContentWhitespace() {
      return this.delegate.isIgnoringElementContentWhitespace();
   }

   public boolean isNamespaceAware() {
      return this.delegate.isNamespaceAware();
   }

   public boolean isValidating() {
      return this.delegate.isValidating();
   }

   public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
      DocumentBuilder db = null;
      boolean isContextCLSet = false;
      Thread thread = Thread.currentThread();
      ClassLoader cl = thread.getContextClassLoader();

      try {
         if (cl != this.getClass().getClassLoader()) {
            isContextCLSet = true;
            thread.setContextClassLoader(this.getClass().getClassLoader());
         }

         db = this.delegate.newDocumentBuilder();
      } finally {
         if (isContextCLSet) {
            thread.setContextClassLoader(cl);
         }

      }

      if (!this.disabledEntityResolutionRegistry) {
         try {
            RegistryEntityResolver registry = new RegistryEntityResolver();
            db.setEntityResolver(registry);
         } catch (XMLRegistryException var8) {
            throw new FactoryConfigurationError(var8, "Could not access XML Registry");
         }
      }

      return db;
   }

   public void setAttribute(String name, Object value) throws IllegalArgumentException {
      boolean isContextCLSet = false;
      Thread thread = Thread.currentThread();
      ClassLoader cl = thread.getContextClassLoader();

      try {
         if (cl != this.getClass().getClassLoader()) {
            isContextCLSet = true;
            thread.setContextClassLoader(this.getClass().getClassLoader());
         }

         this.delegate.setAttribute(name, value);
      } finally {
         if (isContextCLSet) {
            thread.setContextClassLoader(cl);
         }

      }

   }

   public void setCoalescing(boolean coalescing) {
      this.delegate.setCoalescing(coalescing);
   }

   public void setDisabledEntityResolutionRegistry(boolean val) {
      this.disabledEntityResolutionRegistry = val;
   }

   public void setExpandEntityReferences(boolean expandEntityRef) {
      this.delegate.setExpandEntityReferences(expandEntityRef);
   }

   public void setIgnoringComments(boolean ignoreComments) {
      this.delegate.setIgnoringComments(ignoreComments);
   }

   public void setIgnoringElementContentWhitespace(boolean whitespace) {
      this.delegate.setIgnoringElementContentWhitespace(whitespace);
   }

   public void setNamespaceAware(boolean awareness) {
      this.delegate.setNamespaceAware(awareness);
   }

   public void setValidating(boolean validating) {
      this.delegate.setValidating(validating);
   }

   public boolean getFeature(String s) throws ParserConfigurationException {
      boolean isContextCLSet = false;
      Thread thread = Thread.currentThread();
      ClassLoader cl = thread.getContextClassLoader();

      boolean var5;
      try {
         if (cl != this.getClass().getClassLoader()) {
            isContextCLSet = true;
            thread.setContextClassLoader(this.getClass().getClassLoader());
         }

         var5 = this.delegate.getFeature(s);
      } finally {
         if (isContextCLSet) {
            thread.setContextClassLoader(cl);
         }

      }

      return var5;
   }

   public void setFeature(String s, boolean b) throws ParserConfigurationException {
      boolean isContextCLSet = false;
      Thread thread = Thread.currentThread();
      ClassLoader cl = thread.getContextClassLoader();

      try {
         if (cl != this.getClass().getClassLoader()) {
            isContextCLSet = true;
            thread.setContextClassLoader(this.getClass().getClassLoader());
         }

         this.delegate.setFeature(s, b);
      } finally {
         if (isContextCLSet) {
            thread.setContextClassLoader(cl);
         }

      }

   }

   public boolean isXIncludeAware() throws UnsupportedOperationException {
      return this.delegate.isXIncludeAware();
   }

   public void setXIncludeAware(boolean isXIncludeAware) throws UnsupportedOperationException {
      this.delegate.setXIncludeAware(isXIncludeAware);
   }

   public Schema getSchema() throws UnsupportedOperationException {
      return this.delegate.getSchema();
   }

   public void setSchema(Schema schema) throws UnsupportedOperationException {
      this.delegate.setSchema(schema);
   }
}
