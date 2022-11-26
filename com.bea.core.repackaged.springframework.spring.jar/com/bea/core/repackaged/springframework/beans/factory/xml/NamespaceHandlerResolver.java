package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.lang.Nullable;

@FunctionalInterface
public interface NamespaceHandlerResolver {
   @Nullable
   NamespaceHandler resolve(String var1);
}
