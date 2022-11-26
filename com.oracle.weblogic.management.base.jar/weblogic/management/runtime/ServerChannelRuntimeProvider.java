package weblogic.management.runtime;

import weblogic.management.configuration.NetworkAccessPointMBean;

public interface ServerChannelRuntimeProvider {
   ServerChannelRuntimeMBean getRuntime();

   NetworkAccessPointMBean getConfig();
}
