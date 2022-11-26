package com.bea.wls.redef.runtime;

import weblogic.management.runtime.TaskRuntimeMBean;

public interface ClassRedefinitionTaskRuntimeMBean extends TaskRuntimeMBean {
   int getCandidateClassesCount();

   int getProcessedClassesCount();
}
