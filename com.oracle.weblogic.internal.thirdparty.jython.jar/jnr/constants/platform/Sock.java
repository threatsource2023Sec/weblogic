package jnr.constants.platform;

import jnr.constants.Constant;

public enum Sock implements Constant {
   SOCK_STREAM,
   SOCK_DGRAM,
   SOCK_RAW,
   SOCK_RDM,
   SOCK_SEQPACKET,
   SOCK_MAXADDRLEN,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(Sock.class, 20000, 29999);

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

   public static Sock valueOf(long value) {
      return (Sock)resolver.valueOf(value);
   }
}
