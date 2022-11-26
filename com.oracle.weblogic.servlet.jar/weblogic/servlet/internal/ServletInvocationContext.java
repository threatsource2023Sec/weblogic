package weblogic.servlet.internal;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.servlet.security.internal.ServletSecurityContext;
import weblogic.servlet.security.internal.WebAppSecurity;

public interface ServletInvocationContext extends ServletWorkContext {
   void logError(String var1);

   WebAppSecurity getSecurityManager();

   ServletSecurityContext getSecurityContext();

   void initOrRestoreThreadContext(HttpServletRequest var1) throws IOException;

   boolean hasFilters();

   boolean hasRequestListeners();

   FilterChainImpl getFilterChain(ServletStub var1, HttpServletRequest var2, HttpServletResponse var3) throws ServletException;
}
