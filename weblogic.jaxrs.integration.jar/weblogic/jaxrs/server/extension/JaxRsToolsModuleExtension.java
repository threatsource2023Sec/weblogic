package weblogic.jaxrs.server.extension;

import java.util.HashMap;
import java.util.Map;
import weblogic.application.ModuleContext;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleExtension;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.RestWebservicesBean;
import weblogic.servlet.internal.WebBaseModuleExtensionContext;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;

public class JaxRsToolsModuleExtension implements ToolsModuleExtension {
   private final WebBaseModuleExtensionContext modExtCtx;
   private final Descriptor standardDD;
   private final ModuleContext modCtx;

   public JaxRsToolsModuleExtension(WebBaseModuleExtensionContext modExtCtx, ToolsContext toolsCtx, ToolsModule toolsModule, Descriptor standardDD) {
      this.modExtCtx = modExtCtx;
      this.standardDD = standardDD;
      this.modCtx = toolsCtx.getModuleContext(toolsModule.getURI());
   }

   public Map compile(GenericClassLoader cl, Map extensibleModuleDescriptors) throws ToolFailureException {
      return null;
   }

   public Map merge(Map extensibleModuleDescriptors) throws ToolFailureException {
      if (this.standardDD != null && this.standardDD.getRootBean() instanceof WebAppBean && DeployHelper.isJaxRsApplication(this.modCtx.getClassLoader(), this.modExtCtx)) {
         try {
            RestWebservicesBean restWebservicesBean = (RestWebservicesBean)(new DescriptorManager()).createDescriptorRoot(RestWebservicesBean.class).getRootBean();
            JaxRsContainerInitializer.initialize(this.modExtCtx, this.modCtx.getClassLoader(), (WebAppBean)this.standardDD.getRootBean(), restWebservicesBean, DeployHelper.isServletVersion2x(this.standardDD));
            Map map = new HashMap();
            map.put("weblogic.j2ee.descriptor.wl.RestWebservicesBean", (DescriptorBean)restWebservicesBean);
            return map;
         } catch (Exception var4) {
            throw new ToolFailureException("Error initializing JAX-RS applications.", var4);
         }
      } else {
         return null;
      }
   }

   public void write() throws ToolFailureException {
   }
}
