package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.InverseManagerType;
import javax.xml.namespace.QName;

public class InverseManagerTypeImpl extends XmlComplexContentImpl implements InverseManagerType {
   private static final long serialVersionUID = 1L;
   private static final QName ACTION$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "action");
   private static final QName MANAGELRS$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "manage-lrs");

   public InverseManagerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ACTION$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ACTION$0, 0);
         return target;
      }
   }

   public boolean isNilAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ACTION$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACTION$0) != 0;
      }
   }

   public void setAction(String action) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ACTION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ACTION$0);
         }

         target.setStringValue(action);
      }
   }

   public void xsetAction(XmlString action) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ACTION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ACTION$0);
         }

         target.set(action);
      }
   }

   public void setNilAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ACTION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ACTION$0);
         }

         target.setNil();
      }
   }

   public void unsetAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACTION$0, 0);
      }
   }

   public boolean getManageLrs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MANAGELRS$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetManageLrs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MANAGELRS$2, 0);
         return target;
      }
   }

   public boolean isSetManageLrs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MANAGELRS$2) != 0;
      }
   }

   public void setManageLrs(boolean manageLrs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MANAGELRS$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MANAGELRS$2);
         }

         target.setBooleanValue(manageLrs);
      }
   }

   public void xsetManageLrs(XmlBoolean manageLrs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MANAGELRS$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(MANAGELRS$2);
         }

         target.set(manageLrs);
      }
   }

   public void unsetManageLrs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MANAGELRS$2, 0);
      }
   }
}
