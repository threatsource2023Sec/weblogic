package org.jboss.weld.interceptor.spi.model;

public enum InterceptionType {
   AROUND_INVOKE(false, "javax.interceptor.AroundInvoke"),
   AROUND_TIMEOUT(false, "javax.interceptor.AroundTimeout"),
   POST_CONSTRUCT(true, "javax.annotation.PostConstruct"),
   PRE_DESTROY(true, "javax.annotation.PreDestroy"),
   POST_ACTIVATE(true, "javax.ejb.PostActivate"),
   PRE_PASSIVATE(true, "javax.ejb.PrePassivate"),
   AROUND_CONSTRUCT(true, "javax.interceptor.AroundConstruct");

   private final boolean lifecycleCallback;
   private final String annotationClassName;

   private InterceptionType(boolean lifecycleCallback, String annotationClassName) {
      this.lifecycleCallback = lifecycleCallback;
      this.annotationClassName = annotationClassName;
   }

   public boolean isLifecycleCallback() {
      return this.lifecycleCallback;
   }

   public String annotationClassName() {
      return this.annotationClassName;
   }

   public static InterceptionType valueOf(javax.enterprise.inject.spi.InterceptionType interceptionType) {
      return valueOf(interceptionType.name());
   }
}
