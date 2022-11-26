package com.oracle.weblogic.diagnostics.expressions;

import com.oracle.weblogic.diagnostics.utils.BeanInfoLocalizer;
import com.oracle.weblogic.diagnostics.utils.DiagnosticsUtils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.AnnotationLiteral;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;

@Service
@Singleton
public class ExpressionExtensionsManager {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugExpressionExtensionsManager");
   @Inject
   private ExtensionBeanInfoFinder beanInfoFinder;
   @Inject
   private EvaluatorFactory evalFactory;
   @Inject
   private ServiceLocator locator;
   private List beanProviders;
   private List functionProviders;
   private final ConcurrentHashMap beanNamespaceMap = new ConcurrentHashMap();
   private final ConcurrentHashMap functionNamespaceMap = new ConcurrentHashMap();

   @PostConstruct
   private void postConstruct() {
      this.beanProviders = this.locator.getAllServiceHandles(new ExpressionBeanImpl(), new Annotation[0]);
      this.scanServices(this.beanProviders);
      this.functionProviders = this.locator.getAllServiceHandles(new FunctionProviderImpl(), new Annotation[0]);
      this.scanServices(this.functionProviders);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Namespaces found: " + this.beanNamespaceMap.toString());
      }

   }

   public String[] getNamespaces() {
      HashSet namespaces = new HashSet();
      namespaces.addAll(this.beanNamespaceMap.keySet());
      namespaces.addAll(this.functionNamespaceMap.keySet());
      return (String[])namespaces.toArray(new String[namespaces.size()]);
   }

   public String[] getBeanNames(String ns, Annotation... qualifiers) {
      String namespace = normalizeNamespace(ns);
      Set eligibleBeans = new HashSet();
      Map beanMap = (Map)this.beanNamespaceMap.get(namespace);
      if (qualifiers != null && qualifiers.length > 0) {
         if (beanMap != null) {
            Iterator var6 = beanMap.entrySet().iterator();

            while(var6.hasNext()) {
               Map.Entry entry = (Map.Entry)var6.next();
               if (this.serviceHasRequiredQualifiers((ServiceHandle)entry.getValue(), qualifiers)) {
                  eligibleBeans.add(entry.getKey());
               }
            }
         }
      } else if (beanMap != null) {
         eligibleBeans.addAll(beanMap.keySet());
      }

      return (String[])eligibleBeans.toArray(new String[eligibleBeans.size()]);
   }

   public BeanInfo[] getExpressionBeanInfos(String ns, Annotation... qualifiers) {
      return this.getExpressionBeanInfos(ns, Locale.getDefault(), qualifiers);
   }

   public BeanInfo[] getExpressionBeanInfos(String ns, Locale l, Annotation... qualifiers) {
      ArrayList beanList = new ArrayList();
      String[] beanNames = this.getBeanNames(ns, qualifiers);
      String[] var6 = beanNames;
      int var7 = beanNames.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String name = var6[var8];
         BeanInfo beanInfo = this.getExpressionBeanInfo(ns, name, l);
         if (beanInfo != null) {
            beanList.add(beanInfo);
         }
      }

      return (BeanInfo[])beanList.toArray(new BeanInfo[beanList.size()]);
   }

   public BeanInfo getExpressionBeanInfo(String ns, String name) {
      return this.getExpressionBeanInfo(ns, name, Locale.getDefault());
   }

   public BeanInfo getExpressionBeanInfo(String ns, String name, Locale l) {
      BeanInfo beanInfo = this.beanInfoFinder.findBeanInfo(ns, name, l);
      if (beanInfo == null) {
         ServiceHandle beanServiceHandle = this.getBeanServiceHandle(ns, name);
         if (beanServiceHandle != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Creating beanInfo for " + ns + "." + name);
            }

            ActiveDescriptor activeDescriptor = this.getActiveDescriptor(beanServiceHandle);
            Class implementationClass = activeDescriptor.getImplementationClass();
            beanInfo = this.getBeanInfo(implementationClass, l);
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No service handle found for " + ns + "." + name);
         }
      }

      return beanInfo;
   }

   public BeanInfo getBeanInfo(String nodeExpression, Annotation... qualifiers) {
      return this.getBeanInfo(nodeExpression, Locale.getDefault(), qualifiers);
   }

   public BeanInfo getBeanInfo(String nodeExpression, Locale l, Annotation... qualifiers) {
      ExpressionEvaluator evaluator = this.evalFactory.createEvaluator(qualifiers);

      try {
         Class resultType = evaluator.getType(nodeExpression);
         if (resultType != null) {
            BeanInfo var6 = this.getBeanInfo(resultType, l);
            return var6;
         }
      } finally {
         this.evalFactory.destroyEvaluator(evaluator);
      }

      return null;
   }

   public BeanInfo getBeanInfo(Class beanProperty) {
      return this.getBeanInfo(beanProperty, Locale.getDefault());
   }

   public BeanInfo getBeanInfo(Class beanPropertyClass, Locale l) {
      BeanInfo info = null;
      if (beanPropertyClass != null) {
         try {
            if (!DiagnosticsUtils.isPrimitiveUnBoxedType(beanPropertyClass)) {
               info = this.beanInfoFinder.findBeanInfo(beanPropertyClass, l);
               if (info == null) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Building localized bean info for " + beanPropertyClass.getName());
                  }

                  info = Utils.buildLocalizedExpressionBeanInfo(beanPropertyClass, l);
               }
            }
         } catch (IntrospectionException var5) {
            throw new ExpressionBeanRuntimeException(var5);
         }
      }

      return info;
   }

   public BeanInfo findExtensionBeanInfo(Class clazz) {
      return this.beanInfoFinder.findBeanInfo(clazz, Locale.getDefault());
   }

   public Object getBeanInstance(String ns, String bean, Annotation... qualifiers) {
      ServiceHandle beanService = this.getBeanServiceHandle(ns, bean, qualifiers);
      if (beanService == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Could not find service impl for " + ns + "." + bean);
         }

         return null;
      } else {
         ActiveDescriptor activeDescriptor = this.getActiveDescriptor(beanService);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Found service " + activeDescriptor.getImplementation() + " for " + ns + "." + bean);
         }

         return this.locator.getService(activeDescriptor.getImplementationClass(), new Annotation[0]);
      }
   }

   public Method lookupFunction(String ns, String fnName, Annotation... qualifiers) {
      Method m = null;
      String normalizeNamespace = normalizeNamespace(ns);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Looking up function ns=" + normalizeNamespace + ", name=" + fnName);
      }

      Map serviceMap = (Map)this.functionNamespaceMap.get(normalizeNamespace);
      if (serviceMap != null) {
         Iterator var7 = serviceMap.values().iterator();

         while(var7.hasNext()) {
            ServiceHandle handle = (ServiceHandle)var7.next();
            if (this.serviceHasRequiredQualifiers(handle, qualifiers)) {
               Class implementationClass = this.getActiveDescriptor(handle).getImplementationClass();
               m = this.findFunctionMethod(implementationClass, fnName);
               if (m != null) {
                  this.locator.getService(implementationClass, new Annotation[0]);
                  break;
               }
            }
         }
      }

      return m;
   }

   public MethodDescriptor[] getFunctionDescriptors(String ns, Annotation... qualifiers) {
      return this.getFunctionDescriptors(ns, Locale.getDefault(), qualifiers);
   }

   public MethodDescriptor[] getFunctionDescriptors(String ns, Locale l, Annotation... qualifiers) {
      List resultSet = new ArrayList();
      String normalizedNamespace = normalizeNamespace(ns);
      Map serviceMap = (Map)this.functionNamespaceMap.get(normalizedNamespace);
      if (serviceMap != null) {
         Iterator var7 = serviceMap.entrySet().iterator();

         while(true) {
            Map.Entry entry;
            do {
               if (!var7.hasNext()) {
                  return (MethodDescriptor[])resultSet.toArray(new MethodDescriptor[resultSet.size()]);
               }

               entry = (Map.Entry)var7.next();
            } while(!this.serviceHasRequiredQualifiers((ServiceHandle)entry.getValue(), qualifiers));

            Class serviceImplClass = this.getActiveDescriptor((ServiceHandle)entry.getValue()).getImplementationClass();

            try {
               List descriptors = this.buildFunctionDescriptors(serviceImplClass, ns, l);
               resultSet.addAll(descriptors);
            } catch (IntrospectionException var11) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Error occurred looking up function descriptors for provider " + serviceImplClass.getName() + " in namespace " + normalizedNamespace, var11);
               }
            }
         }
      } else {
         return (MethodDescriptor[])resultSet.toArray(new MethodDescriptor[resultSet.size()]);
      }
   }

   private List buildFunctionDescriptors(Class serviceImplClass, String ns, Locale l) throws IntrospectionException {
      BeanInfo fpBeanInfo = Utils.getBeanInfoForClass(serviceImplClass);
      MethodDescriptor[] methodDescriptors = fpBeanInfo.getMethodDescriptors();
      List functionsList = new ArrayList();
      MethodDescriptor[] var7 = methodDescriptors;
      int var8 = methodDescriptors.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         MethodDescriptor md = var7[var9];
         if (FunctionDescriptor.isFunctionOrSmartRule(md.getMethod())) {
            MethodDescriptor fd = FunctionDescriptor.createDescriptor(md, ns);
            BeanInfoLocalizer.localize(fd, l);
            functionsList.add(fd);
         }
      }

      return functionsList;
   }

   private Set scanServices(List serviceHandles) {
      HashSet nsSet = new HashSet();
      Iterator var3 = serviceHandles.iterator();

      while(var3.hasNext()) {
         ServiceHandle handle = (ServiceHandle)var3.next();
         ActiveDescriptor serviceDescriptor = this.getActiveDescriptor(handle);
         Iterator var6 = serviceDescriptor.getQualifierAnnotations().iterator();

         while(var6.hasNext()) {
            Annotation a = (Annotation)var6.next();
            if (a instanceof ExpressionBean) {
               ExpressionBean beanAnnotation = (ExpressionBean)a;
               this.scanBeanService(handle, beanAnnotation);
            } else if (a instanceof FunctionProvider) {
               this.scanFunctionService(handle, serviceDescriptor, a);
            }
         }
      }

      return nsSet;
   }

   private void scanFunctionService(ServiceHandle handle, ActiveDescriptor serviceDescriptor, Annotation a) {
      FunctionProvider functionAnnotation = (FunctionProvider)a;
      String namespace = functionAnnotation.prefix();
      Map functionServiceMap = (Map)this.functionNamespaceMap.get(namespace);
      if (functionServiceMap == null) {
         functionServiceMap = new ConcurrentHashMap();
         this.functionNamespaceMap.put(namespace, functionServiceMap);
      }

      ((Map)functionServiceMap).put(serviceDescriptor.getImplementation(), handle);
   }

   private void scanBeanService(ServiceHandle handle, ExpressionBean beanAnnotation) {
      String beanName = beanAnnotation.name();
      if (beanName != null && !beanName.isEmpty()) {
         String namespace = beanAnnotation.prefix();
         Map beanNamespace = (Map)this.beanNamespaceMap.get(namespace);
         if (beanNamespace == null) {
            beanNamespace = new ConcurrentHashMap();
            this.beanNamespaceMap.put(namespace, beanNamespace);
         }

         ((Map)beanNamespace).put(beanName, handle);
      } else {
         throw new IllegalArgumentException(beanName);
      }
   }

   static String normalizeNamespace(String ns) {
      String normalizedNamespace = ns;
      if (null != ns) {
         normalizedNamespace = ns.trim();
      }

      return normalizedNamespace;
   }

   private Method findFunctionMethod(Class serviceImpl, String functionName) {
      Method m = null;
      Method[] serviceMethods = serviceImpl.getMethods();
      Method[] var5 = serviceMethods;
      int var6 = serviceMethods.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Method serviceMethod = var5[var7];
         m = this.identifyFunctionOrSmartRule(serviceImpl, functionName, serviceMethod);
         if (m != null) {
            break;
         }
      }

      return m;
   }

   private Method identifyFunctionOrSmartRule(Class serviceImpl, String requestedFunctionName, Method serviceMethod) {
      Method functionMethod = null;
      boolean hasFunctionAnnotation = FunctionDescriptor.hasFunctionAnnotation(serviceMethod);
      boolean hasSmartRuleAnnotation = FunctionDescriptor.hasSmartRuleAnnotation(serviceMethod);
      if ((hasFunctionAnnotation || hasSmartRuleAnnotation) && serviceMethod.getName().equals(requestedFunctionName)) {
         functionMethod = serviceMethod;
         if (hasSmartRuleAnnotation) {
            this.checkSmartRuleReturnType(serviceMethod);
         }

         this.locator.getService(serviceImpl, new Annotation[0]);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Found matching method: " + serviceMethod.toString());
         }
      }

      return functionMethod;
   }

   private void checkSmartRuleReturnType(Method serviceMethod) {
      if (!FunctionDescriptor.isPredicate(serviceMethod)) {
         throw new InvalidSmartRuleException(serviceMethod.getDeclaringClass().getName() + "." + serviceMethod.getName());
      }
   }

   static boolean isExcluded(PropertyDescriptor pd) {
      return FunctionDescriptor.hasExcludeAnnotation(pd.getReadMethod()) || FunctionDescriptor.hasExcludeAnnotation(pd.getWriteMethod());
   }

   private ActiveDescriptor getActiveDescriptor(ServiceHandle beanServiceHandle) {
      ActiveDescriptor activeDescriptor = beanServiceHandle.getActiveDescriptor();
      if (!activeDescriptor.isReified()) {
         this.locator.reifyDescriptor(activeDescriptor);
      }

      return activeDescriptor;
   }

   private ServiceHandle getBeanServiceHandle(String ns, String bean, Annotation... qualifiers) {
      ServiceHandle beanService = null;
      String normalizedNamespace = normalizeNamespace(ns);
      if (normalizedNamespace == null) {
         return beanService;
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Searching for bean " + bean + " in namespace " + ns + " with qualifiers " + Arrays.toString(qualifiers));
         }

         Map namespaceToServiceMap = (Map)this.beanNamespaceMap.get(normalizedNamespace);
         ServiceHandle candidateService = null;
         if (namespaceToServiceMap != null) {
            candidateService = (ServiceHandle)namespaceToServiceMap.get(bean);
            if (candidateService != null && this.serviceHasRequiredQualifiers(candidateService, qualifiers)) {
               beanService = candidateService;
            }
         }

         return beanService;
      }
   }

   private boolean serviceHasRequiredQualifiers(ServiceHandle candidateService, Annotation... requiredQualifiers) {
      if (requiredQualifiers != null && requiredQualifiers.length != 0) {
         Set serviceQualifiers = candidateService.getActiveDescriptor().getQualifierAnnotations();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Comparing required qualifers " + Arrays.toString(requiredQualifiers) + " to the service qualifiers for service " + candidateService.getActiveDescriptor().getName() + ": " + serviceQualifiers.toString());
         }

         return serviceQualifiers.containsAll(Arrays.asList(requiredQualifiers));
      } else {
         return true;
      }
   }

   private static class FunctionProviderImpl extends AnnotationLiteral implements FunctionProvider {
      private FunctionProviderImpl() {
      }

      public String prefix() {
         return null;
      }

      public String category() {
         return "";
      }

      // $FF: synthetic method
      FunctionProviderImpl(Object x0) {
         this();
      }
   }

   private static class ExpressionBeanImpl extends AnnotationLiteral implements ExpressionBean {
      private ExpressionBeanImpl() {
      }

      public String name() {
         return null;
      }

      public String prefix() {
         return null;
      }

      // $FF: synthetic method
      ExpressionBeanImpl(Object x0) {
         this();
      }
   }
}
