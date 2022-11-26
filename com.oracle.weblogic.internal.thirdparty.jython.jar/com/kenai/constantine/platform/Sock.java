package com.kenai.constantine.platform;

import com.kenai.constantine.Constant;

/** @deprecated */
@Deprecated
public enum Sock implements Constant {
   SOCK_STREAM,
   SOCK_DGRAM,
   SOCK_RAW,
   SOCK_RDM,
   SOCK_SEQPACKET,
   SOCK_MAXADDRLEN,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(Sock.class, 20000, 29999);

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

   public static final Sock valueOf(int value) {
      return (Sock)resolver.valueOf(value);
   }
}
