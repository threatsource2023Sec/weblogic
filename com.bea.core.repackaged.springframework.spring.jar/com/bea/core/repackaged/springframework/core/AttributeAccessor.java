package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface AttributeAccessor {
   void setAttribute(String var1, @Nullable Object var2);

   @Nullable
   Object getAttribute(String var1);

   @Nullable
   Object removeAttribute(String var1);

   boolean hasAttribute(String var1);

   String[] attributeNames();
}
