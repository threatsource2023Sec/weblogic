package weblogic.application;

import weblogic.j2ee.descriptor.wl.ClassLoadingBean;
import weblogic.j2ee.descriptor.wl.PreferApplicationPackagesBean;
import weblogic.j2ee.descriptor.wl.PreferApplicationResourcesBean;

public interface ClassLoadingConfiguration {
   PreferApplicationPackagesBean getPreferApplicationPackages();

   PreferApplicationResourcesBean getPreferApplicationResources();

   ClassLoadingBean getClassLoading();
}
