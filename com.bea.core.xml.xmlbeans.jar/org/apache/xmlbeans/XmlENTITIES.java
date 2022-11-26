package org.apache.xmlbeans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface XmlENTITIES extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_ENTITIES");

   List getListValue();

   List xgetListValue();

   void setListValue(List var1);

   /** @deprecated */
   List listValue();

   /** @deprecated */
   List xlistValue();

   /** @deprecated */
   void set(List var1);

   public static final class Factory {
      public static XmlENTITIES newInstance() {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().newInstance(XmlENTITIES.type, (XmlOptions)null);
      }

      public static XmlENTITIES newInstance(XmlOptions options) {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().newInstance(XmlENTITIES.type, options);
      }

      public static XmlENTITIES newValue(Object obj) {
         return (XmlENTITIES)XmlENTITIES.type.newValue(obj);
      }

      public static XmlENTITIES parse(String s) throws XmlException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse((String)s, XmlENTITIES.type, (XmlOptions)null);
      }

      public static XmlENTITIES parse(String s, XmlOptions options) throws XmlException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse(s, XmlENTITIES.type, options);
      }

      public static XmlENTITIES parse(File f) throws XmlException, IOException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse((File)f, XmlENTITIES.type, (XmlOptions)null);
      }

      public static XmlENTITIES parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse(f, XmlENTITIES.type, options);
      }

      public static XmlENTITIES parse(URL u) throws XmlException, IOException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse((URL)u, XmlENTITIES.type, (XmlOptions)null);
      }

      public static XmlENTITIES parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse(u, XmlENTITIES.type, options);
      }

      public static XmlENTITIES parse(InputStream is) throws XmlException, IOException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlENTITIES.type, (XmlOptions)null);
      }

      public static XmlENTITIES parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse(is, XmlENTITIES.type, options);
      }

      public static XmlENTITIES parse(Reader r) throws XmlException, IOException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlENTITIES.type, (XmlOptions)null);
      }

      public static XmlENTITIES parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse(r, XmlENTITIES.type, options);
      }

      public static XmlENTITIES parse(Node node) throws XmlException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse((Node)node, XmlENTITIES.type, (XmlOptions)null);
      }

      public static XmlENTITIES parse(Node node, XmlOptions options) throws XmlException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse(node, XmlENTITIES.type, options);
      }

      /** @deprecated */
      public static XmlENTITIES parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlENTITIES.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlENTITIES parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse(xis, XmlENTITIES.type, options);
      }

      public static XmlENTITIES parse(XMLStreamReader xsr) throws XmlException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlENTITIES.type, (XmlOptions)null);
      }

      public static XmlENTITIES parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlENTITIES)XmlBeans.getContextTypeLoader().parse(xsr, XmlENTITIES.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlENTITIES.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlENTITIES.type, options);
      }

      private Factory() {
      }
   }
}
