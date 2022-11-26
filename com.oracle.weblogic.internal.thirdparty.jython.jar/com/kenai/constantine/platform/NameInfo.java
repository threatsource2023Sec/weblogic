package com.kenai.constantine.platform;

import com.kenai.constantine.Constant;

/** @deprecated */
@Deprecated
public enum NameInfo implements Constant {
   NI_MAXHOST,
   NI_MAXSERV,
   NI_NOFQDN,
   NI_NUMERICHOST,
   NI_NAMEREQD,
   NI_NUMERICSERV,
   NI_DGRAM,
   NI_WITHSCOPEID,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(NameInfo.class, 20000, 29999);

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

   public static final NameInfo valueOf(int value) {
      return (NameInfo)resolver.valueOf(value);
   }
}
