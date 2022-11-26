package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.patterns.ExposedState;
import com.bea.core.repackaged.aspectj.weaver.patterns.NamePattern;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionWorld;
import com.bea.core.repackaged.aspectj.weaver.reflect.ShadowMatchImpl;
import com.bea.core.repackaged.aspectj.weaver.tools.ContextBasedMatcher;
import com.bea.core.repackaged.aspectj.weaver.tools.JoinPointMatch;
import com.bea.core.repackaged.aspectj.weaver.tools.MatchingContext;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutDesignatorHandler;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutExpression;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutParameter;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutParser;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutPrimitive;
import com.bea.core.repackaged.aspectj.weaver.tools.ShadowMatch;
import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.IntroductionAwareMethodMatcher;
import com.bea.core.repackaged.springframework.aop.MethodMatcher;
import com.bea.core.repackaged.springframework.aop.ProxyMethodInvocation;
import com.bea.core.repackaged.springframework.aop.framework.autoproxy.ProxyCreationContext;
import com.bea.core.repackaged.springframework.aop.interceptor.ExposeInvocationInterceptor;
import com.bea.core.repackaged.springframework.aop.support.AbstractExpressionPointcut;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AspectJExpressionPointcut extends AbstractExpressionPointcut implements ClassFilter, IntroductionAwareMethodMatcher, BeanFactoryAware {
   private static final Set SUPPORTED_PRIMITIVES = new HashSet();
   private static final Log logger;
   @Nullable
   private Class pointcutDeclarationScope;
   private String[] pointcutParameterNames = new String[0];
   private Class[] pointcutParameterTypes = new Class[0];
   @Nullable
   private BeanFactory beanFactory;
   @Nullable
   private transient ClassLoader pointcutClassLoader;
   @Nullable
   private transient PointcutExpression pointcutExpression;
   private transient Map shadowMatchCache = new ConcurrentHashMap(32);

   public AspectJExpressionPointcut() {
   }

   public AspectJExpressionPointcut(Class declarationScope, String[] paramNames, Class[] paramTypes) {
      this.pointcutDeclarationScope = declarationScope;
      if (paramNames.length != paramTypes.length) {
         throw new IllegalStateException("Number of pointcut parameter names must match number of pointcut parameter types");
      } else {
         this.pointcutParameterNames = paramNames;
         this.pointcutParameterTypes = paramTypes;
      }
   }

   public void setPointcutDeclarationScope(Class pointcutDeclarationScope) {
      this.pointcutDeclarationScope = pointcutDeclarationScope;
   }

   public void setParameterNames(String... names) {
      this.pointcutParameterNames = names;
   }

   public void setParameterTypes(Class... types) {
      this.pointcutParameterTypes = types;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   public ClassFilter getClassFilter() {
      this.obtainPointcutExpression();
      return this;
   }

   public MethodMatcher getMethodMatcher() {
      this.obtainPointcutExpression();
      return this;
   }

   private PointcutExpression obtainPointcutExpression() {
      if (this.getExpression() == null) {
         throw new IllegalStateException("Must set property 'expression' before attempting to match");
      } else {
         if (this.pointcutExpression == null) {
            this.pointcutClassLoader = this.determinePointcutClassLoader();
            this.pointcutExpression = this.buildPointcutExpression(this.pointcutClassLoader);
         }

         return this.pointcutExpression;
      }
   }

   @Nullable
   private ClassLoader determinePointcutClassLoader() {
      if (this.beanFactory instanceof ConfigurableBeanFactory) {
         return ((ConfigurableBeanFactory)this.beanFactory).getBeanClassLoader();
      } else {
         return this.pointcutDeclarationScope != null ? this.pointcutDeclarationScope.getClassLoader() : ClassUtils.getDefaultClassLoader();
      }
   }

   private PointcutExpression buildPointcutExpression(@Nullable ClassLoader classLoader) {
      PointcutParser parser = this.initializePointcutParser(classLoader);
      PointcutParameter[] pointcutParameters = new PointcutParameter[this.pointcutParameterNames.length];

      for(int i = 0; i < pointcutParameters.length; ++i) {
         pointcutParameters[i] = parser.createPointcutParameter(this.pointcutParameterNames[i], this.pointcutParameterTypes[i]);
      }

      return parser.parsePointcutExpression(this.replaceBooleanOperators(this.resolveExpression()), this.pointcutDeclarationScope, pointcutParameters);
   }

   private String resolveExpression() {
      String expression = this.getExpression();
      Assert.state(expression != null, "No expression set");
      return expression;
   }

   private PointcutParser initializePointcutParser(@Nullable ClassLoader classLoader) {
      PointcutParser parser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, classLoader);
      parser.registerPointcutDesignatorHandler(new BeanPointcutDesignatorHandler());
      return parser;
   }

   private String replaceBooleanOperators(String pcExpr) {
      String result = StringUtils.replace(pcExpr, " and ", " && ");
      result = StringUtils.replace(result, " or ", " || ");
      result = StringUtils.replace(result, " not ", " ! ");
      return result;
   }

   public PointcutExpression getPointcutExpression() {
      return this.obtainPointcutExpression();
   }

   public boolean matches(Class targetClass) {
      PointcutExpression pointcutExpression = this.obtainPointcutExpression();

      try {
         try {
            return pointcutExpression.couldMatchJoinPointsInType(targetClass);
         } catch (ReflectionWorld.ReflectionWorldException var5) {
            logger.debug("PointcutExpression matching rejected target class - trying fallback expression", var5);
            PointcutExpression fallbackExpression = this.getFallbackPointcutExpression(targetClass);
            if (fallbackExpression != null) {
               return fallbackExpression.couldMatchJoinPointsInType(targetClass);
            }
         }
      } catch (Throwable var6) {
         logger.debug("PointcutExpression matching rejected target class", var6);
      }

      return false;
   }

   public boolean matches(Method method, Class targetClass, boolean hasIntroductions) {
      this.obtainPointcutExpression();
      ShadowMatch shadowMatch = this.getTargetShadowMatch(method, targetClass);
      if (shadowMatch.alwaysMatches()) {
         return true;
      } else if (shadowMatch.neverMatches()) {
         return false;
      } else if (hasIntroductions) {
         return true;
      } else {
         RuntimeTestWalker walker = this.getRuntimeTestWalker(shadowMatch);
         return !walker.testsSubtypeSensitiveVars() || walker.testTargetInstanceOfResidue(targetClass);
      }
   }

   public boolean matches(Method method, Class targetClass) {
      return this.matches(method, targetClass, false);
   }

   public boolean isRuntime() {
      return this.obtainPointcutExpression().mayNeedDynamicTest();
   }

   public boolean matches(Method method, Class targetClass, Object... args) {
      this.obtainPointcutExpression();
      ShadowMatch shadowMatch = this.getTargetShadowMatch(method, targetClass);
      ProxyMethodInvocation pmi = null;
      Object targetObject = null;
      Object thisObject = null;

      try {
         MethodInvocation mi = ExposeInvocationInterceptor.currentInvocation();
         targetObject = mi.getThis();
         if (!(mi instanceof ProxyMethodInvocation)) {
            throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
         }

         pmi = (ProxyMethodInvocation)mi;
         thisObject = pmi.getProxy();
      } catch (IllegalStateException var11) {
         if (logger.isDebugEnabled()) {
            logger.debug("Could not access current invocation - matching with limited context: " + var11);
         }
      }

      try {
         JoinPointMatch joinPointMatch = shadowMatch.matchesJoinPoint(thisObject, targetObject, args);
         if (pmi != null && thisObject != null) {
            RuntimeTestWalker originalMethodResidueTest = this.getRuntimeTestWalker(this.getShadowMatch(method, method));
            if (!originalMethodResidueTest.testThisInstanceOfResidue(thisObject.getClass())) {
               return false;
            }

            if (joinPointMatch.matches()) {
               this.bindParameters(pmi, joinPointMatch);
            }
         }

         return joinPointMatch.matches();
      } catch (Throwable var10) {
         if (logger.isDebugEnabled()) {
            logger.debug("Failed to evaluate join point for arguments " + Arrays.asList(args) + " - falling back to non-match", var10);
         }

         return false;
      }
   }

   @Nullable
   protected String getCurrentProxiedBeanName() {
      return ProxyCreationContext.getCurrentProxiedBeanName();
   }

   @Nullable
   private PointcutExpression getFallbackPointcutExpression(Class targetClass) {
      try {
         ClassLoader classLoader = targetClass.getClassLoader();
         if (classLoader != null && classLoader != this.pointcutClassLoader) {
            return this.buildPointcutExpression(classLoader);
         }
      } catch (Throwable var3) {
         logger.debug("Failed to create fallback PointcutExpression", var3);
      }

      return null;
   }

   private RuntimeTestWalker getRuntimeTestWalker(ShadowMatch shadowMatch) {
      return shadowMatch instanceof DefensiveShadowMatch ? new RuntimeTestWalker(((DefensiveShadowMatch)shadowMatch).primary) : new RuntimeTestWalker(shadowMatch);
   }

   private void bindParameters(ProxyMethodInvocation invocation, JoinPointMatch jpm) {
      invocation.setUserAttribute(this.resolveExpression(), jpm);
   }

   private ShadowMatch getTargetShadowMatch(Method method, Class targetClass) {
      Method targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
      if (targetMethod.getDeclaringClass().isInterface()) {
         Set ifcs = ClassUtils.getAllInterfacesForClassAsSet(targetClass);
         if (ifcs.size() > 1) {
            try {
               Class compositeInterface = ClassUtils.createCompositeInterface(ClassUtils.toClassArray(ifcs), targetClass.getClassLoader());
               targetMethod = ClassUtils.getMostSpecificMethod(targetMethod, compositeInterface);
            } catch (IllegalArgumentException var6) {
            }
         }
      }

      return this.getShadowMatch(targetMethod, method);
   }

   private ShadowMatch getShadowMatch(Method targetMethod, Method originalMethod) {
      ShadowMatch shadowMatch = (ShadowMatch)this.shadowMatchCache.get(targetMethod);
      if (shadowMatch == null) {
         synchronized(this.shadowMatchCache) {
            PointcutExpression fallbackExpression = null;
            shadowMatch = (ShadowMatch)this.shadowMatchCache.get(targetMethod);
            if (shadowMatch == null) {
               Method methodToMatch = targetMethod;

               try {
                  try {
                     shadowMatch = this.obtainPointcutExpression().matchesMethodExecution(methodToMatch);
                  } catch (ReflectionWorld.ReflectionWorldException var13) {
                     try {
                        fallbackExpression = this.getFallbackPointcutExpression(methodToMatch.getDeclaringClass());
                        if (fallbackExpression != null) {
                           shadowMatch = fallbackExpression.matchesMethodExecution(methodToMatch);
                        }
                     } catch (ReflectionWorld.ReflectionWorldException var12) {
                        fallbackExpression = null;
                     }
                  }

                  if (targetMethod != originalMethod && (shadowMatch == null || ((ShadowMatch)shadowMatch).neverMatches() && Proxy.isProxyClass(targetMethod.getDeclaringClass()))) {
                     methodToMatch = originalMethod;

                     try {
                        shadowMatch = this.obtainPointcutExpression().matchesMethodExecution(methodToMatch);
                     } catch (ReflectionWorld.ReflectionWorldException var11) {
                        try {
                           fallbackExpression = this.getFallbackPointcutExpression(methodToMatch.getDeclaringClass());
                           if (fallbackExpression != null) {
                              shadowMatch = fallbackExpression.matchesMethodExecution(methodToMatch);
                           }
                        } catch (ReflectionWorld.ReflectionWorldException var10) {
                           fallbackExpression = null;
                        }
                     }
                  }
               } catch (Throwable var14) {
                  logger.debug("PointcutExpression matching rejected target method", var14);
                  fallbackExpression = null;
               }

               if (shadowMatch == null) {
                  shadowMatch = new ShadowMatchImpl(FuzzyBoolean.NO, (Test)null, (ExposedState)null, (PointcutParameter[])null);
               } else if (((ShadowMatch)shadowMatch).maybeMatches() && fallbackExpression != null) {
                  shadowMatch = new DefensiveShadowMatch((ShadowMatch)shadowMatch, fallbackExpression.matchesMethodExecution(methodToMatch));
               }

               this.shadowMatchCache.put(targetMethod, shadowMatch);
            }
         }
      }

      return (ShadowMatch)shadowMatch;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AspectJExpressionPointcut)) {
         return false;
      } else {
         AspectJExpressionPointcut otherPc = (AspectJExpressionPointcut)other;
         return ObjectUtils.nullSafeEquals(this.getExpression(), otherPc.getExpression()) && ObjectUtils.nullSafeEquals(this.pointcutDeclarationScope, otherPc.pointcutDeclarationScope) && ObjectUtils.nullSafeEquals(this.pointcutParameterNames, otherPc.pointcutParameterNames) && ObjectUtils.nullSafeEquals(this.pointcutParameterTypes, otherPc.pointcutParameterTypes);
      }
   }

   public int hashCode() {
      int hashCode = ObjectUtils.nullSafeHashCode((Object)this.getExpression());
      hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.pointcutDeclarationScope);
      hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode((Object[])this.pointcutParameterNames);
      hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode((Object[])this.pointcutParameterTypes);
      return hashCode;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("AspectJExpressionPointcut: ");
      sb.append("(");

      for(int i = 0; i < this.pointcutParameterTypes.length; ++i) {
         sb.append(this.pointcutParameterTypes[i].getName());
         sb.append(" ");
         sb.append(this.pointcutParameterNames[i]);
         if (i + 1 < this.pointcutParameterTypes.length) {
            sb.append(", ");
         }
      }

      sb.append(")");
      sb.append(" ");
      if (this.getExpression() != null) {
         sb.append(this.getExpression());
      } else {
         sb.append("<pointcut expression not set>");
      }

      return sb.toString();
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject();
      this.shadowMatchCache = new ConcurrentHashMap(32);
   }

   static {
      SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
      SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
      SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
      SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
      SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
      SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
      SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
      SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
      SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
      SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
      logger = LogFactory.getLog(AspectJExpressionPointcut.class);
   }

   private static class DefensiveShadowMatch implements ShadowMatch {
      private final ShadowMatch primary;
      private final ShadowMatch other;

      public DefensiveShadowMatch(ShadowMatch primary, ShadowMatch other) {
         this.primary = primary;
         this.other = other;
      }

      public boolean alwaysMatches() {
         return this.primary.alwaysMatches();
      }

      public boolean maybeMatches() {
         return this.primary.maybeMatches();
      }

      public boolean neverMatches() {
         return this.primary.neverMatches();
      }

      public JoinPointMatch matchesJoinPoint(Object thisObject, Object targetObject, Object[] args) {
         try {
            return this.primary.matchesJoinPoint(thisObject, targetObject, args);
         } catch (ReflectionWorld.ReflectionWorldException var5) {
            return this.other.matchesJoinPoint(thisObject, targetObject, args);
         }
      }

      public void setMatchingContext(MatchingContext aMatchContext) {
         this.primary.setMatchingContext(aMatchContext);
         this.other.setMatchingContext(aMatchContext);
      }
   }

   private class BeanContextMatcher implements ContextBasedMatcher {
      private final NamePattern expressionPattern;

      public BeanContextMatcher(String expression) {
         this.expressionPattern = new NamePattern(expression);
      }

      /** @deprecated */
      @Deprecated
      public boolean couldMatchJoinPointsInType(Class someClass) {
         return this.contextMatch(someClass) == com.bea.core.repackaged.aspectj.weaver.tools.FuzzyBoolean.YES;
      }

      /** @deprecated */
      @Deprecated
      public boolean couldMatchJoinPointsInType(Class someClass, MatchingContext context) {
         return this.contextMatch(someClass) == com.bea.core.repackaged.aspectj.weaver.tools.FuzzyBoolean.YES;
      }

      public boolean matchesDynamically(MatchingContext context) {
         return true;
      }

      public com.bea.core.repackaged.aspectj.weaver.tools.FuzzyBoolean matchesStatically(MatchingContext context) {
         return this.contextMatch((Class)null);
      }

      public boolean mayNeedDynamicTest() {
         return false;
      }

      private com.bea.core.repackaged.aspectj.weaver.tools.FuzzyBoolean contextMatch(@Nullable Class targetType) {
         String advisedBeanName = AspectJExpressionPointcut.this.getCurrentProxiedBeanName();
         if (advisedBeanName == null) {
            return com.bea.core.repackaged.aspectj.weaver.tools.FuzzyBoolean.MAYBE;
         } else if (BeanFactoryUtils.isGeneratedBeanName(advisedBeanName)) {
            return com.bea.core.repackaged.aspectj.weaver.tools.FuzzyBoolean.NO;
         } else if (targetType != null) {
            boolean isFactory = FactoryBean.class.isAssignableFrom(targetType);
            return com.bea.core.repackaged.aspectj.weaver.tools.FuzzyBoolean.fromBoolean(this.matchesBean(isFactory ? "&" + advisedBeanName : advisedBeanName));
         } else {
            return com.bea.core.repackaged.aspectj.weaver.tools.FuzzyBoolean.fromBoolean(this.matchesBean(advisedBeanName) || this.matchesBean("&" + advisedBeanName));
         }
      }

      private boolean matchesBean(String advisedBeanName) {
         return BeanFactoryAnnotationUtils.isQualifierMatch(this.expressionPattern::matches, advisedBeanName, AspectJExpressionPointcut.this.beanFactory);
      }
   }

   private class BeanPointcutDesignatorHandler implements PointcutDesignatorHandler {
      private static final String BEAN_DESIGNATOR_NAME = "bean";

      private BeanPointcutDesignatorHandler() {
      }

      public String getDesignatorName() {
         return "bean";
      }

      public ContextBasedMatcher parse(String expression) {
         return AspectJExpressionPointcut.this.new BeanContextMatcher(expression);
      }

      // $FF: synthetic method
      BeanPointcutDesignatorHandler(Object x1) {
         this();
      }
   }
}
