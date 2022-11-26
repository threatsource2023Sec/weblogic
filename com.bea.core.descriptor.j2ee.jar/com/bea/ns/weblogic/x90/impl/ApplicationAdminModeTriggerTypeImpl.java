package com.bea.ns.weblogic.x90.impl;

import com.bea.ns.weblogic.x90.ApplicationAdminModeTriggerType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.XsdIntegerType;
import javax.xml.namespace.QName;

public class ApplicationAdminModeTriggerTypeImpl extends XmlComplexContentImpl implements ApplicationAdminModeTriggerType {
   private static final long serialVersionUID = 1L;
   private static final QName MAXSTUCKTHREADTIME$0 = new QName("http://www.bea.com/ns/weblogic/90", "max-stuck-thread-time");
   private static final QName STUCKTHREADCOUNT$2 = new QName("http://www.bea.com/ns/weblogic/90", "stuck-thread-count");
   private static final QName ID$4 = new QName("", "id");

   public ApplicationAdminModeTriggerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdIntegerType getMaxStuckThreadTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MAXSTUCKTHREADTIME$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxStuckThreadTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXSTUCKTHREADTIME$0) != 0;
      }
   }

   public void setMaxStuckThreadTime(XsdIntegerType maxStuckThreadTime) {
      this.generatedSetterHelperImpl(maxStuckThreadTime, MAXSTUCKTHREADTIME$0, 0, (short)1);
   }

   public XsdIntegerType addNewMaxStuckThreadTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MAXSTUCKTHREADTIME$0);
         return target;
      }
   }

   public void unsetMaxStuckThreadTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXSTUCKTHREADTIME$0, 0);
      }
   }

   public XsdIntegerType getStuckThreadCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(STUCKTHREADCOUNT$2, 0);
         return target == null ? null : target;
      }
   }

   public void setStuckThreadCount(XsdIntegerType stuckThreadCount) {
      this.generatedSetterHelperImpl(stuckThreadCount, STUCKTHREADCOUNT$2, 0, (short)1);
   }

   public XsdIntegerType addNewStuckThreadCount() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(STUCKTHREADCOUNT$2);
         return target;
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
