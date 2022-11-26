package javax.enterprise.inject.spi;

import java.util.Set;
import javax.interceptor.InvocationContext;

public interface Interceptor extends Bean {
   Set getInterceptorBindings();

   boolean intercepts(InterceptionType var1);

   Object intercept(InterceptionType var1, Object var2, InvocationContext var3) throws Exception;
}
