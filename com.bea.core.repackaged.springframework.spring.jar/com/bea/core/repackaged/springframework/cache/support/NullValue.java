package com.bea.core.repackaged.springframework.cache.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;

public final class NullValue implements Serializable {
   public static final Object INSTANCE = new NullValue();
   private static final long serialVersionUID = 1L;

   private NullValue() {
   }

   private Object readResolve() {
      return INSTANCE;
   }

   public boolean equals(@Nullable Object obj) {
      return this == obj || obj == null;
   }

   public int hashCode() {
      return NullValue.class.hashCode();
   }

   public String toString() {
      return "null";
   }
}
