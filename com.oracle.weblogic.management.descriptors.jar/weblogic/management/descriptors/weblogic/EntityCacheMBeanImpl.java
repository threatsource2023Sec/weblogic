package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EntityCacheMBeanImpl extends XMLElementMBeanDelegate implements EntityCacheMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_concurrencyStrategy = false;
   private String concurrencyStrategy = "Database";
   private boolean isSet_cacheBetweenTransactions = false;
   private boolean cacheBetweenTransactions = false;
   private boolean isSet_maxBeansInCache = false;
   private int maxBeansInCache = 1000;
   private boolean isSet_readTimeoutSeconds = false;
   private int readTimeoutSeconds = 600;
   private boolean isSet_idleTimeoutSeconds = false;
   private int idleTimeoutSeconds = 600;

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

   public int getMaxBeansInCache() {
      return this.maxBeansInCache;
   }

   public void setMaxBeansInCache(int value) {
      int old = this.maxBeansInCache;
      this.maxBeansInCache = value;
      this.isSet_maxBeansInCache = value != -1;
      this.checkChange("maxBeansInCache", old, this.maxBeansInCache);
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
      result.append(ToXML.indent(indentLevel)).append("<entity-cache");
      result.append(">\n");
      if (this.isSet_maxBeansInCache || 1000 != this.getMaxBeansInCache()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<max-beans-in-cache>").append(this.getMaxBeansInCache()).append("</max-beans-in-cache>\n");
      }

      if (this.isSet_idleTimeoutSeconds || 600 != this.getIdleTimeoutSeconds()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<idle-timeout-seconds>").append(this.getIdleTimeoutSeconds()).append("</idle-timeout-seconds>\n");
      }

      if (this.isSet_readTimeoutSeconds || 600 != this.getReadTimeoutSeconds()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<read-timeout-seconds>").append(this.getReadTimeoutSeconds()).append("</read-timeout-seconds>\n");
      }

      if ((this.isSet_concurrencyStrategy || !"Database".equals(this.getConcurrencyStrategy())) && null != this.getConcurrencyStrategy()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<concurrency-strategy>").append(this.getConcurrencyStrategy()).append("</concurrency-strategy>\n");
      }

      if (this.isSet_cacheBetweenTransactions || this.getCacheBetweenTransactions()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<cache-between-transactions>").append(ToXML.capitalize(Boolean.valueOf(this.getCacheBetweenTransactions()).toString())).append("</cache-between-transactions>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</entity-cache>\n");
      return result.toString();
   }
}
