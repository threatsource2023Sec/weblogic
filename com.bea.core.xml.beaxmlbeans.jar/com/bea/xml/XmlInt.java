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

public interface XmlInt extends XmlLong {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_int");

   int getIntValue();

   void setIntValue(int var1);

   /** @deprecated */
   int intValue();

   /** @deprecated */
   void set(int var1);

   public static final class Factory {
      public static XmlInt newInstance() {
         return (XmlInt)XmlBeans.getContextTypeLoader().newInstance(XmlInt.type, (XmlOptions)null);
      }

      public static XmlInt newInstance(XmlOptions options) {
         return (XmlInt)XmlBeans.getContextTypeLoader().newInstance(XmlInt.type, options);
      }

      public static XmlInt newValue(Object obj) {
         return (XmlInt)XmlInt.type.newValue(obj);
      }

      public static XmlInt parse(String s) throws XmlException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse((String)s, XmlInt.type, (XmlOptions)null);
      }

      public static XmlInt parse(String s, XmlOptions options) throws XmlException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse(s, XmlInt.type, options);
      }

      public static XmlInt parse(File f) throws XmlException, IOException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse((File)f, XmlInt.type, (XmlOptions)null);
      }

      public static XmlInt parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse(f, XmlInt.type, options);
      }

      public static XmlInt parse(URL u) throws XmlException, IOException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse((URL)u, XmlInt.type, (XmlOptions)null);
      }

      public static XmlInt parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse(u, XmlInt.type, options);
      }

      public static XmlInt parse(InputStream is) throws XmlException, IOException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlInt.type, (XmlOptions)null);
      }

      public static XmlInt parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse(is, XmlInt.type, options);
      }

      public static XmlInt parse(Reader r) throws XmlException, IOException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlInt.type, (XmlOptions)null);
      }

      public static XmlInt parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse(r, XmlInt.type, options);
      }

      public static XmlInt parse(Node node) throws XmlException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse((Node)node, XmlInt.type, (XmlOptions)null);
      }

      public static XmlInt parse(Node node, XmlOptions options) throws XmlException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse(node, XmlInt.type, options);
      }

      /** @deprecated */
      public static XmlInt parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlInt.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlInt parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse(xis, XmlInt.type, options);
      }

      public static XmlInt parse(XMLStreamReader xsr) throws XmlException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlInt.type, (XmlOptions)null);
      }

      public static XmlInt parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlInt)XmlBeans.getContextTypeLoader().parse(xsr, XmlInt.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlInt.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlInt.type, options);
      }

      private Factory() {
      }
   }
}
