package weblogic.security.spi;

import java.util.Map;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;

public interface AccessDecision {
   Result isAccessAllowed(Subject var1, Map var2, Resource var3, ContextHandler var4, Direction var5) throws InvalidPrincipalException;

   boolean isProtectedResource(Subject var1, Resource var2) throws InvalidPrincipalException;
}
