package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CompatibilityType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CreateDefaultDbmsTablesType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.DatabaseTypeType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.DefaultDbmsTablesDdlType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TrueFalseType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.ValidateDbSchemaWithType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WeblogicRdbmsBeanType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WeblogicRdbmsJarType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WeblogicRdbmsRelationType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicRdbmsJarTypeImpl extends XmlComplexContentImpl implements WeblogicRdbmsJarType {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICRDBMSBEAN$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "weblogic-rdbms-bean");
   private static final QName WEBLOGICRDBMSRELATION$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "weblogic-rdbms-relation");
   private static final QName ORDERDATABASEOPERATIONS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "order-database-operations");
   private static final QName ENABLEBATCHOPERATIONS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "enable-batch-operations");
   private static final QName CREATEDEFAULTDBMSTABLES$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "create-default-dbms-tables");
   private static final QName VALIDATEDBSCHEMAWITH$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "validate-db-schema-with");
   private static final QName DATABASETYPE$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "database-type");
   private static final QName DEFAULTDBMSTABLESDDL$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "default-dbms-tables-ddl");
   private static final QName COMPATIBILITY$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "compatibility");
   private static final QName ID$18 = new QName("", "id");
   private static final QName VERSION$20 = new QName("", "version");

   public WeblogicRdbmsJarTypeImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicRdbmsBeanType[] getWeblogicRdbmsBeanArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WEBLOGICRDBMSBEAN$0, targetList);
         WeblogicRdbmsBeanType[] result = new WeblogicRdbmsBeanType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WeblogicRdbmsBeanType getWeblogicRdbmsBeanArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicRdbmsBeanType target = null;
         target = (WeblogicRdbmsBeanType)this.get_store().find_element_user(WEBLOGICRDBMSBEAN$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWeblogicRdbmsBeanArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEBLOGICRDBMSBEAN$0);
      }
   }

   public void setWeblogicRdbmsBeanArray(WeblogicRdbmsBeanType[] weblogicRdbmsBeanArray) {
      this.check_orphaned();
      this.arraySetterHelper(weblogicRdbmsBeanArray, WEBLOGICRDBMSBEAN$0);
   }

   public void setWeblogicRdbmsBeanArray(int i, WeblogicRdbmsBeanType weblogicRdbmsBean) {
      this.generatedSetterHelperImpl(weblogicRdbmsBean, WEBLOGICRDBMSBEAN$0, i, (short)2);
   }

   public WeblogicRdbmsBeanType insertNewWeblogicRdbmsBean(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicRdbmsBeanType target = null;
         target = (WeblogicRdbmsBeanType)this.get_store().insert_element_user(WEBLOGICRDBMSBEAN$0, i);
         return target;
      }
   }

   public WeblogicRdbmsBeanType addNewWeblogicRdbmsBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicRdbmsBeanType target = null;
         target = (WeblogicRdbmsBeanType)this.get_store().add_element_user(WEBLOGICRDBMSBEAN$0);
         return target;
      }
   }

   public void removeWeblogicRdbmsBean(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEBLOGICRDBMSBEAN$0, i);
      }
   }

   public WeblogicRdbmsRelationType[] getWeblogicRdbmsRelationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WEBLOGICRDBMSRELATION$2, targetList);
         WeblogicRdbmsRelationType[] result = new WeblogicRdbmsRelationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WeblogicRdbmsRelationType getWeblogicRdbmsRelationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicRdbmsRelationType target = null;
         target = (WeblogicRdbmsRelationType)this.get_store().find_element_user(WEBLOGICRDBMSRELATION$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWeblogicRdbmsRelationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEBLOGICRDBMSRELATION$2);
      }
   }

   public void setWeblogicRdbmsRelationArray(WeblogicRdbmsRelationType[] weblogicRdbmsRelationArray) {
      this.check_orphaned();
      this.arraySetterHelper(weblogicRdbmsRelationArray, WEBLOGICRDBMSRELATION$2);
   }

   public void setWeblogicRdbmsRelationArray(int i, WeblogicRdbmsRelationType weblogicRdbmsRelation) {
      this.generatedSetterHelperImpl(weblogicRdbmsRelation, WEBLOGICRDBMSRELATION$2, i, (short)2);
   }

   public WeblogicRdbmsRelationType insertNewWeblogicRdbmsRelation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicRdbmsRelationType target = null;
         target = (WeblogicRdbmsRelationType)this.get_store().insert_element_user(WEBLOGICRDBMSRELATION$2, i);
         return target;
      }
   }

   public WeblogicRdbmsRelationType addNewWeblogicRdbmsRelation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicRdbmsRelationType target = null;
         target = (WeblogicRdbmsRelationType)this.get_store().add_element_user(WEBLOGICRDBMSRELATION$2);
         return target;
      }
   }

   public void removeWeblogicRdbmsRelation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEBLOGICRDBMSRELATION$2, i);
      }
   }

   public TrueFalseType getOrderDatabaseOperations() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ORDERDATABASEOPERATIONS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetOrderDatabaseOperations() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ORDERDATABASEOPERATIONS$4) != 0;
      }
   }

   public void setOrderDatabaseOperations(TrueFalseType orderDatabaseOperations) {
      this.generatedSetterHelperImpl(orderDatabaseOperations, ORDERDATABASEOPERATIONS$4, 0, (short)1);
   }

   public TrueFalseType addNewOrderDatabaseOperations() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ORDERDATABASEOPERATIONS$4);
         return target;
      }
   }

   public void unsetOrderDatabaseOperations() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ORDERDATABASEOPERATIONS$4, 0);
      }
   }

   public TrueFalseType getEnableBatchOperations() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLEBATCHOPERATIONS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnableBatchOperations() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLEBATCHOPERATIONS$6) != 0;
      }
   }

   public void setEnableBatchOperations(TrueFalseType enableBatchOperations) {
      this.generatedSetterHelperImpl(enableBatchOperations, ENABLEBATCHOPERATIONS$6, 0, (short)1);
   }

   public TrueFalseType addNewEnableBatchOperations() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLEBATCHOPERATIONS$6);
         return target;
      }
   }

   public void unsetEnableBatchOperations() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLEBATCHOPERATIONS$6, 0);
      }
   }

   public CreateDefaultDbmsTablesType getCreateDefaultDbmsTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CreateDefaultDbmsTablesType target = null;
         target = (CreateDefaultDbmsTablesType)this.get_store().find_element_user(CREATEDEFAULTDBMSTABLES$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCreateDefaultDbmsTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CREATEDEFAULTDBMSTABLES$8) != 0;
      }
   }

   public void setCreateDefaultDbmsTables(CreateDefaultDbmsTablesType createDefaultDbmsTables) {
      this.generatedSetterHelperImpl(createDefaultDbmsTables, CREATEDEFAULTDBMSTABLES$8, 0, (short)1);
   }

   public CreateDefaultDbmsTablesType addNewCreateDefaultDbmsTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CreateDefaultDbmsTablesType target = null;
         target = (CreateDefaultDbmsTablesType)this.get_store().add_element_user(CREATEDEFAULTDBMSTABLES$8);
         return target;
      }
   }

   public void unsetCreateDefaultDbmsTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CREATEDEFAULTDBMSTABLES$8, 0);
      }
   }

   public ValidateDbSchemaWithType getValidateDbSchemaWith() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ValidateDbSchemaWithType target = null;
         target = (ValidateDbSchemaWithType)this.get_store().find_element_user(VALIDATEDBSCHEMAWITH$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetValidateDbSchemaWith() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VALIDATEDBSCHEMAWITH$10) != 0;
      }
   }

   public void setValidateDbSchemaWith(ValidateDbSchemaWithType validateDbSchemaWith) {
      this.generatedSetterHelperImpl(validateDbSchemaWith, VALIDATEDBSCHEMAWITH$10, 0, (short)1);
   }

   public ValidateDbSchemaWithType addNewValidateDbSchemaWith() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ValidateDbSchemaWithType target = null;
         target = (ValidateDbSchemaWithType)this.get_store().add_element_user(VALIDATEDBSCHEMAWITH$10);
         return target;
      }
   }

   public void unsetValidateDbSchemaWith() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VALIDATEDBSCHEMAWITH$10, 0);
      }
   }

   public DatabaseTypeType getDatabaseType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DatabaseTypeType target = null;
         target = (DatabaseTypeType)this.get_store().find_element_user(DATABASETYPE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDatabaseType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATABASETYPE$12) != 0;
      }
   }

   public void setDatabaseType(DatabaseTypeType databaseType) {
      this.generatedSetterHelperImpl(databaseType, DATABASETYPE$12, 0, (short)1);
   }

   public DatabaseTypeType addNewDatabaseType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DatabaseTypeType target = null;
         target = (DatabaseTypeType)this.get_store().add_element_user(DATABASETYPE$12);
         return target;
      }
   }

   public void unsetDatabaseType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATABASETYPE$12, 0);
      }
   }

   public DefaultDbmsTablesDdlType getDefaultDbmsTablesDdl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDbmsTablesDdlType target = null;
         target = (DefaultDbmsTablesDdlType)this.get_store().find_element_user(DEFAULTDBMSTABLESDDL$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDefaultDbmsTablesDdl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTDBMSTABLESDDL$14) != 0;
      }
   }

   public void setDefaultDbmsTablesDdl(DefaultDbmsTablesDdlType defaultDbmsTablesDdl) {
      this.generatedSetterHelperImpl(defaultDbmsTablesDdl, DEFAULTDBMSTABLESDDL$14, 0, (short)1);
   }

   public DefaultDbmsTablesDdlType addNewDefaultDbmsTablesDdl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDbmsTablesDdlType target = null;
         target = (DefaultDbmsTablesDdlType)this.get_store().add_element_user(DEFAULTDBMSTABLESDDL$14);
         return target;
      }
   }

   public void unsetDefaultDbmsTablesDdl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTDBMSTABLESDDL$14, 0);
      }
   }

   public CompatibilityType getCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CompatibilityType target = null;
         target = (CompatibilityType)this.get_store().find_element_user(COMPATIBILITY$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMPATIBILITY$16) != 0;
      }
   }

   public void setCompatibility(CompatibilityType compatibility) {
      this.generatedSetterHelperImpl(compatibility, COMPATIBILITY$16, 0, (short)1);
   }

   public CompatibilityType addNewCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CompatibilityType target = null;
         target = (CompatibilityType)this.get_store().add_element_user(COMPATIBILITY$16);
         return target;
      }
   }

   public void unsetCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMPATIBILITY$16, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$18);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$18);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$18) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$18);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$18);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$18);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$18);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$18);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$20);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$20);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$20) != null;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$20);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$20);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$20);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$20);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$20);
      }
   }
}
