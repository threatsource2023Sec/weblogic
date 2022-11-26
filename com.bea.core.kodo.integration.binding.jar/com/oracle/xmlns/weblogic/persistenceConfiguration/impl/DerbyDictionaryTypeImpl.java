package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DerbyDictionaryType;
import javax.xml.namespace.QName;

public class DerbyDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements DerbyDictionaryType {
   private static final long serialVersionUID = 1L;
   private static final QName SHUTDOWNONCLOSE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "shutdown-on-close");

   public DerbyDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getShutdownOnClose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHUTDOWNONCLOSE$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetShutdownOnClose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SHUTDOWNONCLOSE$0, 0);
         return target;
      }
   }

   public boolean isSetShutdownOnClose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHUTDOWNONCLOSE$0) != 0;
      }
   }

   public void setShutdownOnClose(boolean shutdownOnClose) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHUTDOWNONCLOSE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SHUTDOWNONCLOSE$0);
         }

         target.setBooleanValue(shutdownOnClose);
      }
   }

   public void xsetShutdownOnClose(XmlBoolean shutdownOnClose) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SHUTDOWNONCLOSE$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SHUTDOWNONCLOSE$0);
         }

         target.set(shutdownOnClose);
      }
   }

   public void unsetShutdownOnClose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHUTDOWNONCLOSE$0, 0);
      }
   }
}
