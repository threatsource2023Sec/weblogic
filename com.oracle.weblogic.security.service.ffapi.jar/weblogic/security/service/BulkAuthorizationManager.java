package weblogic.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Direction;

public interface BulkAuthorizationManager {
   Set isAccessAllowed(AuthenticatedSubject var1, Map var2, List var3, ContextHandler var4, Direction var5);

   Set isAccessAllowed(AuthenticatedSubject var1, List var2, ContextHandler var3);
}
