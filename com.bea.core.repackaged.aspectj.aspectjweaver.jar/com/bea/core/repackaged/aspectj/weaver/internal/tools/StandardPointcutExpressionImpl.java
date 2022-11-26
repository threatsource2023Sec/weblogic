package com.bea.core.repackaged.aspectj.weaver.internal.tools;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.patterns.AbstractPatternNodeVisitor;
import com.bea.core.repackaged.aspectj.weaver.patterns.AnnotationPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.ArgsAnnotationPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.ArgsPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.CflowPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.ExposedState;
import com.bea.core.repackaged.aspectj.weaver.patterns.IfPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.NotAnnotationTypePattern;
import com.bea.core.repackaged.aspectj.weaver.patterns.NotPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.ThisOrTargetPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.WithinAnnotationPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.WithinCodeAnnotationPointcut;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionFastMatchInfo;
import com.bea.core.repackaged.aspectj.weaver.reflect.StandardShadow;
import com.bea.core.repackaged.aspectj.weaver.reflect.StandardShadowMatchImpl;
import com.bea.core.repackaged.aspectj.weaver.tools.DefaultMatchingContext;
import com.bea.core.repackaged.aspectj.weaver.tools.MatchingContext;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutParameter;
import com.bea.core.repackaged.aspectj.weaver.tools.ShadowMatch;
import com.bea.core.repackaged.aspectj.weaver.tools.StandardPointcutExpression;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;

public class StandardPointcutExpressionImpl implements StandardPointcutExpression {
   private World world;
   private Pointcut pointcut;
   private String expression;
   private PointcutParameter[] parameters;
   private MatchingContext matchContext = new DefaultMatchingContext();

   public StandardPointcutExpressionImpl(Pointcut pointcut, String expression, PointcutParameter[] params, World inWorld) {
      this.pointcut = pointcut;
      this.expression = expression;
      this.world = inWorld;
      this.parameters = params;
      if (this.parameters == null) {
         this.parameters = new PointcutParameter[0];
      }

   }

   public Pointcut getUnderlyingPointcut() {
      return this.pointcut;
   }

   public void setMatchingContext(MatchingContext aMatchContext) {
      this.matchContext = aMatchContext;
   }

   public boolean couldMatchJoinPointsInType(Class aClass) {
      ResolvedType matchType = this.world.resolve(aClass.getName());
      ReflectionFastMatchInfo info = new ReflectionFastMatchInfo(matchType, (Shadow.Kind)null, this.matchContext, this.world);
      return this.pointcut.fastMatch(info).maybeTrue();
   }

   public boolean mayNeedDynamicTest() {
      HasPossibleDynamicContentVisitor visitor = new HasPossibleDynamicContentVisitor();
      this.pointcut.traverse(visitor, (Object)null);
      return visitor.hasDynamicContent();
   }

   private ExposedState getExposedState() {
      return new ExposedState(this.parameters.length);
   }

   public ShadowMatch matchesMethodExecution(ResolvedMember aMethod) {
      return this.matchesExecution(aMethod);
   }

   public ShadowMatch matchesConstructorExecution(Constructor aConstructor) {
      return null;
   }

   private ShadowMatch matchesExecution(ResolvedMember aMember) {
      Shadow s = StandardShadow.makeExecutionShadow(this.world, aMember, this.matchContext);
      StandardShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aMember);
      sm.setWithinCode((ResolvedMember)null);
      sm.setWithinType((ResolvedType)aMember.getDeclaringType());
      return sm;
   }

   public ShadowMatch matchesStaticInitialization(ResolvedType aType) {
      Shadow s = StandardShadow.makeStaticInitializationShadow(this.world, aType, this.matchContext);
      StandardShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject((ResolvedMember)null);
      sm.setWithinCode((ResolvedMember)null);
      sm.setWithinType(aType);
      return sm;
   }

   public ShadowMatch matchesMethodCall(ResolvedMember aMethod, ResolvedMember withinCode) {
      Shadow s = StandardShadow.makeCallShadow(this.world, aMethod, withinCode, this.matchContext);
      StandardShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aMethod);
      sm.setWithinCode(withinCode);
      sm.setWithinType((ResolvedType)withinCode.getDeclaringType());
      return sm;
   }

   private StandardShadowMatchImpl getShadowMatch(Shadow forShadow) {
      FuzzyBoolean match = this.pointcut.match(forShadow);
      Test residueTest = Literal.TRUE;
      ExposedState state = this.getExposedState();
      if (match.maybeTrue()) {
         residueTest = this.pointcut.findResidue(forShadow, state);
      }

      StandardShadowMatchImpl sm = new StandardShadowMatchImpl(match, (Test)residueTest, state, this.parameters);
      sm.setMatchingContext(this.matchContext);
      return sm;
   }

   public String getPointcutExpression() {
      return this.expression;
   }

   public static class Handler implements Member {
      private Class decClass;
      private Class exType;

      public Handler(Class decClass, Class exType) {
         this.decClass = decClass;
         this.exType = exType;
      }

      public int getModifiers() {
         return 0;
      }

      public Class getDeclaringClass() {
         return this.decClass;
      }

      public String getName() {
         return null;
      }

      public Class getHandledExceptionType() {
         return this.exType;
      }

      public boolean isSynthetic() {
         return false;
      }
   }

   private static class HasPossibleDynamicContentVisitor extends AbstractPatternNodeVisitor {
      private boolean hasDynamicContent;

      private HasPossibleDynamicContentVisitor() {
         this.hasDynamicContent = false;
      }

      public boolean hasDynamicContent() {
         return this.hasDynamicContent;
      }

      public Object visit(WithinAnnotationPointcut node, Object data) {
         this.hasDynamicContent = true;
         return null;
      }

      public Object visit(WithinCodeAnnotationPointcut node, Object data) {
         this.hasDynamicContent = true;
         return null;
      }

      public Object visit(AnnotationPointcut node, Object data) {
         this.hasDynamicContent = true;
         return null;
      }

      public Object visit(ArgsAnnotationPointcut node, Object data) {
         this.hasDynamicContent = true;
         return null;
      }

      public Object visit(ArgsPointcut node, Object data) {
         this.hasDynamicContent = true;
         return null;
      }

      public Object visit(CflowPointcut node, Object data) {
         this.hasDynamicContent = true;
         return null;
      }

      public Object visit(IfPointcut node, Object data) {
         this.hasDynamicContent = true;
         return null;
      }

      public Object visit(NotAnnotationTypePattern node, Object data) {
         return node.getNegatedPattern().accept(this, data);
      }

      public Object visit(NotPointcut node, Object data) {
         return node.getNegatedPointcut().accept(this, data);
      }

      public Object visit(ThisOrTargetAnnotationPointcut node, Object data) {
         this.hasDynamicContent = true;
         return null;
      }

      public Object visit(ThisOrTargetPointcut node, Object data) {
         this.hasDynamicContent = true;
         return null;
      }

      // $FF: synthetic method
      HasPossibleDynamicContentVisitor(Object x0) {
         this();
      }
   }
}
