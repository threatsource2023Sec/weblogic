package jnr.constants.platform;

import jnr.constants.Constant;

public enum SocketLevel implements Constant {
   SOL_SOCKET,
   SOL_IP,
   SOL_TCP,
   SOL_UDP,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(SocketLevel.class, 20000, 29999);

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

   public static SocketLevel valueOf(long value) {
      return (SocketLevel)resolver.valueOf(value);
   }
}
