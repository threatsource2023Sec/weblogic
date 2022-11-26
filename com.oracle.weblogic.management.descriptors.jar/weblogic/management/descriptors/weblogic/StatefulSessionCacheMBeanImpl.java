package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class StatefulSessionCacheMBeanImpl extends XMLElementMBeanDelegate implements StatefulSessionCacheMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_sessionTimeoutSeconds = false;
   private int sessionTimeoutSeconds = 600;
   private boolean isSet_maxBeansInCache = false;
   private int maxBeansInCache = 1000;
   private boolean isSet_cacheType = false;
   private String cacheType = "NRU";
   private boolean isSet_idleTimeoutSeconds = false;
   private int idleTimeoutSeconds = 600;

   public int getSessionTimeoutSeconds() {
      return this.sessionTimeoutSeconds;
   }

   public void setSessionTimeoutSeconds(int value) {
      int old = this.sessionTimeoutSeconds;
      this.sessionTimeoutSeconds = value;
      this.isSet_sessionTimeoutSeconds = value != -1;
      this.checkChange("sessionTimeoutSeconds", old, this.sessionTimeoutSeconds);
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

   public String getCacheType() {
      return this.cacheType;
   }

   public void setCacheType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.cacheType;
      this.cacheType = value;
      this.isSet_cacheType = value != null;
      this.checkChange("cacheType", old, this.cacheType);
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
      result.append(ToXML.indent(indentLevel)).append("<stateful-session-cache");
      result.append(">\n");
      if (this.isSet_maxBeansInCache || 1000 != this.getMaxBeansInCache()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<max-beans-in-cache>").append(this.getMaxBeansInCache()).append("</max-beans-in-cache>\n");
      }

      if (this.isSet_idleTimeoutSeconds || 600 != this.getIdleTimeoutSeconds()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<idle-timeout-seconds>").append(this.getIdleTimeoutSeconds()).append("</idle-timeout-seconds>\n");
      }

      if (this.isSet_sessionTimeoutSeconds || 600 != this.getSessionTimeoutSeconds()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<session-timeout-seconds>").append(this.getSessionTimeoutSeconds()).append("</session-timeout-seconds>\n");
      }

      if ((this.isSet_cacheType || !"NRU".equals(this.getCacheType())) && null != this.getCacheType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<cache-type>").append(this.getCacheType()).append("</cache-type>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</stateful-session-cache>\n");
      return result.toString();
   }
}
