package com.oracle.pitchfork.intercept;

import com.bea.core.repackaged.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import com.oracle.pitchfork.interfaces.intercept.InterceptionMetadataI;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class JeeInterceptorPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {
   private static final long serialVersionUID = 966071528289252134L;
   private final Method targetMethod;
   private final InterceptionMetadataI interceptionMetadata;
   private final Map exclusions;
   private final Set orderedPointcuts;
   private final PointcutMatch isFromOrder;
   private Class beanControlInterface;
   private Set beanControlInterfaceMethods;
   private final boolean forTimeout;
   private final boolean forSelf;

   private JeeInterceptorPointcutAdvisor(Method targetMethod, JeeInterceptorInterceptor advice, InterceptionMetadataI interceptionMetadata, Map exclusion, Set orderedPointcuts, PointcutMatch paramIsFromOrder, boolean paramForTimeout, boolean paramForSelf) {
      super(advice);
      this.targetMethod = targetMethod;
      this.interceptionMetadata = interceptionMetadata;
      this.exclusions = exclusion;
      this.orderedPointcuts = orderedPointcuts;
      this.isFromOrder = paramIsFromOrder;
      this.forTimeout = paramForTimeout;
      this.forSelf = paramForSelf;
   }

   JeeInterceptorPointcutAdvisor(Method targetMethod, JeeInterceptorInterceptor advice, InterceptionMetadataI interceptionMetadata, Map exclusion, Set orderedPointcuts, PointcutMatch paramIsFromOrder, boolean paramForSelf) {
      this(targetMethod, advice, interceptionMetadata, exclusion, orderedPointcuts, paramIsFromOrder, false, paramForSelf);
   }

   JeeInterceptorPointcutAdvisor(JeeInterceptorInterceptor advice, InterceptionMetadataI interceptionMetadata, Map exclusion, Set orderedPointcuts, boolean paramForSelf) {
      this((Method)null, advice, interceptionMetadata, exclusion, orderedPointcuts, (PointcutMatch)null, true, paramForSelf);
   }

   void setBeanControlInterfaceAndMethods(Class beanControlInterface, Set beanControlInterfaceMethods) {
      this.beanControlInterface = beanControlInterface;
      this.beanControlInterfaceMethods = beanControlInterfaceMethods;
   }

   public boolean matches(Method method, Class targetClass) {
      if (method.getDeclaringClass().equals(Object.class)) {
         return false;
      } else if (this.forTimeout) {
         return false;
      } else if (this.interceptionMetadata.getContainerControlInterfaces().contains(method.getDeclaringClass())) {
         return false;
      } else if (this.isExcluded(method, targetClass)) {
         return false;
      } else if (!this.checkOrderer(method, targetClass)) {
         return false;
      } else if (this.targetMethod != null && !this.targetMethod.equals(method)) {
         return false;
      } else {
         return this.isBeanControlInterfaceMethods(method);
      }
   }

   private boolean isBeanControlInterfaceMethods(Method method) {
      return method.getDeclaringClass().equals(this.beanControlInterface) ? this.beanControlInterfaceMethods.contains(method) : true;
   }

   public boolean checkOrderer(Method method, Class targetClass) {
      PointcutMatch localMatch = new PointcutMatch(targetClass, method);
      PointcutMatch globalMatch = new PointcutMatch(targetClass, (Method)null);
      if (this.isFromOrder != null) {
         if (this.isFromOrder.equals(localMatch)) {
            return true;
         } else if (this.isFromOrder.equals(globalMatch)) {
            return !this.orderedPointcuts.contains(localMatch);
         } else {
            return false;
         }
      } else if (this.forSelf) {
         return true;
      } else if (this.orderedPointcuts.contains(localMatch)) {
         return false;
      } else if (this.orderedPointcuts.contains(globalMatch)) {
         return this.targetMethod != null && method != null;
      } else {
         return true;
      }
   }

   public boolean isExcluded(Method method, Class targetClass) {
      InterceptorExclusion exclusion = (InterceptorExclusion)this.exclusions.get(method);
      if (exclusion == null) {
         exclusion = (InterceptorExclusion)this.exclusions.get(targetClass);
      }

      return exclusion != null && (this.isClassExcluded(exclusion) || this.isDefaultExcluded(exclusion));
   }

   private boolean isClassExcluded(InterceptorExclusion exclusion) {
      return this.getAdvice().getInterceptorMetadata() != null && exclusion.isExcludeClassInterceptors() && this.getAdvice().getInterceptorMetadata().isClassInterceptor();
   }

   private boolean isDefaultExcluded(InterceptorExclusion exclusion) {
      return this.getAdvice().getInterceptorMetadata() != null && exclusion.isExcludeDefaultInterceptors() && this.getAdvice().getInterceptorMetadata().isDefaultInterceptor();
   }

   public JeeInterceptorInterceptor getAdvice() {
      return (JeeInterceptorInterceptor)super.getAdvice();
   }

   boolean isForTimeout() {
      return this.forTimeout;
   }

   PointcutMatch isFromOrderer() {
      return this.isFromOrder;
   }

   public String toString() {
      return this.getClass().getSimpleName() + ": matches=" + this.targetMethod + "; advice=" + this.getAdvice() + " ;id=" + System.identityHashCode(this);
   }
}
