package com.solarmetric.manage.jmx.remote;

import javax.management.MBeanServer;
import org.apache.openjpa.lib.log.Log;

public interface RemoteMBeanServerFactory {
   MBeanServer createRemoteMBeanServer(String var1, String var2, int var3, Log var4) throws Exception;

   MBeanServer createDefaultRemoteMBeanServer(String var1, int var2, Log var3) throws Exception;
}
