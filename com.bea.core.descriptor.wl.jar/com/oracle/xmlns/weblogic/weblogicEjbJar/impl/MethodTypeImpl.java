package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.MethodParamsType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.MethodType;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.EjbNameType;
import com.sun.java.xml.ns.javaee.MethodIntfType;
import com.sun.java.xml.ns.javaee.MethodNameType;
import javax.xml.namespace.QName;

public class MethodTypeImpl extends XmlComplexContentImpl implements MethodType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "description");
   private static final QName EJBNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "ejb-name");
   private static final QName METHODINTF$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "method-intf");
   private static final QName METHODNAME$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "method-name");
   private static final QName METHODPARAMS$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "method-params");
   private static final QName ID$10 = new QName("", "id");

   public MethodTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0) != 0;
      }
   }

   public void setDescription(DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, 0, (short)1);
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, 0);
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
