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

public interface MinLengthDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MinLengthDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("minlengthe7fddoctype");

   NumFacet getMinLength();

   void setMinLength(NumFacet var1);

   NumFacet addNewMinLength();

   public static final class Factory {
      public static MinLengthDocument newInstance() {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().newInstance(MinLengthDocument.type, (XmlOptions)null);
      }

      public static MinLengthDocument newInstance(XmlOptions options) {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().newInstance(MinLengthDocument.type, options);
      }

      public static MinLengthDocument parse(String xmlAsString) throws XmlException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, MinLengthDocument.type, (XmlOptions)null);
      }

      public static MinLengthDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MinLengthDocument.type, options);
      }

      public static MinLengthDocument parse(File file) throws XmlException, IOException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse((File)file, MinLengthDocument.type, (XmlOptions)null);
      }

      public static MinLengthDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse(file, MinLengthDocument.type, options);
      }

      public static MinLengthDocument parse(URL u) throws XmlException, IOException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse((URL)u, MinLengthDocument.type, (XmlOptions)null);
      }

      public static MinLengthDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse(u, MinLengthDocument.type, options);
      }

      public static MinLengthDocument parse(InputStream is) throws XmlException, IOException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, MinLengthDocument.type, (XmlOptions)null);
      }

      public static MinLengthDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse(is, MinLengthDocument.type, options);
      }

      public static MinLengthDocument parse(Reader r) throws XmlException, IOException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, MinLengthDocument.type, (XmlOptions)null);
      }

      public static MinLengthDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse(r, MinLengthDocument.type, options);
      }

      public static MinLengthDocument parse(XMLStreamReader sr) throws XmlException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, MinLengthDocument.type, (XmlOptions)null);
      }

      public static MinLengthDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse(sr, MinLengthDocument.type, options);
      }

      public static MinLengthDocument parse(Node node) throws XmlException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse((Node)node, MinLengthDocument.type, (XmlOptions)null);
      }

      public static MinLengthDocument parse(Node node, XmlOptions options) throws XmlException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse(node, MinLengthDocument.type, options);
      }

      /** @deprecated */
      public static MinLengthDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, MinLengthDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MinLengthDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MinLengthDocument)XmlBeans.getContextTypeLoader().parse(xis, MinLengthDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MinLengthDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MinLengthDocument.type, options);
      }

      private Factory() {
      }
   }
}
