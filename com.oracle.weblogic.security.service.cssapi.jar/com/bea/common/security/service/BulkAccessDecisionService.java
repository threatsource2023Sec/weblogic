package com.bea.common.security.service;

import java.util.List;
import java.util.Map;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;
import weblogic.security.spi.Result;

public interface BulkAccessDecisionService {
   Result[] isAccessAllowed(Identity var1, Map var2, Resource var3, ContextHandler var4, Direction var5);

   List isAccessAllowed(Identity var1, Map var2, List var3, ContextHandler var4, Direction var5);

   String[] getAccessDecisionClassNames();
}
