package com.solarmetric.manage.jmx;

import javax.management.MBeanOperationInfo;

public interface SubMBeanOperations extends SubMBean {
   MBeanOperationInfo[] createMBeanOperationInfo();
}
