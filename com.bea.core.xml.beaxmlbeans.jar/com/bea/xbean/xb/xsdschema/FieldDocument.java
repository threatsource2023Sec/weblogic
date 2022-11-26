package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface FieldDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FieldDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("field3f9bdoctype");

   Field getField();

   void setField(Field var1);

   Field addNewField();

   public static final class Factory {
      public static FieldDocument newInstance() {
         return (FieldDocument)XmlBeans.getContextTypeLoader().newInstance(FieldDocument.type, (XmlOptions)null);
      }

      public static FieldDocument newInstance(XmlOptions options) {
         return (FieldDocument)XmlBeans.getContextTypeLoader().newInstance(FieldDocument.type, options);
      }

      public static FieldDocument parse(String xmlAsString) throws XmlException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, FieldDocument.type, (XmlOptions)null);
      }

      public static FieldDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, FieldDocument.type, options);
      }

      public static FieldDocument parse(File file) throws XmlException, IOException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse((File)file, FieldDocument.type, (XmlOptions)null);
      }

      public static FieldDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse(file, FieldDocument.type, options);
      }

      public static FieldDocument parse(URL u) throws XmlException, IOException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse((URL)u, FieldDocument.type, (XmlOptions)null);
      }

      public static FieldDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse(u, FieldDocument.type, options);
      }

      public static FieldDocument parse(InputStream is) throws XmlException, IOException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, FieldDocument.type, (XmlOptions)null);
      }

      public static FieldDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse(is, FieldDocument.type, options);
      }

      public static FieldDocument parse(Reader r) throws XmlException, IOException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, FieldDocument.type, (XmlOptions)null);
      }

      public static FieldDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse(r, FieldDocument.type, options);
      }

      public static FieldDocument parse(XMLStreamReader sr) throws XmlException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, FieldDocument.type, (XmlOptions)null);
      }

      public static FieldDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse(sr, FieldDocument.type, options);
      }

      public static FieldDocument parse(Node node) throws XmlException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse((Node)node, FieldDocument.type, (XmlOptions)null);
      }

      public static FieldDocument parse(Node node, XmlOptions options) throws XmlException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse(node, FieldDocument.type, options);
      }

      /** @deprecated */
      public static FieldDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, FieldDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FieldDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FieldDocument)XmlBeans.getContextTypeLoader().parse(xis, FieldDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FieldDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FieldDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Field extends Annotated {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Field.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("field12f5elemtype");

      String getXpath();

      Xpath xgetXpath();

      void setXpath(String var1);

      void xsetXpath(Xpath var1);

      public static final class Factory {
         public static Field newInstance() {
            return (Field)XmlBeans.getContextTypeLoader().newInstance(FieldDocument.Field.type, (XmlOptions)null);
         }

         public static Field newInstance(XmlOptions options) {
            return (Field)XmlBeans.getContextTypeLoader().newInstance(FieldDocument.Field.type, options);
         }

         private Factory() {
         }
      }

      public interface Xpath extends XmlToken {
         SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Xpath.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("xpath7f90attrtype");

         public static final class Factory {
            public static Xpath newValue(Object obj) {
               return (Xpath)FieldDocument.Field.Xpath.type.newValue(obj);
            }

            public static Xpath newInstance() {
               return (Xpath)XmlBeans.getContextTypeLoader().newInstance(FieldDocument.Field.Xpath.type, (XmlOptions)null);
            }

            public static Xpath newInstance(XmlOptions options) {
               return (Xpath)XmlBeans.getContextTypeLoader().newInstance(FieldDocument.Field.Xpath.type, options);
            }

            private Factory() {
            }
         }
      }
   }
}
