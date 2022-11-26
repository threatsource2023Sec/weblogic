package com.kenai.constantine.platform;

import com.kenai.constantine.Constant;

/** @deprecated */
@Deprecated
public enum Shutdown implements Constant {
   SHUT_RD,
   SHUT_WR,
   SHUT_RDWR,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getBitmaskResolver(Shutdown.class);

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

   public static final Shutdown valueOf(int value) {
      return (Shutdown)resolver.valueOf(value);
   }
}
