package weblogic.j2ee.descriptor;

public interface MultipartConfigBean {
   String getLocation();

   void setLocation(String var1);

   boolean isLocationSet();

   long getMaxFileSize();

   void setMaxFileSize(long var1);

   boolean isMaxFileSizeSet();

   long getMaxRequestSize();

   void setMaxRequestSize(long var1);

   boolean isMaxRequestSizeSet();

   int getFileSizeThreshold();

   void setFileSizeThreshold(int var1);

   boolean isFileSizeThresholdSet();
}
