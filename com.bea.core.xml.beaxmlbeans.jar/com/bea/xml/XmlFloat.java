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

public interface XmlFloat extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_float");

   float getFloatValue();

   void setFloatValue(float var1);

   /** @deprecated */
   float floatValue();

   /** @deprecated */
   void set(float var1);

   public static final class Factory {
      public static XmlFloat newInstance() {
         return (XmlFloat)XmlBeans.getContextTypeLoader().newInstance(XmlFloat.type, (XmlOptions)null);
      }

      public static XmlFloat newInstance(XmlOptions options) {
         return (XmlFloat)XmlBeans.getContextTypeLoader().newInstance(XmlFloat.type, options);
      }

      public static XmlFloat newValue(Object obj) {
         return (XmlFloat)XmlFloat.type.newValue(obj);
      }

      public static XmlFloat parse(String s) throws XmlException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse((String)s, XmlFloat.type, (XmlOptions)null);
      }

      public static XmlFloat parse(String s, XmlOptions options) throws XmlException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse(s, XmlFloat.type, options);
      }

      public static XmlFloat parse(File f) throws XmlException, IOException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse((File)f, XmlFloat.type, (XmlOptions)null);
      }

      public static XmlFloat parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse(f, XmlFloat.type, options);
      }

      public static XmlFloat parse(URL u) throws XmlException, IOException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse((URL)u, XmlFloat.type, (XmlOptions)null);
      }

      public static XmlFloat parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse(u, XmlFloat.type, options);
      }

      public static XmlFloat parse(InputStream is) throws XmlException, IOException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlFloat.type, (XmlOptions)null);
      }

      public static XmlFloat parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse(is, XmlFloat.type, options);
      }

      public static XmlFloat parse(Reader r) throws XmlException, IOException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlFloat.type, (XmlOptions)null);
      }

      public static XmlFloat parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse(r, XmlFloat.type, options);
      }

      public static XmlFloat parse(Node node) throws XmlException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse((Node)node, XmlFloat.type, (XmlOptions)null);
      }

      public static XmlFloat parse(Node node, XmlOptions options) throws XmlException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse(node, XmlFloat.type, options);
      }

      /** @deprecated */
      public static XmlFloat parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlFloat.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlFloat parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse(xis, XmlFloat.type, options);
      }

      public static XmlFloat parse(XMLStreamReader xsr) throws XmlException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlFloat.type, (XmlOptions)null);
      }

      public static XmlFloat parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlFloat)XmlBeans.getContextTypeLoader().parse(xsr, XmlFloat.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlFloat.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlFloat.type, options);
      }

      private Factory() {
      }
   }
}
