package javax.security.auth.message;

import java.util.Map;

public interface MessageInfo {
   Object getRequestMessage();

   Object getResponseMessage();

   void setRequestMessage(Object var1);

   void setResponseMessage(Object var1);

   Map getMap();
}
