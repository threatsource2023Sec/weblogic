package weblogic.diagnostics.descriptor;

import java.util.Properties;

public interface WLDFRESTNotificationBean extends WLDFNotificationBean {
   String NO_HTTP_AUTH = "None";
   String BASIC_HTTP_AUTH = "Basic";
   String REST_PUT_METHOD = "PUT";
   String REST_POST_METHOD = "POST";

   String getEndpointURL();

   void setEndpointURL(String var1);

   String getRestInvocationMethodType();

   void setRestInvocationMethodType(String var1);

   String getAcceptedResponseType();

   void setAcceptedResponseType(String var1);

   String getHttpAuthenticationMode();

   void setHttpAuthenticationMode(String var1);

   String getHttpAuthenticationUserName();

   void setHttpAuthenticationUserName(String var1);

   String getHttpAuthenticationPassword();

   void setHttpAuthenticationPassword(String var1);

   byte[] getHttpAuthenticationPasswordEncrypted();

   void setHttpAuthenticationPasswordEncrypted(byte[] var1);

   Properties getCustomNotificationProperties();

   void setCustomNotificationProperties(Properties var1);
}
