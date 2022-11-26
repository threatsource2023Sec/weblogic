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

public interface TableDeprecatedJdoMappingFactoryType extends MappingFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TableDeprecatedJdoMappingFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("tabledeprecatedjdomappingfactorytyped942type");

   String getTableName();

   XmlString xgetTableName();

   boolean isNilTableName();

   boolean isSetTableName();

   void setTableName(String var1);

   void xsetTableName(XmlString var1);

   void setNilTableName();

   void unsetTableName();

   String getUrls();

   XmlString xgetUrls();

   boolean isNilUrls();

   boolean isSetUrls();

   void setUrls(String var1);

   void xsetUrls(XmlString var1);

   void setNilUrls();

   void unsetUrls();

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

   String getMappingColumn();

   XmlString xgetMappingColumn();

   boolean isNilMappingColumn();

   boolean isSetMappingColumn();

   void setMappingColumn(String var1);

   void xsetMappingColumn(XmlString var1);

   void setNilMappingColumn();

   void unsetMappingColumn();

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

   String getNameColumn();

   XmlString xgetNameColumn();

   boolean isNilNameColumn();

   boolean isSetNameColumn();

   void setNameColumn(String var1);

   void xsetNameColumn(XmlString var1);

   void setNilNameColumn();

   void unsetNameColumn();

   boolean getUseSchemaValidation();

   XmlBoolean xgetUseSchemaValidation();

   boolean isSetUseSchemaValidation();

   void setUseSchemaValidation(boolean var1);

   void xsetUseSchemaValidation(XmlBoolean var1);

   void unsetUseSchemaValidation();

   boolean getSingleRow();

   XmlBoolean xgetSingleRow();

   boolean isSetSingleRow();

   void setSingleRow(boolean var1);

   void xsetSingleRow(XmlBoolean var1);

   void unsetSingleRow();

   String getFiles();

   XmlString xgetFiles();

   boolean isNilFiles();

   boolean isSetFiles();

   void setFiles(String var1);

   void xsetFiles(XmlString var1);

   void setNilFiles();

   void unsetFiles();

   boolean getScanTopDown();

   XmlBoolean xgetScanTopDown();

   boolean isSetScanTopDown();

   void setScanTopDown(boolean var1);

   void xsetScanTopDown(XmlBoolean var1);

   void unsetScanTopDown();

   String getResources();

   XmlString xgetResources();

   boolean isNilResources();

   boolean isSetResources();

   void setResources(String var1);

   void xsetResources(XmlString var1);

   void setNilResources();

   void unsetResources();

   public static final class Factory {
      public static TableDeprecatedJdoMappingFactoryType newInstance() {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().newInstance(TableDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableDeprecatedJdoMappingFactoryType newInstance(XmlOptions options) {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().newInstance(TableDeprecatedJdoMappingFactoryType.type, options);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(String xmlAsString) throws XmlException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableDeprecatedJdoMappingFactoryType.type, options);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(File file) throws XmlException, IOException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(file, TableDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(file, TableDeprecatedJdoMappingFactoryType.type, options);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(URL u) throws XmlException, IOException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(u, TableDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(u, TableDeprecatedJdoMappingFactoryType.type, options);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(InputStream is) throws XmlException, IOException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(is, TableDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(is, TableDeprecatedJdoMappingFactoryType.type, options);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(Reader r) throws XmlException, IOException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(r, TableDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(r, TableDeprecatedJdoMappingFactoryType.type, options);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(sr, TableDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(sr, TableDeprecatedJdoMappingFactoryType.type, options);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(Node node) throws XmlException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(node, TableDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableDeprecatedJdoMappingFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(node, TableDeprecatedJdoMappingFactoryType.type, options);
      }

      /** @deprecated */
      public static TableDeprecatedJdoMappingFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xis, TableDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TableDeprecatedJdoMappingFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TableDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xis, TableDeprecatedJdoMappingFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableDeprecatedJdoMappingFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
