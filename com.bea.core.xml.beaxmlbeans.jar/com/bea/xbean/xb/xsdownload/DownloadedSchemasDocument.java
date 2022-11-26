package com.bea.xbean.xb.xsdownload;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface DownloadedSchemasDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DownloadedSchemasDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLTOOLS").resolveHandle("downloadedschemas2dd7doctype");

   DownloadedSchemas getDownloadedSchemas();

   void setDownloadedSchemas(DownloadedSchemas var1);

   DownloadedSchemas addNewDownloadedSchemas();

   public static final class Factory {
      public static DownloadedSchemasDocument newInstance() {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().newInstance(DownloadedSchemasDocument.type, (XmlOptions)null);
      }

      public static DownloadedSchemasDocument newInstance(XmlOptions options) {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().newInstance(DownloadedSchemasDocument.type, options);
      }

      public static DownloadedSchemasDocument parse(String xmlAsString) throws XmlException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, DownloadedSchemasDocument.type, (XmlOptions)null);
      }

      public static DownloadedSchemasDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DownloadedSchemasDocument.type, options);
      }

      public static DownloadedSchemasDocument parse(File file) throws XmlException, IOException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse((File)file, DownloadedSchemasDocument.type, (XmlOptions)null);
      }

      public static DownloadedSchemasDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse(file, DownloadedSchemasDocument.type, options);
      }

      public static DownloadedSchemasDocument parse(URL u) throws XmlException, IOException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse((URL)u, DownloadedSchemasDocument.type, (XmlOptions)null);
      }

      public static DownloadedSchemasDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse(u, DownloadedSchemasDocument.type, options);
      }

      public static DownloadedSchemasDocument parse(InputStream is) throws XmlException, IOException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, DownloadedSchemasDocument.type, (XmlOptions)null);
      }

      public static DownloadedSchemasDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse(is, DownloadedSchemasDocument.type, options);
      }

      public static DownloadedSchemasDocument parse(Reader r) throws XmlException, IOException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, DownloadedSchemasDocument.type, (XmlOptions)null);
      }

      public static DownloadedSchemasDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse(r, DownloadedSchemasDocument.type, options);
      }

      public static DownloadedSchemasDocument parse(XMLStreamReader sr) throws XmlException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, DownloadedSchemasDocument.type, (XmlOptions)null);
      }

      public static DownloadedSchemasDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse(sr, DownloadedSchemasDocument.type, options);
      }

      public static DownloadedSchemasDocument parse(Node node) throws XmlException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse((Node)node, DownloadedSchemasDocument.type, (XmlOptions)null);
      }

      public static DownloadedSchemasDocument parse(Node node, XmlOptions options) throws XmlException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse(node, DownloadedSchemasDocument.type, options);
      }

      /** @deprecated */
      public static DownloadedSchemasDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, DownloadedSchemasDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DownloadedSchemasDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DownloadedSchemasDocument)XmlBeans.getContextTypeLoader().parse(xis, DownloadedSchemasDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DownloadedSchemasDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DownloadedSchemasDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface DownloadedSchemas extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DownloadedSchemas.class.getClassLoader(), "schemacom_bea_xml.system.sXMLTOOLS").resolveHandle("downloadedschemasb3efelemtype");

      DownloadedSchemaEntry[] getEntryArray();

      DownloadedSchemaEntry getEntryArray(int var1);

      int sizeOfEntryArray();

      void setEntryArray(DownloadedSchemaEntry[] var1);

      void setEntryArray(int var1, DownloadedSchemaEntry var2);

      DownloadedSchemaEntry insertNewEntry(int var1);

      DownloadedSchemaEntry addNewEntry();

      void removeEntry(int var1);

      String getDefaultDirectory();

      XmlToken xgetDefaultDirectory();

      boolean isSetDefaultDirectory();

      void setDefaultDirectory(String var1);

      void xsetDefaultDirectory(XmlToken var1);

      void unsetDefaultDirectory();

      public static final class Factory {
         public static DownloadedSchemas newInstance() {
            return (DownloadedSchemas)XmlBeans.getContextTypeLoader().newInstance(DownloadedSchemasDocument.DownloadedSchemas.type, (XmlOptions)null);
         }

         public static DownloadedSchemas newInstance(XmlOptions options) {
            return (DownloadedSchemas)XmlBeans.getContextTypeLoader().newInstance(DownloadedSchemasDocument.DownloadedSchemas.type, options);
         }

         private Factory() {
         }
      }
   }
}
