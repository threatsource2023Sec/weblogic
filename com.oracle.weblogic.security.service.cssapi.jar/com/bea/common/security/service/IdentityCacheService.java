package com.bea.common.security.service;

import javax.security.auth.callback.CallbackHandler;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.service.ContextHandler;

public interface IdentityCacheService {
   Identity getCachedIdentity(String var1);

   Identity getCachedIdentity(IdentityDomainNames var1);

   Identity getCachedIdentity(IdentityDomainNames var1, ContextHandler var2);

   Identity getCachedIdentity(String var1, ContextHandler var2);

   Identity getCachedIdentity(CallbackHandler var1, ContextHandler var2);

   void cacheIdentity(Identity var1);

   void cacheIdentity(Identity var1, ContextHandler var2);
}
