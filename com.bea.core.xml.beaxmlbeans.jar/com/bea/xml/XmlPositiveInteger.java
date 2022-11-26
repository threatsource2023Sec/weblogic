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

public interface XmlPositiveInteger extends XmlNonNegativeInteger {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_positiveInteger");

   public static final class Factory {
      public static XmlPositiveInteger newInstance() {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().newInstance(XmlPositiveInteger.type, (XmlOptions)null);
      }

      public static XmlPositiveInteger newInstance(XmlOptions options) {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().newInstance(XmlPositiveInteger.type, options);
      }

      public static XmlPositiveInteger newValue(Object obj) {
         return (XmlPositiveInteger)XmlPositiveInteger.type.newValue(obj);
      }

      public static XmlPositiveInteger parse(String s) throws XmlException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse((String)s, XmlPositiveInteger.type, (XmlOptions)null);
      }

      public static XmlPositiveInteger parse(String s, XmlOptions options) throws XmlException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse(s, XmlPositiveInteger.type, options);
      }

      public static XmlPositiveInteger parse(File f) throws XmlException, IOException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse((File)f, XmlPositiveInteger.type, (XmlOptions)null);
      }

      public static XmlPositiveInteger parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse(f, XmlPositiveInteger.type, options);
      }

      public static XmlPositiveInteger parse(URL u) throws XmlException, IOException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse((URL)u, XmlPositiveInteger.type, (XmlOptions)null);
      }

      public static XmlPositiveInteger parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse(u, XmlPositiveInteger.type, options);
      }

      public static XmlPositiveInteger parse(InputStream is) throws XmlException, IOException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlPositiveInteger.type, (XmlOptions)null);
      }

      public static XmlPositiveInteger parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse(is, XmlPositiveInteger.type, options);
      }

      public static XmlPositiveInteger parse(Reader r) throws XmlException, IOException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlPositiveInteger.type, (XmlOptions)null);
      }

      public static XmlPositiveInteger parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse(r, XmlPositiveInteger.type, options);
      }

      public static XmlPositiveInteger parse(Node node) throws XmlException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse((Node)node, XmlPositiveInteger.type, (XmlOptions)null);
      }

      public static XmlPositiveInteger parse(Node node, XmlOptions options) throws XmlException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse(node, XmlPositiveInteger.type, options);
      }

      /** @deprecated */
      public static XmlPositiveInteger parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlPositiveInteger.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlPositiveInteger parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse(xis, XmlPositiveInteger.type, options);
      }

      public static XmlPositiveInteger parse(XMLStreamReader xsr) throws XmlException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlPositiveInteger.type, (XmlOptions)null);
      }

      public static XmlPositiveInteger parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlPositiveInteger)XmlBeans.getContextTypeLoader().parse(xsr, XmlPositiveInteger.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlPositiveInteger.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlPositiveInteger.type, options);
      }

      private Factory() {
      }
   }
}
