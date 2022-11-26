package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.GroupNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.RelationshipRoleMapType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.RelationshipRoleNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TrueFalseType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WeblogicRelationshipRoleType;
import com.sun.java.xml.ns.j2Ee.EmptyType;
import javax.xml.namespace.QName;

public class WeblogicRelationshipRoleTypeImpl extends XmlComplexContentImpl implements WeblogicRelationshipRoleType {
   private static final long serialVersionUID = 1L;
   private static final QName RELATIONSHIPROLENAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "relationship-role-name");
   private static final QName GROUPNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "group-name");
   private static final QName RELATIONSHIPROLEMAP$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "relationship-role-map");
   private static final QName DBCASCADEDELETE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "db-cascade-delete");
   private static final QName ENABLEQUERYCACHING$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "enable-query-caching");
   private static final QName ID$10 = new QName("", "id");

   public WeblogicRelationshipRoleTypeImpl(SchemaType sType) {
      super(sType);
   }

   public RelationshipRoleNameType getRelationshipRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipRoleNameType target = null;
         target = (RelationshipRoleNameType)this.get_store().find_element_user(RELATIONSHIPROLENAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setRelationshipRoleName(RelationshipRoleNameType relationshipRoleName) {
      this.generatedSetterHelperImpl(relationshipRoleName, RELATIONSHIPROLENAME$0, 0, (short)1);
   }

   public RelationshipRoleNameType addNewRelationshipRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipRoleNameType target = null;
         target = (RelationshipRoleNameType)this.get_store().add_element_user(RELATIONSHIPROLENAME$0);
         return target;
      }
   }

   public GroupNameType getGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupNameType target = null;
         target = (GroupNameType)this.get_store().find_element_user(GROUPNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUPNAME$2) != 0;
      }
   }

   public void setGroupName(GroupNameType groupName) {
      this.generatedSetterHelperImpl(groupName, GROUPNAME$2, 0, (short)1);
   }

   public GroupNameType addNewGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupNameType target = null;
         target = (GroupNameType)this.get_store().add_element_user(GROUPNAME$2);
         return target;
      }
   }

   public void unsetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GROUPNAME$2, 0);
      }
   }

   public RelationshipRoleMapType getRelationshipRoleMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipRoleMapType target = null;
         target = (RelationshipRoleMapType)this.get_store().find_element_user(RELATIONSHIPROLEMAP$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRelationshipRoleMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RELATIONSHIPROLEMAP$4) != 0;
      }
   }

   public void setRelationshipRoleMap(RelationshipRoleMapType relationshipRoleMap) {
      this.generatedSetterHelperImpl(relationshipRoleMap, RELATIONSHIPROLEMAP$4, 0, (short)1);
   }

   public RelationshipRoleMapType addNewRelationshipRoleMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipRoleMapType target = null;
         target = (RelationshipRoleMapType)this.get_store().add_element_user(RELATIONSHIPROLEMAP$4);
         return target;
      }
   }

   public void unsetRelationshipRoleMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RELATIONSHIPROLEMAP$4, 0);
      }
   }

   public EmptyType getDbCascadeDelete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().find_element_user(DBCASCADEDELETE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDbCascadeDelete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DBCASCADEDELETE$6) != 0;
      }
   }

   public void setDbCascadeDelete(EmptyType dbCascadeDelete) {
      this.generatedSetterHelperImpl(dbCascadeDelete, DBCASCADEDELETE$6, 0, (short)1);
   }

   public EmptyType addNewDbCascadeDelete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().add_element_user(DBCASCADEDELETE$6);
         return target;
      }
   }

   public void unsetDbCascadeDelete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DBCASCADEDELETE$6, 0);
      }
   }

   public TrueFalseType getEnableQueryCaching() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLEQUERYCACHING$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnableQueryCaching() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLEQUERYCACHING$8) != 0;
      }
   }

   public void setEnableQueryCaching(TrueFalseType enableQueryCaching) {
      this.generatedSetterHelperImpl(enableQueryCaching, ENABLEQUERYCACHING$8, 0, (short)1);
   }

   public TrueFalseType addNewEnableQueryCaching() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLEQUERYCACHING$8);
         return target;
      }
   }

   public void unsetEnableQueryCaching() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLEQUERYCACHING$8, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$10) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$10);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$10);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$10);
      }
   }
}
