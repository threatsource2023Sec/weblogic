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

public interface XmlShort extends XmlInt {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_short");

   short getShortValue();

   void setShortValue(short var1);

   /** @deprecated */
   short shortValue();

   /** @deprecated */
   void set(short var1);

   public static final class Factory {
      public static XmlShort newInstance() {
         return (XmlShort)XmlBeans.getContextTypeLoader().newInstance(XmlShort.type, (XmlOptions)null);
      }

      public static XmlShort newInstance(XmlOptions options) {
         return (XmlShort)XmlBeans.getContextTypeLoader().newInstance(XmlShort.type, options);
      }

      public static XmlShort newValue(Object obj) {
         return (XmlShort)XmlShort.type.newValue(obj);
      }

      public static XmlShort parse(String s) throws XmlException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse((String)s, XmlShort.type, (XmlOptions)null);
      }

      public static XmlShort parse(String s, XmlOptions options) throws XmlException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse(s, XmlShort.type, options);
      }

      public static XmlShort parse(File f) throws XmlException, IOException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse((File)f, XmlShort.type, (XmlOptions)null);
      }

      public static XmlShort parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse(f, XmlShort.type, options);
      }

      public static XmlShort parse(URL u) throws XmlException, IOException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse((URL)u, XmlShort.type, (XmlOptions)null);
      }

      public static XmlShort parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse(u, XmlShort.type, options);
      }

      public static XmlShort parse(InputStream is) throws XmlException, IOException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlShort.type, (XmlOptions)null);
      }

      public static XmlShort parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse(is, XmlShort.type, options);
      }

      public static XmlShort parse(Reader r) throws XmlException, IOException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlShort.type, (XmlOptions)null);
      }

      public static XmlShort parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse(r, XmlShort.type, options);
      }

      public static XmlShort parse(Node node) throws XmlException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse((Node)node, XmlShort.type, (XmlOptions)null);
      }

      public static XmlShort parse(Node node, XmlOptions options) throws XmlException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse(node, XmlShort.type, options);
      }

      /** @deprecated */
      public static XmlShort parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlShort.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlShort parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse(xis, XmlShort.type, options);
      }

      public static XmlShort parse(XMLStreamReader xsr) throws XmlException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlShort.type, (XmlOptions)null);
      }

      public static XmlShort parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlShort)XmlBeans.getContextTypeLoader().parse(xsr, XmlShort.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlShort.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlShort.type, options);
      }

      private Factory() {
      }
   }
}
