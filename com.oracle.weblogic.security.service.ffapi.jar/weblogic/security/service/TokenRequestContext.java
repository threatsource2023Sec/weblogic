package weblogic.security.service;

import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Resource;

public interface TokenRequestContext {
   String getTokenType();

   AuthenticatedSubject getRequestor();

   AuthenticatedSubject getSecIdentity();

   Resource getResource();

   ContextHandler getContextHandler();

   TokenRequestContext getPreviousRequestContext();
}
