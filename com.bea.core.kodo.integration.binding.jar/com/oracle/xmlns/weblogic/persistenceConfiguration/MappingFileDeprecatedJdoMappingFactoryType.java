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

public interface MappingFileDeprecatedJdoMappingFactoryType extends MappingFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MappingFileDeprecatedJdoMappingFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("mappingfiledeprecatedjdomappingfactorytype9201type");

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

   boolean getSingleFile();

   XmlBoolean xgetSingleFile();

   boolean isSetSingleFile();

   void setSingleFile(boolean var1);

   void xsetSingleFile(XmlBoolean var1);

   void unsetSingleFile();

   String getTypes();

   XmlString xgetTypes();

   boolean isNilTypes();

   boolean isSetTypes();

   void setTypes(String var1);

   void xsetTypes(XmlString var1);

   void setNilTypes();

   void unsetTypes();

   String getFileName();

   XmlString xgetFileName();

   boolean isNilFileName();

   boolean isSetFileName();

   void setFileName(String var1);

   void xsetFileName(XmlString var1);

   void setNilFileName();

   void unsetFileName();

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
      public static MappingFileDeprecatedJdoMappingFactoryType newInstance() {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().newInstance(MappingFileDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType newInstance(XmlOptions options) {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().newInstance(MappingFileDeprecatedJdoMappingFactoryType.type, options);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(String xmlAsString) throws XmlException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MappingFileDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MappingFileDeprecatedJdoMappingFactoryType.type, options);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(File file) throws XmlException, IOException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(file, MappingFileDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(file, MappingFileDeprecatedJdoMappingFactoryType.type, options);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(URL u) throws XmlException, IOException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(u, MappingFileDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(u, MappingFileDeprecatedJdoMappingFactoryType.type, options);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(InputStream is) throws XmlException, IOException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(is, MappingFileDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(is, MappingFileDeprecatedJdoMappingFactoryType.type, options);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(Reader r) throws XmlException, IOException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(r, MappingFileDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(r, MappingFileDeprecatedJdoMappingFactoryType.type, options);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(sr, MappingFileDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(sr, MappingFileDeprecatedJdoMappingFactoryType.type, options);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(Node node) throws XmlException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(node, MappingFileDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      public static MappingFileDeprecatedJdoMappingFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(node, MappingFileDeprecatedJdoMappingFactoryType.type, options);
      }

      /** @deprecated */
      public static MappingFileDeprecatedJdoMappingFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xis, MappingFileDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MappingFileDeprecatedJdoMappingFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MappingFileDeprecatedJdoMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xis, MappingFileDeprecatedJdoMappingFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MappingFileDeprecatedJdoMappingFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MappingFileDeprecatedJdoMappingFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
