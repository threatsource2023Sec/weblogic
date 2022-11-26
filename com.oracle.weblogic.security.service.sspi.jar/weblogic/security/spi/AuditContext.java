package weblogic.security.spi;

import weblogic.security.service.ContextHandler;

public interface AuditContext {
   ContextHandler getContext();
}
