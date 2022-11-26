package weblogic.diagnostics.image.descriptor;

public interface FailedImageSourceBean {
   String getImageSource();

   void setImageSource(String var1);

   void setFailureExceptionStackTrace(String var1);

   String getFailureExceptionStackTrace();
}
