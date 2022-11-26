package org.jboss.weld.manager.api;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionTarget;

public interface WeldInjectionTarget extends InjectionTarget {
   AnnotatedType getAnnotatedType();
}
