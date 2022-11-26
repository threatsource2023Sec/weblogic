package weblogic.elasticity.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.elasticity.i18n.ElasticityTextTextFormatter;
import weblogic.elasticity.interceptor.DatasourceInfo;
import weblogic.elasticity.interceptor.DatasourceRegistry;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.GlobalServiceLocator;

public class ElasticityUtils {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugElasticServices");
   private static final ElasticityTextTextFormatter DTF = ElasticityTextTextFormatter.getInstance();
   private static final boolean IS_WINDOWS = System.getProperty("os.name").startsWith("Win");
   private static final String WINDOWS_SYSTEM_ROOT_KEY = "SystemRoot";
   private static final String WINDOWS_PATH_KEY = "PATH";
   private static final String WIN_SYSTEM32 = "System32";
   private static final String WINDOWS_SYSTEM_ROOT = System.getenv("SystemRoot");
   private static final String WINDOWS_DEFAULT_PATH;

   public static void checkForAdminServer() {
      if (!ElasticityUtils.LazyInitializer.runtimeAccess.isAdminServer()) {
         throw new IllegalStateException(DTF.getElasticActionsNotAllowedOnManagedServersText());
      }
   }

   public static boolean isWindows() {
      return IS_WINDOWS;
   }

   public static void initScriptProcessEnvironment(ProcessBuilder pb) {
      Map inheritedEnv = pb.environment();
      inheritedEnv.clear();
      if (isWindows()) {
         if (WINDOWS_SYSTEM_ROOT != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Setting windows env vars, SystemRoot=" + WINDOWS_SYSTEM_ROOT + ", PATH=" + WINDOWS_DEFAULT_PATH);
            }

            inheritedEnv.put("SystemRoot", WINDOWS_SYSTEM_ROOT);
            inheritedEnv.put("PATH", WINDOWS_DEFAULT_PATH);
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Unable to set Windows system path, Windows system root directory not found in environment");
         }
      }

   }

   public static List findDomainDataSourceURLs() {
      List datasourceInfos = ElasticityUtils.LazyInitializer.dataSourceRegistry.findConfiguredDatasourceInfos(ElasticityUtils.LazyInitializer.runtimeAccess.getDomain());
      Set urls = new HashSet(datasourceInfos.size());
      if (datasourceInfos.size() > 0) {
         Iterator var2 = datasourceInfos.iterator();

         while(var2.hasNext()) {
            DatasourceInfo info = (DatasourceInfo)var2.next();
            urls.add(info.getUrl());
         }
      }

      return new ArrayList(urls);
   }

   static {
      WINDOWS_DEFAULT_PATH = IS_WINDOWS ? WINDOWS_SYSTEM_ROOT + File.pathSeparator + WINDOWS_SYSTEM_ROOT + File.separator + "System32" : null;
   }

   private static class LazyInitializer {
      private static final RuntimeAccess runtimeAccess = (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);
      private static final DatasourceRegistry dataSourceRegistry = (DatasourceRegistry)GlobalServiceLocator.getServiceLocator().getService(DatasourceRegistry.class, new Annotation[0]);
   }
}
