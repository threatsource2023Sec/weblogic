package weblogic.j2ee.descriptor.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.j2ee.descriptor.EmptyBean;
import weblogic.j2ee.descriptor.WebAppBean;

public class WebAppBeanCustomizer implements Customizer {
   private WebAppBean webAppBean;

   public WebAppBeanCustomizer(WebAppBean webAppBean) {
      this.webAppBean = webAppBean;
   }

   public String getJavaEEModuleName() {
      String[] moduleNames = this.webAppBean.getModuleNames();
      return moduleNames != null && moduleNames.length > 0 ? moduleNames[0] : null;
   }

   public boolean isDenyUncoveredHttpMethods() {
      EmptyBean[] denyUncoveredHttpMethods = this.webAppBean.getDenyUncoveredHttpMethods();
      return denyUncoveredHttpMethods != null && denyUncoveredHttpMethods.length > 0 && denyUncoveredHttpMethods[0] != null;
   }
}
