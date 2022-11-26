package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.weblogicWebservices.PersistenceConfigType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class PersistenceConfigTypeImpl extends XmlComplexContentImpl implements PersistenceConfigType {
   private static final long serialVersionUID = 1L;
   private static final QName CUSTOMIZED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "customized");
   private static final QName DEFAULTLOGICALSTORENAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "default-logical-store-name");

   public PersistenceConfigTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getCustomized() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CUSTOMIZED$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetCustomized() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CUSTOMIZED$0, 0);
         return target;
      }
   }

   public boolean isSetCustomized() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMIZED$0) != 0;
      }
   }

   public void setCustomized(boolean customized) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CUSTOMIZED$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CUSTOMIZED$0);
         }

         target.setBooleanValue(customized);
      }
   }

   public void xsetCustomized(XmlBoolean customized) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CUSTOMIZED$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(CUSTOMIZED$0);
         }

         target.set(customized);
      }
   }

   public void unsetCustomized() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMIZED$0, 0);
      }
   }

   public String getDefaultLogicalStoreName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DEFAULTLOGICALSTORENAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDefaultLogicalStoreName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTLOGICALSTORENAME$2) != 0;
      }
   }

   public void setDefaultLogicalStoreName(String defaultLogicalStoreName) {
      this.generatedSetterHelperImpl(defaultLogicalStoreName, DEFAULTLOGICALSTORENAME$2, 0, (short)1);
   }

   public String addNewDefaultLogicalStoreName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(DEFAULTLOGICALSTORENAME$2);
         return target;
      }
   }

   public void unsetDefaultLogicalStoreName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTLOGICALSTORENAME$2, 0);
      }
   }
}
