package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.springframework.aop.SpringProxy;
import com.bea.core.repackaged.springframework.aop.TargetClassAware;
import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.aop.target.SingletonTargetSource;
import com.bea.core.repackaged.springframework.core.DecoratingProxy;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public abstract class AopProxyUtils {
   @Nullable
   public static Object getSingletonTarget(Object candidate) {
      if (candidate instanceof Advised) {
         TargetSource targetSource = ((Advised)candidate).getTargetSource();
         if (targetSource instanceof SingletonTargetSource) {
            return ((SingletonTargetSource)targetSource).getTarget();
         }
      }

      return null;
   }

   public static Class ultimateTargetClass(Object candidate) {
      Assert.notNull(candidate, "Candidate object must not be null");
      Object current = candidate;

      Class result;
      for(result = null; current instanceof TargetClassAware; current = getSingletonTarget(current)) {
         result = ((TargetClassAware)current).getTargetClass();
      }

      if (result == null) {
         result = AopUtils.isCglibProxy(candidate) ? candidate.getClass().getSuperclass() : candidate.getClass();
      }

      return result;
   }

   public static Class[] completeProxiedInterfaces(AdvisedSupport advised) {
      return completeProxiedInterfaces(advised, false);
   }

   static Class[] completeProxiedInterfaces(AdvisedSupport advised, boolean decoratingProxy) {
      Class[] specifiedInterfaces = advised.getProxiedInterfaces();
      if (specifiedInterfaces.length == 0) {
         Class targetClass = advised.getTargetClass();
         if (targetClass != null) {
            if (targetClass.isInterface()) {
               advised.setInterfaces(targetClass);
            } else if (Proxy.isProxyClass(targetClass)) {
               advised.setInterfaces(targetClass.getInterfaces());
            }

            specifiedInterfaces = advised.getProxiedInterfaces();
         }
      }

      boolean addSpringProxy = !advised.isInterfaceProxied(SpringProxy.class);
      boolean addAdvised = !advised.isOpaque() && !advised.isInterfaceProxied(Advised.class);
      boolean addDecoratingProxy = decoratingProxy && !advised.isInterfaceProxied(DecoratingProxy.class);
      int nonUserIfcCount = 0;
      if (addSpringProxy) {
         ++nonUserIfcCount;
      }

      if (addAdvised) {
         ++nonUserIfcCount;
      }

      if (addDecoratingProxy) {
         ++nonUserIfcCount;
      }

      Class[] proxiedInterfaces = new Class[specifiedInterfaces.length + nonUserIfcCount];
      System.arraycopy(specifiedInterfaces, 0, proxiedInterfaces, 0, specifiedInterfaces.length);
      int index = specifiedInterfaces.length;
      if (addSpringProxy) {
         proxiedInterfaces[index] = SpringProxy.class;
         ++index;
      }

      if (addAdvised) {
         proxiedInterfaces[index] = Advised.class;
         ++index;
      }

      if (addDecoratingProxy) {
         proxiedInterfaces[index] = DecoratingProxy.class;
      }

      return proxiedInterfaces;
   }

   public static Class[] proxiedUserInterfaces(Object proxy) {
      Class[] proxyInterfaces = proxy.getClass().getInterfaces();
      int nonUserIfcCount = 0;
      if (proxy instanceof SpringProxy) {
         ++nonUserIfcCount;
      }

      if (proxy instanceof Advised) {
         ++nonUserIfcCount;
      }

      if (proxy instanceof DecoratingProxy) {
         ++nonUserIfcCount;
      }

      Class[] userInterfaces = new Class[proxyInterfaces.length - nonUserIfcCount];
      System.arraycopy(proxyInterfaces, 0, userInterfaces, 0, userInterfaces.length);
      Assert.notEmpty((Object[])userInterfaces, (String)"JDK proxy must implement one or more interfaces");
      return userInterfaces;
   }

   public static boolean equalsInProxy(AdvisedSupport a, AdvisedSupport b) {
      return a == b || equalsProxiedInterfaces(a, b) && equalsAdvisors(a, b) && a.getTargetSource().equals(b.getTargetSource());
   }

   public static boolean equalsProxiedInterfaces(AdvisedSupport a, AdvisedSupport b) {
      return Arrays.equals(a.getProxiedInterfaces(), b.getProxiedInterfaces());
   }

   public static boolean equalsAdvisors(AdvisedSupport a, AdvisedSupport b) {
      return Arrays.equals(a.getAdvisors(), b.getAdvisors());
   }

   static Object[] adaptArgumentsIfNecessary(Method method, @Nullable Object[] arguments) {
      if (ObjectUtils.isEmpty(arguments)) {
         return new Object[0];
      } else {
         if (method.isVarArgs()) {
            Class[] paramTypes = method.getParameterTypes();
            if (paramTypes.length == arguments.length) {
               int varargIndex = paramTypes.length - 1;
               Class varargType = paramTypes[varargIndex];
               if (varargType.isArray()) {
                  Object varargArray = arguments[varargIndex];
                  if (varargArray instanceof Object[] && !varargType.isInstance(varargArray)) {
                     Object[] newArguments = new Object[arguments.length];
                     System.arraycopy(arguments, 0, newArguments, 0, varargIndex);
                     Class targetElementType = varargType.getComponentType();
                     int varargLength = Array.getLength(varargArray);
                     Object newVarargArray = Array.newInstance(targetElementType, varargLength);
                     System.arraycopy(varargArray, 0, newVarargArray, 0, varargLength);
                     newArguments[varargIndex] = newVarargArray;
                     return newArguments;
                  }
               }
            }
         }

         return arguments;
      }
   }
}
