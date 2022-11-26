package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TangosolQueryCacheType;
import javax.xml.namespace.QName;

public class TangosolQueryCacheTypeImpl extends QueryCacheTypeImpl implements TangosolQueryCacheType {
   private static final long serialVersionUID = 1L;
   private static final QName CLEARONCLOSE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "clear-on-close");
   private static final QName TANGOSOLCACHETYPE$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "tangosol-cache-type");
   private static final QName TANGOSOLCACHENAME$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "tangosol-cache-name");

   public TangosolQueryCacheTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getClearOnClose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLEARONCLOSE$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetClearOnClose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CLEARONCLOSE$0, 0);
         return target;
      }
   }

   public boolean isSetClearOnClose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLEARONCLOSE$0) != 0;
      }
   }

   public void setClearOnClose(boolean clearOnClose) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLEARONCLOSE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CLEARONCLOSE$0);
         }

         target.setBooleanValue(clearOnClose);
      }
   }

   public void xsetClearOnClose(XmlBoolean clearOnClose) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CLEARONCLOSE$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(CLEARONCLOSE$0);
         }

         target.set(clearOnClose);
      }
   }

   public void unsetClearOnClose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLEARONCLOSE$0, 0);
      }
   }

   public String getTangosolCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TANGOSOLCACHETYPE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTangosolCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TANGOSOLCACHETYPE$2, 0);
         return target;
      }
   }

   public boolean isNilTangosolCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TANGOSOLCACHETYPE$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTangosolCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TANGOSOLCACHETYPE$2) != 0;
      }
   }

   public void setTangosolCacheType(String tangosolCacheType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TANGOSOLCACHETYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TANGOSOLCACHETYPE$2);
         }

         target.setStringValue(tangosolCacheType);
      }
   }

   public void xsetTangosolCacheType(XmlString tangosolCacheType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TANGOSOLCACHETYPE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TANGOSOLCACHETYPE$2);
         }

         target.set(tangosolCacheType);
      }
   }

   public void setNilTangosolCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TANGOSOLCACHETYPE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TANGOSOLCACHETYPE$2);
         }

         target.setNil();
      }
   }

   public void unsetTangosolCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TANGOSOLCACHETYPE$2, 0);
      }
   }

   public String getTangosolCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TANGOSOLCACHENAME$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTangosolCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TANGOSOLCACHENAME$4, 0);
         return target;
      }
   }

   public boolean isNilTangosolCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TANGOSOLCACHENAME$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTangosolCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TANGOSOLCACHENAME$4) != 0;
      }
   }

   public void setTangosolCacheName(String tangosolCacheName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TANGOSOLCACHENAME$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TANGOSOLCACHENAME$4);
         }

         target.setStringValue(tangosolCacheName);
      }
   }

   public void xsetTangosolCacheName(XmlString tangosolCacheName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TANGOSOLCACHENAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TANGOSOLCACHENAME$4);
         }

         target.set(tangosolCacheName);
      }
   }

   public void setNilTangosolCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TANGOSOLCACHENAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TANGOSOLCACHENAME$4);
         }

         target.setNil();
      }
   }

   public void unsetTangosolCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TANGOSOLCACHENAME$4, 0);
      }
   }
}
