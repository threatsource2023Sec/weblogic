package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.ContainerTransactionType;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.MethodType;
import com.sun.java.xml.ns.j2Ee.TransAttributeType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ContainerTransactionTypeImpl extends XmlComplexContentImpl implements ContainerTransactionType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName METHOD$2 = new QName("http://java.sun.com/xml/ns/j2ee", "method");
   private static final QName TRANSATTRIBUTE$4 = new QName("http://java.sun.com/xml/ns/j2ee", "trans-attribute");
   private static final QName ID$6 = new QName("", "id");

   public ContainerTransactionTypeImpl(SchemaType sType) {
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

   public MethodType[] getMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(METHOD$2, targetList);
         MethodType[] result = new MethodType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MethodType getMethodArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodType target = null;
         target = (MethodType)this.get_store().find_element_user(METHOD$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(METHOD$2);
      }
   }

   public void setMethodArray(MethodType[] methodArray) {
      this.check_orphaned();
      this.arraySetterHelper(methodArray, METHOD$2);
   }

   public void setMethodArray(int i, MethodType method) {
      this.generatedSetterHelperImpl(method, METHOD$2, i, (short)2);
   }

   public MethodType insertNewMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodType target = null;
         target = (MethodType)this.get_store().insert_element_user(METHOD$2, i);
         return target;
      }
   }

   public MethodType addNewMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodType target = null;
         target = (MethodType)this.get_store().add_element_user(METHOD$2);
         return target;
      }
   }

   public void removeMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(METHOD$2, i);
      }
   }

   public TransAttributeType getTransAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransAttributeType target = null;
         target = (TransAttributeType)this.get_store().find_element_user(TRANSATTRIBUTE$4, 0);
         return target == null ? null : target;
      }
   }

   public void setTransAttribute(TransAttributeType transAttribute) {
      this.generatedSetterHelperImpl(transAttribute, TRANSATTRIBUTE$4, 0, (short)1);
   }

   public TransAttributeType addNewTransAttribute() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransAttributeType target = null;
         target = (TransAttributeType)this.get_store().add_element_user(TRANSATTRIBUTE$4);
         return target;
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
