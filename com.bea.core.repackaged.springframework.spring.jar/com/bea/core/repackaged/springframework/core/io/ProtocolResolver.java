package com.bea.core.repackaged.springframework.core.io;

import com.bea.core.repackaged.springframework.lang.Nullable;

@FunctionalInterface
public interface ProtocolResolver {
   @Nullable
   Resource resolve(String var1, ResourceLoader var2);
}
