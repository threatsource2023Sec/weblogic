package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EntityCacheMBeanImpl extends XMLElementMBeanDelegate implements EntityCacheMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_cachingStrategy = false;
   private String cachingStrategy = "MultiVersion";
   private boolean isSet_entityCacheName = false;
   private String entityCacheName;
   private boolean isSet_maxBeansInCache = false;
   private int maxBeansInCache = 1000;
   private boolean isSet_maxCacheSize = false;
   private MaxCacheSizeMBean maxCacheSize;

   public String getCachingStrategy() {
      return this.cachingStrategy;
   }

   public void setCachingStrategy(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.cachingStrategy;
      this.cachingStrategy = value;
      this.isSet_cachingStrategy = value != null;
      this.checkChange("cachingStrategy", old, this.cachingStrategy);
   }

   public String getEntityCacheName() {
      return this.entityCacheName;
   }

   public void setEntityCacheName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.entityCacheName;
      this.entityCacheName = value;
      this.isSet_entityCacheName = value != null;
      this.checkChange("entityCacheName", old, this.entityCacheName);
   }

   public int getMaxBeansInCache() {
      return this.maxBeansInCache;
   }

   public void setMaxBeansInCache(int value) {
      int old = this.maxBeansInCache;
      this.maxBeansInCache = value;
      this.isSet_maxBeansInCache = value != -1;
      this.checkChange("maxBeansInCache", old, this.maxBeansInCache);
   }

   public MaxCacheSizeMBean getMaxCacheSize() {
      return this.maxCacheSize;
   }

   public void setMaxCacheSize(MaxCacheSizeMBean value) {
      MaxCacheSizeMBean old = this.maxCacheSize;
      this.maxCacheSize = value;
      this.isSet_maxCacheSize = value != null;
      this.checkChange("maxCacheSize", old, this.maxCacheSize);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<entity-cache");
      result.append(">\n");
      if (null != this.getEntityCacheName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<entity-cache-name>").append(this.getEntityCacheName()).append("</entity-cache-name>\n");
      }

      if (null != this.getMaxCacheSize()) {
         result.append(this.getMaxCacheSize().toXML(indentLevel + 2)).append("\n");
      } else if (this.isSet_maxBeansInCache || 1000 != this.getMaxBeansInCache()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<max-beans-in-cache>").append(this.getMaxBeansInCache()).append("</max-beans-in-cache>\n");
      }

      if ((this.isSet_cachingStrategy || !"MultiVersion".equals(this.getCachingStrategy())) && null != this.getCachingStrategy()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<caching-strategy>").append(this.getCachingStrategy()).append("</caching-strategy>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</entity-cache>\n");
      return result.toString();
   }
}
