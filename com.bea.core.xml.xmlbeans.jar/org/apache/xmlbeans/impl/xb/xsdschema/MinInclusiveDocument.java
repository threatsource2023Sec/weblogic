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

public interface MinInclusiveDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MinInclusiveDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("mininclusive8b49doctype");

   Facet getMinInclusive();

   void setMinInclusive(Facet var1);

   Facet addNewMinInclusive();

   public static final class Factory {
      public static MinInclusiveDocument newInstance() {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().newInstance(MinInclusiveDocument.type, (XmlOptions)null);
      }

      public static MinInclusiveDocument newInstance(XmlOptions options) {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().newInstance(MinInclusiveDocument.type, options);
      }

      public static MinInclusiveDocument parse(String xmlAsString) throws XmlException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, MinInclusiveDocument.type, (XmlOptions)null);
      }

      public static MinInclusiveDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MinInclusiveDocument.type, options);
      }

      public static MinInclusiveDocument parse(File file) throws XmlException, IOException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse((File)file, MinInclusiveDocument.type, (XmlOptions)null);
      }

      public static MinInclusiveDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse(file, MinInclusiveDocument.type, options);
      }

      public static MinInclusiveDocument parse(URL u) throws XmlException, IOException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse((URL)u, MinInclusiveDocument.type, (XmlOptions)null);
      }

      public static MinInclusiveDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse(u, MinInclusiveDocument.type, options);
      }

      public static MinInclusiveDocument parse(InputStream is) throws XmlException, IOException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, MinInclusiveDocument.type, (XmlOptions)null);
      }

      public static MinInclusiveDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse(is, MinInclusiveDocument.type, options);
      }

      public static MinInclusiveDocument parse(Reader r) throws XmlException, IOException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, MinInclusiveDocument.type, (XmlOptions)null);
      }

      public static MinInclusiveDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse(r, MinInclusiveDocument.type, options);
      }

      public static MinInclusiveDocument parse(XMLStreamReader sr) throws XmlException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, MinInclusiveDocument.type, (XmlOptions)null);
      }

      public static MinInclusiveDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse(sr, MinInclusiveDocument.type, options);
      }

      public static MinInclusiveDocument parse(Node node) throws XmlException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse((Node)node, MinInclusiveDocument.type, (XmlOptions)null);
      }

      public static MinInclusiveDocument parse(Node node, XmlOptions options) throws XmlException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse(node, MinInclusiveDocument.type, options);
      }

      /** @deprecated */
      public static MinInclusiveDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, MinInclusiveDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MinInclusiveDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MinInclusiveDocument)XmlBeans.getContextTypeLoader().parse(xis, MinInclusiveDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MinInclusiveDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MinInclusiveDocument.type, options);
      }

      private Factory() {
      }
   }
}
