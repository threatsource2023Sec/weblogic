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

public interface XmlName extends XmlToken {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_Name");

   public static final class Factory {
      public static XmlName newInstance() {
         return (XmlName)XmlBeans.getContextTypeLoader().newInstance(XmlName.type, (XmlOptions)null);
      }

      public static XmlName newInstance(XmlOptions options) {
         return (XmlName)XmlBeans.getContextTypeLoader().newInstance(XmlName.type, options);
      }

      public static XmlName newValue(Object obj) {
         return (XmlName)XmlName.type.newValue(obj);
      }

      public static XmlName parse(String s) throws XmlException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse((String)s, XmlName.type, (XmlOptions)null);
      }

      public static XmlName parse(String s, XmlOptions options) throws XmlException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse(s, XmlName.type, options);
      }

      public static XmlName parse(File f) throws XmlException, IOException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse((File)f, XmlName.type, (XmlOptions)null);
      }

      public static XmlName parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse(f, XmlName.type, options);
      }

      public static XmlName parse(URL u) throws XmlException, IOException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse((URL)u, XmlName.type, (XmlOptions)null);
      }

      public static XmlName parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse(u, XmlName.type, options);
      }

      public static XmlName parse(InputStream is) throws XmlException, IOException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlName.type, (XmlOptions)null);
      }

      public static XmlName parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse(is, XmlName.type, options);
      }

      public static XmlName parse(Reader r) throws XmlException, IOException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlName.type, (XmlOptions)null);
      }

      public static XmlName parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse(r, XmlName.type, options);
      }

      public static XmlName parse(Node node) throws XmlException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse((Node)node, XmlName.type, (XmlOptions)null);
      }

      public static XmlName parse(Node node, XmlOptions options) throws XmlException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse(node, XmlName.type, options);
      }

      /** @deprecated */
      public static XmlName parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlName parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse(xis, XmlName.type, options);
      }

      public static XmlName parse(XMLStreamReader xsr) throws XmlException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlName.type, (XmlOptions)null);
      }

      public static XmlName parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlName)XmlBeans.getContextTypeLoader().parse(xsr, XmlName.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlName.type, options);
      }

      private Factory() {
      }
   }
}
