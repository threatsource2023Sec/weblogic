package com.bea.common.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;

public interface BulkAuthorizationService {
   boolean isAccessAllowed(Identity var1, Map var2, Resource var3, ContextHandler var4, Direction var5);

   Set isAccessAllowed(Identity var1, Map var2, List var3, ContextHandler var4, Direction var5);
}
