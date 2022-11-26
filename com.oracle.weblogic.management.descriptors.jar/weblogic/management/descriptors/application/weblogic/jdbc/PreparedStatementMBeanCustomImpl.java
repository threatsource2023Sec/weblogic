package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class PreparedStatementMBeanCustomImpl extends XMLElementMBeanDelegate implements PreparedStatementMBean {
   private String descrEncoding;
   private String descriptorVersion;
   private Boolean profilingEnabled;
   private Integer profilingThreshold;
   private Integer cacheSize;
   private Boolean paramLoggingEnabled;
   private Integer maxParamLen;
   private String cacheType;

   public void setEncoding(String encoding) {
      this.descrEncoding = encoding;
   }

   public String getEncoding() {
      return this.descrEncoding;
   }

   public void setVersion(String version) {
      String old = this.descriptorVersion;
      this.descriptorVersion = version;
      this.checkChange("version", this.descriptorVersion, old);
   }

   public String getVersion() {
      return this.descriptorVersion;
   }

   public void setProfilingEnabled(boolean val) {
      Boolean old = this.profilingEnabled;
      this.profilingEnabled = new Boolean(val);
      this.checkChange("profilingEnabled", old, this.profilingEnabled);
   }

   public boolean isProfilingEnabled() {
      return this.profilingEnabled != null ? this.profilingEnabled : false;
   }

   public void setCacheProfilingThreshold(int val) {
      Integer old = this.profilingThreshold;
      this.profilingThreshold = new Integer(val);
      this.checkChange("cacheProfilingThreshold", old, this.profilingThreshold);
   }

   public int getCacheProfilingThreshold() {
      return this.profilingThreshold != null ? this.profilingThreshold : 10;
   }

   public void setCacheSize(int val) {
      Integer old = this.cacheSize;
      this.cacheSize = new Integer(val);
      this.checkChange("cacheSize", old, this.cacheSize);
   }

   public int getCacheSize() {
      return this.cacheSize != null ? this.cacheSize : 10;
   }

   public void setParameterLoggingEnabled(boolean val) {
      Boolean old = this.paramLoggingEnabled;
      this.paramLoggingEnabled = new Boolean(val);
      this.checkChange("parameterLoggingEnabled", old, this.paramLoggingEnabled);
   }

   public boolean isParameterLoggingEnabled() {
      return this.paramLoggingEnabled != null ? this.paramLoggingEnabled : false;
   }

   public void setMaxParameterLength(int val) {
      Integer old = this.maxParamLen;
      this.maxParamLen = new Integer(val);
      this.checkChange("maxParameterLength", old, this.maxParamLen);
   }

   public int getMaxParameterLength() {
      return this.maxParamLen != null ? this.maxParamLen : 10;
   }

   public void setCacheType(String val) {
      String old = this.cacheType;
      this.cacheType = new String(val);
      this.checkChange("cacheType", old, this.cacheType);
   }

   public String getCacheType() {
      return this.cacheType != null ? this.cacheType : "LRU";
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<prepared-statement");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<profiling-enabled>").append(ToXML.capitalize((new Boolean(this.isProfilingEnabled())).toString())).append("</profiling-enabled>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<cache-profiling-threshold>").append(this.getCacheProfilingThreshold()).append("</cache-profiling-threshold>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<cache-size>").append(this.getCacheSize()).append("</cache-size>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<parameter-logging-enabled>").append(ToXML.capitalize((new Boolean(this.isParameterLoggingEnabled())).toString())).append("</parameter-logging-enabled>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<max-parameter-length>").append(this.getMaxParameterLength()).append("</max-parameter-length>\n");
      if (null != this.getCacheType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<cache-type>").append(this.getCacheType()).append("</cache-type>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</prepared-statement>\n");
      return result.toString();
   }
}
