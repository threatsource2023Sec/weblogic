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

public interface XmlUnsignedByte extends XmlUnsignedShort {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_unsignedByte");

   short getShortValue();

   void setShortValue(short var1);

   /** @deprecated */
   short shortValue();

   /** @deprecated */
   void set(short var1);

   public static final class Factory {
      public static XmlUnsignedByte newInstance() {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().newInstance(XmlUnsignedByte.type, (XmlOptions)null);
      }

      public static XmlUnsignedByte newInstance(XmlOptions options) {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().newInstance(XmlUnsignedByte.type, options);
      }

      public static XmlUnsignedByte newValue(Object obj) {
         return (XmlUnsignedByte)XmlUnsignedByte.type.newValue(obj);
      }

      public static XmlUnsignedByte parse(String s) throws XmlException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse((String)s, XmlUnsignedByte.type, (XmlOptions)null);
      }

      public static XmlUnsignedByte parse(String s, XmlOptions options) throws XmlException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse(s, XmlUnsignedByte.type, options);
      }

      public static XmlUnsignedByte parse(File f) throws XmlException, IOException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse((File)f, XmlUnsignedByte.type, (XmlOptions)null);
      }

      public static XmlUnsignedByte parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse(f, XmlUnsignedByte.type, options);
      }

      public static XmlUnsignedByte parse(URL u) throws XmlException, IOException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse((URL)u, XmlUnsignedByte.type, (XmlOptions)null);
      }

      public static XmlUnsignedByte parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse(u, XmlUnsignedByte.type, options);
      }

      public static XmlUnsignedByte parse(InputStream is) throws XmlException, IOException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlUnsignedByte.type, (XmlOptions)null);
      }

      public static XmlUnsignedByte parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse(is, XmlUnsignedByte.type, options);
      }

      public static XmlUnsignedByte parse(Reader r) throws XmlException, IOException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlUnsignedByte.type, (XmlOptions)null);
      }

      public static XmlUnsignedByte parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse(r, XmlUnsignedByte.type, options);
      }

      public static XmlUnsignedByte parse(Node node) throws XmlException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse((Node)node, XmlUnsignedByte.type, (XmlOptions)null);
      }

      public static XmlUnsignedByte parse(Node node, XmlOptions options) throws XmlException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse(node, XmlUnsignedByte.type, options);
      }

      /** @deprecated */
      public static XmlUnsignedByte parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlUnsignedByte.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlUnsignedByte parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse(xis, XmlUnsignedByte.type, options);
      }

      public static XmlUnsignedByte parse(XMLStreamReader xsr) throws XmlException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlUnsignedByte.type, (XmlOptions)null);
      }

      public static XmlUnsignedByte parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlUnsignedByte)XmlBeans.getContextTypeLoader().parse(xsr, XmlUnsignedByte.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlUnsignedByte.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlUnsignedByte.type, options);
      }

      private Factory() {
      }
   }
}
