package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypeConverter;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.expression.spel.support.ReflectionHelper;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FunctionReference extends SpelNodeImpl {
   private final String name;
   @Nullable
   private volatile Method method;

   public FunctionReference(String functionName, int pos, SpelNodeImpl... arguments) {
      super(pos, arguments);
      this.name = functionName;
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      TypedValue value = state.lookupVariable(this.name);
      if (value == TypedValue.NULL) {
         throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.FUNCTION_NOT_DEFINED, new Object[]{this.name});
      } else if (!(value.getValue() instanceof Method)) {
         throw new SpelEvaluationException(SpelMessage.FUNCTION_REFERENCE_CANNOT_BE_INVOKED, new Object[]{this.name, value.getClass()});
      } else {
         try {
            return this.executeFunctionJLRMethod(state, (Method)value.getValue());
         } catch (SpelEvaluationException var4) {
            var4.setPosition(this.getStartPosition());
            throw var4;
         }
      }
   }

   private TypedValue executeFunctionJLRMethod(ExpressionState state, Method method) throws EvaluationException {
      Object[] functionArgs = this.getArguments(state);
      if (!method.isVarArgs()) {
         int declaredParamCount = method.getParameterCount();
         if (declaredParamCount != functionArgs.length) {
            throw new SpelEvaluationException(SpelMessage.INCORRECT_NUMBER_OF_ARGUMENTS_TO_FUNCTION, new Object[]{functionArgs.length, declaredParamCount});
         }
      }

      if (!Modifier.isStatic(method.getModifiers())) {
         throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.FUNCTION_MUST_BE_STATIC, new Object[]{ClassUtils.getQualifiedMethodName(method), this.name});
      } else {
         TypeConverter converter = state.getEvaluationContext().getTypeConverter();
         boolean argumentConversionOccurred = ReflectionHelper.convertAllArguments(converter, functionArgs, method);
         if (method.isVarArgs()) {
            functionArgs = ReflectionHelper.setupArgumentsForVarargsInvocation(method.getParameterTypes(), functionArgs);
         }

         boolean compilable = false;

         TypedValue var8;
         try {
            ReflectionUtils.makeAccessible(method);
            Object result = method.invoke(method.getClass(), functionArgs);
            compilable = !argumentConversionOccurred;
            var8 = new TypedValue(result, (new TypeDescriptor(new MethodParameter(method, -1))).narrow(result));
         } catch (Exception var12) {
            throw new SpelEvaluationException(this.getStartPosition(), var12, SpelMessage.EXCEPTION_DURING_FUNCTION_CALL, new Object[]{this.name, var12.getMessage()});
         } finally {
            if (compilable) {
               this.exitTypeDescriptor = CodeFlow.toDescriptor(method.getReturnType());
               this.method = method;
            } else {
               this.exitTypeDescriptor = null;
               this.method = null;
            }

         }

         return var8;
      }
   }

   public String toStringAST() {
      StringBuilder sb = (new StringBuilder("#")).append(this.name);
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

   private Object[] getArguments(ExpressionState state) throws EvaluationException {
      Object[] arguments = new Object[this.getChildCount()];

      for(int i = 0; i < arguments.length; ++i) {
         arguments[i] = this.children[i].getValueInternal(state).getValue();
      }

      return arguments;
   }

   public boolean isCompilable() {
      Method method = this.method;
      if (method == null) {
         return false;
      } else {
         int methodModifiers = method.getModifiers();
         if (Modifier.isStatic(methodModifiers) && Modifier.isPublic(methodModifiers) && Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            SpelNodeImpl[] var3 = this.children;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               SpelNodeImpl child = var3[var5];
               if (!child.isCompilable()) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      Method method = this.method;
      Assert.state(method != null, "No method handle");
      String classDesc = method.getDeclaringClass().getName().replace('.', '/');
      generateCodeForArguments(mv, cf, method, this.children);
      mv.visitMethodInsn(184, classDesc, method.getName(), CodeFlow.createSignatureDescriptor(method), false);
      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
