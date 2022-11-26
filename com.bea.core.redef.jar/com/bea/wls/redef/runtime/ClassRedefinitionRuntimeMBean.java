package com.bea.wls.redef.runtime;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;

public interface ClassRedefinitionRuntimeMBean extends RuntimeMBean {
   int getClassRedefinitionCount();

   int getFailedClassRedefinitionCount();

   int getProcessedClassesCount();

   long getTotalClassRedefinitionTime();

   ClassRedefinitionTaskRuntimeMBean redefineClasses() throws ManagementException;

   ClassRedefinitionTaskRuntimeMBean redefineClasses(String var1, String[] var2) throws ManagementException;

   ClassRedefinitionTaskRuntimeMBean[] getClassRedefinitionTasks();
}
