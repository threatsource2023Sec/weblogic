package weblogic.servlet.tools;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.Environment;
import weblogic.servlet.internal.War;
import weblogic.servlet.internal.WebAppHelper;
import weblogic.servlet.internal.WebBaseModuleExtensionContext;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;

public class WARModuleExtensionContext extends WebBaseModuleExtensionContext {
   private War war;
   private WARModule module;

   private WARModuleExtensionContext(WARModule module, War war) {
      super((ApplicationContextInternal)null, module.getState(), module, (Environment)null);
      this.war = war;
      this.module = module;
   }

   public static WARModuleExtensionContext getInstance(WARModule module, War war) {
      return new WARModuleExtensionContext(module, war);
   }

   public GenericClassLoader getTemporaryClassLoader() {
      return this.module.getTemporaryClassLoader();
   }

   protected ClassFinder getResourceFinder(String relativeURI) {
      return this.war.getResourceFinder(relativeURI);
   }

   protected ClassLoader getClassLoader() {
      return this.module.getState().getClassLoader();
   }

   public WebAppHelper getWebAppHelper() {
      return this.war;
   }
}
