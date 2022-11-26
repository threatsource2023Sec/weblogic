package weblogic.application.compiler;

import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ModuleContext;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;

public abstract class ToolsModuleWrapper implements ToolsModule {
   public abstract ToolsModule getDelegate();

   public ToolsModule unwrap() {
      ToolsModule m;
      for(m = this.getDelegate(); m instanceof ToolsModuleWrapper; m = ((ToolsModuleWrapper)m).getDelegate()) {
      }

      return m;
   }

   public String getURI() {
      return this.getDelegate().getURI();
   }

   public String getAltDD() {
      return this.getDelegate().getAltDD();
   }

   public ModuleType getModuleType() {
      return this.getDelegate().getModuleType();
   }

   public String getStandardDescriptorURI() {
      return this.getDelegate().getStandardDescriptorURI();
   }

   public String[] getApplicationNameXPath() {
      return this.getDelegate().getApplicationNameXPath();
   }

   public ClassFinder init(ModuleContext state, ToolsContext ctx, GenericClassLoader parentClassLoader) throws ToolFailureException {
      return this.getDelegate().init(state, ctx, parentClassLoader);
   }

   public boolean needsClassLoader() {
      return this.getDelegate().needsClassLoader();
   }

   public Map merge() throws ToolFailureException {
      return this.getDelegate().merge();
   }

   public void populateValidationInfo(GenericClassLoader cl) throws ToolFailureException {
      this.getDelegate().populateValidationInfo(cl);
   }

   public Map compile(GenericClassLoader cl) throws ToolFailureException {
      return this.getDelegate().compile(cl);
   }

   public void write() throws ToolFailureException {
      this.getDelegate().write();
   }

   public boolean isDeployableObject() {
      return this.getDelegate().isDeployableObject();
   }

   public void enhanceDeploymentView(EditableDeployableObject deployableObject) {
      this.getDelegate().enhanceDeploymentView(deployableObject);
   }

   public void cleanup() {
      this.getDelegate().cleanup();
   }
}
