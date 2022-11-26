package weblogic.messaging.kernel;

import weblogic.messaging.Message;

public interface UOWCallbackCaller {
   void expire(Message var1, boolean var2);

   void expireAll();

   void deleteAll();

   void setUserData(Object var1);

   Object getUserData();
}
