package weblogic.diagnostics.module;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsExtension;
import weblogic.application.compiler.ToolsExtensionFactory;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.descriptor.util.WLDFDescriptorHelper;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;

public class WLDFToolsExtensionFactory implements ToolsExtensionFactory {
   public ToolsExtension createExtension(ToolsContext ctx) {
      return new WLDFToolsExtension();
   }

   public static class WLDFToolsExtension implements ToolsExtension {
      private ToolsContext ctx;

      public void init(ToolsContext ctx, GenericClassLoader appClassLoader) throws ToolFailureException {
         this.ctx = ctx;
      }

      public Map merge() throws ToolFailureException {
         Map parsedBeans = new HashMap();
         File diagDesFile = null;
         DeploymentPlanBean dpBean = this.ctx.getPlanBean();
         File configDir = this.ctx.getConfigDir();
         if (dpBean != null) {
            ModuleOverrideBean[] overrideModules = dpBean.getModuleOverrides();

            for(int i = 0; i < overrideModules.length; ++i) {
               if (overrideModules[i].getModuleName().equals(this.ctx.getEar().getURI()) && overrideModules[i].getModuleType().equals(ModuleType.EAR.toString())) {
                  ModuleDescriptorBean[] moduleDesBeans = overrideModules[i].getModuleDescriptors();

                  for(int j = 0; j < moduleDesBeans.length; ++j) {
                     if (moduleDesBeans[j].isExternal() && moduleDesBeans[j].getRootElement().equals("wldf-resource")) {
                        diagDesFile = new File(configDir, moduleDesBeans[j].getUri());
                        if (diagDesFile.isFile() && diagDesFile.exists()) {
                           break;
                        }

                        diagDesFile = null;
                     }
                  }
               }

               if (diagDesFile != null) {
                  break;
               }
            }
         }

         try {
            DescriptorBean wldfBean = diagDesFile != null ? WLDFDescriptorHelper.getDescriptorBean(diagDesFile, this.ctx.getConfigDir(), this.ctx.getPlanBean(), this.ctx.getEar().getURI(), "META-INF/weblogic-diagnostics.xml") : WLDFDescriptorHelper.getDescriptorBean(this.ctx.getVSource(), this.ctx.getConfigDir(), this.ctx.getPlanBean(), this.ctx.getEar().getURI(), "META-INF/weblogic-diagnostics.xml");
            if (wldfBean != null) {
               parsedBeans.put("META-INF/weblogic-diagnostics.xml", wldfBean);
            } else {
               parsedBeans.put("META-INF/weblogic-diagnostics.xml", (new DescriptorManager()).createDescriptorRoot(WLDFResourceBean.class).getRootBean());
            }
         } catch (Exception var9) {
         }

         return parsedBeans;
      }

      public void cleanup() {
      }

      public void compile() throws ToolFailureException {
      }
   }
}
