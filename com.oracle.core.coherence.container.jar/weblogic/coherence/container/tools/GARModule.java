package weblogic.coherence.container.tools;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ModuleContext;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.coherence.app.descriptor.CoherenceAppDescriptorLoader;
import weblogic.coherence.app.descriptor.wl.CoherenceApplicationBean;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.DescriptorBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.compiler.ToolFailureException;

public class GARModule implements ToolsModule {
   private String uri;
   private ModuleContext state;
   private ToolsContext ctx;
   private CoherenceApplicationBean appBean;

   public GARModule(String uri) {
      this.uri = uri;
   }

   public String getURI() {
      return this.uri;
   }

   public String getAltDD() {
      return null;
   }

   public ModuleType getModuleType() {
      return WebLogicModuleType.GAR;
   }

   public String getStandardDescriptorURI() {
      return "META-INF/coherence-application.xml";
   }

   public String[] getApplicationNameXPath() {
      return null;
   }

   public ClassFinder init(ModuleContext state, ToolsContext ctx, GenericClassLoader parentClassLoader) throws ToolFailureException {
      this.state = state;
      this.ctx = ctx;
      return new MultiClassFinder();
   }

   public boolean needsClassLoader() {
      return true;
   }

   public Map merge() throws ToolFailureException {
      Map descriptors = new HashMap();

      try {
         this.appBean = CoherenceAppDescriptorLoader.getLoader().createCoherenceAppDescriptor(this.state.getVirtualJarFile().getResource("META-INF/coherence-application.xml"));
         descriptors.put("META-INF/coherence-application.xml", (DescriptorBean)this.appBean);
         return descriptors;
      } catch (Exception var3) {
         throw new ToolFailureException(var3.getMessage());
      }
   }

   public void populateValidationInfo(GenericClassLoader cl) throws ToolFailureException {
   }

   public Map compile(GenericClassLoader cl) throws ToolFailureException {
      return Collections.emptyMap();
   }

   public void write() throws ToolFailureException {
   }

   public boolean isDeployableObject() {
      return true;
   }

   public void enhanceDeploymentView(EditableDeployableObject deployableObject) {
   }

   public void cleanup() {
   }
}
