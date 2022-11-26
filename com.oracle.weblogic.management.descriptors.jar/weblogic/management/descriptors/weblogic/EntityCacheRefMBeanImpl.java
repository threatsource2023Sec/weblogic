package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EntityCacheRefMBeanImpl extends XMLElementMBeanDelegate implements EntityCacheRefMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_concurrencyStrategy = false;
   private String concurrencyStrategy;
   private boolean isSet_cacheBetweenTransactions = false;
   private boolean cacheBetweenTransactions = false;
   private boolean isSet_entityCacheName = false;
   private String entityCacheName;
   private boolean isSet_estimatedBeanSize = false;
   private int estimatedBeanSize = 100;
   private boolean isSet_readTimeoutSeconds = false;
   private int readTimeoutSeconds = 600;
   private boolean isSet_idleTimeoutSeconds = false;
   private int idleTimeoutSeconds = 0;

   public String getConcurrencyStrategy() {
      return this.concurrencyStrategy;
   }

   public void setConcurrencyStrategy(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.concurrencyStrategy;
      this.concurrencyStrategy = value;
      this.isSet_concurrencyStrategy = value != null;
      this.checkChange("concurrencyStrategy", old, this.concurrencyStrategy);
   }

   public boolean getCacheBetweenTransactions() {
      return this.cacheBetweenTransactions;
   }

   public void setCacheBetweenTransactions(boolean value) {
      boolean old = this.cacheBetweenTransactions;
      this.cacheBetweenTransactions = value;
      this.isSet_cacheBetweenTransactions = true;
      this.checkChange("cacheBetweenTransactions", old, this.cacheBetweenTransactions);
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

   public int getEstimatedBeanSize() {
      return this.estimatedBeanSize;
   }

   public void setEstimatedBeanSize(int value) {
      int old = this.estimatedBeanSize;
      this.estimatedBeanSize = value;
      this.isSet_estimatedBeanSize = value != -1;
      this.checkChange("estimatedBeanSize", old, this.estimatedBeanSize);
   }

   public int getReadTimeoutSeconds() {
      return this.readTimeoutSeconds;
   }

   public void setReadTimeoutSeconds(int value) {
      int old = this.readTimeoutSeconds;
      this.readTimeoutSeconds = value;
      this.isSet_readTimeoutSeconds = value != -1;
      this.checkChange("readTimeoutSeconds", old, this.readTimeoutSeconds);
   }

   public int getIdleTimeoutSeconds() {
      return this.idleTimeoutSeconds;
   }

   public void setIdleTimeoutSeconds(int value) {
      int old = this.idleTimeoutSeconds;
      this.idleTimeoutSeconds = value;
      this.isSet_idleTimeoutSeconds = value != -1;
      this.checkChange("idleTimeoutSeconds", old, this.idleTimeoutSeconds);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<entity-cache-ref");
      result.append(">\n");
      if (null != this.getEntityCacheName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<entity-cache-name>").append(this.getEntityCacheName()).append("</entity-cache-name>\n");
      }

      if (this.isSet_readTimeoutSeconds || 600 != this.getReadTimeoutSeconds()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<read-timeout-seconds>").append(this.getReadTimeoutSeconds()).append("</read-timeout-seconds>\n");
      }

      if (null != this.getConcurrencyStrategy()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<concurrency-strategy>").append(this.getConcurrencyStrategy()).append("</concurrency-strategy>\n");
      }

      if (this.isSet_cacheBetweenTransactions || this.getCacheBetweenTransactions()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<cache-between-transactions>").append(ToXML.capitalize(Boolean.valueOf(this.getCacheBetweenTransactions()).toString())).append("</cache-between-transactions>\n");
      }

      if (this.isSet_estimatedBeanSize || 100 != this.getEstimatedBeanSize()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<estimated-bean-size>").append(this.getEstimatedBeanSize()).append("</estimated-bean-size>\n");
      }

      if (this.isSet_idleTimeoutSeconds || 0 != this.getIdleTimeoutSeconds()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<idle-timeout-seconds>").append(this.getIdleTimeoutSeconds()).append("</idle-timeout-seconds>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</entity-cache-ref>\n");
      return result.toString();
   }
}
