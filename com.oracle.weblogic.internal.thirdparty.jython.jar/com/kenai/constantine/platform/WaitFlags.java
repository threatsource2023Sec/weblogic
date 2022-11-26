package com.kenai.constantine.platform;

import com.kenai.constantine.Constant;

/** @deprecated */
@Deprecated
public enum WaitFlags implements Constant {
   WNOHANG,
   WUNTRACED,
   WSTOPPED,
   WEXITED,
   WCONTINUED,
   WNOWAIT,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getBitmaskResolver(WaitFlags.class);

   public final int value() {
      return resolver.intValue(this);
   }

   public final int intValue() {
      return (int)resolver.longValue(this);
   }

   public final long longValue() {
      return resolver.longValue(this);
   }

   public final String description() {
      return resolver.description(this);
   }

   public final boolean defined() {
      return true;
   }

   public final String toString() {
      return this.description();
   }

   public static final WaitFlags valueOf(int value) {
      return (WaitFlags)resolver.valueOf(value);
   }
}
