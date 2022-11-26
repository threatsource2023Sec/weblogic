package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.AnnotatedClassBean;
import com.bea.x2004.x03.wlw.externalConfig.AnnotationOverridesBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AnnotationOverridesBeanImpl extends XmlComplexContentImpl implements AnnotationOverridesBean {
   private static final long serialVersionUID = 1L;
   private static final QName ANNOTATEDCLASS$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotated-class");

   public AnnotationOverridesBeanImpl(SchemaType sType) {
      super(sType);
   }

   public AnnotatedClassBean[] getAnnotatedClassArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ANNOTATEDCLASS$0, targetList);
         AnnotatedClassBean[] result = new AnnotatedClassBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AnnotatedClassBean getAnnotatedClassArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotatedClassBean target = null;
         target = (AnnotatedClassBean)this.get_store().find_element_user(ANNOTATEDCLASS$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAnnotatedClassArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANNOTATEDCLASS$0);
      }
   }

   public void setAnnotatedClassArray(AnnotatedClassBean[] annotatedClassArray) {
      this.check_orphaned();
      this.arraySetterHelper(annotatedClassArray, ANNOTATEDCLASS$0);
   }

   public void setAnnotatedClassArray(int i, AnnotatedClassBean annotatedClass) {
      this.generatedSetterHelperImpl(annotatedClass, ANNOTATEDCLASS$0, i, (short)2);
   }

   public AnnotatedClassBean insertNewAnnotatedClass(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotatedClassBean target = null;
         target = (AnnotatedClassBean)this.get_store().insert_element_user(ANNOTATEDCLASS$0, i);
         return target;
      }
   }

   public AnnotatedClassBean addNewAnnotatedClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotatedClassBean target = null;
         target = (AnnotatedClassBean)this.get_store().add_element_user(ANNOTATEDCLASS$0);
         return target;
      }
   }

   public void removeAnnotatedClass(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ANNOTATEDCLASS$0, i);
      }
   }
}
