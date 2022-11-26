package weblogic.application.internal.flow;

import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.DeploymentContext;
import weblogic.application.Module;
import weblogic.application.ModuleNotFoundException;
import weblogic.application.ParentModule;
import weblogic.application.internal.AppDDHolder;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.IOUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class CreateModulesFlow extends ModuleFilterFlow {
   private static final ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String serverName;

   public CreateModulesFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   private void validateTargets() throws DeploymentException {
      SubDeploymentMBean[] sd = this.appCtx.getBasicDeploymentMBean().getSubDeployments();
      if (sd != null && sd.length != 0) {
         for(int i = 0; i < sd.length; ++i) {
            if (!EarUtils.isValidModule(this.appCtx, sd[i].getName())) {
               throw new DeploymentException("The application " + this.appCtx.getApplicationId() + " contains a SubDeploymentMBean with a name " + sd[i].getName() + " however there is no module in the application with that URI or context-root.");
            }
         }

      }
   }

   public void prepare() throws DeploymentException {
      Module[] modules = this.createModules();
      Module[] unprunedModules = modules;
      String[] stoppedModules = this.appCtx.getStoppedModules();
      if (stoppedModules != null) {
         List moduleList = new LinkedList();
         Module[] var5 = modules;
         int var6 = modules.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Module module = var5[var7];
            boolean found = false;

            for(int j = 0; j < stoppedModules.length && !found; ++j) {
               if (module.getId().equals(stoppedModules[j])) {
                  found = true;
               }
            }

            if (found) {
               if (this.isDebugEnabled()) {
                  this.debug("** CreateModulesFlow removed stopped module " + module.getId() + " from list of application modules");
               }
            } else {
               moduleList.add(module);
            }
         }

         modules = (Module[])((Module[])moduleList.toArray(new Module[moduleList.size()]));
      }

      this.appCtx.setApplicationModules(modules);
      this.validateTargets();
      if (this.appCtx.isStaticDeploymentOperation()) {
         this.appCtx.possiblyFixAppRuntimeState(unprunedModules);
      }

   }

   public void validateRedeploy(DeploymentContext deplCtx) throws DeploymentException {
      AppDDHolder appDDs = this.appCtx.getProposedPartialRedeployDDs();
      if (appDDs == null) {
         throw new AssertionError("Could not process proposed application deployment descriptors");
      } else {
         this.appCtx.setAdditionalModuleUris(this.getModuleUriMappings(appDDs));
      }
   }

   private Map getModuleUriMappings(AppDDHolder appDDs) throws DeploymentException {
      Map rtn = Collections.EMPTY_MAP;
      if (appDDs.getApplicationBean() == null) {
         return (Map)rtn;
      } else {
         ModuleBean[] modules = appDDs.getApplicationBean().getModules();
         if (modules.length > 0) {
            rtn = new HashMap(modules.length);
         }

         for(int i = 0; i < modules.length; ++i) {
            ((Map)rtn).put(EarUtils.getModuleURI(modules[i]), EarUtils.reallyGetModuleURI(modules[i]));
         }

         if (appDDs.getWLApplicationBean() == null) {
            return (Map)rtn;
         } else {
            WeblogicModuleBean[] wlModules = appDDs.getWLApplicationBean().getModules();

            for(int i = 0; i < wlModules.length; ++i) {
               String id = this.getModuleId(wlModules[i]);
               String uri = this.getModuleURI(wlModules[i]);
               ((Map)rtn).put(id, uri);
            }

            return (Map)rtn;
         }
      }
   }

   private Module[] createModules() throws DeploymentException {
      List modules = new ArrayList();
      modules.addAll(Arrays.asList(this.createCustomModules()));
      modules.addAll(Arrays.asList(this.createWeblogicApplicationModules()));
      modules.addAll(Arrays.asList(this.createJEEApplicationModules()));
      modules.addAll(Arrays.asList(this.createWeblogicExtensionModules()));
      return this.createWrappedModules(modules);
   }

   private Module[] createCustomModules() throws DeploymentException {
      return (new CustomModulesCreator()).create(this.appCtx);
   }

   private Module[] createJEEApplicationModules() throws DeploymentException {
      return (new JeeModulesCreator()).create(this.appCtx);
   }

   private Module[] createWeblogicApplicationModules() throws DeploymentException {
      return (new WebLogicModulesCreator()).create(this.appCtx);
   }

   private Module[] createWeblogicExtensionModules() throws DeploymentException {
      return (new WebLogicExtensionModulesCreator()).create(this.appCtx);
   }

   public void start(String[] uris) throws DeploymentException {
      boolean isJEEModulePresent = false;
      boolean isWeblogicModulePresent = false;
      String[] var4 = uris;
      int var5 = uris.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String uri = var4[var6];
         if (this.findStandardModuleDescriptor(uri) != null) {
            if (!isJEEModulePresent) {
               isJEEModulePresent = true;
            }
         } else {
            if (this.findWLSModuleDescriptor(uri) == null) {
               throw new ModuleNotFoundException("Trying to start uri " + uri + " which does not currently exist in the application and was not declared in the META-INF/application.xml or META-INF/weblogic-application.xml.  If you were attempting to redeploy a web module, please ensure you specified the context-root rather than the web-uri.");
            }

            if (!isWeblogicModulePresent) {
               isWeblogicModulePresent = true;
            }
         }
      }

      List modulesTobeStarted = new ArrayList();
      if (isJEEModulePresent) {
         modulesTobeStarted.addAll(Arrays.asList(this.createJEEApplicationModules()));
      }

      if (isWeblogicModulePresent) {
         modulesTobeStarted.addAll(Arrays.asList(this.createWeblogicApplicationModules()));
      }

      this.appCtx.setStartingModules(this.createWrappedModules(modulesTobeStarted));
   }

   private ModuleBean findStandardModuleDescriptor(String uri) {
      return this.findModuleDescriptor(this.appCtx.getApplicationDD().getModules(), uri);
   }

   private WeblogicModuleBean findWLSModuleDescriptor(String uri) {
      WeblogicApplicationBean wldd = this.appCtx.getWLApplicationDD();
      return wldd != null ? this.findModuleDescriptor(wldd.getModules(), uri) : null;
   }

   private ModuleBean findModuleDescriptor(ModuleBean[] m, String uri) {
      if (m == null) {
         return null;
      } else {
         for(int i = 0; i < m.length; ++i) {
            if (EarUtils.getModuleURI(m[i]).equals(uri)) {
               return m[i];
            }
         }

         return null;
      }
   }

   private WeblogicModuleBean findModuleDescriptor(WeblogicModuleBean[] m, String uri) {
      if (m == null) {
         return null;
      } else {
         for(int i = 0; i < m.length; ++i) {
            if (this.getModuleId(m[i]).equals(uri)) {
               return m[i];
            }
         }

         return null;
      }
   }

   private String getModuleId(WeblogicModuleBean m) {
      return WebLogicModuleType.MODULETYPE_JDBC.equalsIgnoreCase(m.getType()) && m.getName() != null ? m.getName() : this.getModuleURI(m);
   }

   private String getModuleURI(WeblogicModuleBean m) {
      String uri = m.getPath();
      if (uri == null) {
         throw new AssertionError("WeblogicModuleBean contains no module URI");
      } else {
         return uri;
      }
   }

   private Module createAndAddScopedCustomModules(Module module) throws DeploymentException {
      if (!(module instanceof ParentModule)) {
         return module;
      } else {
         String extLocationUri = ((ParentModule)module).getWLExtensionDirectory();
         ModuleBean mbean = this.findStandardModuleDescriptor(module.getId());
         if (mbean == null) {
            return module;
         } else {
            String uri = EarUtils.reallyGetModuleURI(mbean);
            VirtualJarFile vjf = null;

            Module var7;
            try {
               vjf = VirtualJarFactory.createVirtualJar(this.appCtx.getEar().getModuleRoots(uri));
               ScopedModuleDriver var6 = new ScopedModuleDriver(module, this.appCtx, uri, vjf, extLocationUri);
               return var6;
            } catch (IOException var11) {
               var7 = module;
            } finally {
               IOUtils.forceClose(vjf);
            }

            return var7;
         }
      }
   }

   static {
      serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
   }
}
