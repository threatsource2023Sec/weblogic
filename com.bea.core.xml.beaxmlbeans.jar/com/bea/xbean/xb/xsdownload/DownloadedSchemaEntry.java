package com.bea.xbean.xb.xsdownload;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnyURI;
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

public interface DownloadedSchemaEntry extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DownloadedSchemaEntry.class.getClassLoader(), "schemacom_bea_xml.system.sXMLTOOLS").resolveHandle("downloadedschemaentry1c75type");

   String getFilename();

   XmlToken xgetFilename();

   void setFilename(String var1);

   void xsetFilename(XmlToken var1);

   String getSha1();

   XmlToken xgetSha1();

   void setSha1(String var1);

   void xsetSha1(XmlToken var1);

   String[] getSchemaLocationArray();

   String getSchemaLocationArray(int var1);

   XmlAnyURI[] xgetSchemaLocationArray();

   XmlAnyURI xgetSchemaLocationArray(int var1);

   int sizeOfSchemaLocationArray();

   void setSchemaLocationArray(String[] var1);

   void setSchemaLocationArray(int var1, String var2);

   void xsetSchemaLocationArray(XmlAnyURI[] var1);

   void xsetSchemaLocationArray(int var1, XmlAnyURI var2);

   void insertSchemaLocation(int var1, String var2);

   void addSchemaLocation(String var1);

   XmlAnyURI insertNewSchemaLocation(int var1);

   XmlAnyURI addNewSchemaLocation();

   void removeSchemaLocation(int var1);

   String getNamespace();

   XmlAnyURI xgetNamespace();

   boolean isSetNamespace();

   void setNamespace(String var1);

   void xsetNamespace(XmlAnyURI var1);

   void unsetNamespace();

   public static final class Factory {
      public static DownloadedSchemaEntry newInstance() {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().newInstance(DownloadedSchemaEntry.type, (XmlOptions)null);
      }

      public static DownloadedSchemaEntry newInstance(XmlOptions options) {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().newInstance(DownloadedSchemaEntry.type, options);
      }

      public static DownloadedSchemaEntry parse(String xmlAsString) throws XmlException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, DownloadedSchemaEntry.type, (XmlOptions)null);
      }

      public static DownloadedSchemaEntry parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse(xmlAsString, DownloadedSchemaEntry.type, options);
      }

      public static DownloadedSchemaEntry parse(File file) throws XmlException, IOException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse((File)file, DownloadedSchemaEntry.type, (XmlOptions)null);
      }

      public static DownloadedSchemaEntry parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse(file, DownloadedSchemaEntry.type, options);
      }

      public static DownloadedSchemaEntry parse(URL u) throws XmlException, IOException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse((URL)u, DownloadedSchemaEntry.type, (XmlOptions)null);
      }

      public static DownloadedSchemaEntry parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse(u, DownloadedSchemaEntry.type, options);
      }

      public static DownloadedSchemaEntry parse(InputStream is) throws XmlException, IOException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse((InputStream)is, DownloadedSchemaEntry.type, (XmlOptions)null);
      }

      public static DownloadedSchemaEntry parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse(is, DownloadedSchemaEntry.type, options);
      }

      public static DownloadedSchemaEntry parse(Reader r) throws XmlException, IOException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse((Reader)r, DownloadedSchemaEntry.type, (XmlOptions)null);
      }

      public static DownloadedSchemaEntry parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse(r, DownloadedSchemaEntry.type, options);
      }

      public static DownloadedSchemaEntry parse(XMLStreamReader sr) throws XmlException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, DownloadedSchemaEntry.type, (XmlOptions)null);
      }

      public static DownloadedSchemaEntry parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse(sr, DownloadedSchemaEntry.type, options);
      }

      public static DownloadedSchemaEntry parse(Node node) throws XmlException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse((Node)node, DownloadedSchemaEntry.type, (XmlOptions)null);
      }

      public static DownloadedSchemaEntry parse(Node node, XmlOptions options) throws XmlException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse(node, DownloadedSchemaEntry.type, options);
      }

      /** @deprecated */
      public static DownloadedSchemaEntry parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, DownloadedSchemaEntry.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DownloadedSchemaEntry parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DownloadedSchemaEntry)XmlBeans.getContextTypeLoader().parse(xis, DownloadedSchemaEntry.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DownloadedSchemaEntry.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DownloadedSchemaEntry.type, options);
      }

      private Factory() {
      }
   }
}
