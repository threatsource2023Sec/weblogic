package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface KeyDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KeyDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("key5d16doctype");

   Keybase getKey();

   void setKey(Keybase var1);

   Keybase addNewKey();

   public static final class Factory {
      public static KeyDocument newInstance() {
         return (KeyDocument)XmlBeans.getContextTypeLoader().newInstance(KeyDocument.type, (XmlOptions)null);
      }

      public static KeyDocument newInstance(XmlOptions options) {
         return (KeyDocument)XmlBeans.getContextTypeLoader().newInstance(KeyDocument.type, options);
      }

      public static KeyDocument parse(String xmlAsString) throws XmlException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, KeyDocument.type, (XmlOptions)null);
      }

      public static KeyDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, KeyDocument.type, options);
      }

      public static KeyDocument parse(File file) throws XmlException, IOException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse((File)file, KeyDocument.type, (XmlOptions)null);
      }

      public static KeyDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse(file, KeyDocument.type, options);
      }

      public static KeyDocument parse(URL u) throws XmlException, IOException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse((URL)u, KeyDocument.type, (XmlOptions)null);
      }

      public static KeyDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse(u, KeyDocument.type, options);
      }

      public static KeyDocument parse(InputStream is) throws XmlException, IOException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, KeyDocument.type, (XmlOptions)null);
      }

      public static KeyDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse(is, KeyDocument.type, options);
      }

      public static KeyDocument parse(Reader r) throws XmlException, IOException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, KeyDocument.type, (XmlOptions)null);
      }

      public static KeyDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse(r, KeyDocument.type, options);
      }

      public static KeyDocument parse(XMLStreamReader sr) throws XmlException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, KeyDocument.type, (XmlOptions)null);
      }

      public static KeyDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse(sr, KeyDocument.type, options);
      }

      public static KeyDocument parse(Node node) throws XmlException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse((Node)node, KeyDocument.type, (XmlOptions)null);
      }

      public static KeyDocument parse(Node node, XmlOptions options) throws XmlException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse(node, KeyDocument.type, options);
      }

      /** @deprecated */
      public static KeyDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, KeyDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KeyDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KeyDocument)XmlBeans.getContextTypeLoader().parse(xis, KeyDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KeyDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KeyDocument.type, options);
      }

      private Factory() {
      }
   }
}
