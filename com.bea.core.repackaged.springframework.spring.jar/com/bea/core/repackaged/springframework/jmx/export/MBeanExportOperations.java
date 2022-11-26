package com.bea.core.repackaged.springframework.jmx.export;

import javax.management.ObjectName;

public interface MBeanExportOperations {
   ObjectName registerManagedResource(Object var1) throws MBeanExportException;

   void registerManagedResource(Object var1, ObjectName var2) throws MBeanExportException;

   void unregisterManagedResource(ObjectName var1);
}
