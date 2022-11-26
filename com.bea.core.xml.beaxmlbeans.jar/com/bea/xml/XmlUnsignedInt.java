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

public interface XmlUnsignedInt extends XmlUnsignedLong {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_unsignedInt");

   long getLongValue();

   void setLongValue(long var1);

   /** @deprecated */
   long longValue();

   /** @deprecated */
   void set(long var1);

   public static final class Factory {
      public static XmlUnsignedInt newInstance() {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().newInstance(XmlUnsignedInt.type, (XmlOptions)null);
      }

      public static XmlUnsignedInt newInstance(XmlOptions options) {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().newInstance(XmlUnsignedInt.type, options);
      }

      public static XmlUnsignedInt newValue(Object obj) {
         return (XmlUnsignedInt)XmlUnsignedInt.type.newValue(obj);
      }

      public static XmlUnsignedInt parse(String s) throws XmlException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse((String)s, XmlUnsignedInt.type, (XmlOptions)null);
      }

      public static XmlUnsignedInt parse(String s, XmlOptions options) throws XmlException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse(s, XmlUnsignedInt.type, options);
      }

      public static XmlUnsignedInt parse(File f) throws XmlException, IOException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse((File)f, XmlUnsignedInt.type, (XmlOptions)null);
      }

      public static XmlUnsignedInt parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse(f, XmlUnsignedInt.type, options);
      }

      public static XmlUnsignedInt parse(URL u) throws XmlException, IOException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse((URL)u, XmlUnsignedInt.type, (XmlOptions)null);
      }

      public static XmlUnsignedInt parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse(u, XmlUnsignedInt.type, options);
      }

      public static XmlUnsignedInt parse(InputStream is) throws XmlException, IOException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlUnsignedInt.type, (XmlOptions)null);
      }

      public static XmlUnsignedInt parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse(is, XmlUnsignedInt.type, options);
      }

      public static XmlUnsignedInt parse(Reader r) throws XmlException, IOException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlUnsignedInt.type, (XmlOptions)null);
      }

      public static XmlUnsignedInt parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse(r, XmlUnsignedInt.type, options);
      }

      public static XmlUnsignedInt parse(Node node) throws XmlException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse((Node)node, XmlUnsignedInt.type, (XmlOptions)null);
      }

      public static XmlUnsignedInt parse(Node node, XmlOptions options) throws XmlException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse(node, XmlUnsignedInt.type, options);
      }

      /** @deprecated */
      public static XmlUnsignedInt parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlUnsignedInt.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlUnsignedInt parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse(xis, XmlUnsignedInt.type, options);
      }

      public static XmlUnsignedInt parse(XMLStreamReader xsr) throws XmlException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlUnsignedInt.type, (XmlOptions)null);
      }

      public static XmlUnsignedInt parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlUnsignedInt)XmlBeans.getContextTypeLoader().parse(xsr, XmlUnsignedInt.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlUnsignedInt.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlUnsignedInt.type, options);
      }

      private Factory() {
      }
   }
}
