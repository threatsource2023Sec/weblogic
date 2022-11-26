package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.AnnotationInstanceBean;
import com.bea.x2004.x03.wlw.externalConfig.NestedAnnotationBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class NestedAnnotationBeanImpl extends XmlComplexContentImpl implements NestedAnnotationBean {
   private static final long serialVersionUID = 1L;
   private static final QName MEMBERNAME$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "member-name");
   private static final QName ANNOTATION$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotation");

   public NestedAnnotationBeanImpl(SchemaType sType) {
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

   public AnnotationInstanceBean getAnnotation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationInstanceBean target = null;
         target = (AnnotationInstanceBean)this.get_store().find_element_user(ANNOTATION$2, 0);
         return target == null ? null : target;
      }
   }

   public void setAnnotation(AnnotationInstanceBean annotation) {
      this.generatedSetterHelperImpl(annotation, ANNOTATION$2, 0, (short)1);
   }

   public AnnotationInstanceBean addNewAnnotation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnnotationInstanceBean target = null;
         target = (AnnotationInstanceBean)this.get_store().add_element_user(ANNOTATION$2);
         return target;
      }
   }
}
