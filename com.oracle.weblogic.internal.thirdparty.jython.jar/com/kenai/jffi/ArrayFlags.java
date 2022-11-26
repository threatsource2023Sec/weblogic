package com.kenai.jffi;

public final class ArrayFlags {
   public static final int IN = 1;
   public static final int OUT = 2;
   public static final int PINNED = 8;
   public static final int NULTERMINATE = 4;
   public static final int CLEAR = 16;

   private ArrayFlags() {
   }

   public static final boolean isOut(int flags) {
      return (flags & 3) != 1;
   }

   public static final boolean isIn(int flags) {
      return (flags & 3) != 2;
   }
}
