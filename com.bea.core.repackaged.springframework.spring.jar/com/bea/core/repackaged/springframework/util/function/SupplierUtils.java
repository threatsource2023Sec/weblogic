package com.bea.core.repackaged.springframework.util.function;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.function.Supplier;

public abstract class SupplierUtils {
   @Nullable
   public static Object resolve(@Nullable Supplier supplier) {
      return supplier != null ? supplier.get() : null;
   }
}
