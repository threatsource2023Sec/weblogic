package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.IsolationLevelType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.MethodType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TransactionIsolationType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class TransactionIsolationTypeImpl extends XmlComplexContentImpl implements TransactionIsolationType {
   private static final long serialVersionUID = 1L;
   private static final QName ISOLATIONLEVEL$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "isolation-level");
   private static final QName METHOD$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "method");
   private static final QName ID$4 = new QName("", "id");

   public TransactionIsolationTypeImpl(SchemaType sType) {
      super(sType);
   }

   public IsolationLevelType getIsolationLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IsolationLevelType target = null;
         target = (IsolationLevelType)this.get_store().find_element_user(ISOLATIONLEVEL$0, 0);
         return target == null ? null : target;
      }
   }

   public void setIsolationLevel(IsolationLevelType isolationLevel) {
      this.generatedSetterHelperImpl(isolationLevel, ISOLATIONLEVEL$0, 0, (short)1);
   }

   public IsolationLevelType addNewIsolationLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IsolationLevelType target = null;
         target = (IsolationLevelType)this.get_store().add_element_user(ISOLATIONLEVEL$0);
         return target;
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
