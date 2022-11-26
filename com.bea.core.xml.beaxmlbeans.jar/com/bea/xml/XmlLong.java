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

public interface XmlLong extends XmlInteger {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_long");

   long getLongValue();

   void setLongValue(long var1);

   /** @deprecated */
   long longValue();

   /** @deprecated */
   void set(long var1);

   public static final class Factory {
      public static XmlLong newInstance() {
         return (XmlLong)XmlBeans.getContextTypeLoader().newInstance(XmlLong.type, (XmlOptions)null);
      }

      public static XmlLong newInstance(XmlOptions options) {
         return (XmlLong)XmlBeans.getContextTypeLoader().newInstance(XmlLong.type, options);
      }

      public static XmlLong newValue(Object obj) {
         return (XmlLong)XmlLong.type.newValue(obj);
      }

      public static XmlLong parse(String s) throws XmlException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse((String)s, XmlLong.type, (XmlOptions)null);
      }

      public static XmlLong parse(String s, XmlOptions options) throws XmlException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse(s, XmlLong.type, options);
      }

      public static XmlLong parse(File f) throws XmlException, IOException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse((File)f, XmlLong.type, (XmlOptions)null);
      }

      public static XmlLong parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse(f, XmlLong.type, options);
      }

      public static XmlLong parse(URL u) throws XmlException, IOException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse((URL)u, XmlLong.type, (XmlOptions)null);
      }

      public static XmlLong parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse(u, XmlLong.type, options);
      }

      public static XmlLong parse(InputStream is) throws XmlException, IOException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlLong.type, (XmlOptions)null);
      }

      public static XmlLong parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse(is, XmlLong.type, options);
      }

      public static XmlLong parse(Reader r) throws XmlException, IOException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlLong.type, (XmlOptions)null);
      }

      public static XmlLong parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse(r, XmlLong.type, options);
      }

      public static XmlLong parse(Node node) throws XmlException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse((Node)node, XmlLong.type, (XmlOptions)null);
      }

      public static XmlLong parse(Node node, XmlOptions options) throws XmlException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse(node, XmlLong.type, options);
      }

      /** @deprecated */
      public static XmlLong parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlLong.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlLong parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse(xis, XmlLong.type, options);
      }

      public static XmlLong parse(XMLStreamReader xsr) throws XmlException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlLong.type, (XmlOptions)null);
      }

      public static XmlLong parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlLong)XmlBeans.getContextTypeLoader().parse(xsr, XmlLong.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlLong.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlLong.type, options);
      }

      private Factory() {
      }
   }
}
