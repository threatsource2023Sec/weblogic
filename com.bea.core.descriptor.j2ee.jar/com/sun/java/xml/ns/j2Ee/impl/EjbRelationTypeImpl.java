package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.EjbRelationType;
import com.sun.java.xml.ns.j2Ee.EjbRelationshipRoleType;
import com.sun.java.xml.ns.j2Ee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class EjbRelationTypeImpl extends XmlComplexContentImpl implements EjbRelationType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName EJBRELATIONNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-relation-name");
   private static final QName EJBRELATIONSHIPROLE$4 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-relationship-role");
   private static final QName ID$6 = new QName("", "id");

   public EjbRelationTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$0, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, i);
      }
   }

   public String getEjbRelationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(EJBRELATIONNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjbRelationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBRELATIONNAME$2) != 0;
      }
   }

   public void setEjbRelationName(String ejbRelationName) {
      this.generatedSetterHelperImpl(ejbRelationName, EJBRELATIONNAME$2, 0, (short)1);
   }

   public String addNewEjbRelationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(EJBRELATIONNAME$2);
         return target;
      }
   }

   public void unsetEjbRelationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBRELATIONNAME$2, 0);
      }
   }

   public EjbRelationshipRoleType[] getEjbRelationshipRoleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBRELATIONSHIPROLE$4, targetList);
         EjbRelationshipRoleType[] result = new EjbRelationshipRoleType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbRelationshipRoleType getEjbRelationshipRoleArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRelationshipRoleType target = null;
         target = (EjbRelationshipRoleType)this.get_store().find_element_user(EJBRELATIONSHIPROLE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEjbRelationshipRoleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBRELATIONSHIPROLE$4);
      }
   }

   public void setEjbRelationshipRoleArray(EjbRelationshipRoleType[] ejbRelationshipRoleArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbRelationshipRoleArray, EJBRELATIONSHIPROLE$4);
   }

   public void setEjbRelationshipRoleArray(int i, EjbRelationshipRoleType ejbRelationshipRole) {
      this.generatedSetterHelperImpl(ejbRelationshipRole, EJBRELATIONSHIPROLE$4, i, (short)2);
   }

   public EjbRelationshipRoleType insertNewEjbRelationshipRole(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRelationshipRoleType target = null;
         target = (EjbRelationshipRoleType)this.get_store().insert_element_user(EJBRELATIONSHIPROLE$4, i);
         return target;
      }
   }

   public EjbRelationshipRoleType addNewEjbRelationshipRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRelationshipRoleType target = null;
         target = (EjbRelationshipRoleType)this.get_store().add_element_user(EJBRELATIONSHIPROLE$4);
         return target;
      }
   }

   public void removeEjbRelationshipRole(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBRELATIONSHIPROLE$4, i);
      }
   }

   public java.lang.String getId() {
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

   public void setId(java.lang.String id) {
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
