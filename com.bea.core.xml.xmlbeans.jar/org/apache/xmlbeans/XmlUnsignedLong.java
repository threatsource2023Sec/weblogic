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

public interface XmlUnsignedLong extends XmlNonNegativeInteger {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_unsignedLong");

   public static final class Factory {
      public static XmlUnsignedLong newInstance() {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().newInstance(XmlUnsignedLong.type, (XmlOptions)null);
      }

      public static XmlUnsignedLong newInstance(XmlOptions options) {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().newInstance(XmlUnsignedLong.type, options);
      }

      public static XmlUnsignedLong newValue(Object obj) {
         return (XmlUnsignedLong)XmlUnsignedLong.type.newValue(obj);
      }

      public static XmlUnsignedLong parse(String s) throws XmlException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse((String)s, XmlUnsignedLong.type, (XmlOptions)null);
      }

      public static XmlUnsignedLong parse(String s, XmlOptions options) throws XmlException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse(s, XmlUnsignedLong.type, options);
      }

      public static XmlUnsignedLong parse(File f) throws XmlException, IOException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse((File)f, XmlUnsignedLong.type, (XmlOptions)null);
      }

      public static XmlUnsignedLong parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse(f, XmlUnsignedLong.type, options);
      }

      public static XmlUnsignedLong parse(URL u) throws XmlException, IOException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse((URL)u, XmlUnsignedLong.type, (XmlOptions)null);
      }

      public static XmlUnsignedLong parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse(u, XmlUnsignedLong.type, options);
      }

      public static XmlUnsignedLong parse(InputStream is) throws XmlException, IOException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlUnsignedLong.type, (XmlOptions)null);
      }

      public static XmlUnsignedLong parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse(is, XmlUnsignedLong.type, options);
      }

      public static XmlUnsignedLong parse(Reader r) throws XmlException, IOException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlUnsignedLong.type, (XmlOptions)null);
      }

      public static XmlUnsignedLong parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse(r, XmlUnsignedLong.type, options);
      }

      public static XmlUnsignedLong parse(Node node) throws XmlException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse((Node)node, XmlUnsignedLong.type, (XmlOptions)null);
      }

      public static XmlUnsignedLong parse(Node node, XmlOptions options) throws XmlException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse(node, XmlUnsignedLong.type, options);
      }

      /** @deprecated */
      public static XmlUnsignedLong parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlUnsignedLong.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlUnsignedLong parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse(xis, XmlUnsignedLong.type, options);
      }

      public static XmlUnsignedLong parse(XMLStreamReader xsr) throws XmlException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlUnsignedLong.type, (XmlOptions)null);
      }

      public static XmlUnsignedLong parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlUnsignedLong)XmlBeans.getContextTypeLoader().parse(xsr, XmlUnsignedLong.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlUnsignedLong.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlUnsignedLong.type, options);
      }

      private Factory() {
      }
   }
}
