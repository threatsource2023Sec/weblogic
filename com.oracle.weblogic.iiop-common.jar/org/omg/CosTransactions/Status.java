package org.omg.CosTransactions;

import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public class Status implements IDLEntity {
   private int __value;
   private static int __size = 10;
   private static Status[] __array;
   public static final int _StatusActive = 0;
   public static final Status StatusActive;
   public static final int _StatusMarkedRollback = 1;
   public static final Status StatusMarkedRollback;
   public static final int _StatusPrepared = 2;
   public static final Status StatusPrepared;
   public static final int _StatusCommitted = 3;
   public static final Status StatusCommitted;
   public static final int _StatusRolledBack = 4;
   public static final Status StatusRolledBack;
   public static final int _StatusUnknown = 5;
   public static final Status StatusUnknown;
   public static final int _StatusNoTransaction = 6;
   public static final Status StatusNoTransaction;
   public static final int _StatusPreparing = 7;
   public static final Status StatusPreparing;
   public static final int _StatusCommitting = 8;
   public static final Status StatusCommitting;
   public static final int _StatusRollingBack = 9;
   public static final Status StatusRollingBack;

   public int value() {
      return this.__value;
   }

   public static Status from_int(int value) {
      if (value >= 0 && value < __size) {
         return __array[value];
      } else {
         throw new BAD_PARAM();
      }
   }

   protected Status(int value) {
      this.__value = value;
      __array[this.__value] = this;
   }

   static {
      __array = new Status[__size];
      StatusActive = new Status(0);
      StatusMarkedRollback = new Status(1);
      StatusPrepared = new Status(2);
      StatusCommitted = new Status(3);
      StatusRolledBack = new Status(4);
      StatusUnknown = new Status(5);
      StatusNoTransaction = new Status(6);
      StatusPreparing = new Status(7);
      StatusCommitting = new Status(8);
      StatusRollingBack = new Status(9);
   }
}
