package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ApplicationEntityCacheType;
import com.oracle.xmlns.weblogic.weblogicApplication.MaxCacheSizeType;
import javax.xml.namespace.QName;

public class ApplicationEntityCacheTypeImpl extends XmlComplexContentImpl implements ApplicationEntityCacheType {
   private static final long serialVersionUID = 1L;
   private static final QName ENTITYCACHENAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "entity-cache-name");
   private static final QName MAXBEANSINCACHE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "max-beans-in-cache");
   private static final QName MAXCACHESIZE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "max-cache-size");
   private static final QName MAXQUERIESINCACHE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "max-queries-in-cache");
   private static final QName CACHINGSTRATEGY$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "caching-strategy");

   public ApplicationEntityCacheTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getEntityCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENTITYCACHENAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetEntityCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENTITYCACHENAME$0, 0);
         return target;
      }
   }

   public void setEntityCacheName(String entityCacheName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENTITYCACHENAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENTITYCACHENAME$0);
         }

         target.setStringValue(entityCacheName);
      }
   }

   public void xsetEntityCacheName(XmlString entityCacheName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ENTITYCACHENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ENTITYCACHENAME$0);
         }

         target.set(entityCacheName);
      }
   }

   public int getMaxBeansInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXBEANSINCACHE$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxBeansInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXBEANSINCACHE$2, 0);
         return target;
      }
   }

   public boolean isSetMaxBeansInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXBEANSINCACHE$2) != 0;
      }
   }

   public void setMaxBeansInCache(int maxBeansInCache) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXBEANSINCACHE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXBEANSINCACHE$2);
         }

         target.setIntValue(maxBeansInCache);
      }
   }

   public void xsetMaxBeansInCache(XmlInt maxBeansInCache) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXBEANSINCACHE$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXBEANSINCACHE$2);
         }

         target.set(maxBeansInCache);
      }
   }

   public void unsetMaxBeansInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXBEANSINCACHE$2, 0);
      }
   }

   public MaxCacheSizeType getMaxCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MaxCacheSizeType target = null;
         target = (MaxCacheSizeType)this.get_store().find_element_user(MAXCACHESIZE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXCACHESIZE$4) != 0;
      }
   }

   public void setMaxCacheSize(MaxCacheSizeType maxCacheSize) {
      this.generatedSetterHelperImpl(maxCacheSize, MAXCACHESIZE$4, 0, (short)1);
   }

   public MaxCacheSizeType addNewMaxCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MaxCacheSizeType target = null;
         target = (MaxCacheSizeType)this.get_store().add_element_user(MAXCACHESIZE$4);
         return target;
      }
   }

   public void unsetMaxCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXCACHESIZE$4, 0);
      }
   }

   public int getMaxQueriesInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXQUERIESINCACHE$6, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxQueriesInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXQUERIESINCACHE$6, 0);
         return target;
      }
   }

   public boolean isSetMaxQueriesInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXQUERIESINCACHE$6) != 0;
      }
   }

   public void setMaxQueriesInCache(int maxQueriesInCache) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXQUERIESINCACHE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXQUERIESINCACHE$6);
         }

         target.setIntValue(maxQueriesInCache);
      }
   }

   public void xsetMaxQueriesInCache(XmlInt maxQueriesInCache) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXQUERIESINCACHE$6, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXQUERIESINCACHE$6);
         }

         target.set(maxQueriesInCache);
      }
   }

   public void unsetMaxQueriesInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXQUERIESINCACHE$6, 0);
      }
   }

   public String getCachingStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHINGSTRATEGY$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCachingStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CACHINGSTRATEGY$8, 0);
         return target;
      }
   }

   public boolean isSetCachingStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHINGSTRATEGY$8) != 0;
      }
   }

   public void setCachingStrategy(String cachingStrategy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHINGSTRATEGY$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CACHINGSTRATEGY$8);
         }

         target.setStringValue(cachingStrategy);
      }
   }

   public void xsetCachingStrategy(XmlString cachingStrategy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CACHINGSTRATEGY$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CACHINGSTRATEGY$8);
         }

         target.set(cachingStrategy);
      }
   }

   public void unsetCachingStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHINGSTRATEGY$8, 0);
      }
   }
}
