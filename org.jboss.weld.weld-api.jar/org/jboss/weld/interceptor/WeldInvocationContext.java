package org.jboss.weld.interceptor;

import java.util.Set;
import javax.interceptor.InvocationContext;

public interface WeldInvocationContext extends InvocationContext {
   String INTERCEPTOR_BINDINGS_KEY = "org.jboss.weld.interceptor.bindings";

   Set getInterceptorBindings();

   Set getInterceptorBindingsByType(Class var1);
}
