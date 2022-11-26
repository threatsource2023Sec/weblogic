package org.omg.CosTransactions;

import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public class Vote implements IDLEntity {
   private int __value;
   private static int __size = 3;
   private static Vote[] __array;
   public static final int _VoteCommit = 0;
   public static final Vote VoteCommit;
   public static final int _VoteRollback = 1;
   public static final Vote VoteRollback;
   public static final int _VoteReadOnly = 2;
   public static final Vote VoteReadOnly;

   public int value() {
      return this.__value;
   }

   public static Vote from_int(int value) {
      if (value >= 0 && value < __size) {
         return __array[value];
      } else {
         throw new BAD_PARAM();
      }
   }

   protected Vote(int value) {
      this.__value = value;
      __array[this.__value] = this;
   }

   static {
      __array = new Vote[__size];
      VoteCommit = new Vote(0);
      VoteRollback = new Vote(1);
      VoteReadOnly = new Vote(2);
   }
}
