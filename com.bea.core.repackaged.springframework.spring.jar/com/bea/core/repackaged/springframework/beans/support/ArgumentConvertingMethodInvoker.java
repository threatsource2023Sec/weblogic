package com.bea.core.repackaged.springframework.beans.support;

import com.bea.core.repackaged.springframework.beans.PropertyEditorRegistry;
import com.bea.core.repackaged.springframework.beans.SimpleTypeConverter;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.TypeMismatchException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.MethodInvoker;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.beans.PropertyEditor;
import java.lang.reflect.Method;

public class ArgumentConvertingMethodInvoker extends MethodInvoker {
   @Nullable
   private TypeConverter typeConverter;
   private boolean useDefaultConverter = true;

   public void setTypeConverter(@Nullable TypeConverter typeConverter) {
      this.typeConverter = typeConverter;
      this.useDefaultConverter = typeConverter == null;
   }

   @Nullable
   public TypeConverter getTypeConverter() {
      if (this.typeConverter == null && this.useDefaultConverter) {
         this.typeConverter = this.getDefaultTypeConverter();
      }

      return this.typeConverter;
   }

   protected TypeConverter getDefaultTypeConverter() {
      return new SimpleTypeConverter();
   }

   public void registerCustomEditor(Class requiredType, PropertyEditor propertyEditor) {
      TypeConverter converter = this.getTypeConverter();
      if (!(converter instanceof PropertyEditorRegistry)) {
         throw new IllegalStateException("TypeConverter does not implement PropertyEditorRegistry interface: " + converter);
      } else {
         ((PropertyEditorRegistry)converter).registerCustomEditor(requiredType, propertyEditor);
      }
   }

   protected Method findMatchingMethod() {
      Method matchingMethod = super.findMatchingMethod();
      if (matchingMethod == null) {
         matchingMethod = this.doFindMatchingMethod(this.getArguments());
      }

      if (matchingMethod == null) {
         matchingMethod = this.doFindMatchingMethod(new Object[]{this.getArguments()});
      }

      return matchingMethod;
   }

   @Nullable
   protected Method doFindMatchingMethod(Object[] arguments) {
      TypeConverter converter = this.getTypeConverter();
      if (converter != null) {
         String targetMethod = this.getTargetMethod();
         Method matchingMethod = null;
         int argCount = arguments.length;
         Class targetClass = this.getTargetClass();
         Assert.state(targetClass != null, "No target class set");
         Method[] candidates = ReflectionUtils.getAllDeclaredMethods(targetClass);
         int minTypeDiffWeight = Integer.MAX_VALUE;
         Object[] argumentsToUse = null;
         Method[] var10 = candidates;
         int var11 = candidates.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            Method candidate = var10[var12];
            if (candidate.getName().equals(targetMethod)) {
               Class[] paramTypes = candidate.getParameterTypes();
               if (paramTypes.length == argCount) {
                  Object[] convertedArguments = new Object[argCount];
                  boolean match = true;

                  int typeDiffWeight;
                  for(typeDiffWeight = 0; typeDiffWeight < argCount && match; ++typeDiffWeight) {
                     try {
                        convertedArguments[typeDiffWeight] = converter.convertIfNecessary(arguments[typeDiffWeight], paramTypes[typeDiffWeight]);
                     } catch (TypeMismatchException var19) {
                        match = false;
                     }
                  }

                  if (match) {
                     typeDiffWeight = getTypeDifferenceWeight(paramTypes, convertedArguments);
                     if (typeDiffWeight < minTypeDiffWeight) {
                        minTypeDiffWeight = typeDiffWeight;
                        matchingMethod = candidate;
                        argumentsToUse = convertedArguments;
                     }
                  }
               }
            }
         }

         if (matchingMethod != null) {
            this.setArguments(argumentsToUse);
            return matchingMethod;
         }
      }

      return null;
   }
}
