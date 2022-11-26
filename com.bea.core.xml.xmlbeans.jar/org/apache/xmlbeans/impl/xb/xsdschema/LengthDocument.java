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

public interface LengthDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LengthDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("length7edddoctype");

   NumFacet getLength();

   void setLength(NumFacet var1);

   NumFacet addNewLength();

   public static final class Factory {
      public static LengthDocument newInstance() {
         return (LengthDocument)XmlBeans.getContextTypeLoader().newInstance(LengthDocument.type, (XmlOptions)null);
      }

      public static LengthDocument newInstance(XmlOptions options) {
         return (LengthDocument)XmlBeans.getContextTypeLoader().newInstance(LengthDocument.type, options);
      }

      public static LengthDocument parse(String xmlAsString) throws XmlException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, LengthDocument.type, (XmlOptions)null);
      }

      public static LengthDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, LengthDocument.type, options);
      }

      public static LengthDocument parse(File file) throws XmlException, IOException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse((File)file, LengthDocument.type, (XmlOptions)null);
      }

      public static LengthDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse(file, LengthDocument.type, options);
      }

      public static LengthDocument parse(URL u) throws XmlException, IOException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse((URL)u, LengthDocument.type, (XmlOptions)null);
      }

      public static LengthDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse(u, LengthDocument.type, options);
      }

      public static LengthDocument parse(InputStream is) throws XmlException, IOException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, LengthDocument.type, (XmlOptions)null);
      }

      public static LengthDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse(is, LengthDocument.type, options);
      }

      public static LengthDocument parse(Reader r) throws XmlException, IOException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, LengthDocument.type, (XmlOptions)null);
      }

      public static LengthDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse(r, LengthDocument.type, options);
      }

      public static LengthDocument parse(XMLStreamReader sr) throws XmlException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, LengthDocument.type, (XmlOptions)null);
      }

      public static LengthDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse(sr, LengthDocument.type, options);
      }

      public static LengthDocument parse(Node node) throws XmlException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse((Node)node, LengthDocument.type, (XmlOptions)null);
      }

      public static LengthDocument parse(Node node, XmlOptions options) throws XmlException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse(node, LengthDocument.type, options);
      }

      /** @deprecated */
      public static LengthDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, LengthDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LengthDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LengthDocument)XmlBeans.getContextTypeLoader().parse(xis, LengthDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LengthDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LengthDocument.type, options);
      }

      private Factory() {
      }
   }
}
