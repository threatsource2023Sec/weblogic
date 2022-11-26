package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnyURI;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ImportDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ImportDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("import99fedoctype");

   Import getImport();

   void setImport(Import var1);

   Import addNewImport();

   public static final class Factory {
      public static ImportDocument newInstance() {
         return (ImportDocument)XmlBeans.getContextTypeLoader().newInstance(ImportDocument.type, (XmlOptions)null);
      }

      public static ImportDocument newInstance(XmlOptions options) {
         return (ImportDocument)XmlBeans.getContextTypeLoader().newInstance(ImportDocument.type, options);
      }

      public static ImportDocument parse(String xmlAsString) throws XmlException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, ImportDocument.type, (XmlOptions)null);
      }

      public static ImportDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ImportDocument.type, options);
      }

      public static ImportDocument parse(File file) throws XmlException, IOException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse((File)file, ImportDocument.type, (XmlOptions)null);
      }

      public static ImportDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse(file, ImportDocument.type, options);
      }

      public static ImportDocument parse(URL u) throws XmlException, IOException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse((URL)u, ImportDocument.type, (XmlOptions)null);
      }

      public static ImportDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse(u, ImportDocument.type, options);
      }

      public static ImportDocument parse(InputStream is) throws XmlException, IOException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, ImportDocument.type, (XmlOptions)null);
      }

      public static ImportDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse(is, ImportDocument.type, options);
      }

      public static ImportDocument parse(Reader r) throws XmlException, IOException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, ImportDocument.type, (XmlOptions)null);
      }

      public static ImportDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse(r, ImportDocument.type, options);
      }

      public static ImportDocument parse(XMLStreamReader sr) throws XmlException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, ImportDocument.type, (XmlOptions)null);
      }

      public static ImportDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse(sr, ImportDocument.type, options);
      }

      public static ImportDocument parse(Node node) throws XmlException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse((Node)node, ImportDocument.type, (XmlOptions)null);
      }

      public static ImportDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse(node, ImportDocument.type, options);
      }

      /** @deprecated */
      public static ImportDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, ImportDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ImportDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ImportDocument)XmlBeans.getContextTypeLoader().parse(xis, ImportDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ImportDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ImportDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Import extends Annotated {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Import.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("importe2ffelemtype");

      String getNamespace();

      XmlAnyURI xgetNamespace();

      boolean isSetNamespace();

      void setNamespace(String var1);

      void xsetNamespace(XmlAnyURI var1);

      void unsetNamespace();

      String getSchemaLocation();

      XmlAnyURI xgetSchemaLocation();

      boolean isSetSchemaLocation();

      void setSchemaLocation(String var1);

      void xsetSchemaLocation(XmlAnyURI var1);

      void unsetSchemaLocation();

      public static final class Factory {
         public static Import newInstance() {
            return (Import)XmlBeans.getContextTypeLoader().newInstance(ImportDocument.Import.type, (XmlOptions)null);
         }

         public static Import newInstance(XmlOptions options) {
            return (Import)XmlBeans.getContextTypeLoader().newInstance(ImportDocument.Import.type, options);
         }

         private Factory() {
         }
      }
   }
}
