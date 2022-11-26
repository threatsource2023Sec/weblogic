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

public interface LocalElement extends Element {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LocalElement.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("localelement2ce2type");

   public static final class Factory {
      public static LocalElement newInstance() {
         return (LocalElement)XmlBeans.getContextTypeLoader().newInstance(LocalElement.type, (XmlOptions)null);
      }

      public static LocalElement newInstance(XmlOptions options) {
         return (LocalElement)XmlBeans.getContextTypeLoader().newInstance(LocalElement.type, options);
      }

      public static LocalElement parse(String xmlAsString) throws XmlException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, LocalElement.type, (XmlOptions)null);
      }

      public static LocalElement parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalElement.type, options);
      }

      public static LocalElement parse(File file) throws XmlException, IOException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse((File)file, LocalElement.type, (XmlOptions)null);
      }

      public static LocalElement parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse(file, LocalElement.type, options);
      }

      public static LocalElement parse(URL u) throws XmlException, IOException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse((URL)u, LocalElement.type, (XmlOptions)null);
      }

      public static LocalElement parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse(u, LocalElement.type, options);
      }

      public static LocalElement parse(InputStream is) throws XmlException, IOException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse((InputStream)is, LocalElement.type, (XmlOptions)null);
      }

      public static LocalElement parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse(is, LocalElement.type, options);
      }

      public static LocalElement parse(Reader r) throws XmlException, IOException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse((Reader)r, LocalElement.type, (XmlOptions)null);
      }

      public static LocalElement parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse(r, LocalElement.type, options);
      }

      public static LocalElement parse(XMLStreamReader sr) throws XmlException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, LocalElement.type, (XmlOptions)null);
      }

      public static LocalElement parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse(sr, LocalElement.type, options);
      }

      public static LocalElement parse(Node node) throws XmlException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse((Node)node, LocalElement.type, (XmlOptions)null);
      }

      public static LocalElement parse(Node node, XmlOptions options) throws XmlException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse(node, LocalElement.type, options);
      }

      /** @deprecated */
      public static LocalElement parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, LocalElement.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LocalElement parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LocalElement)XmlBeans.getContextTypeLoader().parse(xis, LocalElement.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalElement.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalElement.type, options);
      }

      private Factory() {
      }
   }
}
