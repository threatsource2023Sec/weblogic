package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionFailedException;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

final class ObjectToObjectConverter implements ConditionalGenericConverter {
   private static final Map conversionMemberCache = new ConcurrentReferenceHashMap(32);

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Object.class, Object.class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return sourceType.getType() != targetType.getType() && hasConversionMethodOrConstructor(targetType.getType(), sourceType.getType());
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         return null;
      } else {
         Class sourceClass = sourceType.getType();
         Class targetClass = targetType.getType();
         Member member = getValidatedMember(targetClass, sourceClass);

         try {
            if (member instanceof Method) {
               Method method = (Method)member;
               ReflectionUtils.makeAccessible(method);
               if (!Modifier.isStatic(method.getModifiers())) {
                  return method.invoke(source);
               }

               return method.invoke((Object)null, source);
            }

            if (member instanceof Constructor) {
               Constructor ctor = (Constructor)member;
               ReflectionUtils.makeAccessible(ctor);
               return ctor.newInstance(source);
            }
         } catch (InvocationTargetException var8) {
            throw new ConversionFailedException(sourceType, targetType, source, var8.getTargetException());
         } catch (Throwable var9) {
            throw new ConversionFailedException(sourceType, targetType, source, var9);
         }

         throw new IllegalStateException(String.format("No to%3$s() method exists on %1$s, and no static valueOf/of/from(%1$s) method or %3$s(%1$s) constructor exists on %2$s.", sourceClass.getName(), targetClass.getName(), targetClass.getSimpleName()));
      }
   }

   static boolean hasConversionMethodOrConstructor(Class targetClass, Class sourceClass) {
      return getValidatedMember(targetClass, sourceClass) != null;
   }

   @Nullable
   private static Member getValidatedMember(Class targetClass, Class sourceClass) {
      Member member = (Member)conversionMemberCache.get(targetClass);
      if (isApplicable(member, sourceClass)) {
         return member;
      } else {
         Member member = determineToMethod(targetClass, sourceClass);
         if (member == null) {
            member = determineFactoryMethod(targetClass, sourceClass);
            if (member == null) {
               member = determineFactoryConstructor(targetClass, sourceClass);
               if (member == null) {
                  return null;
               }
            }
         }

         conversionMemberCache.put(targetClass, member);
         return (Member)member;
      }
   }

   private static boolean isApplicable(Member member, Class sourceClass) {
      if (member instanceof Method) {
         Method method = (Method)member;
         return !Modifier.isStatic(method.getModifiers()) ? ClassUtils.isAssignable(method.getDeclaringClass(), sourceClass) : method.getParameterTypes()[0] == sourceClass;
      } else if (member instanceof Constructor) {
         Constructor ctor = (Constructor)member;
         return ctor.getParameterTypes()[0] == sourceClass;
      } else {
         return false;
      }
   }

   @Nullable
   private static Method determineToMethod(Class targetClass, Class sourceClass) {
      if (String.class != targetClass && String.class != sourceClass) {
         Method method = ClassUtils.getMethodIfAvailable(sourceClass, "to" + targetClass.getSimpleName());
         return method != null && !Modifier.isStatic(method.getModifiers()) && ClassUtils.isAssignable(targetClass, method.getReturnType()) ? method : null;
      } else {
         return null;
      }
   }

   @Nullable
   private static Method determineFactoryMethod(Class targetClass, Class sourceClass) {
      if (String.class == targetClass) {
         return null;
      } else {
         Method method = ClassUtils.getStaticMethod(targetClass, "valueOf", sourceClass);
         if (method == null) {
            method = ClassUtils.getStaticMethod(targetClass, "of", sourceClass);
            if (method == null) {
               method = ClassUtils.getStaticMethod(targetClass, "from", sourceClass);
            }
         }

         return method;
      }
   }

   @Nullable
   private static Constructor determineFactoryConstructor(Class targetClass, Class sourceClass) {
      return ClassUtils.getConstructorIfAvailable(targetClass, sourceClass);
   }
}
