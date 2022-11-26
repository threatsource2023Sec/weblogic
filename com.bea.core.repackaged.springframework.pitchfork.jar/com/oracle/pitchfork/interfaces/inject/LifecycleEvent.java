package com.oracle.pitchfork.interfaces.inject;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.interceptor.AroundConstruct;

public enum LifecycleEvent {
   AROUND_CONSTRUCT(AroundConstruct.class),
   POST_CONSTRUCT(PostConstruct.class),
   PRE_DESTROY(PreDestroy.class),
   POST_ACTIVATE(PostActivate.class),
   PRE_PASSIVATE(PrePassivate.class);

   private Class A;

   private LifecycleEvent(Class A) {
      this.A = A;
   }

   public Class getAnnotation() {
      return this.A;
   }
}
