package com.bea.core.repackaged.springframework.context;

import java.util.EventListener;

@FunctionalInterface
public interface ApplicationListener extends EventListener {
   void onApplicationEvent(ApplicationEvent var1);
}
