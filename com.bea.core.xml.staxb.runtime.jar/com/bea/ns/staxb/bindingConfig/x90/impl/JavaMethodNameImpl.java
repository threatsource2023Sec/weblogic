package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.JavaClassName;
import com.bea.ns.staxb.bindingConfig.x90.JavaMethodName;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlToken;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class JavaMethodNameImpl extends XmlComplexContentImpl implements JavaMethodName {
   private static final long serialVersionUID = 1L;
   private static final QName METHODNAME$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "method-name");
   private static final QName PARAMTYPE$2 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "param-type");

   public JavaMethodNameImpl(SchemaType sType) {
      super(sType);
   }

   public String getMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(METHODNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlToken xgetMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_element_user(METHODNAME$0, 0);
         return target;
      }
   }

   public void setMethodName(String methodName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(METHODNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(METHODNAME$0);
         }

         target.setStringValue(methodName);
      }
   }

   public void xsetMethodName(XmlToken methodName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_element_user(METHODNAME$0, 0);
         if (target == null) {
            target = (XmlToken)this.get_store().add_element_user(METHODNAME$0);
         }

         target.set(methodName);
      }
   }

   public String[] getParamTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PARAMTYPE$2, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getParamTypeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARAMTYPE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public JavaClassName[] xgetParamTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PARAMTYPE$2, targetList);
         JavaClassName[] result = new JavaClassName[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JavaClassName xgetParamTypeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaClassName target = null;
         target = (JavaClassName)this.get_store().find_element_user(PARAMTYPE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfParamTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PARAMTYPE$2);
      }
   }

   public void setParamTypeArray(String[] paramTypeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(paramTypeArray, PARAMTYPE$2);
      }
   }

   public void setParamTypeArray(int i, String paramType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARAMTYPE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(paramType);
         }
      }
   }

   public void xsetParamTypeArray(JavaClassName[] paramTypeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(paramTypeArray, PARAMTYPE$2);
      }
   }

   public void xsetParamTypeArray(int i, JavaClassName paramType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaClassName target = null;
         target = (JavaClassName)this.get_store().find_element_user(PARAMTYPE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(paramType);
         }
      }
   }

   public void insertParamType(int i, String paramType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(PARAMTYPE$2, i);
         target.setStringValue(paramType);
      }
   }

   public void addParamType(String paramType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(PARAMTYPE$2);
         target.setStringValue(paramType);
      }
   }

   public JavaClassName insertNewParamType(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaClassName target = null;
         target = (JavaClassName)this.get_store().insert_element_user(PARAMTYPE$2, i);
         return target;
      }
   }

   public JavaClassName addNewParamType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaClassName target = null;
         target = (JavaClassName)this.get_store().add_element_user(PARAMTYPE$2);
         return target;
      }
   }

   public void removeParamType(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PARAMTYPE$2, i);
      }
   }
}
