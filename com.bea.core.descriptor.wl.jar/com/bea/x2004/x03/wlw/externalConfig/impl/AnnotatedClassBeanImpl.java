package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.AnnotatedClassBean;
import com.bea.x2004.x03.wlw.externalConfig.AnnotatedFieldBean;
import com.bea.x2004.x03.wlw.externalConfig.AnnotatedMethodBean;
import com.bea.x2004.x03.wlw.externalConfig.AnnotationInstanceBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AnnotatedClassBeanImpl extends XmlComplexContentImpl implements AnnotatedClassBean {
   private static final long serialVersionUID = 1L;
   private static final QName ANNOTATEDCLASSNAME$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotated-class-name");
   private static final QName COMPONENTTYPE$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "component-type");
   private static final QName ANNOTATION$4 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotation");
   private static final QName FIELD$6 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "field");
   private static final QName METHOD$8 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "method");

   public AnnotatedClassBeanImpl(SchemaType sType) {
      super(sType);
   }

   public String getAnnotatedClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ANNOTATEDCLASSNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAnnotatedClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ANNOTATEDCLASSNAME$0, 0);
         return target;
      }
   }

   public void setAnnotatedClassName(String annotatedClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ANNOTATEDCLASSNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ANNOTATEDCLASSNAME$0);
         }

         target.setStringValue(annotatedClassName);
      }
   }

   public void xsetAnnotatedClassName(XmlString annotatedClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ANNOTATEDCLASSNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ANNOTATEDCLASSNAME$0);
         }

         target.set(annotatedClassName);
      }
   }

   public String getComponentType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COMPONENTTYPE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetComponentType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COMPONENTTYPE$2, 0);
         return target;
      }
   }

   public boolean isSetComponentType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMPONENTTYPE$2) != 0;
      }
   }

   public void setComponentType(String componentType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COMPONENTTYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COMPONENTTYPE$2);
         }

         target.setStringValue(componentType);
      }
   }

   public void xsetComponentType(XmlString componentType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COMPONENTTYPE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COMPONENTTYPE$2);
         }

         target.set(componentType);
      }
   }

   public void unsetComponentType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMPONENTTYPE$2, 0);
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

   public AnnotatedFieldBean[] getFieldArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FIELD$6, targetList);
         AnnotatedFieldBean[] result = new AnnotatedFieldBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AnnotatedFieldBean getFieldArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotatedFieldBean target = null;
         target = (AnnotatedFieldBean)this.get_store().find_element_user(FIELD$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFieldArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FIELD$6);
      }
   }

   public void setFieldArray(AnnotatedFieldBean[] fieldArray) {
      this.check_orphaned();
      this.arraySetterHelper(fieldArray, FIELD$6);
   }

   public void setFieldArray(int i, AnnotatedFieldBean field) {
      this.generatedSetterHelperImpl(field, FIELD$6, i, (short)2);
   }

   public AnnotatedFieldBean insertNewField(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotatedFieldBean target = null;
         target = (AnnotatedFieldBean)this.get_store().insert_element_user(FIELD$6, i);
         return target;
      }
   }

   public AnnotatedFieldBean addNewField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotatedFieldBean target = null;
         target = (AnnotatedFieldBean)this.get_store().add_element_user(FIELD$6);
         return target;
      }
   }

   public void removeField(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FIELD$6, i);
      }
   }

   public AnnotatedMethodBean[] getMethodArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(METHOD$8, targetList);
         AnnotatedMethodBean[] result = new AnnotatedMethodBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AnnotatedMethodBean getMethodArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotatedMethodBean target = null;
         target = (AnnotatedMethodBean)this.get_store().find_element_user(METHOD$8, i);
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
         return this.get_store().count_elements(METHOD$8);
      }
   }

   public void setMethodArray(AnnotatedMethodBean[] methodArray) {
      this.check_orphaned();
      this.arraySetterHelper(methodArray, METHOD$8);
   }

   public void setMethodArray(int i, AnnotatedMethodBean method) {
      this.generatedSetterHelperImpl(method, METHOD$8, i, (short)2);
   }

   public AnnotatedMethodBean insertNewMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotatedMethodBean target = null;
         target = (AnnotatedMethodBean)this.get_store().insert_element_user(METHOD$8, i);
         return target;
      }
   }

   public AnnotatedMethodBean addNewMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotatedMethodBean target = null;
         target = (AnnotatedMethodBean)this.get_store().add_element_user(METHOD$8);
         return target;
      }
   }

   public void removeMethod(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(METHOD$8, i);
      }
   }
}
