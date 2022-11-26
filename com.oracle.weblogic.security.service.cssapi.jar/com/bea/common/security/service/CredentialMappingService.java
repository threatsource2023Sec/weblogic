package com.bea.common.security.service;

import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public interface CredentialMappingService {
   Object[] getCredentials(Identity var1, Identity var2, Resource var3, ContextHandler var4, String var5);

   Object[] getCredentials(Identity var1, String var2, Resource var3, ContextHandler var4, String var5);
}
