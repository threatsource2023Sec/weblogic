package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.EntityCacheRefType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.EntityCacheType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.EntityClusteringType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.EntityDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.InvalidationTargetType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.PersistenceType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.PoolType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TimerDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TrueFalseType;
import javax.xml.namespace.QName;

public class EntityDescriptorTypeImpl extends XmlComplexContentImpl implements EntityDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName POOL$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "pool");
   private static final QName TIMERDESCRIPTOR$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "timer-descriptor");
   private static final QName ENTITYCACHE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "entity-cache");
   private static final QName ENTITYCACHEREF$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "entity-cache-ref");
   private static final QName PERSISTENCE$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "persistence");
   private static final QName ENTITYCLUSTERING$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "entity-clustering");
   private static final QName INVALIDATIONTARGET$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "invalidation-target");
   private static final QName ENABLEDYNAMICQUERIES$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "enable-dynamic-queries");
   private static final QName ID$16 = new QName("", "id");

   public EntityDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PoolType getPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PoolType target = null;
         target = (PoolType)this.get_store().find_element_user(POOL$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POOL$0) != 0;
      }
   }

   public void setPool(PoolType pool) {
      this.generatedSetterHelperImpl(pool, POOL$0, 0, (short)1);
   }

   public PoolType addNewPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PoolType target = null;
         target = (PoolType)this.get_store().add_element_user(POOL$0);
         return target;
      }
   }

   public void unsetPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POOL$0, 0);
      }
   }

   public TimerDescriptorType getTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerDescriptorType target = null;
         target = (TimerDescriptorType)this.get_store().find_element_user(TIMERDESCRIPTOR$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMERDESCRIPTOR$2) != 0;
      }
   }

   public void setTimerDescriptor(TimerDescriptorType timerDescriptor) {
      this.generatedSetterHelperImpl(timerDescriptor, TIMERDESCRIPTOR$2, 0, (short)1);
   }

   public TimerDescriptorType addNewTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerDescriptorType target = null;
         target = (TimerDescriptorType)this.get_store().add_element_user(TIMERDESCRIPTOR$2);
         return target;
      }
   }

   public void unsetTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMERDESCRIPTOR$2, 0);
      }
   }

   public EntityCacheType getEntityCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityCacheType target = null;
         target = (EntityCacheType)this.get_store().find_element_user(ENTITYCACHE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEntityCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENTITYCACHE$4) != 0;
      }
   }

   public void setEntityCache(EntityCacheType entityCache) {
      this.generatedSetterHelperImpl(entityCache, ENTITYCACHE$4, 0, (short)1);
   }

   public EntityCacheType addNewEntityCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityCacheType target = null;
         target = (EntityCacheType)this.get_store().add_element_user(ENTITYCACHE$4);
         return target;
      }
   }

   public void unsetEntityCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENTITYCACHE$4, 0);
      }
   }

   public EntityCacheRefType getEntityCacheRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityCacheRefType target = null;
         target = (EntityCacheRefType)this.get_store().find_element_user(ENTITYCACHEREF$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEntityCacheRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENTITYCACHEREF$6) != 0;
      }
   }

   public void setEntityCacheRef(EntityCacheRefType entityCacheRef) {
      this.generatedSetterHelperImpl(entityCacheRef, ENTITYCACHEREF$6, 0, (short)1);
   }

   public EntityCacheRefType addNewEntityCacheRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityCacheRefType target = null;
         target = (EntityCacheRefType)this.get_store().add_element_user(ENTITYCACHEREF$6);
         return target;
      }
   }

   public void unsetEntityCacheRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENTITYCACHEREF$6, 0);
      }
   }

   public PersistenceType getPersistence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceType target = null;
         target = (PersistenceType)this.get_store().find_element_user(PERSISTENCE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCE$8) != 0;
      }
   }

   public void setPersistence(PersistenceType persistence) {
      this.generatedSetterHelperImpl(persistence, PERSISTENCE$8, 0, (short)1);
   }

   public PersistenceType addNewPersistence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceType target = null;
         target = (PersistenceType)this.get_store().add_element_user(PERSISTENCE$8);
         return target;
      }
   }

   public void unsetPersistence() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCE$8, 0);
      }
   }

   public EntityClusteringType getEntityClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityClusteringType target = null;
         target = (EntityClusteringType)this.get_store().find_element_user(ENTITYCLUSTERING$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEntityClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENTITYCLUSTERING$10) != 0;
      }
   }

   public void setEntityClustering(EntityClusteringType entityClustering) {
      this.generatedSetterHelperImpl(entityClustering, ENTITYCLUSTERING$10, 0, (short)1);
   }

   public EntityClusteringType addNewEntityClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EntityClusteringType target = null;
         target = (EntityClusteringType)this.get_store().add_element_user(ENTITYCLUSTERING$10);
         return target;
      }
   }

   public void unsetEntityClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENTITYCLUSTERING$10, 0);
      }
   }

   public InvalidationTargetType getInvalidationTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InvalidationTargetType target = null;
         target = (InvalidationTargetType)this.get_store().find_element_user(INVALIDATIONTARGET$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInvalidationTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INVALIDATIONTARGET$12) != 0;
      }
   }

   public void setInvalidationTarget(InvalidationTargetType invalidationTarget) {
      this.generatedSetterHelperImpl(invalidationTarget, INVALIDATIONTARGET$12, 0, (short)1);
   }

   public InvalidationTargetType addNewInvalidationTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InvalidationTargetType target = null;
         target = (InvalidationTargetType)this.get_store().add_element_user(INVALIDATIONTARGET$12);
         return target;
      }
   }

   public void unsetInvalidationTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INVALIDATIONTARGET$12, 0);
      }
   }

   public TrueFalseType getEnableDynamicQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLEDYNAMICQUERIES$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnableDynamicQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLEDYNAMICQUERIES$14) != 0;
      }
   }

   public void setEnableDynamicQueries(TrueFalseType enableDynamicQueries) {
      this.generatedSetterHelperImpl(enableDynamicQueries, ENABLEDYNAMICQUERIES$14, 0, (short)1);
   }

   public TrueFalseType addNewEnableDynamicQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLEDYNAMICQUERIES$14);
         return target;
      }
   }

   public void unsetEnableDynamicQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLEDYNAMICQUERIES$14, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$16) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$16);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$16);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$16);
      }
   }
}
