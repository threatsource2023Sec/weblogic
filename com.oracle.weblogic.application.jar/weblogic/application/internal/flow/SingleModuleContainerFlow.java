package weblogic.application.internal.flow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import weblogic.application.Extensible;
import weblogic.application.Module;
import weblogic.application.ParentModule;
import weblogic.application.internal.ExtensibleModuleWrapper;
import weblogic.application.internal.FlowContext;
import weblogic.application.utils.IOUtils;
import weblogic.management.DeploymentException;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class SingleModuleContainerFlow extends ModuleFilterFlow {
   public SingleModuleContainerFlow(FlowContext appCtx, Module module) throws DeploymentException {
      super(appCtx);
      ArrayList l = new ArrayList(1);
      Module wrappedModule = module;

      try {
         if (module instanceof ParentModule) {
            VirtualJarFile vjf = null;

            try {
               File stageFile = new File(appCtx.getStagingPath());
               if (!stageFile.exists()) {
                  String partitionName = appCtx.getPartitionName();
                  throw new IOException("Stage file does not exist." + (partitionName == null && partitionName.isEmpty() ? "" : " Partition " + partitionName + " may not be running."));
               }

               vjf = VirtualJarFactory.createVirtualJar(stageFile);
               wrappedModule = new ScopedModuleDriver(module, appCtx, this.getModuleUri(), vjf, ((ParentModule)module).getWLExtensionDirectory());
            } finally {
               IOUtils.forceClose(vjf);
            }
         }

         if (module instanceof Extensible) {
            wrappedModule = new ExtensibleModuleWrapper((Module)wrappedModule, appCtx);
         }

         l.add(wrappedModule);
         appCtx.setApplicationModules(this.createWrappedModules(l));
      } catch (IOException var12) {
         throw new DeploymentException(var12);
      }
   }

   private String getModuleUri() {
      return this.appCtx.getApplicationMBean() != null ? this.appCtx.getApplicationMBean().getComponents()[0].getURI() : this.appCtx.getSystemResourceMBean().getDescriptorFileName();
   }
}
