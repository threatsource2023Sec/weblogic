package weblogic.application.internal.flow;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.application.DeploymentContext;
import weblogic.application.Module;
import weblogic.application.ModuleManager;
import weblogic.application.ModuleNotFoundException;
import weblogic.application.internal.AppClassLoaderManagerImpl;
import weblogic.application.internal.FlowContext;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.ManagementUtils;
import weblogic.management.DeploymentException;
import weblogic.server.GlobalServiceLocator;

public final class TailModuleRedeployFlow extends BaseFlow {
   public TailModuleRedeployFlow(FlowContext appCtx) {
      super(appCtx);
   }

   public void start(String[] uris) throws DeploymentException {
      this.appCtx.mergeStartingModules();
   }

   public void stop(String[] uris) throws DeploymentException {
      Module[] modules = this.appCtx.getApplicationModules();
      List stoppingModules = new ArrayList();

      for(int i = 0; i < modules.length; ++i) {
         for(int j = 0; j < uris.length; ++j) {
            if (modules[i].getId().equals(uris[j])) {
               stoppingModules.add(modules[i]);
               break;
            }
         }
      }

      this.appCtx.setStoppingModules((Module[])((Module[])stoppingModules.toArray(new Module[stoppingModules.size()])));
   }

   public void validateRedeploy(DeploymentContext deplCtx) throws DeploymentException {
      String[] uris = deplCtx.getUpdatedResourceURIs();
      if (!deplCtx.isAppStaged()) {
         this.validateModuleUris(uris);
      }

      this.validateClassLoaderStructure(uris);
   }

   private String[] getOldUris(String[] uris) {
      ModuleManager mm = this.appCtx.getModuleManager();
      List uriList = new ArrayList(Arrays.asList(uris));
      Iterator it = uriList.listIterator();

      while(it.hasNext()) {
         String uri = (String)it.next();
         if (mm.isNewUri(uri)) {
            it.remove();
         }
      }

      return (String[])((String[])uriList.toArray(new String[0]));
   }

   private void validateModuleUris(String[] uris) throws ModuleNotFoundException {
      ModuleManager mm = this.appCtx.getModuleManager();
      if (!mm.validateModuleIds(uris)) {
         uris = EarUtils.toModuleIds(this.appCtx, uris);
         if (!mm.validateModuleIds(uris)) {
            throw new ModuleNotFoundException(mm.getInvalidModuleIds(uris));
         }
      }

   }

   private void validateClassLoaderStructure(String[] uris) throws DeploymentException {
      String[] oldUris = this.getOldUris(uris);
      AppClassLoaderManagerImpl clm = (AppClassLoaderManagerImpl)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManagerImpl.class, new Annotation[0]);
      if (!ManagementUtils.isProductionModeEnabled()) {
         Set updated = clm.updatePartialDeploySet(this.appCtx, oldUris);
         if (updated.size() > oldUris.length) {
            updated.addAll(Arrays.asList(uris));
            this.appCtx.setPartialRedeployURIs((String[])((String[])updated.toArray(new String[updated.size()])));
         }
      } else {
         clm.checkPartialRedeploy(this.appCtx, oldUris);
      }

   }
}
