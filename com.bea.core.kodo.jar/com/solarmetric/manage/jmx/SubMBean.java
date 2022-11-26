package com.solarmetric.manage.jmx;

import java.io.Serializable;
import javax.management.MBeanAttributeInfo;

public interface SubMBean extends Serializable {
   Object getSub();

   String getPrefix();

   MBeanAttributeInfo[] createMBeanAttributeInfo();
}
