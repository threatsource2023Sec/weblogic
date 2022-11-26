package com.bea.core.repackaged.aspectj.weaver.internal.tools;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
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
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionShadow;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionWorld;
import com.bea.core.repackaged.aspectj.weaver.reflect.ShadowMatchImpl;
import com.bea.core.repackaged.aspectj.weaver.tools.DefaultMatchingContext;
import com.bea.core.repackaged.aspectj.weaver.tools.MatchingContext;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutExpression;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutParameter;
import com.bea.core.repackaged.aspectj.weaver.tools.ShadowMatch;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class PointcutExpressionImpl implements PointcutExpression {
   private static final boolean MATCH_INFO = false;
   private World world;
   private Pointcut pointcut;
   private String expression;
   private PointcutParameter[] parameters;
   private MatchingContext matchContext = new DefaultMatchingContext();

   public PointcutExpressionImpl(Pointcut pointcut, String expression, PointcutParameter[] params, World inWorld) {
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
      if (matchType.isMissing() && this.world instanceof ReflectionWorld) {
         matchType = ((ReflectionWorld)this.world).resolveUsingClass(aClass);
      }

      ReflectionFastMatchInfo info = new ReflectionFastMatchInfo(matchType, (Shadow.Kind)null, this.matchContext, this.world);
      boolean couldMatch = this.pointcut.fastMatch(info).maybeTrue();
      return couldMatch;
   }

   public boolean mayNeedDynamicTest() {
      HasPossibleDynamicContentVisitor visitor = new HasPossibleDynamicContentVisitor();
      this.pointcut.traverse(visitor, (Object)null);
      return visitor.hasDynamicContent();
   }

   private ExposedState getExposedState() {
      return new ExposedState(this.parameters.length);
   }

   public ShadowMatch matchesMethodExecution(Method aMethod) {
      ShadowMatch match = this.matchesExecution(aMethod);
      return match;
   }

   public ShadowMatch matchesConstructorExecution(Constructor aConstructor) {
      ShadowMatch match = this.matchesExecution(aConstructor);
      return match;
   }

   private ShadowMatch matchesExecution(Member aMember) {
      Shadow s = ReflectionShadow.makeExecutionShadow(this.world, aMember, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aMember);
      sm.setWithinCode((Member)null);
      sm.setWithinType(aMember.getDeclaringClass());
      return sm;
   }

   public ShadowMatch matchesStaticInitialization(Class aClass) {
      Shadow s = ReflectionShadow.makeStaticInitializationShadow(this.world, aClass, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject((Member)null);
      sm.setWithinCode((Member)null);
      sm.setWithinType(aClass);
      return sm;
   }

   public ShadowMatch matchesAdviceExecution(Method aMethod) {
      Shadow s = ReflectionShadow.makeAdviceExecutionShadow(this.world, aMethod, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aMethod);
      sm.setWithinCode((Member)null);
      sm.setWithinType(aMethod.getDeclaringClass());
      return sm;
   }

   public ShadowMatch matchesInitialization(Constructor aConstructor) {
      Shadow s = ReflectionShadow.makeInitializationShadow(this.world, aConstructor, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aConstructor);
      sm.setWithinCode((Member)null);
      sm.setWithinType(aConstructor.getDeclaringClass());
      return sm;
   }

   public ShadowMatch matchesPreInitialization(Constructor aConstructor) {
      Shadow s = ReflectionShadow.makePreInitializationShadow(this.world, aConstructor, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aConstructor);
      sm.setWithinCode((Member)null);
      sm.setWithinType(aConstructor.getDeclaringClass());
      return sm;
   }

   public ShadowMatch matchesMethodCall(Method aMethod, Member withinCode) {
      Shadow s = ReflectionShadow.makeCallShadow(this.world, aMethod, (Member)withinCode, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aMethod);
      sm.setWithinCode(withinCode);
      sm.setWithinType(withinCode.getDeclaringClass());
      return sm;
   }

   public ShadowMatch matchesMethodCall(Method aMethod, Class callerType) {
      Shadow s = ReflectionShadow.makeCallShadow(this.world, aMethod, (Class)callerType, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aMethod);
      sm.setWithinCode((Member)null);
      sm.setWithinType(callerType);
      return sm;
   }

   public ShadowMatch matchesConstructorCall(Constructor aConstructor, Class callerType) {
      Shadow s = ReflectionShadow.makeCallShadow(this.world, aConstructor, (Class)callerType, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aConstructor);
      sm.setWithinCode((Member)null);
      sm.setWithinType(callerType);
      return sm;
   }

   public ShadowMatch matchesConstructorCall(Constructor aConstructor, Member withinCode) {
      Shadow s = ReflectionShadow.makeCallShadow(this.world, aConstructor, (Member)withinCode, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aConstructor);
      sm.setWithinCode(withinCode);
      sm.setWithinType(withinCode.getDeclaringClass());
      return sm;
   }

   public ShadowMatch matchesHandler(Class exceptionType, Class handlingType) {
      Shadow s = ReflectionShadow.makeHandlerShadow(this.world, exceptionType, handlingType, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject((Member)null);
      sm.setWithinCode((Member)null);
      sm.setWithinType(handlingType);
      return sm;
   }

   public ShadowMatch matchesHandler(Class exceptionType, Member withinCode) {
      Shadow s = ReflectionShadow.makeHandlerShadow(this.world, exceptionType, withinCode, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject((Member)null);
      sm.setWithinCode(withinCode);
      sm.setWithinType(withinCode.getDeclaringClass());
      return sm;
   }

   public ShadowMatch matchesFieldGet(Field aField, Class withinType) {
      Shadow s = ReflectionShadow.makeFieldGetShadow(this.world, aField, withinType, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aField);
      sm.setWithinCode((Member)null);
      sm.setWithinType(withinType);
      return sm;
   }

   public ShadowMatch matchesFieldGet(Field aField, Member withinCode) {
      Shadow s = ReflectionShadow.makeFieldGetShadow(this.world, aField, withinCode, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aField);
      sm.setWithinCode(withinCode);
      sm.setWithinType(withinCode.getDeclaringClass());
      return sm;
   }

   public ShadowMatch matchesFieldSet(Field aField, Class withinType) {
      Shadow s = ReflectionShadow.makeFieldSetShadow(this.world, aField, withinType, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aField);
      sm.setWithinCode((Member)null);
      sm.setWithinType(withinType);
      return sm;
   }

   public ShadowMatch matchesFieldSet(Field aField, Member withinCode) {
      Shadow s = ReflectionShadow.makeFieldSetShadow(this.world, aField, withinCode, this.matchContext);
      ShadowMatchImpl sm = this.getShadowMatch(s);
      sm.setSubject(aField);
      sm.setWithinCode(withinCode);
      sm.setWithinType(withinCode.getDeclaringClass());
      return sm;
   }

   private ShadowMatchImpl getShadowMatch(Shadow forShadow) {
      FuzzyBoolean match = this.pointcut.match(forShadow);
      Test residueTest = Literal.TRUE;
      ExposedState state = this.getExposedState();
      if (match.maybeTrue()) {
         residueTest = this.pointcut.findResidue(forShadow, state);
      }

      ShadowMatchImpl sm = new ShadowMatchImpl(match, (Test)residueTest, state, this.parameters);
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
