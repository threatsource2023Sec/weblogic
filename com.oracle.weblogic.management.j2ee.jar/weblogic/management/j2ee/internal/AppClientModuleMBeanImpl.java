package weblogic.management.j2ee.internal;

import weblogic.management.j2ee.AppClientModuleMBean;

public final class AppClientModuleMBeanImpl extends J2EEModuleMBeanImpl implements AppClientModuleMBean {
   public AppClientModuleMBeanImpl(String name, String server, String jvm, ApplicationInfo info) {
      super(name, server, jvm, info);
   }
}
