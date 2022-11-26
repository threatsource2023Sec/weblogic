package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.ConstructorExecutor;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Constructor;

public class ReflectiveConstructorExecutor implements ConstructorExecutor {
   private final Constructor ctor;
   @Nullable
   private final Integer varargsPosition;

   public ReflectiveConstructorExecutor(Constructor ctor) {
      this.ctor = ctor;
      if (ctor.isVarArgs()) {
         Class[] paramTypes = ctor.getParameterTypes();
         this.varargsPosition = paramTypes.length - 1;
      } else {
         this.varargsPosition = null;
      }

   }

   public TypedValue execute(EvaluationContext context, Object... arguments) throws AccessException {
      try {
         ReflectionHelper.convertArguments(context.getTypeConverter(), arguments, this.ctor, this.varargsPosition);
         if (this.ctor.isVarArgs()) {
            arguments = ReflectionHelper.setupArgumentsForVarargsInvocation(this.ctor.getParameterTypes(), arguments);
         }

         ReflectionUtils.makeAccessible(this.ctor);
         return new TypedValue(this.ctor.newInstance(arguments));
      } catch (Exception var4) {
         throw new AccessException("Problem invoking constructor: " + this.ctor, var4);
      }
   }

   public Constructor getConstructor() {
      return this.ctor;
   }
}
