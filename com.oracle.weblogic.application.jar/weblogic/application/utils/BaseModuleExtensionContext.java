package weblogic.application.utils;

import java.util.Collection;
import java.util.Collections;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Extensible;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.naming.Environment;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;
import weblogic.utils.classloaders.GenericClassLoader;

public abstract class BaseModuleExtensionContext implements ModuleExtensionContext {
   private final Extensible extensibleModule;
   private final ApplicationContextInternal appCtx;
   private final Environment env;
   private PojoEnvironmentBean bean;
   private ModuleContext modCtx;

   public BaseModuleExtensionContext(ApplicationContextInternal appCtx, ModuleContext modCtx, Extensible extensibleModule, Environment env) {
      this.extensibleModule = extensibleModule;
      this.appCtx = appCtx;
      this.env = env;
      this.modCtx = modCtx;
   }

   public Extensible getExtensibleModule() {
      return this.extensibleModule;
   }

   public ApplicationContextInternal getApplicationContext() {
      return this.appCtx;
   }

   public Environment getEnvironment(String componentName) {
      return this.env;
   }

   public Collection getEnvironments() {
      return Collections.singleton(this.env);
   }

   public Collection getBeanClassNames() {
      return Collections.emptyList();
   }

   public ClassInfoFinder getClassInfoFinder() {
      throw new UnsupportedOperationException("This operations is not yet supported by module: " + this.getClass().getName());
   }

   public PojoEnvironmentBean getPojoEnvironmentBean() {
      return this.bean;
   }

   public void setPojoEnvironmentBean(PojoEnvironmentBean bean) {
      this.bean = bean;
   }

   public GenericClassLoader getTemporaryClassLoader() {
      return this.modCtx.getTemporaryClassLoader();
   }
}
