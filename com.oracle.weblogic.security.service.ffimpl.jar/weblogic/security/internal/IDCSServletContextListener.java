package weblogic.security.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import weblogic.management.DeploymentException;
import weblogic.security.providers.authentication.IDCSFilterAccess;
import weblogic.security.shared.LoggerWrapper;
import weblogic.servlet.WebLogicServletContext;

public class IDCSServletContextListener implements ServletContextListener {
   private static final String IDCSFilterClassName = "weblogic.security.internal.IDCSSessionSynchronizationFilter";
   private static final String IDCSFilterName = "IDCSFilter";
   private static final String[] URLPatterns = new String[]{"/*"};
   private static final List internalApps = new ArrayList(Arrays.asList("bea_wls_internal", "consoleapp", "wls-management-services", "weblogic"));
   private static LoggerWrapper LOGGER = LoggerWrapper.getInstance("SecurityAtn");

   private boolean isDebugEnabled() {
      return LOGGER != null ? LOGGER.isDebugEnabled() : false;
   }

   public void contextInitialized(ServletContextEvent sce) {
      ServletContext sc = sce.getServletContext();
      WebLogicServletContext ctx = (WebLogicServletContext)sc;
      String realmName = ctx.getSecurityRealmName();
      String applicationId = ctx.getApplicationId();

      try {
         if (isIDCSProviderConfigured(realmName)) {
            if (applicationId != null && !internalApps.contains(applicationId)) {
               HashMap initParams = new HashMap();
               initParams.put("FilterServiceKey", realmName);
               ctx.registerFilter("IDCSFilter", "weblogic.security.internal.IDCSSessionSynchronizationFilter", URLPatterns, (String[])null, initParams);
               if (this.isDebugEnabled()) {
                  LOGGER.debug("Registered filter weblogic.security.internal.IDCSSessionSynchronizationFilter for web application " + applicationId + " on realm " + realmName);
               }
            }
         } else if (this.isDebugEnabled()) {
            LOGGER.debug("weblogic.security.internal.IDCSSessionSynchronizationFilter not enabled for web application " + applicationId + " on realm " + realmName);
         }
      } catch (DeploymentException var7) {
         if (this.isDebugEnabled()) {
            LOGGER.debug("Failed to register weblogic.security.internal.IDCSSessionSynchronizationFilter for web application " + applicationId + " on realm: " + var7.getMessage());
         }
      }

   }

   public void contextDestroyed(ServletContextEvent sce) {
   }

   public static boolean isIDCSProviderConfigured(String realmName) {
      return IDCSFilterAccess.getInstance().getFilterService(realmName) != null;
   }
}
