package weblogic.j2ee.descriptor.wl;

public interface PreparedStatementBean {
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

   int getCacheType();

   void setCacheType(int var1);
}
