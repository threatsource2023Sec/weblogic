package org.jboss.weld.module.jta;

import javax.enterprise.event.TransactionPhase;

enum Status {
   ALL {
      public boolean matches(int status) {
         return true;
      }
   },
   SUCCESS {
      public boolean matches(int status) {
         return status == 3;
      }
   },
   FAILURE {
      public boolean matches(int status) {
         return status != 3;
      }
   };

   private Status() {
   }

   public abstract boolean matches(int var1);

   public static Status valueOf(TransactionPhase transactionPhase) {
      if (transactionPhase != TransactionPhase.BEFORE_COMPLETION && transactionPhase != TransactionPhase.AFTER_COMPLETION) {
         if (transactionPhase == TransactionPhase.AFTER_SUCCESS) {
            return SUCCESS;
         } else if (transactionPhase == TransactionPhase.AFTER_FAILURE) {
            return FAILURE;
         } else {
            throw new IllegalArgumentException("Unknown transaction phase " + transactionPhase);
         }
      } else {
         return ALL;
      }
   }

   // $FF: synthetic method
   Status(Object x2) {
      this();
   }
}
