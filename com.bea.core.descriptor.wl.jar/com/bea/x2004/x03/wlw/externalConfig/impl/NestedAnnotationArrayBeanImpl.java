package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.AnnotationInstanceBean;
import com.bea.x2004.x03.wlw.externalConfig.NestedAnnotationArrayBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class NestedAnnotationArrayBeanImpl extends XmlComplexContentImpl implements NestedAnnotationArrayBean {
   private static final long serialVersionUID = 1L;
   private static final QName MEMBERNAME$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "member-name");
   private static final QName ANNOTATION$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotation");

   public NestedAnnotationArrayBeanImpl(SchemaType sType) {
      super(sType);
   }

   public String getMemberName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEMBERNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMemberName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MEMBERNAME$0, 0);
         return target;
      }
   }

   public void setMemberName(String memberName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEMBERNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MEMBERNAME$0);
         }

         target.setStringValue(memberName);
      }
   }

   public void xsetMemberName(XmlString memberName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MEMBERNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MEMBERNAME$0);
         }

         target.set(memberName);
      }
   }

   public AnnotationInstanceBean[] getAnnotationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ANNOTATION$2, targetList);
         AnnotationInstanceBean[] result = new AnnotationInstanceBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AnnotationInstanceBean getAnnotationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationInstanceBean target = null;
         target = (AnnotationInstanceBean)this.get_store().find_element_user(ANNOTATION$2, i);
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
         return this.get_store().count_elements(ANNOTATION$2);
      }
   }

   public void setAnnotationArray(AnnotationInstanceBean[] annotationArray) {
      this.check_orphaned();
      this.arraySetterHelper(annotationArray, ANNOTATION$2);
   }

   public void setAnnotationArray(int i, AnnotationInstanceBean annotation) {
      this.generatedSetterHelperImpl(annotation, ANNOTATION$2, i, (short)2);
   }

   public AnnotationInstanceBean insertNewAnnotation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationInstanceBean target = null;
         target = (AnnotationInstanceBean)this.get_store().insert_element_user(ANNOTATION$2, i);
         return target;
      }
   }

   public AnnotationInstanceBean addNewAnnotation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationInstanceBean target = null;
         target = (AnnotationInstanceBean)this.get_store().add_element_user(ANNOTATION$2);
         return target;
      }
   }

   public void removeAnnotation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ANNOTATION$2, i);
      }
   }
}
