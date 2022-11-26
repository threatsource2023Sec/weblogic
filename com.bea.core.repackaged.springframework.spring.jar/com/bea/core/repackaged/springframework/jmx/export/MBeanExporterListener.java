package com.bea.core.repackaged.springframework.jmx.export;

import javax.management.ObjectName;

public interface MBeanExporterListener {
   void mbeanRegistered(ObjectName var1);

   void mbeanUnregistered(ObjectName var1);
}
