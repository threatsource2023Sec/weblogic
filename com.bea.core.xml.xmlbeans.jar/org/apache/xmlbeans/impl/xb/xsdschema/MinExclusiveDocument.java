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

public interface MinExclusiveDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MinExclusiveDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("minexclusive64d7doctype");

   Facet getMinExclusive();

   void setMinExclusive(Facet var1);

   Facet addNewMinExclusive();

   public static final class Factory {
      public static MinExclusiveDocument newInstance() {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().newInstance(MinExclusiveDocument.type, (XmlOptions)null);
      }

      public static MinExclusiveDocument newInstance(XmlOptions options) {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().newInstance(MinExclusiveDocument.type, options);
      }

      public static MinExclusiveDocument parse(String xmlAsString) throws XmlException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, MinExclusiveDocument.type, (XmlOptions)null);
      }

      public static MinExclusiveDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MinExclusiveDocument.type, options);
      }

      public static MinExclusiveDocument parse(File file) throws XmlException, IOException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse((File)file, MinExclusiveDocument.type, (XmlOptions)null);
      }

      public static MinExclusiveDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse(file, MinExclusiveDocument.type, options);
      }

      public static MinExclusiveDocument parse(URL u) throws XmlException, IOException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse((URL)u, MinExclusiveDocument.type, (XmlOptions)null);
      }

      public static MinExclusiveDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse(u, MinExclusiveDocument.type, options);
      }

      public static MinExclusiveDocument parse(InputStream is) throws XmlException, IOException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, MinExclusiveDocument.type, (XmlOptions)null);
      }

      public static MinExclusiveDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse(is, MinExclusiveDocument.type, options);
      }

      public static MinExclusiveDocument parse(Reader r) throws XmlException, IOException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, MinExclusiveDocument.type, (XmlOptions)null);
      }

      public static MinExclusiveDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse(r, MinExclusiveDocument.type, options);
      }

      public static MinExclusiveDocument parse(XMLStreamReader sr) throws XmlException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, MinExclusiveDocument.type, (XmlOptions)null);
      }

      public static MinExclusiveDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse(sr, MinExclusiveDocument.type, options);
      }

      public static MinExclusiveDocument parse(Node node) throws XmlException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse((Node)node, MinExclusiveDocument.type, (XmlOptions)null);
      }

      public static MinExclusiveDocument parse(Node node, XmlOptions options) throws XmlException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse(node, MinExclusiveDocument.type, options);
      }

      /** @deprecated */
      public static MinExclusiveDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, MinExclusiveDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MinExclusiveDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MinExclusiveDocument)XmlBeans.getContextTypeLoader().parse(xis, MinExclusiveDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MinExclusiveDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MinExclusiveDocument.type, options);
      }

      private Factory() {
      }
   }
}
