package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ConcurrentHashMapType;
import javax.xml.namespace.QName;

public class ConcurrentHashMapTypeImpl extends QueryCompilationCacheTypeImpl implements ConcurrentHashMapType {
   private static final long serialVersionUID = 1L;
   private static final QName MAXSIZE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "max-size");

   public ConcurrentHashMapTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getMaxSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXSIZE$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXSIZE$0, 0);
         return target;
      }
   }

   public boolean isSetMaxSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXSIZE$0) != 0;
      }
   }

   public void setMaxSize(int maxSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXSIZE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXSIZE$0);
         }

         target.setIntValue(maxSize);
      }
   }

   public void xsetMaxSize(XmlInt maxSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXSIZE$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXSIZE$0);
         }

         target.set(maxSize);
      }
   }

   public void unsetMaxSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXSIZE$0, 0);
      }
   }
}
