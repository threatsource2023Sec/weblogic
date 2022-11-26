package com.solarmetric.manage.jmx;

import javax.management.ObjectName;

public interface MBeanProvider {
   Object getMBean();

   ObjectName getMBeanName();
}
