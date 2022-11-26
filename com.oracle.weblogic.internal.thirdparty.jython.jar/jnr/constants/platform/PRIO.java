package jnr.constants.platform;

import jnr.constants.Constant;

public enum PRIO implements Constant {
   PRIO_MIN,
   PRIO_PROCESS,
   PRIO_PGRP,
   PRIO_USER,
   PRIO_MAX,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(PRIO.class, 20000, 29999);

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

   public static PRIO valueOf(long value) {
      return (PRIO)resolver.valueOf(value);
   }
}
