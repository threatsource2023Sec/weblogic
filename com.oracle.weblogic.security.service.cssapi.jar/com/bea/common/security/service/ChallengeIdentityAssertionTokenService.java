package com.bea.common.security.service;

import javax.security.auth.callback.CallbackHandler;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.IdentityAssertionException;

public interface ChallengeIdentityAssertionTokenService {
   boolean isTokenTypeSupported(String var1);

   Object getChallengeToken(String var1) throws IdentityAssertionException;

   Object getChallengeToken(String var1, ContextHandler var2) throws IdentityAssertionException;

   ChallengeContext assertChallengeIdentity(String var1, Object var2, ContextHandler var3) throws IdentityAssertionException;

   public interface ChallengeContext {
      boolean hasChallengeIdentityCompleted();

      Object getChallengeToken();

      CallbackHandler getCallbackHandler();

      void continueChallengeIdentity(String var1, Object var2, ContextHandler var3) throws IdentityAssertionException;
   }
}
