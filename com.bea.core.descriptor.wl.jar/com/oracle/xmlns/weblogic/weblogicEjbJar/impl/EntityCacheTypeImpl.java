package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ConcurrencyStrategyType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.EntityCacheType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TrueFalseType;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class EntityCacheTypeImpl extends XmlComplexContentImpl implements EntityCacheType {
   private static final long serialVersionUID = 1L;
   private static final QName MAXBEANSINCACHE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "max-beans-in-cache");
   private static final QName MAXQUERIESINCACHE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "max-queries-in-cache");
   private static final QName IDLETIMEOUTSECONDS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "idle-timeout-seconds");
   private static final QName READTIMEOUTSECONDS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "read-timeout-seconds");
   private static final QName CONCURRENCYSTRATEGY$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "concurrency-strategy");
   private static final QName CACHEBETWEENTRANSACTIONS$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "cache-between-transactions");
   private static final QName DISABLEREADYINSTANCES$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "disable-ready-instances");
   private static final QName ID$14 = new QName("", "id");

   public EntityCacheTypeImpl(SchemaType sType) {
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

   public XsdIntegerType getMaxQueriesInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MAXQUERIESINCACHE$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxQueriesInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXQUERIESINCACHE$2) != 0;
      }
   }

   public void setMaxQueriesInCache(XsdIntegerType maxQueriesInCache) {
      this.generatedSetterHelperImpl(maxQueriesInCache, MAXQUERIESINCACHE$2, 0, (short)1);
   }

   public XsdIntegerType addNewMaxQueriesInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MAXQUERIESINCACHE$2);
         return target;
      }
   }

   public void unsetMaxQueriesInCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXQUERIESINCACHE$2, 0);
      }
   }

   public XsdNonNegativeIntegerType getIdleTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(IDLETIMEOUTSECONDS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIdleTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IDLETIMEOUTSECONDS$4) != 0;
      }
   }

   public void setIdleTimeoutSeconds(XsdNonNegativeIntegerType idleTimeoutSeconds) {
      this.generatedSetterHelperImpl(idleTimeoutSeconds, IDLETIMEOUTSECONDS$4, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewIdleTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(IDLETIMEOUTSECONDS$4);
         return target;
      }
   }

   public void unsetIdleTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IDLETIMEOUTSECONDS$4, 0);
      }
   }

   public XsdNonNegativeIntegerType getReadTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(READTIMEOUTSECONDS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetReadTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(READTIMEOUTSECONDS$6) != 0;
      }
   }

   public void setReadTimeoutSeconds(XsdNonNegativeIntegerType readTimeoutSeconds) {
      this.generatedSetterHelperImpl(readTimeoutSeconds, READTIMEOUTSECONDS$6, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewReadTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(READTIMEOUTSECONDS$6);
         return target;
      }
   }

   public void unsetReadTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(READTIMEOUTSECONDS$6, 0);
      }
   }

   public ConcurrencyStrategyType getConcurrencyStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrencyStrategyType target = null;
         target = (ConcurrencyStrategyType)this.get_store().find_element_user(CONCURRENCYSTRATEGY$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConcurrencyStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONCURRENCYSTRATEGY$8) != 0;
      }
   }

   public void setConcurrencyStrategy(ConcurrencyStrategyType concurrencyStrategy) {
      this.generatedSetterHelperImpl(concurrencyStrategy, CONCURRENCYSTRATEGY$8, 0, (short)1);
   }

   public ConcurrencyStrategyType addNewConcurrencyStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrencyStrategyType target = null;
         target = (ConcurrencyStrategyType)this.get_store().add_element_user(CONCURRENCYSTRATEGY$8);
         return target;
      }
   }

   public void unsetConcurrencyStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONCURRENCYSTRATEGY$8, 0);
      }
   }

   public TrueFalseType getCacheBetweenTransactions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CACHEBETWEENTRANSACTIONS$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCacheBetweenTransactions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHEBETWEENTRANSACTIONS$10) != 0;
      }
   }

   public void setCacheBetweenTransactions(TrueFalseType cacheBetweenTransactions) {
      this.generatedSetterHelperImpl(cacheBetweenTransactions, CACHEBETWEENTRANSACTIONS$10, 0, (short)1);
   }

   public TrueFalseType addNewCacheBetweenTransactions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CACHEBETWEENTRANSACTIONS$10);
         return target;
      }
   }

   public void unsetCacheBetweenTransactions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHEBETWEENTRANSACTIONS$10, 0);
      }
   }

   public TrueFalseType getDisableReadyInstances() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(DISABLEREADYINSTANCES$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDisableReadyInstances() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISABLEREADYINSTANCES$12) != 0;
      }
   }

   public void setDisableReadyInstances(TrueFalseType disableReadyInstances) {
      this.generatedSetterHelperImpl(disableReadyInstances, DISABLEREADYINSTANCES$12, 0, (short)1);
   }

   public TrueFalseType addNewDisableReadyInstances() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(DISABLEREADYINSTANCES$12);
         return target;
      }
   }

   public void unsetDisableReadyInstances() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISABLEREADYINSTANCES$12, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$14) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$14);
      }
   }
}
