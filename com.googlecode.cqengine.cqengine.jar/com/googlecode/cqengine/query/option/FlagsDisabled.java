package com.googlecode.cqengine.query.option;

import java.util.HashSet;
import java.util.Set;

public class FlagsDisabled {
   final Set flags = new HashSet();

   public void add(Object flag) {
      this.flags.add(flag);
   }

   public void remove(Object flag) {
      this.flags.remove(flag);
   }

   public boolean isFlagDisabled(Object flag) {
      return this.flags.contains(flag);
   }

   public String toString() {
      return "flagsDisabled=" + this.flags;
   }

   public static FlagsDisabled forQueryOptions(QueryOptions queryOptions) {
      FlagsDisabled flags = (FlagsDisabled)queryOptions.get(FlagsDisabled.class);
      if (flags == null) {
         flags = new FlagsDisabled();
         queryOptions.put(FlagsDisabled.class, flags);
      }

      return flags;
   }

   public static boolean isFlagDisabled(QueryOptions queryOptions, Object flag) {
      FlagsDisabled flagsDisabled = (FlagsDisabled)queryOptions.get(FlagsDisabled.class);
      return flagsDisabled != null && flagsDisabled.isFlagDisabled(flag);
   }
}
