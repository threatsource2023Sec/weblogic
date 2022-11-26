package org.glassfish.hk2.extras.interception.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.IndexedFilter;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.extras.interception.Intercepted;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.glassfish.hk2.extras.interception.InterceptorOrderingService;

@Singleton
@Visibility(DescriptorVisibility.LOCAL)
public class DefaultInterceptionService implements InterceptionService {
   private static final IndexedFilter METHOD_FILTER = new IndexedFilter() {
      public boolean matches(Descriptor d) {
         return d.getQualifiers().contains(Interceptor.class.getName());
      }

      public String getAdvertisedContract() {
         return MethodInterceptor.class.getName();
      }

      public String getName() {
         return null;
      }
   };
   private static final IndexedFilter CONSTRUCTOR_FILTER = new IndexedFilter() {
      public boolean matches(Descriptor d) {
         return d.getQualifiers().contains(Interceptor.class.getName());
      }

      public String getAdvertisedContract() {
         return ConstructorInterceptor.class.getName();
      }

      public String getName() {
         return null;
      }
   };
   @Inject
   private ServiceLocator locator;
   @Inject
   private IterableProvider orderers;

   public Filter getDescriptorFilter() {
      return new Filter() {
         public boolean matches(Descriptor d) {
            return d.getQualifiers().contains(Intercepted.class.getName());
         }
      };
   }

   private List orderMethods(Method method, List current) {
      List retVal = current;
      Iterator var4 = this.orderers.iterator();

      while(var4.hasNext()) {
         InterceptorOrderingService orderer = (InterceptorOrderingService)var4.next();
         List given = Collections.unmodifiableList((List)retVal);

         List returned;
         try {
            returned = orderer.modifyMethodInterceptors(method, given);
         } catch (Throwable var9) {
            returned = null;
         }

         if (returned != null && returned != given) {
            retVal = new ArrayList(returned);
         }
      }

      return (List)retVal;
   }

   private List orderConstructors(Constructor constructor, List current) {
      List retVal = current;
      Iterator var4 = this.orderers.iterator();

      while(var4.hasNext()) {
         InterceptorOrderingService orderer = (InterceptorOrderingService)var4.next();
         List given = Collections.unmodifiableList((List)retVal);

         List returned;
         try {
            returned = orderer.modifyConstructorInterceptors(constructor, given);
         } catch (Throwable var9) {
            returned = null;
         }

         if (returned != null && returned != given) {
            retVal = new ArrayList(returned);
         }
      }

      return (List)retVal;
   }

   public List getMethodInterceptors(Method method) {
      HashSet allBindings = ReflectionUtilities.getAllBindingsFromMethod(method);
      List allInterceptors = this.locator.getAllServiceHandles(METHOD_FILTER);
      List handles = new ArrayList(allInterceptors.size());
      Iterator var5 = allInterceptors.iterator();

      while(var5.hasNext()) {
         ServiceHandle handle = (ServiceHandle)var5.next();
         ActiveDescriptor ad = handle.getActiveDescriptor();
         if (!ad.isReified()) {
            ad = this.locator.reifyDescriptor(ad);
         }

         Class interceptorClass = ad.getImplementationClass();
         HashSet allInterceptorBindings = ReflectionUtilities.getAllBindingsFromClass(interceptorClass);
         boolean found = false;
         Iterator var11 = allInterceptorBindings.iterator();

         while(var11.hasNext()) {
            String interceptorBinding = (String)var11.next();
            if (allBindings.contains(interceptorBinding)) {
               found = true;
               break;
            }
         }

         if (found && handle != null) {
            handles.add(handle);
         }
      }

      List handles = this.orderMethods(method, handles);
      if (handles.isEmpty()) {
         return Collections.emptyList();
      } else {
         List retVal = new ArrayList(handles.size());
         Iterator var15 = handles.iterator();

         while(var15.hasNext()) {
            ServiceHandle handle = (ServiceHandle)var15.next();
            MethodInterceptor interceptor = (MethodInterceptor)handle.getService();
            if (interceptor != null) {
               retVal.add(interceptor);
            }
         }

         return retVal;
      }
   }

   public List getConstructorInterceptors(Constructor constructor) {
      HashSet allBindings = ReflectionUtilities.getAllBindingsFromConstructor(constructor);
      List allInterceptors = this.locator.getAllServiceHandles(CONSTRUCTOR_FILTER);
      List handles = new ArrayList(allInterceptors.size());
      Iterator var5 = allInterceptors.iterator();

      while(var5.hasNext()) {
         ServiceHandle handle = (ServiceHandle)var5.next();
         ActiveDescriptor ad = handle.getActiveDescriptor();
         if (!ad.isReified()) {
            ad = this.locator.reifyDescriptor(ad);
         }

         Class interceptorClass = ad.getImplementationClass();
         HashSet allInterceptorBindings = ReflectionUtilities.getAllBindingsFromClass(interceptorClass);
         boolean found = false;
         Iterator var11 = allInterceptorBindings.iterator();

         while(var11.hasNext()) {
            String interceptorBinding = (String)var11.next();
            if (allBindings.contains(interceptorBinding)) {
               found = true;
               break;
            }
         }

         if (found && handle != null) {
            handles.add(handle);
         }
      }

      List handles = this.orderConstructors(constructor, handles);
      if (handles.isEmpty()) {
         return Collections.emptyList();
      } else {
         List retVal = new ArrayList(handles.size());
         Iterator var15 = handles.iterator();

         while(var15.hasNext()) {
            ServiceHandle handle = (ServiceHandle)var15.next();
            ConstructorInterceptor interceptor = (ConstructorInterceptor)handle.getService();
            if (interceptor != null) {
               retVal.add(interceptor);
            }
         }

         return retVal;
      }
   }
}
