package weblogic.application.internal.flow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.Extensible;
import weblogic.application.Module;
import weblogic.application.ModuleFactory;
import weblogic.application.ParentModule;
import weblogic.application.internal.ExtensibleModuleWrapper;
import weblogic.application.internal.FlowContext;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.IOUtils;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.management.DeploymentException;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class JeeModulesCreator implements ModulesCreator {
   private static final ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();

   public Module[] create(FlowContext context) throws DeploymentException {
      ApplicationBean bean = context.getApplicationDD();
      if (bean == null) {
         return new Module[0];
      } else {
         ModuleBean[] moduleBeans = this.getPartialRedeployModuleBeans(context);
         if (moduleBeans == null) {
            moduleBeans = bean.getModules();
         }

         List modules = new ArrayList();
         this.createModules(context, moduleBeans, modules);
         return (Module[])modules.toArray(new Module[modules.size()]);
      }
   }

   private ModuleBean[] getPartialRedeployModuleBeans(FlowContext context) {
      String[] uris = context.getPartialRedeployURIs();
      if (uris != null && uris.length > 0) {
         List partialRedeployModules = new ArrayList();
         String[] var4 = uris;
         int var5 = uris.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String uri = var4[var6];
            ModuleBean moduleBean = this.findStandardModuleDescriptor(context, uri);
            partialRedeployModules.add(moduleBean);
         }

         return (ModuleBean[])partialRedeployModules.toArray(new ModuleBean[partialRedeployModules.size()]);
      } else {
         return null;
      }
   }

   private void createModules(FlowContext context, ModuleBean[] moduleDD, List modules) throws DeploymentException {
      if (moduleDD != null && moduleDD.length != 0) {
         ModuleBean[] var4 = moduleDD;
         int var5 = moduleDD.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ModuleBean aModuleDD = var4[var6];
            Module m = this.createModuleFromFactories(context, aModuleDD);
            modules.add(m);
         }

      }
   }

   private Module createModuleFromFactories(FlowContext context, ModuleBean dd) throws DeploymentException {
      Iterator it = afm.getModuleFactories();

      Module m;
      do {
         if (!it.hasNext()) {
            throw new DeploymentException("Unable to create module " + EarUtils.getModuleURI(dd));
         }

         ModuleFactory mf = (ModuleFactory)it.next();
         m = mf.createModule(dd);
      } while(m == null);

      return this.wrapModule(context, m);
   }

   private Module wrapModule(FlowContext context, Module module) throws DeploymentException {
      Module wrappedModule = module;
      if (module instanceof ParentModule) {
         String extLocationUri = ((ParentModule)module).getWLExtensionDirectory();
         wrappedModule = this.createAndAddScopedCustomModules(context, module, extLocationUri);
      }

      if (module instanceof Extensible) {
         wrappedModule = new ExtensibleModuleWrapper((Module)wrappedModule, context);
      }

      return (Module)wrappedModule;
   }

   private Module createAndAddScopedCustomModules(FlowContext context, Module module, String extLocationUri) throws DeploymentException {
      ModuleBean mbean = this.findStandardModuleDescriptor(context, module.getId());
      if (mbean == null) {
         return module;
      } else {
         String uri = EarUtils.reallyGetModuleURI(mbean);
         VirtualJarFile vjf = null;

         Module var8;
         try {
            vjf = VirtualJarFactory.createVirtualJar(context.getEar().getModuleRoots(uri));
            ScopedModuleDriver var7 = new ScopedModuleDriver(module, context, uri, vjf, extLocationUri);
            return var7;
         } catch (IOException var12) {
            var8 = module;
         } finally {
            IOUtils.forceClose(vjf);
         }

         return var8;
      }
   }

   private ModuleBean findStandardModuleDescriptor(FlowContext context, String uri) {
      return this.findModuleDescriptor(context.getApplicationDD().getModules(), uri);
   }

   private ModuleBean findModuleDescriptor(ModuleBean[] moduleBeans, String uri) {
      if (moduleBeans == null) {
         return null;
      } else {
         ModuleBean[] var3 = moduleBeans;
         int var4 = moduleBeans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ModuleBean moduleBean = var3[var5];
            if (EarUtils.getModuleURI(moduleBean).equals(uri)) {
               return moduleBean;
            }
         }

         return null;
      }
   }
}
