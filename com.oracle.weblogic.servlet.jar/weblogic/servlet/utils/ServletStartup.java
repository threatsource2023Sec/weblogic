package weblogic.servlet.utils;

import java.util.Hashtable;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import weblogic.common.T3ServicesDef;
import weblogic.common.T3StartupDef;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.StackTraceUtils;

public final class ServletStartup implements T3StartupDef {
   public void setServices(T3ServicesDef services) {
   }

   public String startup(String name, Hashtable params) throws Exception {
      ServletContext context = WebServerRegistry.getInstance().getHttpServerManager().defaultHttpServer().getServletContextManager().getDefaultContext();
      String servletName = (String)params.get("servlet");
      if (servletName == null) {
         return "no servlet argument given";
      } else {
         Servlet servlet = null;

         try {
            servlet = context.getServlet(servletName);
         } catch (ServletException var7) {
            return "servlet: " + servletName + " threw ServletException " + StackTraceUtils.throwable2StackTrace(var7);
         }

         return servlet == null ? "servlet: " + servletName + " not found" : "servlet: " + servletName + " initialized";
      }
   }
}
