package weblogic.security.service;

import com.bea.common.security.service.TokenResponseContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Resource;

public interface SecurityTokenServiceManager {
   Object issueToken(String var1, AuthenticatedSubject var2, AuthenticatedSubject var3, Resource var4, ContextHandler var5);

   TokenResponseContext challengeIssueToken(TokenRequestContext var1);
}
