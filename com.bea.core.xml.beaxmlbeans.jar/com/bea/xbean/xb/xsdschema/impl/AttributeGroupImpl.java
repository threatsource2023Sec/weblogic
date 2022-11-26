package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.xb.xsdschema.Attribute;
import com.bea.xbean.xb.xsdschema.AttributeGroup;
import com.bea.xbean.xb.xsdschema.AttributeGroupRef;
import com.bea.xbean.xb.xsdschema.Wildcard;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlNCName;
import com.bea.xml.XmlQName;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AttributeGroupImpl extends AnnotatedImpl implements AttributeGroup {
   private static final QName ATTRIBUTE$0 = new QName("http://www.w3.org/2001/XMLSchema", "attribute");
   private static final QName ATTRIBUTEGROUP$2 = new QName("http://www.w3.org/2001/XMLSchema", "attributeGroup");
   private static final QName ANYATTRIBUTE$4 = new QName("http://www.w3.org/2001/XMLSchema", "anyAttribute");
   private static final QName NAME$6 = new QName("", "name");
   private static final QName REF$8 = new QName("", "ref");

   public AttributeGroupImpl(SchemaType sType) {
      super(sType);
   }

   public Attribute[] getAttributeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ATTRIBUTE$0, targetList);
         Attribute[] result = new Attribute[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Attribute getAttributeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().find_element_user(ATTRIBUTE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAttributeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ATTRIBUTE$0);
      }
   }

   public void setAttributeArray(Attribute[] attributeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(attributeArray, ATTRIBUTE$0);
      }
   }

   public void setAttributeArray(int i, Attribute attribute) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().find_element_user(ATTRIBUTE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(attribute);
         }
      }
   }

   public Attribute insertNewAttribute(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().insert_element_user(ATTRIBUTE$0, i);
         return target;
      }
   }

   public Attribute addNewAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().add_element_user(ATTRIBUTE$0);
         return target;
      }
   }

   public void removeAttribute(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTRIBUTE$0, i);
      }
   }

   public AttributeGroupRef[] getAttributeGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ATTRIBUTEGROUP$2, targetList);
         AttributeGroupRef[] result = new AttributeGroupRef[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AttributeGroupRef getAttributeGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().find_element_user(ATTRIBUTEGROUP$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAttributeGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ATTRIBUTEGROUP$2);
      }
   }

   public void setAttributeGroupArray(AttributeGroupRef[] attributeGroupArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(attributeGroupArray, ATTRIBUTEGROUP$2);
      }
   }

   public void setAttributeGroupArray(int i, AttributeGroupRef attributeGroup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().find_element_user(ATTRIBUTEGROUP$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(attributeGroup);
         }
      }
   }

   public AttributeGroupRef insertNewAttributeGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().insert_element_user(ATTRIBUTEGROUP$2, i);
         return target;
      }
   }

   public AttributeGroupRef addNewAttributeGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().add_element_user(ATTRIBUTEGROUP$2);
         return target;
      }
   }

   public void removeAttributeGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTRIBUTEGROUP$2, i);
      }
   }

   public Wildcard getAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().find_element_user((QName)ANYATTRIBUTE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANYATTRIBUTE$4) != 0;
      }
   }

   public void setAnyAttribute(Wildcard anyAttribute) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().find_element_user((QName)ANYATTRIBUTE$4, 0);
         if (target == null) {
            target = (Wildcard)this.get_store().add_element_user(ANYATTRIBUTE$4);
         }

         target.set(anyAttribute);
      }
   }

   public Wildcard addNewAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().add_element_user(ANYATTRIBUTE$4);
         return target;
      }
   }

   public void unsetAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)ANYATTRIBUTE$4, 0);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlNCName xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$6);
         return target;
      }
   }

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NAME$6) != null;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$6);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlNCName name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$6);
         if (target == null) {
            target = (XmlNCName)this.get_store().add_attribute_user(NAME$6);
         }

         target.set(name);
      }
   }

   public void unsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NAME$6);
      }
   }

   public QName getRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$8);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(REF$8);
         return target;
      }
   }

   public boolean isSetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(REF$8) != null;
      }
   }

   public void setRef(QName ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(REF$8);
         }

         target.setQNameValue(ref);
      }
   }

   public void xsetRef(XmlQName ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(REF$8);
         if (target == null) {
            target = (XmlQName)this.get_store().add_attribute_user(REF$8);
         }

         target.set(ref);
      }
   }

   public void unsetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(REF$8);
      }
   }
}
