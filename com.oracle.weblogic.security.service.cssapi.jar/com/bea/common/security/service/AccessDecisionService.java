package com.bea.common.security.service;

import java.util.Map;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;
import weblogic.security.spi.Result;

public interface AccessDecisionService {
   Result[] isAccessAllowed(Identity var1, Map var2, Resource var3, ContextHandler var4, Direction var5);

   String[] getAccessDecisionClassNames();
}
