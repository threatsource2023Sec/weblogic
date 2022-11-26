package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnyURI;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlLanguage;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface DocumentationDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DocumentationDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("documentation6cdbdoctype");

   Documentation getDocumentation();

   void setDocumentation(Documentation var1);

   Documentation addNewDocumentation();

   public static final class Factory {
      public static DocumentationDocument newInstance() {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().newInstance(DocumentationDocument.type, (XmlOptions)null);
      }

      public static DocumentationDocument newInstance(XmlOptions options) {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().newInstance(DocumentationDocument.type, options);
      }

      public static DocumentationDocument parse(String xmlAsString) throws XmlException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, DocumentationDocument.type, (XmlOptions)null);
      }

      public static DocumentationDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DocumentationDocument.type, options);
      }

      public static DocumentationDocument parse(File file) throws XmlException, IOException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse((File)file, DocumentationDocument.type, (XmlOptions)null);
      }

      public static DocumentationDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse(file, DocumentationDocument.type, options);
      }

      public static DocumentationDocument parse(URL u) throws XmlException, IOException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse((URL)u, DocumentationDocument.type, (XmlOptions)null);
      }

      public static DocumentationDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse(u, DocumentationDocument.type, options);
      }

      public static DocumentationDocument parse(InputStream is) throws XmlException, IOException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, DocumentationDocument.type, (XmlOptions)null);
      }

      public static DocumentationDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse(is, DocumentationDocument.type, options);
      }

      public static DocumentationDocument parse(Reader r) throws XmlException, IOException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, DocumentationDocument.type, (XmlOptions)null);
      }

      public static DocumentationDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse(r, DocumentationDocument.type, options);
      }

      public static DocumentationDocument parse(XMLStreamReader sr) throws XmlException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, DocumentationDocument.type, (XmlOptions)null);
      }

      public static DocumentationDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse(sr, DocumentationDocument.type, options);
      }

      public static DocumentationDocument parse(Node node) throws XmlException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse((Node)node, DocumentationDocument.type, (XmlOptions)null);
      }

      public static DocumentationDocument parse(Node node, XmlOptions options) throws XmlException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse(node, DocumentationDocument.type, options);
      }

      /** @deprecated */
      public static DocumentationDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, DocumentationDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DocumentationDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DocumentationDocument)XmlBeans.getContextTypeLoader().parse(xis, DocumentationDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DocumentationDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DocumentationDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Documentation extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Documentation.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("documentationa475elemtype");

      String getSource();

      XmlAnyURI xgetSource();

      boolean isSetSource();

      void setSource(String var1);

      void xsetSource(XmlAnyURI var1);

      void unsetSource();

      String getLang();

      XmlLanguage xgetLang();

      boolean isSetLang();

      void setLang(String var1);

      void xsetLang(XmlLanguage var1);

      void unsetLang();

      public static final class Factory {
         public static Documentation newInstance() {
            return (Documentation)XmlBeans.getContextTypeLoader().newInstance(DocumentationDocument.Documentation.type, (XmlOptions)null);
         }

         public static Documentation newInstance(XmlOptions options) {
            return (Documentation)XmlBeans.getContextTypeLoader().newInstance(DocumentationDocument.Documentation.type, options);
         }

         private Factory() {
         }
      }
   }
}
