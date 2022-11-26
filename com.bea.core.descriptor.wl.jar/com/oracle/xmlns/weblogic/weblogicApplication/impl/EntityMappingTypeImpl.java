package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.EntityMappingType;
import javax.xml.namespace.QName;

public class EntityMappingTypeImpl extends XmlComplexContentImpl implements EntityMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName ENTITYMAPPINGNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "entity-mapping-name");
   private static final QName PUBLICID$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "public-id");
   private static final QName SYSTEMID$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "system-id");
   private static final QName ENTITYURI$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "entity-uri");
   private static final QName WHENTOCACHE$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "when-to-cache");
   private static final QName CACHETIMEOUTINTERVAL$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "cache-timeout-interval");

   public EntityMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getEntityMappingName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENTITYMAPPINGNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetEntityMappingName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENTITYMAPPINGNAME$0, 0);
         return target;
      }
   }

   public void setEntityMappingName(String entityMappingName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENTITYMAPPINGNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENTITYMAPPINGNAME$0);
         }

         target.setStringValue(entityMappingName);
      }
   }

   public void xsetEntityMappingName(XmlString entityMappingName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENTITYMAPPINGNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ENTITYMAPPINGNAME$0);
         }

         target.set(entityMappingName);
      }
   }

   public String getPublicId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PUBLICID$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPublicId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PUBLICID$2, 0);
         return target;
      }
   }

   public boolean isSetPublicId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PUBLICID$2) != 0;
      }
   }

   public void setPublicId(String publicId) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PUBLICID$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PUBLICID$2);
         }

         target.setStringValue(publicId);
      }
   }

   public void xsetPublicId(XmlString publicId) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PUBLICID$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PUBLICID$2);
         }

         target.set(publicId);
      }
   }

   public void unsetPublicId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PUBLICID$2, 0);
      }
   }

   public String getSystemId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYSTEMID$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSystemId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYSTEMID$4, 0);
         return target;
      }
   }

   public boolean isSetSystemId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SYSTEMID$4) != 0;
      }
   }

   public void setSystemId(String systemId) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYSTEMID$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SYSTEMID$4);
         }

         target.setStringValue(systemId);
      }
   }

   public void xsetSystemId(XmlString systemId) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYSTEMID$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SYSTEMID$4);
         }

         target.set(systemId);
      }
   }

   public void unsetSystemId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SYSTEMID$4, 0);
      }
   }

   public String getEntityUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENTITYURI$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetEntityUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENTITYURI$6, 0);
         return target;
      }
   }

   public boolean isSetEntityUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENTITYURI$6) != 0;
      }
   }

   public void setEntityUri(String entityUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENTITYURI$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENTITYURI$6);
         }

         target.setStringValue(entityUri);
      }
   }

   public void xsetEntityUri(XmlString entityUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENTITYURI$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ENTITYURI$6);
         }

         target.set(entityUri);
      }
   }

   public void unsetEntityUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENTITYURI$6, 0);
      }
   }

   public String getWhenToCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WHENTOCACHE$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetWhenToCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(WHENTOCACHE$8, 0);
         return target;
      }
   }

   public boolean isSetWhenToCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WHENTOCACHE$8) != 0;
      }
   }

   public void setWhenToCache(String whenToCache) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WHENTOCACHE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(WHENTOCACHE$8);
         }

         target.setStringValue(whenToCache);
      }
   }

   public void xsetWhenToCache(XmlString whenToCache) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(WHENTOCACHE$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(WHENTOCACHE$8);
         }

         target.set(whenToCache);
      }
   }

   public void unsetWhenToCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WHENTOCACHE$8, 0);
      }
   }

   public int getCacheTimeoutInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHETIMEOUTINTERVAL$10, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetCacheTimeoutInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CACHETIMEOUTINTERVAL$10, 0);
         return target;
      }
   }

   public boolean isSetCacheTimeoutInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHETIMEOUTINTERVAL$10) != 0;
      }
   }

   public void setCacheTimeoutInterval(int cacheTimeoutInterval) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHETIMEOUTINTERVAL$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CACHETIMEOUTINTERVAL$10);
         }

         target.setIntValue(cacheTimeoutInterval);
      }
   }

   public void xsetCacheTimeoutInterval(XmlInt cacheTimeoutInterval) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CACHETIMEOUTINTERVAL$10, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(CACHETIMEOUTINTERVAL$10);
         }

         target.set(cacheTimeoutInterval);
      }
   }

   public void unsetCacheTimeoutInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHETIMEOUTINTERVAL$10, 0);
      }
   }
}
