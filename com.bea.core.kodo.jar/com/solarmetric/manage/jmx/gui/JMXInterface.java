package com.solarmetric.manage.jmx.gui;

import javax.management.MBeanServer;
import org.apache.openjpa.lib.conf.Configurable;

public interface JMXInterface extends Runnable, Configurable {
   void setMBeanServer(MBeanServer var1) throws Exception;
}
