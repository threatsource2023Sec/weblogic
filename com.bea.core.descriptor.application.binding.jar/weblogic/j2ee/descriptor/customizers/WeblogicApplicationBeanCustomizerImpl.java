package weblogic.j2ee.descriptor.customizers;

import weblogic.j2ee.descriptor.wl.EjbReferenceDescriptionBean;
import weblogic.j2ee.descriptor.wl.ResourceDescriptionBean;
import weblogic.j2ee.descriptor.wl.ResourceEnvDescriptionBean;
import weblogic.j2ee.descriptor.wl.ServiceReferenceDescriptionBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnvironmentBean;

public class WeblogicApplicationBeanCustomizerImpl implements WeblogicApplicationBeanCustomizer {
   private WeblogicApplicationBean bean;
   private AppWeblogicEnvironmentBean appBean;

   public WeblogicApplicationBeanCustomizerImpl(WeblogicApplicationBean bean) {
      this.bean = bean;
   }

   public WeblogicEnvironmentBean convertToWeblogicEnvironmentBean() {
      if (this.appBean == null) {
         this.appBean = new AppWeblogicEnvironmentBean(this.bean);
      }

      return this.appBean;
   }

   private class AppWeblogicEnvironmentBean implements WeblogicEnvironmentBean {
      private WeblogicApplicationBean wldd;

      private AppWeblogicEnvironmentBean(WeblogicApplicationBean wldd) {
         this.wldd = wldd;
      }

      public ResourceDescriptionBean[] getResourceDescriptions() {
         ResourceDescriptionBean[] values = new ResourceDescriptionBean[0];
         if (this.wldd != null) {
            values = this.wldd.getResourceDescriptions();
         }

         return values;
      }

      public ResourceEnvDescriptionBean[] getResourceEnvDescriptions() {
         ResourceEnvDescriptionBean[] values = new ResourceEnvDescriptionBean[0];
         if (this.wldd != null) {
            values = this.wldd.getResourceEnvDescriptions();
         }

         return values;
      }

      public EjbReferenceDescriptionBean[] getEjbReferenceDescriptions() {
         EjbReferenceDescriptionBean[] values = new EjbReferenceDescriptionBean[0];
         if (this.wldd != null) {
            values = this.wldd.getEjbReferenceDescriptions();
         }

         return values;
      }

      public ServiceReferenceDescriptionBean[] getServiceReferenceDescriptions() {
         ServiceReferenceDescriptionBean[] values = new ServiceReferenceDescriptionBean[0];
         if (this.wldd != null) {
            values = this.wldd.getServiceReferenceDescriptions();
         }

         return values;
      }

      // $FF: synthetic method
      AppWeblogicEnvironmentBean(WeblogicApplicationBean x1, Object x2) {
         this(x1);
      }
   }
}
