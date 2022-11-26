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

public interface DeprecatedJdoMappingDefaultsType extends MappingDefaultsType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeprecatedJdoMappingDefaultsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("deprecatedjdomappingdefaultstype0969type");

   boolean getUseClassCriteria();

   XmlBoolean xgetUseClassCriteria();

   boolean isSetUseClassCriteria();

   void setUseClassCriteria(boolean var1);

   void xsetUseClassCriteria(XmlBoolean var1);

   void unsetUseClassCriteria();

   String getBaseClassStrategy();

   XmlString xgetBaseClassStrategy();

   boolean isNilBaseClassStrategy();

   boolean isSetBaseClassStrategy();

   void setBaseClassStrategy(String var1);

   void xsetBaseClassStrategy(XmlString var1);

   void setNilBaseClassStrategy();

   void unsetBaseClassStrategy();

   String getVersionStrategy();

   XmlString xgetVersionStrategy();

   boolean isNilVersionStrategy();

   boolean isSetVersionStrategy();

   void setVersionStrategy(String var1);

   void xsetVersionStrategy(XmlString var1);

   void setNilVersionStrategy();

   void unsetVersionStrategy();

   String getDiscriminatorColumnName();

   XmlString xgetDiscriminatorColumnName();

   boolean isNilDiscriminatorColumnName();

   boolean isSetDiscriminatorColumnName();

   void setDiscriminatorColumnName(String var1);

   void xsetDiscriminatorColumnName(XmlString var1);

   void setNilDiscriminatorColumnName();

   void unsetDiscriminatorColumnName();

   String getSubclassStrategy();

   XmlString xgetSubclassStrategy();

   boolean isNilSubclassStrategy();

   boolean isSetSubclassStrategy();

   void setSubclassStrategy(String var1);

   void xsetSubclassStrategy(XmlString var1);

   void setNilSubclassStrategy();

   void unsetSubclassStrategy();

   boolean getIndexVersion();

   XmlBoolean xgetIndexVersion();

   boolean isSetIndexVersion();

   void setIndexVersion(boolean var1);

   void xsetIndexVersion(XmlBoolean var1);

   void unsetIndexVersion();

   boolean getDefaultMissingInfo();

   XmlBoolean xgetDefaultMissingInfo();

   boolean isSetDefaultMissingInfo();

   void setDefaultMissingInfo(boolean var1);

   void xsetDefaultMissingInfo(XmlBoolean var1);

   void unsetDefaultMissingInfo();

   boolean getIndexLogicalForeignKeys();

   XmlBoolean xgetIndexLogicalForeignKeys();

   boolean isSetIndexLogicalForeignKeys();

   void setIndexLogicalForeignKeys(boolean var1);

   void xsetIndexLogicalForeignKeys(XmlBoolean var1);

   void unsetIndexLogicalForeignKeys();

   String getNullIndicatorColumnName();

   XmlString xgetNullIndicatorColumnName();

   boolean isNilNullIndicatorColumnName();

   boolean isSetNullIndicatorColumnName();

   void setNullIndicatorColumnName(String var1);

   void xsetNullIndicatorColumnName(XmlString var1);

   void setNilNullIndicatorColumnName();

   void unsetNullIndicatorColumnName();

   int getForeignKeyDeleteAction();

   XmlInt xgetForeignKeyDeleteAction();

   boolean isSetForeignKeyDeleteAction();

   void setForeignKeyDeleteAction(int var1);

   void xsetForeignKeyDeleteAction(XmlInt var1);

   void unsetForeignKeyDeleteAction();

   String getJoinForeignKeyDeleteAction();

   XmlString xgetJoinForeignKeyDeleteAction();

   boolean isNilJoinForeignKeyDeleteAction();

   boolean isSetJoinForeignKeyDeleteAction();

   void setJoinForeignKeyDeleteAction(String var1);

   void xsetJoinForeignKeyDeleteAction(XmlString var1);

   void setNilJoinForeignKeyDeleteAction();

   void unsetJoinForeignKeyDeleteAction();

   String getDiscriminatorStrategy();

   XmlString xgetDiscriminatorStrategy();

   boolean isNilDiscriminatorStrategy();

   boolean isSetDiscriminatorStrategy();

   void setDiscriminatorStrategy(String var1);

   void xsetDiscriminatorStrategy(XmlString var1);

   void setNilDiscriminatorStrategy();

   void unsetDiscriminatorStrategy();

   boolean getDeferConstraints();

   XmlBoolean xgetDeferConstraints();

   boolean isSetDeferConstraints();

   void setDeferConstraints(boolean var1);

   void xsetDeferConstraints(XmlBoolean var1);

   void unsetDeferConstraints();

   String getFieldStrategies();

   XmlString xgetFieldStrategies();

   boolean isNilFieldStrategies();

   boolean isSetFieldStrategies();

   void setFieldStrategies(String var1);

   void xsetFieldStrategies(XmlString var1);

   void setNilFieldStrategies();

   void unsetFieldStrategies();

   String getVersionColumnName();

   XmlString xgetVersionColumnName();

   boolean isNilVersionColumnName();

   boolean isSetVersionColumnName();

   void setVersionColumnName(String var1);

   void xsetVersionColumnName(XmlString var1);

   void setNilVersionColumnName();

   void unsetVersionColumnName();

   String getDataStoreIdColumnName();

   XmlString xgetDataStoreIdColumnName();

   boolean isNilDataStoreIdColumnName();

   boolean isSetDataStoreIdColumnName();

   void setDataStoreIdColumnName(String var1);

   void xsetDataStoreIdColumnName(XmlString var1);

   void setNilDataStoreIdColumnName();

   void unsetDataStoreIdColumnName();

   boolean getIndexDiscriminator();

   XmlBoolean xgetIndexDiscriminator();

   boolean isSetIndexDiscriminator();

   void setIndexDiscriminator(boolean var1);

   void xsetIndexDiscriminator(XmlBoolean var1);

   void unsetIndexDiscriminator();

   boolean getStoreEnumOrdinal();

   XmlBoolean xgetStoreEnumOrdinal();

   boolean isSetStoreEnumOrdinal();

   void setStoreEnumOrdinal(boolean var1);

   void xsetStoreEnumOrdinal(XmlBoolean var1);

   void unsetStoreEnumOrdinal();

   boolean getOrderLists();

   XmlBoolean xgetOrderLists();

   boolean isSetOrderLists();

   void setOrderLists(boolean var1);

   void xsetOrderLists(XmlBoolean var1);

   void unsetOrderLists();

   String getOrderColumnName();

   XmlString xgetOrderColumnName();

   boolean isNilOrderColumnName();

   boolean isSetOrderColumnName();

   void setOrderColumnName(String var1);

   void xsetOrderColumnName(XmlString var1);

   void setNilOrderColumnName();

   void unsetOrderColumnName();

   boolean getAddNullIndicator();

   XmlBoolean xgetAddNullIndicator();

   boolean isSetAddNullIndicator();

   void setAddNullIndicator(boolean var1);

   void xsetAddNullIndicator(XmlBoolean var1);

   void unsetAddNullIndicator();

   boolean getStoreUnmappedObjectIdString();

   XmlBoolean xgetStoreUnmappedObjectIdString();

   boolean isSetStoreUnmappedObjectIdString();

   void setStoreUnmappedObjectIdString(boolean var1);

   void xsetStoreUnmappedObjectIdString(XmlBoolean var1);

   void unsetStoreUnmappedObjectIdString();

   public static final class Factory {
      public static DeprecatedJdoMappingDefaultsType newInstance() {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().newInstance(DeprecatedJdoMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMappingDefaultsType newInstance(XmlOptions options) {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().newInstance(DeprecatedJdoMappingDefaultsType.type, options);
      }

      public static DeprecatedJdoMappingDefaultsType parse(String xmlAsString) throws XmlException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeprecatedJdoMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMappingDefaultsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeprecatedJdoMappingDefaultsType.type, options);
      }

      public static DeprecatedJdoMappingDefaultsType parse(File file) throws XmlException, IOException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(file, DeprecatedJdoMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMappingDefaultsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(file, DeprecatedJdoMappingDefaultsType.type, options);
      }

      public static DeprecatedJdoMappingDefaultsType parse(URL u) throws XmlException, IOException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(u, DeprecatedJdoMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMappingDefaultsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(u, DeprecatedJdoMappingDefaultsType.type, options);
      }

      public static DeprecatedJdoMappingDefaultsType parse(InputStream is) throws XmlException, IOException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(is, DeprecatedJdoMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMappingDefaultsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(is, DeprecatedJdoMappingDefaultsType.type, options);
      }

      public static DeprecatedJdoMappingDefaultsType parse(Reader r) throws XmlException, IOException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(r, DeprecatedJdoMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMappingDefaultsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(r, DeprecatedJdoMappingDefaultsType.type, options);
      }

      public static DeprecatedJdoMappingDefaultsType parse(XMLStreamReader sr) throws XmlException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(sr, DeprecatedJdoMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMappingDefaultsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(sr, DeprecatedJdoMappingDefaultsType.type, options);
      }

      public static DeprecatedJdoMappingDefaultsType parse(Node node) throws XmlException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(node, DeprecatedJdoMappingDefaultsType.type, (XmlOptions)null);
      }

      public static DeprecatedJdoMappingDefaultsType parse(Node node, XmlOptions options) throws XmlException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(node, DeprecatedJdoMappingDefaultsType.type, options);
      }

      /** @deprecated */
      public static DeprecatedJdoMappingDefaultsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xis, DeprecatedJdoMappingDefaultsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeprecatedJdoMappingDefaultsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeprecatedJdoMappingDefaultsType)XmlBeans.getContextTypeLoader().parse(xis, DeprecatedJdoMappingDefaultsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeprecatedJdoMappingDefaultsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeprecatedJdoMappingDefaultsType.type, options);
      }

      private Factory() {
      }
   }
}
