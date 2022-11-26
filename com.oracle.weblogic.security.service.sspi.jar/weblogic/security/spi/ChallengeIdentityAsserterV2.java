package weblogic.security.spi;

import weblogic.security.service.ContextHandler;

public interface ChallengeIdentityAsserterV2 extends IdentityAsserterV2 {
   Object getChallengeToken(String var1, ContextHandler var2);

   ProviderChallengeContext assertChallengeIdentity(String var1, Object var2, ContextHandler var3) throws IdentityAssertionException;

   void continueChallengeIdentity(ProviderChallengeContext var1, String var2, Object var3, ContextHandler var4) throws IdentityAssertionException;
}
