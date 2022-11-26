package javax.security.enterprise;

import java.security.Principal;
import java.util.Set;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityContext {
   Principal getCallerPrincipal();

   Set getPrincipalsByType(Class var1);

   boolean isCallerInRole(String var1);

   boolean hasAccessToWebResource(String var1, String... var2);

   AuthenticationStatus authenticate(HttpServletRequest var1, HttpServletResponse var2, AuthenticationParameters var3);
}
