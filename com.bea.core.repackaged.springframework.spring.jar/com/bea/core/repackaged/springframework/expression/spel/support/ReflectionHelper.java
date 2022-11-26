package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypeConverter;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public abstract class ReflectionHelper {
   @Nullable
   static ArgumentsMatchInfo compareArguments(List expectedArgTypes, List suppliedArgTypes, TypeConverter typeConverter) {
      Assert.isTrue(expectedArgTypes.size() == suppliedArgTypes.size(), "Expected argument types and supplied argument types should be arrays of same length");
      ArgumentsMatchKind match = ReflectionHelper.ArgumentsMatchKind.EXACT;

      for(int i = 0; i < expectedArgTypes.size() && match != null; ++i) {
         TypeDescriptor suppliedArg = (TypeDescriptor)suppliedArgTypes.get(i);
         TypeDescriptor expectedArg = (TypeDescriptor)expectedArgTypes.get(i);
         if (suppliedArg == null) {
            if (expectedArg.isPrimitive()) {
               match = null;
            }
         } else if (!expectedArg.equals(suppliedArg)) {
            if (suppliedArg.isAssignableTo(expectedArg)) {
               if (match != ReflectionHelper.ArgumentsMatchKind.REQUIRES_CONVERSION) {
                  match = ReflectionHelper.ArgumentsMatchKind.CLOSE;
               }
            } else if (typeConverter.canConvert(suppliedArg, expectedArg)) {
               match = ReflectionHelper.ArgumentsMatchKind.REQUIRES_CONVERSION;
            } else {
               match = null;
            }
         }
      }

      return match != null ? new ArgumentsMatchInfo(match) : null;
   }

   public static int getTypeDifferenceWeight(List paramTypes, List argTypes) {
      int result = 0;

      for(int i = 0; i < paramTypes.size(); ++i) {
         TypeDescriptor paramType = (TypeDescriptor)paramTypes.get(i);
         TypeDescriptor argType = i < argTypes.size() ? (TypeDescriptor)argTypes.get(i) : null;
         if (argType == null) {
            if (paramType.isPrimitive()) {
               return Integer.MAX_VALUE;
            }
         } else {
            Class paramTypeClazz = paramType.getType();
            if (!ClassUtils.isAssignable(paramTypeClazz, argType.getType())) {
               return Integer.MAX_VALUE;
            }

            if (paramTypeClazz.isPrimitive()) {
               paramTypeClazz = Object.class;
            }

            Class superClass = argType.getType().getSuperclass();

            while(superClass != null) {
               if (paramTypeClazz.equals(superClass)) {
                  result += 2;
                  superClass = null;
               } else if (ClassUtils.isAssignable(paramTypeClazz, superClass)) {
                  result += 2;
                  superClass = superClass.getSuperclass();
               } else {
                  superClass = null;
               }
            }

            if (paramTypeClazz.isInterface()) {
               ++result;
            }
         }
      }

      return result;
   }

   @Nullable
   static ArgumentsMatchInfo compareArgumentsVarargs(List expectedArgTypes, List suppliedArgTypes, TypeConverter typeConverter) {
      Assert.isTrue(!CollectionUtils.isEmpty((Collection)expectedArgTypes), "Expected arguments must at least include one array (the varargs parameter)");
      Assert.isTrue(((TypeDescriptor)expectedArgTypes.get(expectedArgTypes.size() - 1)).isArray(), "Final expected argument should be array type (the varargs parameter)");
      ArgumentsMatchKind match = ReflectionHelper.ArgumentsMatchKind.EXACT;
      int argCountUpToVarargs = expectedArgTypes.size() - 1;

      TypeDescriptor suppliedArg;
      for(int i = 0; i < argCountUpToVarargs && match != null; ++i) {
         suppliedArg = (TypeDescriptor)suppliedArgTypes.get(i);
         TypeDescriptor expectedArg = (TypeDescriptor)expectedArgTypes.get(i);
         if (suppliedArg == null) {
            if (expectedArg.isPrimitive()) {
               match = null;
            }
         } else if (!expectedArg.equals(suppliedArg)) {
            if (suppliedArg.isAssignableTo(expectedArg)) {
               if (match != ReflectionHelper.ArgumentsMatchKind.REQUIRES_CONVERSION) {
                  match = ReflectionHelper.ArgumentsMatchKind.CLOSE;
               }
            } else if (typeConverter.canConvert(suppliedArg, expectedArg)) {
               match = ReflectionHelper.ArgumentsMatchKind.REQUIRES_CONVERSION;
            } else {
               match = null;
            }
         }
      }

      if (match == null) {
         return null;
      } else {
         if (suppliedArgTypes.size() != expectedArgTypes.size() || !((TypeDescriptor)expectedArgTypes.get(expectedArgTypes.size() - 1)).equals(suppliedArgTypes.get(suppliedArgTypes.size() - 1))) {
            TypeDescriptor varargsDesc = (TypeDescriptor)expectedArgTypes.get(expectedArgTypes.size() - 1);
            suppliedArg = varargsDesc.getElementTypeDescriptor();
            Assert.state(suppliedArg != null, "No element type");
            Class varargsParamType = suppliedArg.getType();

            for(int i = expectedArgTypes.size() - 1; i < suppliedArgTypes.size(); ++i) {
               TypeDescriptor suppliedArg = (TypeDescriptor)suppliedArgTypes.get(i);
               if (suppliedArg == null) {
                  if (varargsParamType.isPrimitive()) {
                     match = null;
                  }
               } else if (varargsParamType != suppliedArg.getType()) {
                  if (ClassUtils.isAssignable(varargsParamType, suppliedArg.getType())) {
                     if (match != ReflectionHelper.ArgumentsMatchKind.REQUIRES_CONVERSION) {
                        match = ReflectionHelper.ArgumentsMatchKind.CLOSE;
                     }
                  } else if (typeConverter.canConvert(suppliedArg, TypeDescriptor.valueOf(varargsParamType))) {
                     match = ReflectionHelper.ArgumentsMatchKind.REQUIRES_CONVERSION;
                  } else {
                     match = null;
                  }
               }
            }
         }

         return match != null ? new ArgumentsMatchInfo(match) : null;
      }
   }

   public static boolean convertAllArguments(TypeConverter converter, Object[] arguments, Method method) throws SpelEvaluationException {
      Integer varargsPosition = method.isVarArgs() ? method.getParameterCount() - 1 : null;
      return convertArguments(converter, arguments, method, varargsPosition);
   }

   static boolean convertArguments(TypeConverter converter, Object[] arguments, Executable executable, @Nullable Integer varargsPosition) throws EvaluationException {
      boolean conversionOccurred = false;
      int i;
      TypeDescriptor targetType;
      Object argument;
      if (varargsPosition == null) {
         for(i = 0; i < arguments.length; ++i) {
            targetType = new TypeDescriptor(MethodParameter.forExecutable(executable, i));
            argument = arguments[i];
            arguments[i] = converter.convertValue(argument, TypeDescriptor.forObject(argument), targetType);
            conversionOccurred |= argument != arguments[i];
         }
      } else {
         for(i = 0; i < varargsPosition; ++i) {
            targetType = new TypeDescriptor(MethodParameter.forExecutable(executable, i));
            argument = arguments[i];
            arguments[i] = converter.convertValue(argument, TypeDescriptor.forObject(argument), targetType);
            conversionOccurred |= argument != arguments[i];
         }

         MethodParameter methodParam = MethodParameter.forExecutable(executable, varargsPosition);
         if (varargsPosition == arguments.length - 1) {
            targetType = new TypeDescriptor(methodParam);
            argument = arguments[varargsPosition];
            TypeDescriptor sourceType = TypeDescriptor.forObject(argument);
            arguments[varargsPosition] = converter.convertValue(argument, sourceType, targetType);
            if (argument != arguments[varargsPosition] && !isFirstEntryInArray(argument, arguments[varargsPosition])) {
               conversionOccurred = true;
            }
         } else {
            targetType = (new TypeDescriptor(methodParam)).getElementTypeDescriptor();
            Assert.state(targetType != null, "No element type");

            for(int i = varargsPosition; i < arguments.length; ++i) {
               Object argument = arguments[i];
               arguments[i] = converter.convertValue(argument, TypeDescriptor.forObject(argument), targetType);
               conversionOccurred |= argument != arguments[i];
            }
         }
      }

      return conversionOccurred;
   }

   private static boolean isFirstEntryInArray(Object value, @Nullable Object possibleArray) {
      if (possibleArray == null) {
         return false;
      } else {
         Class type = possibleArray.getClass();
         if (type.isArray() && Array.getLength(possibleArray) != 0 && ClassUtils.isAssignableValue(type.getComponentType(), value)) {
            Object arrayValue = Array.get(possibleArray, 0);
            return type.getComponentType().isPrimitive() ? arrayValue.equals(value) : arrayValue == value;
         } else {
            return false;
         }
      }
   }

   public static Object[] setupArgumentsForVarargsInvocation(Class[] requiredParameterTypes, Object... args) {
      int parameterCount = requiredParameterTypes.length;
      int argumentCount = args.length;
      if (parameterCount == args.length && requiredParameterTypes[parameterCount - 1] == (args[argumentCount - 1] != null ? args[argumentCount - 1].getClass() : null)) {
         return args;
      } else {
         int arraySize = 0;
         if (argumentCount >= parameterCount) {
            arraySize = argumentCount - (parameterCount - 1);
         }

         Object[] newArgs = new Object[parameterCount];
         System.arraycopy(args, 0, newArgs, 0, newArgs.length - 1);
         Class componentType = requiredParameterTypes[parameterCount - 1].getComponentType();
         Object repackagedArgs = Array.newInstance(componentType, arraySize);

         for(int i = 0; i < arraySize; ++i) {
            Array.set(repackagedArgs, i, args[parameterCount - 1 + i]);
         }

         newArgs[newArgs.length - 1] = repackagedArgs;
         return newArgs;
      }
   }

   static class ArgumentsMatchInfo {
      private final ArgumentsMatchKind kind;

      ArgumentsMatchInfo(ArgumentsMatchKind kind) {
         this.kind = kind;
      }

      public boolean isExactMatch() {
         return this.kind == ReflectionHelper.ArgumentsMatchKind.EXACT;
      }

      public boolean isCloseMatch() {
         return this.kind == ReflectionHelper.ArgumentsMatchKind.CLOSE;
      }

      public boolean isMatchRequiringConversion() {
         return this.kind == ReflectionHelper.ArgumentsMatchKind.REQUIRES_CONVERSION;
      }

      public String toString() {
         return "ArgumentMatchInfo: " + this.kind;
      }
   }

   static enum ArgumentsMatchKind {
      EXACT,
      CLOSE,
      REQUIRES_CONVERSION;
   }
}
