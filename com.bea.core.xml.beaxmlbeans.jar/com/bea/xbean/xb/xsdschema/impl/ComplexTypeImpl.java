package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.xb.xsdschema.All;
import com.bea.xbean.xb.xsdschema.Attribute;
import com.bea.xbean.xb.xsdschema.AttributeGroupRef;
import com.bea.xbean.xb.xsdschema.ComplexContentDocument;
import com.bea.xbean.xb.xsdschema.ComplexType;
import com.bea.xbean.xb.xsdschema.DerivationSet;
import com.bea.xbean.xb.xsdschema.ExplicitGroup;
import com.bea.xbean.xb.xsdschema.GroupRef;
import com.bea.xbean.xb.xsdschema.SimpleContentDocument;
import com.bea.xbean.xb.xsdschema.Wildcard;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlNCName;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ComplexTypeImpl extends AnnotatedImpl implements ComplexType {
   private static final QName SIMPLECONTENT$0 = new QName("http://www.w3.org/2001/XMLSchema", "simpleContent");
   private static final QName COMPLEXCONTENT$2 = new QName("http://www.w3.org/2001/XMLSchema", "complexContent");
   private static final QName GROUP$4 = new QName("http://www.w3.org/2001/XMLSchema", "group");
   private static final QName ALL$6 = new QName("http://www.w3.org/2001/XMLSchema", "all");
   private static final QName CHOICE$8 = new QName("http://www.w3.org/2001/XMLSchema", "choice");
   private static final QName SEQUENCE$10 = new QName("http://www.w3.org/2001/XMLSchema", "sequence");
   private static final QName ATTRIBUTE$12 = new QName("http://www.w3.org/2001/XMLSchema", "attribute");
   private static final QName ATTRIBUTEGROUP$14 = new QName("http://www.w3.org/2001/XMLSchema", "attributeGroup");
   private static final QName ANYATTRIBUTE$16 = new QName("http://www.w3.org/2001/XMLSchema", "anyAttribute");
   private static final QName NAME$18 = new QName("", "name");
   private static final QName MIXED$20 = new QName("", "mixed");
   private static final QName ABSTRACT$22 = new QName("", "abstract");
   private static final QName FINAL$24 = new QName("", "final");
   private static final QName BLOCK$26 = new QName("", "block");

   public ComplexTypeImpl(SchemaType sType) {
      super(sType);
   }

   public SimpleContentDocument.SimpleContent getSimpleContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleContentDocument.SimpleContent target = null;
         target = (SimpleContentDocument.SimpleContent)this.get_store().find_element_user((QName)SIMPLECONTENT$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSimpleContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SIMPLECONTENT$0) != 0;
      }
   }

   public void setSimpleContent(SimpleContentDocument.SimpleContent simpleContent) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleContentDocument.SimpleContent target = null;
         target = (SimpleContentDocument.SimpleContent)this.get_store().find_element_user((QName)SIMPLECONTENT$0, 0);
         if (target == null) {
            target = (SimpleContentDocument.SimpleContent)this.get_store().add_element_user(SIMPLECONTENT$0);
         }

         target.set(simpleContent);
      }
   }

   public SimpleContentDocument.SimpleContent addNewSimpleContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleContentDocument.SimpleContent target = null;
         target = (SimpleContentDocument.SimpleContent)this.get_store().add_element_user(SIMPLECONTENT$0);
         return target;
      }
   }

   public void unsetSimpleContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)SIMPLECONTENT$0, 0);
      }
   }

   public ComplexContentDocument.ComplexContent getComplexContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ComplexContentDocument.ComplexContent target = null;
         target = (ComplexContentDocument.ComplexContent)this.get_store().find_element_user((QName)COMPLEXCONTENT$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetComplexContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMPLEXCONTENT$2) != 0;
      }
   }

   public void setComplexContent(ComplexContentDocument.ComplexContent complexContent) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ComplexContentDocument.ComplexContent target = null;
         target = (ComplexContentDocument.ComplexContent)this.get_store().find_element_user((QName)COMPLEXCONTENT$2, 0);
         if (target == null) {
            target = (ComplexContentDocument.ComplexContent)this.get_store().add_element_user(COMPLEXCONTENT$2);
         }

         target.set(complexContent);
      }
   }

   public ComplexContentDocument.ComplexContent addNewComplexContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ComplexContentDocument.ComplexContent target = null;
         target = (ComplexContentDocument.ComplexContent)this.get_store().add_element_user(COMPLEXCONTENT$2);
         return target;
      }
   }

   public void unsetComplexContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)COMPLEXCONTENT$2, 0);
      }
   }

   public GroupRef getGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().find_element_user((QName)GROUP$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUP$4) != 0;
      }
   }

   public void setGroup(GroupRef group) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().find_element_user((QName)GROUP$4, 0);
         if (target == null) {
            target = (GroupRef)this.get_store().add_element_user(GROUP$4);
         }

         target.set(group);
      }
   }

   public GroupRef addNewGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupRef target = null;
         target = (GroupRef)this.get_store().add_element_user(GROUP$4);
         return target;
      }
   }

   public void unsetGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)GROUP$4, 0);
      }
   }

   public All getAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().find_element_user((QName)ALL$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALL$6) != 0;
      }
   }

   public void setAll(All all) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().find_element_user((QName)ALL$6, 0);
         if (target == null) {
            target = (All)this.get_store().add_element_user(ALL$6);
         }

         target.set(all);
      }
   }

   public All addNewAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         All target = null;
         target = (All)this.get_store().add_element_user(ALL$6);
         return target;
      }
   }

   public void unsetAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)ALL$6, 0);
      }
   }

   public ExplicitGroup getChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)CHOICE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHOICE$8) != 0;
      }
   }

   public void setChoice(ExplicitGroup choice) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)CHOICE$8, 0);
         if (target == null) {
            target = (ExplicitGroup)this.get_store().add_element_user(CHOICE$8);
         }

         target.set(choice);
      }
   }

   public ExplicitGroup addNewChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().add_element_user(CHOICE$8);
         return target;
      }
   }

   public void unsetChoice() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)CHOICE$8, 0);
      }
   }

   public ExplicitGroup getSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)SEQUENCE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SEQUENCE$10) != 0;
      }
   }

   public void setSequence(ExplicitGroup sequence) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().find_element_user((QName)SEQUENCE$10, 0);
         if (target == null) {
            target = (ExplicitGroup)this.get_store().add_element_user(SEQUENCE$10);
         }

         target.set(sequence);
      }
   }

   public ExplicitGroup addNewSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExplicitGroup target = null;
         target = (ExplicitGroup)this.get_store().add_element_user(SEQUENCE$10);
         return target;
      }
   }

   public void unsetSequence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)SEQUENCE$10, 0);
      }
   }

   public Attribute[] getAttributeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ATTRIBUTE$12, targetList);
         Attribute[] result = new Attribute[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Attribute getAttributeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().find_element_user(ATTRIBUTE$12, i);
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
         return this.get_store().count_elements(ATTRIBUTE$12);
      }
   }

   public void setAttributeArray(Attribute[] attributeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(attributeArray, ATTRIBUTE$12);
      }
   }

   public void setAttributeArray(int i, Attribute attribute) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().find_element_user(ATTRIBUTE$12, i);
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
         target = (Attribute)this.get_store().insert_element_user(ATTRIBUTE$12, i);
         return target;
      }
   }

   public Attribute addNewAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Attribute target = null;
         target = (Attribute)this.get_store().add_element_user(ATTRIBUTE$12);
         return target;
      }
   }

   public void removeAttribute(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTRIBUTE$12, i);
      }
   }

   public AttributeGroupRef[] getAttributeGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)ATTRIBUTEGROUP$14, targetList);
         AttributeGroupRef[] result = new AttributeGroupRef[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AttributeGroupRef getAttributeGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().find_element_user(ATTRIBUTEGROUP$14, i);
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
         return this.get_store().count_elements(ATTRIBUTEGROUP$14);
      }
   }

   public void setAttributeGroupArray(AttributeGroupRef[] attributeGroupArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(attributeGroupArray, ATTRIBUTEGROUP$14);
      }
   }

   public void setAttributeGroupArray(int i, AttributeGroupRef attributeGroup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().find_element_user(ATTRIBUTEGROUP$14, i);
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
         target = (AttributeGroupRef)this.get_store().insert_element_user(ATTRIBUTEGROUP$14, i);
         return target;
      }
   }

   public AttributeGroupRef addNewAttributeGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AttributeGroupRef target = null;
         target = (AttributeGroupRef)this.get_store().add_element_user(ATTRIBUTEGROUP$14);
         return target;
      }
   }

   public void removeAttributeGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTRIBUTEGROUP$14, i);
      }
   }

   public Wildcard getAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().find_element_user((QName)ANYATTRIBUTE$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANYATTRIBUTE$16) != 0;
      }
   }

   public void setAnyAttribute(Wildcard anyAttribute) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().find_element_user((QName)ANYATTRIBUTE$16, 0);
         if (target == null) {
            target = (Wildcard)this.get_store().add_element_user(ANYATTRIBUTE$16);
         }

         target.set(anyAttribute);
      }
   }

   public Wildcard addNewAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard target = null;
         target = (Wildcard)this.get_store().add_element_user(ANYATTRIBUTE$16);
         return target;
      }
   }

   public void unsetAnyAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)ANYATTRIBUTE$16, 0);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$18);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlNCName xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$18);
         return target;
      }
   }

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NAME$18) != null;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$18);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$18);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlNCName name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$18);
         if (target == null) {
            target = (XmlNCName)this.get_store().add_attribute_user(NAME$18);
         }

         target.set(name);
      }
   }

   public void unsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NAME$18);
      }
   }

   public boolean getMixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(MIXED$20);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(MIXED$20);
         }

         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(MIXED$20);
         if (target == null) {
            target = (XmlBoolean)this.get_default_attribute_value(MIXED$20);
         }

         return target;
      }
   }

   public boolean isSetMixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(MIXED$20) != null;
      }
   }

   public void setMixed(boolean mixed) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(MIXED$20);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(MIXED$20);
         }

         target.setBooleanValue(mixed);
      }
   }

   public void xsetMixed(XmlBoolean mixed) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(MIXED$20);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(MIXED$20);
         }

         target.set(mixed);
      }
   }

   public void unsetMixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(MIXED$20);
      }
   }

   public boolean getAbstract() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ABSTRACT$22);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(ABSTRACT$22);
         }

         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetAbstract() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(ABSTRACT$22);
         if (target == null) {
            target = (XmlBoolean)this.get_default_attribute_value(ABSTRACT$22);
         }

         return target;
      }
   }

   public boolean isSetAbstract() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ABSTRACT$22) != null;
      }
   }

   public void setAbstract(boolean xabstract) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ABSTRACT$22);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ABSTRACT$22);
         }

         target.setBooleanValue(xabstract);
      }
   }

   public void xsetAbstract(XmlBoolean xabstract) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(ABSTRACT$22);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(ABSTRACT$22);
         }

         target.set(xabstract);
      }
   }

   public void unsetAbstract() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ABSTRACT$22);
      }
   }

   public Object getFinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FINAL$24);
         return target == null ? null : target.getObjectValue();
      }
   }

   public DerivationSet xgetFinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DerivationSet target = null;
         target = (DerivationSet)this.get_store().find_attribute_user(FINAL$24);
         return target;
      }
   }

   public boolean isSetFinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(FINAL$24) != null;
      }
   }

   public void setFinal(Object xfinal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FINAL$24);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(FINAL$24);
         }

         target.setObjectValue(xfinal);
      }
   }

   public void xsetFinal(DerivationSet xfinal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DerivationSet target = null;
         target = (DerivationSet)this.get_store().find_attribute_user(FINAL$24);
         if (target == null) {
            target = (DerivationSet)this.get_store().add_attribute_user(FINAL$24);
         }

         target.set(xfinal);
      }
   }

   public void unsetFinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(FINAL$24);
      }
   }

   public Object getBlock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(BLOCK$26);
         return target == null ? null : target.getObjectValue();
      }
   }

   public DerivationSet xgetBlock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DerivationSet target = null;
         target = (DerivationSet)this.get_store().find_attribute_user(BLOCK$26);
         return target;
      }
   }

   public boolean isSetBlock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(BLOCK$26) != null;
      }
   }

   public void setBlock(Object block) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(BLOCK$26);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(BLOCK$26);
         }

         target.setObjectValue(block);
      }
   }

   public void xsetBlock(DerivationSet block) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DerivationSet target = null;
         target = (DerivationSet)this.get_store().find_attribute_user(BLOCK$26);
         if (target == null) {
            target = (DerivationSet)this.get_store().add_attribute_user(BLOCK$26);
         }

         target.set(block);
      }
   }

   public void unsetBlock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(BLOCK$26);
      }
   }
}
