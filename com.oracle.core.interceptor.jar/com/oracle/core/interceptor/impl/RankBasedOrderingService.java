package com.oracle.core.interceptor.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.extras.interception.InterceptorOrderingService;
import org.jvnet.hk2.annotations.Service;

@Singleton
@Service
@Rank(-2147483646)
public class RankBasedOrderingService implements InterceptorOrderingService {
   @Inject
   ServiceLocator serviceLocator;
   @Inject
   MethodInvocationContextManager methodInvocationContextManager;
   @Inject
   InterceptorWorkflowLifecycleManagerAdapter interceptorWorkflowLifecycleManagerAdapter;

   public List modifyMethodInterceptors(Method method, List serviceHandles) {
      if (serviceHandles.size() > 0) {
         ArrayList result = new ArrayList();
         result.add(createServiceHandle(new MethodInterceptorListWrapper(this.serviceLocator, this.interceptorWorkflowLifecycleManagerAdapter, serviceHandles, this.methodInvocationContextManager)));
         return result;
      } else {
         return serviceHandles;
      }
   }

   public static ServiceHandle createServiceHandle(final Object target) {
      ServiceHandle sh = new ServiceHandle() {
         private Object serviceData;

         public Object getService() {
            return target;
         }

         public ActiveDescriptor getActiveDescriptor() {
            return null;
         }

         public boolean isActive() {
            return true;
         }

         public void destroy() {
         }

         public void setServiceData(Object o) {
            this.serviceData = o;
         }

         public Object getServiceData() {
            return this.serviceData;
         }

         public List getSubHandles() {
            return Collections.emptyList();
         }
      };
      return sh;
   }

   public List modifyConstructorInterceptors(Constructor constructor, List serviceHandles) {
      return null;
   }
}
