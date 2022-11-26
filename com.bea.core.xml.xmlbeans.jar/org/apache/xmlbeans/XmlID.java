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

public interface XmlID extends XmlNCName {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_ID");

   public static final class Factory {
      public static XmlID newInstance() {
         return (XmlID)XmlBeans.getContextTypeLoader().newInstance(XmlID.type, (XmlOptions)null);
      }

      public static XmlID newInstance(XmlOptions options) {
         return (XmlID)XmlBeans.getContextTypeLoader().newInstance(XmlID.type, options);
      }

      public static XmlID newValue(Object obj) {
         return (XmlID)XmlID.type.newValue(obj);
      }

      public static XmlID parse(String s) throws XmlException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse((String)s, XmlID.type, (XmlOptions)null);
      }

      public static XmlID parse(String s, XmlOptions options) throws XmlException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse(s, XmlID.type, options);
      }

      public static XmlID parse(File f) throws XmlException, IOException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse((File)f, XmlID.type, (XmlOptions)null);
      }

      public static XmlID parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse(f, XmlID.type, options);
      }

      public static XmlID parse(URL u) throws XmlException, IOException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse((URL)u, XmlID.type, (XmlOptions)null);
      }

      public static XmlID parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse(u, XmlID.type, options);
      }

      public static XmlID parse(InputStream is) throws XmlException, IOException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlID.type, (XmlOptions)null);
      }

      public static XmlID parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse(is, XmlID.type, options);
      }

      public static XmlID parse(Reader r) throws XmlException, IOException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlID.type, (XmlOptions)null);
      }

      public static XmlID parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse(r, XmlID.type, options);
      }

      public static XmlID parse(Node node) throws XmlException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse((Node)node, XmlID.type, (XmlOptions)null);
      }

      public static XmlID parse(Node node, XmlOptions options) throws XmlException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse(node, XmlID.type, options);
      }

      /** @deprecated */
      public static XmlID parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlID.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlID parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse(xis, XmlID.type, options);
      }

      public static XmlID parse(XMLStreamReader xsr) throws XmlException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlID.type, (XmlOptions)null);
      }

      public static XmlID parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlID)XmlBeans.getContextTypeLoader().parse(xsr, XmlID.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlID.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlID.type, options);
      }

      private Factory() {
      }
   }
}
