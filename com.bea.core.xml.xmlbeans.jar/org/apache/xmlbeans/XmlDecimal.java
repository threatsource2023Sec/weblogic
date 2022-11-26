package org.apache.xmlbeans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface XmlDecimal extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_decimal");

   BigDecimal getBigDecimalValue();

   void setBigDecimalValue(BigDecimal var1);

   /** @deprecated */
   BigDecimal bigDecimalValue();

   /** @deprecated */
   void set(BigDecimal var1);

   public static final class Factory {
      public static XmlDecimal newInstance() {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().newInstance(XmlDecimal.type, (XmlOptions)null);
      }

      public static XmlDecimal newInstance(XmlOptions options) {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().newInstance(XmlDecimal.type, options);
      }

      public static XmlDecimal newValue(Object obj) {
         return (XmlDecimal)XmlDecimal.type.newValue(obj);
      }

      public static XmlDecimal parse(String s) throws XmlException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse((String)s, XmlDecimal.type, (XmlOptions)null);
      }

      public static XmlDecimal parse(String s, XmlOptions options) throws XmlException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse(s, XmlDecimal.type, options);
      }

      public static XmlDecimal parse(File f) throws XmlException, IOException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse((File)f, XmlDecimal.type, (XmlOptions)null);
      }

      public static XmlDecimal parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse(f, XmlDecimal.type, options);
      }

      public static XmlDecimal parse(URL u) throws XmlException, IOException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse((URL)u, XmlDecimal.type, (XmlOptions)null);
      }

      public static XmlDecimal parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse(u, XmlDecimal.type, options);
      }

      public static XmlDecimal parse(InputStream is) throws XmlException, IOException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlDecimal.type, (XmlOptions)null);
      }

      public static XmlDecimal parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse(is, XmlDecimal.type, options);
      }

      public static XmlDecimal parse(Reader r) throws XmlException, IOException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlDecimal.type, (XmlOptions)null);
      }

      public static XmlDecimal parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse(r, XmlDecimal.type, options);
      }

      public static XmlDecimal parse(Node node) throws XmlException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse((Node)node, XmlDecimal.type, (XmlOptions)null);
      }

      public static XmlDecimal parse(Node node, XmlOptions options) throws XmlException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse(node, XmlDecimal.type, options);
      }

      /** @deprecated */
      public static XmlDecimal parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlDecimal.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlDecimal parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse(xis, XmlDecimal.type, options);
      }

      public static XmlDecimal parse(XMLStreamReader xsr) throws XmlException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlDecimal.type, (XmlOptions)null);
      }

      public static XmlDecimal parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlDecimal)XmlBeans.getContextTypeLoader().parse(xsr, XmlDecimal.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlDecimal.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlDecimal.type, options);
      }

      private Factory() {
      }
   }
}
