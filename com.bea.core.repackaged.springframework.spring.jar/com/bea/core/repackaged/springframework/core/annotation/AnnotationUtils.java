package com.bea.core.repackaged.springframework.core.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.BridgeMethodResolver;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AnnotationUtils {
   public static final String VALUE = "value";
   private static final Map findAnnotationCache = new ConcurrentReferenceHashMap(256);
   private static final Map metaPresentCache = new ConcurrentReferenceHashMap(256);
   private static final Map declaredAnnotationsCache = new ConcurrentReferenceHashMap(256);
   private static final Map annotatedBaseTypeCache = new ConcurrentReferenceHashMap(256);
   /** @deprecated */
   @Deprecated
   private static final Map annotatedInterfaceCache;
   private static final Map synthesizableCache;
   private static final Map attributeAliasesCache;
   private static final Map attributeMethodsCache;
   private static final Map aliasDescriptorCache;
   @Nullable
   private static transient Log logger;

   @Nullable
   public static Annotation getAnnotation(Annotation annotation, Class annotationType) {
      if (annotationType.isInstance(annotation)) {
         return synthesizeAnnotation(annotation);
      } else {
         Class annotatedElement = annotation.annotationType();

         try {
            Annotation metaAnn = annotatedElement.getAnnotation(annotationType);
            return metaAnn != null ? synthesizeAnnotation(metaAnn, (AnnotatedElement)annotatedElement) : null;
         } catch (Throwable var4) {
            handleIntrospectionFailure(annotatedElement, var4);
            return null;
         }
      }
   }

   @Nullable
   public static Annotation getAnnotation(AnnotatedElement annotatedElement, Class annotationType) {
      try {
         Annotation annotation = annotatedElement.getAnnotation(annotationType);
         if (annotation == null) {
            Annotation[] var3 = annotatedElement.getAnnotations();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Annotation metaAnn = var3[var5];
               annotation = metaAnn.annotationType().getAnnotation(annotationType);
               if (annotation != null) {
                  break;
               }
            }
         }

         return annotation != null ? synthesizeAnnotation(annotation, annotatedElement) : null;
      } catch (Throwable var7) {
         handleIntrospectionFailure(annotatedElement, var7);
         return null;
      }
   }

   @Nullable
   public static Annotation getAnnotation(Method method, Class annotationType) {
      Method resolvedMethod = BridgeMethodResolver.findBridgedMethod(method);
      return getAnnotation((AnnotatedElement)resolvedMethod, (Class)annotationType);
   }

   @Nullable
   public static Annotation[] getAnnotations(AnnotatedElement annotatedElement) {
      try {
         return synthesizeAnnotationArray((Annotation[])annotatedElement.getAnnotations(), (Object)annotatedElement);
      } catch (Throwable var2) {
         handleIntrospectionFailure(annotatedElement, var2);
         return null;
      }
   }

   @Nullable
   public static Annotation[] getAnnotations(Method method) {
      try {
         return synthesizeAnnotationArray((Annotation[])BridgeMethodResolver.findBridgedMethod(method).getAnnotations(), (Object)method);
      } catch (Throwable var2) {
         handleIntrospectionFailure(method, var2);
         return null;
      }
   }

   public static Set getRepeatableAnnotations(AnnotatedElement annotatedElement, Class annotationType) {
      return getRepeatableAnnotations(annotatedElement, annotationType, (Class)null);
   }

   public static Set getRepeatableAnnotations(AnnotatedElement annotatedElement, Class annotationType, @Nullable Class containerAnnotationType) {
      Set annotations = getDeclaredRepeatableAnnotations(annotatedElement, annotationType, containerAnnotationType);
      if (annotations.isEmpty() && annotatedElement instanceof Class) {
         Class superclass = ((Class)annotatedElement).getSuperclass();
         if (superclass != null && superclass != Object.class) {
            return getRepeatableAnnotations(superclass, annotationType, containerAnnotationType);
         }
      }

      return annotations;
   }

   public static Set getDeclaredRepeatableAnnotations(AnnotatedElement annotatedElement, Class annotationType) {
      return getDeclaredRepeatableAnnotations(annotatedElement, annotationType, (Class)null);
   }

   public static Set getDeclaredRepeatableAnnotations(AnnotatedElement annotatedElement, Class annotationType, @Nullable Class containerAnnotationType) {
      try {
         if (annotatedElement instanceof Method) {
            annotatedElement = BridgeMethodResolver.findBridgedMethod((Method)annotatedElement);
         }

         return (new AnnotationCollector(annotationType, containerAnnotationType)).getResult((AnnotatedElement)annotatedElement);
      } catch (Throwable var4) {
         handleIntrospectionFailure((AnnotatedElement)annotatedElement, var4);
         return Collections.emptySet();
      }
   }

   @Nullable
   public static Annotation findAnnotation(AnnotatedElement annotatedElement, @Nullable Class annotationType) {
      if (annotationType == null) {
         return null;
      } else {
         Annotation ann = findAnnotation((AnnotatedElement)annotatedElement, annotationType, new HashSet());
         return ann != null ? synthesizeAnnotation(ann, annotatedElement) : null;
      }
   }

   @Nullable
   private static Annotation findAnnotation(AnnotatedElement annotatedElement, Class annotationType, Set visited) {
      try {
         Annotation annotation = annotatedElement.getDeclaredAnnotation(annotationType);
         if (annotation != null) {
            return annotation;
         }

         Annotation[] var4 = getDeclaredAnnotations(annotatedElement);
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Annotation declaredAnn = var4[var6];
            Class declaredType = declaredAnn.annotationType();
            if (!isInJavaLangAnnotationPackage(declaredType) && visited.add(declaredAnn)) {
               annotation = findAnnotation((AnnotatedElement)declaredType, annotationType, visited);
               if (annotation != null) {
                  return annotation;
               }
            }
         }
      } catch (Throwable var9) {
         handleIntrospectionFailure(annotatedElement, var9);
      }

      return null;
   }

   @Nullable
   public static Annotation findAnnotation(Method method, @Nullable Class annotationType) {
      Assert.notNull(method, (String)"Method must not be null");
      if (annotationType == null) {
         return null;
      } else {
         AnnotationCacheKey cacheKey = new AnnotationCacheKey(method, annotationType);
         Annotation result = (Annotation)findAnnotationCache.get(cacheKey);
         if (result == null) {
            Method resolvedMethod = BridgeMethodResolver.findBridgedMethod(method);
            result = findAnnotation((AnnotatedElement)resolvedMethod, annotationType);
            if (result == null) {
               result = searchOnInterfaces(method, annotationType, method.getDeclaringClass().getInterfaces());
            }

            Class clazz = method.getDeclaringClass();

            while(result == null) {
               clazz = clazz.getSuperclass();
               if (clazz == null || clazz == Object.class) {
                  break;
               }

               Set annotatedMethods = getAnnotatedMethodsInBaseType(clazz);
               if (!annotatedMethods.isEmpty()) {
                  Iterator var7 = annotatedMethods.iterator();

                  while(var7.hasNext()) {
                     Method annotatedMethod = (Method)var7.next();
                     if (isOverride(method, annotatedMethod)) {
                        Method resolvedSuperMethod = BridgeMethodResolver.findBridgedMethod(annotatedMethod);
                        result = findAnnotation((AnnotatedElement)resolvedSuperMethod, annotationType);
                        if (result != null) {
                           break;
                        }
                     }
                  }
               }

               if (result == null) {
                  result = searchOnInterfaces(method, annotationType, clazz.getInterfaces());
               }
            }

            if (result != null) {
               result = synthesizeAnnotation(result, (AnnotatedElement)method);
               findAnnotationCache.put(cacheKey, result);
            }
         }

         return result;
      }
   }

   @Nullable
   private static Annotation searchOnInterfaces(Method method, Class annotationType, Class... ifcs) {
      Class[] var3 = ifcs;
      int var4 = ifcs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class ifc = var3[var5];
         Set annotatedMethods = getAnnotatedMethodsInBaseType(ifc);
         if (!annotatedMethods.isEmpty()) {
            Iterator var8 = annotatedMethods.iterator();

            while(var8.hasNext()) {
               Method annotatedMethod = (Method)var8.next();
               if (isOverride(method, annotatedMethod)) {
                  Annotation annotation = getAnnotation(annotatedMethod, annotationType);
                  if (annotation != null) {
                     return annotation;
                  }
               }
            }
         }
      }

      return null;
   }

   static boolean isOverride(Method method, Method candidate) {
      if (candidate.getName().equals(method.getName()) && candidate.getParameterCount() == method.getParameterCount()) {
         Class[] paramTypes = method.getParameterTypes();
         if (Arrays.equals(candidate.getParameterTypes(), paramTypes)) {
            return true;
         } else {
            for(int i = 0; i < paramTypes.length; ++i) {
               if (paramTypes[i] != ResolvableType.forMethodParameter(candidate, i, method.getDeclaringClass()).resolve()) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   static Set getAnnotatedMethodsInBaseType(Class baseType) {
      boolean ifcCheck = baseType.isInterface();
      if (ifcCheck && ClassUtils.isJavaLanguageInterface(baseType)) {
         return Collections.emptySet();
      } else {
         Set annotatedMethods = (Set)annotatedBaseTypeCache.get(baseType);
         if (annotatedMethods != null) {
            return (Set)annotatedMethods;
         } else {
            Method[] methods = ifcCheck ? baseType.getMethods() : baseType.getDeclaredMethods();
            Method[] var4 = methods;
            int var5 = methods.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Method baseMethod = var4[var6];

               try {
                  if ((ifcCheck || !Modifier.isPrivate(baseMethod.getModifiers())) && hasSearchableAnnotations(baseMethod)) {
                     if (annotatedMethods == null) {
                        annotatedMethods = new HashSet();
                     }

                     ((Set)annotatedMethods).add(baseMethod);
                  }
               } catch (Throwable var9) {
                  handleIntrospectionFailure(baseMethod, var9);
               }
            }

            if (annotatedMethods == null) {
               annotatedMethods = Collections.emptySet();
            }

            annotatedBaseTypeCache.put(baseType, annotatedMethods);
            return (Set)annotatedMethods;
         }
      }
   }

   private static boolean hasSearchableAnnotations(Method ifcMethod) {
      Annotation[] anns = getDeclaredAnnotations(ifcMethod);
      if (anns.length == 0) {
         return false;
      } else {
         Annotation[] var2 = anns;
         int var3 = anns.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Annotation ann = var2[var4];
            String name = ann.annotationType().getName();
            if (!name.startsWith("java.lang.") && !name.startsWith("com.bea.core.repackaged.springframework.lang.")) {
               return true;
            }
         }

         return false;
      }
   }

   static Annotation[] getDeclaredAnnotations(AnnotatedElement element) {
      return !(element instanceof Class) && !(element instanceof Member) ? element.getDeclaredAnnotations() : (Annotation[])declaredAnnotationsCache.computeIfAbsent(element, AnnotatedElement::getDeclaredAnnotations);
   }

   @Nullable
   public static Annotation findAnnotation(Class clazz, @Nullable Class annotationType) {
      return findAnnotation(clazz, annotationType, true);
   }

   @Nullable
   private static Annotation findAnnotation(Class clazz, @Nullable Class annotationType, boolean synthesize) {
      Assert.notNull(clazz, (String)"Class must not be null");
      if (annotationType == null) {
         return null;
      } else {
         AnnotationCacheKey cacheKey = new AnnotationCacheKey(clazz, annotationType);
         Annotation result = (Annotation)findAnnotationCache.get(cacheKey);
         if (result == null) {
            result = findAnnotation((Class)clazz, annotationType, new HashSet());
            if (result != null && synthesize) {
               result = synthesizeAnnotation(result, (AnnotatedElement)clazz);
               findAnnotationCache.put(cacheKey, result);
            }
         }

         return result;
      }
   }

   @Nullable
   private static Annotation findAnnotation(Class clazz, Class annotationType, Set visited) {
      int var5;
      Annotation annotation;
      try {
         Annotation annotation = clazz.getDeclaredAnnotation(annotationType);
         if (annotation != null) {
            return annotation;
         }

         Annotation[] var4 = getDeclaredAnnotations(clazz);
         var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            annotation = var4[var6];
            Class declaredType = annotation.annotationType();
            if (!isInJavaLangAnnotationPackage(declaredType) && visited.add(annotation)) {
               annotation = findAnnotation(declaredType, annotationType, visited);
               if (annotation != null) {
                  return annotation;
               }
            }
         }
      } catch (Throwable var9) {
         handleIntrospectionFailure(clazz, var9);
         return null;
      }

      Class[] var10 = clazz.getInterfaces();
      int var12 = var10.length;

      for(var5 = 0; var5 < var12; ++var5) {
         Class ifc = var10[var5];
         annotation = findAnnotation(ifc, annotationType, visited);
         if (annotation != null) {
            return annotation;
         }
      }

      Class superclass = clazz.getSuperclass();
      if (superclass != null && superclass != Object.class) {
         return findAnnotation(superclass, annotationType, visited);
      } else {
         return null;
      }
   }

   @Nullable
   public static Class findAnnotationDeclaringClass(Class annotationType, @Nullable Class clazz) {
      if (clazz != null && clazz != Object.class) {
         return isAnnotationDeclaredLocally(annotationType, clazz) ? clazz : findAnnotationDeclaringClass(annotationType, clazz.getSuperclass());
      } else {
         return null;
      }
   }

   @Nullable
   public static Class findAnnotationDeclaringClassForTypes(List annotationTypes, @Nullable Class clazz) {
      if (clazz != null && clazz != Object.class) {
         Iterator var2 = annotationTypes.iterator();

         Class annotationType;
         do {
            if (!var2.hasNext()) {
               return findAnnotationDeclaringClassForTypes(annotationTypes, clazz.getSuperclass());
            }

            annotationType = (Class)var2.next();
         } while(!isAnnotationDeclaredLocally(annotationType, clazz));

         return clazz;
      } else {
         return null;
      }
   }

   public static boolean isAnnotationDeclaredLocally(Class annotationType, Class clazz) {
      try {
         return clazz.getDeclaredAnnotation(annotationType) != null;
      } catch (Throwable var3) {
         handleIntrospectionFailure(clazz, var3);
         return false;
      }
   }

   public static boolean isAnnotationInherited(Class annotationType, Class clazz) {
      return clazz.isAnnotationPresent(annotationType) && !isAnnotationDeclaredLocally(annotationType, clazz);
   }

   public static boolean isAnnotationMetaPresent(Class annotationType, @Nullable Class metaAnnotationType) {
      Assert.notNull(annotationType, (String)"Annotation type must not be null");
      if (metaAnnotationType == null) {
         return false;
      } else {
         AnnotationCacheKey cacheKey = new AnnotationCacheKey(annotationType, metaAnnotationType);
         Boolean metaPresent = (Boolean)metaPresentCache.get(cacheKey);
         if (metaPresent != null) {
            return metaPresent;
         } else {
            metaPresent = Boolean.FALSE;
            if (findAnnotation(annotationType, metaAnnotationType, false) != null) {
               metaPresent = Boolean.TRUE;
            }

            metaPresentCache.put(cacheKey, metaPresent);
            return metaPresent;
         }
      }
   }

   static boolean hasPlainJavaAnnotationsOnly(@Nullable Object annotatedElement) {
      Class clazz;
      if (annotatedElement instanceof Class) {
         clazz = (Class)annotatedElement;
      } else {
         if (!(annotatedElement instanceof Member)) {
            return false;
         }

         clazz = ((Member)annotatedElement).getDeclaringClass();
      }

      String name = clazz.getName();
      return name.startsWith("java.") || name.startsWith("com.bea.core.repackaged.springframework.lang.");
   }

   public static boolean isInJavaLangAnnotationPackage(@Nullable Annotation annotation) {
      return annotation != null && isInJavaLangAnnotationPackage(annotation.annotationType());
   }

   static boolean isInJavaLangAnnotationPackage(@Nullable Class annotationType) {
      return annotationType != null && isInJavaLangAnnotationPackage(annotationType.getName());
   }

   public static boolean isInJavaLangAnnotationPackage(@Nullable String annotationType) {
      return annotationType != null && annotationType.startsWith("java.lang.annotation");
   }

   public static void validateAnnotation(Annotation annotation) {
      Iterator var1 = getAttributeMethods(annotation.annotationType()).iterator();

      while(true) {
         Method method;
         Class returnType;
         do {
            if (!var1.hasNext()) {
               return;
            }

            method = (Method)var1.next();
            returnType = method.getReturnType();
         } while(returnType != Class.class && returnType != Class[].class);

         try {
            method.invoke(annotation);
         } catch (Throwable var5) {
            throw new IllegalStateException("Could not obtain annotation attribute value for " + method, var5);
         }
      }
   }

   public static Map getAnnotationAttributes(Annotation annotation) {
      return getAnnotationAttributes((AnnotatedElement)null, annotation);
   }

   public static Map getAnnotationAttributes(Annotation annotation, boolean classValuesAsString) {
      return getAnnotationAttributes(annotation, classValuesAsString, false);
   }

   public static AnnotationAttributes getAnnotationAttributes(Annotation annotation, boolean classValuesAsString, boolean nestedAnnotationsAsMap) {
      return getAnnotationAttributes((AnnotatedElement)null, annotation, classValuesAsString, nestedAnnotationsAsMap);
   }

   public static AnnotationAttributes getAnnotationAttributes(@Nullable AnnotatedElement annotatedElement, Annotation annotation) {
      return getAnnotationAttributes(annotatedElement, annotation, false, false);
   }

   public static AnnotationAttributes getAnnotationAttributes(@Nullable AnnotatedElement annotatedElement, Annotation annotation, boolean classValuesAsString, boolean nestedAnnotationsAsMap) {
      return getAnnotationAttributes((Object)annotatedElement, annotation, classValuesAsString, nestedAnnotationsAsMap);
   }

   private static AnnotationAttributes getAnnotationAttributes(@Nullable Object annotatedElement, Annotation annotation, boolean classValuesAsString, boolean nestedAnnotationsAsMap) {
      AnnotationAttributes attributes = retrieveAnnotationAttributes(annotatedElement, annotation, classValuesAsString, nestedAnnotationsAsMap);
      postProcessAnnotationAttributes(annotatedElement, attributes, classValuesAsString, nestedAnnotationsAsMap);
      return attributes;
   }

   static AnnotationAttributes retrieveAnnotationAttributes(@Nullable Object annotatedElement, Annotation annotation, boolean classValuesAsString, boolean nestedAnnotationsAsMap) {
      Class annotationType = annotation.annotationType();
      AnnotationAttributes attributes = new AnnotationAttributes(annotationType);
      Iterator var6 = getAttributeMethods(annotationType).iterator();

      while(var6.hasNext()) {
         Method method = (Method)var6.next();

         try {
            Object attributeValue = method.invoke(annotation);
            Object defaultValue = method.getDefaultValue();
            if (defaultValue != null && ObjectUtils.nullSafeEquals(attributeValue, defaultValue)) {
               attributeValue = new DefaultValueHolder(defaultValue);
            }

            attributes.put(method.getName(), adaptValue(annotatedElement, attributeValue, classValuesAsString, nestedAnnotationsAsMap));
         } catch (Throwable var10) {
            if (var10 instanceof InvocationTargetException) {
               Throwable targetException = ((InvocationTargetException)var10).getTargetException();
               rethrowAnnotationConfigurationException(targetException);
            }

            throw new IllegalStateException("Could not obtain annotation attribute value for " + method, var10);
         }
      }

      return attributes;
   }

   @Nullable
   static Object adaptValue(@Nullable Object annotatedElement, @Nullable Object value, boolean classValuesAsString, boolean nestedAnnotationsAsMap) {
      int i;
      if (classValuesAsString) {
         if (value instanceof Class) {
            return ((Class)value).getName();
         }

         if (value instanceof Class[]) {
            Class[] clazzArray = (Class[])((Class[])value);
            String[] classNames = new String[clazzArray.length];

            for(i = 0; i < clazzArray.length; ++i) {
               classNames[i] = clazzArray[i].getName();
            }

            return classNames;
         }
      }

      if (value instanceof Annotation) {
         Annotation annotation = (Annotation)value;
         return nestedAnnotationsAsMap ? getAnnotationAttributes(annotatedElement, annotation, classValuesAsString, true) : synthesizeAnnotation(annotation, annotatedElement);
      } else if (!(value instanceof Annotation[])) {
         return value;
      } else {
         Annotation[] annotations = (Annotation[])((Annotation[])value);
         if (!nestedAnnotationsAsMap) {
            return synthesizeAnnotationArray(annotations, annotatedElement);
         } else {
            AnnotationAttributes[] mappedAnnotations = new AnnotationAttributes[annotations.length];

            for(i = 0; i < annotations.length; ++i) {
               mappedAnnotations[i] = getAnnotationAttributes(annotatedElement, annotations[i], classValuesAsString, true);
            }

            return mappedAnnotations;
         }
      }
   }

   public static void registerDefaultValues(AnnotationAttributes attributes) {
      Class annotationType = attributes.annotationType();
      if (annotationType != null && Modifier.isPublic(annotationType.getModifiers())) {
         Iterator var2 = getAttributeMethods(annotationType).iterator();

         while(true) {
            String attributeName;
            Object defaultValue;
            do {
               do {
                  if (!var2.hasNext()) {
                     return;
                  }

                  Method annotationAttribute = (Method)var2.next();
                  attributeName = annotationAttribute.getName();
                  defaultValue = annotationAttribute.getDefaultValue();
               } while(defaultValue == null);
            } while(attributes.containsKey(attributeName));

            if (defaultValue instanceof Annotation) {
               defaultValue = getAnnotationAttributes((Annotation)defaultValue, false, true);
            } else if (defaultValue instanceof Annotation[]) {
               Annotation[] realAnnotations = (Annotation[])((Annotation[])defaultValue);
               AnnotationAttributes[] mappedAnnotations = new AnnotationAttributes[realAnnotations.length];

               for(int i = 0; i < realAnnotations.length; ++i) {
                  mappedAnnotations[i] = getAnnotationAttributes(realAnnotations[i], false, true);
               }

               defaultValue = mappedAnnotations;
            }

            attributes.put(attributeName, new DefaultValueHolder(defaultValue));
         }
      }
   }

   public static void postProcessAnnotationAttributes(@Nullable Object annotatedElement, AnnotationAttributes attributes, boolean classValuesAsString) {
      postProcessAnnotationAttributes(annotatedElement, attributes, classValuesAsString, false);
   }

   static void postProcessAnnotationAttributes(@Nullable Object annotatedElement, @Nullable AnnotationAttributes attributes, boolean classValuesAsString, boolean nestedAnnotationsAsMap) {
      if (attributes != null) {
         Class annotationType = attributes.annotationType();
         Set valuesAlreadyReplaced = new HashSet();
         if (!attributes.validated) {
            Map aliasMap = getAttributeAliasMap(annotationType);
            aliasMap.forEach((attributeNamex, aliasedAttributeNames) -> {
               if (!valuesAlreadyReplaced.contains(attributeNamex)) {
                  Object value = attributes.get(attributeNamex);
                  boolean valuePresent = value != null && !(value instanceof DefaultValueHolder);
                  Iterator var9 = aliasedAttributeNames.iterator();

                  String aliasedAttributeName;
                  Object aliasedValue;
                  do {
                     while(true) {
                        boolean aliasPresent;
                        do {
                           do {
                              if (!var9.hasNext()) {
                                 return;
                              }

                              aliasedAttributeName = (String)var9.next();
                           } while(valuesAlreadyReplaced.contains(aliasedAttributeName));

                           aliasedValue = attributes.get(aliasedAttributeName);
                           aliasPresent = aliasedValue != null && !(aliasedValue instanceof DefaultValueHolder);
                        } while(!valuePresent && !aliasPresent);

                        if (valuePresent && aliasPresent) {
                           break;
                        }

                        if (aliasPresent) {
                           attributes.put(attributeNamex, adaptValue(annotatedElement, aliasedValue, classValuesAsString, nestedAnnotationsAsMap));
                           valuesAlreadyReplaced.add(attributeNamex);
                        } else {
                           attributes.put(aliasedAttributeName, adaptValue(annotatedElement, value, classValuesAsString, nestedAnnotationsAsMap));
                           valuesAlreadyReplaced.add(aliasedAttributeName);
                        }
                     }
                  } while(ObjectUtils.nullSafeEquals(value, aliasedValue));

                  String elementAsString = annotatedElement != null ? annotatedElement.toString() : "unknown element";
                  throw new AnnotationConfigurationException(String.format("In AnnotationAttributes for annotation [%s] declared on %s, attribute '%s' and its alias '%s' are declared with values of [%s] and [%s], but only one is permitted.", attributes.displayName, elementAsString, attributeNamex, aliasedAttributeName, ObjectUtils.nullSafeToString(value), ObjectUtils.nullSafeToString(aliasedValue)));
               }
            });
            attributes.validated = true;
         }

         Iterator var10 = attributes.entrySet().iterator();

         while(var10.hasNext()) {
            Map.Entry attributeEntry = (Map.Entry)var10.next();
            String attributeName = (String)attributeEntry.getKey();
            if (!valuesAlreadyReplaced.contains(attributeName)) {
               Object value = attributeEntry.getValue();
               if (value instanceof DefaultValueHolder) {
                  value = ((DefaultValueHolder)value).defaultValue;
                  attributes.put(attributeName, adaptValue(annotatedElement, value, classValuesAsString, nestedAnnotationsAsMap));
               }
            }
         }

      }
   }

   @Nullable
   public static Object getValue(Annotation annotation) {
      return getValue(annotation, "value");
   }

   @Nullable
   public static Object getValue(@Nullable Annotation annotation, @Nullable String attributeName) {
      if (annotation != null && StringUtils.hasText(attributeName)) {
         try {
            Method method = annotation.annotationType().getDeclaredMethod(attributeName);
            ReflectionUtils.makeAccessible(method);
            return method.invoke(annotation);
         } catch (NoSuchMethodException var3) {
            return null;
         } catch (InvocationTargetException var4) {
            rethrowAnnotationConfigurationException(var4.getTargetException());
            throw new IllegalStateException("Could not obtain value for annotation attribute '" + attributeName + "' in " + annotation, var4);
         } catch (Throwable var5) {
            handleIntrospectionFailure(annotation.getClass(), var5);
            return null;
         }
      } else {
         return null;
      }
   }

   @Nullable
   public static Object getDefaultValue(Annotation annotation) {
      return getDefaultValue(annotation, "value");
   }

   @Nullable
   public static Object getDefaultValue(@Nullable Annotation annotation, @Nullable String attributeName) {
      return annotation != null ? getDefaultValue(annotation.annotationType(), attributeName) : null;
   }

   @Nullable
   public static Object getDefaultValue(Class annotationType) {
      return getDefaultValue(annotationType, "value");
   }

   @Nullable
   public static Object getDefaultValue(@Nullable Class annotationType, @Nullable String attributeName) {
      if (annotationType != null && StringUtils.hasText(attributeName)) {
         try {
            return annotationType.getDeclaredMethod(attributeName).getDefaultValue();
         } catch (Throwable var3) {
            handleIntrospectionFailure(annotationType, var3);
            return null;
         }
      } else {
         return null;
      }
   }

   static Annotation synthesizeAnnotation(Annotation annotation) {
      return synthesizeAnnotation(annotation, (AnnotatedElement)null);
   }

   public static Annotation synthesizeAnnotation(Annotation annotation, @Nullable AnnotatedElement annotatedElement) {
      return synthesizeAnnotation(annotation, (Object)annotatedElement);
   }

   static Annotation synthesizeAnnotation(Annotation annotation, @Nullable Object annotatedElement) {
      if (!(annotation instanceof SynthesizedAnnotation) && !hasPlainJavaAnnotationsOnly(annotatedElement)) {
         Class annotationType = annotation.annotationType();
         if (!isSynthesizable(annotationType)) {
            return annotation;
         } else {
            DefaultAnnotationAttributeExtractor attributeExtractor = new DefaultAnnotationAttributeExtractor(annotation, annotatedElement);
            InvocationHandler handler = new SynthesizedAnnotationInvocationHandler(attributeExtractor);
            Class[] exposedInterfaces = new Class[]{annotationType, SynthesizedAnnotation.class};
            return (Annotation)Proxy.newProxyInstance(annotation.getClass().getClassLoader(), exposedInterfaces, handler);
         }
      } else {
         return annotation;
      }
   }

   public static Annotation synthesizeAnnotation(Map attributes, Class annotationType, @Nullable AnnotatedElement annotatedElement) {
      MapAnnotationAttributeExtractor attributeExtractor = new MapAnnotationAttributeExtractor(attributes, annotationType, annotatedElement);
      InvocationHandler handler = new SynthesizedAnnotationInvocationHandler(attributeExtractor);
      Class[] exposedInterfaces = canExposeSynthesizedMarker(annotationType) ? new Class[]{annotationType, SynthesizedAnnotation.class} : new Class[]{annotationType};
      return (Annotation)Proxy.newProxyInstance(annotationType.getClassLoader(), exposedInterfaces, handler);
   }

   public static Annotation synthesizeAnnotation(Class annotationType) {
      return synthesizeAnnotation(Collections.emptyMap(), annotationType, (AnnotatedElement)null);
   }

   static Annotation[] synthesizeAnnotationArray(Annotation[] annotations, @Nullable Object annotatedElement) {
      if (hasPlainJavaAnnotationsOnly(annotatedElement)) {
         return annotations;
      } else {
         Annotation[] synthesized = (Annotation[])((Annotation[])Array.newInstance(annotations.getClass().getComponentType(), annotations.length));

         for(int i = 0; i < annotations.length; ++i) {
            synthesized[i] = synthesizeAnnotation(annotations[i], annotatedElement);
         }

         return synthesized;
      }
   }

   @Nullable
   static Annotation[] synthesizeAnnotationArray(@Nullable Map[] maps, Class annotationType) {
      if (maps == null) {
         return null;
      } else {
         Annotation[] synthesized = (Annotation[])((Annotation[])Array.newInstance(annotationType, maps.length));

         for(int i = 0; i < maps.length; ++i) {
            synthesized[i] = synthesizeAnnotation(maps[i], annotationType, (AnnotatedElement)null);
         }

         return synthesized;
      }
   }

   static Map getAttributeAliasMap(@Nullable Class annotationType) {
      if (annotationType == null) {
         return Collections.emptyMap();
      } else {
         Map map = (Map)attributeAliasesCache.get(annotationType);
         if (map != null) {
            return map;
         } else {
            Map map = new LinkedHashMap();
            Iterator var2 = getAttributeMethods(annotationType).iterator();

            while(var2.hasNext()) {
               Method attribute = (Method)var2.next();
               List aliasNames = getAttributeAliasNames(attribute);
               if (!aliasNames.isEmpty()) {
                  map.put(attribute.getName(), aliasNames);
               }
            }

            attributeAliasesCache.put(annotationType, map);
            return map;
         }
      }
   }

   private static boolean canExposeSynthesizedMarker(Class annotationType) {
      try {
         return Class.forName(SynthesizedAnnotation.class.getName(), false, annotationType.getClassLoader()) == SynthesizedAnnotation.class;
      } catch (ClassNotFoundException var2) {
         return false;
      }
   }

   private static boolean isSynthesizable(Class annotationType) {
      if (hasPlainJavaAnnotationsOnly(annotationType)) {
         return false;
      } else {
         Boolean synthesizable = (Boolean)synthesizableCache.get(annotationType);
         if (synthesizable != null) {
            return synthesizable;
         } else {
            synthesizable = Boolean.FALSE;
            Iterator var2 = getAttributeMethods(annotationType).iterator();

            while(var2.hasNext()) {
               Method attribute = (Method)var2.next();
               if (!getAttributeAliasNames(attribute).isEmpty()) {
                  synthesizable = Boolean.TRUE;
                  break;
               }

               Class returnType = attribute.getReturnType();
               if (Annotation[].class.isAssignableFrom(returnType)) {
                  Class nestedAnnotationType = returnType.getComponentType();
                  if (isSynthesizable(nestedAnnotationType)) {
                     synthesizable = Boolean.TRUE;
                     break;
                  }
               } else if (Annotation.class.isAssignableFrom(returnType) && isSynthesizable(returnType)) {
                  synthesizable = Boolean.TRUE;
                  break;
               }
            }

            synthesizableCache.put(annotationType, synthesizable);
            return synthesizable;
         }
      }
   }

   static List getAttributeAliasNames(Method attribute) {
      AliasDescriptor descriptor = AnnotationUtils.AliasDescriptor.from(attribute);
      return descriptor != null ? descriptor.getAttributeAliasNames() : Collections.emptyList();
   }

   @Nullable
   static String getAttributeOverrideName(Method attribute, @Nullable Class metaAnnotationType) {
      AliasDescriptor descriptor = AnnotationUtils.AliasDescriptor.from(attribute);
      return descriptor != null && metaAnnotationType != null ? descriptor.getAttributeOverrideName(metaAnnotationType) : null;
   }

   static List getAttributeMethods(Class annotationType) {
      List methods = (List)attributeMethodsCache.get(annotationType);
      if (methods != null) {
         return methods;
      } else {
         List methods = new ArrayList();
         Method[] var2 = annotationType.getDeclaredMethods();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method method = var2[var4];
            if (isAttributeMethod(method)) {
               ReflectionUtils.makeAccessible(method);
               methods.add(method);
            }
         }

         attributeMethodsCache.put(annotationType, methods);
         return methods;
      }
   }

   @Nullable
   static Annotation getAnnotation(AnnotatedElement element, String annotationName) {
      Annotation[] var2 = element.getAnnotations();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Annotation annotation = var2[var4];
         if (annotation.annotationType().getName().equals(annotationName)) {
            return annotation;
         }
      }

      return null;
   }

   static boolean isAttributeMethod(@Nullable Method method) {
      return method != null && method.getParameterCount() == 0 && method.getReturnType() != Void.TYPE;
   }

   static boolean isAnnotationTypeMethod(@Nullable Method method) {
      return method != null && method.getName().equals("annotationType") && method.getParameterCount() == 0;
   }

   @Nullable
   static Class resolveContainerAnnotationType(Class annotationType) {
      Repeatable repeatable = (Repeatable)getAnnotation((AnnotatedElement)annotationType, (Class)Repeatable.class);
      return repeatable != null ? repeatable.value() : null;
   }

   static void rethrowAnnotationConfigurationException(Throwable ex) {
      if (ex instanceof AnnotationConfigurationException) {
         throw (AnnotationConfigurationException)ex;
      }
   }

   static void handleIntrospectionFailure(@Nullable AnnotatedElement element, Throwable ex) {
      rethrowAnnotationConfigurationException(ex);
      Log loggerToUse = logger;
      if (loggerToUse == null) {
         loggerToUse = LogFactory.getLog(AnnotationUtils.class);
         logger = loggerToUse;
      }

      if (element instanceof Class && Annotation.class.isAssignableFrom((Class)element)) {
         if (loggerToUse.isDebugEnabled()) {
            loggerToUse.debug("Failed to meta-introspect annotation " + element + ": " + ex);
         }
      } else if (loggerToUse.isInfoEnabled()) {
         loggerToUse.info("Failed to introspect annotations on " + element + ": " + ex);
      }

   }

   public static void clearCache() {
      findAnnotationCache.clear();
      metaPresentCache.clear();
      declaredAnnotationsCache.clear();
      annotatedBaseTypeCache.clear();
      synthesizableCache.clear();
      attributeAliasesCache.clear();
      attributeMethodsCache.clear();
      aliasDescriptorCache.clear();
   }

   static {
      annotatedInterfaceCache = annotatedBaseTypeCache;
      synthesizableCache = new ConcurrentReferenceHashMap(256);
      attributeAliasesCache = new ConcurrentReferenceHashMap(256);
      attributeMethodsCache = new ConcurrentReferenceHashMap(256);
      aliasDescriptorCache = new ConcurrentReferenceHashMap(256);
   }

   private static class DefaultValueHolder {
      final Object defaultValue;

      public DefaultValueHolder(Object defaultValue) {
         this.defaultValue = defaultValue;
      }
   }

   private static final class AliasDescriptor {
      private final Method sourceAttribute;
      private final Class sourceAnnotationType;
      private final String sourceAttributeName;
      private final Method aliasedAttribute;
      private final Class aliasedAnnotationType;
      private final String aliasedAttributeName;
      private final boolean isAliasPair;

      @Nullable
      public static AliasDescriptor from(Method attribute) {
         AliasDescriptor descriptor = (AliasDescriptor)AnnotationUtils.aliasDescriptorCache.get(attribute);
         if (descriptor != null) {
            return descriptor;
         } else {
            AliasFor aliasFor = (AliasFor)attribute.getAnnotation(AliasFor.class);
            if (aliasFor == null) {
               return null;
            } else {
               descriptor = new AliasDescriptor(attribute, aliasFor);
               descriptor.validate();
               AnnotationUtils.aliasDescriptorCache.put(attribute, descriptor);
               return descriptor;
            }
         }
      }

      private AliasDescriptor(Method sourceAttribute, AliasFor aliasFor) {
         Class declaringClass = sourceAttribute.getDeclaringClass();
         this.sourceAttribute = sourceAttribute;
         this.sourceAnnotationType = declaringClass;
         this.sourceAttributeName = sourceAttribute.getName();
         this.aliasedAnnotationType = Annotation.class == aliasFor.annotation() ? this.sourceAnnotationType : aliasFor.annotation();
         this.aliasedAttributeName = this.getAliasedAttributeName(aliasFor, sourceAttribute);
         if (this.aliasedAnnotationType == this.sourceAnnotationType && this.aliasedAttributeName.equals(this.sourceAttributeName)) {
            String msg = String.format("@AliasFor declaration on attribute '%s' in annotation [%s] points to itself. Specify 'annotation' to point to a same-named attribute on a meta-annotation.", sourceAttribute.getName(), declaringClass.getName());
            throw new AnnotationConfigurationException(msg);
         } else {
            try {
               this.aliasedAttribute = this.aliasedAnnotationType.getDeclaredMethod(this.aliasedAttributeName);
            } catch (NoSuchMethodException var6) {
               String msg = String.format("Attribute '%s' in annotation [%s] is declared as an @AliasFor nonexistent attribute '%s' in annotation [%s].", this.sourceAttributeName, this.sourceAnnotationType.getName(), this.aliasedAttributeName, this.aliasedAnnotationType.getName());
               throw new AnnotationConfigurationException(msg, var6);
            }

            this.isAliasPair = this.sourceAnnotationType == this.aliasedAnnotationType;
         }
      }

      private void validate() {
         if (!this.isAliasPair && !AnnotationUtils.isAnnotationMetaPresent(this.sourceAnnotationType, this.aliasedAnnotationType)) {
            String msg = String.format("@AliasFor declaration on attribute '%s' in annotation [%s] declares an alias for attribute '%s' in meta-annotation [%s] which is not meta-present.", this.sourceAttributeName, this.sourceAnnotationType.getName(), this.aliasedAttributeName, this.aliasedAnnotationType.getName());
            throw new AnnotationConfigurationException(msg);
         } else {
            String msg;
            if (this.isAliasPair) {
               AliasFor mirrorAliasFor = (AliasFor)this.aliasedAttribute.getAnnotation(AliasFor.class);
               String mirrorAliasedAttributeName;
               if (mirrorAliasFor == null) {
                  mirrorAliasedAttributeName = String.format("Attribute '%s' in annotation [%s] must be declared as an @AliasFor [%s].", this.aliasedAttributeName, this.sourceAnnotationType.getName(), this.sourceAttributeName);
                  throw new AnnotationConfigurationException(mirrorAliasedAttributeName);
               }

               mirrorAliasedAttributeName = this.getAliasedAttributeName(mirrorAliasFor, this.aliasedAttribute);
               if (!this.sourceAttributeName.equals(mirrorAliasedAttributeName)) {
                  msg = String.format("Attribute '%s' in annotation [%s] must be declared as an @AliasFor [%s], not [%s].", this.aliasedAttributeName, this.sourceAnnotationType.getName(), this.sourceAttributeName, mirrorAliasedAttributeName);
                  throw new AnnotationConfigurationException(msg);
               }
            }

            Class returnType = this.sourceAttribute.getReturnType();
            Class aliasedReturnType = this.aliasedAttribute.getReturnType();
            if (returnType != aliasedReturnType && (!aliasedReturnType.isArray() || returnType != aliasedReturnType.getComponentType())) {
               msg = String.format("Misconfigured aliases: attribute '%s' in annotation [%s] and attribute '%s' in annotation [%s] must declare the same return type.", this.sourceAttributeName, this.sourceAnnotationType.getName(), this.aliasedAttributeName, this.aliasedAnnotationType.getName());
               throw new AnnotationConfigurationException(msg);
            } else {
               if (this.isAliasPair) {
                  this.validateDefaultValueConfiguration(this.aliasedAttribute);
               }

            }
         }
      }

      private void validateDefaultValueConfiguration(Method aliasedAttribute) {
         Object defaultValue = this.sourceAttribute.getDefaultValue();
         Object aliasedDefaultValue = aliasedAttribute.getDefaultValue();
         String msg;
         if (defaultValue != null && aliasedDefaultValue != null) {
            if (!ObjectUtils.nullSafeEquals(defaultValue, aliasedDefaultValue)) {
               msg = String.format("Misconfigured aliases: attribute '%s' in annotation [%s] and attribute '%s' in annotation [%s] must declare the same default value.", this.sourceAttributeName, this.sourceAnnotationType.getName(), aliasedAttribute.getName(), aliasedAttribute.getDeclaringClass().getName());
               throw new AnnotationConfigurationException(msg);
            }
         } else {
            msg = String.format("Misconfigured aliases: attribute '%s' in annotation [%s] and attribute '%s' in annotation [%s] must declare default values.", this.sourceAttributeName, this.sourceAnnotationType.getName(), aliasedAttribute.getName(), aliasedAttribute.getDeclaringClass().getName());
            throw new AnnotationConfigurationException(msg);
         }
      }

      private void validateAgainst(AliasDescriptor otherDescriptor) {
         this.validateDefaultValueConfiguration(otherDescriptor.sourceAttribute);
      }

      private boolean isOverrideFor(Class metaAnnotationType) {
         return this.aliasedAnnotationType == metaAnnotationType;
      }

      private boolean isAliasFor(AliasDescriptor otherDescriptor) {
         for(AliasDescriptor lhs = this; lhs != null; lhs = lhs.getAttributeOverrideDescriptor()) {
            for(AliasDescriptor rhs = otherDescriptor; rhs != null; rhs = rhs.getAttributeOverrideDescriptor()) {
               if (lhs.aliasedAttribute.equals(rhs.aliasedAttribute)) {
                  return true;
               }
            }
         }

         return false;
      }

      public List getAttributeAliasNames() {
         if (this.isAliasPair) {
            return Collections.singletonList(this.aliasedAttributeName);
         } else {
            List aliases = new ArrayList();
            Iterator var2 = this.getOtherDescriptors().iterator();

            while(var2.hasNext()) {
               AliasDescriptor otherDescriptor = (AliasDescriptor)var2.next();
               if (this.isAliasFor(otherDescriptor)) {
                  this.validateAgainst(otherDescriptor);
                  aliases.add(otherDescriptor.sourceAttributeName);
               }
            }

            return aliases;
         }
      }

      private List getOtherDescriptors() {
         List otherDescriptors = new ArrayList();
         Iterator var2 = AnnotationUtils.getAttributeMethods(this.sourceAnnotationType).iterator();

         while(var2.hasNext()) {
            Method currentAttribute = (Method)var2.next();
            if (!this.sourceAttribute.equals(currentAttribute)) {
               AliasDescriptor otherDescriptor = from(currentAttribute);
               if (otherDescriptor != null) {
                  otherDescriptors.add(otherDescriptor);
               }
            }
         }

         return otherDescriptors;
      }

      @Nullable
      public String getAttributeOverrideName(Class metaAnnotationType) {
         for(AliasDescriptor desc = this; desc != null; desc = desc.getAttributeOverrideDescriptor()) {
            if (desc.isOverrideFor(metaAnnotationType)) {
               return desc.aliasedAttributeName;
            }
         }

         return null;
      }

      @Nullable
      private AliasDescriptor getAttributeOverrideDescriptor() {
         return this.isAliasPair ? null : from(this.aliasedAttribute);
      }

      private String getAliasedAttributeName(AliasFor aliasFor, Method attribute) {
         String attributeName = aliasFor.attribute();
         String value = aliasFor.value();
         boolean attributeDeclared = StringUtils.hasText(attributeName);
         boolean valueDeclared = StringUtils.hasText(value);
         if (attributeDeclared && valueDeclared) {
            String msg = String.format("In @AliasFor declared on attribute '%s' in annotation [%s], attribute 'attribute' and its alias 'value' are present with values of [%s] and [%s], but only one is permitted.", attribute.getName(), attribute.getDeclaringClass().getName(), attributeName, value);
            throw new AnnotationConfigurationException(msg);
         } else {
            attributeName = attributeDeclared ? attributeName : value;
            return StringUtils.hasText(attributeName) ? attributeName.trim() : attribute.getName();
         }
      }

      public String toString() {
         return String.format("%s: @%s(%s) is an alias for @%s(%s)", this.getClass().getSimpleName(), this.sourceAnnotationType.getSimpleName(), this.sourceAttributeName, this.aliasedAnnotationType.getSimpleName(), this.aliasedAttributeName);
      }
   }

   private static class AnnotationCollector {
      private final Class annotationType;
      @Nullable
      private final Class containerAnnotationType;
      private final Set visited = new HashSet();
      private final Set result = new LinkedHashSet();

      AnnotationCollector(Class annotationType, @Nullable Class containerAnnotationType) {
         this.annotationType = annotationType;
         this.containerAnnotationType = containerAnnotationType != null ? containerAnnotationType : AnnotationUtils.resolveContainerAnnotationType(annotationType);
      }

      Set getResult(AnnotatedElement element) {
         this.process(element);
         return Collections.unmodifiableSet(this.result);
      }

      private void process(AnnotatedElement element) {
         if (this.visited.add(element)) {
            try {
               Annotation[] annotations = AnnotationUtils.getDeclaredAnnotations(element);
               Annotation[] var3 = annotations;
               int var4 = annotations.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  Annotation ann = var3[var5];
                  Class currentAnnotationType = ann.annotationType();
                  if (ObjectUtils.nullSafeEquals(this.annotationType, currentAnnotationType)) {
                     this.result.add(AnnotationUtils.synthesizeAnnotation(ann, element));
                  } else if (ObjectUtils.nullSafeEquals(this.containerAnnotationType, currentAnnotationType)) {
                     this.result.addAll(this.getValue(element, ann));
                  } else if (!AnnotationUtils.isInJavaLangAnnotationPackage(currentAnnotationType)) {
                     this.process(currentAnnotationType);
                  }
               }
            } catch (Throwable var8) {
               AnnotationUtils.handleIntrospectionFailure(element, var8);
            }
         }

      }

      private List getValue(AnnotatedElement element, Annotation annotation) {
         try {
            List synthesizedAnnotations = new ArrayList();
            Annotation[] value = (Annotation[])((Annotation[])AnnotationUtils.getValue(annotation));
            if (value != null) {
               Annotation[] var5 = value;
               int var6 = value.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  Annotation anno = var5[var7];
                  synthesizedAnnotations.add(AnnotationUtils.synthesizeAnnotation(anno, element));
               }
            }

            return synthesizedAnnotations;
         } catch (Throwable var9) {
            AnnotationUtils.handleIntrospectionFailure(element, var9);
            return Collections.emptyList();
         }
      }
   }

   private static final class AnnotationCacheKey implements Comparable {
      private final AnnotatedElement element;
      private final Class annotationType;

      public AnnotationCacheKey(AnnotatedElement element, Class annotationType) {
         this.element = element;
         this.annotationType = annotationType;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof AnnotationCacheKey)) {
            return false;
         } else {
            AnnotationCacheKey otherKey = (AnnotationCacheKey)other;
            return this.element.equals(otherKey.element) && this.annotationType.equals(otherKey.annotationType);
         }
      }

      public int hashCode() {
         return this.element.hashCode() * 29 + this.annotationType.hashCode();
      }

      public String toString() {
         return "@" + this.annotationType + " on " + this.element;
      }

      public int compareTo(AnnotationCacheKey other) {
         int result = this.element.toString().compareTo(other.element.toString());
         if (result == 0) {
            result = this.annotationType.getName().compareTo(other.annotationType.getName());
         }

         return result;
      }
   }
}
