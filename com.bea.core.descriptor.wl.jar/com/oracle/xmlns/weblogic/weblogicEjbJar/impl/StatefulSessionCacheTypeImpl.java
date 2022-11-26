package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.CacheTypeType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.StatefulSessionCacheType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class StatefulSessionCacheTypeImpl extends XmlComplexContentImpl implements StatefulSessionCacheType {
   private static final long serialVersionUID = 1L;
   private static final QName MAXBEANSINCACHE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "max-beans-in-cache");
   private static final QName IDLETIMEOUTSECONDS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "idle-timeout-seconds");
   private static final QName SESSIONTIMEOUTSECONDS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "session-timeout-seconds");
   private static final QName CACHETYPE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "cache-type");
   private static final QName ID$8 = new QName("", "id");

   public StatefulSessionCacheTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdNonNegativeIntegerType getMaxBeansInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(MAXBEANSINCACHE$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxBeansInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXBEANSINCACHE$0) != 0;
      }
   }

   public void setMaxBeansInCache(XsdNonNegativeIntegerType maxBeansInCache) {
      this.generatedSetterHelperImpl(maxBeansInCache, MAXBEANSINCACHE$0, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewMaxBeansInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(MAXBEANSINCACHE$0);
         return target;
      }
   }

   public void unsetMaxBeansInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXBEANSINCACHE$0, 0);
      }
   }

   public XsdNonNegativeIntegerType getIdleTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(IDLETIMEOUTSECONDS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIdleTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IDLETIMEOUTSECONDS$2) != 0;
      }
   }

   public void setIdleTimeoutSeconds(XsdNonNegativeIntegerType idleTimeoutSeconds) {
      this.generatedSetterHelperImpl(idleTimeoutSeconds, IDLETIMEOUTSECONDS$2, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewIdleTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(IDLETIMEOUTSECONDS$2);
         return target;
      }
   }

   public void unsetIdleTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IDLETIMEOUTSECONDS$2, 0);
      }
   }

   public XsdNonNegativeIntegerType getSessionTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(SESSIONTIMEOUTSECONDS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSessionTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SESSIONTIMEOUTSECONDS$4) != 0;
      }
   }

   public void setSessionTimeoutSeconds(XsdNonNegativeIntegerType sessionTimeoutSeconds) {
      this.generatedSetterHelperImpl(sessionTimeoutSeconds, SESSIONTIMEOUTSECONDS$4, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewSessionTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(SESSIONTIMEOUTSECONDS$4);
         return target;
      }
   }

   public void unsetSessionTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SESSIONTIMEOUTSECONDS$4, 0);
      }
   }

   public CacheTypeType getCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CacheTypeType target = null;
         target = (CacheTypeType)this.get_store().find_element_user(CACHETYPE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHETYPE$6) != 0;
      }
   }

   public void setCacheType(CacheTypeType cacheType) {
      this.generatedSetterHelperImpl(cacheType, CACHETYPE$6, 0, (short)1);
   }

   public CacheTypeType addNewCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CacheTypeType target = null;
         target = (CacheTypeType)this.get_store().add_element_user(CACHETYPE$6);
         return target;
      }
   }

   public void unsetCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHETYPE$6, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$8) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$8);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$8);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$8);
      }
   }
}
