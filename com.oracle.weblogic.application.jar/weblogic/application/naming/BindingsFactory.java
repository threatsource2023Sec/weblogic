package weblogic.application.naming;

import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.diagnostics.debug.DebugLogger;

public final class BindingsFactory {
   private static final BindingsFactory INSTANCE = new BindingsFactory();

   private BindingsFactory() {
   }

   public static BindingsFactory getInstance() {
      return INSTANCE;
   }

   public Environment createWebAppEnvironment(Context root, String appName, String moduleName, String moduleId, DebugLogger logger, Context appNamingRootContext, Context moduleNSCtx) throws NamingException {
      return new EnvironmentBuilder(root, appName, moduleName, moduleId, moduleName, Environment.EnvType.WEBAPP, logger, appNamingRootContext, moduleNSCtx, false);
   }

   public Environment createEjbEnvironment(Context root, String appName, String moduleName, String moduleId, String compName, DebugLogger logger, Context appNamingRootContext, Context moduleNSCtx) throws NamingException {
      return new EnvironmentBuilder(root, appName, moduleName, moduleId, compName, Environment.EnvType.EJB, logger, appNamingRootContext, moduleNSCtx, false);
   }

   public Environment createManagedBeanEnvironment(Context root, String appName, String moduleName, String moduleId, String compName, DebugLogger logger, Context appNamingRootContext, Context moduleNSCtx, boolean isClient) throws NamingException {
      return new EnvironmentBuilder(root, appName, moduleName, moduleId, compName, Environment.EnvType.MANAGED_BEAN, logger, appNamingRootContext, moduleNSCtx, isClient);
   }

   public Environment createAppEnvironment(String appName, DebugLogger logger, Context appNamingRootCtx) throws NamingException {
      return new EnvironmentBuilder(appNamingRootCtx, appName, (String)null, (String)null, (String)null, Environment.EnvType.APPLICATION, logger, appNamingRootCtx, (Context)null, false);
   }

   public Environment createClientEnvironment(Context root, String appName, String moduleId, DebugLogger logger, Context appNamingRootCtx) throws NamingException {
      return new EnvironmentBuilder(root, appName, moduleId, moduleId, moduleId, Environment.EnvType.CLIENT, logger, appNamingRootCtx, root, false);
   }
}
