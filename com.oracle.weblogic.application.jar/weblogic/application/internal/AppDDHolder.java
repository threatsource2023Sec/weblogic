package weblogic.application.internal;

import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;

public final class AppDDHolder {
   private final ApplicationBean appDD;
   private final WeblogicApplicationBean WLAppDD;
   private final WeblogicExtensionBean WLExtDD;

   public AppDDHolder(ApplicationBean appDD, WeblogicApplicationBean WLAppDD, WeblogicExtensionBean WLExtDD) {
      this.appDD = appDD;
      this.WLAppDD = WLAppDD;
      this.WLExtDD = WLExtDD;
   }

   public ApplicationBean getApplicationBean() {
      return this.appDD;
   }

   public WeblogicApplicationBean getWLApplicationBean() {
      return this.WLAppDD;
   }

   public WeblogicExtensionBean getWLExtensionBean() {
      return this.WLExtDD;
   }
}
