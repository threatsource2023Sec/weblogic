package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EntityDescriptorMBeanImpl extends XMLElementMBeanDelegate implements EntityDescriptorMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_persistence = false;
   private PersistenceMBean persistence;
   private boolean isSet_invalidationTarget = false;
   private InvalidationTargetMBean invalidationTarget;
   private boolean isSet_entityCache = false;
   private EntityCacheMBean entityCache;
   private boolean isSet_entityCacheRef = false;
   private EntityCacheRefMBean entityCacheRef;
   private boolean isSet_enableDynamicQueries = false;
   private boolean enableDynamicQueries = false;
   private boolean isSet_entityClustering = false;
   private EntityClusteringMBean entityClustering;
   private boolean isSet_pool = false;
   private PoolMBean pool;

   public PersistenceMBean getPersistence() {
      return this.persistence;
   }

   public void setPersistence(PersistenceMBean value) {
      PersistenceMBean old = this.persistence;
      this.persistence = value;
      this.isSet_persistence = value != null;
      this.checkChange("persistence", old, this.persistence);
   }

   public InvalidationTargetMBean getInvalidationTarget() {
      return this.invalidationTarget;
   }

   public void setInvalidationTarget(InvalidationTargetMBean value) {
      InvalidationTargetMBean old = this.invalidationTarget;
      this.invalidationTarget = value;
      this.isSet_invalidationTarget = value != null;
      this.checkChange("invalidationTarget", old, this.invalidationTarget);
   }

   public EntityCacheMBean getEntityCache() {
      return this.entityCache;
   }

   public void setEntityCache(EntityCacheMBean value) {
      EntityCacheMBean old = this.entityCache;
      this.entityCache = value;
      this.isSet_entityCache = value != null;
      this.checkChange("entityCache", old, this.entityCache);
   }

   public EntityCacheRefMBean getEntityCacheRef() {
      return this.entityCacheRef;
   }

   public void setEntityCacheRef(EntityCacheRefMBean value) {
      EntityCacheRefMBean old = this.entityCacheRef;
      this.entityCacheRef = value;
      this.isSet_entityCacheRef = value != null;
      this.checkChange("entityCacheRef", old, this.entityCacheRef);
   }

   public boolean isEnableDynamicQueries() {
      return this.enableDynamicQueries;
   }

   public void setEnableDynamicQueries(boolean value) {
      boolean old = this.enableDynamicQueries;
      this.enableDynamicQueries = value;
      this.isSet_enableDynamicQueries = true;
      this.checkChange("enableDynamicQueries", old, this.enableDynamicQueries);
   }

   public EntityClusteringMBean getEntityClustering() {
      return this.entityClustering;
   }

   public void setEntityClustering(EntityClusteringMBean value) {
      EntityClusteringMBean old = this.entityClustering;
      this.entityClustering = value;
      this.isSet_entityClustering = value != null;
      this.checkChange("entityClustering", old, this.entityClustering);
   }

   public PoolMBean getPool() {
      return this.pool;
   }

   public void setPool(PoolMBean value) {
      PoolMBean old = this.pool;
      this.pool = value;
      this.isSet_pool = value != null;
      this.checkChange("pool", old, this.pool);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<entity-descriptor");
      result.append(">\n");
      if (null != this.getPool()) {
         result.append(this.getPool().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getEntityCacheRef()) {
         result.append(this.getEntityCacheRef().toXML(indentLevel + 2)).append("\n");
      } else if (null != this.getEntityCache()) {
         result.append(this.getEntityCache().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getPersistence()) {
         result.append(this.getPersistence().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getEntityClustering()) {
         result.append(this.getEntityClustering().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getInvalidationTarget()) {
         result.append(this.getInvalidationTarget().toXML(indentLevel + 2)).append("\n");
      }

      if (this.isSet_enableDynamicQueries || this.isEnableDynamicQueries()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<enable-dynamic-queries>").append(ToXML.capitalize(Boolean.valueOf(this.isEnableDynamicQueries()).toString())).append("</enable-dynamic-queries>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</entity-descriptor>\n");
      return result.toString();
   }
}
