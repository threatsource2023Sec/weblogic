package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.Label;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.ExpressionInvocationTargetException;
import com.bea.core.repackaged.springframework.expression.MethodExecutor;
import com.bea.core.repackaged.springframework.expression.MethodResolver;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.expression.spel.support.ReflectiveMethodExecutor;
import com.bea.core.repackaged.springframework.expression.spel.support.ReflectiveMethodResolver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MethodReference extends SpelNodeImpl {
   private final String name;
   private final boolean nullSafe;
   @Nullable
   private String originalPrimitiveExitTypeDescriptor;
   @Nullable
   private volatile CachedMethodExecutor cachedExecutor;

   public MethodReference(boolean nullSafe, String methodName, int pos, SpelNodeImpl... arguments) {
      super(pos, arguments);
      this.name = methodName;
      this.nullSafe = nullSafe;
   }

   public final String getName() {
      return this.name;
   }

   protected ValueRef getValueRef(ExpressionState state) throws EvaluationException {
      Object[] arguments = this.getArguments(state);
      if (state.getActiveContextObject().getValue() == null) {
         this.throwIfNotNullSafe(this.getArgumentTypes(arguments));
         return ValueRef.NullValueRef.INSTANCE;
      } else {
         return new MethodValueRef(state, arguments);
      }
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      EvaluationContext evaluationContext = state.getEvaluationContext();
      Object value = state.getActiveContextObject().getValue();
      TypeDescriptor targetType = state.getActiveContextObject().getTypeDescriptor();
      Object[] arguments = this.getArguments(state);
      TypedValue result = this.getValueInternal(evaluationContext, value, targetType, arguments);
      this.updateExitTypeDescriptor();
      return result;
   }

   private TypedValue getValueInternal(EvaluationContext evaluationContext, @Nullable Object value, @Nullable TypeDescriptor targetType, Object[] arguments) {
      List argumentTypes = this.getArgumentTypes(arguments);
      if (value == null) {
         this.throwIfNotNullSafe(argumentTypes);
         return TypedValue.NULL;
      } else {
         MethodExecutor executorToUse = this.getCachedExecutor(evaluationContext, value, targetType, argumentTypes);
         if (executorToUse != null) {
            try {
               return executorToUse.execute(evaluationContext, value, arguments);
            } catch (AccessException var9) {
               this.throwSimpleExceptionIfPossible(value, var9);
               this.cachedExecutor = null;
            }
         }

         executorToUse = this.findAccessorForMethod(argumentTypes, value, evaluationContext);
         this.cachedExecutor = new CachedMethodExecutor(executorToUse, value instanceof Class ? (Class)value : null, targetType, argumentTypes);

         try {
            return executorToUse.execute(evaluationContext, value, arguments);
         } catch (AccessException var8) {
            this.throwSimpleExceptionIfPossible(value, var8);
            throw new SpelEvaluationException(this.getStartPosition(), var8, SpelMessage.EXCEPTION_DURING_METHOD_INVOCATION, new Object[]{this.name, value.getClass().getName(), var8.getMessage()});
         }
      }
   }

   private void throwIfNotNullSafe(List argumentTypes) {
      if (!this.nullSafe) {
         throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.METHOD_CALL_ON_NULL_OBJECT_NOT_ALLOWED, new Object[]{FormatHelper.formatMethodForMessage(this.name, argumentTypes)});
      }
   }

   private Object[] getArguments(ExpressionState state) {
      Object[] arguments = new Object[this.getChildCount()];

      for(int i = 0; i < arguments.length; ++i) {
         try {
            state.pushActiveContextObject(state.getScopeRootContextObject());
            arguments[i] = this.children[i].getValueInternal(state).getValue();
         } finally {
            state.popActiveContextObject();
         }
      }

      return arguments;
   }

   private List getArgumentTypes(Object... arguments) {
      List descriptors = new ArrayList(arguments.length);
      Object[] var3 = arguments;
      int var4 = arguments.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object argument = var3[var5];
         descriptors.add(TypeDescriptor.forObject(argument));
      }

      return Collections.unmodifiableList(descriptors);
   }

   @Nullable
   private MethodExecutor getCachedExecutor(EvaluationContext evaluationContext, Object value, @Nullable TypeDescriptor target, List argumentTypes) {
      List methodResolvers = evaluationContext.getMethodResolvers();
      if (methodResolvers.size() == 1 && methodResolvers.get(0) instanceof ReflectiveMethodResolver) {
         CachedMethodExecutor executorToCheck = this.cachedExecutor;
         if (executorToCheck != null && executorToCheck.isSuitable(value, target, argumentTypes)) {
            return executorToCheck.get();
         } else {
            this.cachedExecutor = null;
            return null;
         }
      } else {
         return null;
      }
   }

   private MethodExecutor findAccessorForMethod(List argumentTypes, Object targetObject, EvaluationContext evaluationContext) throws SpelEvaluationException {
      AccessException accessException = null;
      List methodResolvers = evaluationContext.getMethodResolvers();
      Iterator var6 = methodResolvers.iterator();

      while(var6.hasNext()) {
         MethodResolver methodResolver = (MethodResolver)var6.next();

         try {
            MethodExecutor methodExecutor = methodResolver.resolve(evaluationContext, targetObject, this.name, argumentTypes);
            if (methodExecutor != null) {
               return methodExecutor;
            }
         } catch (AccessException var9) {
            accessException = var9;
            break;
         }
      }

      String method = FormatHelper.formatMethodForMessage(this.name, argumentTypes);
      String className = FormatHelper.formatClassNameForMessage(targetObject instanceof Class ? (Class)targetObject : targetObject.getClass());
      if (accessException != null) {
         throw new SpelEvaluationException(this.getStartPosition(), accessException, SpelMessage.PROBLEM_LOCATING_METHOD, new Object[]{method, className});
      } else {
         throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.METHOD_NOT_FOUND, new Object[]{method, className});
      }
   }

   private void throwSimpleExceptionIfPossible(Object value, AccessException ex) {
      if (ex.getCause() instanceof InvocationTargetException) {
         Throwable rootCause = ex.getCause().getCause();
         if (rootCause instanceof RuntimeException) {
            throw (RuntimeException)rootCause;
         } else {
            throw new ExpressionInvocationTargetException(this.getStartPosition(), "A problem occurred when trying to execute method '" + this.name + "' on object of type [" + value.getClass().getName() + "]", rootCause);
         }
      }
   }

   private void updateExitTypeDescriptor() {
      CachedMethodExecutor executorToCheck = this.cachedExecutor;
      if (executorToCheck != null && executorToCheck.get() instanceof ReflectiveMethodExecutor) {
         Method method = ((ReflectiveMethodExecutor)executorToCheck.get()).getMethod();
         String descriptor = CodeFlow.toDescriptor(method.getReturnType());
         if (this.nullSafe && CodeFlow.isPrimitive(descriptor)) {
            this.originalPrimitiveExitTypeDescriptor = descriptor;
            this.exitTypeDescriptor = CodeFlow.toBoxedDescriptor(descriptor);
         } else {
            this.exitTypeDescriptor = descriptor;
         }
      }

   }

   public String toStringAST() {
      StringBuilder sb = new StringBuilder(this.name);
      sb.append("(");

      for(int i = 0; i < this.getChildCount(); ++i) {
         if (i > 0) {
            sb.append(",");
         }

         sb.append(this.getChild(i).toStringAST());
      }

      sb.append(")");
      return sb.toString();
   }

   public boolean isCompilable() {
      CachedMethodExecutor executorToCheck = this.cachedExecutor;
      if (executorToCheck != null && !executorToCheck.hasProxyTarget() && executorToCheck.get() instanceof ReflectiveMethodExecutor) {
         SpelNodeImpl[] var2 = this.children;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            SpelNodeImpl child = var2[var4];
            if (!child.isCompilable()) {
               return false;
            }
         }

         ReflectiveMethodExecutor executor = (ReflectiveMethodExecutor)executorToCheck.get();
         if (executor.didArgumentConversionOccur()) {
            return false;
         } else {
            Class clazz = executor.getMethod().getDeclaringClass();
            if (!Modifier.isPublic(clazz.getModifiers()) && executor.getPublicDeclaringClass() == null) {
               return false;
            } else {
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      CachedMethodExecutor executorToCheck = this.cachedExecutor;
      if (executorToCheck != null && executorToCheck.get() instanceof ReflectiveMethodExecutor) {
         ReflectiveMethodExecutor methodExecutor = (ReflectiveMethodExecutor)executorToCheck.get();
         Method method = methodExecutor.getMethod();
         boolean isStaticMethod = Modifier.isStatic(method.getModifiers());
         String descriptor = cf.lastDescriptor();
         Label skipIfNull = null;
         if (descriptor == null && !isStaticMethod) {
            cf.loadTarget(mv);
         }

         if ((descriptor != null || !isStaticMethod) && this.nullSafe) {
            mv.visitInsn(89);
            skipIfNull = new Label();
            Label continueLabel = new Label();
            mv.visitJumpInsn(199, continueLabel);
            CodeFlow.insertCheckCast(mv, this.exitTypeDescriptor);
            mv.visitJumpInsn(167, skipIfNull);
            mv.visitLabel(continueLabel);
         }

         if (descriptor != null && isStaticMethod) {
            mv.visitInsn(87);
         }

         if (CodeFlow.isPrimitive(descriptor)) {
            CodeFlow.insertBoxIfNecessary(mv, descriptor.charAt(0));
         }

         String classDesc;
         if (Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            classDesc = method.getDeclaringClass().getName().replace('.', '/');
         } else {
            Class publicDeclaringClass = methodExecutor.getPublicDeclaringClass();
            Assert.state(publicDeclaringClass != null, "No public declaring class");
            classDesc = publicDeclaringClass.getName().replace('.', '/');
         }

         if (!isStaticMethod && (descriptor == null || !descriptor.substring(1).equals(classDesc))) {
            CodeFlow.insertCheckCast(mv, "L" + classDesc);
         }

         generateCodeForArguments(mv, cf, method, this.children);
         mv.visitMethodInsn(isStaticMethod ? 184 : 182, classDesc, method.getName(), CodeFlow.createSignatureDescriptor(method), method.getDeclaringClass().isInterface());
         cf.pushDescriptor(this.exitTypeDescriptor);
         if (this.originalPrimitiveExitTypeDescriptor != null) {
            CodeFlow.insertBoxIfNecessary(mv, this.originalPrimitiveExitTypeDescriptor);
         }

         if (skipIfNull != null) {
            mv.visitLabel(skipIfNull);
         }

      } else {
         throw new IllegalStateException("No applicable cached executor found: " + executorToCheck);
      }
   }

   private static class CachedMethodExecutor {
      private final MethodExecutor methodExecutor;
      @Nullable
      private final Class staticClass;
      @Nullable
      private final TypeDescriptor target;
      private final List argumentTypes;

      public CachedMethodExecutor(MethodExecutor methodExecutor, @Nullable Class staticClass, @Nullable TypeDescriptor target, List argumentTypes) {
         this.methodExecutor = methodExecutor;
         this.staticClass = staticClass;
         this.target = target;
         this.argumentTypes = argumentTypes;
      }

      public boolean isSuitable(Object value, @Nullable TypeDescriptor target, List argumentTypes) {
         return (this.staticClass == null || this.staticClass == value) && ObjectUtils.nullSafeEquals(this.target, target) && this.argumentTypes.equals(argumentTypes);
      }

      public boolean hasProxyTarget() {
         return this.target != null && Proxy.isProxyClass(this.target.getType());
      }

      public MethodExecutor get() {
         return this.methodExecutor;
      }
   }

   private class MethodValueRef implements ValueRef {
      private final EvaluationContext evaluationContext;
      @Nullable
      private final Object value;
      @Nullable
      private final TypeDescriptor targetType;
      private final Object[] arguments;

      public MethodValueRef(ExpressionState state, Object[] arguments) {
         this.evaluationContext = state.getEvaluationContext();
         this.value = state.getActiveContextObject().getValue();
         this.targetType = state.getActiveContextObject().getTypeDescriptor();
         this.arguments = arguments;
      }

      public TypedValue getValue() {
         TypedValue result = MethodReference.this.getValueInternal(this.evaluationContext, this.value, this.targetType, this.arguments);
         MethodReference.this.updateExitTypeDescriptor();
         return result;
      }

      public void setValue(@Nullable Object newValue) {
         throw new SpelEvaluationException(0, SpelMessage.NOT_ASSIGNABLE, new Object[]{MethodReference.this.name});
      }

      public boolean isWritable() {
         return false;
      }
   }
}
