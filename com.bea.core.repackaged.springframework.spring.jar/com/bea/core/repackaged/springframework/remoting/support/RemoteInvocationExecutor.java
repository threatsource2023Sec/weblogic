package com.bea.core.repackaged.springframework.remoting.support;

import java.lang.reflect.InvocationTargetException;

public interface RemoteInvocationExecutor {
   Object invoke(RemoteInvocation var1, Object var2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
