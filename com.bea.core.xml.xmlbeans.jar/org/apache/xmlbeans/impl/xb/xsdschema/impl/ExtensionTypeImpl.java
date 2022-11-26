package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlQName;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.apache.xmlbeans.impl.xb.xsdschema.AttributeGroupRef;
import org.apache.xmlbeans.impl.xb.xsdschema.ExplicitGroup;
import org.apache.xmlbeans.impl.xb.xsdschema.ExtensionType;
import org.apache.xmlbeans.impl.xb.xsdschema.GroupRef;
import org.apache.xmlbeans.impl.xb.xsdschema.Wildcard;

public class ExtensionTypeImpl extends AnnotatedImpl implements ExtensionType {
   private static final QName GROUP$0 = new QName("http://www.w3.org/2001/XMLSchema", "group");
   private static final QName ALL$2 = new QName("http://www.w3.org/2001/XMLSchema", "all");
   private static final QName CHOICE$4 = new QName("http://www.w3.org/2001/XMLSchema", "choice");
   private static final QName SEQUENCE$6 = new QName("http://www.w3.org/2001/XMLSchema", "sequence");
   private static final QName ATTRIBUTE$8 = new QName("http://www.w3.org/2001/XMLSchema", "attribute");
   private static final QName ATTRIBUTEGROUP$10 = new QName("http://www.w3.org/2001/XMLSchema", "attributeGroup");
   private static final QName ANYATTRIBUTE$12 = new QName("http://www.w3.org/2001/XMLSchema", "anyAttribute");
   private static final QName BASE$14 = new QName("", "base");

   public ExtensionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public GroupRef getGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().find_element_user((QName)GROUP$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUP$0) != 0;
      }
   }

   public void setGroup(GroupRef group) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().find_element_user((QName)GROUP$0, 0);
         if (target == null) {
            target = (GroupRef)this.get_store().add_element_user(GROUP$0);
         }

         target.set(group);
      }
   }

   public GroupRef addNewGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().add_element_user(GROUP$0);
         return target;
      }
   }

   public void unsetGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)GROUP$0, 0);
      }
   }

   public All getAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().find_element_user((QName)ALL$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALL$2) != 0;
      }
   }

   public void setAll(All all) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().find_element_user((QName)ALL$2, 0);
         if (target == null) {
            target = (All)this.get_store().add_element_user(ALL$2);
         }

         target.set(all);
      }
   }

   public All addNewAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().add_element_user(ALL$2);
         return target;
      }
   }

   public void unsetAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)ALL$2, 0);
      }
   }

   public ExplicitGroup getChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)CHOICE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHOICE$4) != 0;
      }
   }

   public void setChoice(ExplicitGroup choice) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)CHOICE$4, 0);
         if (target == null) {
            target = (ExplicitGroup)this.get_store().add_element_user(CHOICE$4);
         }

         target.set(choice);
      }
   }

   public ExplicitGroup addNewChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().add_element_user(CHOICE$4);
         return target;
      }
   }

   public void unsetChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)CHOICE$4, 0);
      }
   }

   public ExplicitGroup getSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)SEQUENCE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SEQUENCE$6) != 0;
      }
   }

   public void setSequence(ExplicitGroup sequence) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)SEQUENCE$6, 0);
         if (target == null) {
            target = (ExplicitGroup)this.get_store().add_element_user(SEQUENCE$6);
         }

         target.set(sequence);
      }
   }

   public ExplicitGroup addNewSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().add_element_user(SEQUENCE$6);
         return target;
      }
   }

   public void unsetSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)SEQUENCE$6, 0);
      }
   }

   public Attribute[] getAttributeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ATTRIBUTE$8, targetList);
         Attribute[] result = new Attribute[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Attribute getAttributeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().find_element_user(ATTRIBUTE$8, i);
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
         return this.get_store().count_elements(ATTRIBUTE$8);
      }
   }

   public void setAttributeArray(Attribute[] attributeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(attributeArray, ATTRIBUTE$8);
      }
   }

   public void setAttributeArray(int i, Attribute attribute) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().find_element_user(ATTRIBUTE$8, i);
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
         target = (Attribute)this.get_store().insert_element_user(ATTRIBUTE$8, i);
         return target;
      }
   }

   public Attribute addNewAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().add_element_user(ATTRIBUTE$8);
         return target;
      }
   }

   public void removeAttribute(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTRIBUTE$8, i);
      }
   }

   public AttributeGroupRef[] getAttributeGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ATTRIBUTEGROUP$10, targetList);
         AttributeGroupRef[] result = new AttributeGroupRef[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AttributeGroupRef getAttributeGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().find_element_user(ATTRIBUTEGROUP$10, i);
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
         return this.get_store().count_elements(ATTRIBUTEGROUP$10);
      }
   }

   public void setAttributeGroupArray(AttributeGroupRef[] attributeGroupArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(attributeGroupArray, ATTRIBUTEGROUP$10);
      }
   }

   public void setAttributeGroupArray(int i, AttributeGroupRef attributeGroup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().find_element_user(ATTRIBUTEGROUP$10, i);
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
         target = (AttributeGroupRef)this.get_store().insert_element_user(ATTRIBUTEGROUP$10, i);
         return target;
      }
   }

   public AttributeGroupRef addNewAttributeGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().add_element_user(ATTRIBUTEGROUP$10);
         return target;
      }
   }

   public void removeAttributeGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTRIBUTEGROUP$10, i);
      }
   }

   public Wildcard getAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().find_element_user((QName)ANYATTRIBUTE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANYATTRIBUTE$12) != 0;
      }
   }

   public void setAnyAttribute(Wildcard anyAttribute) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().find_element_user((QName)ANYATTRIBUTE$12, 0);
         if (target == null) {
            target = (Wildcard)this.get_store().add_element_user(ANYATTRIBUTE$12);
         }

         target.set(anyAttribute);
      }
   }

   public Wildcard addNewAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().add_element_user(ANYATTRIBUTE$12);
         return target;
      }
   }

   public void unsetAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)ANYATTRIBUTE$12, 0);
      }
   }

   public QName getBase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(BASE$14);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetBase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(BASE$14);
         return target;
      }
   }

   public void setBase(QName base) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(BASE$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(BASE$14);
         }

         target.setQNameValue(base);
      }
   }

   public void xsetBase(XmlQName base) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(BASE$14);
         if (target == null) {
            target = (XmlQName)this.get_store().add_attribute_user(BASE$14);
         }

         target.set(base);
      }
   }
}
