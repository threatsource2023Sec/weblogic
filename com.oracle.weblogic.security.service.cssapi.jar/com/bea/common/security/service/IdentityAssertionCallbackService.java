package com.bea.common.security.service;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import weblogic.security.service.ContextHandler;

public interface IdentityAssertionCallbackService {
   Identity assertIdentity(CallbackHandler var1, ContextHandler var2) throws LoginException;
}
