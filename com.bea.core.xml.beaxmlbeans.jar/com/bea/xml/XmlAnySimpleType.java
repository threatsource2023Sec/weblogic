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

public interface XmlAnySimpleType extends XmlObject {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_anySimpleType");

   /** @deprecated */
   String stringValue();

   /** @deprecated */
   void set(String var1);

   String getStringValue();

   void setStringValue(String var1);

   public static final class Factory {
      public static XmlAnySimpleType newInstance() {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().newInstance(XmlAnySimpleType.type, (XmlOptions)null);
      }

      public static XmlAnySimpleType newInstance(XmlOptions options) {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().newInstance(XmlAnySimpleType.type, options);
      }

      public static XmlAnySimpleType newValue(Object obj) {
         return XmlAnySimpleType.type.newValue(obj);
      }

      public static XmlAnySimpleType parse(String s) throws XmlException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse((String)s, XmlAnySimpleType.type, (XmlOptions)null);
      }

      public static XmlAnySimpleType parse(String s, XmlOptions options) throws XmlException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse(s, XmlAnySimpleType.type, options);
      }

      public static XmlAnySimpleType parse(File f) throws XmlException, IOException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse((File)f, XmlAnySimpleType.type, (XmlOptions)null);
      }

      public static XmlAnySimpleType parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse(f, XmlAnySimpleType.type, options);
      }

      public static XmlAnySimpleType parse(URL u) throws XmlException, IOException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse((URL)u, XmlAnySimpleType.type, (XmlOptions)null);
      }

      public static XmlAnySimpleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse(u, XmlAnySimpleType.type, options);
      }

      public static XmlAnySimpleType parse(InputStream is) throws XmlException, IOException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlAnySimpleType.type, (XmlOptions)null);
      }

      public static XmlAnySimpleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse(is, XmlAnySimpleType.type, options);
      }

      public static XmlAnySimpleType parse(Reader r) throws XmlException, IOException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlAnySimpleType.type, (XmlOptions)null);
      }

      public static XmlAnySimpleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse(r, XmlAnySimpleType.type, options);
      }

      public static XmlAnySimpleType parse(Node node) throws XmlException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse((Node)node, XmlAnySimpleType.type, (XmlOptions)null);
      }

      public static XmlAnySimpleType parse(Node node, XmlOptions options) throws XmlException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse(node, XmlAnySimpleType.type, options);
      }

      /** @deprecated */
      public static XmlAnySimpleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlAnySimpleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlAnySimpleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse(xis, XmlAnySimpleType.type, options);
      }

      public static XmlAnySimpleType parse(XMLStreamReader xsr) throws XmlException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlAnySimpleType.type, (XmlOptions)null);
      }

      public static XmlAnySimpleType parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlAnySimpleType)XmlBeans.getContextTypeLoader().parse(xsr, XmlAnySimpleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlAnySimpleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlAnySimpleType.type, options);
      }

      private Factory() {
      }
   }
}
