package weblogic.security.service;

import java.util.Map;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.internal.SecurityMessage;
import weblogic.security.service.internal.UserLockoutAdministrationService;
import weblogic.security.spi.DigestNotAvailableException;
import weblogic.security.spi.IdentityAssertionException;

public interface PrincipalAuthenticator {
   AuthenticatedSubject authenticate(CallbackHandler var1, ContextHandler var2) throws LoginException;

   AuthenticatedSubject authenticate(CallbackHandler var1) throws LoginException;

   Map getAssertionsEncodingMap();

   Map[] getAssertionsEncodingPrecedence();

   boolean doesTokenTypeRequireBase64Decoding(String var1);

   boolean doesTokenRequireBase64Decoding(Object var1);

   AuthenticatedSubject assertIdentity(String var1, Object var2) throws LoginException;

   AuthenticatedSubject assertIdentity(String var1, Object var2, ContextHandler var3) throws LoginException;

   boolean isTokenTypeSupported(String var1);

   AuthenticatedSubject impersonateIdentity(String var1) throws LoginException;

   AuthenticatedSubject impersonateIdentity(String var1, ContextHandler var2) throws LoginException;

   AuthenticatedSubject impersonateIdentity(CallbackHandler var1, boolean var2, ContextHandler var3) throws LoginException;

   boolean validateIdentity(AuthenticatedSubject var1);

   Object getChallengeToken(String var1, ContextHandler var2) throws IdentityAssertionException;

   Object getChallengeToken(String var1) throws IdentityAssertionException;

   ChallengeContext assertChallengeIdentity(String var1, Object var2, ContextHandler var3) throws LoginException;

   void continueChallengeIdentity(ChallengeContext var1, String var2, Object var3, ContextHandler var4) throws LoginException;

   Filter[] getServletAuthenticationFilters(ServletContext var1) throws ServletException;

   void destroyServletAuthenticationFilters(Filter[] var1);

   byte[] getPasswordDigest(String var1, byte[] var2, String var3) throws DigestNotAvailableException;

   byte[] getDerivedKey(String var1, byte[] var2, int var3) throws DigestNotAvailableException;

   UserLockoutManager getUserLockoutManager();

   UserLockoutAdministrationService getUserLockoutAdministrationService();

   void receiveSecurityMessageCommon(HostID var1, SecurityMessage var2);
}
