package weblogic.apache.xerces.jaxp;

import java.util.Hashtable;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import weblogic.apache.xerces.parsers.DOMParser;
import weblogic.apache.xerces.util.SAXMessageFormatter;

public class DocumentBuilderFactoryImpl extends DocumentBuilderFactory {
   private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
   private static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
   private static final String XINCLUDE_FEATURE = "http://apache.org/xml/features/xinclude";
   private static final String INCLUDE_IGNORABLE_WHITESPACE = "http://apache.org/xml/features/dom/include-ignorable-whitespace";
   private static final String CREATE_ENTITY_REF_NODES_FEATURE = "http://apache.org/xml/features/dom/create-entity-ref-nodes";
   private static final String INCLUDE_COMMENTS_FEATURE = "http://apache.org/xml/features/include-comments";
   private static final String CREATE_CDATA_NODES_FEATURE = "http://apache.org/xml/features/create-cdata-nodes";
   private Hashtable attributes;
   private Hashtable features;
   private Schema grammar;
   private boolean isXIncludeAware;
   private boolean fSecureProcess = false;

   public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
      if (this.grammar != null && this.attributes != null) {
         if (this.attributes.containsKey("http://java.sun.com/xml/jaxp/properties/schemaLanguage")) {
            throw new ParserConfigurationException(SAXMessageFormatter.formatMessage((Locale)null, "schema-already-specified", new Object[]{"http://java.sun.com/xml/jaxp/properties/schemaLanguage"}));
         }

         if (this.attributes.containsKey("http://java.sun.com/xml/jaxp/properties/schemaSource")) {
            throw new ParserConfigurationException(SAXMessageFormatter.formatMessage((Locale)null, "schema-already-specified", new Object[]{"http://java.sun.com/xml/jaxp/properties/schemaSource"}));
         }
      }

      try {
         return new DocumentBuilderImpl(this, this.attributes, this.features, this.fSecureProcess);
      } catch (SAXException var2) {
         throw new ParserConfigurationException(var2.getMessage());
      }
   }

   public void setAttribute(String var1, Object var2) throws IllegalArgumentException {
      if (var2 == null) {
         if (this.attributes != null) {
            this.attributes.remove(var1);
         }

      } else {
         if (this.attributes == null) {
            this.attributes = new Hashtable();
         }

         this.attributes.put(var1, var2);

         try {
            new DocumentBuilderImpl(this, this.attributes, this.features);
         } catch (Exception var4) {
            this.attributes.remove(var1);
            throw new IllegalArgumentException(var4.getMessage());
         }
      }
   }

   public Object getAttribute(String var1) throws IllegalArgumentException {
      Object var2;
      if (this.attributes != null) {
         var2 = this.attributes.get(var1);
         if (var2 != null) {
            return var2;
         }
      }

      var2 = null;

      try {
         DOMParser var7 = (new DocumentBuilderImpl(this, this.attributes, this.features)).getDOMParser();
         return var7.getProperty(var1);
      } catch (SAXException var6) {
         try {
            boolean var4 = ((DOMParser)var2).getFeature(var1);
            return var4 ? Boolean.TRUE : Boolean.FALSE;
         } catch (SAXException var5) {
            throw new IllegalArgumentException(var6.getMessage());
         }
      }
   }

   public Schema getSchema() {
      return this.grammar;
   }

   public void setSchema(Schema var1) {
      this.grammar = var1;
   }

   public boolean isXIncludeAware() {
      return this.isXIncludeAware;
   }

   public void setXIncludeAware(boolean var1) {
      this.isXIncludeAware = var1;
   }

   public boolean getFeature(String var1) throws ParserConfigurationException {
      if (var1.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
         return this.fSecureProcess;
      } else if (var1.equals("http://xml.org/sax/features/namespaces")) {
         return this.isNamespaceAware();
      } else if (var1.equals("http://xml.org/sax/features/validation")) {
         return this.isValidating();
      } else if (var1.equals("http://apache.org/xml/features/xinclude")) {
         return this.isXIncludeAware();
      } else if (var1.equals("http://apache.org/xml/features/dom/include-ignorable-whitespace")) {
         return !this.isIgnoringElementContentWhitespace();
      } else if (var1.equals("http://apache.org/xml/features/dom/create-entity-ref-nodes")) {
         return !this.isExpandEntityReferences();
      } else if (var1.equals("http://apache.org/xml/features/include-comments")) {
         return !this.isIgnoringComments();
      } else if (var1.equals("http://apache.org/xml/features/create-cdata-nodes")) {
         return !this.isCoalescing();
      } else {
         if (this.features != null) {
            Object var2 = this.features.get(var1);
            if (var2 != null) {
               return (Boolean)var2;
            }
         }

         try {
            DOMParser var4 = (new DocumentBuilderImpl(this, this.attributes, this.features)).getDOMParser();
            return var4.getFeature(var1);
         } catch (SAXException var3) {
            throw new ParserConfigurationException(var3.getMessage());
         }
      }
   }

   public void setFeature(String var1, boolean var2) throws ParserConfigurationException {
      if (var1.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
         this.fSecureProcess = var2;
      } else if (var1.equals("http://xml.org/sax/features/namespaces")) {
         this.setNamespaceAware(var2);
      } else if (var1.equals("http://xml.org/sax/features/validation")) {
         this.setValidating(var2);
      } else if (var1.equals("http://apache.org/xml/features/xinclude")) {
         this.setXIncludeAware(var2);
      } else if (var1.equals("http://apache.org/xml/features/dom/include-ignorable-whitespace")) {
         this.setIgnoringElementContentWhitespace(!var2);
      } else if (var1.equals("http://apache.org/xml/features/dom/create-entity-ref-nodes")) {
         this.setExpandEntityReferences(!var2);
      } else if (var1.equals("http://apache.org/xml/features/include-comments")) {
         this.setIgnoringComments(!var2);
      } else if (var1.equals("http://apache.org/xml/features/create-cdata-nodes")) {
         this.setCoalescing(!var2);
      } else {
         if (this.features == null) {
            this.features = new Hashtable();
         }

         this.features.put(var1, var2 ? Boolean.TRUE : Boolean.FALSE);

         try {
            new DocumentBuilderImpl(this, this.attributes, this.features);
         } catch (SAXNotSupportedException var5) {
            this.features.remove(var1);
            throw new ParserConfigurationException(var5.getMessage());
         } catch (SAXNotRecognizedException var6) {
            this.features.remove(var1);
            throw new ParserConfigurationException(var6.getMessage());
         }
      }
   }
}
