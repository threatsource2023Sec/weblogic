package weblogic.security.service;

import weblogic.security.acl.internal.AuthenticatedSubject;

public interface ChallengeContext {
   boolean hasChallengeIdentityCompleted();

   AuthenticatedSubject getAuthenticatedSubject();

   Object getChallengeToken();
}
