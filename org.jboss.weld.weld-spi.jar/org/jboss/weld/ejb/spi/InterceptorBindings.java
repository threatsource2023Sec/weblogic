package org.jboss.weld.ejb.spi;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import javax.enterprise.inject.spi.InterceptionType;

public interface InterceptorBindings {
   Collection getAllInterceptors();

   List getMethodInterceptors(InterceptionType var1, Method var2);

   List getLifecycleInterceptors(InterceptionType var1);
}
