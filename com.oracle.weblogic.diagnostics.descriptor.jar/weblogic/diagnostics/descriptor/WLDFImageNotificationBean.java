package weblogic.diagnostics.descriptor;

public interface WLDFImageNotificationBean extends WLDFNotificationBean {
   /** @deprecated */
   @Deprecated
   String getImageDirectory();

   /** @deprecated */
   @Deprecated
   void setImageDirectory(String var1);

   int getImageLockout();

   void setImageLockout(int var1);
}
