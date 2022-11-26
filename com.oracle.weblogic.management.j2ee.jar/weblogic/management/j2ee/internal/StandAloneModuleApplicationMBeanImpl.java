package weblogic.management.j2ee.internal;

import weblogic.management.j2ee.StandAloneModuleApplicationMBean;

public final class StandAloneModuleApplicationMBeanImpl extends J2EEModuleMBeanImpl implements StandAloneModuleApplicationMBean {
   public StandAloneModuleApplicationMBeanImpl(String name, String server, String jvm, ApplicationInfo info) {
      super(name, server, jvm, info);
   }
}
