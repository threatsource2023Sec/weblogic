package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.xb.xsdschema.All;
import com.bea.xbean.xb.xsdschema.AllNNI;
import com.bea.xbean.xb.xsdschema.AnyDocument;
import com.bea.xbean.xb.xsdschema.ExplicitGroup;
import com.bea.xbean.xb.xsdschema.Group;
import com.bea.xbean.xb.xsdschema.GroupRef;
import com.bea.xbean.xb.xsdschema.LocalElement;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlNCName;
import com.bea.xml.XmlNonNegativeInteger;
import com.bea.xml.XmlQName;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class GroupImpl extends AnnotatedImpl implements Group {
   private static final QName ELEMENT$0 = new QName("http://www.w3.org/2001/XMLSchema", "element");
   private static final QName GROUP$2 = new QName("http://www.w3.org/2001/XMLSchema", "group");
   private static final QName ALL$4 = new QName("http://www.w3.org/2001/XMLSchema", "all");
   private static final QName CHOICE$6 = new QName("http://www.w3.org/2001/XMLSchema", "choice");
   private static final QName SEQUENCE$8 = new QName("http://www.w3.org/2001/XMLSchema", "sequence");
   private static final QName ANY$10 = new QName("http://www.w3.org/2001/XMLSchema", "any");
   private static final QName NAME$12 = new QName("", "name");
   private static final QName REF$14 = new QName("", "ref");
   private static final QName MINOCCURS$16 = new QName("", "minOccurs");
   private static final QName MAXOCCURS$18 = new QName("", "maxOccurs");

   public GroupImpl(SchemaType sType) {
      super(sType);
   }

   public LocalElement[] getElementArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ELEMENT$0, targetList);
         LocalElement[] result = new LocalElement[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LocalElement getElementArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalElement target = null;
         target = (LocalElement)this.get_store().find_element_user(ELEMENT$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfElementArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ELEMENT$0);
      }
   }

   public void setElementArray(LocalElement[] elementArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(elementArray, ELEMENT$0);
      }
   }

   public void setElementArray(int i, LocalElement element) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalElement target = null;
         target = (LocalElement)this.get_store().find_element_user(ELEMENT$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(element);
         }
      }
   }

   public LocalElement insertNewElement(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalElement target = null;
         target = (LocalElement)this.get_store().insert_element_user(ELEMENT$0, i);
         return target;
      }
   }

   public LocalElement addNewElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalElement target = null;
         target = (LocalElement)this.get_store().add_element_user(ELEMENT$0);
         return target;
      }
   }

   public void removeElement(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ELEMENT$0, i);
      }
   }

   public GroupRef[] getGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)GROUP$2, targetList);
         GroupRef[] result = new GroupRef[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public GroupRef getGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().find_element_user(GROUP$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUP$2);
      }
   }

   public void setGroupArray(GroupRef[] groupArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(groupArray, GROUP$2);
      }
   }

   public void setGroupArray(int i, GroupRef group) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().find_element_user(GROUP$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(group);
         }
      }
   }

   public GroupRef insertNewGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().insert_element_user(GROUP$2, i);
         return target;
      }
   }

   public GroupRef addNewGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().add_element_user(GROUP$2);
         return target;
      }
   }

   public void removeGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GROUP$2, i);
      }
   }

   public All[] getAllArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ALL$4, targetList);
         All[] result = new All[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public All getAllArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().find_element_user(ALL$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAllArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALL$4);
      }
   }

   public void setAllArray(All[] allArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(allArray, ALL$4);
      }
   }

   public void setAllArray(int i, All all) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().find_element_user(ALL$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(all);
         }
      }
   }

   public All insertNewAll(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().insert_element_user(ALL$4, i);
         return target;
      }
   }

   public All addNewAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().add_element_user(ALL$4);
         return target;
      }
   }

   public void removeAll(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ALL$4, i);
      }
   }

   public ExplicitGroup[] getChoiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)CHOICE$6, targetList);
         ExplicitGroup[] result = new ExplicitGroup[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ExplicitGroup getChoiceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user(CHOICE$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfChoiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHOICE$6);
      }
   }

   public void setChoiceArray(ExplicitGroup[] choiceArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(choiceArray, CHOICE$6);
      }
   }

   public void setChoiceArray(int i, ExplicitGroup choice) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user(CHOICE$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(choice);
         }
      }
   }

   public ExplicitGroup insertNewChoice(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().insert_element_user(CHOICE$6, i);
         return target;
      }
   }

   public ExplicitGroup addNewChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().add_element_user(CHOICE$6);
         return target;
      }
   }

   public void removeChoice(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHOICE$6, i);
      }
   }

   public ExplicitGroup[] getSequenceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)SEQUENCE$8, targetList);
         ExplicitGroup[] result = new ExplicitGroup[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ExplicitGroup getSequenceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user(SEQUENCE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSequenceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SEQUENCE$8);
      }
   }

   public void setSequenceArray(ExplicitGroup[] sequenceArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(sequenceArray, SEQUENCE$8);
      }
   }

   public void setSequenceArray(int i, ExplicitGroup sequence) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user(SEQUENCE$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(sequence);
         }
      }
   }

   public ExplicitGroup insertNewSequence(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().insert_element_user(SEQUENCE$8, i);
         return target;
      }
   }

   public ExplicitGroup addNewSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().add_element_user(SEQUENCE$8);
         return target;
      }
   }

   public void removeSequence(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SEQUENCE$8, i);
      }
   }

   public AnyDocument.Any[] getAnyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ANY$10, targetList);
         AnyDocument.Any[] result = new AnyDocument.Any[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AnyDocument.Any getAnyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnyDocument.Any target = null;
         target = (AnyDocument.Any)this.get_store().find_element_user(ANY$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAnyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANY$10);
      }
   }

   public void setAnyArray(AnyDocument.Any[] anyArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(anyArray, ANY$10);
      }
   }

   public void setAnyArray(int i, AnyDocument.Any any) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnyDocument.Any target = null;
         target = (AnyDocument.Any)this.get_store().find_element_user(ANY$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(any);
         }
      }
   }

   public AnyDocument.Any insertNewAny(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnyDocument.Any target = null;
         target = (AnyDocument.Any)this.get_store().insert_element_user(ANY$10, i);
         return target;
      }
   }

   public AnyDocument.Any addNewAny() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnyDocument.Any target = null;
         target = (AnyDocument.Any)this.get_store().add_element_user(ANY$10);
         return target;
      }
   }

   public void removeAny(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ANY$10, i);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$12);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlNCName xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$12);
         return target;
      }
   }

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NAME$12) != null;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$12);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlNCName name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$12);
         if (target == null) {
            target = (XmlNCName)this.get_store().add_attribute_user(NAME$12);
         }

         target.set(name);
      }
   }

   public void unsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NAME$12);
      }
   }

   public QName getRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$14);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(REF$14);
         return target;
      }
   }

   public boolean isSetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(REF$14) != null;
      }
   }

   public void setRef(QName ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(REF$14);
         }

         target.setQNameValue(ref);
      }
   }

   public void xsetRef(XmlQName ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(REF$14);
         if (target == null) {
            target = (XmlQName)this.get_store().add_attribute_user(REF$14);
         }

         target.set(ref);
      }
   }

   public void unsetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(REF$14);
      }
   }

   public BigInteger getMinOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(MINOCCURS$16);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(MINOCCURS$16);
         }

         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlNonNegativeInteger xgetMinOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNonNegativeInteger target = null;
         target = (XmlNonNegativeInteger)this.get_store().find_attribute_user(MINOCCURS$16);
         if (target == null) {
            target = (XmlNonNegativeInteger)this.get_default_attribute_value(MINOCCURS$16);
         }

         return target;
      }
   }

   public boolean isSetMinOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(MINOCCURS$16) != null;
      }
   }

   public void setMinOccurs(BigInteger minOccurs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(MINOCCURS$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(MINOCCURS$16);
         }

         target.setBigIntegerValue(minOccurs);
      }
   }

   public void xsetMinOccurs(XmlNonNegativeInteger minOccurs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNonNegativeInteger target = null;
         target = (XmlNonNegativeInteger)this.get_store().find_attribute_user(MINOCCURS$16);
         if (target == null) {
            target = (XmlNonNegativeInteger)this.get_store().add_attribute_user(MINOCCURS$16);
         }

         target.set(minOccurs);
      }
   }

   public void unsetMinOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(MINOCCURS$16);
      }
   }

   public Object getMaxOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(MAXOCCURS$18);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(MAXOCCURS$18);
         }

         return target == null ? null : target.getObjectValue();
      }
   }

   public AllNNI xgetMaxOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AllNNI target = null;
         target = (AllNNI)this.get_store().find_attribute_user(MAXOCCURS$18);
         if (target == null) {
            target = (AllNNI)this.get_default_attribute_value(MAXOCCURS$18);
         }

         return target;
      }
   }

   public boolean isSetMaxOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(MAXOCCURS$18) != null;
      }
   }

   public void setMaxOccurs(Object maxOccurs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(MAXOCCURS$18);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(MAXOCCURS$18);
         }

         target.setObjectValue(maxOccurs);
      }
   }

   public void xsetMaxOccurs(AllNNI maxOccurs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AllNNI target = null;
         target = (AllNNI)this.get_store().find_attribute_user(MAXOCCURS$18);
         if (target == null) {
            target = (AllNNI)this.get_store().add_attribute_user(MAXOCCURS$18);
         }

         target.set(maxOccurs);
      }
   }

   public void unsetMaxOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(MAXOCCURS$18);
      }
   }
}
