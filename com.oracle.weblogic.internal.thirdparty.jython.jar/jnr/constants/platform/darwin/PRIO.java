package jnr.constants.platform.darwin;

import jnr.constants.Constant;

public enum PRIO implements Constant {
   PRIO_MIN(-20L),
   PRIO_PROCESS(0L),
   PRIO_PGRP(1L),
   PRIO_USER(2L),
   PRIO_MAX(20L);

   private final long value;
   public static final long MIN_VALUE = -20L;
   public static final long MAX_VALUE = 20L;

   private PRIO(long value) {
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
