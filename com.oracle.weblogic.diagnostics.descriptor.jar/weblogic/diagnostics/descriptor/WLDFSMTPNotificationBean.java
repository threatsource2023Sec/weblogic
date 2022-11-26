package weblogic.diagnostics.descriptor;

public interface WLDFSMTPNotificationBean extends WLDFNotificationBean {
   String getMailSessionJNDIName();

   void setMailSessionJNDIName(String var1);

   String getSubject();

   void setSubject(String var1);

   String getBody();

   void setBody(String var1);

   String[] getRecipients();

   void setRecipients(String[] var1);

   void addRecipient(String var1);

   void removeRecipient(String var1);
}
