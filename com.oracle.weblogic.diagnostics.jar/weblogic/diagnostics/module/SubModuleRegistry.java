package weblogic.diagnostics.module;

import java.lang.reflect.Method;
import java.util.ArrayList;
import weblogic.application.ApplicationContext;
import weblogic.descriptor.DescriptorDiff;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.harvester.internal.HarvesterSubModule;
import weblogic.diagnostics.watch.WatchSubModule;

public final class SubModuleRegistry {
   private static Method[] allModuleResolvers = null;
   private static int[] appScopedModules = null;
   private static int[] modules = null;
   private static final Boolean NOT_APP_SCOPED = new Boolean(false);
   private static final Boolean IS_APP_SCOPED = new Boolean(true);
   private static final Object[][] moduleSpecs;
   private static SubModuleRegistry self;

   public static String getWLDFSubModuleName(WLDFSubModule subModule) {
      return ((ModuleInstance)subModule).name;
   }

   public static Class getWLDFSubModuleType(WLDFSubModule subModule) {
      return ((ModuleInstance)subModule).module.getClass();
   }

   public static WLDFSubModule[] getWLDFSubModules() {
      try {
         if (modules == null) {
            initSubModules();
         }

         return getSubModuleArray(modules);
      } catch (RuntimeException var1) {
         throw var1;
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   static WLDFSubModule[] getAppScopedWLDFSubModules() {
      try {
         if (appScopedModules == null) {
            initSubModules();
         }

         return getSubModuleArray(appScopedModules);
      } catch (RuntimeException var1) {
         throw var1;
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   private static WLDFSubModule[] getSubModuleArray(int[] subModuleIndexArray) {
      try {
         WLDFSubModule[] subModuleArray = new WLDFSubModule[subModuleIndexArray.length];

         for(int i = 0; i < subModuleIndexArray.length; ++i) {
            int specIndex = subModuleIndexArray[i];
            String name = (String)moduleSpecs[specIndex][0];
            Method instantiator = allModuleResolvers[specIndex];
            WLDFSubModule subMod = (WLDFSubModule)instantiator.invoke((Object)null);
            WLDFSubModule subMod = self.new ModuleInstance(name, subMod);
            subModuleArray[i] = subMod;
         }

         return subModuleArray;
      } catch (RuntimeException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new RuntimeException(var8);
      }
   }

   private static void initSubModules() throws Exception {
      int[] appScopedModulesTemp = new int[moduleSpecs.length];
      int appScopedModulesTempIndex = 0;
      int[] modulesTemp = new int[moduleSpecs.length];
      int modulesTempIndex = 0;
      allModuleResolvers = new Method[moduleSpecs.length];

      for(int i = 0; i < moduleSpecs.length; ++i) {
         Object[] spec = (Object[])moduleSpecs[i];
         String subModulesName = (String)spec[0];
         String subModuleClassName = (String)spec[1];
         Boolean subModuleType = (Boolean)spec[2];
         boolean isAppScoped = subModuleType;
         Class subModuleClass = null;

         try {
            subModuleClass = Class.forName(subModuleClassName);
            Method instantiator = subModuleClass.getDeclaredMethod("createInstance");
            allModuleResolvers[i] = instantiator;
            modulesTemp[modulesTempIndex] = i;
            ++modulesTempIndex;
            if (isAppScoped) {
               appScopedModulesTemp[appScopedModulesTempIndex] = i;
               ++appScopedModulesTempIndex;
            }
         } catch (RuntimeException var12) {
            throw var12;
         } catch (Exception var13) {
            throw new RuntimeException(var13);
         }
      }

      appScopedModules = new int[appScopedModulesTempIndex];
      System.arraycopy(appScopedModulesTemp, 0, appScopedModules, 0, appScopedModulesTempIndex);
      modules = new int[modulesTempIndex];
      System.arraycopy(modulesTemp, 0, modules, 0, modulesTempIndex);
   }

   static WLDFSubModule[] getPartitionScopedWLDFSubModules() {
      ArrayList subModules = new ArrayList();
      subModules.add(HarvesterSubModule.createInstance());
      subModules.add(WatchSubModule.createInstance());
      return (WLDFSubModule[])subModules.toArray(new WLDFSubModule[subModules.size()]);
   }

   static {
      moduleSpecs = new Object[][]{{"Instrumentation System WLDF Submodule", "weblogic.diagnostics.instrumentation.InstrumentationSubmodule", IS_APP_SCOPED}, {"Harvester WLDF Submodule", "weblogic.diagnostics.harvester.internal.HarvesterSubModule", NOT_APP_SCOPED}, {"Watches & Notifications WLDF Submodule", "weblogic.diagnostics.watch.WatchSubModule", NOT_APP_SCOPED}};
      self = new SubModuleRegistry();
   }

   private class ModuleInstance implements WLDFSubModule {
      String name;
      WLDFSubModule module;

      private ModuleInstance(String name, WLDFSubModule module) {
         this.name = name;
         this.module = module;
      }

      public void init(String partition, ApplicationContext appCtx, WLDFResourceBean wldfResource) throws WLDFModuleException {
         this.module.init(partition, appCtx, wldfResource);
      }

      public void prepare() throws WLDFModuleException {
         this.module.prepare();
      }

      public void activate() throws WLDFModuleException {
         this.module.activate();
      }

      public void deactivate() throws WLDFModuleException {
         this.module.deactivate();
      }

      public void unprepare() throws WLDFModuleException {
         this.module.unprepare();
      }

      public void destroy() throws WLDFModuleException {
         this.module.destroy();
      }

      public void prepareUpdate(WLDFResourceBean proposedBean, DescriptorDiff proposedUpdates) throws WLDFModuleException {
         this.module.prepareUpdate(proposedBean, proposedUpdates);
      }

      public void activateUpdate(WLDFResourceBean proposedBean, DescriptorDiff proposedUpdates) throws WLDFModuleException {
         this.module.activateUpdate(proposedBean, proposedUpdates);
      }

      public void rollbackUpdate(WLDFResourceBean proposedBean, DescriptorDiff proposedUpdates) {
         this.module.rollbackUpdate(proposedBean, proposedUpdates);
      }

      // $FF: synthetic method
      ModuleInstance(String x1, WLDFSubModule x2, Object x3) {
         this(x1, x2);
      }
   }
}
