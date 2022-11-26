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

public interface MaxExclusiveDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MaxExclusiveDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("maxexclusive6d69doctype");

   Facet getMaxExclusive();

   void setMaxExclusive(Facet var1);

   Facet addNewMaxExclusive();

   public static final class Factory {
      public static MaxExclusiveDocument newInstance() {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().newInstance(MaxExclusiveDocument.type, (XmlOptions)null);
      }

      public static MaxExclusiveDocument newInstance(XmlOptions options) {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().newInstance(MaxExclusiveDocument.type, options);
      }

      public static MaxExclusiveDocument parse(String xmlAsString) throws XmlException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, MaxExclusiveDocument.type, (XmlOptions)null);
      }

      public static MaxExclusiveDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MaxExclusiveDocument.type, options);
      }

      public static MaxExclusiveDocument parse(File file) throws XmlException, IOException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse((File)file, MaxExclusiveDocument.type, (XmlOptions)null);
      }

      public static MaxExclusiveDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse(file, MaxExclusiveDocument.type, options);
      }

      public static MaxExclusiveDocument parse(URL u) throws XmlException, IOException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse((URL)u, MaxExclusiveDocument.type, (XmlOptions)null);
      }

      public static MaxExclusiveDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse(u, MaxExclusiveDocument.type, options);
      }

      public static MaxExclusiveDocument parse(InputStream is) throws XmlException, IOException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, MaxExclusiveDocument.type, (XmlOptions)null);
      }

      public static MaxExclusiveDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse(is, MaxExclusiveDocument.type, options);
      }

      public static MaxExclusiveDocument parse(Reader r) throws XmlException, IOException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, MaxExclusiveDocument.type, (XmlOptions)null);
      }

      public static MaxExclusiveDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse(r, MaxExclusiveDocument.type, options);
      }

      public static MaxExclusiveDocument parse(XMLStreamReader sr) throws XmlException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, MaxExclusiveDocument.type, (XmlOptions)null);
      }

      public static MaxExclusiveDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse(sr, MaxExclusiveDocument.type, options);
      }

      public static MaxExclusiveDocument parse(Node node) throws XmlException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse((Node)node, MaxExclusiveDocument.type, (XmlOptions)null);
      }

      public static MaxExclusiveDocument parse(Node node, XmlOptions options) throws XmlException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse(node, MaxExclusiveDocument.type, options);
      }

      /** @deprecated */
      public static MaxExclusiveDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, MaxExclusiveDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MaxExclusiveDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MaxExclusiveDocument)XmlBeans.getContextTypeLoader().parse(xis, MaxExclusiveDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MaxExclusiveDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MaxExclusiveDocument.type, options);
      }

      private Factory() {
      }
   }
}
