package jnr.constants.platform;

import jnr.constants.Constant;

public enum Access implements Constant {
   F_OK,
   X_OK,
   W_OK,
   R_OK,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getBitmaskResolver(Access.class);

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

   public static Access valueOf(long value) {
      return (Access)resolver.valueOf(value);
   }
}
