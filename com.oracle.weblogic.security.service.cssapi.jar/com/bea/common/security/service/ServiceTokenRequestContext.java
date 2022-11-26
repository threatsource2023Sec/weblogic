package com.bea.common.security.service;

import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public interface ServiceTokenRequestContext {
   String getTokenType();

   Identity getRequestor();

   Identity getSecIdentity();

   Resource getResource();

   ContextHandler getContextHandler();

   ServiceTokenRequestContext getPreviousRequestContext();
}
