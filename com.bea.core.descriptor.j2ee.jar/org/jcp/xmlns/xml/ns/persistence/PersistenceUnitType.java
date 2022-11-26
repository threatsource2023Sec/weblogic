package org.jcp.xmlns.xml.ns.persistence;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface PersistenceUnitType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceUnitType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("persistenceunittype94e8type");

   String getDescription();

   XmlString xgetDescription();

   boolean isSetDescription();

   void setDescription(String var1);

   void xsetDescription(XmlString var1);

   void unsetDescription();

   String getProvider();

   XmlString xgetProvider();

   boolean isSetProvider();

   void setProvider(String var1);

   void xsetProvider(XmlString var1);

   void unsetProvider();

   String getJtaDataSource();

   XmlString xgetJtaDataSource();

   boolean isSetJtaDataSource();

   void setJtaDataSource(String var1);

   void xsetJtaDataSource(XmlString var1);

   void unsetJtaDataSource();

   String getNonJtaDataSource();

   XmlString xgetNonJtaDataSource();

   boolean isSetNonJtaDataSource();

   void setNonJtaDataSource(String var1);

   void xsetNonJtaDataSource(XmlString var1);

   void unsetNonJtaDataSource();

   String[] getMappingFileArray();

   String getMappingFileArray(int var1);

   XmlString[] xgetMappingFileArray();

   XmlString xgetMappingFileArray(int var1);

   int sizeOfMappingFileArray();

   void setMappingFileArray(String[] var1);

   void setMappingFileArray(int var1, String var2);

   void xsetMappingFileArray(XmlString[] var1);

   void xsetMappingFileArray(int var1, XmlString var2);

   void insertMappingFile(int var1, String var2);

   void addMappingFile(String var1);

   XmlString insertNewMappingFile(int var1);

   XmlString addNewMappingFile();

   void removeMappingFile(int var1);

   String[] getJarFileArray();

   String getJarFileArray(int var1);

   XmlString[] xgetJarFileArray();

   XmlString xgetJarFileArray(int var1);

   int sizeOfJarFileArray();

   void setJarFileArray(String[] var1);

   void setJarFileArray(int var1, String var2);

   void xsetJarFileArray(XmlString[] var1);

   void xsetJarFileArray(int var1, XmlString var2);

   void insertJarFile(int var1, String var2);

   void addJarFile(String var1);

   XmlString insertNewJarFile(int var1);

   XmlString addNewJarFile();

   void removeJarFile(int var1);

   String[] getClass1Array();

   String getClass1Array(int var1);

   XmlString[] xgetClass1Array();

   XmlString xgetClass1Array(int var1);

   int sizeOfClass1Array();

   void setClass1Array(String[] var1);

   void setClass1Array(int var1, String var2);

   void xsetClass1Array(XmlString[] var1);

   void xsetClass1Array(int var1, XmlString var2);

   void insertClass1(int var1, String var2);

   void addClass1(String var1);

   XmlString insertNewClass1(int var1);

   XmlString addNewClass1();

   void removeClass1(int var1);

   boolean getExcludeUnlistedClasses();

   XmlBoolean xgetExcludeUnlistedClasses();

   boolean isSetExcludeUnlistedClasses();

   void setExcludeUnlistedClasses(boolean var1);

   void xsetExcludeUnlistedClasses(XmlBoolean var1);

   void unsetExcludeUnlistedClasses();

   PersistenceUnitCachingType.Enum getSharedCacheMode();

   PersistenceUnitCachingType xgetSharedCacheMode();

   boolean isSetSharedCacheMode();

   void setSharedCacheMode(PersistenceUnitCachingType.Enum var1);

   void xsetSharedCacheMode(PersistenceUnitCachingType var1);

   void unsetSharedCacheMode();

   PersistenceUnitValidationModeType.Enum getValidationMode();

   PersistenceUnitValidationModeType xgetValidationMode();

   boolean isSetValidationMode();

   void setValidationMode(PersistenceUnitValidationModeType.Enum var1);

   void xsetValidationMode(PersistenceUnitValidationModeType var1);

   void unsetValidationMode();

   PropertiesType getProperties();

   boolean isSetProperties();

   void setProperties(PropertiesType var1);

   PropertiesType addNewProperties();

   void unsetProperties();

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   PersistenceUnitTransactionType.Enum getTransactionType();

   PersistenceUnitTransactionType xgetTransactionType();

   boolean isSetTransactionType();

   void setTransactionType(PersistenceUnitTransactionType.Enum var1);

   void xsetTransactionType(PersistenceUnitTransactionType var1);

   void unsetTransactionType();

   public static final class Factory {
      public static PersistenceUnitType newInstance() {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitType.type, (XmlOptions)null);
      }

      public static PersistenceUnitType newInstance(XmlOptions options) {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitType.type, options);
      }

      public static PersistenceUnitType parse(String xmlAsString) throws XmlException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUnitType.type, (XmlOptions)null);
      }

      public static PersistenceUnitType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUnitType.type, options);
      }

      public static PersistenceUnitType parse(File file) throws XmlException, IOException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUnitType.type, (XmlOptions)null);
      }

      public static PersistenceUnitType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUnitType.type, options);
      }

      public static PersistenceUnitType parse(URL u) throws XmlException, IOException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUnitType.type, (XmlOptions)null);
      }

      public static PersistenceUnitType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUnitType.type, options);
      }

      public static PersistenceUnitType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUnitType.type, (XmlOptions)null);
      }

      public static PersistenceUnitType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUnitType.type, options);
      }

      public static PersistenceUnitType parse(Reader r) throws XmlException, IOException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUnitType.type, (XmlOptions)null);
      }

      public static PersistenceUnitType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUnitType.type, options);
      }

      public static PersistenceUnitType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUnitType.type, (XmlOptions)null);
      }

      public static PersistenceUnitType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUnitType.type, options);
      }

      public static PersistenceUnitType parse(Node node) throws XmlException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUnitType.type, (XmlOptions)null);
      }

      public static PersistenceUnitType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUnitType.type, options);
      }

      /** @deprecated */
      public static PersistenceUnitType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUnitType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceUnitType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceUnitType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUnitType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUnitType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUnitType.type, options);
      }

      private Factory() {
      }
   }
}
