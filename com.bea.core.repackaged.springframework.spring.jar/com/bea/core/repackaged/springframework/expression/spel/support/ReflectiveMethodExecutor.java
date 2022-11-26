package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.MethodExecutor;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectiveMethodExecutor implements MethodExecutor {
   private final Method originalMethod;
   private final Method methodToInvoke;
   @Nullable
   private final Integer varargsPosition;
   private boolean computedPublicDeclaringClass = false;
   @Nullable
   private Class publicDeclaringClass;
   private boolean argumentConversionOccurred = false;

   public ReflectiveMethodExecutor(Method method) {
      this.originalMethod = method;
      this.methodToInvoke = ClassUtils.getInterfaceMethodIfPossible(method);
      if (method.isVarArgs()) {
         Class[] paramTypes = method.getParameterTypes();
         this.varargsPosition = paramTypes.length - 1;
      } else {
         this.varargsPosition = null;
      }

   }

   public final Method getMethod() {
      return this.originalMethod;
   }

   @Nullable
   public Class getPublicDeclaringClass() {
      if (!this.computedPublicDeclaringClass) {
         this.publicDeclaringClass = this.discoverPublicDeclaringClass(this.originalMethod, this.originalMethod.getDeclaringClass());
         this.computedPublicDeclaringClass = true;
      }

      return this.publicDeclaringClass;
   }

   @Nullable
   private Class discoverPublicDeclaringClass(Method method, Class clazz) {
      if (Modifier.isPublic(clazz.getModifiers())) {
         try {
            clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
            return clazz;
         } catch (NoSuchMethodException var4) {
         }
      }

      return clazz.getSuperclass() != null ? this.discoverPublicDeclaringClass(method, clazz.getSuperclass()) : null;
   }

   public boolean didArgumentConversionOccur() {
      return this.argumentConversionOccurred;
   }

   public TypedValue execute(EvaluationContext context, Object target, Object... arguments) throws AccessException {
      try {
         this.argumentConversionOccurred = ReflectionHelper.convertArguments(context.getTypeConverter(), arguments, this.originalMethod, this.varargsPosition);
         if (this.originalMethod.isVarArgs()) {
            arguments = ReflectionHelper.setupArgumentsForVarargsInvocation(this.originalMethod.getParameterTypes(), arguments);
         }

         ReflectionUtils.makeAccessible(this.methodToInvoke);
         Object value = this.methodToInvoke.invoke(target, arguments);
         return new TypedValue(value, (new TypeDescriptor(new MethodParameter(this.originalMethod, -1))).narrow(value));
      } catch (Exception var5) {
         throw new AccessException("Problem invoking method: " + this.methodToInvoke, var5);
      }
   }
}
