package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class StatefulSessionDescriptorMBeanImpl extends XMLElementMBeanDelegate implements StatefulSessionDescriptorMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_allowRemoveDuringTransaction = false;
   private boolean allowRemoveDuringTransaction = false;
   private boolean isSet_persistentStoreDir = false;
   private String persistentStoreDir;
   private boolean isSet_statefulSessionCache = false;
   private StatefulSessionCacheMBean statefulSessionCache;
   private boolean isSet_allowConcurrentCalls = false;
   private boolean allowConcurrentCalls = false;
   private boolean isSet_statefulSessionClustering = false;
   private StatefulSessionClusteringMBean statefulSessionClustering;

   public boolean getAllowRemoveDuringTransaction() {
      return this.allowRemoveDuringTransaction;
   }

   public void setAllowRemoveDuringTransaction(boolean value) {
      boolean old = this.allowRemoveDuringTransaction;
      this.allowRemoveDuringTransaction = value;
      this.isSet_allowRemoveDuringTransaction = true;
      this.checkChange("allowRemoveDuringTransaction", old, this.allowRemoveDuringTransaction);
   }

   public String getPersistentStoreDir() {
      return this.persistentStoreDir;
   }

   public void setPersistentStoreDir(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.persistentStoreDir;
      this.persistentStoreDir = value;
      this.isSet_persistentStoreDir = value != null;
      this.checkChange("persistentStoreDir", old, this.persistentStoreDir);
   }

   public StatefulSessionCacheMBean getStatefulSessionCache() {
      return this.statefulSessionCache;
   }

   public void setStatefulSessionCache(StatefulSessionCacheMBean value) {
      StatefulSessionCacheMBean old = this.statefulSessionCache;
      this.statefulSessionCache = value;
      this.isSet_statefulSessionCache = value != null;
      this.checkChange("statefulSessionCache", old, this.statefulSessionCache);
   }

   public boolean getAllowConcurrentCalls() {
      return this.allowConcurrentCalls;
   }

   public void setAllowConcurrentCalls(boolean value) {
      boolean old = this.allowConcurrentCalls;
      this.allowConcurrentCalls = value;
      this.isSet_allowConcurrentCalls = true;
      this.checkChange("allowConcurrentCalls", old, this.allowConcurrentCalls);
   }

   public StatefulSessionClusteringMBean getStatefulSessionClustering() {
      return this.statefulSessionClustering;
   }

   public void setStatefulSessionClustering(StatefulSessionClusteringMBean value) {
      StatefulSessionClusteringMBean old = this.statefulSessionClustering;
      this.statefulSessionClustering = value;
      this.isSet_statefulSessionClustering = value != null;
      this.checkChange("statefulSessionClustering", old, this.statefulSessionClustering);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<stateful-session-descriptor");
      result.append(">\n");
      if (null != this.getStatefulSessionCache()) {
         result.append(this.getStatefulSessionCache().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getPersistentStoreDir()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<persistent-store-dir>").append(this.getPersistentStoreDir()).append("</persistent-store-dir>\n");
      }

      if (null != this.getStatefulSessionClustering()) {
         result.append(this.getStatefulSessionClustering().toXML(indentLevel + 2)).append("\n");
      }

      if (this.isSet_allowConcurrentCalls || this.getAllowConcurrentCalls()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<allow-concurrent-calls>").append(ToXML.capitalize(Boolean.valueOf(this.getAllowConcurrentCalls()).toString())).append("</allow-concurrent-calls>\n");
      }

      if (this.isSet_allowRemoveDuringTransaction || this.getAllowRemoveDuringTransaction()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<allow-remove-during-transaction>").append(ToXML.capitalize(Boolean.valueOf(this.getAllowRemoveDuringTransaction()).toString())).append("</allow-remove-during-transaction>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</stateful-session-descriptor>\n");
      return result.toString();
   }
}
