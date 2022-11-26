package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum INAddr implements Constant {
   INADDR_ANY(0L),
   INADDR_BROADCAST(4294967295L),
   INADDR_NONE(4294967295L),
   INADDR_LOOPBACK(2130706433L),
   INADDR_UNSPEC_GROUP(3758096384L),
   INADDR_ALLHOSTS_GROUP(3758096385L),
   INADDR_ALLRTRS_GROUP(3758096386L),
   INADDR_MAX_LOCAL_GROUP(3758096639L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 4294967295L;

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
