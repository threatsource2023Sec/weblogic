package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.KotlinDetector;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.ReflectJvmMapping;

public abstract class BeanUtils {
   private static final Log logger = LogFactory.getLog(BeanUtils.class);
   private static final Set unknownEditorTypes = Collections.newSetFromMap(new ConcurrentReferenceHashMap(64));

   /** @deprecated */
   @Deprecated
   public static Object instantiate(Class clazz) throws BeanInstantiationException {
      Assert.notNull(clazz, (String)"Class must not be null");
      if (clazz.isInterface()) {
         throw new BeanInstantiationException(clazz, "Specified class is an interface");
      } else {
         try {
            return clazz.newInstance();
         } catch (InstantiationException var2) {
            throw new BeanInstantiationException(clazz, "Is it an abstract class?", var2);
         } catch (IllegalAccessException var3) {
            throw new BeanInstantiationException(clazz, "Is the constructor accessible?", var3);
         }
      }
   }

   public static Object instantiateClass(Class clazz) throws BeanInstantiationException {
      Assert.notNull(clazz, (String)"Class must not be null");
      if (clazz.isInterface()) {
         throw new BeanInstantiationException(clazz, "Specified class is an interface");
      } else {
         try {
            return instantiateClass(clazz.getDeclaredConstructor());
         } catch (NoSuchMethodException var3) {
            Constructor ctor = findPrimaryConstructor(clazz);
            if (ctor != null) {
               return instantiateClass(ctor);
            } else {
               throw new BeanInstantiationException(clazz, "No default constructor found", var3);
            }
         } catch (LinkageError var4) {
            throw new BeanInstantiationException(clazz, "Unresolvable class definition", var4);
         }
      }
   }

   public static Object instantiateClass(Class clazz, Class assignableTo) throws BeanInstantiationException {
      Assert.isAssignable(assignableTo, clazz);
      return instantiateClass(clazz);
   }

   public static Object instantiateClass(Constructor ctor, Object... args) throws BeanInstantiationException {
      Assert.notNull(ctor, (String)"Constructor must not be null");

      try {
         ReflectionUtils.makeAccessible(ctor);
         return KotlinDetector.isKotlinReflectPresent() && KotlinDetector.isKotlinType(ctor.getDeclaringClass()) ? BeanUtils.KotlinDelegate.instantiateClass(ctor, args) : ctor.newInstance(args);
      } catch (InstantiationException var3) {
         throw new BeanInstantiationException(ctor, "Is it an abstract class?", var3);
      } catch (IllegalAccessException var4) {
         throw new BeanInstantiationException(ctor, "Is the constructor accessible?", var4);
      } catch (IllegalArgumentException var5) {
         throw new BeanInstantiationException(ctor, "Illegal arguments for constructor", var5);
      } catch (InvocationTargetException var6) {
         throw new BeanInstantiationException(ctor, "Constructor threw exception", var6.getTargetException());
      }
   }

   @Nullable
   public static Constructor findPrimaryConstructor(Class clazz) {
      Assert.notNull(clazz, (String)"Class must not be null");
      if (KotlinDetector.isKotlinReflectPresent() && KotlinDetector.isKotlinType(clazz)) {
         Constructor kotlinPrimaryConstructor = BeanUtils.KotlinDelegate.findPrimaryConstructor(clazz);
         if (kotlinPrimaryConstructor != null) {
            return kotlinPrimaryConstructor;
         }
      }

      return null;
   }

   @Nullable
   public static Method findMethod(Class clazz, String methodName, Class... paramTypes) {
      try {
         return clazz.getMethod(methodName, paramTypes);
      } catch (NoSuchMethodException var4) {
         return findDeclaredMethod(clazz, methodName, paramTypes);
      }
   }

   @Nullable
   public static Method findDeclaredMethod(Class clazz, String methodName, Class... paramTypes) {
      try {
         return clazz.getDeclaredMethod(methodName, paramTypes);
      } catch (NoSuchMethodException var4) {
         return clazz.getSuperclass() != null ? findDeclaredMethod(clazz.getSuperclass(), methodName, paramTypes) : null;
      }
   }

   @Nullable
   public static Method findMethodWithMinimalParameters(Class clazz, String methodName) throws IllegalArgumentException {
      Method targetMethod = findMethodWithMinimalParameters(clazz.getMethods(), methodName);
      if (targetMethod == null) {
         targetMethod = findDeclaredMethodWithMinimalParameters(clazz, methodName);
      }

      return targetMethod;
   }

   @Nullable
   public static Method findDeclaredMethodWithMinimalParameters(Class clazz, String methodName) throws IllegalArgumentException {
      Method targetMethod = findMethodWithMinimalParameters(clazz.getDeclaredMethods(), methodName);
      if (targetMethod == null && clazz.getSuperclass() != null) {
         targetMethod = findDeclaredMethodWithMinimalParameters(clazz.getSuperclass(), methodName);
      }

      return targetMethod;
   }

   @Nullable
   public static Method findMethodWithMinimalParameters(Method[] methods, String methodName) throws IllegalArgumentException {
      Method targetMethod = null;
      int numMethodsFoundWithCurrentMinimumArgs = 0;
      Method[] var4 = methods;
      int var5 = methods.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method method = var4[var6];
         if (method.getName().equals(methodName)) {
            int numParams = method.getParameterCount();
            if (targetMethod != null && numParams >= targetMethod.getParameterCount()) {
               if (!method.isBridge() && targetMethod.getParameterCount() == numParams) {
                  if (targetMethod.isBridge()) {
                     targetMethod = method;
                  } else {
                     ++numMethodsFoundWithCurrentMinimumArgs;
                  }
               }
            } else {
               targetMethod = method;
               numMethodsFoundWithCurrentMinimumArgs = 1;
            }
         }
      }

      if (numMethodsFoundWithCurrentMinimumArgs > 1) {
         throw new IllegalArgumentException("Cannot resolve method '" + methodName + "' to a unique method. Attempted to resolve to overloaded method with the least number of parameters but there were " + numMethodsFoundWithCurrentMinimumArgs + " candidates.");
      } else {
         return targetMethod;
      }
   }

   @Nullable
   public static Method resolveSignature(String signature, Class clazz) {
      Assert.hasText(signature, "'signature' must not be empty");
      Assert.notNull(clazz, (String)"Class must not be null");
      int startParen = signature.indexOf(40);
      int endParen = signature.indexOf(41);
      if (startParen > -1 && endParen == -1) {
         throw new IllegalArgumentException("Invalid method signature '" + signature + "': expected closing ')' for args list");
      } else if (startParen == -1 && endParen > -1) {
         throw new IllegalArgumentException("Invalid method signature '" + signature + "': expected opening '(' for args list");
      } else if (startParen == -1) {
         return findMethodWithMinimalParameters(clazz, signature);
      } else {
         String methodName = signature.substring(0, startParen);
         String[] parameterTypeNames = StringUtils.commaDelimitedListToStringArray(signature.substring(startParen + 1, endParen));
         Class[] parameterTypes = new Class[parameterTypeNames.length];

         for(int i = 0; i < parameterTypeNames.length; ++i) {
            String parameterTypeName = parameterTypeNames[i].trim();

            try {
               parameterTypes[i] = ClassUtils.forName(parameterTypeName, clazz.getClassLoader());
            } catch (Throwable var10) {
               throw new IllegalArgumentException("Invalid method signature: unable to resolve type [" + parameterTypeName + "] for argument " + i + ". Root cause: " + var10);
            }
         }

         return findMethod(clazz, methodName, parameterTypes);
      }
   }

   public static PropertyDescriptor[] getPropertyDescriptors(Class clazz) throws BeansException {
      CachedIntrospectionResults cr = CachedIntrospectionResults.forClass(clazz);
      return cr.getPropertyDescriptors();
   }

   @Nullable
   public static PropertyDescriptor getPropertyDescriptor(Class clazz, String propertyName) throws BeansException {
      CachedIntrospectionResults cr = CachedIntrospectionResults.forClass(clazz);
      return cr.getPropertyDescriptor(propertyName);
   }

   @Nullable
   public static PropertyDescriptor findPropertyForMethod(Method method) throws BeansException {
      return findPropertyForMethod(method, method.getDeclaringClass());
   }

   @Nullable
   public static PropertyDescriptor findPropertyForMethod(Method method, Class clazz) throws BeansException {
      Assert.notNull(method, (String)"Method must not be null");
      PropertyDescriptor[] pds = getPropertyDescriptors(clazz);
      PropertyDescriptor[] var3 = pds;
      int var4 = pds.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PropertyDescriptor pd = var3[var5];
         if (method.equals(pd.getReadMethod()) || method.equals(pd.getWriteMethod())) {
            return pd;
         }
      }

      return null;
   }

   @Nullable
   public static PropertyEditor findEditorByConvention(@Nullable Class targetType) {
      if (targetType != null && !targetType.isArray() && !unknownEditorTypes.contains(targetType)) {
         ClassLoader cl = targetType.getClassLoader();
         if (cl == null) {
            try {
               cl = ClassLoader.getSystemClassLoader();
               if (cl == null) {
                  return null;
               }
            } catch (Throwable var5) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Could not access system ClassLoader: " + var5);
               }

               return null;
            }
         }

         String editorName = targetType.getName() + "Editor";

         try {
            Class editorClass = cl.loadClass(editorName);
            if (!PropertyEditor.class.isAssignableFrom(editorClass)) {
               if (logger.isInfoEnabled()) {
                  logger.info("Editor class [" + editorName + "] does not implement [java.beans.PropertyEditor] interface");
               }

               unknownEditorTypes.add(targetType);
               return null;
            } else {
               return (PropertyEditor)instantiateClass(editorClass);
            }
         } catch (ClassNotFoundException var4) {
            if (logger.isTraceEnabled()) {
               logger.trace("No property editor [" + editorName + "] found for type " + targetType.getName() + " according to 'Editor' suffix convention");
            }

            unknownEditorTypes.add(targetType);
            return null;
         }
      } else {
         return null;
      }
   }

   public static Class findPropertyType(String propertyName, @Nullable Class... beanClasses) {
      if (beanClasses != null) {
         Class[] var2 = beanClasses;
         int var3 = beanClasses.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class beanClass = var2[var4];
            PropertyDescriptor pd = getPropertyDescriptor(beanClass, propertyName);
            if (pd != null) {
               return pd.getPropertyType();
            }
         }
      }

      return Object.class;
   }

   public static MethodParameter getWriteMethodParameter(PropertyDescriptor pd) {
      if (pd instanceof GenericTypeAwarePropertyDescriptor) {
         return new MethodParameter(((GenericTypeAwarePropertyDescriptor)pd).getWriteMethodParameter());
      } else {
         Method writeMethod = pd.getWriteMethod();
         Assert.state(writeMethod != null, "No write method available");
         return new MethodParameter(writeMethod, 0);
      }
   }

   public static boolean isSimpleProperty(Class type) {
      Assert.notNull(type, (String)"'type' must not be null");
      return isSimpleValueType(type) || type.isArray() && isSimpleValueType(type.getComponentType());
   }

   public static boolean isSimpleValueType(Class type) {
      return type != Void.TYPE && type != Void.class && (ClassUtils.isPrimitiveOrWrapper(type) || Enum.class.isAssignableFrom(type) || CharSequence.class.isAssignableFrom(type) || Number.class.isAssignableFrom(type) || Date.class.isAssignableFrom(type) || URI.class == type || URL.class == type || Locale.class == type || Class.class == type);
   }

   public static void copyProperties(Object source, Object target) throws BeansException {
      copyProperties(source, target, (Class)null, (String[])null);
   }

   public static void copyProperties(Object source, Object target, Class editable) throws BeansException {
      copyProperties(source, target, editable, (String[])null);
   }

   public static void copyProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
      copyProperties(source, target, (Class)null, ignoreProperties);
   }

   private static void copyProperties(Object source, Object target, @Nullable Class editable, @Nullable String... ignoreProperties) throws BeansException {
      Assert.notNull(source, "Source must not be null");
      Assert.notNull(target, "Target must not be null");
      Class actualEditable = target.getClass();
      if (editable != null) {
         if (!editable.isInstance(target)) {
            throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
         }

         actualEditable = editable;
      }

      PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
      List ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
      PropertyDescriptor[] var7 = targetPds;
      int var8 = targetPds.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         PropertyDescriptor targetPd = var7[var9];
         Method writeMethod = targetPd.getWriteMethod();
         if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
            PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
            if (sourcePd != null) {
               Method readMethod = sourcePd.getReadMethod();
               if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                  try {
                     if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                     }

                     Object value = readMethod.invoke(source);
                     if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                        writeMethod.setAccessible(true);
                     }

                     writeMethod.invoke(target, value);
                  } catch (Throwable var15) {
                     throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", var15);
                  }
               }
            }
         }
      }

   }

   private static class KotlinDelegate {
      @Nullable
      public static Constructor findPrimaryConstructor(Class clazz) {
         try {
            KFunction primaryCtor = KClasses.getPrimaryConstructor(JvmClassMappingKt.getKotlinClass(clazz));
            if (primaryCtor == null) {
               return null;
            } else {
               Constructor constructor = ReflectJvmMapping.getJavaConstructor(primaryCtor);
               if (constructor == null) {
                  throw new IllegalStateException("Failed to find Java constructor for Kotlin primary constructor: " + clazz.getName());
               } else {
                  return constructor;
               }
            }
         } catch (UnsupportedOperationException var3) {
            return null;
         }
      }

      public static Object instantiateClass(Constructor ctor, Object... args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
         KFunction kotlinConstructor = ReflectJvmMapping.getKotlinFunction(ctor);
         if (kotlinConstructor == null) {
            return ctor.newInstance(args);
         } else {
            List parameters = kotlinConstructor.getParameters();
            Map argParameters = new HashMap(parameters.size());
            Assert.isTrue(args.length <= parameters.size(), "Number of provided arguments should be less of equals than number of constructor parameters");

            for(int i = 0; i < args.length; ++i) {
               if (!((KParameter)parameters.get(i)).isOptional() || args[i] != null) {
                  argParameters.put(parameters.get(i), args[i]);
               }
            }

            return kotlinConstructor.callBy(argParameters);
         }
      }
   }
}
