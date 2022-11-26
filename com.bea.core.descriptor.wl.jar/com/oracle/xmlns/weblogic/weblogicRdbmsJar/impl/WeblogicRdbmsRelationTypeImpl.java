package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.RelationNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TableNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WeblogicRdbmsRelationType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WeblogicRelationshipRoleType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicRdbmsRelationTypeImpl extends XmlComplexContentImpl implements WeblogicRdbmsRelationType {
   private static final long serialVersionUID = 1L;
   private static final QName RELATIONNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "relation-name");
   private static final QName TABLENAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "table-name");
   private static final QName WEBLOGICRELATIONSHIPROLE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "weblogic-relationship-role");
   private static final QName ID$6 = new QName("", "id");

   public WeblogicRdbmsRelationTypeImpl(SchemaType sType) {
      super(sType);
   }

   public RelationNameType getRelationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationNameType target = null;
         target = (RelationNameType)this.get_store().find_element_user(RELATIONNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setRelationName(RelationNameType relationName) {
      this.generatedSetterHelperImpl(relationName, RELATIONNAME$0, 0, (short)1);
   }

   public RelationNameType addNewRelationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationNameType target = null;
         target = (RelationNameType)this.get_store().add_element_user(RELATIONNAME$0);
         return target;
      }
   }

   public TableNameType getTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableNameType target = null;
         target = (TableNameType)this.get_store().find_element_user(TABLENAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLENAME$2) != 0;
      }
   }

   public void setTableName(TableNameType tableName) {
      this.generatedSetterHelperImpl(tableName, TABLENAME$2, 0, (short)1);
   }

   public TableNameType addNewTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableNameType target = null;
         target = (TableNameType)this.get_store().add_element_user(TABLENAME$2);
         return target;
      }
   }

   public void unsetTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLENAME$2, 0);
      }
   }

   public WeblogicRelationshipRoleType[] getWeblogicRelationshipRoleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WEBLOGICRELATIONSHIPROLE$4, targetList);
         WeblogicRelationshipRoleType[] result = new WeblogicRelationshipRoleType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WeblogicRelationshipRoleType getWeblogicRelationshipRoleArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicRelationshipRoleType target = null;
         target = (WeblogicRelationshipRoleType)this.get_store().find_element_user(WEBLOGICRELATIONSHIPROLE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWeblogicRelationshipRoleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEBLOGICRELATIONSHIPROLE$4);
      }
   }

   public void setWeblogicRelationshipRoleArray(WeblogicRelationshipRoleType[] weblogicRelationshipRoleArray) {
      this.check_orphaned();
      this.arraySetterHelper(weblogicRelationshipRoleArray, WEBLOGICRELATIONSHIPROLE$4);
   }

   public void setWeblogicRelationshipRoleArray(int i, WeblogicRelationshipRoleType weblogicRelationshipRole) {
      this.generatedSetterHelperImpl(weblogicRelationshipRole, WEBLOGICRELATIONSHIPROLE$4, i, (short)2);
   }

   public WeblogicRelationshipRoleType insertNewWeblogicRelationshipRole(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicRelationshipRoleType target = null;
         target = (WeblogicRelationshipRoleType)this.get_store().insert_element_user(WEBLOGICRELATIONSHIPROLE$4, i);
         return target;
      }
   }

   public WeblogicRelationshipRoleType addNewWeblogicRelationshipRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicRelationshipRoleType target = null;
         target = (WeblogicRelationshipRoleType)this.get_store().add_element_user(WEBLOGICRELATIONSHIPROLE$4);
         return target;
      }
   }

   public void removeWeblogicRelationshipRole(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEBLOGICRELATIONSHIPROLE$4, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
