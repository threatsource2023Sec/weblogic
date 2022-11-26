package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.context.ApplicationListener;
import java.lang.reflect.Method;

public interface EventListenerFactory {
   boolean supportsMethod(Method var1);

   ApplicationListener createApplicationListener(String var1, Class var2, Method var3);
}
