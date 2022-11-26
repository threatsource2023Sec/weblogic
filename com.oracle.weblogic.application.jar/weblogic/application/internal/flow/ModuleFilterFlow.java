package weblogic.application.internal.flow;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleLocationInfo;
import weblogic.application.ModuleWrapper;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.ManagementUtils;
import weblogic.application.utils.TargetUtils;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.WebBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;

abstract class ModuleFilterFlow extends BaseFlow {
   ModuleFilterFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   private Map makeSubDeploymentMap(SubDeploymentMBean[] sdm) {
      if (sdm != null && sdm.length != 0) {
         Map m = new HashMap(sdm.length);

         for(int i = 0; i < sdm.length; ++i) {
            m.put(EarUtils.toModuleId(this.appCtx, sdm[i].getName()), sdm[i]);
         }

         return m;
      } else {
         return Collections.EMPTY_MAP;
      }
   }

   private String findContextRoot(String uri) {
      ApplicationBean dd = this.appCtx.getApplicationDD();
      if (dd == null) {
         return null;
      } else {
         ModuleBean[] m = dd.getModules();
         if (m == null) {
            return null;
         } else {
            for(int i = 0; i < m.length; ++i) {
               WebBean web = m[i].getWeb();
               if (web != null && uri.equals(web.getWebUri())) {
                  return web.getContextRoot();
               }
            }

            return null;
         }
      }
   }

   private SubDeploymentMBean findSubDeployment(Map subDeploymentMap, String uri) {
      if (subDeploymentMap.size() == 0) {
         return null;
      } else {
         SubDeploymentMBean s = (SubDeploymentMBean)subDeploymentMap.get(uri);
         if (s != null) {
            return s;
         } else {
            String contextRoot = this.findContextRoot(uri);
            if (contextRoot != null) {
               s = (SubDeploymentMBean)subDeploymentMap.get(contextRoot);
               if (s != null) {
                  return s;
               }
            }

            WeblogicApplicationBean wldd = this.appCtx.getWLApplicationDD();
            if (wldd != null) {
               WeblogicModuleBean[] wlmm = wldd.getModules();
               if (wlmm != null) {
                  for(int i = 0; i < wlmm.length; ++i) {
                     if (uri.equals(wlmm[i].getPath())) {
                        return (SubDeploymentMBean)subDeploymentMap.get(wlmm[i].getName());
                     }
                  }
               }
            }

            return null;
         }
      }
   }

   protected Module[] createWrappedModules(List moduleList) {
      ServerMBean server = ManagementUtils.getServerMBean();
      BasicDeploymentMBean mbean = this.appCtx.getBasicDeploymentMBean();
      if (mbean instanceof AppDeploymentMBean && this.appCtx.getProposedDomain() != null) {
         mbean = this.appCtx.getProposedDomain().lookupAppDeployment(((BasicDeploymentMBean)mbean).getName());
      }

      TargetMBean appwideTarget = TargetUtils.findLocalTarget(((BasicDeploymentMBean)mbean).getTargets(), server);
      Map subDeploymentMap = this.makeSubDeploymentMap(((BasicDeploymentMBean)mbean).getSubDeployments());
      Module[] m = new Module[moduleList.size()];
      Iterator it = moduleList.iterator();

      for(int i = 0; i < m.length; ++i) {
         Module mod = (Module)it.next();
         SubDeploymentMBean sdm = this.findSubDeployment(subDeploymentMap, mod.getId());
         if (sdm == null) {
            Module module = mod;
            if (mod instanceof ModuleWrapper) {
               module = ((ModuleWrapper)mod).unwrap();
            }

            if (module instanceof ModuleLocationInfo) {
               sdm = this.findSubDeployment(subDeploymentMap, ((ModuleLocationInfo)module).getModuleURI());
            }
         }

         if (sdm != null && sdm.getTargets().length == 0 && sdm.getSubDeployments().length == 0) {
            if (this.isDebugEnabled()) {
               this.debug("Filtering out module " + mod.getId() + " of application " + this.appCtx.getApplicationId() + " as it has a sub-deployment entry with no target information");
            }

            m[i] = new NonTargetedModuleInvoker(mod, false);
         } else if (this.hasNoTargets(sdm) && appwideTarget != null) {
            m[i] = new ModuleListenerInvoker(mod, appwideTarget);
         } else if (sdm != null && sdm.getTargets().length > 0) {
            TargetMBean subTarget = TargetUtils.findLocalTarget(sdm.getTargets(), server);
            if (subTarget != null) {
               m[i] = new ModuleListenerInvoker(mod, subTarget);
            } else {
               m[i] = new NonTargetedModuleInvoker(mod, true);
            }
         } else {
            m[i] = new NonTargetedModuleInvoker(mod, true);
         }
      }

      return m;
   }

   private boolean hasNoTargets(SubDeploymentMBean sdm) {
      return sdm == null || sdm.getTargets() == null || sdm.getTargets().length == 0;
   }
}
