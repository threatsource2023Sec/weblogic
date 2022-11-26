package weblogic.application.internal.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.Module;
import weblogic.application.WebLogicApplicationModuleFactory;
import weblogic.application.WeblogicModuleFactory;
import weblogic.application.internal.FlowContext;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.management.DeploymentException;

public class WebLogicModulesCreator implements ModulesCreator {
   private static final ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();

   public Module[] create(FlowContext context) throws DeploymentException {
      WeblogicApplicationBean wldd = context.getWLApplicationDD();
      if (wldd == null) {
         return new Module[0];
      } else {
         boolean partialRedeploy = true;
         WeblogicModuleBean[] weblogicModuleBeans = this.getPartialRedeployModuleBeans(context);
         if (weblogicModuleBeans == null) {
            weblogicModuleBeans = wldd.getModules();
            partialRedeploy = false;
         }

         List modules = new ArrayList();
         this.createModules(weblogicModuleBeans, modules);
         if (!partialRedeploy) {
            this.createOldStyleWLDDModules(wldd, modules);
         }

         return (Module[])modules.toArray(new Module[modules.size()]);
      }
   }

   private WeblogicModuleBean[] getPartialRedeployModuleBeans(FlowContext context) {
      String[] uris = context.getPartialRedeployURIs();
      if (uris != null && uris.length > 0) {
         List partialRedeployModules = new ArrayList();
         String[] var4 = uris;
         int var5 = uris.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String uri = var4[var6];
            WeblogicModuleBean weblogicModuleBean = this.findWLSModuleDescriptor(context, uri);
            partialRedeployModules.add(weblogicModuleBean);
         }

         return (WeblogicModuleBean[])partialRedeployModules.toArray(new WeblogicModuleBean[partialRedeployModules.size()]);
      } else {
         return null;
      }
   }

   private void createModules(WeblogicModuleBean[] moduleDD, List moduleList) throws DeploymentException {
      if (moduleDD != null && moduleDD.length != 0) {
         WeblogicModuleBean[] var3 = moduleDD;
         int var4 = moduleDD.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            WeblogicModuleBean aModuleDD = var3[var5];
            Module m = this.createModuleFromFactories(aModuleDD);
            moduleList.add(m);
         }

      }
   }

   private Module createModuleFromFactories(WeblogicModuleBean dd) throws DeploymentException {
      Iterator it = afm.getWeblogicModuleFactories();

      Module module;
      do {
         if (!it.hasNext()) {
            throw new DeploymentException("Unable to find module for " + dd.getClass().getName());
         }

         WeblogicModuleFactory moduleFactory = (WeblogicModuleFactory)it.next();
         module = moduleFactory.createModule(dd);
      } while(module == null);

      return module;
   }

   private void createOldStyleWLDDModules(WeblogicApplicationBean wldd, List modules) throws DeploymentException {
      Iterator it = afm.getWLAppModuleFactories();

      while(it.hasNext()) {
         WebLogicApplicationModuleFactory mf = (WebLogicApplicationModuleFactory)it.next();
         Module[] m = mf.createModule(wldd);
         if (m != null && m.length > 0) {
            modules.addAll(Arrays.asList(m));
         }
      }

   }

   private WeblogicModuleBean findWLSModuleDescriptor(FlowContext context, String uri) {
      WeblogicApplicationBean wldd = context.getWLApplicationDD();
      if (wldd != null) {
         WeblogicModuleBean[] moduleBeans = wldd.getModules();
         if (moduleBeans == null) {
            return null;
         }

         WeblogicModuleBean[] var5 = moduleBeans;
         int var6 = moduleBeans.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            WeblogicModuleBean moduleBean = var5[var7];
            if (this.getModuleId(moduleBean).equals(uri)) {
               return moduleBean;
            }
         }
      }

      return null;
   }

   private String getModuleId(WeblogicModuleBean m) {
      if (WebLogicModuleType.MODULETYPE_JDBC.equalsIgnoreCase(m.getType()) && m.getName() != null) {
         return m.getName();
      } else {
         String uri = m.getPath();
         if (uri == null) {
            throw new AssertionError("WeblogicModuleBean contains no module URI");
         } else {
            return uri;
         }
      }
   }
}
