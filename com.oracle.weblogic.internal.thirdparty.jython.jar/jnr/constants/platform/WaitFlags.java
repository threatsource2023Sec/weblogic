package jnr.constants.platform;

import jnr.constants.Constant;

public enum WaitFlags implements Constant {
   WNOHANG,
   WUNTRACED,
   WSTOPPED,
   WEXITED,
   WCONTINUED,
   WNOWAIT,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getBitmaskResolver(WaitFlags.class);

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

   public static WaitFlags valueOf(long value) {
      return (WaitFlags)resolver.valueOf(value);
   }
}
