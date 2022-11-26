package jnr.constants.platform;

import jnr.constants.Constant;

public enum RLIM implements Constant {
   RLIM_NLIMITS,
   RLIM_INFINITY,
   RLIM_SAVED_MAX,
   RLIM_SAVED_CUR,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(RLIM.class, 20000, 29999);

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
      return resolver.defined(this);
   }

   public final String toString() {
      return this.description();
   }

   public static RLIM valueOf(long value) {
      return (RLIM)resolver.valueOf(value);
   }
}
