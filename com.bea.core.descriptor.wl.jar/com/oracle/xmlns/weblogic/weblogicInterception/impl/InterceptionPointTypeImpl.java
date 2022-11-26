package com.oracle.xmlns.weblogic.weblogicInterception.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicInterception.InterceptionPointType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class InterceptionPointTypeImpl extends XmlComplexContentImpl implements InterceptionPointType {
   private static final long serialVersionUID = 1L;
   private static final QName TYPE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "type");
   private static final QName NAMESEGMENT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "name-segment");

   public InterceptionPointTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPE$0, 0);
         return target;
      }
   }

   public void setType(String type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TYPE$0);
         }

         target.setStringValue(type);
      }
   }

   public void xsetType(XmlString type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TYPE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TYPE$0);
         }

         target.set(type);
      }
   }

   public String[] getNameSegmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(NAMESEGMENT$2, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getNameSegmentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAMESEGMENT$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetNameSegmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(NAMESEGMENT$2, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetNameSegmentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMESEGMENT$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfNameSegmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NAMESEGMENT$2);
      }
   }

   public void setNameSegmentArray(String[] nameSegmentArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(nameSegmentArray, NAMESEGMENT$2);
      }
   }

   public void setNameSegmentArray(int i, String nameSegment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAMESEGMENT$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(nameSegment);
         }
      }
   }

   public void xsetNameSegmentArray(XmlString[] nameSegmentArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(nameSegmentArray, NAMESEGMENT$2);
      }
   }

   public void xsetNameSegmentArray(int i, XmlString nameSegment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAMESEGMENT$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(nameSegment);
         }
      }
   }

   public void insertNameSegment(int i, String nameSegment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(NAMESEGMENT$2, i);
         target.setStringValue(nameSegment);
      }
   }

   public void addNameSegment(String nameSegment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(NAMESEGMENT$2);
         target.setStringValue(nameSegment);
      }
   }

   public XmlString insertNewNameSegment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(NAMESEGMENT$2, i);
         return target;
      }
   }

   public XmlString addNewNameSegment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(NAMESEGMENT$2);
         return target;
      }
   }

   public void removeNameSegment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NAMESEGMENT$2, i);
      }
   }
}
