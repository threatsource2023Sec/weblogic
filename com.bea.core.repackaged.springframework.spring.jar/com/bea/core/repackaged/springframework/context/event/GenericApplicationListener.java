package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.context.ApplicationListener;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface GenericApplicationListener extends ApplicationListener, Ordered {
   boolean supportsEventType(ResolvableType var1);

   default boolean supportsSourceType(@Nullable Class sourceType) {
      return true;
   }

   default int getOrder() {
      return Integer.MAX_VALUE;
   }
}
