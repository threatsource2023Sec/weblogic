package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.ast.Var;
import java.util.List;

public abstract class NameBindingPointcut extends Pointcut {
   protected Test exposeStateForVar(Var var, TypePattern type, ExposedState state, World world) {
      if (type instanceof BindingTypePattern) {
         BindingTypePattern b = (BindingTypePattern)type;
         state.set(b.getFormalIndex(), var);
      }

      ResolvedType myType = type.getExactType().resolve(world);
      if (myType.isParameterizedType()) {
         myType = myType.getRawType();
      }

      return Test.makeInstanceof(var, myType.resolve(world));
   }

   public abstract List getBindingTypePatterns();

   public abstract List getBindingAnnotationTypePatterns();
}
