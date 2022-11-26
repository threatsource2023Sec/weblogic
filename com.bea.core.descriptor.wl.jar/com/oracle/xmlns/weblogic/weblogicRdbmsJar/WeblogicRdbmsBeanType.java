package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.EjbNameType;
import com.sun.java.xml.ns.j2Ee.XsdNonNegativeIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WeblogicRdbmsBeanType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicRdbmsBeanType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicrdbmsbeantype9767type");

   EjbNameType getEjbName();

   void setEjbName(EjbNameType var1);

   EjbNameType addNewEjbName();

   DataSourceJndiNameType getDataSourceJndiName();

   void setDataSourceJndiName(DataSourceJndiNameType var1);

   DataSourceJndiNameType addNewDataSourceJndiName();

   UnknownPrimaryKeyFieldType getUnknownPrimaryKeyField();

   boolean isSetUnknownPrimaryKeyField();

   void setUnknownPrimaryKeyField(UnknownPrimaryKeyFieldType var1);

   UnknownPrimaryKeyFieldType addNewUnknownPrimaryKeyField();

   void unsetUnknownPrimaryKeyField();

   TableMapType[] getTableMapArray();

   TableMapType getTableMapArray(int var1);

   int sizeOfTableMapArray();

   void setTableMapArray(TableMapType[] var1);

   void setTableMapArray(int var1, TableMapType var2);

   TableMapType insertNewTableMap(int var1);

   TableMapType addNewTableMap();

   void removeTableMap(int var1);

   FieldGroupType[] getFieldGroupArray();

   FieldGroupType getFieldGroupArray(int var1);

   int sizeOfFieldGroupArray();

   void setFieldGroupArray(FieldGroupType[] var1);

   void setFieldGroupArray(int var1, FieldGroupType var2);

   FieldGroupType insertNewFieldGroup(int var1);

   FieldGroupType addNewFieldGroup();

   void removeFieldGroup(int var1);

   RelationshipCachingType[] getRelationshipCachingArray();

   RelationshipCachingType getRelationshipCachingArray(int var1);

   int sizeOfRelationshipCachingArray();

   void setRelationshipCachingArray(RelationshipCachingType[] var1);

   void setRelationshipCachingArray(int var1, RelationshipCachingType var2);

   RelationshipCachingType insertNewRelationshipCaching(int var1);

   RelationshipCachingType addNewRelationshipCaching();

   void removeRelationshipCaching(int var1);

   SqlShapeType[] getSqlShapeArray();

   SqlShapeType getSqlShapeArray(int var1);

   int sizeOfSqlShapeArray();

   void setSqlShapeArray(SqlShapeType[] var1);

   void setSqlShapeArray(int var1, SqlShapeType var2);

   SqlShapeType insertNewSqlShape(int var1);

   SqlShapeType addNewSqlShape();

   void removeSqlShape(int var1);

   WeblogicQueryType[] getWeblogicQueryArray();

   WeblogicQueryType getWeblogicQueryArray(int var1);

   int sizeOfWeblogicQueryArray();

   void setWeblogicQueryArray(WeblogicQueryType[] var1);

   void setWeblogicQueryArray(int var1, WeblogicQueryType var2);

   WeblogicQueryType insertNewWeblogicQuery(int var1);

   WeblogicQueryType addNewWeblogicQuery();

   void removeWeblogicQuery(int var1);

   DelayDatabaseInsertUntilType getDelayDatabaseInsertUntil();

   boolean isSetDelayDatabaseInsertUntil();

   void setDelayDatabaseInsertUntil(DelayDatabaseInsertUntilType var1);

   DelayDatabaseInsertUntilType addNewDelayDatabaseInsertUntil();

   void unsetDelayDatabaseInsertUntil();

   TrueFalseType getUseSelectForUpdate();

   boolean isSetUseSelectForUpdate();

   void setUseSelectForUpdate(TrueFalseType var1);

   TrueFalseType addNewUseSelectForUpdate();

   void unsetUseSelectForUpdate();

   XsdNonNegativeIntegerType getLockOrder();

   boolean isSetLockOrder();

   void setLockOrder(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewLockOrder();

   void unsetLockOrder();

   InstanceLockOrderType getInstanceLockOrder();

   boolean isSetInstanceLockOrder();

   void setInstanceLockOrder(InstanceLockOrderType var1);

   InstanceLockOrderType addNewInstanceLockOrder();

   void unsetInstanceLockOrder();

   AutomaticKeyGenerationType getAutomaticKeyGeneration();

   boolean isSetAutomaticKeyGeneration();

   void setAutomaticKeyGeneration(AutomaticKeyGenerationType var1);

   AutomaticKeyGenerationType addNewAutomaticKeyGeneration();

   void unsetAutomaticKeyGeneration();

   TrueFalseType getCheckExistsOnMethod();

   boolean isSetCheckExistsOnMethod();

   void setCheckExistsOnMethod(TrueFalseType var1);

   TrueFalseType addNewCheckExistsOnMethod();

   void unsetCheckExistsOnMethod();

   TrueFalseType getClusterInvalidationDisabled();

   boolean isSetClusterInvalidationDisabled();

   void setClusterInvalidationDisabled(TrueFalseType var1);

   TrueFalseType addNewClusterInvalidationDisabled();

   void unsetClusterInvalidationDisabled();

   TrueFalseType getUseInnerJoin();

   boolean isSetUseInnerJoin();

   void setUseInnerJoin(TrueFalseType var1);

   TrueFalseType addNewUseInnerJoin();

   void unsetUseInnerJoin();

   CmpFieldType getCategoryCmpField();

   boolean isSetCategoryCmpField();

   void setCategoryCmpField(CmpFieldType var1);

   CmpFieldType addNewCategoryCmpField();

   void unsetCategoryCmpField();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WeblogicRdbmsBeanType newInstance() {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().newInstance(WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType newInstance(XmlOptions options) {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().newInstance(WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(String xmlAsString) throws XmlException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(File file) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(file, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(file, WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(URL u) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(u, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(u, WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(is, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(is, WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(Reader r) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(r, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(r, WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(Node node) throws XmlException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(node, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(node, WeblogicRdbmsBeanType.type, options);
      }

      /** @deprecated */
      public static WeblogicRdbmsBeanType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicRdbmsBeanType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicRdbmsBeanType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicRdbmsBeanType.type, options);
      }

      private Factory() {
      }
   }
}
