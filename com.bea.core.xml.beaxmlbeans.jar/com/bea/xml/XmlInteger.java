package com.bea.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface XmlInteger extends XmlDecimal {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_integer");

   BigInteger getBigIntegerValue();

   void setBigIntegerValue(BigInteger var1);

   /** @deprecated */
   BigInteger bigIntegerValue();

   /** @deprecated */
   void set(BigInteger var1);

   public static final class Factory {
      public static XmlInteger newInstance() {
         return (XmlInteger)XmlBeans.getContextTypeLoader().newInstance(XmlInteger.type, (XmlOptions)null);
      }

      public static XmlInteger newInstance(XmlOptions options) {
         return (XmlInteger)XmlBeans.getContextTypeLoader().newInstance(XmlInteger.type, options);
      }

      public static XmlInteger newValue(Object obj) {
         return (XmlInteger)XmlInteger.type.newValue(obj);
      }

      public static XmlInteger parse(String s) throws XmlException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse((String)s, XmlInteger.type, (XmlOptions)null);
      }

      public static XmlInteger parse(String s, XmlOptions options) throws XmlException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse(s, XmlInteger.type, options);
      }

      public static XmlInteger parse(File f) throws XmlException, IOException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse((File)f, XmlInteger.type, (XmlOptions)null);
      }

      public static XmlInteger parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse(f, XmlInteger.type, options);
      }

      public static XmlInteger parse(URL u) throws XmlException, IOException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse((URL)u, XmlInteger.type, (XmlOptions)null);
      }

      public static XmlInteger parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse(u, XmlInteger.type, options);
      }

      public static XmlInteger parse(InputStream is) throws XmlException, IOException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlInteger.type, (XmlOptions)null);
      }

      public static XmlInteger parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse(is, XmlInteger.type, options);
      }

      public static XmlInteger parse(Reader r) throws XmlException, IOException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlInteger.type, (XmlOptions)null);
      }

      public static XmlInteger parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse(r, XmlInteger.type, options);
      }

      public static XmlInteger parse(Node node) throws XmlException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse((Node)node, XmlInteger.type, (XmlOptions)null);
      }

      public static XmlInteger parse(Node node, XmlOptions options) throws XmlException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse(node, XmlInteger.type, options);
      }

      /** @deprecated */
      public static XmlInteger parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlInteger.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlInteger parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse(xis, XmlInteger.type, options);
      }

      public static XmlInteger parse(XMLStreamReader xsr) throws XmlException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlInteger.type, (XmlOptions)null);
      }

      public static XmlInteger parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlInteger)XmlBeans.getContextTypeLoader().parse(xsr, XmlInteger.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlInteger.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlInteger.type, options);
      }

      private Factory() {
      }
   }
}
