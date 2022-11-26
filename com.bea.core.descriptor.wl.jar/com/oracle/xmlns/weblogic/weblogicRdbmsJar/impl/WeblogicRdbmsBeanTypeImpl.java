package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.AutomaticKeyGenerationType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CmpFieldType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.DataSourceJndiNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.DelayDatabaseInsertUntilType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.FieldGroupType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.InstanceLockOrderType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.RelationshipCachingType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.SqlShapeType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TableMapType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TrueFalseType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.UnknownPrimaryKeyFieldType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WeblogicQueryType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WeblogicRdbmsBeanType;
import com.sun.java.xml.ns.j2Ee.EjbNameType;
import com.sun.java.xml.ns.j2Ee.XsdNonNegativeIntegerType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicRdbmsBeanTypeImpl extends XmlComplexContentImpl implements WeblogicRdbmsBeanType {
   private static final long serialVersionUID = 1L;
   private static final QName EJBNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "ejb-name");
   private static final QName DATASOURCEJNDINAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "data-source-jndi-name");
   private static final QName UNKNOWNPRIMARYKEYFIELD$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "unknown-primary-key-field");
   private static final QName TABLEMAP$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "table-map");
   private static final QName FIELDGROUP$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "field-group");
   private static final QName RELATIONSHIPCACHING$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "relationship-caching");
   private static final QName SQLSHAPE$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "sql-shape");
   private static final QName WEBLOGICQUERY$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "weblogic-query");
   private static final QName DELAYDATABASEINSERTUNTIL$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "delay-database-insert-until");
   private static final QName USESELECTFORUPDATE$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "use-select-for-update");
   private static final QName LOCKORDER$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "lock-order");
   private static final QName INSTANCELOCKORDER$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "instance-lock-order");
   private static final QName AUTOMATICKEYGENERATION$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "automatic-key-generation");
   private static final QName CHECKEXISTSONMETHOD$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "check-exists-on-method");
   private static final QName CLUSTERINVALIDATIONDISABLED$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "cluster-invalidation-disabled");
   private static final QName USEINNERJOIN$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "use-inner-join");
   private static final QName CATEGORYCMPFIELD$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "category-cmp-field");
   private static final QName ID$34 = new QName("", "id");

   public WeblogicRdbmsBeanTypeImpl(SchemaType sType) {
      super(sType);
   }

   public EjbNameType getEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().find_element_user(EJBNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbName(EjbNameType ejbName) {
      this.generatedSetterHelperImpl(ejbName, EJBNAME$0, 0, (short)1);
   }

   public EjbNameType addNewEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().add_element_user(EJBNAME$0);
         return target;
      }
   }

   public DataSourceJndiNameType getDataSourceJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceJndiNameType target = null;
         target = (DataSourceJndiNameType)this.get_store().find_element_user(DATASOURCEJNDINAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setDataSourceJndiName(DataSourceJndiNameType dataSourceJndiName) {
      this.generatedSetterHelperImpl(dataSourceJndiName, DATASOURCEJNDINAME$2, 0, (short)1);
   }

   public DataSourceJndiNameType addNewDataSourceJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceJndiNameType target = null;
         target = (DataSourceJndiNameType)this.get_store().add_element_user(DATASOURCEJNDINAME$2);
         return target;
      }
   }

   public UnknownPrimaryKeyFieldType getUnknownPrimaryKeyField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnknownPrimaryKeyFieldType target = null;
         target = (UnknownPrimaryKeyFieldType)this.get_store().find_element_user(UNKNOWNPRIMARYKEYFIELD$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUnknownPrimaryKeyField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNKNOWNPRIMARYKEYFIELD$4) != 0;
      }
   }

   public void setUnknownPrimaryKeyField(UnknownPrimaryKeyFieldType unknownPrimaryKeyField) {
      this.generatedSetterHelperImpl(unknownPrimaryKeyField, UNKNOWNPRIMARYKEYFIELD$4, 0, (short)1);
   }

   public UnknownPrimaryKeyFieldType addNewUnknownPrimaryKeyField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnknownPrimaryKeyFieldType target = null;
         target = (UnknownPrimaryKeyFieldType)this.get_store().add_element_user(UNKNOWNPRIMARYKEYFIELD$4);
         return target;
      }
   }

   public void unsetUnknownPrimaryKeyField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNKNOWNPRIMARYKEYFIELD$4, 0);
      }
   }

   public TableMapType[] getTableMapArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TABLEMAP$6, targetList);
         TableMapType[] result = new TableMapType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public TableMapType getTableMapArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableMapType target = null;
         target = (TableMapType)this.get_store().find_element_user(TABLEMAP$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfTableMapArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLEMAP$6);
      }
   }

   public void setTableMapArray(TableMapType[] tableMapArray) {
      this.check_orphaned();
      this.arraySetterHelper(tableMapArray, TABLEMAP$6);
   }

   public void setTableMapArray(int i, TableMapType tableMap) {
      this.generatedSetterHelperImpl(tableMap, TABLEMAP$6, i, (short)2);
   }

   public TableMapType insertNewTableMap(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableMapType target = null;
         target = (TableMapType)this.get_store().insert_element_user(TABLEMAP$6, i);
         return target;
      }
   }

   public TableMapType addNewTableMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableMapType target = null;
         target = (TableMapType)this.get_store().add_element_user(TABLEMAP$6);
         return target;
      }
   }

   public void removeTableMap(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLEMAP$6, i);
      }
   }

   public FieldGroupType[] getFieldGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FIELDGROUP$8, targetList);
         FieldGroupType[] result = new FieldGroupType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FieldGroupType getFieldGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldGroupType target = null;
         target = (FieldGroupType)this.get_store().find_element_user(FIELDGROUP$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFieldGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FIELDGROUP$8);
      }
   }

   public void setFieldGroupArray(FieldGroupType[] fieldGroupArray) {
      this.check_orphaned();
      this.arraySetterHelper(fieldGroupArray, FIELDGROUP$8);
   }

   public void setFieldGroupArray(int i, FieldGroupType fieldGroup) {
      this.generatedSetterHelperImpl(fieldGroup, FIELDGROUP$8, i, (short)2);
   }

   public FieldGroupType insertNewFieldGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldGroupType target = null;
         target = (FieldGroupType)this.get_store().insert_element_user(FIELDGROUP$8, i);
         return target;
      }
   }

   public FieldGroupType addNewFieldGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldGroupType target = null;
         target = (FieldGroupType)this.get_store().add_element_user(FIELDGROUP$8);
         return target;
      }
   }

   public void removeFieldGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FIELDGROUP$8, i);
      }
   }

   public RelationshipCachingType[] getRelationshipCachingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RELATIONSHIPCACHING$10, targetList);
         RelationshipCachingType[] result = new RelationshipCachingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public RelationshipCachingType getRelationshipCachingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipCachingType target = null;
         target = (RelationshipCachingType)this.get_store().find_element_user(RELATIONSHIPCACHING$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfRelationshipCachingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RELATIONSHIPCACHING$10);
      }
   }

   public void setRelationshipCachingArray(RelationshipCachingType[] relationshipCachingArray) {
      this.check_orphaned();
      this.arraySetterHelper(relationshipCachingArray, RELATIONSHIPCACHING$10);
   }

   public void setRelationshipCachingArray(int i, RelationshipCachingType relationshipCaching) {
      this.generatedSetterHelperImpl(relationshipCaching, RELATIONSHIPCACHING$10, i, (short)2);
   }

   public RelationshipCachingType insertNewRelationshipCaching(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipCachingType target = null;
         target = (RelationshipCachingType)this.get_store().insert_element_user(RELATIONSHIPCACHING$10, i);
         return target;
      }
   }

   public RelationshipCachingType addNewRelationshipCaching() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipCachingType target = null;
         target = (RelationshipCachingType)this.get_store().add_element_user(RELATIONSHIPCACHING$10);
         return target;
      }
   }

   public void removeRelationshipCaching(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RELATIONSHIPCACHING$10, i);
      }
   }

   public SqlShapeType[] getSqlShapeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SQLSHAPE$12, targetList);
         SqlShapeType[] result = new SqlShapeType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SqlShapeType getSqlShapeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlShapeType target = null;
         target = (SqlShapeType)this.get_store().find_element_user(SQLSHAPE$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSqlShapeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SQLSHAPE$12);
      }
   }

   public void setSqlShapeArray(SqlShapeType[] sqlShapeArray) {
      this.check_orphaned();
      this.arraySetterHelper(sqlShapeArray, SQLSHAPE$12);
   }

   public void setSqlShapeArray(int i, SqlShapeType sqlShape) {
      this.generatedSetterHelperImpl(sqlShape, SQLSHAPE$12, i, (short)2);
   }

   public SqlShapeType insertNewSqlShape(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlShapeType target = null;
         target = (SqlShapeType)this.get_store().insert_element_user(SQLSHAPE$12, i);
         return target;
      }
   }

   public SqlShapeType addNewSqlShape() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlShapeType target = null;
         target = (SqlShapeType)this.get_store().add_element_user(SQLSHAPE$12);
         return target;
      }
   }

   public void removeSqlShape(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SQLSHAPE$12, i);
      }
   }

   public WeblogicQueryType[] getWeblogicQueryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WEBLOGICQUERY$14, targetList);
         WeblogicQueryType[] result = new WeblogicQueryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WeblogicQueryType getWeblogicQueryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicQueryType target = null;
         target = (WeblogicQueryType)this.get_store().find_element_user(WEBLOGICQUERY$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWeblogicQueryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEBLOGICQUERY$14);
      }
   }

   public void setWeblogicQueryArray(WeblogicQueryType[] weblogicQueryArray) {
      this.check_orphaned();
      this.arraySetterHelper(weblogicQueryArray, WEBLOGICQUERY$14);
   }

   public void setWeblogicQueryArray(int i, WeblogicQueryType weblogicQuery) {
      this.generatedSetterHelperImpl(weblogicQuery, WEBLOGICQUERY$14, i, (short)2);
   }

   public WeblogicQueryType insertNewWeblogicQuery(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicQueryType target = null;
         target = (WeblogicQueryType)this.get_store().insert_element_user(WEBLOGICQUERY$14, i);
         return target;
      }
   }

   public WeblogicQueryType addNewWeblogicQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicQueryType target = null;
         target = (WeblogicQueryType)this.get_store().add_element_user(WEBLOGICQUERY$14);
         return target;
      }
   }

   public void removeWeblogicQuery(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEBLOGICQUERY$14, i);
      }
   }

   public DelayDatabaseInsertUntilType getDelayDatabaseInsertUntil() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DelayDatabaseInsertUntilType target = null;
         target = (DelayDatabaseInsertUntilType)this.get_store().find_element_user(DELAYDATABASEINSERTUNTIL$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDelayDatabaseInsertUntil() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DELAYDATABASEINSERTUNTIL$16) != 0;
      }
   }

   public void setDelayDatabaseInsertUntil(DelayDatabaseInsertUntilType delayDatabaseInsertUntil) {
      this.generatedSetterHelperImpl(delayDatabaseInsertUntil, DELAYDATABASEINSERTUNTIL$16, 0, (short)1);
   }

   public DelayDatabaseInsertUntilType addNewDelayDatabaseInsertUntil() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DelayDatabaseInsertUntilType target = null;
         target = (DelayDatabaseInsertUntilType)this.get_store().add_element_user(DELAYDATABASEINSERTUNTIL$16);
         return target;
      }
   }

   public void unsetDelayDatabaseInsertUntil() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DELAYDATABASEINSERTUNTIL$16, 0);
      }
   }

   public TrueFalseType getUseSelectForUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(USESELECTFORUPDATE$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUseSelectForUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USESELECTFORUPDATE$18) != 0;
      }
   }

   public void setUseSelectForUpdate(TrueFalseType useSelectForUpdate) {
      this.generatedSetterHelperImpl(useSelectForUpdate, USESELECTFORUPDATE$18, 0, (short)1);
   }

   public TrueFalseType addNewUseSelectForUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(USESELECTFORUPDATE$18);
         return target;
      }
   }

   public void unsetUseSelectForUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USESELECTFORUPDATE$18, 0);
      }
   }

   public XsdNonNegativeIntegerType getLockOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(LOCKORDER$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLockOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCKORDER$20) != 0;
      }
   }

   public void setLockOrder(XsdNonNegativeIntegerType lockOrder) {
      this.generatedSetterHelperImpl(lockOrder, LOCKORDER$20, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewLockOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(LOCKORDER$20);
         return target;
      }
   }

   public void unsetLockOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCKORDER$20, 0);
      }
   }

   public InstanceLockOrderType getInstanceLockOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InstanceLockOrderType target = null;
         target = (InstanceLockOrderType)this.get_store().find_element_user(INSTANCELOCKORDER$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInstanceLockOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INSTANCELOCKORDER$22) != 0;
      }
   }

   public void setInstanceLockOrder(InstanceLockOrderType instanceLockOrder) {
      this.generatedSetterHelperImpl(instanceLockOrder, INSTANCELOCKORDER$22, 0, (short)1);
   }

   public InstanceLockOrderType addNewInstanceLockOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InstanceLockOrderType target = null;
         target = (InstanceLockOrderType)this.get_store().add_element_user(INSTANCELOCKORDER$22);
         return target;
      }
   }

   public void unsetInstanceLockOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INSTANCELOCKORDER$22, 0);
      }
   }

   public AutomaticKeyGenerationType getAutomaticKeyGeneration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AutomaticKeyGenerationType target = null;
         target = (AutomaticKeyGenerationType)this.get_store().find_element_user(AUTOMATICKEYGENERATION$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAutomaticKeyGeneration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTOMATICKEYGENERATION$24) != 0;
      }
   }

   public void setAutomaticKeyGeneration(AutomaticKeyGenerationType automaticKeyGeneration) {
      this.generatedSetterHelperImpl(automaticKeyGeneration, AUTOMATICKEYGENERATION$24, 0, (short)1);
   }

   public AutomaticKeyGenerationType addNewAutomaticKeyGeneration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AutomaticKeyGenerationType target = null;
         target = (AutomaticKeyGenerationType)this.get_store().add_element_user(AUTOMATICKEYGENERATION$24);
         return target;
      }
   }

   public void unsetAutomaticKeyGeneration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTOMATICKEYGENERATION$24, 0);
      }
   }

   public TrueFalseType getCheckExistsOnMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CHECKEXISTSONMETHOD$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCheckExistsOnMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHECKEXISTSONMETHOD$26) != 0;
      }
   }

   public void setCheckExistsOnMethod(TrueFalseType checkExistsOnMethod) {
      this.generatedSetterHelperImpl(checkExistsOnMethod, CHECKEXISTSONMETHOD$26, 0, (short)1);
   }

   public TrueFalseType addNewCheckExistsOnMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CHECKEXISTSONMETHOD$26);
         return target;
      }
   }

   public void unsetCheckExistsOnMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHECKEXISTSONMETHOD$26, 0);
      }
   }

   public TrueFalseType getClusterInvalidationDisabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CLUSTERINVALIDATIONDISABLED$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClusterInvalidationDisabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLUSTERINVALIDATIONDISABLED$28) != 0;
      }
   }

   public void setClusterInvalidationDisabled(TrueFalseType clusterInvalidationDisabled) {
      this.generatedSetterHelperImpl(clusterInvalidationDisabled, CLUSTERINVALIDATIONDISABLED$28, 0, (short)1);
   }

   public TrueFalseType addNewClusterInvalidationDisabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CLUSTERINVALIDATIONDISABLED$28);
         return target;
      }
   }

   public void unsetClusterInvalidationDisabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLUSTERINVALIDATIONDISABLED$28, 0);
      }
   }

   public TrueFalseType getUseInnerJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(USEINNERJOIN$30, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUseInnerJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USEINNERJOIN$30) != 0;
      }
   }

   public void setUseInnerJoin(TrueFalseType useInnerJoin) {
      this.generatedSetterHelperImpl(useInnerJoin, USEINNERJOIN$30, 0, (short)1);
   }

   public TrueFalseType addNewUseInnerJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(USEINNERJOIN$30);
         return target;
      }
   }

   public void unsetUseInnerJoin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USEINNERJOIN$30, 0);
      }
   }

   public CmpFieldType getCategoryCmpField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().find_element_user(CATEGORYCMPFIELD$32, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCategoryCmpField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CATEGORYCMPFIELD$32) != 0;
      }
   }

   public void setCategoryCmpField(CmpFieldType categoryCmpField) {
      this.generatedSetterHelperImpl(categoryCmpField, CATEGORYCMPFIELD$32, 0, (short)1);
   }

   public CmpFieldType addNewCategoryCmpField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmpFieldType target = null;
         target = (CmpFieldType)this.get_store().add_element_user(CATEGORYCMPFIELD$32);
         return target;
      }
   }

   public void unsetCategoryCmpField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CATEGORYCMPFIELD$32, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$34);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$34);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$34) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$34);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$34);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$34);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$34);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$34);
      }
   }
}
