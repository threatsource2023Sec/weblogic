package weblogic.deploy.api.internal.utils;

import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.deploy.api.spi.config.DescriptorSupportManager;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.LibraryRefBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;

public class WarMerger extends AppMerger {
   public DescriptorSupport getDescriptorSupport() {
      return DescriptorSupportManager.getForTag("web-app");
   }

   protected LibraryRefBean[] getLibraryRefs(DescriptorBean wlsdd) {
      WeblogicWebAppBean appdd = (WeblogicWebAppBean)wlsdd;
      return appdd.getLibraryRefs();
   }
}
