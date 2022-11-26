package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.EjbNameType;
import com.sun.java.xml.ns.j2Ee.MethodIntfType;
import com.sun.java.xml.ns.j2Ee.MethodNameType;
import com.sun.java.xml.ns.j2Ee.MethodParamsType;
import com.sun.java.xml.ns.j2Ee.MethodType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class MethodTypeImpl extends XmlComplexContentImpl implements MethodType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName EJBNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-name");
   private static final QName METHODINTF$4 = new QName("http://java.sun.com/xml/ns/j2ee", "method-intf");
   private static final QName METHODNAME$6 = new QName("http://java.sun.com/xml/ns/j2ee", "method-name");
   private static final QName METHODPARAMS$8 = new QName("http://java.sun.com/xml/ns/j2ee", "method-params");
   private static final QName ID$10 = new QName("", "id");

   public MethodTypeImpl(SchemaType sType) {
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

   public EjbNameType getEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().find_element_user(EJBNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbName(EjbNameType ejbName) {
      this.generatedSetterHelperImpl(ejbName, EJBNAME$2, 0, (short)1);
   }

   public EjbNameType addNewEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().add_element_user(EJBNAME$2);
         return target;
      }
   }

   public MethodIntfType getMethodIntf() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodIntfType target = null;
         target = (MethodIntfType)this.get_store().find_element_user(METHODINTF$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMethodIntf() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(METHODINTF$4) != 0;
      }
   }

   public void setMethodIntf(MethodIntfType methodIntf) {
      this.generatedSetterHelperImpl(methodIntf, METHODINTF$4, 0, (short)1);
   }

   public MethodIntfType addNewMethodIntf() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodIntfType target = null;
         target = (MethodIntfType)this.get_store().add_element_user(METHODINTF$4);
         return target;
      }
   }

   public void unsetMethodIntf() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(METHODINTF$4, 0);
      }
   }

   public MethodNameType getMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodNameType target = null;
         target = (MethodNameType)this.get_store().find_element_user(METHODNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public void setMethodName(MethodNameType methodName) {
      this.generatedSetterHelperImpl(methodName, METHODNAME$6, 0, (short)1);
   }

   public MethodNameType addNewMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodNameType target = null;
         target = (MethodNameType)this.get_store().add_element_user(METHODNAME$6);
         return target;
      }
   }

   public MethodParamsType getMethodParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodParamsType target = null;
         target = (MethodParamsType)this.get_store().find_element_user(METHODPARAMS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMethodParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(METHODPARAMS$8) != 0;
      }
   }

   public void setMethodParams(MethodParamsType methodParams) {
      this.generatedSetterHelperImpl(methodParams, METHODPARAMS$8, 0, (short)1);
   }

   public MethodParamsType addNewMethodParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodParamsType target = null;
         target = (MethodParamsType)this.get_store().add_element_user(METHODPARAMS$8);
         return target;
      }
   }

   public void unsetMethodParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(METHODPARAMS$8, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$10) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$10);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$10);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$10);
      }
   }
}
