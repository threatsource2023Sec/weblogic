package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.lang.Nullable;

final class NullBean {
   public boolean equals(@Nullable Object obj) {
      return this == obj || obj == null;
   }

   public int hashCode() {
      return NullBean.class.hashCode();
   }

   public String toString() {
      return "null";
   }
}
