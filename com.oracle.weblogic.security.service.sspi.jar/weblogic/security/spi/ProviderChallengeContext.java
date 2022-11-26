package weblogic.security.spi;

import javax.security.auth.callback.CallbackHandler;

public interface ProviderChallengeContext {
   boolean hasChallengeIdentityCompleted();

   CallbackHandler getCallbackHandler();

   Object getChallengeToken();
}
