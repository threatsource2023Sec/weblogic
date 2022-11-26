package weblogic.application.compiler;

import java.util.Collections;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ModuleContext;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;

public class LibraryModule implements ToolsModule {
   private final ToolsModule delegate;

   public ToolsModule getDelegate() {
      return this.delegate;
   }

   public LibraryModule(ToolsModule delegate) {
      this.delegate = delegate;
   }

   public String getStandardDescriptorURI() {
      return this.delegate.getStandardDescriptorURI();
   }

   public ClassFinder init(ModuleContext state, ToolsContext ctx, GenericClassLoader parentClassLoader) throws ToolFailureException {
      return this.delegate.init(state, ctx, parentClassLoader);
   }

   public Map compile(GenericClassLoader cl) throws ToolFailureException {
      this.populateValidationInfo(cl);
      return Collections.emptyMap();
   }

   public void populateValidationInfo(GenericClassLoader cl) throws ToolFailureException {
      this.delegate.populateValidationInfo(cl);
   }

   public Map merge() throws ToolFailureException {
      return this.delegate.merge();
   }

   public void write() throws ToolFailureException {
      this.delegate.write();
   }

   public String getURI() {
      return this.delegate.getURI();
   }

   public String getAltDD() {
      return this.delegate.getAltDD();
   }

   public void cleanup() {
      this.delegate.cleanup();
   }

   public String toString() {
      return this.delegate.toString() + " (library)";
   }

   public ModuleType getModuleType() {
      return this.delegate.getModuleType();
   }

   public boolean isDeployableObject() {
      return this.delegate.isDeployableObject();
   }

   public void enhanceDeploymentView(EditableDeployableObject deployableObject) {
      this.delegate.enhanceDeploymentView(deployableObject);
   }

   public boolean needsClassLoader() {
      return this.delegate.needsClassLoader();
   }

   public String[] getApplicationNameXPath() {
      return this.delegate.getApplicationNameXPath();
   }
}
