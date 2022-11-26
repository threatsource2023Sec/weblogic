package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.MethodParamsType;
import com.sun.java.xml.ns.javaee.JavaTypeType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class MethodParamsTypeImpl extends XmlComplexContentImpl implements MethodParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName METHODPARAM$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "method-param");
   private static final QName ID$2 = new QName("", "id");

   public MethodParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public JavaTypeType[] getMethodParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(METHODPARAM$0, targetList);
         JavaTypeType[] result = new JavaTypeType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JavaTypeType getMethodParamArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaTypeType target = null;
         target = (JavaTypeType)this.get_store().find_element_user(METHODPARAM$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMethodParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(METHODPARAM$0);
      }
   }

   public void setMethodParamArray(JavaTypeType[] methodParamArray) {
      this.check_orphaned();
      this.arraySetterHelper(methodParamArray, METHODPARAM$0);
   }

   public void setMethodParamArray(int i, JavaTypeType methodParam) {
      this.generatedSetterHelperImpl(methodParam, METHODPARAM$0, i, (short)2);
   }

   public JavaTypeType insertNewMethodParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaTypeType target = null;
         target = (JavaTypeType)this.get_store().insert_element_user(METHODPARAM$0, i);
         return target;
      }
   }

   public JavaTypeType addNewMethodParam() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaTypeType target = null;
         target = (JavaTypeType)this.get_store().add_element_user(METHODPARAM$0);
         return target;
      }
   }

   public void removeMethodParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(METHODPARAM$0, i);
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
