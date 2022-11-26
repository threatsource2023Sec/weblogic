package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.xb.xsdschema.FieldDocument;
import com.bea.xbean.xb.xsdschema.Keybase;
import com.bea.xbean.xb.xsdschema.SelectorDocument;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlNCName;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class KeybaseImpl extends AnnotatedImpl implements Keybase {
   private static final QName SELECTOR$0 = new QName("http://www.w3.org/2001/XMLSchema", "selector");
   private static final QName FIELD$2 = new QName("http://www.w3.org/2001/XMLSchema", "field");
   private static final QName NAME$4 = new QName("", "name");

   public KeybaseImpl(SchemaType sType) {
      super(sType);
   }

   public SelectorDocument.Selector getSelector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SelectorDocument.Selector target = null;
         target = (SelectorDocument.Selector)this.get_store().find_element_user((QName)SELECTOR$0, 0);
         return target == null ? null : target;
      }
   }

   public void setSelector(SelectorDocument.Selector selector) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SelectorDocument.Selector target = null;
         target = (SelectorDocument.Selector)this.get_store().find_element_user((QName)SELECTOR$0, 0);
         if (target == null) {
            target = (SelectorDocument.Selector)this.get_store().add_element_user(SELECTOR$0);
         }

         target.set(selector);
      }
   }

   public SelectorDocument.Selector addNewSelector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SelectorDocument.Selector target = null;
         target = (SelectorDocument.Selector)this.get_store().add_element_user(SELECTOR$0);
         return target;
      }
   }

   public FieldDocument.Field[] getFieldArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)FIELD$2, targetList);
         FieldDocument.Field[] result = new FieldDocument.Field[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FieldDocument.Field getFieldArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldDocument.Field target = null;
         target = (FieldDocument.Field)this.get_store().find_element_user(FIELD$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFieldArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FIELD$2);
      }
   }

   public void setFieldArray(FieldDocument.Field[] fieldArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(fieldArray, FIELD$2);
      }
   }

   public void setFieldArray(int i, FieldDocument.Field field) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldDocument.Field target = null;
         target = (FieldDocument.Field)this.get_store().find_element_user(FIELD$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(field);
         }
      }
   }

   public FieldDocument.Field insertNewField(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldDocument.Field target = null;
         target = (FieldDocument.Field)this.get_store().insert_element_user(FIELD$2, i);
         return target;
      }
   }

   public FieldDocument.Field addNewField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldDocument.Field target = null;
         target = (FieldDocument.Field)this.get_store().add_element_user(FIELD$2);
         return target;
      }
   }

   public void removeField(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FIELD$2, i);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlNCName xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$4);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$4);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlNCName name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$4);
         if (target == null) {
            target = (XmlNCName)this.get_store().add_attribute_user(NAME$4);
         }

         target.set(name);
      }
   }
}
