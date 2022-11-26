package weblogic.j2eeclient.tools;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ModuleContext;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2eeclient.ApplicationClientDescriptor;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.NullClassFinder;
import weblogic.utils.compiler.ToolFailureException;

public class CARModule implements ToolsModule {
   private ModuleContext state;
   private final String altDD;
   private final String moduleUri;

   public CARModule(String uri, String altDD) {
      this.moduleUri = uri;
      this.altDD = altDD;
   }

   public ClassFinder init(ModuleContext state, ToolsContext ctx, GenericClassLoader parentClassLoader) throws ToolFailureException {
      this.state = state;
      return NullClassFinder.NULL_FINDER;
   }

   public boolean needsClassLoader() {
      return false;
   }

   public String getAltDD() {
      return this.altDD;
   }

   public String getURI() {
      return this.moduleUri;
   }

   public Map merge() throws ToolFailureException {
      Map descriptors = new HashMap();
      ApplicationClientDescriptor appClientDes = new ApplicationClientDescriptor(this.state.getVirtualJarFile(), (File)null, (DeploymentPlanBean)null, (String)null);

      try {
         if (appClientDes.getApplicationClientBean() != null) {
            descriptors.put("META-INF/application-client.xml", (DescriptorBean)appClientDes.getApplicationClientBean());
         }

         if (appClientDes.getWeblogicApplicationClientBean() != null) {
            descriptors.put("META-INF/weblogic-application.xml", (DescriptorBean)appClientDes.getWeblogicApplicationClientBean());
         }
      } catch (Exception var4) {
      }

      return descriptors;
   }

   public ModuleType getModuleType() {
      return ModuleType.CAR;
   }

   public String toString() {
      return this.getURI();
   }

   public String getStandardDescriptorURI() {
      return "META-INF/application-client.xml";
   }

   public String[] getApplicationNameXPath() {
      return new String[]{"application-client", "module-name"};
   }

   public boolean isDeployableObject() {
      return true;
   }

   public void cleanup() {
   }

   public Map compile(GenericClassLoader cl) throws ToolFailureException {
      return Collections.emptyMap();
   }

   public void populateValidationInfo(GenericClassLoader cl) throws ToolFailureException {
   }

   public void write() throws ToolFailureException {
   }

   public void enhanceDeploymentView(EditableDeployableObject deployableObject) {
   }
}
