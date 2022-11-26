package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.InMemorySavepointManagerType;
import javax.xml.namespace.QName;

public class InMemorySavepointManagerTypeImpl extends SavepointManagerTypeImpl implements InMemorySavepointManagerType {
   private static final long serialVersionUID = 1L;
   private static final QName PREFLUSH$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "pre-flush");

   public InMemorySavepointManagerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getPreFlush() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PREFLUSH$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetPreFlush() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(PREFLUSH$0, 0);
         return target;
      }
   }

   public boolean isSetPreFlush() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREFLUSH$0) != 0;
      }
   }

   public void setPreFlush(boolean preFlush) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PREFLUSH$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PREFLUSH$0);
         }

         target.setBooleanValue(preFlush);
      }
   }

   public void xsetPreFlush(XmlBoolean preFlush) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(PREFLUSH$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(PREFLUSH$0);
         }

         target.set(preFlush);
      }
   }

   public void unsetPreFlush() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREFLUSH$0, 0);
      }
   }
}
