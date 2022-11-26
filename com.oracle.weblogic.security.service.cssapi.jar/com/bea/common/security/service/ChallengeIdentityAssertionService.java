package com.bea.common.security.service;

import javax.security.auth.login.LoginException;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.IdentityAssertionException;

public interface ChallengeIdentityAssertionService {
   boolean isTokenTypeSupported(String var1);

   Object getChallengeToken(String var1) throws IdentityAssertionException;

   Object getChallengeToken(String var1, ContextHandler var2) throws IdentityAssertionException;

   ChallengeContext assertChallengeIdentity(String var1, Object var2, ContextHandler var3) throws LoginException;

   public interface ChallengeContext {
      boolean hasChallengeIdentityCompleted();

      Object getChallengeToken();

      Identity getIdentity();

      void continueChallengeIdentity(String var1, Object var2, ContextHandler var3) throws LoginException;
   }
}
