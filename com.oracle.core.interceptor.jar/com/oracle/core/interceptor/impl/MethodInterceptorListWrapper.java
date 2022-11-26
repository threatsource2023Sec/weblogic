package com.oracle.core.interceptor.impl;

import com.oracle.core.interceptor.ConfigurableMethodInterceptor;
import com.oracle.core.interceptor.MethodInvocationContext;
import com.oracle.core.interceptor.OperationCancelledException;
import com.oracle.core.interceptor.WorkflowProducer;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.HK2Invocation;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.management.configuration.InterceptorMBean;
import weblogic.management.configuration.InterceptorsMBean;
import weblogic.management.workflow.WorkflowBuilder;
import weblogic.management.workflow.WorkflowProgress;

public class MethodInterceptorListWrapper implements MethodInterceptor {
   private List discoveredInterceptors = new ArrayList();
   private List sortedMethodInterceptors = new ArrayList();
   private MethodInvocationContextManager invocationContextManager;
   private ServiceLocator serviceLocator;
   private InterceptorWorkflowLifecycleManagerAdapter interceptorWorkflowLifecycleManagerAdapter;
   private long lastKnownInterceptorAdapterChangeNumber = -1L;
   private AtomicBoolean regenerating = new AtomicBoolean(false);

   public MethodInterceptorListWrapper(ServiceLocator serviceLocator, InterceptorWorkflowLifecycleManagerAdapter adapter, List serviceHandles, MethodInvocationContextManager invocationContextManager) {
      this.serviceLocator = serviceLocator;
      this.interceptorWorkflowLifecycleManagerAdapter = adapter;
      this.invocationContextManager = invocationContextManager;
      this.discoveredInterceptors = new ArrayList(serviceHandles);
   }

   public Object invoke(MethodInvocation methodInvocation) throws Throwable {
      long adapterChangeNumber = this.interceptorWorkflowLifecycleManagerAdapter.getLastInterceptorsMBeanChangeNumber();
      if (this.lastKnownInterceptorAdapterChangeNumber != adapterChangeNumber) {
         synchronized(this.regenerating) {
            if (this.lastKnownInterceptorAdapterChangeNumber != adapterChangeNumber) {
               this.regenerateOrderedMethodInterceptors(methodInvocation);
               this.lastKnownInterceptorAdapterChangeNumber = adapterChangeNumber;
            }
         }
      }

      return (new InterceptorInvoker(this.sortedMethodInterceptors)).invoke(methodInvocation);
   }

   private void regenerateOrderedMethodInterceptors(MethodInvocation methodInvocation) {
      boolean whiteListingOn = false;
      InterceptorsMBean interceptorsMBean = this.interceptorWorkflowLifecycleManagerAdapter.getInterceptorsMBean();
      if (interceptorsMBean != null) {
         whiteListingOn = interceptorsMBean.isWhiteListingEnabled();
      }

      List serviceHandleWrappers = new ArrayList();
      Set configurableTypeNames = new HashSet();
      Map typeName2ServiceHandleWrapper = new HashMap();
      Iterator var7 = this.discoveredInterceptors.iterator();

      String typeName;
      ServiceHandleWrapper serviceHandleWrapper;
      while(var7.hasNext()) {
         ServiceHandle serviceHandle = (ServiceHandle)var7.next();
         ActiveDescriptor activeDescriptor = serviceHandle.getActiveDescriptor();
         Class interceptorClass = activeDescriptor.getImplementationClass();
         typeName = activeDescriptor.getName() != null ? activeDescriptor.getName() : interceptorClass.getName();
         if (!ConfigurableMethodInterceptor.class.isAssignableFrom(interceptorClass)) {
            boolean isInternalInterceptor = true;
            if (isInternalInterceptor || !whiteListingOn) {
               if (typeName2ServiceHandleWrapper.containsKey(typeName)) {
                  throw new IllegalStateException("There is already another interceptor with the same hk2 name: " + typeName);
               }

               serviceHandleWrapper = new ServiceHandleWrapper(serviceHandle);
               serviceHandleWrappers.add(serviceHandleWrapper);
               typeName2ServiceHandleWrapper.put(typeName, serviceHandleWrapper);
            }
         } else {
            configurableTypeNames.add(typeName);
         }
      }

      if (interceptorsMBean != null) {
         InterceptorMBean[] var15 = interceptorsMBean.getInterceptors();
         int var17 = var15.length;

         for(int var19 = 0; var19 < var17; ++var19) {
            InterceptorMBean interceptorMBean = var15[var19];
            typeName = interceptorMBean.getInterceptorTypeName();
            int rank = interceptorMBean.getPriority();
            serviceHandleWrapper = (ServiceHandleWrapper)typeName2ServiceHandleWrapper.get(typeName);
            if (serviceHandleWrapper != null) {
               serviceHandleWrapper.setModifiedRank(rank);
            } else if (configurableTypeNames.contains(typeName)) {
               ConfigurableMethodInterceptor interceptor = (ConfigurableMethodInterceptor)this.serviceLocator.getService(ConfigurableMethodInterceptor.class, typeName, new Annotation[0]);
               if (interceptor == null) {
                  throw new IllegalArgumentException("Could not instantiate MethodInterceptor (a HK2 Service) named: " + typeName);
               }

               serviceHandleWrapper = new ServiceHandleWrapper(RankBasedOrderingService.createServiceHandle(interceptor));
               serviceHandleWrapper.setModifiedRank(rank);
               serviceHandleWrappers.add(serviceHandleWrapper);
               interceptor.configure(interceptorMBean);
            }
         }
      }

      Collections.sort(serviceHandleWrappers, new RankBasedComparator());
      List serviceHandleArrayList = new ArrayList();
      Iterator var18 = serviceHandleWrappers.iterator();

      while(var18.hasNext()) {
         ServiceHandleWrapper shw = (ServiceHandleWrapper)var18.next();
         serviceHandleArrayList.add(shw.getDelegate());
      }

      this.sortedMethodInterceptors = serviceHandleArrayList;
   }

   private static class RankBasedComparator implements Comparator {
      private RankBasedComparator() {
      }

      public int compare(ServiceHandleWrapper o1, ServiceHandleWrapper o2) {
         return o2.getModifiedRank() - o1.getModifiedRank();
      }

      // $FF: synthetic method
      RankBasedComparator(Object x0) {
         this();
      }
   }

   private static class ServiceHandleWrapper implements ServiceHandle {
      private ServiceHandle delegate;
      private int modifiedRank;

      private ServiceHandleWrapper(ServiceHandle delegate) {
         this.delegate = delegate;
         this.modifiedRank = delegate != null && delegate.getActiveDescriptor() != null ? delegate.getActiveDescriptor().getRanking() : 0;
      }

      public ServiceHandle getDelegate() {
         return this.delegate;
      }

      public int getModifiedRank() {
         return this.modifiedRank;
      }

      public void setModifiedRank(int modifiedRank) {
         this.modifiedRank = modifiedRank;
      }

      public MethodInterceptor getService() {
         return (MethodInterceptor)this.delegate.getService();
      }

      public ActiveDescriptor getActiveDescriptor() {
         return this.delegate.getActiveDescriptor();
      }

      public boolean isActive() {
         return this.delegate.isActive();
      }

      public void destroy() {
         this.delegate.destroy();
      }

      public void setServiceData(Object o) {
         this.delegate.setServiceData(o);
      }

      public Object getServiceData() {
         return this.delegate.getServiceData();
      }

      public List getSubHandles() {
         return this.delegate.getSubHandles();
      }

      public String toString() {
         return "ServiceHandleWrapper{delegate=" + this.delegate + ", modifiedRank=" + this.modifiedRank + '}';
      }

      // $FF: synthetic method
      ServiceHandleWrapper(ServiceHandle x0, Object x1) {
         this(x0);
      }
   }

   private class InterceptorInvoker implements MethodInterceptor, MethodInvocation, HK2Invocation {
      private List interceptorServiceHandles;
      private MethodInvocation methodInvocation;
      private int index;
      private int maxIndex;

      private InterceptorInvoker(List interceptorServiceHandles) {
         this.interceptorServiceHandles = interceptorServiceHandles;
         this.maxIndex = interceptorServiceHandles.size();
      }

      public Object invoke(MethodInvocation methodInvocation) throws Throwable {
         this.methodInvocation = methodInvocation;
         WorkflowProducer workflowProducerAnn = (WorkflowProducer)methodInvocation.getMethod().getAnnotation(WorkflowProducer.class);
         MethodInterceptorListWrapper.this.invocationContextManager.pushMethodInvocationContext(new MethodInvocationContextImpl(methodInvocation, workflowProducerAnn != null));

         Object var12;
         try {
            Object result = this.proceed();
            MethodInvocationContextImpl ctxImpl = (MethodInvocationContextImpl)MethodInterceptorListWrapper.this.invocationContextManager.getCurrent();
            WorkflowBuilder builder = ctxImpl.getWorkflowBuilder(false);
            if (builder != null) {
               Method method = methodInvocation.getMethod();
               String workflowName = methodInvocation.getThis().getClass().getName() + "." + method.getName();
               WorkflowProgress progress = MethodInterceptorListWrapper.this.interceptorWorkflowLifecycleManagerAdapter.getWorkflowLifecycleManager().startWorkflow(builder, workflowName);
               ctxImpl.setWorkflowProgress(progress);
            }

            var12 = result;
         } finally {
            MethodInterceptorListWrapper.this.invocationContextManager.popMethodInvocationContext();
         }

         return var12;
      }

      public Object proceed() throws Throwable {
         Object result = null;
         if (this.index < this.maxIndex) {
            MethodInvocationContext ctx = MethodInterceptorListWrapper.this.invocationContextManager.getCurrent();
            if (ctx.isCanceled()) {
               throw new OperationCancelledException("Operation cancelled");
            }

            result = ((MethodInterceptor)((ServiceHandle)this.interceptorServiceHandles.get(this.index++)).getService()).invoke(this);
            --this.index;
         } else {
            result = this.methodInvocation.proceed();
         }

         return result;
      }

      public Object getInterceptedObject() {
         return this.methodInvocation.getThis();
      }

      public Method getMethod() {
         return this.methodInvocation.getMethod();
      }

      public Object[] getParameters() {
         return this.methodInvocation.getArguments();
      }

      public Object[] getArguments() {
         return this.getParameters();
      }

      public Object getThis() {
         return this.methodInvocation.getThis();
      }

      public AccessibleObject getStaticPart() {
         return this.methodInvocation.getStaticPart();
      }

      public Object getUserData(String key) {
         return ((HK2Invocation)this.methodInvocation).getUserData(key);
      }

      public void setUserData(String key, Object value) {
         ((HK2Invocation)this.methodInvocation).setUserData(key, value);
      }

      // $FF: synthetic method
      InterceptorInvoker(List x1, Object x2) {
         this(x1);
      }
   }
}
