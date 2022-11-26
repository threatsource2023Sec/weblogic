package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.NamedMethodType;
import com.sun.java.xml.ns.javaee.RemoveMethodType;
import com.sun.java.xml.ns.javaee.TrueFalseType;
import javax.xml.namespace.QName;

public class RemoveMethodTypeImpl extends XmlComplexContentImpl implements RemoveMethodType {
   private static final long serialVersionUID = 1L;
   private static final QName BEANMETHOD$0 = new QName("http://java.sun.com/xml/ns/javaee", "bean-method");
   private static final QName RETAINIFEXCEPTION$2 = new QName("http://java.sun.com/xml/ns/javaee", "retain-if-exception");
   private static final QName ID$4 = new QName("", "id");

   public RemoveMethodTypeImpl(SchemaType sType) {
      super(sType);
   }

   public NamedMethodType getBeanMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().find_element_user(BEANMETHOD$0, 0);
         return target == null ? null : target;
      }
   }

   public void setBeanMethod(NamedMethodType beanMethod) {
      this.generatedSetterHelperImpl(beanMethod, BEANMETHOD$0, 0, (short)1);
   }

   public NamedMethodType addNewBeanMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().add_element_user(BEANMETHOD$0);
         return target;
      }
   }

   public TrueFalseType getRetainIfException() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(RETAINIFEXCEPTION$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRetainIfException() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RETAINIFEXCEPTION$2) != 0;
      }
   }

   public void setRetainIfException(TrueFalseType retainIfException) {
      this.generatedSetterHelperImpl(retainIfException, RETAINIFEXCEPTION$2, 0, (short)1);
   }

   public TrueFalseType addNewRetainIfException() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(RETAINIFEXCEPTION$2);
         return target;
      }
   }

   public void unsetRetainIfException() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RETAINIFEXCEPTION$2, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
