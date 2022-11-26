package com.bea.common.security.service;

import java.util.List;
import java.util.Set;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;
import weblogic.security.spi.Result;

public interface BulkAdjudicationService {
   boolean adjudicate(Result[] var1, Resource var2, ContextHandler var3);

   Set adjudicate(List var1, List var2, ContextHandler var3);
}
