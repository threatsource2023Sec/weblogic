package com.bea.ns.weblogic.x60.impl;

import com.bea.ns.weblogic.x60.DatabaseTypeType;
import com.bea.ns.weblogic.x60.TrueFalseType;
import com.bea.ns.weblogic.x60.ValidateDbSchemaWithType;
import com.bea.ns.weblogic.x60.WeblogicRdbmsBeanType;
import com.bea.ns.weblogic.x60.WeblogicRdbmsJarType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicRdbmsJarTypeImpl extends XmlComplexContentImpl implements WeblogicRdbmsJarType {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICRDBMSBEAN$0 = new QName("http://www.bea.com/ns/weblogic/60", "weblogic-rdbms-bean");
   private static final QName CREATEDEFAULTDBMSTABLES$2 = new QName("http://www.bea.com/ns/weblogic/60", "create-default-dbms-tables");
   private static final QName VALIDATEDBSCHEMAWITH$4 = new QName("http://www.bea.com/ns/weblogic/60", "validate-db-schema-with");
   private static final QName DATABASETYPE$6 = new QName("http://www.bea.com/ns/weblogic/60", "database-type");

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

   public TrueFalseType getCreateDefaultDbmsTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CREATEDEFAULTDBMSTABLES$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCreateDefaultDbmsTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CREATEDEFAULTDBMSTABLES$2) != 0;
      }
   }

   public void setCreateDefaultDbmsTables(TrueFalseType createDefaultDbmsTables) {
      this.generatedSetterHelperImpl(createDefaultDbmsTables, CREATEDEFAULTDBMSTABLES$2, 0, (short)1);
   }

   public TrueFalseType addNewCreateDefaultDbmsTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CREATEDEFAULTDBMSTABLES$2);
         return target;
      }
   }

   public void unsetCreateDefaultDbmsTables() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CREATEDEFAULTDBMSTABLES$2, 0);
      }
   }

   public ValidateDbSchemaWithType getValidateDbSchemaWith() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ValidateDbSchemaWithType target = null;
         target = (ValidateDbSchemaWithType)this.get_store().find_element_user(VALIDATEDBSCHEMAWITH$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetValidateDbSchemaWith() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VALIDATEDBSCHEMAWITH$4) != 0;
      }
   }

   public void setValidateDbSchemaWith(ValidateDbSchemaWithType validateDbSchemaWith) {
      this.generatedSetterHelperImpl(validateDbSchemaWith, VALIDATEDBSCHEMAWITH$4, 0, (short)1);
   }

   public ValidateDbSchemaWithType addNewValidateDbSchemaWith() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ValidateDbSchemaWithType target = null;
         target = (ValidateDbSchemaWithType)this.get_store().add_element_user(VALIDATEDBSCHEMAWITH$4);
         return target;
      }
   }

   public void unsetValidateDbSchemaWith() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VALIDATEDBSCHEMAWITH$4, 0);
      }
   }

   public DatabaseTypeType getDatabaseType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DatabaseTypeType target = null;
         target = (DatabaseTypeType)this.get_store().find_element_user(DATABASETYPE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDatabaseType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATABASETYPE$6) != 0;
      }
   }

   public void setDatabaseType(DatabaseTypeType databaseType) {
      this.generatedSetterHelperImpl(databaseType, DATABASETYPE$6, 0, (short)1);
   }

   public DatabaseTypeType addNewDatabaseType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DatabaseTypeType target = null;
         target = (DatabaseTypeType)this.get_store().add_element_user(DATABASETYPE$6);
         return target;
      }
   }

   public void unsetDatabaseType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATABASETYPE$6, 0);
      }
   }
}
