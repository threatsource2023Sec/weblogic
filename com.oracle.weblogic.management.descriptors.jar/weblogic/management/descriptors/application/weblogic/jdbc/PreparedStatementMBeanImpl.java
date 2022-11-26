package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class PreparedStatementMBeanImpl extends XMLElementMBeanDelegate implements PreparedStatementMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_cacheSize = false;
   private int cacheSize;
   private boolean isSet_profilingEnabled = false;
   private boolean profilingEnabled;
   private boolean isSet_maxParameterLength = false;
   private int maxParameterLength;
   private boolean isSet_parameterLoggingEnabled = false;
   private boolean parameterLoggingEnabled;
   private boolean isSet_cacheProfilingThreshold = false;
   private int cacheProfilingThreshold;
   private boolean isSet_cacheType = false;
   private String cacheType;

   public int getCacheSize() {
      return this.cacheSize;
   }

   public void setCacheSize(int value) {
      int old = this.cacheSize;
      this.cacheSize = value;
      this.isSet_cacheSize = value != -1;
      this.checkChange("cacheSize", old, this.cacheSize);
   }

   public boolean isProfilingEnabled() {
      return this.profilingEnabled;
   }

   public void setProfilingEnabled(boolean value) {
      boolean old = this.profilingEnabled;
      this.profilingEnabled = value;
      this.isSet_profilingEnabled = true;
      this.checkChange("profilingEnabled", old, this.profilingEnabled);
   }

   public int getMaxParameterLength() {
      return this.maxParameterLength;
   }

   public void setMaxParameterLength(int value) {
      int old = this.maxParameterLength;
      this.maxParameterLength = value;
      this.isSet_maxParameterLength = value != -1;
      this.checkChange("maxParameterLength", old, this.maxParameterLength);
   }

   public boolean isParameterLoggingEnabled() {
      return this.parameterLoggingEnabled;
   }

   public void setParameterLoggingEnabled(boolean value) {
      boolean old = this.parameterLoggingEnabled;
      this.parameterLoggingEnabled = value;
      this.isSet_parameterLoggingEnabled = true;
      this.checkChange("parameterLoggingEnabled", old, this.parameterLoggingEnabled);
   }

   public int getCacheProfilingThreshold() {
      return this.cacheProfilingThreshold;
   }

   public void setCacheProfilingThreshold(int value) {
      int old = this.cacheProfilingThreshold;
      this.cacheProfilingThreshold = value;
      this.isSet_cacheProfilingThreshold = value != -1;
      this.checkChange("cacheProfilingThreshold", old, this.cacheProfilingThreshold);
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

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<prepared-statement");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</prepared-statement>\n");
      return result.toString();
   }
}
