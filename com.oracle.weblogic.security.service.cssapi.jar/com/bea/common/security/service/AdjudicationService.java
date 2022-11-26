package com.bea.common.security.service;

import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;
import weblogic.security.spi.Result;

public interface AdjudicationService {
   boolean adjudicate(Result[] var1, Resource var2, ContextHandler var3);
}
