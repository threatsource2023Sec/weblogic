package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutParameter;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutParser;

public class InternalUseOnlyPointcutParser extends PointcutParser {
   public InternalUseOnlyPointcutParser(ClassLoader classLoader, ReflectionWorld world) {
      this.setClassLoader(classLoader);
      this.setWorld(world);
   }

   public InternalUseOnlyPointcutParser(ClassLoader classLoader) {
      this.setClassLoader(classLoader);
   }

   public Pointcut resolvePointcutExpression(String expression, Class inScope, PointcutParameter[] formalParameters) {
      return super.resolvePointcutExpression(expression, inScope, formalParameters);
   }

   public Pointcut concretizePointcutExpression(Pointcut pc, Class inScope, PointcutParameter[] formalParameters) {
      return super.concretizePointcutExpression(pc, inScope, formalParameters);
   }
}
