package com.bea.common.security.service;

import javax.security.auth.callback.CallbackHandler;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.IdentityAssertionException;

public interface IdentityAssertionTokenService {
   boolean isTokenTypeSupported(String var1);

   CallbackHandler assertIdentity(String var1, Object var2, ContextHandler var3) throws IdentityAssertionException;
}
