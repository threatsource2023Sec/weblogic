package org.python.google.common.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ImmutableList;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
public abstract class Invokable extends Element implements GenericDeclaration {
   Invokable(AccessibleObject member) {
      super(member);
   }

   public static Invokable from(Method method) {
      return new MethodInvokable(method);
   }

   public static Invokable from(Constructor constructor) {
      return new ConstructorInvokable(constructor);
   }

   public abstract boolean isOverridable();

   public abstract boolean isVarArgs();

   @CanIgnoreReturnValue
   public final Object invoke(@Nullable Object receiver, Object... args) throws InvocationTargetException, IllegalAccessException {
      return this.invokeInternal(receiver, (Object[])Preconditions.checkNotNull(args));
   }

   public final TypeToken getReturnType() {
      return TypeToken.of(this.getGenericReturnType());
   }

   public final ImmutableList getParameters() {
      Type[] parameterTypes = this.getGenericParameterTypes();
      Annotation[][] annotations = this.getParameterAnnotations();
      ImmutableList.Builder builder = ImmutableList.builder();

      for(int i = 0; i < parameterTypes.length; ++i) {
         builder.add((Object)(new Parameter(this, i, TypeToken.of(parameterTypes[i]), annotations[i])));
      }

      return builder.build();
   }

   public final ImmutableList getExceptionTypes() {
      ImmutableList.Builder builder = ImmutableList.builder();
      Type[] var2 = this.getGenericExceptionTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type type = var2[var4];
         TypeToken exceptionType = TypeToken.of(type);
         builder.add((Object)exceptionType);
      }

      return builder.build();
   }

   public final Invokable returning(Class returnType) {
      return this.returning(TypeToken.of(returnType));
   }

   public final Invokable returning(TypeToken returnType) {
      if (!returnType.isSupertypeOf(this.getReturnType())) {
         throw new IllegalArgumentException("Invokable is known to return " + this.getReturnType() + ", not " + returnType);
      } else {
         return this;
      }
   }

   public final Class getDeclaringClass() {
      return super.getDeclaringClass();
   }

   public TypeToken getOwnerType() {
      return TypeToken.of(this.getDeclaringClass());
   }

   abstract Object invokeInternal(@Nullable Object var1, Object[] var2) throws InvocationTargetException, IllegalAccessException;

   abstract Type[] getGenericParameterTypes();

   abstract Type[] getGenericExceptionTypes();

   abstract Annotation[][] getParameterAnnotations();

   abstract Type getGenericReturnType();

   static class ConstructorInvokable extends Invokable {
      final Constructor constructor;

      ConstructorInvokable(Constructor constructor) {
         super(constructor);
         this.constructor = constructor;
      }

      final Object invokeInternal(@Nullable Object receiver, Object[] args) throws InvocationTargetException, IllegalAccessException {
         try {
            return this.constructor.newInstance(args);
         } catch (InstantiationException var4) {
            throw new RuntimeException(this.constructor + " failed.", var4);
         }
      }

      Type getGenericReturnType() {
         Class declaringClass = this.getDeclaringClass();
         TypeVariable[] typeParams = declaringClass.getTypeParameters();
         return (Type)(typeParams.length > 0 ? Types.newParameterizedType(declaringClass, typeParams) : declaringClass);
      }

      Type[] getGenericParameterTypes() {
         Type[] types = this.constructor.getGenericParameterTypes();
         if (types.length > 0 && this.mayNeedHiddenThis()) {
            Class[] rawParamTypes = this.constructor.getParameterTypes();
            if (types.length == rawParamTypes.length && rawParamTypes[0] == this.getDeclaringClass().getEnclosingClass()) {
               return (Type[])Arrays.copyOfRange(types, 1, types.length);
            }
         }

         return types;
      }

      Type[] getGenericExceptionTypes() {
         return this.constructor.getGenericExceptionTypes();
      }

      final Annotation[][] getParameterAnnotations() {
         return this.constructor.getParameterAnnotations();
      }

      public final TypeVariable[] getTypeParameters() {
         TypeVariable[] declaredByClass = this.getDeclaringClass().getTypeParameters();
         TypeVariable[] declaredByConstructor = this.constructor.getTypeParameters();
         TypeVariable[] result = new TypeVariable[declaredByClass.length + declaredByConstructor.length];
         System.arraycopy(declaredByClass, 0, result, 0, declaredByClass.length);
         System.arraycopy(declaredByConstructor, 0, result, declaredByClass.length, declaredByConstructor.length);
         return result;
      }

      public final boolean isOverridable() {
         return false;
      }

      public final boolean isVarArgs() {
         return this.constructor.isVarArgs();
      }

      private boolean mayNeedHiddenThis() {
         Class declaringClass = this.constructor.getDeclaringClass();
         if (declaringClass.getEnclosingConstructor() != null) {
            return true;
         } else {
            Method enclosingMethod = declaringClass.getEnclosingMethod();
            if (enclosingMethod != null) {
               return !Modifier.isStatic(enclosingMethod.getModifiers());
            } else {
               return declaringClass.getEnclosingClass() != null && !Modifier.isStatic(declaringClass.getModifiers());
            }
         }
      }
   }

   static class MethodInvokable extends Invokable {
      final Method method;

      MethodInvokable(Method method) {
         super(method);
         this.method = method;
      }

      final Object invokeInternal(@Nullable Object receiver, Object[] args) throws InvocationTargetException, IllegalAccessException {
         return this.method.invoke(receiver, args);
      }

      Type getGenericReturnType() {
         return this.method.getGenericReturnType();
      }

      Type[] getGenericParameterTypes() {
         return this.method.getGenericParameterTypes();
      }

      Type[] getGenericExceptionTypes() {
         return this.method.getGenericExceptionTypes();
      }

      final Annotation[][] getParameterAnnotations() {
         return this.method.getParameterAnnotations();
      }

      public final TypeVariable[] getTypeParameters() {
         return this.method.getTypeParameters();
      }

      public final boolean isOverridable() {
         return !this.isFinal() && !this.isPrivate() && !this.isStatic() && !Modifier.isFinal(this.getDeclaringClass().getModifiers());
      }

      public final boolean isVarArgs() {
         return this.method.isVarArgs();
      }
   }
}
