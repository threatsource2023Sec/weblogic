package com.bea.core.jatmi.intf;

import javax.security.auth.login.LoginException;

public interface TCSecurityService {
   void shutdown(int var1);

   void pushUser(TCAuthenticatedUser var1);

   TCAuthenticatedUser getUser();

   void popUser();

   String getAnonymousUserName();

   TCAuthenticatedUser impersonate(String var1) throws LoginException;

   TCAppKey getAppKeyGenerator(String var1, String var2, String var3, boolean var4, int var5);

   int getSecProviderId();

   String getSecProviderName();
}
