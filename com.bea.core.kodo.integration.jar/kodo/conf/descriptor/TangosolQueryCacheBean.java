package kodo.conf.descriptor;

public interface TangosolQueryCacheBean extends QueryCacheBean {
   boolean getClearOnClose();

   void setClearOnClose(boolean var1);

   String getTangosolCacheType();

   void setTangosolCacheType(String var1);

   String getTangosolCacheName();

   void setTangosolCacheName(String var1);
}
