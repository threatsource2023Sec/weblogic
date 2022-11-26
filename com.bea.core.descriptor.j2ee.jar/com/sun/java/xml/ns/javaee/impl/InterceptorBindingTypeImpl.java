package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.FullyQualifiedClassType;
import com.sun.java.xml.ns.javaee.InterceptorBindingType;
import com.sun.java.xml.ns.javaee.InterceptorOrderType;
import com.sun.java.xml.ns.javaee.NamedMethodType;
import com.sun.java.xml.ns.javaee.String;
import com.sun.java.xml.ns.javaee.TrueFalseType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class InterceptorBindingTypeImpl extends XmlComplexContentImpl implements InterceptorBindingType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/javaee", "description");
   private static final QName EJBNAME$2 = new QName("http://java.sun.com/xml/ns/javaee", "ejb-name");
   private static final QName INTERCEPTORCLASS$4 = new QName("http://java.sun.com/xml/ns/javaee", "interceptor-class");
   private static final QName INTERCEPTORORDER$6 = new QName("http://java.sun.com/xml/ns/javaee", "interceptor-order");
   private static final QName EXCLUDEDEFAULTINTERCEPTORS$8 = new QName("http://java.sun.com/xml/ns/javaee", "exclude-default-interceptors");
   private static final QName EXCLUDECLASSINTERCEPTORS$10 = new QName("http://java.sun.com/xml/ns/javaee", "exclude-class-interceptors");
   private static final QName METHOD$12 = new QName("http://java.sun.com/xml/ns/javaee", "method");
   private static final QName ID$14 = new QName("", "id");

   public InterceptorBindingTypeImpl(SchemaType sType) {
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

   public String getEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(EJBNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbName(String ejbName) {
      this.generatedSetterHelperImpl(ejbName, EJBNAME$2, 0, (short)1);
   }

   public String addNewEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(EJBNAME$2);
         return target;
      }
   }

   public FullyQualifiedClassType[] getInterceptorClassArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INTERCEPTORCLASS$4, targetList);
         FullyQualifiedClassType[] result = new FullyQualifiedClassType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FullyQualifiedClassType getInterceptorClassArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(INTERCEPTORCLASS$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfInterceptorClassArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INTERCEPTORCLASS$4);
      }
   }

   public void setInterceptorClassArray(FullyQualifiedClassType[] interceptorClassArray) {
      this.check_orphaned();
      this.arraySetterHelper(interceptorClassArray, INTERCEPTORCLASS$4);
   }

   public void setInterceptorClassArray(int i, FullyQualifiedClassType interceptorClass) {
      this.generatedSetterHelperImpl(interceptorClass, INTERCEPTORCLASS$4, i, (short)2);
   }

   public FullyQualifiedClassType insertNewInterceptorClass(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().insert_element_user(INTERCEPTORCLASS$4, i);
         return target;
      }
   }

   public FullyQualifiedClassType addNewInterceptorClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(INTERCEPTORCLASS$4);
         return target;
      }
   }

   public void removeInterceptorClass(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INTERCEPTORCLASS$4, i);
      }
   }

   public InterceptorOrderType getInterceptorOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InterceptorOrderType target = null;
         target = (InterceptorOrderType)this.get_store().find_element_user(INTERCEPTORORDER$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInterceptorOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INTERCEPTORORDER$6) != 0;
      }
   }

   public void setInterceptorOrder(InterceptorOrderType interceptorOrder) {
      this.generatedSetterHelperImpl(interceptorOrder, INTERCEPTORORDER$6, 0, (short)1);
   }

   public InterceptorOrderType addNewInterceptorOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InterceptorOrderType target = null;
         target = (InterceptorOrderType)this.get_store().add_element_user(INTERCEPTORORDER$6);
         return target;
      }
   }

   public void unsetInterceptorOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INTERCEPTORORDER$6, 0);
      }
   }

   public TrueFalseType getExcludeDefaultInterceptors() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(EXCLUDEDEFAULTINTERCEPTORS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetExcludeDefaultInterceptors() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXCLUDEDEFAULTINTERCEPTORS$8) != 0;
      }
   }

   public void setExcludeDefaultInterceptors(TrueFalseType excludeDefaultInterceptors) {
      this.generatedSetterHelperImpl(excludeDefaultInterceptors, EXCLUDEDEFAULTINTERCEPTORS$8, 0, (short)1);
   }

   public TrueFalseType addNewExcludeDefaultInterceptors() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(EXCLUDEDEFAULTINTERCEPTORS$8);
         return target;
      }
   }

   public void unsetExcludeDefaultInterceptors() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXCLUDEDEFAULTINTERCEPTORS$8, 0);
      }
   }

   public TrueFalseType getExcludeClassInterceptors() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(EXCLUDECLASSINTERCEPTORS$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetExcludeClassInterceptors() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXCLUDECLASSINTERCEPTORS$10) != 0;
      }
   }

   public void setExcludeClassInterceptors(TrueFalseType excludeClassInterceptors) {
      this.generatedSetterHelperImpl(excludeClassInterceptors, EXCLUDECLASSINTERCEPTORS$10, 0, (short)1);
   }

   public TrueFalseType addNewExcludeClassInterceptors() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(EXCLUDECLASSINTERCEPTORS$10);
         return target;
      }
   }

   public void unsetExcludeClassInterceptors() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXCLUDECLASSINTERCEPTORS$10, 0);
      }
   }

   public NamedMethodType getMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().find_element_user(METHOD$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(METHOD$12) != 0;
      }
   }

   public void setMethod(NamedMethodType method) {
      this.generatedSetterHelperImpl(method, METHOD$12, 0, (short)1);
   }

   public NamedMethodType addNewMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().add_element_user(METHOD$12);
         return target;
      }
   }

   public void unsetMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(METHOD$12, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$14) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$14);
      }
   }
}
