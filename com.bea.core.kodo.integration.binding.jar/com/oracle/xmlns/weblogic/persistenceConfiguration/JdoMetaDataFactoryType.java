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

public interface JdoMetaDataFactoryType extends MetaDataFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JdoMetaDataFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("jdometadatafactorytypedb75type");

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

   boolean getConstraintNames();

   XmlBoolean xgetConstraintNames();

   boolean isSetConstraintNames();

   void setConstraintNames(boolean var1);

   void xsetConstraintNames(XmlBoolean var1);

   void unsetConstraintNames();

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
      public static JdoMetaDataFactoryType newInstance() {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().newInstance(JdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static JdoMetaDataFactoryType newInstance(XmlOptions options) {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().newInstance(JdoMetaDataFactoryType.type, options);
      }

      public static JdoMetaDataFactoryType parse(String xmlAsString) throws XmlException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static JdoMetaDataFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JdoMetaDataFactoryType.type, options);
      }

      public static JdoMetaDataFactoryType parse(File file) throws XmlException, IOException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(file, JdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static JdoMetaDataFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(file, JdoMetaDataFactoryType.type, options);
      }

      public static JdoMetaDataFactoryType parse(URL u) throws XmlException, IOException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(u, JdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static JdoMetaDataFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(u, JdoMetaDataFactoryType.type, options);
      }

      public static JdoMetaDataFactoryType parse(InputStream is) throws XmlException, IOException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(is, JdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static JdoMetaDataFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(is, JdoMetaDataFactoryType.type, options);
      }

      public static JdoMetaDataFactoryType parse(Reader r) throws XmlException, IOException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(r, JdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static JdoMetaDataFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(r, JdoMetaDataFactoryType.type, options);
      }

      public static JdoMetaDataFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(sr, JdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static JdoMetaDataFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(sr, JdoMetaDataFactoryType.type, options);
      }

      public static JdoMetaDataFactoryType parse(Node node) throws XmlException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(node, JdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static JdoMetaDataFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(node, JdoMetaDataFactoryType.type, options);
      }

      /** @deprecated */
      public static JdoMetaDataFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xis, JdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JdoMetaDataFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JdoMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xis, JdoMetaDataFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdoMetaDataFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JdoMetaDataFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
