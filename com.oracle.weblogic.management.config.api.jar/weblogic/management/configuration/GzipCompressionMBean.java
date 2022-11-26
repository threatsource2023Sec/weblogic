package weblogic.management.configuration;

public interface GzipCompressionMBean extends ConfigurationMBean {
   boolean isGzipCompressionEnabled();

   void setGzipCompressionEnabled(boolean var1);

   long getGzipCompressionMinContentLength();

   void setGzipCompressionMinContentLength(long var1);

   String[] getGzipCompressionContentType();

   void setGzipCompressionContentType(String[] var1);
}
