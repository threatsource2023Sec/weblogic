package com.bea.common.security.service;

import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public interface SecurityTokenService {
   Object issueToken(String var1, Identity var2, Identity var3, Resource var4, ContextHandler var5);

   TokenResponseContext challengeIssueToken(ServiceTokenRequestContext var1);
}
