package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.weblogicApplication.MaxCacheSizeType;
import javax.xml.namespace.QName;

public class MaxCacheSizeTypeImpl extends XmlComplexContentImpl implements MaxCacheSizeType {
   private static final long serialVersionUID = 1L;
   private static final QName BYTES$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "bytes");
   private static final QName MEGABYTES$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "megabytes");

   public MaxCacheSizeTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getBytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTES$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetBytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(BYTES$0, 0);
         return target;
      }
   }

   public boolean isSetBytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BYTES$0) != 0;
      }
   }

   public void setBytes(int bytes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTES$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BYTES$0);
         }

         target.setIntValue(bytes);
      }
   }

   public void xsetBytes(XmlInt bytes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(BYTES$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(BYTES$0);
         }

         target.set(bytes);
      }
   }

   public void unsetBytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BYTES$0, 0);
      }
   }

   public int getMegabytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEGABYTES$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMegabytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MEGABYTES$2, 0);
         return target;
      }
   }

   public boolean isSetMegabytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MEGABYTES$2) != 0;
      }
   }

   public void setMegabytes(int megabytes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MEGABYTES$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MEGABYTES$2);
         }

         target.setIntValue(megabytes);
      }
   }

   public void xsetMegabytes(XmlInt megabytes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MEGABYTES$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MEGABYTES$2);
         }

         target.set(megabytes);
      }
   }

   public void unsetMegabytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MEGABYTES$2, 0);
      }
   }
}
