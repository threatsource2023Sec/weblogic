package org.jboss.weld.bootstrap.event;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.enterprise.inject.spi.InterceptionType;

public interface InterceptorConfigurator {
   InterceptorConfigurator intercept(InterceptionType var1, Function var2);

   InterceptorConfigurator interceptWithMetadata(InterceptionType var1, BiFunction var2);

   InterceptorConfigurator addBinding(Annotation var1);

   InterceptorConfigurator addBindings(Annotation... var1);

   InterceptorConfigurator addBindings(Set var1);

   InterceptorConfigurator bindings(Annotation... var1);

   InterceptorConfigurator priority(int var1);
}
