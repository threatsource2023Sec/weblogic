package com.bea.common.security.service;

import javax.security.auth.login.LoginException;
import weblogic.security.service.ContextHandler;

public interface IdentityAssertionService {
   boolean isTokenTypeSupported(String var1);

   Identity assertIdentity(String var1, Object var2, ContextHandler var3) throws LoginException;
}
