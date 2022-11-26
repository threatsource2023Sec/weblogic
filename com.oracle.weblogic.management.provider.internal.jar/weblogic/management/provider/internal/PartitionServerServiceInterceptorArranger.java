package weblogic.management.provider.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.extras.interception.InterceptorOrderingService;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.partition.admin.PartitionLifecycleDebugger;
import weblogic.management.partition.admin.PartitionManagerService;
import weblogic.server.ServerService;

@Singleton
@Service
public class PartitionServerServiceInterceptorArranger implements InterceptorOrderingService {
   private final Map serverServiceOrdering = new HashMap();
   private final MultiException errors = new MultiException();
   @Inject
   private ServerServiceDependencyAnalyzer analyzer;

   @PostConstruct
   private void buildServerServiceOrdering() {
      int i = 0;
      Iterator var2 = this.analyzer.orderedServerServices().iterator();

      while(var2.hasNext()) {
         Class ssClass = (Class)var2.next();
         this.serverServiceOrdering.put(ssClass, i++);
      }

   }

   public MultiException getErrors() {
      return this.errors;
   }

   public List modifyMethodInterceptors(Method method, List currentList) {
      if (!method.getDeclaringClass().equals(PartitionManagerService.class)) {
         return null;
      } else {
         currentList = this.gatherServerServiceInterceptors(currentList);
         this.dumpHandles("Intermediate ", method, currentList);
         List result = new ArrayList();
         Map serverServiceInterceptors = new HashMap();
         boolean isProcessingReorderableInterceptors = false;
         boolean isDoneProcessingReorderableInterceptors = false;

         try {
            Iterator var7 = currentList.iterator();

            while(var7.hasNext()) {
               ServiceHandle miHandle = (ServiceHandle)var7.next();
               ServerServiceInterceptor ssi = (ServerServiceInterceptor)((MethodInterceptor)miHandle.getService()).getClass().getAnnotation(ServerServiceInterceptor.class);
               if (ssi != null) {
                  if (isDoneProcessingReorderableInterceptors) {
                     StringBuilder sb = new StringBuilder("Reorderable interceptor handles are not adjacent: ServerServiceInterceptor ");
                     sb.append(miHandle.getActiveDescriptor().getImplementationClass().getName()).append(" appears after an earlier contiguous group of other ServerServiceInterceptors");
                     PartitionLifecycleDebugger.debug(sb.toString());
                     throw new IllegalArgumentException(sb.toString());
                  }

                  Class serviceClass = this.getServerService(ssi);
                  if (serviceClass == null) {
                     PartitionLifecycleDebugger.debug("WARNING: ServerServiceInterceptor " + ((MethodInterceptor)miHandle.getService()).getClass().getName() + " refers to a server service class that could not be found");
                  }

                  isProcessingReorderableInterceptors = true;
                  serverServiceInterceptors.put(miHandle, serviceClass);
               } else {
                  if (isProcessingReorderableInterceptors) {
                     isProcessingReorderableInterceptors = false;
                     isDoneProcessingReorderableInterceptors = true;
                     PartitionLifecycleDebugger.debug("Detected end of server service interceptors");
                     result.addAll(this.sort(serverServiceInterceptors));
                  }

                  result.add(miHandle);
               }
            }

            if (!isDoneProcessingReorderableInterceptors) {
               result.addAll(this.sort(serverServiceInterceptors));
            }

            this.dumpHandles("Final", method, result);
            return result;
         } catch (Exception var11) {
            PartitionLifecycleDebugger.debug("Error re-ordering interceptors" + var11.getMessage());
            this.errors.addError(var11);
            throw var11;
         }
      }
   }

   private List sort(Map serviceHandles) {
      List workingList = new ArrayList(serviceHandles.entrySet());
      Collections.sort(workingList, new Comparator() {
         public int compare(Map.Entry o1, Map.Entry o2) {
            Integer o1Position = (Integer)PartitionServerServiceInterceptorArranger.this.serverServiceOrdering.get(o1.getValue());
            Integer o2Position = (Integer)PartitionServerServiceInterceptorArranger.this.serverServiceOrdering.get(o2.getValue());
            if (o1Position == null) {
               o1Position = Integer.MAX_VALUE;
            }

            if (o2Position == null) {
               o2Position = Integer.MAX_VALUE;
            }

            byte result;
            if (o1Position < o2Position) {
               result = -1;
            } else if (o1Position > o2Position) {
               result = 1;
            } else {
               result = 0;
            }

            return result;
         }
      });
      List result = new ArrayList();
      Iterator var4 = workingList.iterator();

      while(var4.hasNext()) {
         Map.Entry entry = (Map.Entry)var4.next();
         result.add(entry.getKey());
      }

      return result;
   }

   private void dumpHandles(String titlePrefix, Method method, List currentList) {
      if (PartitionLifecycleDebugger.isDebugEnabled()) {
         PartitionLifecycleDebugger.debug(titlePrefix + " interceptor ordering for " + method.getDeclaringClass().getName() + "." + method.getName());
         Iterator var4 = currentList.iterator();

         while(var4.hasNext()) {
            ServiceHandle handle = (ServiceHandle)var4.next();
            PartitionLifecycleDebugger.debug("  " + handle.getActiveDescriptor().getImplementationClass().getName());
         }
      }

   }

   private List gatherServerServiceInterceptors(List currentList) {
      List result = new ArrayList(currentList);
      Collections.sort(result, new Comparator() {
         public int compare(ServiceHandle o1, ServiceHandle o2) {
            Class o1Class = o1.getActiveDescriptor().getImplementationClass();
            Annotation anno = o1Class.getAnnotation(Rank.class);
            int o1Rank = anno != null ? ((Rank)anno).value() : 0;
            Class o2Class = o2.getActiveDescriptor().getImplementationClass();
            anno = o2Class.getAnnotation(Rank.class);
            int o2Rank = anno != null ? ((Rank)anno).value() : 0;
            if (o1Rank < o2Rank) {
               return 1;
            } else if (o1Rank > o2Rank) {
               return -1;
            } else {
               return o2Class.getAnnotation(ServerServiceInterceptor.class) != null ? -1 : 1;
            }
         }
      });
      return result;
   }

   private Class getServerService(ServerServiceInterceptor ssi) {
      return ssi != null && ServerService.class.isAssignableFrom(ssi.value()) ? ssi.value() : null;
   }

   private Class serverServerClass(Class c) {
      return ServerService.class.isAssignableFrom(c) ? c : null;
   }

   public List modifyConstructorInterceptors(Constructor constructor, List currentList) {
      return null;
   }

   private static Integer findRunLevel(Class c) {
      RunLevel rl = (RunLevel)c.getAnnotation(RunLevel.class);
      return rl == null ? null : new Integer(rl.value());
   }
}
