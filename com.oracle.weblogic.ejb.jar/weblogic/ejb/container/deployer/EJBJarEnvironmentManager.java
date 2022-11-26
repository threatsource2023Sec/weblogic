package weblogic.ejb.container.deployer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.naming.BindingsFactory;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.Environment;
import weblogic.ejb.container.EJBDebugService;

final class EJBJarEnvironmentManager implements EnvironmentManager {
   private final String appId;
   private final String moduleId;
   private final String moduleName;
   private final Context appNamingRootContext;
   private final Context moduleCtx;
   private final Context moduleNSCtx;
   private final Map envMap = new HashMap();

   EJBJarEnvironmentManager(ApplicationContextInternal appCtx, String moduleName, String moduleURI, String moduleId) {
      this.appNamingRootContext = appCtx.getRootContext();
      this.appId = appCtx.getApplicationId();
      this.moduleName = moduleName;
      this.moduleId = moduleId;

      try {
         Context ejbCtx = (Context)appCtx.getEnvContext().lookup("ejb");
         this.moduleCtx = ejbCtx.createSubcontext(EnvUtils.normalizeJarName(moduleURI));
         this.moduleNSCtx = this.moduleCtx.createSubcontext("module");
         this.moduleNSCtx.createSubcontext("env");
         ModuleContext mc = appCtx.getModuleContext(moduleId);
         if (mc != null) {
            mc.getRegistry().put(Context.class.getName(), this.moduleCtx);
         }

      } catch (NamingException var7) {
         throw new AssertionError(var7);
      }
   }

   public Environment createEnvironmentFor(String name) throws NamingException {
      Context compCtx = null;

      try {
         compCtx = this.moduleCtx.createSubcontext("#" + name);
      } catch (NamingException var4) {
         throw new AssertionError(var4);
      }

      Environment env = BindingsFactory.getInstance().createEjbEnvironment(compCtx, this.appId, this.moduleName, this.moduleId, name, EJBDebugService.deploymentLogger, this.appNamingRootContext, this.moduleNSCtx);
      this.envMap.put(name, env);
      return env;
   }

   public Environment getEnvironmentFor(String name) {
      return (Environment)this.envMap.get(name);
   }

   public Collection getEnvironments() {
      return this.envMap.values();
   }

   public void cleanup() {
      Iterator var1 = this.envMap.values().iterator();

      while(var1.hasNext()) {
         Environment e = (Environment)var1.next();
         e.destroy();
      }

      this.envMap.clear();
   }
}
