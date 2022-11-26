package weblogic.j2ee.descriptor.customizers;

import weblogic.j2ee.descriptor.JavaEEModuleNameBean;
import weblogic.j2ee.descriptor.ModuleNameBean;
import weblogic.j2ee.descriptor.WebAppBean;

public class JavaEEModuleNameCustomizer implements JavaEEModuleNameBean {
   private JavaEEModuleNameBean underlyingBean;

   public JavaEEModuleNameCustomizer(JavaEEModuleNameBean parent) {
      this.underlyingBean = parent;
   }

   public String getJavaEEModuleName() {
      if (!(this.underlyingBean instanceof WebAppBean)) {
         if (this.underlyingBean instanceof ModuleNameBean) {
            return ((ModuleNameBean)this.underlyingBean).getModuleName();
         } else {
            throw new AssertionError("Underlying bean should be a WebAppBean or implement ModuleNameBean");
         }
      } else {
         String[] moduleNames = ((WebAppBean)this.underlyingBean).getModuleNames();
         return moduleNames != null && moduleNames.length > 0 ? moduleNames[0] : null;
      }
   }
}
