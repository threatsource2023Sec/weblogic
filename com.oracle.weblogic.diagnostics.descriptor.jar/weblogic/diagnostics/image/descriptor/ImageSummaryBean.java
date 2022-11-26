package weblogic.diagnostics.image.descriptor;

public interface ImageSummaryBean {
   String getImageCreationDate();

   void setImageCreationDate(String var1);

   void setImageFileName(String var1);

   String getImageFileName();

   void setImageCreationElapsedTime(long var1);

   long getImageCreationElapsedTime();

   void setImageCaptureCancelled(boolean var1);

   boolean isImageCaptureCancelled();

   void setServerReleaseInfo(String var1);

   String getServerReleaseInfo();

   void setServerName(String var1);

   String getServerName();

   void setMuxerClass(String var1);

   String getMuxerClass();

   SystemPropertyBean[] getSystemProperties();

   SystemPropertyBean createSystemProperty();

   SuccessfulImageSourceBean createSuccessfulImageSource();

   SuccessfulImageSourceBean[] getSuccessfulImageSources();

   FailedImageSourceBean createFailedImageSource();

   FailedImageSourceBean[] getFailedImageSource();

   void setRequesterThreadName(String var1);

   String getRequesterThreadName();

   void setRequesterUserId(String var1);

   String getRequesterUserId();

   void setRequestStackTrace(String var1);

   String getRequestStackTrace();
}
