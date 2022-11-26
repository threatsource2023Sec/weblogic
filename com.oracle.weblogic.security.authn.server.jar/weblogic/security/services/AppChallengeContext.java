package weblogic.security.services;

import javax.security.auth.Subject;

public interface AppChallengeContext {
   boolean hasChallengeIdentityCompleted();

   Subject getAuthenticatedSubject();

   Object getChallengeToken();
}
