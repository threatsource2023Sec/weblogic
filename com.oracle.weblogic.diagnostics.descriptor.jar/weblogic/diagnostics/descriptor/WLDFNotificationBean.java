package weblogic.diagnostics.descriptor;

public interface WLDFNotificationBean extends WLDFBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   int getTimeout();

   void setTimeout(int var1);
}
