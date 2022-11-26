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

public interface UniqueDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UniqueDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("unique3752doctype");

   Keybase getUnique();

   void setUnique(Keybase var1);

   Keybase addNewUnique();

   public static final class Factory {
      public static UniqueDocument newInstance() {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().newInstance(UniqueDocument.type, (XmlOptions)null);
      }

      public static UniqueDocument newInstance(XmlOptions options) {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().newInstance(UniqueDocument.type, options);
      }

      public static UniqueDocument parse(String xmlAsString) throws XmlException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, UniqueDocument.type, (XmlOptions)null);
      }

      public static UniqueDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, UniqueDocument.type, options);
      }

      public static UniqueDocument parse(File file) throws XmlException, IOException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse((File)file, UniqueDocument.type, (XmlOptions)null);
      }

      public static UniqueDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse(file, UniqueDocument.type, options);
      }

      public static UniqueDocument parse(URL u) throws XmlException, IOException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse((URL)u, UniqueDocument.type, (XmlOptions)null);
      }

      public static UniqueDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse(u, UniqueDocument.type, options);
      }

      public static UniqueDocument parse(InputStream is) throws XmlException, IOException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, UniqueDocument.type, (XmlOptions)null);
      }

      public static UniqueDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse(is, UniqueDocument.type, options);
      }

      public static UniqueDocument parse(Reader r) throws XmlException, IOException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, UniqueDocument.type, (XmlOptions)null);
      }

      public static UniqueDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse(r, UniqueDocument.type, options);
      }

      public static UniqueDocument parse(XMLStreamReader sr) throws XmlException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, UniqueDocument.type, (XmlOptions)null);
      }

      public static UniqueDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse(sr, UniqueDocument.type, options);
      }

      public static UniqueDocument parse(Node node) throws XmlException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse((Node)node, UniqueDocument.type, (XmlOptions)null);
      }

      public static UniqueDocument parse(Node node, XmlOptions options) throws XmlException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse(node, UniqueDocument.type, options);
      }

      /** @deprecated */
      public static UniqueDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, UniqueDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UniqueDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UniqueDocument)XmlBeans.getContextTypeLoader().parse(xis, UniqueDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UniqueDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UniqueDocument.type, options);
      }

      private Factory() {
      }
   }
}
