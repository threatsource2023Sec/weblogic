package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.AnnotatedMethodBean;
import com.bea.x2004.x03.wlw.externalConfig.AnnotationInstanceBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AnnotatedMethodBeanImpl extends XmlComplexContentImpl implements AnnotatedMethodBean {
   private static final long serialVersionUID = 1L;
   private static final QName METHODKEY$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "method-key");
   private static final QName METHODNAME$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "method-name");
   private static final QName ANNOTATION$4 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotation");
   private static final QName PARAMTERTYPE$6 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "paramter-type");

   public AnnotatedMethodBeanImpl(SchemaType sType) {
      super(sType);
   }

   public String getMethodKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(METHODKEY$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMethodKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(METHODKEY$0, 0);
         return target;
      }
   }

   public void setMethodKey(String methodKey) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(METHODKEY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(METHODKEY$0);
         }

         target.setStringValue(methodKey);
      }
   }

   public void xsetMethodKey(XmlString methodKey) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(METHODKEY$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(METHODKEY$0);
         }

         target.set(methodKey);
      }
   }

   public String getMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(METHODNAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(METHODNAME$2, 0);
         return target;
      }
   }

   public void setMethodName(String methodName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(METHODNAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(METHODNAME$2);
         }

         target.setStringValue(methodName);
      }
   }

   public void xsetMethodName(XmlString methodName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(METHODNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(METHODNAME$2);
         }

         target.set(methodName);
      }
   }

   public AnnotationInstanceBean[] getAnnotationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ANNOTATION$4, targetList);
         AnnotationInstanceBean[] result = new AnnotationInstanceBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AnnotationInstanceBean getAnnotationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationInstanceBean target = null;
         target = (AnnotationInstanceBean)this.get_store().find_element_user(ANNOTATION$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAnnotationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANNOTATION$4);
      }
   }

   public void setAnnotationArray(AnnotationInstanceBean[] annotationArray) {
      this.check_orphaned();
      this.arraySetterHelper(annotationArray, ANNOTATION$4);
   }

   public void setAnnotationArray(int i, AnnotationInstanceBean annotation) {
      this.generatedSetterHelperImpl(annotation, ANNOTATION$4, i, (short)2);
   }

   public AnnotationInstanceBean insertNewAnnotation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationInstanceBean target = null;
         target = (AnnotationInstanceBean)this.get_store().insert_element_user(ANNOTATION$4, i);
         return target;
      }
   }

   public AnnotationInstanceBean addNewAnnotation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationInstanceBean target = null;
         target = (AnnotationInstanceBean)this.get_store().add_element_user(ANNOTATION$4);
         return target;
      }
   }

   public void removeAnnotation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ANNOTATION$4, i);
      }
   }

   public String[] getParamterTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PARAMTERTYPE$6, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getParamterTypeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARAMTERTYPE$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetParamterTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PARAMTERTYPE$6, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetParamterTypeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARAMTERTYPE$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfParamterTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PARAMTERTYPE$6);
      }
   }

   public void setParamterTypeArray(String[] paramterTypeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(paramterTypeArray, PARAMTERTYPE$6);
      }
   }

   public void setParamterTypeArray(int i, String paramterType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARAMTERTYPE$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(paramterType);
         }
      }
   }

   public void xsetParamterTypeArray(XmlString[] paramterTypeArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(paramterTypeArray, PARAMTERTYPE$6);
      }
   }

   public void xsetParamterTypeArray(int i, XmlString paramterType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARAMTERTYPE$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(paramterType);
         }
      }
   }

   public void insertParamterType(int i, String paramterType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(PARAMTERTYPE$6, i);
         target.setStringValue(paramterType);
      }
   }

   public void addParamterType(String paramterType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(PARAMTERTYPE$6);
         target.setStringValue(paramterType);
      }
   }

   public XmlString insertNewParamterType(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(PARAMTERTYPE$6, i);
         return target;
      }
   }

   public XmlString addNewParamterType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(PARAMTERTYPE$6);
         return target;
      }
   }

   public void removeParamterType(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PARAMTERTYPE$6, i);
      }
   }
}
