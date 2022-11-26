package org.glassfish.hk2.extras.interception;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface InterceptorOrderingService {
   List modifyMethodInterceptors(Method var1, List var2);

   List modifyConstructorInterceptors(Constructor var1, List var2);
}
