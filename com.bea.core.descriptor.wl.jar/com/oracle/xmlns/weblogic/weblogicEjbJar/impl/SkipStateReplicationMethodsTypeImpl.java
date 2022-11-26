package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.MethodType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.SkipStateReplicationMethodsType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SkipStateReplicationMethodsTypeImpl extends XmlComplexContentImpl implements SkipStateReplicationMethodsType {
   private static final long serialVersionUID = 1L;
   private static final QName METHOD$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "method");
   private static final QName ID$2 = new QName("", "id");

   public SkipStateReplicationMethodsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public MethodType[] getMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(METHOD$0, targetList);
         MethodType[] result = new MethodType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MethodType getMethodArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodType target = null;
         target = (MethodType)this.get_store().find_element_user(METHOD$0, i);
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
         return this.get_store().count_elements(METHOD$0);
      }
   }

   public void setMethodArray(MethodType[] methodArray) {
      this.check_orphaned();
      this.arraySetterHelper(methodArray, METHOD$0);
   }

   public void setMethodArray(int i, MethodType method) {
      this.generatedSetterHelperImpl(method, METHOD$0, i, (short)2);
   }

   public MethodType insertNewMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodType target = null;
         target = (MethodType)this.get_store().insert_element_user(METHOD$0, i);
         return target;
      }
   }

   public MethodType addNewMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MethodType target = null;
         target = (MethodType)this.get_store().add_element_user(METHOD$0);
         return target;
      }
   }

   public void removeMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(METHOD$0, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$2) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$2);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$2);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$2);
      }
   }
}
