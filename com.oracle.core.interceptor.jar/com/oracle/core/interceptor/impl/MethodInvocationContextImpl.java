package com.oracle.core.interceptor.impl;

import com.oracle.core.interceptor.MethodInvocationContext;
import java.util.HashMap;
import java.util.Map;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.HK2Invocation;
import weblogic.management.workflow.WorkflowBuilder;
import weblogic.management.workflow.WorkflowProgress;

public class MethodInvocationContextImpl implements MethodInvocationContext {
   private MethodInvocation inv;
   private WorkflowProgress workflowProgress;
   private boolean canProduceWorkflow;
   private boolean canceled;

   MethodInvocationContextImpl(MethodInvocation inv, boolean canProduceWorkflow) {
      this.inv = inv;
      this.canProduceWorkflow = canProduceWorkflow;
   }

   boolean isCanProduceWorkflow() {
      return this.canProduceWorkflow;
   }

   public WorkflowBuilder getWorkflowBuilder(boolean create) {
      String workflowBuilderKey = WorkflowBuilder.class.getName();
      HK2Invocation hk2Invocation = (HK2Invocation)this.inv;
      WorkflowBuilder builder = (WorkflowBuilder)hk2Invocation.getUserData(workflowBuilderKey);
      if (builder == null && create) {
         builder = WorkflowBuilder.newInstance();
         HashMap map = new HashMap();
         map.put("InterceptorSharedDataConstants_workflow_shared_data_map_key", new HashMap());
         builder.add(map);
         hk2Invocation.setUserData(workflowBuilderKey, builder);
      }

      return builder;
   }

   public Map getSharedInterceptorDataMap() {
      HK2Invocation hk2Invocation = (HK2Invocation)this.inv;
      Map map = (Map)hk2Invocation.getUserData("InterceptorSharedDataConstants_interceptor_shared_data_map_key");
      if (map == null) {
         map = new HashMap();
         hk2Invocation.setUserData("InterceptorSharedDataConstants_interceptor_shared_data_map_key", map);
      }

      return (Map)map;
   }

   public boolean isCanceled() {
      return this.canceled;
   }

   public void cancel() {
      this.canceled = true;
   }

   public MethodInvocation getMethodInvocation() {
      return this.inv;
   }

   public WorkflowBuilder getWorkflowBuilder() {
      return this.getWorkflowBuilder(true);
   }

   public WorkflowProgress getWorkflowProgress() {
      return this.workflowProgress;
   }

   public void setWorkflowProgress(WorkflowProgress workflowProgress) {
      this.workflowProgress = workflowProgress;
   }
}
