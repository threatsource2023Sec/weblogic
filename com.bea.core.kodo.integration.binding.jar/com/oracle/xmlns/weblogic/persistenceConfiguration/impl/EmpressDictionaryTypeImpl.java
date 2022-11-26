package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.EmpressDictionaryType;
import javax.xml.namespace.QName;

public class EmpressDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements EmpressDictionaryType {
   private static final long serialVersionUID = 1L;
   private static final QName ALLOWCONCURRENTREAD$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "allow-concurrent-read");

   public EmpressDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getAllowConcurrentRead() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLOWCONCURRENTREAD$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetAllowConcurrentRead() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ALLOWCONCURRENTREAD$0, 0);
         return target;
      }
   }

   public boolean isSetAllowConcurrentRead() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALLOWCONCURRENTREAD$0) != 0;
      }
   }

   public void setAllowConcurrentRead(boolean allowConcurrentRead) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLOWCONCURRENTREAD$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ALLOWCONCURRENTREAD$0);
         }

         target.setBooleanValue(allowConcurrentRead);
      }
   }

   public void xsetAllowConcurrentRead(XmlBoolean allowConcurrentRead) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ALLOWCONCURRENTREAD$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ALLOWCONCURRENTREAD$0);
         }

         target.set(allowConcurrentRead);
      }
   }

   public void unsetAllowConcurrentRead() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ALLOWCONCURRENTREAD$0, 0);
      }
   }
}
