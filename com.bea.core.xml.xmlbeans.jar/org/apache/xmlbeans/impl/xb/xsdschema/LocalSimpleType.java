package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface LocalSimpleType extends SimpleType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LocalSimpleType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("localsimpletype410etype");

   public static final class Factory {
      public static LocalSimpleType newInstance() {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().newInstance(LocalSimpleType.type, (XmlOptions)null);
      }

      public static LocalSimpleType newInstance(XmlOptions options) {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().newInstance(LocalSimpleType.type, options);
      }

      public static LocalSimpleType parse(String xmlAsString) throws XmlException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, LocalSimpleType.type, (XmlOptions)null);
      }

      public static LocalSimpleType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalSimpleType.type, options);
      }

      public static LocalSimpleType parse(File file) throws XmlException, IOException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse((File)file, LocalSimpleType.type, (XmlOptions)null);
      }

      public static LocalSimpleType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse(file, LocalSimpleType.type, options);
      }

      public static LocalSimpleType parse(URL u) throws XmlException, IOException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse((URL)u, LocalSimpleType.type, (XmlOptions)null);
      }

      public static LocalSimpleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse(u, LocalSimpleType.type, options);
      }

      public static LocalSimpleType parse(InputStream is) throws XmlException, IOException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse((InputStream)is, LocalSimpleType.type, (XmlOptions)null);
      }

      public static LocalSimpleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse(is, LocalSimpleType.type, options);
      }

      public static LocalSimpleType parse(Reader r) throws XmlException, IOException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse((Reader)r, LocalSimpleType.type, (XmlOptions)null);
      }

      public static LocalSimpleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse(r, LocalSimpleType.type, options);
      }

      public static LocalSimpleType parse(XMLStreamReader sr) throws XmlException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, LocalSimpleType.type, (XmlOptions)null);
      }

      public static LocalSimpleType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse(sr, LocalSimpleType.type, options);
      }

      public static LocalSimpleType parse(Node node) throws XmlException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse((Node)node, LocalSimpleType.type, (XmlOptions)null);
      }

      public static LocalSimpleType parse(Node node, XmlOptions options) throws XmlException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse(node, LocalSimpleType.type, options);
      }

      /** @deprecated */
      public static LocalSimpleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, LocalSimpleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LocalSimpleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LocalSimpleType)XmlBeans.getContextTypeLoader().parse(xis, LocalSimpleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalSimpleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalSimpleType.type, options);
      }

      private Factory() {
      }
   }
}
