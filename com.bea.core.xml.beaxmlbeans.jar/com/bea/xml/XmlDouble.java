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

public interface XmlDouble extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_double");

   double getDoubleValue();

   void setDoubleValue(double var1);

   /** @deprecated */
   double doubleValue();

   /** @deprecated */
   void set(double var1);

   public static final class Factory {
      public static XmlDouble newInstance() {
         return (XmlDouble)XmlBeans.getContextTypeLoader().newInstance(XmlDouble.type, (XmlOptions)null);
      }

      public static XmlDouble newInstance(XmlOptions options) {
         return (XmlDouble)XmlBeans.getContextTypeLoader().newInstance(XmlDouble.type, options);
      }

      public static XmlDouble newValue(Object obj) {
         return (XmlDouble)XmlDouble.type.newValue(obj);
      }

      public static XmlDouble parse(String s) throws XmlException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse((String)s, XmlDouble.type, (XmlOptions)null);
      }

      public static XmlDouble parse(String s, XmlOptions options) throws XmlException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse(s, XmlDouble.type, options);
      }

      public static XmlDouble parse(File f) throws XmlException, IOException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse((File)f, XmlDouble.type, (XmlOptions)null);
      }

      public static XmlDouble parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse(f, XmlDouble.type, options);
      }

      public static XmlDouble parse(URL u) throws XmlException, IOException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse((URL)u, XmlDouble.type, (XmlOptions)null);
      }

      public static XmlDouble parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse(u, XmlDouble.type, options);
      }

      public static XmlDouble parse(InputStream is) throws XmlException, IOException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlDouble.type, (XmlOptions)null);
      }

      public static XmlDouble parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse(is, XmlDouble.type, options);
      }

      public static XmlDouble parse(Reader r) throws XmlException, IOException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlDouble.type, (XmlOptions)null);
      }

      public static XmlDouble parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse(r, XmlDouble.type, options);
      }

      public static XmlDouble parse(Node node) throws XmlException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse((Node)node, XmlDouble.type, (XmlOptions)null);
      }

      public static XmlDouble parse(Node node, XmlOptions options) throws XmlException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse(node, XmlDouble.type, options);
      }

      /** @deprecated */
      public static XmlDouble parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlDouble.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlDouble parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse(xis, XmlDouble.type, options);
      }

      public static XmlDouble parse(XMLStreamReader xsr) throws XmlException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlDouble.type, (XmlOptions)null);
      }

      public static XmlDouble parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlDouble)XmlBeans.getContextTypeLoader().parse(xsr, XmlDouble.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlDouble.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlDouble.type, options);
      }

      private Factory() {
      }
   }
}
