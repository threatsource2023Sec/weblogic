package org.apache.xmlbeans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface XmlNOTATION extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_NOTATION");

   public static final class Factory {
      public static XmlNOTATION newInstance() {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().newInstance(XmlNOTATION.type, (XmlOptions)null);
      }

      public static XmlNOTATION newInstance(XmlOptions options) {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().newInstance(XmlNOTATION.type, options);
      }

      public static XmlNOTATION newValue(Object obj) {
         return (XmlNOTATION)XmlNOTATION.type.newValue(obj);
      }

      public static XmlNOTATION parse(String s) throws XmlException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse((String)s, XmlNOTATION.type, (XmlOptions)null);
      }

      public static XmlNOTATION parse(String s, XmlOptions options) throws XmlException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse(s, XmlNOTATION.type, options);
      }

      public static XmlNOTATION parse(File f) throws XmlException, IOException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse((File)f, XmlNOTATION.type, (XmlOptions)null);
      }

      public static XmlNOTATION parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse(f, XmlNOTATION.type, options);
      }

      public static XmlNOTATION parse(URL u) throws XmlException, IOException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse((URL)u, XmlNOTATION.type, (XmlOptions)null);
      }

      public static XmlNOTATION parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse(u, XmlNOTATION.type, options);
      }

      public static XmlNOTATION parse(InputStream is) throws XmlException, IOException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlNOTATION.type, (XmlOptions)null);
      }

      public static XmlNOTATION parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse(is, XmlNOTATION.type, options);
      }

      public static XmlNOTATION parse(Reader r) throws XmlException, IOException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlNOTATION.type, (XmlOptions)null);
      }

      public static XmlNOTATION parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse(r, XmlNOTATION.type, options);
      }

      public static XmlNOTATION parse(Node node) throws XmlException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse((Node)node, XmlNOTATION.type, (XmlOptions)null);
      }

      public static XmlNOTATION parse(Node node, XmlOptions options) throws XmlException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse(node, XmlNOTATION.type, options);
      }

      /** @deprecated */
      public static XmlNOTATION parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlNOTATION.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlNOTATION parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse(xis, XmlNOTATION.type, options);
      }

      public static XmlNOTATION parse(XMLStreamReader xsr) throws XmlException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlNOTATION.type, (XmlOptions)null);
      }

      public static XmlNOTATION parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlNOTATION)XmlBeans.getContextTypeLoader().parse(xsr, XmlNOTATION.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlNOTATION.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlNOTATION.type, options);
      }

      private Factory() {
      }
   }
}
