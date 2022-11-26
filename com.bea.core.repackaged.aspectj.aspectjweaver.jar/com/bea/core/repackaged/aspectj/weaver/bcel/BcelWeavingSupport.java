package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.weaver.Advice;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.ConcreteTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.IWeavingSupport;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ast.Var;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;

public class BcelWeavingSupport implements IWeavingSupport {
   public Advice createAdviceMunger(AjAttribute.AdviceAttribute attribute, Pointcut pointcut, Member signature, ResolvedType concreteAspect) {
      return new BcelAdvice(attribute, pointcut, signature, concreteAspect);
   }

   public ConcreteTypeMunger makeCflowStackFieldAdder(ResolvedMember cflowField) {
      return new BcelCflowStackFieldAdder(cflowField);
   }

   public ConcreteTypeMunger makeCflowCounterFieldAdder(ResolvedMember cflowField) {
      return new BcelCflowCounterFieldAdder(cflowField);
   }

   public ConcreteTypeMunger makePerClauseAspect(ResolvedType aspect, PerClause.Kind kind) {
      return new BcelPerClauseAspectAdder(aspect, kind);
   }

   public Var makeCflowAccessVar(ResolvedType formalType, Member cflowField, int arrayIndex) {
      return new BcelCflowAccessVar(formalType, cflowField, arrayIndex);
   }

   public ConcreteTypeMunger concreteTypeMunger(ResolvedTypeMunger munger, ResolvedType aspectType) {
      return new BcelTypeMunger(munger, aspectType);
   }

   public ConcreteTypeMunger createAccessForInlineMunger(ResolvedType aspect) {
      return new BcelAccessForInlineMunger(aspect);
   }
}
