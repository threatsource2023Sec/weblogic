package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.And;
import com.bea.core.repackaged.aspectj.weaver.ast.Call;
import com.bea.core.repackaged.aspectj.weaver.ast.FieldGetCall;
import com.bea.core.repackaged.aspectj.weaver.ast.HasAnnotation;
import com.bea.core.repackaged.aspectj.weaver.ast.ITestVisitor;
import com.bea.core.repackaged.aspectj.weaver.ast.Instanceof;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Not;
import com.bea.core.repackaged.aspectj.weaver.ast.Or;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.internal.tools.MatchingContextBasedTest;
import com.bea.core.repackaged.aspectj.weaver.patterns.ExposedState;
import com.bea.core.repackaged.aspectj.weaver.tools.DefaultMatchingContext;
import com.bea.core.repackaged.aspectj.weaver.tools.JoinPointMatch;
import com.bea.core.repackaged.aspectj.weaver.tools.MatchingContext;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutParameter;
import com.bea.core.repackaged.aspectj.weaver.tools.ShadowMatch;

public class StandardShadowMatchImpl implements ShadowMatch {
   private FuzzyBoolean match;
   private ExposedState state;
   private Test residualTest;
   private PointcutParameter[] params;
   private ResolvedMember withinCode;
   private ResolvedMember subject;
   private ResolvedType withinType;
   private MatchingContext matchContext = new DefaultMatchingContext();

   public StandardShadowMatchImpl(FuzzyBoolean match, Test test, ExposedState state, PointcutParameter[] params) {
      this.match = match;
      this.residualTest = test;
      this.state = state;
      this.params = params;
   }

   public void setWithinCode(ResolvedMember aMember) {
      this.withinCode = aMember;
   }

   public void setSubject(ResolvedMember aMember) {
      this.subject = aMember;
   }

   public void setWithinType(ResolvedType aClass) {
      this.withinType = aClass;
   }

   public boolean alwaysMatches() {
      return this.match.alwaysTrue();
   }

   public boolean maybeMatches() {
      return this.match.maybeTrue();
   }

   public boolean neverMatches() {
      return this.match.alwaysFalse();
   }

   public JoinPointMatch matchesJoinPoint(Object thisObject, Object targetObject, Object[] args) {
      if (this.neverMatches()) {
         return JoinPointMatchImpl.NO_MATCH;
      } else {
         return (JoinPointMatch)((new RuntimeTestEvaluator(this.residualTest, thisObject, targetObject, args, this.matchContext)).matches() ? new JoinPointMatchImpl(this.getPointcutParameters(thisObject, targetObject, args)) : JoinPointMatchImpl.NO_MATCH);
      }
   }

   public void setMatchingContext(MatchingContext aMatchContext) {
      this.matchContext = aMatchContext;
   }

   private PointcutParameter[] getPointcutParameters(Object thisObject, Object targetObject, Object[] args) {
      return null;
   }

   private static class RuntimeTestEvaluator implements ITestVisitor {
      private boolean matches = true;
      private final Test test;
      private final Object thisObject;
      private final Object targetObject;
      private final Object[] args;
      private final MatchingContext matchContext;

      public RuntimeTestEvaluator(Test aTest, Object thisObject, Object targetObject, Object[] args, MatchingContext context) {
         this.test = aTest;
         this.thisObject = thisObject;
         this.targetObject = targetObject;
         this.args = args;
         this.matchContext = context;
      }

      public boolean matches() {
         this.test.accept(this);
         return this.matches;
      }

      public void visit(And e) {
         boolean leftMatches = (new RuntimeTestEvaluator(e.getLeft(), this.thisObject, this.targetObject, this.args, this.matchContext)).matches();
         if (!leftMatches) {
            this.matches = false;
         } else {
            this.matches = (new RuntimeTestEvaluator(e.getRight(), this.thisObject, this.targetObject, this.args, this.matchContext)).matches();
         }

      }

      public void visit(Instanceof i) {
         ReflectionVar v = (ReflectionVar)i.getVar();
         Object value = v.getBindingAtJoinPoint(this.thisObject, this.targetObject, this.args);
         World world = v.getType().getWorld();
         ResolvedType desiredType = i.getType().resolve(world);
         ResolvedType actualType = world.resolve(value.getClass().getName());
         this.matches = desiredType.isAssignableFrom(actualType);
      }

      public void visit(MatchingContextBasedTest matchingContextTest) {
         this.matches = matchingContextTest.matches(this.matchContext);
      }

      public void visit(Not not) {
         this.matches = !(new RuntimeTestEvaluator(not.getBody(), this.thisObject, this.targetObject, this.args, this.matchContext)).matches();
      }

      public void visit(Or or) {
         boolean leftMatches = (new RuntimeTestEvaluator(or.getLeft(), this.thisObject, this.targetObject, this.args, this.matchContext)).matches();
         if (leftMatches) {
            this.matches = true;
         } else {
            this.matches = (new RuntimeTestEvaluator(or.getRight(), this.thisObject, this.targetObject, this.args, this.matchContext)).matches();
         }

      }

      public void visit(Literal literal) {
         if (literal == Literal.FALSE) {
            this.matches = false;
         } else {
            this.matches = true;
         }

      }

      public void visit(Call call) {
         throw new UnsupportedOperationException("Can't evaluate call test at runtime");
      }

      public void visit(FieldGetCall fieldGetCall) {
         throw new UnsupportedOperationException("Can't evaluate fieldGetCall test at runtime");
      }

      public void visit(HasAnnotation hasAnnotation) {
         ReflectionVar v = (ReflectionVar)hasAnnotation.getVar();
         Object value = v.getBindingAtJoinPoint(this.thisObject, this.targetObject, this.args);
         World world = v.getType().getWorld();
         ResolvedType actualVarType = world.resolve(value.getClass().getName());
         ResolvedType requiredAnnotationType = hasAnnotation.getAnnotationType().resolve(world);
         this.matches = actualVarType.hasAnnotation(requiredAnnotationType);
      }
   }
}
