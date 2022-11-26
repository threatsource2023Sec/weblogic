package org.jboss.weld.manager.api;

import javax.enterprise.inject.spi.Bean;

public interface WeldInjectionTargetBuilder {
   WeldInjectionTargetBuilder setResourceInjectionEnabled(boolean var1);

   WeldInjectionTargetBuilder setTargetClassLifecycleCallbacksEnabled(boolean var1);

   WeldInjectionTargetBuilder setInterceptionEnabled(boolean var1);

   WeldInjectionTargetBuilder setDecorationEnabled(boolean var1);

   WeldInjectionTargetBuilder setBean(Bean var1);

   WeldInjectionTarget build();
}
