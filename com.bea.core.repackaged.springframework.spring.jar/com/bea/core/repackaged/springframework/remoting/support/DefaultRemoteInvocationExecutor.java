package com.bea.core.repackaged.springframework.remoting.support;

import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.InvocationTargetException;

public class DefaultRemoteInvocationExecutor implements RemoteInvocationExecutor {
   public Object invoke(RemoteInvocation invocation, Object targetObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Assert.notNull(invocation, (String)"RemoteInvocation must not be null");
      Assert.notNull(targetObject, "Target object must not be null");
      return invocation.invoke(targetObject);
   }
}
