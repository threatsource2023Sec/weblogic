package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.aspectj.lang.JoinPoint;
import com.bea.core.repackaged.aspectj.lang.ProceedingJoinPoint;
import com.bea.core.repackaged.aspectj.weaver.tools.JoinPointMatch;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutParameter;
import com.bea.core.repackaged.springframework.aop.AopInvocationException;
import com.bea.core.repackaged.springframework.aop.MethodMatcher;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.ProxyMethodInvocation;
import com.bea.core.repackaged.springframework.aop.interceptor.ExposeInvocationInterceptor;
import com.bea.core.repackaged.springframework.aop.support.ComposablePointcut;
import com.bea.core.repackaged.springframework.aop.support.MethodMatchers;
import com.bea.core.repackaged.springframework.aop.support.StaticMethodMatcher;
import com.bea.core.repackaged.springframework.core.DefaultParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.core.ParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAspectJAdvice implements Advice, AspectJPrecedenceInformation, Serializable {
   protected static final String JOIN_POINT_KEY = JoinPoint.class.getName();
   private final Class declaringClass;
   private final String methodName;
   private final Class[] parameterTypes;
   protected transient Method aspectJAdviceMethod;
   private final AspectJExpressionPointcut pointcut;
   private final AspectInstanceFactory aspectInstanceFactory;
   private String aspectName = "";
   private int declarationOrder;
   @Nullable
   private String[] argumentNames;
   @Nullable
   private String throwingName;
   @Nullable
   private String returningName;
   private Class discoveredReturningType = Object.class;
   private Class discoveredThrowingType = Object.class;
   private int joinPointArgumentIndex = -1;
   private int joinPointStaticPartArgumentIndex = -1;
   @Nullable
   private Map argumentBindings;
   private boolean argumentsIntrospected = false;
   @Nullable
   private Type discoveredReturningGenericType;

   public static JoinPoint currentJoinPoint() {
      MethodInvocation mi = ExposeInvocationInterceptor.currentInvocation();
      if (!(mi instanceof ProxyMethodInvocation)) {
         throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
      } else {
         ProxyMethodInvocation pmi = (ProxyMethodInvocation)mi;
         JoinPoint jp = (JoinPoint)pmi.getUserAttribute(JOIN_POINT_KEY);
         if (jp == null) {
            jp = new MethodInvocationProceedingJoinPoint(pmi);
            pmi.setUserAttribute(JOIN_POINT_KEY, jp);
         }

         return (JoinPoint)jp;
      }
   }

   public AbstractAspectJAdvice(Method aspectJAdviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory aspectInstanceFactory) {
      Assert.notNull(aspectJAdviceMethod, (String)"Advice method must not be null");
      this.declaringClass = aspectJAdviceMethod.getDeclaringClass();
      this.methodName = aspectJAdviceMethod.getName();
      this.parameterTypes = aspectJAdviceMethod.getParameterTypes();
      this.aspectJAdviceMethod = aspectJAdviceMethod;
      this.pointcut = pointcut;
      this.aspectInstanceFactory = aspectInstanceFactory;
   }

   public final Method getAspectJAdviceMethod() {
      return this.aspectJAdviceMethod;
   }

   public final AspectJExpressionPointcut getPointcut() {
      this.calculateArgumentBindings();
      return this.pointcut;
   }

   public final Pointcut buildSafePointcut() {
      Pointcut pc = this.getPointcut();
      MethodMatcher safeMethodMatcher = MethodMatchers.intersection(new AdviceExcludingMethodMatcher(this.aspectJAdviceMethod), pc.getMethodMatcher());
      return new ComposablePointcut(pc.getClassFilter(), safeMethodMatcher);
   }

   public final AspectInstanceFactory getAspectInstanceFactory() {
      return this.aspectInstanceFactory;
   }

   @Nullable
   public final ClassLoader getAspectClassLoader() {
      return this.aspectInstanceFactory.getAspectClassLoader();
   }

   public int getOrder() {
      return this.aspectInstanceFactory.getOrder();
   }

   public void setAspectName(String name) {
      this.aspectName = name;
   }

   public String getAspectName() {
      return this.aspectName;
   }

   public void setDeclarationOrder(int order) {
      this.declarationOrder = order;
   }

   public int getDeclarationOrder() {
      return this.declarationOrder;
   }

   public void setArgumentNames(String argNames) {
      String[] tokens = StringUtils.commaDelimitedListToStringArray(argNames);
      this.setArgumentNamesFromStringArray(tokens);
   }

   public void setArgumentNamesFromStringArray(String... args) {
      this.argumentNames = new String[args.length];

      for(int i = 0; i < args.length; ++i) {
         this.argumentNames[i] = StringUtils.trimWhitespace(args[i]);
         if (!this.isVariableName(this.argumentNames[i])) {
            throw new IllegalArgumentException("'argumentNames' property of AbstractAspectJAdvice contains an argument name '" + this.argumentNames[i] + "' that is not a valid Java identifier");
         }
      }

      if (this.argumentNames != null && this.aspectJAdviceMethod.getParameterCount() == this.argumentNames.length + 1) {
         Class firstArgType = this.aspectJAdviceMethod.getParameterTypes()[0];
         if (firstArgType == JoinPoint.class || firstArgType == ProceedingJoinPoint.class || firstArgType == JoinPoint.StaticPart.class) {
            String[] oldNames = this.argumentNames;
            this.argumentNames = new String[oldNames.length + 1];
            this.argumentNames[0] = "THIS_JOIN_POINT";
            System.arraycopy(oldNames, 0, this.argumentNames, 1, oldNames.length);
         }
      }

   }

   public void setReturningName(String name) {
      throw new UnsupportedOperationException("Only afterReturning advice can be used to bind a return value");
   }

   protected void setReturningNameNoCheck(String name) {
      if (this.isVariableName(name)) {
         this.returningName = name;
      } else {
         try {
            this.discoveredReturningType = ClassUtils.forName(name, this.getAspectClassLoader());
         } catch (Throwable var3) {
            throw new IllegalArgumentException("Returning name '" + name + "' is neither a valid argument name nor the fully-qualified name of a Java type on the classpath. Root cause: " + var3);
         }
      }

   }

   protected Class getDiscoveredReturningType() {
      return this.discoveredReturningType;
   }

   @Nullable
   protected Type getDiscoveredReturningGenericType() {
      return this.discoveredReturningGenericType;
   }

   public void setThrowingName(String name) {
      throw new UnsupportedOperationException("Only afterThrowing advice can be used to bind a thrown exception");
   }

   protected void setThrowingNameNoCheck(String name) {
      if (this.isVariableName(name)) {
         this.throwingName = name;
      } else {
         try {
            this.discoveredThrowingType = ClassUtils.forName(name, this.getAspectClassLoader());
         } catch (Throwable var3) {
            throw new IllegalArgumentException("Throwing name '" + name + "' is neither a valid argument name nor the fully-qualified name of a Java type on the classpath. Root cause: " + var3);
         }
      }

   }

   protected Class getDiscoveredThrowingType() {
      return this.discoveredThrowingType;
   }

   private boolean isVariableName(String name) {
      char[] chars = name.toCharArray();
      if (!Character.isJavaIdentifierStart(chars[0])) {
         return false;
      } else {
         for(int i = 1; i < chars.length; ++i) {
            if (!Character.isJavaIdentifierPart(chars[i])) {
               return false;
            }
         }

         return true;
      }
   }

   public final synchronized void calculateArgumentBindings() {
      if (!this.argumentsIntrospected && this.parameterTypes.length != 0) {
         int numUnboundArgs = this.parameterTypes.length;
         Class[] parameterTypes = this.aspectJAdviceMethod.getParameterTypes();
         if (this.maybeBindJoinPoint(parameterTypes[0]) || this.maybeBindProceedingJoinPoint(parameterTypes[0]) || this.maybeBindJoinPointStaticPart(parameterTypes[0])) {
            --numUnboundArgs;
         }

         if (numUnboundArgs > 0) {
            this.bindArgumentsByName(numUnboundArgs);
         }

         this.argumentsIntrospected = true;
      }
   }

   private boolean maybeBindJoinPoint(Class candidateParameterType) {
      if (JoinPoint.class == candidateParameterType) {
         this.joinPointArgumentIndex = 0;
         return true;
      } else {
         return false;
      }
   }

   private boolean maybeBindProceedingJoinPoint(Class candidateParameterType) {
      if (ProceedingJoinPoint.class == candidateParameterType) {
         if (!this.supportsProceedingJoinPoint()) {
            throw new IllegalArgumentException("ProceedingJoinPoint is only supported for around advice");
         } else {
            this.joinPointArgumentIndex = 0;
            return true;
         }
      } else {
         return false;
      }
   }

   protected boolean supportsProceedingJoinPoint() {
      return false;
   }

   private boolean maybeBindJoinPointStaticPart(Class candidateParameterType) {
      if (JoinPoint.StaticPart.class == candidateParameterType) {
         this.joinPointStaticPartArgumentIndex = 0;
         return true;
      } else {
         return false;
      }
   }

   private void bindArgumentsByName(int numArgumentsExpectingToBind) {
      if (this.argumentNames == null) {
         this.argumentNames = this.createParameterNameDiscoverer().getParameterNames(this.aspectJAdviceMethod);
      }

      if (this.argumentNames != null) {
         this.bindExplicitArguments(numArgumentsExpectingToBind);
      } else {
         throw new IllegalStateException("Advice method [" + this.aspectJAdviceMethod.getName() + "] requires " + numArgumentsExpectingToBind + " arguments to be bound by name, but the argument names were not specified and could not be discovered.");
      }
   }

   protected ParameterNameDiscoverer createParameterNameDiscoverer() {
      DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
      AspectJAdviceParameterNameDiscoverer adviceParameterNameDiscoverer = new AspectJAdviceParameterNameDiscoverer(this.pointcut.getExpression());
      adviceParameterNameDiscoverer.setReturningName(this.returningName);
      adviceParameterNameDiscoverer.setThrowingName(this.throwingName);
      adviceParameterNameDiscoverer.setRaiseExceptions(true);
      discoverer.addDiscoverer(adviceParameterNameDiscoverer);
      return discoverer;
   }

   private void bindExplicitArguments(int numArgumentsLeftToBind) {
      Assert.state(this.argumentNames != null, "No argument names available");
      this.argumentBindings = new HashMap();
      int numExpectedArgumentNames = this.aspectJAdviceMethod.getParameterCount();
      if (this.argumentNames.length != numExpectedArgumentNames) {
         throw new IllegalStateException("Expecting to find " + numExpectedArgumentNames + " arguments to bind by name in advice, but actually found " + this.argumentNames.length + " arguments.");
      } else {
         int argumentIndexOffset = this.parameterTypes.length - numArgumentsLeftToBind;

         for(int i = argumentIndexOffset; i < this.argumentNames.length; ++i) {
            this.argumentBindings.put(this.argumentNames[i], i);
         }

         Integer index;
         if (this.returningName != null) {
            if (!this.argumentBindings.containsKey(this.returningName)) {
               throw new IllegalStateException("Returning argument name '" + this.returningName + "' was not bound in advice arguments");
            }

            index = (Integer)this.argumentBindings.get(this.returningName);
            this.discoveredReturningType = this.aspectJAdviceMethod.getParameterTypes()[index];
            this.discoveredReturningGenericType = this.aspectJAdviceMethod.getGenericParameterTypes()[index];
         }

         if (this.throwingName != null) {
            if (!this.argumentBindings.containsKey(this.throwingName)) {
               throw new IllegalStateException("Throwing argument name '" + this.throwingName + "' was not bound in advice arguments");
            }

            index = (Integer)this.argumentBindings.get(this.throwingName);
            this.discoveredThrowingType = this.aspectJAdviceMethod.getParameterTypes()[index];
         }

         this.configurePointcutParameters(this.argumentNames, argumentIndexOffset);
      }
   }

   private void configurePointcutParameters(String[] argumentNames, int argumentIndexOffset) {
      int numParametersToRemove = argumentIndexOffset;
      if (this.returningName != null) {
         numParametersToRemove = argumentIndexOffset + 1;
      }

      if (this.throwingName != null) {
         ++numParametersToRemove;
      }

      String[] pointcutParameterNames = new String[argumentNames.length - numParametersToRemove];
      Class[] pointcutParameterTypes = new Class[pointcutParameterNames.length];
      Class[] methodParameterTypes = this.aspectJAdviceMethod.getParameterTypes();
      int index = 0;

      for(int i = 0; i < argumentNames.length; ++i) {
         if (i >= argumentIndexOffset && !argumentNames[i].equals(this.returningName) && !argumentNames[i].equals(this.throwingName)) {
            pointcutParameterNames[index] = argumentNames[i];
            pointcutParameterTypes[index] = methodParameterTypes[i];
            ++index;
         }
      }

      this.pointcut.setParameterNames(pointcutParameterNames);
      this.pointcut.setParameterTypes(pointcutParameterTypes);
   }

   protected Object[] argBinding(JoinPoint jp, @Nullable JoinPointMatch jpMatch, @Nullable Object returnValue, @Nullable Throwable ex) {
      this.calculateArgumentBindings();
      Object[] adviceInvocationArgs = new Object[this.parameterTypes.length];
      int numBound = 0;
      if (this.joinPointArgumentIndex != -1) {
         adviceInvocationArgs[this.joinPointArgumentIndex] = jp;
         ++numBound;
      } else if (this.joinPointStaticPartArgumentIndex != -1) {
         adviceInvocationArgs[this.joinPointStaticPartArgumentIndex] = jp.getStaticPart();
         ++numBound;
      }

      if (!CollectionUtils.isEmpty(this.argumentBindings)) {
         if (jpMatch != null) {
            PointcutParameter[] parameterBindings = jpMatch.getParameterBindings();
            PointcutParameter[] var8 = parameterBindings;
            int var9 = parameterBindings.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               PointcutParameter parameter = var8[var10];
               String name = parameter.getName();
               Integer index = (Integer)this.argumentBindings.get(name);
               adviceInvocationArgs[index] = parameter.getBinding();
               ++numBound;
            }
         }

         Integer index;
         if (this.returningName != null) {
            index = (Integer)this.argumentBindings.get(this.returningName);
            adviceInvocationArgs[index] = returnValue;
            ++numBound;
         }

         if (this.throwingName != null) {
            index = (Integer)this.argumentBindings.get(this.throwingName);
            adviceInvocationArgs[index] = ex;
            ++numBound;
         }
      }

      if (numBound != this.parameterTypes.length) {
         throw new IllegalStateException("Required to bind " + this.parameterTypes.length + " arguments, but only bound " + numBound + " (JoinPointMatch " + (jpMatch == null ? "was NOT" : "WAS") + " bound in invocation)");
      } else {
         return adviceInvocationArgs;
      }
   }

   protected Object invokeAdviceMethod(@Nullable JoinPointMatch jpMatch, @Nullable Object returnValue, @Nullable Throwable ex) throws Throwable {
      return this.invokeAdviceMethodWithGivenArgs(this.argBinding(this.getJoinPoint(), jpMatch, returnValue, ex));
   }

   protected Object invokeAdviceMethod(JoinPoint jp, @Nullable JoinPointMatch jpMatch, @Nullable Object returnValue, @Nullable Throwable t) throws Throwable {
      return this.invokeAdviceMethodWithGivenArgs(this.argBinding(jp, jpMatch, returnValue, t));
   }

   protected Object invokeAdviceMethodWithGivenArgs(Object[] args) throws Throwable {
      Object[] actualArgs = args;
      if (this.aspectJAdviceMethod.getParameterCount() == 0) {
         actualArgs = null;
      }

      try {
         ReflectionUtils.makeAccessible(this.aspectJAdviceMethod);
         return this.aspectJAdviceMethod.invoke(this.aspectInstanceFactory.getAspectInstance(), actualArgs);
      } catch (IllegalArgumentException var4) {
         throw new AopInvocationException("Mismatch on arguments to advice method [" + this.aspectJAdviceMethod + "]; pointcut expression [" + this.pointcut.getPointcutExpression() + "]", var4);
      } catch (InvocationTargetException var5) {
         throw var5.getTargetException();
      }
   }

   protected JoinPoint getJoinPoint() {
      return currentJoinPoint();
   }

   @Nullable
   protected JoinPointMatch getJoinPointMatch() {
      MethodInvocation mi = ExposeInvocationInterceptor.currentInvocation();
      if (!(mi instanceof ProxyMethodInvocation)) {
         throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
      } else {
         return this.getJoinPointMatch((ProxyMethodInvocation)mi);
      }
   }

   @Nullable
   protected JoinPointMatch getJoinPointMatch(ProxyMethodInvocation pmi) {
      String expression = this.pointcut.getExpression();
      return expression != null ? (JoinPointMatch)pmi.getUserAttribute(expression) : null;
   }

   public String toString() {
      return this.getClass().getName() + ": advice method [" + this.aspectJAdviceMethod + "]; aspect name '" + this.aspectName + "'";
   }

   private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
      inputStream.defaultReadObject();

      try {
         this.aspectJAdviceMethod = this.declaringClass.getMethod(this.methodName, this.parameterTypes);
      } catch (NoSuchMethodException var3) {
         throw new IllegalStateException("Failed to find advice method on deserialization", var3);
      }
   }

   private static class AdviceExcludingMethodMatcher extends StaticMethodMatcher {
      private final Method adviceMethod;

      public AdviceExcludingMethodMatcher(Method adviceMethod) {
         this.adviceMethod = adviceMethod;
      }

      public boolean matches(Method method, Class targetClass) {
         return !this.adviceMethod.equals(method);
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof AdviceExcludingMethodMatcher)) {
            return false;
         } else {
            AdviceExcludingMethodMatcher otherMm = (AdviceExcludingMethodMatcher)other;
            return this.adviceMethod.equals(otherMm.adviceMethod);
         }
      }

      public int hashCode() {
         return this.adviceMethod.hashCode();
      }

      public String toString() {
         return this.getClass().getName() + ": " + this.adviceMethod;
      }
   }
}
