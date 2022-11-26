package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface DeprecatedJdoMetaDataFactoryType extends MetaDataFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeprecatedJdoMetaDataFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("deprecatedjdometadatafactorytype27ddtype");

   boolean getUseSchemaValidation();

   XmlBoolean xgetUseSchemaValidation();

   boolean isSetUseSchemaValidation();

   void setUseSchemaValidation(boolean var1);

   void xsetUseSchemaValidation(XmlBoolean var1);

   void unsetUseSchemaValidation();

   String getUrls();

   XmlString xgetUrls();

   boolean isNilUrls();

   boolean isSetUrls();

   void setUrls(String var1);

   void xsetUrls(XmlString var1);

   void setNilUrls();

   void unsetUrls();

   String getFiles();

   XmlString xgetFiles();

   boolean isNilFiles();

   boolean isSetFiles();

   void setFiles(String var1);

   void xsetFiles(XmlString var1);

   void setNilFiles();

   void unsetFiles();

   String getClasspathScan();

   XmlString xgetClasspathScan();

   boolean isNilClasspathScan();

   boolean isSetClasspathScan();

   void setClasspathScan(String var1);

   void xsetClasspathScan(XmlString var1);

   void setNilClasspathScan();

   void unsetClasspathScan();

   String getTypes();

   XmlString xgetTypes();

   boolean isNilTypes();

   boolean isSetTypes();

   void setTypes(String var1);

   void xsetTypes(XmlString var1);

   void setNilTypes();

   void unsetTypes();

   int getStoreMode();

   XmlInt xgetStoreMode();

   boolean isSetStoreMode();

   void setStoreMode(int var1);

   void xsetStoreMode(XmlInt var1);

   void unsetStoreMode();

   boolean getStrict();

   XmlBoolean xgetStrict();

   boolean isSetStrict();

   void setStrict(boolean var1);

   void xsetStrict(XmlBoolean var1);

   void unsetStrict();

   String getResources();

   XmlString xgetResources();

   boolean isNilResources();

   boolean isSetResources();

   void setResources(String var1);

   void xsetResources(XmlString var1);

   void setNilResources();

   void unsetResources();

   boolean getScanTopDown();

   XmlBoolean xgetScanTopDown();

   boolean isSetScanTopDown();

   void setScanTopDown(boolean var1);

   void xsetScanTopDown(XmlBoolean var1);

   void unsetScanTopDown();

   public static final class Factory {
      public static DeprecatedJdoMetaDataFactoryType newInstance() {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().newInstance(DeprecatedJdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMetaDataFactoryType newInstance(XmlOptions options) {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().newInstance(DeprecatedJdoMetaDataFactoryType.type, options);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(String xmlAsString) throws XmlException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeprecatedJdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeprecatedJdoMetaDataFactoryType.type, options);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(File file) throws XmlException, IOException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(file, DeprecatedJdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(file, DeprecatedJdoMetaDataFactoryType.type, options);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(URL u) throws XmlException, IOException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(u, DeprecatedJdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(u, DeprecatedJdoMetaDataFactoryType.type, options);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(InputStream is) throws XmlException, IOException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(is, DeprecatedJdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(is, DeprecatedJdoMetaDataFactoryType.type, options);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(Reader r) throws XmlException, IOException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(r, DeprecatedJdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(r, DeprecatedJdoMetaDataFactoryType.type, options);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(sr, DeprecatedJdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(sr, DeprecatedJdoMetaDataFactoryType.type, options);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(Node node) throws XmlException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(node, DeprecatedJdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMetaDataFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(node, DeprecatedJdoMetaDataFactoryType.type, options);
      }

      /** @deprecated */
      public static DeprecatedJdoMetaDataFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xis, DeprecatedJdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeprecatedJdoMetaDataFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeprecatedJdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xis, DeprecatedJdoMetaDataFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeprecatedJdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeprecatedJdoMetaDataFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
