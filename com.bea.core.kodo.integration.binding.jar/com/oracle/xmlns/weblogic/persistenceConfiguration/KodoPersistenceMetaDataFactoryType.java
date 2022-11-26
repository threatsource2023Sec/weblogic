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

public interface KodoPersistenceMetaDataFactoryType extends MetaDataFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KodoPersistenceMetaDataFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("kodopersistencemetadatafactorytype3b77type");

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

   String getDefaultAccessType();

   XmlString xgetDefaultAccessType();

   boolean isNilDefaultAccessType();

   boolean isSetDefaultAccessType();

   void setDefaultAccessType(String var1);

   void xsetDefaultAccessType(XmlString var1);

   void setNilDefaultAccessType();

   void unsetDefaultAccessType();

   boolean getFieldOverride();

   XmlBoolean xgetFieldOverride();

   boolean isSetFieldOverride();

   void setFieldOverride(boolean var1);

   void xsetFieldOverride(XmlBoolean var1);

   void unsetFieldOverride();

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

   public static final class Factory {
      public static KodoPersistenceMetaDataFactoryType newInstance() {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().newInstance(KodoPersistenceMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static KodoPersistenceMetaDataFactoryType newInstance(XmlOptions options) {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().newInstance(KodoPersistenceMetaDataFactoryType.type, options);
      }

      public static KodoPersistenceMetaDataFactoryType parse(String xmlAsString) throws XmlException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoPersistenceMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static KodoPersistenceMetaDataFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoPersistenceMetaDataFactoryType.type, options);
      }

      public static KodoPersistenceMetaDataFactoryType parse(File file) throws XmlException, IOException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(file, KodoPersistenceMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static KodoPersistenceMetaDataFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(file, KodoPersistenceMetaDataFactoryType.type, options);
      }

      public static KodoPersistenceMetaDataFactoryType parse(URL u) throws XmlException, IOException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(u, KodoPersistenceMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static KodoPersistenceMetaDataFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(u, KodoPersistenceMetaDataFactoryType.type, options);
      }

      public static KodoPersistenceMetaDataFactoryType parse(InputStream is) throws XmlException, IOException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(is, KodoPersistenceMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static KodoPersistenceMetaDataFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(is, KodoPersistenceMetaDataFactoryType.type, options);
      }

      public static KodoPersistenceMetaDataFactoryType parse(Reader r) throws XmlException, IOException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(r, KodoPersistenceMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static KodoPersistenceMetaDataFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(r, KodoPersistenceMetaDataFactoryType.type, options);
      }

      public static KodoPersistenceMetaDataFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(sr, KodoPersistenceMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static KodoPersistenceMetaDataFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(sr, KodoPersistenceMetaDataFactoryType.type, options);
      }

      public static KodoPersistenceMetaDataFactoryType parse(Node node) throws XmlException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(node, KodoPersistenceMetaDataFactoryType.type, (XmlOptions)null);
      }

      public static KodoPersistenceMetaDataFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(node, KodoPersistenceMetaDataFactoryType.type, options);
      }

      /** @deprecated */
      public static KodoPersistenceMetaDataFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xis, KodoPersistenceMetaDataFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KodoPersistenceMetaDataFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KodoPersistenceMetaDataFactoryType)XmlBeans.getContextTypeLoader().parse(xis, KodoPersistenceMetaDataFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoPersistenceMetaDataFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoPersistenceMetaDataFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
