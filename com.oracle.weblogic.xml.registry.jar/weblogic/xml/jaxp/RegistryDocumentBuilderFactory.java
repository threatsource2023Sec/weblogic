package weblogic.xml.jaxp;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import weblogic.xml.XMLLogger;
import weblogic.xml.registry.XMLRegistry;
import weblogic.xml.registry.XMLRegistryException;
import weblogic.xml.util.CachedInputStream;
import weblogic.xml.util.Debug;

public class RegistryDocumentBuilderFactory extends DocumentBuilderFactory {
   static Debug.DebugFacility dbg;
   private DOMFactoryProperties factoryProperties = new DOMFactoryProperties();
   private DocumentBuilderFactory factory = null;
   private boolean docSpecificParser = false;
   private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
   private CachedInputStream jaxpSchemaStream = null;

   public Object getAttribute(String name) throws IllegalArgumentException {
      try {
         XMLRegistry[] registryPath = XMLRegistry.getXMLRegistryPath();
         if (this.factory == null) {
            for(int i = 0; i < registryPath.length; ++i) {
               this.factory = registryPath[i].getDocumentBuilderFactory();
               this.docSpecificParser = registryPath[i].hasDocumentSpecificParserEntries();
               if (this.factory != null) {
                  break;
               }
            }
         }

         if (this.factory == null) {
            this.factory = new WebLogicDocumentBuilderFactory();
            this.docSpecificParser = false;
            this.factory.getAttribute(name);
         } else if (!this.docSpecificParser) {
            this.factory.getAttribute(name);
         }
      } catch (XMLRegistryException var4) {
      }

      return this.factoryProperties.getAttribute(name);
   }

   public boolean isCoalescing() {
      return this.factoryProperties.get("Coalescing");
   }

   public boolean isExpandEntityReferences() {
      return this.factoryProperties.get("ExpandEntityReferences");
   }

   public boolean isIgnoringComments() {
      return this.factoryProperties.get("IgnoringComments");
   }

   public boolean isIgnoringElementContentWhitespace() {
      return this.factoryProperties.get("IgnoringElementContentWhitespace");
   }

   public boolean isNamespaceAware() {
      return this.factoryProperties.get("Namespaceaware");
   }

   public boolean isValidating() {
      return this.factoryProperties.get("Validating");
   }

   public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
      RegistryDocumentBuilder builder = new RegistryDocumentBuilder((DOMFactoryProperties)this.factoryProperties.clone());
      builder.setJaxpSchemaSource(this.jaxpSchemaStream);
      builder.setJaxpSchemaSource(this.jaxpSchemaStream);
      return builder;
   }

   public void setAttribute(String name, Object value) throws IllegalArgumentException {
      try {
         value = this.filterJaxpStream(name, value);
         XMLRegistry[] registryPath = XMLRegistry.getXMLRegistryPath();
         if (this.factory == null) {
            for(int i = 0; i < registryPath.length; ++i) {
               this.factory = registryPath[i].getDocumentBuilderFactory();
               this.docSpecificParser = registryPath[i].hasDocumentSpecificParserEntries();
               if (this.factory != null) {
                  break;
               }
            }
         }

         if (this.factory == null) {
            this.factory = new WebLogicDocumentBuilderFactory();
            this.docSpecificParser = false;
            this.factory.setAttribute(name, value);
         } else if (!this.docSpecificParser) {
            this.factory.setAttribute(name, value);
         }
      } catch (XMLRegistryException var5) {
      }

      this.factoryProperties.setAttribute(name, value);
   }

   public void setCoalescing(boolean coalescing) {
      this.factoryProperties.put("Coalescing", coalescing);
   }

   public void setExpandEntityReferences(boolean expandEntityRef) {
      this.factoryProperties.put("ExpandEntityReferences", expandEntityRef);
   }

   public void setIgnoringComments(boolean ignoreComments) {
      this.factoryProperties.put("IgnoringComments", ignoreComments);
   }

   public void setIgnoringElementContentWhitespace(boolean whitespace) {
      this.factoryProperties.put("IgnoringElementContentWhitespace", whitespace);
   }

   public void setNamespaceAware(boolean awareness) {
      this.factoryProperties.put("Namespaceaware", awareness);
   }

   public void setValidating(boolean validating) {
      this.factoryProperties.put("Validating", validating);
   }

   public boolean getFeature(String s) {
      return this.factoryProperties.get(s);
   }

   public void setFeature(String s, boolean b) {
      this.factoryProperties.put(s, b);
   }

   public boolean isXIncludeAware() throws UnsupportedOperationException {
      return this.factoryProperties.get("XIncludeAware");
   }

   public void setXIncludeAware(boolean isXIncludeAware) throws UnsupportedOperationException {
      this.factoryProperties.put("XIncludeAware", isXIncludeAware);
   }

   public Schema getSchema() throws UnsupportedOperationException {
      return (Schema)this.factoryProperties.getAttribute("Schema");
   }

   public void setSchema(Schema schema) throws UnsupportedOperationException {
      this.factoryProperties.setAttribute("Schema", schema);
   }

   private Object filterJaxpStream(String name, Object value) {
      if (name != null && name.equals("http://java.sun.com/xml/jaxp/properties/schemaSource")) {
         if (value instanceof InputStream) {
            try {
               if (this.jaxpSchemaStream != null) {
                  this.closeJaxpSchemaStream();
               }

               this.jaxpSchemaStream = new CachedInputStream((InputStream)value);
               return this.jaxpSchemaStream;
            } catch (IOException var4) {
               XMLLogger.logIOException(var4.getMessage());
               throw new IllegalArgumentException("Error caching stream for http://java.sun.com/xml/jaxp/properties/schemaSource " + var4.toString());
            }
         }

         if (this.jaxpSchemaStream != null) {
            try {
               this.closeJaxpSchemaStream();
            } catch (IOException var5) {
               XMLLogger.logIOException(var5.getMessage());
               throw new IllegalArgumentException("Error Processing Attribute http://java.sun.com/xml/jaxp/properties/schemaSource " + var5.toString());
            }
         }
      }

      return value;
   }

   private void closeJaxpSchemaStream() throws IOException {
      weblogic.utils.Debug.assertion(this.jaxpSchemaStream != null, "JaxpSchemaStream cannot be null");

      try {
         this.jaxpSchemaStream.closeAll();
      } catch (IOException var2) {
         XMLLogger.logIOException(var2.getMessage());
         throw var2;
      }
   }

   static {
      dbg = XMLContext.dbg;
   }
}
