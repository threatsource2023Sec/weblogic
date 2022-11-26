package weblogic.management.runtime;

import weblogic.management.internal.AppInfo;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;

public interface SessionHelperManagerRuntimeMBean extends RuntimeMBean {
   SessionHelperRuntimeMBean getSessionHelper(AppInfo var1, WLSModelMBeanContext var2);

   void releaseSessionHelper(String var1);
}
