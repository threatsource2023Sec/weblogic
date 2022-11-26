package com.googlecode.cqengine.query.option;

import java.util.HashSet;
import java.util.Set;

public class FlagsEnabled {
   final Set flags = new HashSet();

   public void add(Object flag) {
      this.flags.add(flag);
   }

   public void remove(Object flag) {
      this.flags.remove(flag);
   }

   public boolean isFlagEnabled(Object flag) {
      return this.flags.contains(flag);
   }

   public String toString() {
      return "flagsEnabled=" + this.flags;
   }

   public static FlagsEnabled forQueryOptions(QueryOptions queryOptions) {
      FlagsEnabled flags = (FlagsEnabled)queryOptions.get(FlagsEnabled.class);
      if (flags == null) {
         flags = new FlagsEnabled();
         queryOptions.put(FlagsEnabled.class, flags);
      }

      return flags;
   }

   public static boolean isFlagEnabled(QueryOptions queryOptions, Object flag) {
      FlagsEnabled flagsDisabled = (FlagsEnabled)queryOptions.get(FlagsEnabled.class);
      return flagsDisabled != null && flagsDisabled.isFlagEnabled(flag);
   }
}
