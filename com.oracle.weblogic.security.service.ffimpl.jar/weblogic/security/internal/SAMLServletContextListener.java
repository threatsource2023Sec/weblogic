package weblogic.security.internal;

import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import weblogic.management.DeploymentException;
import weblogic.security.SecurityLogger;
import weblogic.security.shared.LoggerWrapper;
import weblogic.servlet.WebLogicServletContext;

public class SAMLServletContextListener implements ServletContextListener {
   private static final String servletClassName = "com.bea.common.security.saml.SAMLServlet";
   private static LoggerWrapper LOGGER = LoggerWrapper.getInstance("SecuritySAMLService");

   private static final void logDebug(String msg) {
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug("SAMLServletContextListener: " + msg);
      }

   }

   public void contextInitialized(ServletContextEvent sce) {
      ServletContext sc = sce.getServletContext();
      WebLogicServletContext ctx = (WebLogicServletContext)sc;
      String contextPath = ctx.getContextPath();
      if (contextPath != null && contextPath.length() != 0 && !contextPath.equals("/")) {
         String[] itsURIs = SAMLServerConfig.getConfiguredIntersiteTransferURIs(contextPath);
         if (itsURIs != null) {
            this.registerSAMLService(ctx, contextPath, "samlits", itsURIs);
         }

         String[] acsURIs = SAMLServerConfig.getConfiguredAssertionConsumerURIs(contextPath);
         if (acsURIs != null) {
            this.registerSAMLService(ctx, contextPath, "samlacs", acsURIs);
         }

         String[] arsURIs = SAMLServerConfig.getConfiguredAssertionRetrievalURIs(contextPath);
         if (arsURIs != null) {
            this.registerSAMLService(ctx, contextPath, "samlars", arsURIs);
         }

      }
   }

   public void contextDestroyed(ServletContextEvent sce) {
   }

   private void registerSAMLService(WebLogicServletContext ctx, String contextPath, String serviceName, String[] uris) {
      HashMap initParams = new HashMap();
      initParams.put("ServiceName", serviceName);
      String realmName = SAMLServerConfig.getRealmName();
      initParams.put("ServletInfoKey", realmName);

      try {
         ctx.registerServlet(serviceName, "com.bea.common.security.saml.SAMLServlet", uris, initParams, -1);
      } catch (DeploymentException var8) {
         logDebug("Exception while registering SAML service " + serviceName + " in context '" + contextPath + "': " + var8.toString());
         SecurityLogger.logExceptionRegisteringSAMLService(serviceName, contextPath, var8);
         return;
      }

      for(int i = 0; i < uris.length; ++i) {
         logDebug("Registered SAML service '" + serviceName + "' at URI '" + contextPath + uris[i] + "'");
         SecurityLogger.logRegisteredSAMLService(serviceName, uris[i], contextPath);
      }

   }
}
