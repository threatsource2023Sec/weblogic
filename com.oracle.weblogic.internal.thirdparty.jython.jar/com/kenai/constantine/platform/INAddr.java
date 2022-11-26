package com.kenai.constantine.platform;

import com.kenai.constantine.Constant;

/** @deprecated */
@Deprecated
public enum INAddr implements Constant {
   INADDR_ANY,
   INADDR_BROADCAST,
   INADDR_NONE,
   INADDR_LOOPBACK,
   INADDR_UNSPEC_GROUP,
   INADDR_ALLHOSTS_GROUP,
   INADDR_ALLRTRS_GROUP,
   INADDR_MAX_LOCAL_GROUP,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(INAddr.class, 20000, 29999);

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

   public static final INAddr valueOf(int value) {
      return (INAddr)resolver.valueOf(value);
   }
}
