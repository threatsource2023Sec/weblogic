package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBean;

public interface PreparedStatementMBean extends XMLElementMBean {
   boolean isProfilingEnabled();

   void setProfilingEnabled(boolean var1);

   int getCacheProfilingThreshold();

   void setCacheProfilingThreshold(int var1);

   int getCacheSize();

   void setCacheSize(int var1);

   boolean isParameterLoggingEnabled();

   void setParameterLoggingEnabled(boolean var1);

   int getMaxParameterLength();

   void setMaxParameterLength(int var1);

   String getCacheType();

   void setCacheType(String var1);
}
