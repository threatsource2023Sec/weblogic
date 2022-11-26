package com.bea.common.security.service;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import weblogic.security.service.ContextHandler;

public interface IdentityImpersonationService {
   Identity impersonateIdentity(String var1, ContextHandler var2) throws LoginException;

   Identity impersonateIdentity(CallbackHandler var1, boolean var2, ContextHandler var3) throws LoginException;
}
