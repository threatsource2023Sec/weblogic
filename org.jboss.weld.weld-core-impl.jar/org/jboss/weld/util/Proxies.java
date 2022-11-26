package org.jboss.weld.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.bean.proxy.ProxyInstantiator;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.exceptions.UnproxyableResolutionException;
import org.jboss.weld.logging.UtilLogger;
import org.jboss.weld.logging.ValidatorLogger;
import org.jboss.weld.util.collections.Arrays2;
import org.jboss.weld.util.reflection.Reflections;

public class Proxies {
   private Proxies() {
   }

   public static boolean isTypeProxyable(Type type, ServiceRegistry services) {
      return getUnproxyableTypeException(type, services) == null;
   }

   public static UnproxyableResolutionException getUnproxyableTypeException(Type type, ServiceRegistry services) {
      return getUnproxyableTypeException(type, (Bean)null, services, false);
   }

   public static boolean isTypesProxyable(Bean declaringBean, ServiceRegistry services) {
      return getUnproxyableTypesException(declaringBean, services) == null;
   }

   public static boolean isTypesProxyable(Iterable types, ServiceRegistry services) {
      return getUnproxyableTypesException(types, services) == null;
   }

   public static UnproxyableResolutionException getUnproxyableTypesException(Bean declaringBean, ServiceRegistry services) {
      if (declaringBean == null) {
         throw new IllegalArgumentException("Null declaring bean!");
      } else {
         return getUnproxyableTypesExceptionInt(declaringBean.getTypes(), declaringBean, services);
      }
   }

   public static UnproxyableResolutionException getUnproxyableTypesException(Iterable types, ServiceRegistry services) {
      return getUnproxyableTypesExceptionInt(types, (Bean)null, services);
   }

   public static UnproxyableResolutionException getUnproxyableTypeException(Type type, Bean declaringBean, ServiceRegistry services, boolean ignoreFinalMethods) {
      return !(type instanceof Class) && !(type instanceof ParameterizedType) && !(type instanceof GenericArrayType) ? ValidatorLogger.LOG.notProxyableUnknown(type, getDeclaringBeanInfo(declaringBean)) : getUnproxyableClassException(Reflections.getRawType(type), declaringBean, services, ignoreFinalMethods);
   }

   private static UnproxyableResolutionException getUnproxyableTypesExceptionInt(Iterable types, Bean declaringBean, ServiceRegistry services) {
      Iterator var3 = types.iterator();

      while(var3.hasNext()) {
         Type apiType = (Type)var3.next();
         if (!Object.class.equals(apiType)) {
            UnproxyableResolutionException e = getUnproxyableTypeException(apiType, declaringBean, services, false);
            if (e != null) {
               return e;
            }
         }
      }

      return null;
   }

   private static UnproxyableResolutionException getUnproxyableClassException(Class clazz, Bean declaringBean, ServiceRegistry services, boolean ignoreFinalMethods) {
      if (clazz.isInterface()) {
         return null;
      } else {
         Constructor constructor = null;

         try {
            constructor = SecurityActions.getDeclaredConstructor(clazz);
         } catch (Exception var6) {
         }

         if (clazz.isPrimitive()) {
            return ValidatorLogger.LOG.notProxyablePrimitive(clazz, getDeclaringBeanInfo(declaringBean));
         } else if (Reflections.isArrayType(clazz)) {
            return ValidatorLogger.LOG.notProxyableArrayType(clazz, getDeclaringBeanInfo(declaringBean));
         } else if (Reflections.isFinal(clazz)) {
            return ValidatorLogger.LOG.notProxyableFinalType(clazz, getDeclaringBeanInfo(declaringBean));
         } else {
            Method finalMethod = Reflections.getNonPrivateNonStaticFinalMethod(clazz);
            if (finalMethod != null) {
               if (!ignoreFinalMethods && !Beans.shouldIgnoreFinalMethods(declaringBean) && !((WeldConfiguration)services.get(WeldConfiguration.class)).isFinalMethodIgnored(clazz.getName())) {
                  return ValidatorLogger.LOG.notProxyableFinalMethod(clazz, finalMethod, getDeclaringBeanInfo(declaringBean));
               }

               ValidatorLogger.LOG.notProxyableFinalMethodIgnored(finalMethod, getDeclaringBeanInfo(declaringBean));
            }

            UnproxyableResolutionException exception = ((ProxyInstantiator)services.get(ProxyInstantiator.class)).validateNoargConstructor(constructor, clazz, declaringBean);
            return exception != null ? exception : null;
         }
      }
   }

   public static Object getDeclaringBeanInfo(Bean bean) {
      return bean != null ? bean : "<unknown javax.enterprise.inject.spi.Bean instance>";
   }

   public static LinkedHashSet sortInterfacesHierarchy(Set interfaces) {
      LinkedHashSet sorted = new LinkedHashSet(interfaces.size());
      processSuperinterface((Class)null, interfaces, sorted);
      if (interfaces.size() != sorted.size()) {
         Set unprocessed = new HashSet(interfaces);
         unprocessed.removeAll(sorted);
         Iterator var3 = unprocessed.iterator();

         while(var3.hasNext()) {
            Class unprocessedInterface = (Class)var3.next();
            processSuperinterface(unprocessedInterface, interfaces, sorted);
            sorted.add(unprocessedInterface);
         }
      }

      return sorted;
   }

   private static void processSuperinterface(Class superinterface, Set interfaces, LinkedHashSet sorted) {
      Iterator var3 = interfaces.iterator();

      while(var3.hasNext()) {
         Class interfaceClass = (Class)var3.next();
         if (isInterfaceExtending(interfaceClass, superinterface)) {
            processSuperinterface(interfaceClass, interfaces, sorted);
            sorted.add(interfaceClass);
         }
      }

   }

   private static boolean isInterfaceExtending(Class interfaceClass, Class superinterface) {
      if (interfaceClass.equals(superinterface)) {
         return false;
      } else if (superinterface == null) {
         return interfaceClass.getInterfaces().length == 0;
      } else {
         return Arrays2.contains(interfaceClass.getInterfaces(), superinterface);
      }
   }

   public static class TypeInfo {
      private final Set interfaces = new LinkedHashSet();
      private final Set classes = new LinkedHashSet();

      private TypeInfo() {
      }

      public Class getSuperClass() {
         if (this.classes.isEmpty()) {
            return Object.class;
         } else {
            Iterator it = this.classes.iterator();
            Class superclass = (Class)it.next();

            while(it.hasNext()) {
               Class clazz = (Class)it.next();
               if (superclass.isAssignableFrom(clazz)) {
                  superclass = clazz;
               }
            }

            return superclass;
         }
      }

      public Class getSuperInterface() {
         if (this.interfaces.isEmpty()) {
            return null;
         } else {
            Iterator it = this.interfaces.iterator();
            Class superclass = (Class)it.next();

            while(it.hasNext()) {
               Class clazz = (Class)it.next();
               if (superclass.isAssignableFrom(clazz)) {
                  superclass = clazz;
               }
            }

            return superclass;
         }
      }

      private TypeInfo add(Type type) {
         if (type instanceof Class) {
            Class clazz = (Class)type;
            if (clazz.isInterface()) {
               this.interfaces.add(clazz);
            } else {
               this.classes.add(clazz);
            }
         } else {
            if (!(type instanceof ParameterizedType)) {
               throw UtilLogger.LOG.cannotProxyNonClassType(type);
            }

            this.add(((ParameterizedType)type).getRawType());
         }

         return this;
      }

      public Set getClasses() {
         return Collections.unmodifiableSet(this.classes);
      }

      public Set getInterfaces() {
         return Collections.unmodifiableSet(this.interfaces);
      }

      public static TypeInfo of(Set types) {
         TypeInfo typeInfo = new TypeInfo();
         Iterator var2 = types.iterator();

         while(var2.hasNext()) {
            Type type = (Type)var2.next();
            typeInfo.add(type);
         }

         return typeInfo;
      }
   }
}
