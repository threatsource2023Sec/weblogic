package weblogic.servlet.utils;

import java.util.HashSet;
import java.util.Set;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.wl.LoggingBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.logging.j2ee.ServletContextLogger;
import weblogic.management.ManagementException;
import weblogic.management.configuration.WebServerMBean;
import weblogic.management.runtime.WLDFAccessRuntimeMBean;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.WebAppModule;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.HttpServerManager;
import weblogic.servlet.spi.ManagementProvider;
import weblogic.servlet.spi.WebServerRegistry;

public class ServletAccessorHelper {
   public static final String WEBAPP_LOG = "WebAppLog";

   public static Set getLogicalNamesForWebApps(WebServerMBean webServer) {
      String webServerName = webServer.getName();
      HttpServer httpServer = WebServerRegistry.getInstance().getHttpServerManager().getHttpServer(webServerName);
      HashSet logicalNames = new HashSet();
      WebAppServletContext[] servletContexts = httpServer != null ? httpServer.getServletContextManager().getAllContexts() : null;
      if (servletContexts != null) {
         for(int i = 0; i < servletContexts.length; ++i) {
            WebAppServletContext servletContext = servletContexts[i];
            if (servletContext != null) {
               WebAppModule webAppModule = servletContext.getWebAppModule();
               if (webAppModule != null) {
                  WeblogicWebAppBean wlsWebAppBean = webAppModule.getWlWebAppBean();
                  if (wlsWebAppBean != null) {
                     LoggingBean loggingBean = (LoggingBean)DescriptorUtils.getFirstChildOrDefaultBean(wlsWebAppBean, wlsWebAppBean.getLoggings(), "Logging");
                     if (loggingBean != null && loggingBean.getLogFilename() != null) {
                        String contextPath = servletContext.getContextPath();
                        if (contextPath != null) {
                           logicalNames.add(getLogicalName(webServerName, contextPath));
                        }
                     }
                  }
               }
            }
         }
      }

      return logicalNames;
   }

   public static String getLogFileName(String webServerName, String contextPath) {
      ServletContextLogger servletContextLogger = getServletContextLogger(webServerName, contextPath);
      return servletContextLogger == null ? null : servletContextLogger.getLogFilePath();
   }

   public static String getAccessLogFilePath(String webServerName) {
      HttpServerManager httpSrvManager = WebServerRegistry.getInstance().getHttpServerManager();
      HttpServer httpServer = httpSrvManager.getHttpServer(webServerName);
      return httpServer == null ? null : httpServer.getLogManager().getLogFilePath();
   }

   public static String getAccessLogRotationDirPath(String webServerName) {
      HttpServerManager httpSrvManager = WebServerRegistry.getInstance().getHttpServerManager();
      HttpServer httpServer = httpSrvManager.getHttpServer(webServerName);
      return httpServer == null ? null : httpServer.getLogManager().getLogRotationDir();
   }

   public static String getLogFileRotationDir(String webServerName, String contextPath) {
      ServletContextLogger servletContextLogger = getServletContextLogger(webServerName, contextPath);
      return servletContextLogger == null ? null : servletContextLogger.getLogRotationDirPath();
   }

   public static void removeAccessor(ManagementProvider managementProvider, String partitionName, String webServerName, String contextPath) throws ManagementException {
      WLDFAccessRuntimeMBean accessRuntime = null;
      if (partitionName == null) {
         accessRuntime = managementProvider.getWLDFAccessRuntime();
      } else {
         accessRuntime = managementProvider.getWLDFPartitionAccessRuntime(partitionName);
      }

      if (accessRuntime != null) {
         ((WLDFAccessRuntimeMBean)accessRuntime).removeAccessor(getLogicalName(webServerName, contextPath));
      }

   }

   private static ServletContextLogger getServletContextLogger(String webServerName, String contextPath) {
      HttpServerManager httpSrvManager = WebServerRegistry.getInstance().getHttpServerManager();
      HttpServer httpServer = httpSrvManager.getHttpServer(webServerName);
      if (httpServer == null) {
         return null;
      } else {
         WebAppServletContext servletContext = httpServer.getServletContextManager().getContextForContextPath(contextPath);
         return servletContext == null ? null : servletContext.getServletContextLogger();
      }
   }

   private static String getLogicalName(String webServerName, String contextPath) {
      return contextPath.length() > 0 && contextPath.charAt(0) == '/' ? "WebAppLog/" + webServerName + contextPath : "WebAppLog/" + webServerName + '/' + contextPath;
   }
}
