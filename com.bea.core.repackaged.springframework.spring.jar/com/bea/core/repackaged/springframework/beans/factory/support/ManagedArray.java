package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class ManagedArray extends ManagedList {
   @Nullable
   volatile Class resolvedElementType;

   public ManagedArray(String elementTypeName, int size) {
      super(size);
      Assert.notNull(elementTypeName, (String)"elementTypeName must not be null");
      this.setElementTypeName(elementTypeName);
   }
}
