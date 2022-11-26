package jnr.constants.platform.fake;

import jnr.constants.Constant;

public enum INAddr implements Constant {
   INADDR_ANY(1L),
   INADDR_BROADCAST(2L),
   INADDR_NONE(3L),
   INADDR_LOOPBACK(4L),
   INADDR_UNSPEC_GROUP(5L),
   INADDR_ALLHOSTS_GROUP(6L),
   INADDR_ALLRTRS_GROUP(7L),
   INADDR_MAX_LOCAL_GROUP(8L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 8L;

   private INAddr(long value) {
      this.value = value;
   }

   public final int intValue() {
      return (int)this.value;
   }

   public final long longValue() {
      return this.value;
   }

   public final boolean defined() {
      return true;
   }
}
