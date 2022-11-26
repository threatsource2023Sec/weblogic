package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.Serializable;
import java.util.Arrays;

public class SimpleKey implements Serializable {
   public static final SimpleKey EMPTY = new SimpleKey(new Object[0]);
   private final Object[] params;
   private final int hashCode;

   public SimpleKey(Object... elements) {
      Assert.notNull(elements, (String)"Elements must not be null");
      this.params = new Object[elements.length];
      System.arraycopy(elements, 0, this.params, 0, elements.length);
      this.hashCode = Arrays.deepHashCode(this.params);
   }

   public boolean equals(Object other) {
      return this == other || other instanceof SimpleKey && Arrays.deepEquals(this.params, ((SimpleKey)other).params);
   }

   public final int hashCode() {
      return this.hashCode;
   }

   public String toString() {
      return this.getClass().getSimpleName() + " [" + StringUtils.arrayToCommaDelimitedString(this.params) + "]";
   }
}
