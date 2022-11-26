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

public interface XmlNormalizedString extends XmlString {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_normalizedString");

   public static final class Factory {
      public static XmlNormalizedString newInstance() {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().newInstance(XmlNormalizedString.type, (XmlOptions)null);
      }

      public static XmlNormalizedString newInstance(XmlOptions options) {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().newInstance(XmlNormalizedString.type, options);
      }

      public static XmlNormalizedString newValue(Object obj) {
         return (XmlNormalizedString)XmlNormalizedString.type.newValue(obj);
      }

      public static XmlNormalizedString parse(String s) throws XmlException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse((String)s, XmlNormalizedString.type, (XmlOptions)null);
      }

      public static XmlNormalizedString parse(String s, XmlOptions options) throws XmlException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse(s, XmlNormalizedString.type, options);
      }

      public static XmlNormalizedString parse(File f) throws XmlException, IOException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse((File)f, XmlNormalizedString.type, (XmlOptions)null);
      }

      public static XmlNormalizedString parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse(f, XmlNormalizedString.type, options);
      }

      public static XmlNormalizedString parse(URL u) throws XmlException, IOException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse((URL)u, XmlNormalizedString.type, (XmlOptions)null);
      }

      public static XmlNormalizedString parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse(u, XmlNormalizedString.type, options);
      }

      public static XmlNormalizedString parse(InputStream is) throws XmlException, IOException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlNormalizedString.type, (XmlOptions)null);
      }

      public static XmlNormalizedString parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse(is, XmlNormalizedString.type, options);
      }

      public static XmlNormalizedString parse(Reader r) throws XmlException, IOException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlNormalizedString.type, (XmlOptions)null);
      }

      public static XmlNormalizedString parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse(r, XmlNormalizedString.type, options);
      }

      public static XmlNormalizedString parse(Node node) throws XmlException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse((Node)node, XmlNormalizedString.type, (XmlOptions)null);
      }

      public static XmlNormalizedString parse(Node node, XmlOptions options) throws XmlException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse(node, XmlNormalizedString.type, options);
      }

      /** @deprecated */
      public static XmlNormalizedString parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlNormalizedString.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlNormalizedString parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse(xis, XmlNormalizedString.type, options);
      }

      public static XmlNormalizedString parse(XMLStreamReader xsr) throws XmlException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlNormalizedString.type, (XmlOptions)null);
      }

      public static XmlNormalizedString parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlNormalizedString)XmlBeans.getContextTypeLoader().parse(xsr, XmlNormalizedString.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlNormalizedString.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlNormalizedString.type, options);
      }

      private Factory() {
      }
   }
}
