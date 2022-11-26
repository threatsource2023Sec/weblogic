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

public interface XmlNegativeInteger extends XmlNonPositiveInteger {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_negativeInteger");

   public static final class Factory {
      public static XmlNegativeInteger newInstance() {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().newInstance(XmlNegativeInteger.type, (XmlOptions)null);
      }

      public static XmlNegativeInteger newInstance(XmlOptions options) {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().newInstance(XmlNegativeInteger.type, options);
      }

      public static XmlNegativeInteger newValue(Object obj) {
         return (XmlNegativeInteger)XmlNegativeInteger.type.newValue(obj);
      }

      public static XmlNegativeInteger parse(String s) throws XmlException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse((String)s, XmlNegativeInteger.type, (XmlOptions)null);
      }

      public static XmlNegativeInteger parse(String s, XmlOptions options) throws XmlException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse(s, XmlNegativeInteger.type, options);
      }

      public static XmlNegativeInteger parse(File f) throws XmlException, IOException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse((File)f, XmlNegativeInteger.type, (XmlOptions)null);
      }

      public static XmlNegativeInteger parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse(f, XmlNegativeInteger.type, options);
      }

      public static XmlNegativeInteger parse(URL u) throws XmlException, IOException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse((URL)u, XmlNegativeInteger.type, (XmlOptions)null);
      }

      public static XmlNegativeInteger parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse(u, XmlNegativeInteger.type, options);
      }

      public static XmlNegativeInteger parse(InputStream is) throws XmlException, IOException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlNegativeInteger.type, (XmlOptions)null);
      }

      public static XmlNegativeInteger parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse(is, XmlNegativeInteger.type, options);
      }

      public static XmlNegativeInteger parse(Reader r) throws XmlException, IOException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlNegativeInteger.type, (XmlOptions)null);
      }

      public static XmlNegativeInteger parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse(r, XmlNegativeInteger.type, options);
      }

      public static XmlNegativeInteger parse(Node node) throws XmlException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse((Node)node, XmlNegativeInteger.type, (XmlOptions)null);
      }

      public static XmlNegativeInteger parse(Node node, XmlOptions options) throws XmlException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse(node, XmlNegativeInteger.type, options);
      }

      /** @deprecated */
      public static XmlNegativeInteger parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlNegativeInteger.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlNegativeInteger parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse(xis, XmlNegativeInteger.type, options);
      }

      public static XmlNegativeInteger parse(XMLStreamReader xsr) throws XmlException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlNegativeInteger.type, (XmlOptions)null);
      }

      public static XmlNegativeInteger parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlNegativeInteger)XmlBeans.getContextTypeLoader().parse(xsr, XmlNegativeInteger.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlNegativeInteger.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlNegativeInteger.type, options);
      }

      private Factory() {
      }
   }
}
