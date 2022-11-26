package weblogic.cacheprovider.coherence;

import java.util.ArrayList;
import java.util.List;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.coherence.container.server.CoherenceCacheConfigModule;
import weblogic.coherence.descriptor.CoherenceClusterDescriptorLoader;
import weblogic.coherence.descriptor.wl.WeblogicCoherenceBean;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceCacheConfigMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.utils.classloaders.GenericClassLoader;

public class CoherenceClusterModule implements Module {
   private final String uri;
   private WeblogicCoherenceBean bean;
   private Module[] childModules;

   public CoherenceClusterModule(String uri, CoherenceClusterSystemResourceMBean mbean) {
      this.uri = uri;
      CoherenceCacheConfigMBean[] cacheConfigMBeans = mbean.getCoherenceCacheConfigs() == null ? new CoherenceCacheConfigMBean[0] : mbean.getCoherenceCacheConfigs();
      List listModules = new ArrayList(cacheConfigMBeans.length);
      ServerMBean localServer = CoherenceClusterManager.getServerMBean();

      for(int i = 0; i < cacheConfigMBeans.length; ++i) {
         if (this.isValid(localServer, cacheConfigMBeans[i].getTargets())) {
            listModules.add(new CoherenceCacheConfigModule(uri, cacheConfigMBeans[i]));
         }
      }

      this.childModules = (Module[])listModules.toArray(new CoherenceCacheConfigModule[0]);
   }

   public String getId() {
      return this.uri;
   }

   public String getModuleURI() {
      return this.uri;
   }

   public String getType() {
      return "coherence cluster";
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return null;
   }

   public DescriptorBean[] getDescriptors() {
      return this.bean != null ? new DescriptorBean[]{(DescriptorBean)this.bean} : new DescriptorBean[0];
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      Module[] var4 = this.childModules;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Module module = var4[var6];
         module.init(appCtx, parent, reg);
      }

      return parent;
   }

   public void initUsingLoader(ApplicationContext appCtx, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      Module[] var4 = this.childModules;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Module module = var4[var6];
         module.initUsingLoader(appCtx, gcl, reg);
      }

      try {
         this.bean = CoherenceClusterDescriptorLoader.getLoader().createCoherenceDescriptor(this.uri);
      } catch (Exception var8) {
         if (var8 instanceof ModuleException) {
            throw (ModuleException)var8;
         } else {
            throw new ModuleException(var8);
         }
      }
   }

   public void prepare() throws ModuleException {
      Module[] var1 = this.childModules;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Module module = var1[var3];
         module.prepare();
      }

   }

   public void activate() throws ModuleException {
      Module[] var1 = this.childModules;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Module module = var1[var3];
         module.activate();
      }

   }

   public void start() throws ModuleException {
      Module[] var1 = this.childModules;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Module module = var1[var3];
         module.start();
      }

   }

   public void deactivate() throws ModuleException {
      Module[] var1 = this.childModules;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Module module = var1[var3];
         module.deactivate();
      }

   }

   public void unprepare() throws ModuleException {
      Module[] var1 = this.childModules;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Module module = var1[var3];
         module.unprepare();
      }

   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      Module[] var2 = this.childModules;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module module = var2[var4];
         module.destroy(reg);
      }

   }

   public void remove() throws ModuleException {
      Module[] var1 = this.childModules;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Module module = var1[var3];
         module.remove();
      }

   }

   public void adminToProduction() {
      Module[] var1 = this.childModules;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Module module = var1[var3];
         module.adminToProduction();
      }

   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws ModuleException {
      Module[] var2 = this.childModules;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module module = var2[var4];
         module.gracefulProductionToAdmin(barrier);
      }

   }

   public void forceProductionToAdmin() throws ModuleException {
      Module[] var1 = this.childModules;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Module module = var1[var3];
         module.forceProductionToAdmin();
      }

   }

   private boolean isValid(ServerMBean server, TargetMBean[] targets) {
      TargetMBean[] var3 = targets;
      int var4 = targets.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         TargetMBean target = var3[var5];
         if (target.getName().equals(server.getName())) {
            return true;
         }

         if (target instanceof ClusterMBean && server.getCluster() != null && target.getName().equals(server.getCluster().getName())) {
            return true;
         }
      }

      return false;
   }
}
