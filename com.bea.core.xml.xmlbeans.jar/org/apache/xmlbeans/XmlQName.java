package org.apache.xmlbeans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface XmlQName extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_QName");

   QName getQNameValue();

   void setQNameValue(QName var1);

   /** @deprecated */
   QName qNameValue();

   /** @deprecated */
   void set(QName var1);

   public static final class Factory {
      public static XmlQName newInstance() {
         return (XmlQName)XmlBeans.getContextTypeLoader().newInstance(XmlQName.type, (XmlOptions)null);
      }

      public static XmlQName newInstance(XmlOptions options) {
         return (XmlQName)XmlBeans.getContextTypeLoader().newInstance(XmlQName.type, options);
      }

      public static XmlQName newValue(Object obj) {
         return (XmlQName)XmlQName.type.newValue(obj);
      }

      public static XmlQName parse(String s) throws XmlException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse((String)s, XmlQName.type, (XmlOptions)null);
      }

      public static XmlQName parse(String s, XmlOptions options) throws XmlException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse(s, XmlQName.type, options);
      }

      public static XmlQName parse(File f) throws XmlException, IOException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse((File)f, XmlQName.type, (XmlOptions)null);
      }

      public static XmlQName parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse(f, XmlQName.type, options);
      }

      public static XmlQName parse(URL u) throws XmlException, IOException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse((URL)u, XmlQName.type, (XmlOptions)null);
      }

      public static XmlQName parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse(u, XmlQName.type, options);
      }

      public static XmlQName parse(InputStream is) throws XmlException, IOException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlQName.type, (XmlOptions)null);
      }

      public static XmlQName parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse(is, XmlQName.type, options);
      }

      public static XmlQName parse(Reader r) throws XmlException, IOException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlQName.type, (XmlOptions)null);
      }

      public static XmlQName parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse(r, XmlQName.type, options);
      }

      public static XmlQName parse(Node node) throws XmlException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse((Node)node, XmlQName.type, (XmlOptions)null);
      }

      public static XmlQName parse(Node node, XmlOptions options) throws XmlException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse(node, XmlQName.type, options);
      }

      /** @deprecated */
      public static XmlQName parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlQName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlQName parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse(xis, XmlQName.type, options);
      }

      public static XmlQName parse(XMLStreamReader xsr) throws XmlException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlQName.type, (XmlOptions)null);
      }

      public static XmlQName parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlQName)XmlBeans.getContextTypeLoader().parse(xsr, XmlQName.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlQName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlQName.type, options);
      }

      private Factory() {
      }
   }
}
