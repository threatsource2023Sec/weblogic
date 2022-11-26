package weblogic.security.service;

import com.bea.core.security.managers.NotSupportedException;
import java.util.Map;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class PrincipalAuthenticator implements SecurityService {
   public void initialize(String realmName) {
      throw new NotSupportedException();
   }

   public void start() {
      throw new NotSupportedException();
   }

   public void suspend() {
      throw new NotSupportedException();
   }

   public void shutdown() {
      throw new NotSupportedException();
   }

   public PrincipalAuthenticator() {
   }

   public PrincipalAuthenticator(String realmName) {
      throw new NotSupportedException();
   }

   public AuthenticatedSubject authenticate(CallbackHandler callbackHandler, ContextHandler contextHandler) throws LoginException {
      throw new NotSupportedException();
   }

   public AuthenticatedSubject authenticate(CallbackHandler callbackHandler) throws LoginException {
      return this.authenticate(callbackHandler, (ContextHandler)null);
   }

   /** @deprecated */
   @Deprecated
   public Map getAssertionsEncodingMap() {
      throw new NotSupportedException();
   }

   public boolean doesTokenTypeRequireBase64Decoding(String tokenType) {
      throw new NotSupportedException();
   }

   public boolean doesTokenRequireBase64Decoding(Object o) {
      throw new NotSupportedException();
   }

   public AuthenticatedSubject assertIdentity(String tokenType, Object token) throws LoginException {
      throw new NotSupportedException();
   }

   public AuthenticatedSubject assertIdentity(String tokenType, Object token, ContextHandler contextHandler) throws LoginException {
      throw new NotSupportedException();
   }

   public boolean isTokenTypeSupported(String tokenType) {
      throw new NotSupportedException();
   }

   public AuthenticatedSubject impersonateIdentity(String username) throws LoginException {
      throw new NotSupportedException();
   }

   public AuthenticatedSubject impersonateIdentity(String username, ContextHandler contextHandler) throws LoginException {
      throw new NotSupportedException();
   }

   public boolean validateIdentity(AuthenticatedSubject subject) {
      throw new NotSupportedException();
   }

   public Object getChallengeToken(String tokenType, ContextHandler handler) throws IdentityAssertionException {
      throw new NotSupportedException();
   }

   public Object getChallengeToken(String tokenType) throws IdentityAssertionException {
      throw new NotSupportedException();
   }

   public ChallengeContext assertChallengeIdentity(String tokenType, Object token, ContextHandler contextHandler) throws LoginException {
      throw new NotSupportedException();
   }

   public void continueChallengeIdentity(ChallengeContext context, String tokenType, Object token, ContextHandler handler) throws LoginException {
      throw new NotSupportedException();
   }

   public byte[] getPasswordDigest(String username, byte[] nonce, String created) throws DigestNotAvailableException {
      throw new NotSupportedException();
   }

   public byte[] getDerivedKey(String username, byte[] salt, int iteration) throws DigestNotAvailableException {
      throw new NotSupportedException();
   }

   public UserLockoutManager getUserLockoutManager() {
      throw new NotSupportedException();
   }
}
