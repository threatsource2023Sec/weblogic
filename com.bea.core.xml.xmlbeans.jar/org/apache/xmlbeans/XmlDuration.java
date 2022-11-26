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

public interface XmlDuration extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_duration");

   GDuration getGDurationValue();

   void setGDurationValue(GDuration var1);

   /** @deprecated */
   GDuration gDurationValue();

   /** @deprecated */
   void set(GDurationSpecification var1);

   public static final class Factory {
      public static XmlDuration newInstance() {
         return (XmlDuration)XmlBeans.getContextTypeLoader().newInstance(XmlDuration.type, (XmlOptions)null);
      }

      public static XmlDuration newInstance(XmlOptions options) {
         return (XmlDuration)XmlBeans.getContextTypeLoader().newInstance(XmlDuration.type, options);
      }

      public static XmlDuration newValue(Object obj) {
         return (XmlDuration)XmlDuration.type.newValue(obj);
      }

      public static XmlDuration parse(String s) throws XmlException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse((String)s, XmlDuration.type, (XmlOptions)null);
      }

      public static XmlDuration parse(String s, XmlOptions options) throws XmlException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse(s, XmlDuration.type, options);
      }

      public static XmlDuration parse(File f) throws XmlException, IOException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse((File)f, XmlDuration.type, (XmlOptions)null);
      }

      public static XmlDuration parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse(f, XmlDuration.type, options);
      }

      public static XmlDuration parse(URL u) throws XmlException, IOException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse((URL)u, XmlDuration.type, (XmlOptions)null);
      }

      public static XmlDuration parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse(u, XmlDuration.type, options);
      }

      public static XmlDuration parse(InputStream is) throws XmlException, IOException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlDuration.type, (XmlOptions)null);
      }

      public static XmlDuration parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse(is, XmlDuration.type, options);
      }

      public static XmlDuration parse(Reader r) throws XmlException, IOException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlDuration.type, (XmlOptions)null);
      }

      public static XmlDuration parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse(r, XmlDuration.type, options);
      }

      public static XmlDuration parse(Node node) throws XmlException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse((Node)node, XmlDuration.type, (XmlOptions)null);
      }

      public static XmlDuration parse(Node node, XmlOptions options) throws XmlException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse(node, XmlDuration.type, options);
      }

      /** @deprecated */
      public static XmlDuration parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlDuration.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlDuration parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse(xis, XmlDuration.type, options);
      }

      public static XmlDuration parse(XMLStreamReader xsr) throws XmlException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlDuration.type, (XmlOptions)null);
      }

      public static XmlDuration parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlDuration)XmlBeans.getContextTypeLoader().parse(xsr, XmlDuration.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlDuration.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlDuration.type, options);
      }

      private Factory() {
      }
   }
}
