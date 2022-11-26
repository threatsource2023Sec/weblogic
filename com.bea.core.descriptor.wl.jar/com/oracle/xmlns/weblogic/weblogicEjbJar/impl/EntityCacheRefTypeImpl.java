package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ConcurrencyStrategyType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.EntityCacheNameType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.EntityCacheRefType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TrueFalseType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import com.sun.java.xml.ns.javaee.XsdPositiveIntegerType;
import javax.xml.namespace.QName;

public class EntityCacheRefTypeImpl extends XmlComplexContentImpl implements EntityCacheRefType {
   private static final long serialVersionUID = 1L;
   private static final QName ENTITYCACHENAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "entity-cache-name");
   private static final QName IDLETIMEOUTSECONDS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "idle-timeout-seconds");
   private static final QName READTIMEOUTSECONDS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "read-timeout-seconds");
   private static final QName CONCURRENCYSTRATEGY$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "concurrency-strategy");
   private static final QName CACHEBETWEENTRANSACTIONS$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "cache-between-transactions");
   private static final QName ESTIMATEDBEANSIZE$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "estimated-bean-size");
   private static final QName ID$12 = new QName("", "id");

   public EntityCacheRefTypeImpl(SchemaType sType) {
      super(sType);
   }

   public EntityCacheNameType getEntityCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityCacheNameType target = null;
         target = (EntityCacheNameType)this.get_store().find_element_user(ENTITYCACHENAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setEntityCacheName(EntityCacheNameType entityCacheName) {
      this.generatedSetterHelperImpl(entityCacheName, ENTITYCACHENAME$0, 0, (short)1);
   }

   public EntityCacheNameType addNewEntityCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityCacheNameType target = null;
         target = (EntityCacheNameType)this.get_store().add_element_user(ENTITYCACHENAME$0);
         return target;
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

   public XsdNonNegativeIntegerType getReadTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(READTIMEOUTSECONDS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetReadTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(READTIMEOUTSECONDS$4) != 0;
      }
   }

   public void setReadTimeoutSeconds(XsdNonNegativeIntegerType readTimeoutSeconds) {
      this.generatedSetterHelperImpl(readTimeoutSeconds, READTIMEOUTSECONDS$4, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewReadTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(READTIMEOUTSECONDS$4);
         return target;
      }
   }

   public void unsetReadTimeoutSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(READTIMEOUTSECONDS$4, 0);
      }
   }

   public ConcurrencyStrategyType getConcurrencyStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrencyStrategyType target = null;
         target = (ConcurrencyStrategyType)this.get_store().find_element_user(CONCURRENCYSTRATEGY$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConcurrencyStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONCURRENCYSTRATEGY$6) != 0;
      }
   }

   public void setConcurrencyStrategy(ConcurrencyStrategyType concurrencyStrategy) {
      this.generatedSetterHelperImpl(concurrencyStrategy, CONCURRENCYSTRATEGY$6, 0, (short)1);
   }

   public ConcurrencyStrategyType addNewConcurrencyStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrencyStrategyType target = null;
         target = (ConcurrencyStrategyType)this.get_store().add_element_user(CONCURRENCYSTRATEGY$6);
         return target;
      }
   }

   public void unsetConcurrencyStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONCURRENCYSTRATEGY$6, 0);
      }
   }

   public TrueFalseType getCacheBetweenTransactions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(CACHEBETWEENTRANSACTIONS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCacheBetweenTransactions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHEBETWEENTRANSACTIONS$8) != 0;
      }
   }

   public void setCacheBetweenTransactions(TrueFalseType cacheBetweenTransactions) {
      this.generatedSetterHelperImpl(cacheBetweenTransactions, CACHEBETWEENTRANSACTIONS$8, 0, (short)1);
   }

   public TrueFalseType addNewCacheBetweenTransactions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(CACHEBETWEENTRANSACTIONS$8);
         return target;
      }
   }

   public void unsetCacheBetweenTransactions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHEBETWEENTRANSACTIONS$8, 0);
      }
   }

   public XsdPositiveIntegerType getEstimatedBeanSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().find_element_user(ESTIMATEDBEANSIZE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEstimatedBeanSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ESTIMATEDBEANSIZE$10) != 0;
      }
   }

   public void setEstimatedBeanSize(XsdPositiveIntegerType estimatedBeanSize) {
      this.generatedSetterHelperImpl(estimatedBeanSize, ESTIMATEDBEANSIZE$10, 0, (short)1);
   }

   public XsdPositiveIntegerType addNewEstimatedBeanSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().add_element_user(ESTIMATEDBEANSIZE$10);
         return target;
      }
   }

   public void unsetEstimatedBeanSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ESTIMATEDBEANSIZE$10, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$12) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$12);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$12);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$12);
      }
   }
}
