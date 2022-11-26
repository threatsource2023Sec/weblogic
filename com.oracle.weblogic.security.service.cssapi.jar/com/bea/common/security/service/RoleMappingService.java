package com.bea.common.security.service;

import java.util.Map;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public interface RoleMappingService {
   Map getRoles(Identity var1, Resource var2, ContextHandler var3);
}
