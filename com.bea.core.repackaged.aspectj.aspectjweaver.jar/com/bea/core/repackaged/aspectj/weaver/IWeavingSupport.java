package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.weaver.ast.Var;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;

public interface IWeavingSupport {
   Advice createAdviceMunger(AjAttribute.AdviceAttribute var1, Pointcut var2, Member var3, ResolvedType var4);

   ConcreteTypeMunger makeCflowStackFieldAdder(ResolvedMember var1);

   ConcreteTypeMunger makeCflowCounterFieldAdder(ResolvedMember var1);

   ConcreteTypeMunger makePerClauseAspect(ResolvedType var1, PerClause.Kind var2);

   ConcreteTypeMunger concreteTypeMunger(ResolvedTypeMunger var1, ResolvedType var2);

   ConcreteTypeMunger createAccessForInlineMunger(ResolvedType var1);

   Var makeCflowAccessVar(ResolvedType var1, Member var2, int var3);
}
