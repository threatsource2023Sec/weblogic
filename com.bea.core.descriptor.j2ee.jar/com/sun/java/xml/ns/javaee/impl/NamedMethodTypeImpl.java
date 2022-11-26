package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.MethodParamsType;
import com.sun.java.xml.ns.javaee.NamedMethodType;
import com.sun.java.xml.ns.javaee.String;
import javax.xml.namespace.QName;

public class NamedMethodTypeImpl extends XmlComplexContentImpl implements NamedMethodType {
   private static final long serialVersionUID = 1L;
   private static final QName METHODNAME$0 = new QName("http://java.sun.com/xml/ns/javaee", "method-name");
   private static final QName METHODPARAMS$2 = new QName("http://java.sun.com/xml/ns/javaee", "method-params");
   private static final QName ID$4 = new QName("", "id");

   public NamedMethodTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(METHODNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMethodName(String methodName) {
      this.generatedSetterHelperImpl(methodName, METHODNAME$0, 0, (short)1);
   }

   public String addNewMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(METHODNAME$0);
         return target;
      }
   }

   public MethodParamsType getMethodParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodParamsType target = null;
         target = (MethodParamsType)this.get_store().find_element_user(METHODPARAMS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMethodParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(METHODPARAMS$2) != 0;
      }
   }

   public void setMethodParams(MethodParamsType methodParams) {
      this.generatedSetterHelperImpl(methodParams, METHODPARAMS$2, 0, (short)1);
   }

   public MethodParamsType addNewMethodParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodParamsType target = null;
         target = (MethodParamsType)this.get_store().add_element_user(METHODPARAMS$2);
         return target;
      }
   }

   public void unsetMethodParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(METHODPARAMS$2, 0);
      }
   }

   public java.lang.String getId() {
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

   public void setId(java.lang.String id) {
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
