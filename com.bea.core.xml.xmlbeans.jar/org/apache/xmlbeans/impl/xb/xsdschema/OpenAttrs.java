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
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface OpenAttrs extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OpenAttrs.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("openattrs2d4dtype");

   public static final class Factory {
      public static OpenAttrs newInstance() {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().newInstance(OpenAttrs.type, (XmlOptions)null);
      }

      public static OpenAttrs newInstance(XmlOptions options) {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().newInstance(OpenAttrs.type, options);
      }

      public static OpenAttrs parse(String xmlAsString) throws XmlException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, OpenAttrs.type, (XmlOptions)null);
      }

      public static OpenAttrs parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse(xmlAsString, OpenAttrs.type, options);
      }

      public static OpenAttrs parse(File file) throws XmlException, IOException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse((File)file, OpenAttrs.type, (XmlOptions)null);
      }

      public static OpenAttrs parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse(file, OpenAttrs.type, options);
      }

      public static OpenAttrs parse(URL u) throws XmlException, IOException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse((URL)u, OpenAttrs.type, (XmlOptions)null);
      }

      public static OpenAttrs parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse(u, OpenAttrs.type, options);
      }

      public static OpenAttrs parse(InputStream is) throws XmlException, IOException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse((InputStream)is, OpenAttrs.type, (XmlOptions)null);
      }

      public static OpenAttrs parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse(is, OpenAttrs.type, options);
      }

      public static OpenAttrs parse(Reader r) throws XmlException, IOException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse((Reader)r, OpenAttrs.type, (XmlOptions)null);
      }

      public static OpenAttrs parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse(r, OpenAttrs.type, options);
      }

      public static OpenAttrs parse(XMLStreamReader sr) throws XmlException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, OpenAttrs.type, (XmlOptions)null);
      }

      public static OpenAttrs parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse(sr, OpenAttrs.type, options);
      }

      public static OpenAttrs parse(Node node) throws XmlException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse((Node)node, OpenAttrs.type, (XmlOptions)null);
      }

      public static OpenAttrs parse(Node node, XmlOptions options) throws XmlException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse(node, OpenAttrs.type, options);
      }

      /** @deprecated */
      public static OpenAttrs parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, OpenAttrs.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OpenAttrs parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OpenAttrs)XmlBeans.getContextTypeLoader().parse(xis, OpenAttrs.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OpenAttrs.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OpenAttrs.type, options);
      }

      private Factory() {
      }
   }
}
