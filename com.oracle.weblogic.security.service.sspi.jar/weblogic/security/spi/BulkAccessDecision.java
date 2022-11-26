package weblogic.security.spi;

import java.util.List;
import java.util.Map;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;

public interface BulkAccessDecision {
   Map isAccessAllowed(Subject var1, Map var2, List var3, ContextHandler var4, Direction var5) throws InvalidPrincipalException;
}
