package com.bea.common.security.spi;

import javax.security.auth.callback.CallbackHandler;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.IdentityAssertionException;

public interface ChallengeIdentityAsserter {
   Object getChallengeToken(String var1);

   Object getChallengeToken(String var1, ContextHandler var2);

   ChallengeContext assertChallengeIdentity(String var1, Object var2, ContextHandler var3) throws IdentityAssertionException;

   public interface ChallengeContext {
      boolean hasChallengeIdentityCompleted();

      Object getChallengeToken();

      CallbackHandler getCallbackHandler();

      void continueChallengeIdentity(String var1, Object var2, ContextHandler var3) throws IdentityAssertionException;
   }
}
