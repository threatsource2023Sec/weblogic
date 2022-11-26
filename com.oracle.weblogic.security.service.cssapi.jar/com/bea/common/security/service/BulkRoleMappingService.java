package com.bea.common.security.service;

import java.util.List;
import java.util.Map;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public interface BulkRoleMappingService {
   Map getRoles(Identity var1, Resource var2, ContextHandler var3);

   Map getRoles(Identity var1, List var2, ContextHandler var3);
}
