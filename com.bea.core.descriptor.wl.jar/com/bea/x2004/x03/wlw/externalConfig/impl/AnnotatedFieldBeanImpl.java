package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.AnnotatedFieldBean;
import com.bea.x2004.x03.wlw.externalConfig.AnnotationInstanceBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AnnotatedFieldBeanImpl extends XmlComplexContentImpl implements AnnotatedFieldBean {
   private static final long serialVersionUID = 1L;
   private static final QName FIELDNAME$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "field-name");
   private static final QName INSTANCETYPE$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "instance-type");
   private static final QName ANNOTATION$4 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotation");

   public AnnotatedFieldBeanImpl(SchemaType sType) {
      super(sType);
   }

   public String getFieldName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FIELDNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFieldName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FIELDNAME$0, 0);
         return target;
      }
   }

   public void setFieldName(String fieldName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FIELDNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FIELDNAME$0);
         }

         target.setStringValue(fieldName);
      }
   }

   public void xsetFieldName(XmlString fieldName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FIELDNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FIELDNAME$0);
         }

         target.set(fieldName);
      }
   }

   public String getInstanceType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INSTANCETYPE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetInstanceType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INSTANCETYPE$2, 0);
         return target;
      }
   }

   public void setInstanceType(String instanceType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INSTANCETYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INSTANCETYPE$2);
         }

         target.setStringValue(instanceType);
      }
   }

   public void xsetInstanceType(XmlString instanceType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INSTANCETYPE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(INSTANCETYPE$2);
         }

         target.set(instanceType);
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
}
