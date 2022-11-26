package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.ConstructorExecutor;
import com.bea.core.repackaged.springframework.expression.ConstructorResolver;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypeConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectiveConstructorResolver implements ConstructorResolver {
   @Nullable
   public ConstructorExecutor resolve(EvaluationContext context, String typeName, List argumentTypes) throws AccessException {
      try {
         TypeConverter typeConverter = context.getTypeConverter();
         Class type = context.getTypeLocator().findType(typeName);
         Constructor[] ctors = type.getConstructors();
         Arrays.sort(ctors, (c1, c2) -> {
            int c1pl = c1.getParameterCount();
            int c2pl = c2.getParameterCount();
            return Integer.compare(c1pl, c2pl);
         });
         Constructor closeMatch = null;
         Constructor matchRequiringConversion = null;
         Constructor[] var9 = ctors;
         int var10 = ctors.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            Constructor ctor = var9[var11];
            Class[] paramTypes = ctor.getParameterTypes();
            List paramDescriptors = new ArrayList(paramTypes.length);

            for(int i = 0; i < paramTypes.length; ++i) {
               paramDescriptors.add(new TypeDescriptor(new MethodParameter(ctor, i)));
            }

            ReflectionHelper.ArgumentsMatchInfo matchInfo = null;
            if (ctor.isVarArgs() && argumentTypes.size() >= paramTypes.length - 1) {
               matchInfo = ReflectionHelper.compareArgumentsVarargs(paramDescriptors, argumentTypes, typeConverter);
            } else if (paramTypes.length == argumentTypes.size()) {
               matchInfo = ReflectionHelper.compareArguments(paramDescriptors, argumentTypes, typeConverter);
            }

            if (matchInfo != null) {
               if (matchInfo.isExactMatch()) {
                  return new ReflectiveConstructorExecutor(ctor);
               }

               if (matchInfo.isCloseMatch()) {
                  closeMatch = ctor;
               } else if (matchInfo.isMatchRequiringConversion()) {
                  matchRequiringConversion = ctor;
               }
            }
         }

         if (closeMatch != null) {
            return new ReflectiveConstructorExecutor(closeMatch);
         } else if (matchRequiringConversion != null) {
            return new ReflectiveConstructorExecutor(matchRequiringConversion);
         } else {
            return null;
         }
      } catch (EvaluationException var16) {
         throw new AccessException("Failed to resolve constructor", var16);
      }
   }
}
