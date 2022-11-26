package com.oracle.core.interceptor;

import java.util.Map;
import org.aopalliance.intercept.MethodInvocation;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.workflow.WorkflowBuilder;
import weblogic.management.workflow.WorkflowProgress;

@Contract
public interface MethodInvocationContext {
   WorkflowBuilder getWorkflowBuilder();

   WorkflowProgress getWorkflowProgress();

   MethodInvocation getMethodInvocation();

   Map getSharedInterceptorDataMap();

   boolean isCanceled();

   void cancel();
}
