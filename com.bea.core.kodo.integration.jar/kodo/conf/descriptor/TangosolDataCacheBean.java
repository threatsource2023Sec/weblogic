package kodo.conf.descriptor;

public interface TangosolDataCacheBean extends DataCacheBean {
   boolean getClearOnClose();

   void setClearOnClose(boolean var1);

   String getTangosolCacheType();

   void setTangosolCacheType(String var1);

   String getTangosolCacheName();

   void setTangosolCacheName(String var1);

   String getEvictionSchedule();

   void setEvictionSchedule(String var1);
}
