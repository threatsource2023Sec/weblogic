package jnr.constants.platform;

import jnr.constants.Constant;

public enum Shutdown implements Constant {
   SHUT_RD,
   SHUT_WR,
   SHUT_RDWR,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getBitmaskResolver(Shutdown.class);

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

   public static Shutdown valueOf(long value) {
      return (Shutdown)resolver.valueOf(value);
   }
}
