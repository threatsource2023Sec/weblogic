package com.bea.x2004.x03.wlw.externalConfig.impl;

import com.bea.x2004.x03.wlw.externalConfig.AnnotationInstanceBean;
import com.bea.x2004.x03.wlw.externalConfig.ArrayMemberBean;
import com.bea.x2004.x03.wlw.externalConfig.MemberBean;
import com.bea.x2004.x03.wlw.externalConfig.NestedAnnotationArrayBean;
import com.bea.x2004.x03.wlw.externalConfig.NestedAnnotationBean;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AnnotationInstanceBeanImpl extends XmlComplexContentImpl implements AnnotationInstanceBean {
   private static final long serialVersionUID = 1L;
   private static final QName ANNOTATIONCLASSNAME$0 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "annotation-class-name");
   private static final QName MEMBER$2 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "member");
   private static final QName ARRAYMEMBER$4 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "array-member");
   private static final QName NESTEDANNOTATION$6 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "nested-annotation");
   private static final QName NESTEDANNOTATIONARRAY1$8 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "nested-annotation-array");
   private static final QName UPDATECOUNT$10 = new QName("http://www.bea.com/2004/03/wlw/external-config/", "update-count");

   public AnnotationInstanceBeanImpl(SchemaType sType) {
      super(sType);
   }

   public String getAnnotationClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ANNOTATIONCLASSNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAnnotationClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ANNOTATIONCLASSNAME$0, 0);
         return target;
      }
   }

   public void setAnnotationClassName(String annotationClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ANNOTATIONCLASSNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ANNOTATIONCLASSNAME$0);
         }

         target.setStringValue(annotationClassName);
      }
   }

   public void xsetAnnotationClassName(XmlString annotationClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ANNOTATIONCLASSNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ANNOTATIONCLASSNAME$0);
         }

         target.set(annotationClassName);
      }
   }

   public MemberBean[] getMemberArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MEMBER$2, targetList);
         MemberBean[] result = new MemberBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MemberBean getMemberArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MemberBean target = null;
         target = (MemberBean)this.get_store().find_element_user(MEMBER$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMemberArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MEMBER$2);
      }
   }

   public void setMemberArray(MemberBean[] memberArray) {
      this.check_orphaned();
      this.arraySetterHelper(memberArray, MEMBER$2);
   }

   public void setMemberArray(int i, MemberBean member) {
      this.generatedSetterHelperImpl(member, MEMBER$2, i, (short)2);
   }

   public MemberBean insertNewMember(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MemberBean target = null;
         target = (MemberBean)this.get_store().insert_element_user(MEMBER$2, i);
         return target;
      }
   }

   public MemberBean addNewMember() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MemberBean target = null;
         target = (MemberBean)this.get_store().add_element_user(MEMBER$2);
         return target;
      }
   }

   public void removeMember(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MEMBER$2, i);
      }
   }

   public ArrayMemberBean[] getArrayMemberArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ARRAYMEMBER$4, targetList);
         ArrayMemberBean[] result = new ArrayMemberBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ArrayMemberBean getArrayMemberArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ArrayMemberBean target = null;
         target = (ArrayMemberBean)this.get_store().find_element_user(ARRAYMEMBER$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfArrayMemberArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ARRAYMEMBER$4);
      }
   }

   public void setArrayMemberArray(ArrayMemberBean[] arrayMemberArray) {
      this.check_orphaned();
      this.arraySetterHelper(arrayMemberArray, ARRAYMEMBER$4);
   }

   public void setArrayMemberArray(int i, ArrayMemberBean arrayMember) {
      this.generatedSetterHelperImpl(arrayMember, ARRAYMEMBER$4, i, (short)2);
   }

   public ArrayMemberBean insertNewArrayMember(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ArrayMemberBean target = null;
         target = (ArrayMemberBean)this.get_store().insert_element_user(ARRAYMEMBER$4, i);
         return target;
      }
   }

   public ArrayMemberBean addNewArrayMember() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ArrayMemberBean target = null;
         target = (ArrayMemberBean)this.get_store().add_element_user(ARRAYMEMBER$4);
         return target;
      }
   }

   public void removeArrayMember(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ARRAYMEMBER$4, i);
      }
   }

   public NestedAnnotationBean[] getNestedAnnotationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(NESTEDANNOTATION$6, targetList);
         NestedAnnotationBean[] result = new NestedAnnotationBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public NestedAnnotationBean getNestedAnnotationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NestedAnnotationBean target = null;
         target = (NestedAnnotationBean)this.get_store().find_element_user(NESTEDANNOTATION$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfNestedAnnotationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NESTEDANNOTATION$6);
      }
   }

   public void setNestedAnnotationArray(NestedAnnotationBean[] nestedAnnotationArray) {
      this.check_orphaned();
      this.arraySetterHelper(nestedAnnotationArray, NESTEDANNOTATION$6);
   }

   public void setNestedAnnotationArray(int i, NestedAnnotationBean nestedAnnotation) {
      this.generatedSetterHelperImpl(nestedAnnotation, NESTEDANNOTATION$6, i, (short)2);
   }

   public NestedAnnotationBean insertNewNestedAnnotation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NestedAnnotationBean target = null;
         target = (NestedAnnotationBean)this.get_store().insert_element_user(NESTEDANNOTATION$6, i);
         return target;
      }
   }

   public NestedAnnotationBean addNewNestedAnnotation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NestedAnnotationBean target = null;
         target = (NestedAnnotationBean)this.get_store().add_element_user(NESTEDANNOTATION$6);
         return target;
      }
   }

   public void removeNestedAnnotation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NESTEDANNOTATION$6, i);
      }
   }

   public NestedAnnotationArrayBean[] getNestedAnnotationArray1Array() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(NESTEDANNOTATIONARRAY1$8, targetList);
         NestedAnnotationArrayBean[] result = new NestedAnnotationArrayBean[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public NestedAnnotationArrayBean getNestedAnnotationArray1Array(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NestedAnnotationArrayBean target = null;
         target = (NestedAnnotationArrayBean)this.get_store().find_element_user(NESTEDANNOTATIONARRAY1$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfNestedAnnotationArray1Array() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NESTEDANNOTATIONARRAY1$8);
      }
   }

   public void setNestedAnnotationArray1Array(NestedAnnotationArrayBean[] nestedAnnotationArray1Array) {
      this.check_orphaned();
      this.arraySetterHelper(nestedAnnotationArray1Array, NESTEDANNOTATIONARRAY1$8);
   }

   public void setNestedAnnotationArray1Array(int i, NestedAnnotationArrayBean nestedAnnotationArray1) {
      this.generatedSetterHelperImpl(nestedAnnotationArray1, NESTEDANNOTATIONARRAY1$8, i, (short)2);
   }

   public NestedAnnotationArrayBean insertNewNestedAnnotationArray1(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NestedAnnotationArrayBean target = null;
         target = (NestedAnnotationArrayBean)this.get_store().insert_element_user(NESTEDANNOTATIONARRAY1$8, i);
         return target;
      }
   }

   public NestedAnnotationArrayBean addNewNestedAnnotationArray1() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NestedAnnotationArrayBean target = null;
         target = (NestedAnnotationArrayBean)this.get_store().add_element_user(NESTEDANNOTATIONARRAY1$8);
         return target;
      }
   }

   public void removeNestedAnnotationArray1(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NESTEDANNOTATIONARRAY1$8, i);
      }
   }

   public long getUpdateCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UPDATECOUNT$10, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetUpdateCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(UPDATECOUNT$10, 0);
         return target;
      }
   }

   public boolean isSetUpdateCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UPDATECOUNT$10) != 0;
      }
   }

   public void setUpdateCount(long updateCount) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UPDATECOUNT$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(UPDATECOUNT$10);
         }

         target.setLongValue(updateCount);
      }
   }

   public void xsetUpdateCount(XmlLong updateCount) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(UPDATECOUNT$10, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(UPDATECOUNT$10);
         }

         target.set(updateCount);
      }
   }

   public void unsetUpdateCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UPDATECOUNT$10, 0);
      }
   }
}
