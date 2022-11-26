package org.jboss.weld.resolution;

import javax.enterprise.inject.spi.InterceptionType;

public interface InterceptorResolvable extends Resolvable {
   InterceptionType getInterceptionType();
}
