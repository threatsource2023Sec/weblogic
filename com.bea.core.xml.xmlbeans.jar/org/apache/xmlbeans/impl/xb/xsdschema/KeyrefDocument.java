package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlQName;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface KeyrefDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KeyrefDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("keyref45afdoctype");

   Keyref getKeyref();

   void setKeyref(Keyref var1);

   Keyref addNewKeyref();

   public static final class Factory {
      public static KeyrefDocument newInstance() {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().newInstance(KeyrefDocument.type, (XmlOptions)null);
      }

      public static KeyrefDocument newInstance(XmlOptions options) {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().newInstance(KeyrefDocument.type, options);
      }

      public static KeyrefDocument parse(String xmlAsString) throws XmlException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, KeyrefDocument.type, (XmlOptions)null);
      }

      public static KeyrefDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, KeyrefDocument.type, options);
      }

      public static KeyrefDocument parse(File file) throws XmlException, IOException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse((File)file, KeyrefDocument.type, (XmlOptions)null);
      }

      public static KeyrefDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse(file, KeyrefDocument.type, options);
      }

      public static KeyrefDocument parse(URL u) throws XmlException, IOException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse((URL)u, KeyrefDocument.type, (XmlOptions)null);
      }

      public static KeyrefDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse(u, KeyrefDocument.type, options);
      }

      public static KeyrefDocument parse(InputStream is) throws XmlException, IOException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, KeyrefDocument.type, (XmlOptions)null);
      }

      public static KeyrefDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse(is, KeyrefDocument.type, options);
      }

      public static KeyrefDocument parse(Reader r) throws XmlException, IOException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, KeyrefDocument.type, (XmlOptions)null);
      }

      public static KeyrefDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse(r, KeyrefDocument.type, options);
      }

      public static KeyrefDocument parse(XMLStreamReader sr) throws XmlException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, KeyrefDocument.type, (XmlOptions)null);
      }

      public static KeyrefDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse(sr, KeyrefDocument.type, options);
      }

      public static KeyrefDocument parse(Node node) throws XmlException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse((Node)node, KeyrefDocument.type, (XmlOptions)null);
      }

      public static KeyrefDocument parse(Node node, XmlOptions options) throws XmlException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse(node, KeyrefDocument.type, options);
      }

      /** @deprecated */
      public static KeyrefDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, KeyrefDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KeyrefDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KeyrefDocument)XmlBeans.getContextTypeLoader().parse(xis, KeyrefDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KeyrefDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KeyrefDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Keyref extends Keybase {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Keyref.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("keyref7a1felemtype");

      QName getRefer();

      XmlQName xgetRefer();

      void setRefer(QName var1);

      void xsetRefer(XmlQName var1);

      public static final class Factory {
         public static Keyref newInstance() {
            return (Keyref)XmlBeans.getContextTypeLoader().newInstance(KeyrefDocument.Keyref.type, (XmlOptions)null);
         }

         public static Keyref newInstance(XmlOptions options) {
            return (Keyref)XmlBeans.getContextTypeLoader().newInstance(KeyrefDocument.Keyref.type, options);
         }

         private Factory() {
         }
      }
   }
}
