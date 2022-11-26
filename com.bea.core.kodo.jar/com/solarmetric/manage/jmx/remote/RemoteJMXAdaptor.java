package com.solarmetric.manage.jmx.remote;

import javax.management.MBeanServer;
import org.apache.openjpa.lib.conf.Configurable;

public interface RemoteJMXAdaptor extends Configurable {
   void setMBeanServer(MBeanServer var1);

   void init();

   void close();
}
