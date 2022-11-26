package weblogic.servlet.internal.dd.compliance;

import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.j2ee.validation.ModuleValidationInfo;

public class DeploymentInfo {
   private WebAppBean bean;
   private WeblogicWebAppBean wlBean;
   private ClassLoader classLoader;
   private ModuleValidationInfo moduleValidationInfo;
   private boolean verbose;
   private boolean isWebServiceModule;

   public DeploymentInfo() {
   }

   public DeploymentInfo(WebAppBean wa, WeblogicWebAppBean wl) {
      this.bean = wa;
      this.wlBean = wl;
   }

   public WebAppBean getWebAppBean() {
      return this.bean;
   }

   public void setWebAppBean(WebAppBean b) {
      this.bean = b;
   }

   public WeblogicWebAppBean getWeblogicWebAppBean() {
      return this.wlBean;
   }

   public void setWeblogicWebAppBean(WeblogicWebAppBean b) {
      this.wlBean = b;
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public void setClassLoader(ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   public ModuleValidationInfo getModuleValidationInfo() {
      return this.moduleValidationInfo;
   }

   public void setModuleValidationInfo(ModuleValidationInfo moduleValidationInfo) {
      this.moduleValidationInfo = moduleValidationInfo;
   }

   public void setVerbose(boolean v) {
      this.verbose = v;
   }

   public boolean isVerbose() {
      return this.verbose;
   }

   public void setIsWebServiceModule(boolean b) {
      this.isWebServiceModule = b;
   }

   public boolean isWebServiceModule() {
      return this.isWebServiceModule;
   }
}
