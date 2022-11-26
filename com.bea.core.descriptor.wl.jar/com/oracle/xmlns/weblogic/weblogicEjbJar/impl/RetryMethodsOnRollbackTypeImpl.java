package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.MethodType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.RetryMethodsOnRollbackType;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class RetryMethodsOnRollbackTypeImpl extends XmlComplexContentImpl implements RetryMethodsOnRollbackType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "description");
   private static final QName RETRYCOUNT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "retry-count");
   private static final QName METHOD$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "method");
   private static final QName ID$6 = new QName("", "id");

   public RetryMethodsOnRollbackTypeImpl(SchemaType sType) {
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

   public XsdNonNegativeIntegerType getRetryCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(RETRYCOUNT$2, 0);
         return target == null ? null : target;
      }
   }

   public void setRetryCount(XsdNonNegativeIntegerType retryCount) {
      this.generatedSetterHelperImpl(retryCount, RETRYCOUNT$2, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewRetryCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(RETRYCOUNT$2);
         return target;
      }
   }

   public MethodType[] getMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(METHOD$4, targetList);
         MethodType[] result = new MethodType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MethodType getMethodArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodType target = null;
         target = (MethodType)this.get_store().find_element_user(METHOD$4, i);
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
         return this.get_store().count_elements(METHOD$4);
      }
   }

   public void setMethodArray(MethodType[] methodArray) {
      this.check_orphaned();
      this.arraySetterHelper(methodArray, METHOD$4);
   }

   public void setMethodArray(int i, MethodType method) {
      this.generatedSetterHelperImpl(method, METHOD$4, i, (short)2);
   }

   public MethodType insertNewMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodType target = null;
         target = (MethodType)this.get_store().insert_element_user(METHOD$4, i);
         return target;
      }
   }

   public MethodType addNewMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodType target = null;
         target = (MethodType)this.get_store().add_element_user(METHOD$4);
         return target;
      }
   }

   public void removeMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(METHOD$4, i);
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
