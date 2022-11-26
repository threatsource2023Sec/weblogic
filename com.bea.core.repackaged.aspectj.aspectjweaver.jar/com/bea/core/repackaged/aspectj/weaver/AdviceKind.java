package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.util.TypeSafeEnum;
import java.io.IOException;

public class AdviceKind extends TypeSafeEnum {
   private int precedence;
   private boolean isAfter;
   private boolean isCflow;
   public static final AdviceKind Before = new AdviceKind("before", 1, 0, false, false);
   public static final AdviceKind After = new AdviceKind("after", 2, 0, true, false);
   public static final AdviceKind AfterThrowing = new AdviceKind("afterThrowing", 3, 0, true, false);
   public static final AdviceKind AfterReturning = new AdviceKind("afterReturning", 4, 0, true, false);
   public static final AdviceKind Around = new AdviceKind("around", 5, 0, false, false);
   public static final AdviceKind CflowEntry = new AdviceKind("cflowEntry", 6, 1, false, true);
   public static final AdviceKind CflowBelowEntry = new AdviceKind("cflowBelowEntry", 7, -1, false, true);
   public static final AdviceKind InterInitializer = new AdviceKind("interInitializer", 8, -2, false, false);
   public static final AdviceKind PerCflowEntry = new AdviceKind("perCflowEntry", 9, 1, false, true);
   public static final AdviceKind PerCflowBelowEntry = new AdviceKind("perCflowBelowEntry", 10, -1, false, true);
   public static final AdviceKind PerThisEntry = new AdviceKind("perThisEntry", 11, 1, false, false);
   public static final AdviceKind PerTargetEntry = new AdviceKind("perTargetEntry", 12, 1, false, false);
   public static final AdviceKind Softener = new AdviceKind("softener", 13, 1, false, false);
   public static final AdviceKind PerTypeWithinEntry = new AdviceKind("perTypeWithinEntry", 14, 1, false, false);

   public AdviceKind(String name, int key, int precedence, boolean isAfter, boolean isCflow) {
      super(name, key);
      this.precedence = precedence;
      this.isAfter = isAfter;
      this.isCflow = isCflow;
   }

   public static AdviceKind read(VersionedDataInputStream s) throws IOException {
      int key = s.readByte();
      switch (key) {
         case 1:
            return Before;
         case 2:
            return After;
         case 3:
            return AfterThrowing;
         case 4:
            return AfterReturning;
         case 5:
            return Around;
         case 6:
            return CflowEntry;
         case 7:
            return CflowBelowEntry;
         case 8:
            return InterInitializer;
         case 9:
            return PerCflowEntry;
         case 10:
            return PerCflowBelowEntry;
         case 11:
            return PerThisEntry;
         case 12:
            return PerTargetEntry;
         case 13:
            return Softener;
         case 14:
            return PerTypeWithinEntry;
         default:
            throw new RuntimeException("unimplemented kind: " + key);
      }
   }

   public static AdviceKind stringToKind(String s) {
      if (s.equals(Before.getName())) {
         return Before;
      } else if (s.equals(After.getName())) {
         return After;
      } else if (s.equals(AfterThrowing.getName())) {
         return AfterThrowing;
      } else if (s.equals(AfterReturning.getName())) {
         return AfterReturning;
      } else if (s.equals(Around.getName())) {
         return Around;
      } else {
         throw new IllegalArgumentException("unknown kind: \"" + s + "\"");
      }
   }

   public boolean isAfter() {
      return this.isAfter;
   }

   public boolean isCflow() {
      return this.isCflow;
   }

   public int getPrecedence() {
      return this.precedence;
   }

   public boolean isPerEntry() {
      return this == PerCflowEntry || this == PerCflowBelowEntry || this == PerThisEntry || this == PerTargetEntry || this == PerTypeWithinEntry;
   }

   public boolean isPerObjectEntry() {
      return this == PerThisEntry || this == PerTargetEntry;
   }
}
