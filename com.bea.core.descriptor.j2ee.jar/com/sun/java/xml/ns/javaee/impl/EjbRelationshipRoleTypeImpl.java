package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.CmrFieldType;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.EjbRelationshipRoleType;
import com.sun.java.xml.ns.javaee.EmptyType;
import com.sun.java.xml.ns.javaee.MultiplicityType;
import com.sun.java.xml.ns.javaee.RelationshipRoleSourceType;
import com.sun.java.xml.ns.javaee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class EjbRelationshipRoleTypeImpl extends XmlComplexContentImpl implements EjbRelationshipRoleType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/javaee", "description");
   private static final QName EJBRELATIONSHIPROLENAME$2 = new QName("http://java.sun.com/xml/ns/javaee", "ejb-relationship-role-name");
   private static final QName MULTIPLICITY$4 = new QName("http://java.sun.com/xml/ns/javaee", "multiplicity");
   private static final QName CASCADEDELETE$6 = new QName("http://java.sun.com/xml/ns/javaee", "cascade-delete");
   private static final QName RELATIONSHIPROLESOURCE$8 = new QName("http://java.sun.com/xml/ns/javaee", "relationship-role-source");
   private static final QName CMRFIELD$10 = new QName("http://java.sun.com/xml/ns/javaee", "cmr-field");
   private static final QName ID$12 = new QName("", "id");

   public EjbRelationshipRoleTypeImpl(SchemaType sType) {
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

   public String getEjbRelationshipRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(EJBRELATIONSHIPROLENAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjbRelationshipRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBRELATIONSHIPROLENAME$2) != 0;
      }
   }

   public void setEjbRelationshipRoleName(String ejbRelationshipRoleName) {
      this.generatedSetterHelperImpl(ejbRelationshipRoleName, EJBRELATIONSHIPROLENAME$2, 0, (short)1);
   }

   public String addNewEjbRelationshipRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(EJBRELATIONSHIPROLENAME$2);
         return target;
      }
   }

   public void unsetEjbRelationshipRoleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBRELATIONSHIPROLENAME$2, 0);
      }
   }

   public MultiplicityType getMultiplicity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MultiplicityType target = null;
         target = (MultiplicityType)this.get_store().find_element_user(MULTIPLICITY$4, 0);
         return target == null ? null : target;
      }
   }

   public void setMultiplicity(MultiplicityType multiplicity) {
      this.generatedSetterHelperImpl(multiplicity, MULTIPLICITY$4, 0, (short)1);
   }

   public MultiplicityType addNewMultiplicity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MultiplicityType target = null;
         target = (MultiplicityType)this.get_store().add_element_user(MULTIPLICITY$4);
         return target;
      }
   }

   public EmptyType getCascadeDelete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().find_element_user(CASCADEDELETE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCascadeDelete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CASCADEDELETE$6) != 0;
      }
   }

   public void setCascadeDelete(EmptyType cascadeDelete) {
      this.generatedSetterHelperImpl(cascadeDelete, CASCADEDELETE$6, 0, (short)1);
   }

   public EmptyType addNewCascadeDelete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmptyType target = null;
         target = (EmptyType)this.get_store().add_element_user(CASCADEDELETE$6);
         return target;
      }
   }

   public void unsetCascadeDelete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CASCADEDELETE$6, 0);
      }
   }

   public RelationshipRoleSourceType getRelationshipRoleSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipRoleSourceType target = null;
         target = (RelationshipRoleSourceType)this.get_store().find_element_user(RELATIONSHIPROLESOURCE$8, 0);
         return target == null ? null : target;
      }
   }

   public void setRelationshipRoleSource(RelationshipRoleSourceType relationshipRoleSource) {
      this.generatedSetterHelperImpl(relationshipRoleSource, RELATIONSHIPROLESOURCE$8, 0, (short)1);
   }

   public RelationshipRoleSourceType addNewRelationshipRoleSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipRoleSourceType target = null;
         target = (RelationshipRoleSourceType)this.get_store().add_element_user(RELATIONSHIPROLESOURCE$8);
         return target;
      }
   }

   public CmrFieldType getCmrField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmrFieldType target = null;
         target = (CmrFieldType)this.get_store().find_element_user(CMRFIELD$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCmrField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CMRFIELD$10) != 0;
      }
   }

   public void setCmrField(CmrFieldType cmrField) {
      this.generatedSetterHelperImpl(cmrField, CMRFIELD$10, 0, (short)1);
   }

   public CmrFieldType addNewCmrField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CmrFieldType target = null;
         target = (CmrFieldType)this.get_store().add_element_user(CMRFIELD$10);
         return target;
      }
   }

   public void unsetCmrField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CMRFIELD$10, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$12) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$12);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$12);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$12);
      }
   }
}
