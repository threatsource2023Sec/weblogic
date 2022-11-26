package weblogic.managedbean;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.ManagedBean;
import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptor;
import javax.interceptor.InterceptorBinding;
import javax.interceptor.Interceptors;
import weblogic.j2ee.dd.xml.J2eeAnnotationProcessor;
import weblogic.j2ee.descriptor.AroundInvokeBean;
import weblogic.j2ee.descriptor.AroundTimeoutBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.InterceptorBindingBean;
import weblogic.j2ee.descriptor.InterceptorMethodsBean;
import weblogic.j2ee.descriptor.InterceptorsBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.MethodParamsBean;
import weblogic.j2ee.descriptor.NamedMethodBean;
import weblogic.j2ee.descriptor.wl.ManagedBeanBean;
import weblogic.j2ee.descriptor.wl.ManagedBeansBean;

public class ManagedBeanAnnotationProcessor extends J2eeAnnotationProcessor {
   private ClassLoader cl;
   private boolean processIcptrBindings = true;

   public ManagedBeanAnnotationProcessor() {
   }

   public ManagedBeanAnnotationProcessor(ClassLoader loader) {
      this.cl = loader;
   }

   public void setProcessIcptrBindings(boolean processIcptrBindings) {
      this.processIcptrBindings = processIcptrBindings;
   }

   public void processAnnotations(Set interceptorClasses, Set beanClasses, ManagedBeansBean mbb) throws ClassNotFoundException {
      Map bindingToInterceptor = new HashMap();
      InterceptorsBean interceptorsBean = mbb.getInterceptors();
      if (interceptorsBean == null) {
         interceptorsBean = mbb.createInterceptors();
      }

      Iterator var6 = interceptorClasses.iterator();

      Class beanClass;
      while(var6.hasNext()) {
         beanClass = (Class)var6.next();
         if (beanClass.isAnnotationPresent(Interceptor.class)) {
            this.findOrCreateInterceptorBean(interceptorsBean, beanClass);
            this.processInterceptorAnnotation(bindingToInterceptor, beanClass);
         }
      }

      var6 = beanClasses.iterator();

      while(var6.hasNext()) {
         beanClass = (Class)var6.next();
         if (beanClass.isAnnotationPresent(ManagedBean.class)) {
            String beanName = ManagedBeanUtils.calculateManagedBeanName(beanClass);
            ManagedBeanBean managedBean = mbb.lookupManagedBean(beanName);
            if (managedBean != null) {
               this.processAroundAnnotations(beanClass, managedBean);
               this.processJ2eeAnnotations(beanClass, managedBean);
               this.recordComponentClass(beanClass);
            }

            Set methods = new HashSet(this.getMethods(beanClass));
            this.processInterceptorBindings(beanName, beanClass, methods, mbb, bindingToInterceptor);
         }
      }

      this.processInterceptorClasses(mbb.getInterceptorBindings(), interceptorsBean);
   }

   private void processInterceptorClasses(InterceptorBindingBean[] interceptorBindings, InterceptorsBean interceptors) throws ClassNotFoundException {
      Set iceptorClassNames = new HashSet();
      int var5;
      int var6;
      if (interceptorBindings != null) {
         InterceptorBindingBean[] var4 = interceptorBindings;
         var5 = interceptorBindings.length;

         for(var6 = 0; var6 < var5; ++var6) {
            InterceptorBindingBean ibb = var4[var6];
            String[] iceptorClasses = null;
            if (ibb.getInterceptorOrder() != null) {
               iceptorClasses = ibb.getInterceptorOrder().getInterceptorClasses();
            } else {
               iceptorClasses = ibb.getInterceptorClasses();
            }

            String[] var9 = iceptorClasses;
            int var10 = iceptorClasses.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               String className = var9[var11];
               iceptorClassNames.add(className);
            }
         }
      }

      if (interceptors != null) {
         InterceptorBean[] var13 = interceptors.getInterceptors();
         var5 = var13.length;

         for(var6 = 0; var6 < var5; ++var6) {
            InterceptorBean iceptor = var13[var6];
            this.processInterceptorClass(iceptor);
            iceptorClassNames.remove(iceptor.getInterceptorClass());
         }
      }

      if (!iceptorClassNames.isEmpty()) {
         Iterator var14 = iceptorClassNames.iterator();

         while(var14.hasNext()) {
            String className = (String)var14.next();
            InterceptorBean iceptor = interceptors.createInterceptor();
            iceptor.setInterceptorClass(className);
            this.processInterceptorClass(iceptor);
         }
      }

   }

   private void processInterceptorClass(InterceptorBean iceptor) throws ClassNotFoundException {
      Class iceptorClass = this.cl.loadClass(iceptor.getInterceptorClass());
      this.processJ2eeAnnotations(iceptorClass, iceptor);
      this.processAroundAnnotations(iceptorClass, iceptor);
      this.processAroundConstructAnnotations(iceptorClass, iceptor);
   }

   protected void processAroundConstructAnnotations(Class iceptorClass, InterceptorBean iceptor) {
      this.addLifecycleCallbackDefaults(iceptor.getAroundConstructs(), iceptorClass.getName());
      Set aroundConstructMethods = new HashSet();
      LifecycleCallbackBean[] var4 = iceptor.getAroundConstructs();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         LifecycleCallbackBean lcb = var4[var6];
         aroundConstructMethods.add(lcb.getLifecycleCallbackMethod());
      }

      Iterator var8 = this.findAnnotatedMethods(iceptorClass, AroundConstruct.class, true).iterator();

      while(var8.hasNext()) {
         Method m = (Method)var8.next();
         if (!aroundConstructMethods.contains(m.getName())) {
            this.populateLifecyleCallbackBean(iceptor.createAroundConstruct(), m);
         }
      }

   }

   protected void processInterceptorAnnotation(Map bindingToInterceptor, Class clazz) {
      if (this.processIcptrBindings) {
         Object target;
         for(Iterator var3 = this.getBindingAnnotations(clazz).iterator(); var3.hasNext(); ((Set)target).add(clazz)) {
            Class binding = (Class)var3.next();
            target = (Set)bindingToInterceptor.get(binding);
            if (target == null) {
               target = new TreeSet(new ClassNameComparator());
               bindingToInterceptor.put(binding, target);
            }
         }

      }
   }

   protected InterceptorBean findOrCreateInterceptorBean(InterceptorsBean isb, Class interceptorClass) {
      String interceptorClassName = interceptorClass.getName();
      InterceptorBean[] var4 = isb.getInterceptors();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         InterceptorBean ib = var4[var6];
         if (interceptorClassName.equals(ib.getInterceptorClass())) {
            return ib;
         }
      }

      InterceptorBean ib = isb.createInterceptor();
      ib.setInterceptorClass(interceptorClassName);
      return ib;
   }

   private void processInterceptorBindings(String beanName, Class beanClass, Set methods, ManagedBeansBean mbb, Map bindingToInterceptor) {
      Iterator var6 = methods.iterator();

      while(true) {
         Method m;
         List interceptorClasses;
         boolean excludeClass;
         do {
            if (!var6.hasNext()) {
               List interceptorClasses = this.getOrderedListOfInterceptors(beanClass, bindingToInterceptor);
               if (!interceptorClasses.isEmpty()) {
                  InterceptorBindingBean ibb = null;
                  InterceptorBindingBean[] var14 = mbb.getInterceptorBindings();
                  int var15 = var14.length;

                  for(int var16 = 0; var16 < var15; ++var16) {
                     InterceptorBindingBean ib = var14[var16];
                     if (ib.getEjbName().equals(beanName) && ib.getMethod() == null) {
                        ibb = ib;
                     }
                  }

                  if (ibb == null) {
                     ibb = mbb.createInterceptorBinding();
                     ibb.setEjbName(beanName);
                  }

                  if (!interceptorClasses.isEmpty()) {
                     this.perhapsAddInterceptors(ibb, interceptorClasses);
                  }
               }

               return;
            }

            m = (Method)var6.next();
            interceptorClasses = this.getOrderedListOfInterceptors(m, bindingToInterceptor);
            excludeClass = m.isAnnotationPresent(ExcludeClassInterceptors.class);
         } while(interceptorClasses.isEmpty() && !excludeClass);

         InterceptorBindingBean ibb = this.getMethodInterceptorBinding(beanName, m, mbb.getInterceptorBindings(), methods);
         if (ibb == null) {
            ibb = mbb.createInterceptorBinding();
            ibb.setEjbName(beanName);
            this.populateMethodBean(ibb.createMethod(), m);
         }

         if (!interceptorClasses.isEmpty()) {
            this.perhapsAddInterceptors(ibb, interceptorClasses);
         }

         if (excludeClass && !this.isSet("ExcludeClassInterceptors", ibb)) {
            ibb.setExcludeClassInterceptors(true);
         }
      }
   }

   private void processAroundAnnotations(Class beanClass, InterceptorMethodsBean ib) {
      Iterator var3 = this.findAnnotatedMethods(beanClass, AroundInvoke.class, true).iterator();

      Method m;
      while(var3.hasNext()) {
         m = (Method)var3.next();
         this.populateAroundInvokeBean(ib.createAroundInvoke(), m);
      }

      var3 = this.findAnnotatedMethods(beanClass, AroundTimeout.class, true).iterator();

      while(var3.hasNext()) {
         m = (Method)var3.next();
         this.populateAroundTimeoutBean(ib.createAroundTimeout(), m);
      }

   }

   protected void populateAroundInvokeBean(AroundInvokeBean lc, Method m) {
      lc.setClassName(m.getDeclaringClass().getName());
      lc.setMethodName(m.getName());
   }

   protected void populateAroundTimeoutBean(AroundTimeoutBean lc, Method m) {
      lc.setClassName(m.getDeclaringClass().getName());
      lc.setMethodName(m.getName());
   }

   protected void populateMethodBean(NamedMethodBean mb, Method m) {
      this.populateMethodBean(mb, m.getName(), m.getParameterTypes());
   }

   protected void populateMethodBean(NamedMethodBean mb, String methodName, Class[] parameterTypes) {
      mb.setMethodName(methodName);
      MethodParamsBean paramsBean = mb.createMethodParams();
      Class[] var5 = parameterTypes;
      int var6 = parameterTypes.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Class param = var5[var7];
         paramsBean.addMethodParam(param.getCanonicalName());
      }

   }

   protected void perhapsAddInterceptors(InterceptorBindingBean ibb, List interceptors) {
      String[] ddInterceptors = ibb.getInterceptorClasses();
      ibb.setInterceptorClasses((String[])null);
      Iterator var4 = interceptors.iterator();

      while(var4.hasNext()) {
         Class ic = (Class)var4.next();
         ibb.addInterceptorClass(ic.getName());
      }

      String[] var8 = ddInterceptors;
      int var9 = ddInterceptors.length;

      for(int var6 = 0; var6 < var9; ++var6) {
         String ddIcptr = var8[var6];
         ibb.addInterceptorClass(ddIcptr);
      }

   }

   protected InterceptorBindingBean getMethodInterceptorBinding(String ejbName, Method m, InterceptorBindingBean[] ibs, Set businessMethods) {
      InterceptorBindingBean[] var5 = ibs;
      int var6 = ibs.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         InterceptorBindingBean ib = var5[var7];
         if (ib.getEjbName().equals(ejbName)) {
            NamedMethodBean method = ib.getMethod();
            if (method != null && m.getName().equals(method.getMethodName())) {
               MethodParamsBean mp = method.getMethodParams();
               if (mp != null) {
                  if (this.paramTypesMatch(mp, m.getParameterTypes())) {
                     return ib;
                  }
               } else {
                  int matchCount = 0;
                  Iterator var12 = businessMethods.iterator();

                  while(var12.hasNext()) {
                     Method bm = (Method)var12.next();
                     if (bm.getName().equals(m.getName())) {
                        ++matchCount;
                        if (matchCount > 1) {
                           break;
                        }
                     }
                  }

                  if (matchCount == 1) {
                     return ib;
                  }
               }
            }
         }
      }

      return null;
   }

   protected InterceptorBindingBean getConstructorInterceptorBinding(String ejbName, Constructor c, InterceptorBindingBean[] ibs) {
      InterceptorBindingBean[] var4 = ibs;
      int var5 = ibs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         InterceptorBindingBean ib = var4[var6];
         NamedMethodBean method = ib.getMethod();
         if (ib.getEjbName().equals(ejbName) && method != null && c.getDeclaringClass().getSimpleName().equals(method.getMethodName())) {
            MethodParamsBean mp = method.getMethodParams();
            if (c.getParameterTypes().length == 0) {
               if (mp == null) {
                  return ib;
               }
            } else if (mp != null && this.paramTypesMatch(mp, c.getParameterTypes())) {
               return ib;
            }
         }
      }

      return null;
   }

   private boolean paramTypesMatch(MethodParamsBean mp, Class[] paramTypes) {
      String[] params = mp.getMethodParams();
      if (params.length == paramTypes.length) {
         for(int index = 0; index < params.length; ++index) {
            if (!paramTypes[index].getName().equals(params[index])) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private List getBindingAnnotations(Class object) {
      List retVal = new LinkedList();
      Annotation[] var3 = object.getAnnotations();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Annotation annotation = var3[var5];
         Class annotationType = annotation.annotationType();
         if (annotationType.isAnnotationPresent(InterceptorBinding.class)) {
            retVal.add(annotationType);
         }
      }

      return retVal;
   }

   protected List getOrderedListOfInterceptors(Method object, Map bindingToInterceptors) {
      LinkedList retVal = new LinkedList();
      this.getOrderedListOfInterceptors(object, bindingToInterceptors, retVal);
      return retVal;
   }

   protected List getOrderedListOfInterceptors(Constructor object, Map bindingToInterceptors) {
      LinkedList retVal = new LinkedList();
      this.getOrderedListOfInterceptors(object, bindingToInterceptors, retVal);
      return retVal;
   }

   protected List getOrderedListOfInterceptors(Class object, Map bindingToInterceptors) {
      LinkedList retVal = new LinkedList();
      this.getOrderedListOfInterceptorsSingleClass(object, bindingToInterceptors, retVal);
      return retVal;
   }

   private void getOrderedListOfInterceptors(AnnotatedElement object, Map bindingToInterceptors, LinkedList processed) {
      Annotation[] var4 = object.getDeclaredAnnotations();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Annotation annotation = var4[var6];
         Class annotationType = annotation.annotationType();
         if (Interceptors.class.equals(annotationType)) {
            Interceptors ic = (Interceptors)object.getAnnotation(Interceptors.class);
            Class[] var15 = ic.value();
            int var16 = var15.length;

            for(int var12 = 0; var12 < var16; ++var12) {
               Class value = var15[var12];
               processed.add(value);
            }
         } else if (this.processIcptrBindings && annotationType.isAnnotationPresent(InterceptorBinding.class)) {
            Set interceptors = (Set)bindingToInterceptors.get(annotationType);
            if (interceptors != null) {
               Iterator var10 = interceptors.iterator();

               while(var10.hasNext()) {
                  Class interceptor = (Class)var10.next();
                  processed.add(interceptor);
               }
            }
         }
      }

   }

   private void getOrderedListOfInterceptorsSingleClass(Class object, Map bindingToInterceptors, LinkedList processed) {
      if (object != null && !Object.class.equals(object)) {
         this.getOrderedListOfInterceptorsSingleClass(object.getSuperclass(), bindingToInterceptors, processed);
         this.getOrderedListOfInterceptors(object, bindingToInterceptors, processed);
      }
   }

   protected List findAnnotatedMethods(Class bean, Class annotation, boolean needNonOverridenMethods) {
      List allmethods;
      if (needNonOverridenMethods) {
         allmethods = this.getMethods(bean);
      } else {
         allmethods = Arrays.asList(bean.getMethods());
      }

      List methods = new ArrayList();
      Iterator var6 = allmethods.iterator();

      while(var6.hasNext()) {
         Method m = (Method)var6.next();
         if (m.isAnnotationPresent(annotation)) {
            methods.add(m);
         }
      }

      return methods;
   }

   protected static final class ClassNameComparator implements Comparator {
      public ClassNameComparator() {
      }

      public int compare(Class o1, Class o2) {
         return o1.getName().compareTo(o2.getName());
      }
   }
}
