package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class MethodOverrides {
   private final Set overrides = new CopyOnWriteArraySet();

   public MethodOverrides() {
   }

   public MethodOverrides(MethodOverrides other) {
      this.addOverrides(other);
   }

   public void addOverrides(@Nullable MethodOverrides other) {
      if (other != null) {
         this.overrides.addAll(other.overrides);
      }

   }

   public void addOverride(MethodOverride override) {
      this.overrides.add(override);
   }

   public Set getOverrides() {
      return this.overrides;
   }

   public boolean isEmpty() {
      return this.overrides.isEmpty();
   }

   @Nullable
   public MethodOverride getOverride(Method method) {
      MethodOverride match = null;
      Iterator var3 = this.overrides.iterator();

      while(var3.hasNext()) {
         MethodOverride candidate = (MethodOverride)var3.next();
         if (candidate.matches(method)) {
            match = candidate;
         }
      }

      return match;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof MethodOverrides)) {
         return false;
      } else {
         MethodOverrides that = (MethodOverrides)other;
         return this.overrides.equals(that.overrides);
      }
   }

   public int hashCode() {
      return this.overrides.hashCode();
   }
}
