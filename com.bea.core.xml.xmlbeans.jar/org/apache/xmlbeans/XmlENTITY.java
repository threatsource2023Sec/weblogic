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

public interface XmlENTITY extends XmlNCName {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_ENTITY");

   public static final class Factory {
      public static XmlENTITY newInstance() {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().newInstance(XmlENTITY.type, (XmlOptions)null);
      }

      public static XmlENTITY newInstance(XmlOptions options) {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().newInstance(XmlENTITY.type, options);
      }

      public static XmlENTITY newValue(Object obj) {
         return (XmlENTITY)XmlENTITY.type.newValue(obj);
      }

      public static XmlENTITY parse(String s) throws XmlException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse((String)s, XmlENTITY.type, (XmlOptions)null);
      }

      public static XmlENTITY parse(String s, XmlOptions options) throws XmlException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse(s, XmlENTITY.type, options);
      }

      public static XmlENTITY parse(File f) throws XmlException, IOException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse((File)f, XmlENTITY.type, (XmlOptions)null);
      }

      public static XmlENTITY parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse(f, XmlENTITY.type, options);
      }

      public static XmlENTITY parse(URL u) throws XmlException, IOException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse((URL)u, XmlENTITY.type, (XmlOptions)null);
      }

      public static XmlENTITY parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse(u, XmlENTITY.type, options);
      }

      public static XmlENTITY parse(InputStream is) throws XmlException, IOException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlENTITY.type, (XmlOptions)null);
      }

      public static XmlENTITY parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse(is, XmlENTITY.type, options);
      }

      public static XmlENTITY parse(Reader r) throws XmlException, IOException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlENTITY.type, (XmlOptions)null);
      }

      public static XmlENTITY parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse(r, XmlENTITY.type, options);
      }

      public static XmlENTITY parse(Node node) throws XmlException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse((Node)node, XmlENTITY.type, (XmlOptions)null);
      }

      public static XmlENTITY parse(Node node, XmlOptions options) throws XmlException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse(node, XmlENTITY.type, options);
      }

      /** @deprecated */
      public static XmlENTITY parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlENTITY.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlENTITY parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse(xis, XmlENTITY.type, options);
      }

      public static XmlENTITY parse(XMLStreamReader xsr) throws XmlException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlENTITY.type, (XmlOptions)null);
      }

      public static XmlENTITY parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlENTITY)XmlBeans.getContextTypeLoader().parse(xsr, XmlENTITY.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlENTITY.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlENTITY.type, options);
      }

      private Factory() {
      }
   }
}
