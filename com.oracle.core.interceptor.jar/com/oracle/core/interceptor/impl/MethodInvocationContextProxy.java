package com.oracle.core.interceptor.impl;

import com.oracle.core.interceptor.MethodInvocationContext;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.aopalliance.intercept.MethodInvocation;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.workflow.WorkflowBuilder;
import weblogic.management.workflow.WorkflowProgress;

@Service
@Singleton
public class MethodInvocationContextProxy implements MethodInvocationContext {
   @Inject
   MethodInvocationContextManager ctxMgr;

   public MethodInvocation getMethodInvocation() {
      MethodInvocationContext delegate = this.ctxMgr.getCurrent();
      return delegate == null ? null : delegate.getMethodInvocation();
   }

   public boolean isCanceled() {
      MethodInvocationContext delegate = this.ctxMgr.getCurrent();
      return delegate != null && delegate.isCanceled();
   }

   public void cancel() {
      MethodInvocationContext delegate = this.ctxMgr.getCurrent();
      if (delegate != null) {
         delegate.cancel();
      }

   }

   public WorkflowBuilder getWorkflowBuilder() {
      MethodInvocationContext delegate = this.ctxMgr.getCurrent();
      if (delegate == null) {
         return null;
      } else {
         MethodInvocationContextImpl ctxImpl = (MethodInvocationContextImpl)delegate;
         if (!ctxImpl.isCanProduceWorkflow()) {
            throw new UnsupportedOperationException("getWorkflowBuilder() allowed only on intercepted methods that are annotated with @WorkflowProducer");
         } else {
            return ctxImpl.getWorkflowBuilder();
         }
      }
   }

   public WorkflowProgress getWorkflowProgress() {
      MethodInvocationContext delegate = this.ctxMgr.getCurrent();
      if (delegate == null) {
         return null;
      } else {
         MethodInvocationContextImpl ctxImpl = (MethodInvocationContextImpl)delegate;
         if (!ctxImpl.isCanProduceWorkflow()) {
            throw new UnsupportedOperationException("getWorkflowProgress() allowed only on intercepted methods that are annotated with @WorkflowProducer");
         } else {
            return ctxImpl.getWorkflowProgress();
         }
      }
   }

   public Map getSharedInterceptorDataMap() {
      MethodInvocationContext delegate = this.ctxMgr.getCurrent();
      return delegate == null ? null : delegate.getSharedInterceptorDataMap();
   }
}
