package weblogic.servlet.proxy;

import java.io.PrintStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RoutingHandler {
   void init(HttpClusterServlet var1);

   void destroy();

   HttpClusterServlet.RequestInfo route(HttpClusterServlet.RequestInfo var1, HttpServletRequest var2) throws Exception;

   void addRequestHeaders(HttpServletRequest var1, PrintStream var2, HttpClusterServlet.RequestInfo var3, HttpClusterServlet.Server var4);

   boolean handleResponseHeader(HttpServletResponse var1, String var2, String var3, HttpClusterServlet.RequestInfo var4);

   void notifyRoutingDecision(HttpClusterServlet.Server var1, Object var2);
}
