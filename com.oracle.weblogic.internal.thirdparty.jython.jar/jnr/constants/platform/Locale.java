package jnr.constants.platform;

import jnr.constants.Constant;

public enum Locale implements Constant {
   LC_CTYPE,
   LC_NUMERIC,
   LC_TIME,
   LC_COLLATE,
   LC_MONETARY,
   LC_MESSAGES,
   LC_ALL,
   LC_PAPER,
   LC_NAME,
   LC_ADDRESS,
   LC_TELEPHONE,
   LC_MEASUREMENT,
   LC_IDENTIFICATION,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(Locale.class, 20000, 29999);

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

   public static Locale valueOf(long value) {
      return (Locale)resolver.valueOf(value);
   }
}
