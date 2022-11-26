package com.bea.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface XmlByte extends XmlShort {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_byte");

   /** @deprecated */
   byte byteValue();

   /** @deprecated */
   void set(byte var1);

   byte getByteValue();

   void setByteValue(byte var1);

   public static final class Factory {
      public static XmlByte newInstance() {
         return (XmlByte)XmlBeans.getContextTypeLoader().newInstance(XmlByte.type, (XmlOptions)null);
      }

      public static XmlByte newInstance(XmlOptions options) {
         return (XmlByte)XmlBeans.getContextTypeLoader().newInstance(XmlByte.type, options);
      }

      public static XmlByte newValue(Object obj) {
         return (XmlByte)XmlByte.type.newValue(obj);
      }

      public static XmlByte parse(String s) throws XmlException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse((String)s, XmlByte.type, (XmlOptions)null);
      }

      public static XmlByte parse(String s, XmlOptions options) throws XmlException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse(s, XmlByte.type, options);
      }

      public static XmlByte parse(File f) throws XmlException, IOException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse((File)f, XmlByte.type, (XmlOptions)null);
      }

      public static XmlByte parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse(f, XmlByte.type, options);
      }

      public static XmlByte parse(URL u) throws XmlException, IOException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse((URL)u, XmlByte.type, (XmlOptions)null);
      }

      public static XmlByte parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse(u, XmlByte.type, options);
      }

      public static XmlByte parse(InputStream is) throws XmlException, IOException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlByte.type, (XmlOptions)null);
      }

      public static XmlByte parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse(is, XmlByte.type, options);
      }

      public static XmlByte parse(Reader r) throws XmlException, IOException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlByte.type, (XmlOptions)null);
      }

      public static XmlByte parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse(r, XmlByte.type, options);
      }

      public static XmlByte parse(Node node) throws XmlException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse((Node)node, XmlByte.type, (XmlOptions)null);
      }

      public static XmlByte parse(Node node, XmlOptions options) throws XmlException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse(node, XmlByte.type, options);
      }

      /** @deprecated */
      public static XmlByte parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlByte.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlByte parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse(xis, XmlByte.type, options);
      }

      public static XmlByte parse(XMLStreamReader xsr) throws XmlException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlByte.type, (XmlOptions)null);
      }

      public static XmlByte parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlByte)XmlBeans.getContextTypeLoader().parse(xsr, XmlByte.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlByte.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlByte.type, options);
      }

      private Factory() {
      }
   }
}
