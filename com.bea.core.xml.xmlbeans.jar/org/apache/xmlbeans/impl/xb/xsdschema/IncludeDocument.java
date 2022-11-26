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
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface IncludeDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IncludeDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("includeaf6ddoctype");

   Include getInclude();

   void setInclude(Include var1);

   Include addNewInclude();

   public static final class Factory {
      public static IncludeDocument newInstance() {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().newInstance(IncludeDocument.type, (XmlOptions)null);
      }

      public static IncludeDocument newInstance(XmlOptions options) {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().newInstance(IncludeDocument.type, options);
      }

      public static IncludeDocument parse(String xmlAsString) throws XmlException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, IncludeDocument.type, (XmlOptions)null);
      }

      public static IncludeDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, IncludeDocument.type, options);
      }

      public static IncludeDocument parse(File file) throws XmlException, IOException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse((File)file, IncludeDocument.type, (XmlOptions)null);
      }

      public static IncludeDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse(file, IncludeDocument.type, options);
      }

      public static IncludeDocument parse(URL u) throws XmlException, IOException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse((URL)u, IncludeDocument.type, (XmlOptions)null);
      }

      public static IncludeDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse(u, IncludeDocument.type, options);
      }

      public static IncludeDocument parse(InputStream is) throws XmlException, IOException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, IncludeDocument.type, (XmlOptions)null);
      }

      public static IncludeDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse(is, IncludeDocument.type, options);
      }

      public static IncludeDocument parse(Reader r) throws XmlException, IOException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, IncludeDocument.type, (XmlOptions)null);
      }

      public static IncludeDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse(r, IncludeDocument.type, options);
      }

      public static IncludeDocument parse(XMLStreamReader sr) throws XmlException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, IncludeDocument.type, (XmlOptions)null);
      }

      public static IncludeDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse(sr, IncludeDocument.type, options);
      }

      public static IncludeDocument parse(Node node) throws XmlException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse((Node)node, IncludeDocument.type, (XmlOptions)null);
      }

      public static IncludeDocument parse(Node node, XmlOptions options) throws XmlException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse(node, IncludeDocument.type, options);
      }

      /** @deprecated */
      public static IncludeDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, IncludeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IncludeDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IncludeDocument)XmlBeans.getContextTypeLoader().parse(xis, IncludeDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IncludeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IncludeDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Include extends Annotated {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Include.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("include59d9elemtype");

      String getSchemaLocation();

      XmlAnyURI xgetSchemaLocation();

      void setSchemaLocation(String var1);

      void xsetSchemaLocation(XmlAnyURI var1);

      public static final class Factory {
         public static Include newInstance() {
            return (Include)XmlBeans.getContextTypeLoader().newInstance(IncludeDocument.Include.type, (XmlOptions)null);
         }

         public static Include newInstance(XmlOptions options) {
            return (Include)XmlBeans.getContextTypeLoader().newInstance(IncludeDocument.Include.type, options);
         }

         private Factory() {
         }
      }
   }
}
