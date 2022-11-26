package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.context.ApplicationEvent;
import com.bea.core.repackaged.springframework.context.ApplicationListener;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface ApplicationEventMulticaster {
   void addApplicationListener(ApplicationListener var1);

   void addApplicationListenerBean(String var1);

   void removeApplicationListener(ApplicationListener var1);

   void removeApplicationListenerBean(String var1);

   void removeAllListeners();

   void multicastEvent(ApplicationEvent var1);

   void multicastEvent(ApplicationEvent var1, @Nullable ResolvableType var2);
}
