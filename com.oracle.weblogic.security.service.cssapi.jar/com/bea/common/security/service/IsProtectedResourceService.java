package com.bea.common.security.service;

import weblogic.security.spi.Resource;

public interface IsProtectedResourceService {
   boolean isProtectedResource(Identity var1, Resource var2);
}
