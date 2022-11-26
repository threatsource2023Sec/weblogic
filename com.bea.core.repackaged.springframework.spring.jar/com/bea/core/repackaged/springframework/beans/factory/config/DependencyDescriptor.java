package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.InjectionPoint;
import com.bea.core.repackaged.springframework.beans.factory.NoUniqueBeanDefinitionException;
import com.bea.core.repackaged.springframework.core.GenericTypeResolver;
import com.bea.core.repackaged.springframework.core.KotlinDetector;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.ParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.ReflectJvmMapping;

public class DependencyDescriptor extends InjectionPoint implements Serializable {
   private final Class declaringClass;
   @Nullable
   private String methodName;
   @Nullable
   private Class[] parameterTypes;
   private int parameterIndex;
   @Nullable
   private String fieldName;
   private final boolean required;
   private final boolean eager;
   private int nestingLevel;
   @Nullable
   private Class containingClass;
   @Nullable
   private transient volatile ResolvableType resolvableType;
   @Nullable
   private transient volatile TypeDescriptor typeDescriptor;

   public DependencyDescriptor(MethodParameter methodParameter, boolean required) {
      this(methodParameter, required, true);
   }

   public DependencyDescriptor(MethodParameter methodParameter, boolean required, boolean eager) {
      super(methodParameter);
      this.nestingLevel = 1;
      this.declaringClass = methodParameter.getDeclaringClass();
      if (methodParameter.getMethod() != null) {
         this.methodName = methodParameter.getMethod().getName();
      }

      this.parameterTypes = methodParameter.getExecutable().getParameterTypes();
      this.parameterIndex = methodParameter.getParameterIndex();
      this.containingClass = methodParameter.getContainingClass();
      this.required = required;
      this.eager = eager;
   }

   public DependencyDescriptor(Field field, boolean required) {
      this(field, required, true);
   }

   public DependencyDescriptor(Field field, boolean required, boolean eager) {
      super(field);
      this.nestingLevel = 1;
      this.declaringClass = field.getDeclaringClass();
      this.fieldName = field.getName();
      this.required = required;
      this.eager = eager;
   }

   public DependencyDescriptor(DependencyDescriptor original) {
      super((InjectionPoint)original);
      this.nestingLevel = 1;
      this.declaringClass = original.declaringClass;
      this.methodName = original.methodName;
      this.parameterTypes = original.parameterTypes;
      this.parameterIndex = original.parameterIndex;
      this.fieldName = original.fieldName;
      this.containingClass = original.containingClass;
      this.required = original.required;
      this.eager = original.eager;
      this.nestingLevel = original.nestingLevel;
   }

   public boolean isRequired() {
      if (!this.required) {
         return false;
      } else if (this.field == null) {
         return !this.obtainMethodParameter().isOptional();
      } else {
         return this.field.getType() != Optional.class && !this.hasNullableAnnotation() && (!KotlinDetector.isKotlinReflectPresent() || !KotlinDetector.isKotlinType(this.field.getDeclaringClass()) || !DependencyDescriptor.KotlinDelegate.isNullable(this.field));
      }
   }

   private boolean hasNullableAnnotation() {
      Annotation[] var1 = this.getAnnotations();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Annotation ann = var1[var3];
         if ("Nullable".equals(ann.annotationType().getSimpleName())) {
            return true;
         }
      }

      return false;
   }

   public boolean isEager() {
      return this.eager;
   }

   @Nullable
   public Object resolveNotUnique(ResolvableType type, Map matchingBeans) throws BeansException {
      throw new NoUniqueBeanDefinitionException(type, matchingBeans.keySet());
   }

   /** @deprecated */
   @Deprecated
   @Nullable
   public Object resolveNotUnique(Class type, Map matchingBeans) throws BeansException {
      throw new NoUniqueBeanDefinitionException(type, matchingBeans.keySet());
   }

   @Nullable
   public Object resolveShortcut(BeanFactory beanFactory) throws BeansException {
      return null;
   }

   public Object resolveCandidate(String beanName, Class requiredType, BeanFactory beanFactory) throws BeansException {
      return beanFactory.getBean(beanName);
   }

   public void increaseNestingLevel() {
      ++this.nestingLevel;
      this.resolvableType = null;
      if (this.methodParameter != null) {
         this.methodParameter.increaseNestingLevel();
      }

   }

   public void setContainingClass(Class containingClass) {
      this.containingClass = containingClass;
      this.resolvableType = null;
      if (this.methodParameter != null) {
         GenericTypeResolver.resolveParameterType(this.methodParameter, containingClass);
      }

   }

   public ResolvableType getResolvableType() {
      ResolvableType resolvableType = this.resolvableType;
      if (resolvableType == null) {
         resolvableType = this.field != null ? ResolvableType.forField(this.field, this.nestingLevel, this.containingClass) : ResolvableType.forMethodParameter(this.obtainMethodParameter());
         this.resolvableType = resolvableType;
      }

      return resolvableType;
   }

   public TypeDescriptor getTypeDescriptor() {
      TypeDescriptor typeDescriptor = this.typeDescriptor;
      if (typeDescriptor == null) {
         typeDescriptor = this.field != null ? new TypeDescriptor(this.getResolvableType(), this.getDependencyType(), this.getAnnotations()) : new TypeDescriptor(this.obtainMethodParameter());
         this.typeDescriptor = typeDescriptor;
      }

      return typeDescriptor;
   }

   public boolean fallbackMatchAllowed() {
      return false;
   }

   public DependencyDescriptor forFallbackMatch() {
      return new DependencyDescriptor(this) {
         public boolean fallbackMatchAllowed() {
            return true;
         }
      };
   }

   public void initParameterNameDiscovery(@Nullable ParameterNameDiscoverer parameterNameDiscoverer) {
      if (this.methodParameter != null) {
         this.methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);
      }

   }

   @Nullable
   public String getDependencyName() {
      return this.field != null ? this.field.getName() : this.obtainMethodParameter().getParameterName();
   }

   public Class getDependencyType() {
      if (this.field != null) {
         if (this.nestingLevel > 1) {
            Type type = this.field.getGenericType();

            for(int i = 2; i <= this.nestingLevel; ++i) {
               if (type instanceof ParameterizedType) {
                  Type[] args = ((ParameterizedType)type).getActualTypeArguments();
                  type = args[args.length - 1];
               }
            }

            if (type instanceof Class) {
               return (Class)type;
            } else {
               if (type instanceof ParameterizedType) {
                  Type arg = ((ParameterizedType)type).getRawType();
                  if (arg instanceof Class) {
                     return (Class)arg;
                  }
               }

               return Object.class;
            }
         } else {
            return this.field.getType();
         }
      } else {
         return this.obtainMethodParameter().getNestedParameterType();
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else {
         DependencyDescriptor otherDesc = (DependencyDescriptor)other;
         return this.required == otherDesc.required && this.eager == otherDesc.eager && this.nestingLevel == otherDesc.nestingLevel && this.containingClass == otherDesc.containingClass;
      }
   }

   public int hashCode() {
      return 31 * super.hashCode() + ObjectUtils.nullSafeHashCode((Object)this.containingClass);
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject();

      try {
         if (this.fieldName != null) {
            this.field = this.declaringClass.getDeclaredField(this.fieldName);
         } else {
            if (this.methodName != null) {
               this.methodParameter = new MethodParameter(this.declaringClass.getDeclaredMethod(this.methodName, this.parameterTypes), this.parameterIndex);
            } else {
               this.methodParameter = new MethodParameter(this.declaringClass.getDeclaredConstructor(this.parameterTypes), this.parameterIndex);
            }

            for(int i = 1; i < this.nestingLevel; ++i) {
               this.methodParameter.increaseNestingLevel();
            }
         }

      } catch (Throwable var3) {
         throw new IllegalStateException("Could not find original class structure", var3);
      }
   }

   private static class KotlinDelegate {
      public static boolean isNullable(Field field) {
         KProperty property = ReflectJvmMapping.getKotlinProperty(field);
         return property != null && property.getReturnType().isMarkedNullable();
      }
   }
}
