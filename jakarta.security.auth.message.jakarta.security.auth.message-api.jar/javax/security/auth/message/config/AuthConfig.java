package javax.security.auth.message.config;

import javax.security.auth.message.MessageInfo;

public interface AuthConfig {
   String getMessageLayer();

   String getAppContext();

   String getAuthContextID(MessageInfo var1);

   void refresh();

   boolean isProtected();
}
