package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class InstanceFilter {
   private final Collection includes;
   private final Collection excludes;
   private final boolean matchIfEmpty;

   public InstanceFilter(@Nullable Collection includes, @Nullable Collection excludes, boolean matchIfEmpty) {
      this.includes = (Collection)(includes != null ? includes : Collections.emptyList());
      this.excludes = (Collection)(excludes != null ? excludes : Collections.emptyList());
      this.matchIfEmpty = matchIfEmpty;
   }

   public boolean match(Object instance) {
      Assert.notNull(instance, "Instance to match must not be null");
      boolean includesSet = !this.includes.isEmpty();
      boolean excludesSet = !this.excludes.isEmpty();
      if (!includesSet && !excludesSet) {
         return this.matchIfEmpty;
      } else {
         boolean matchIncludes = this.match(instance, this.includes);
         boolean matchExcludes = this.match(instance, this.excludes);
         if (!includesSet) {
            return !matchExcludes;
         } else if (!excludesSet) {
            return matchIncludes;
         } else {
            return matchIncludes && !matchExcludes;
         }
      }
   }

   protected boolean match(Object instance, Object candidate) {
      return instance.equals(candidate);
   }

   protected boolean match(Object instance, Collection candidates) {
      Iterator var3 = candidates.iterator();

      Object candidate;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         candidate = var3.next();
      } while(!this.match(instance, candidate));

      return true;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
      sb.append(": includes=").append(this.includes);
      sb.append(", excludes=").append(this.excludes);
      sb.append(", matchIfEmpty=").append(this.matchIfEmpty);
      return sb.toString();
   }
}
