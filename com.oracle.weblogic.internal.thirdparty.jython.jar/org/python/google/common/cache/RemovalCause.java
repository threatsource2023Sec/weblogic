package org.python.google.common.cache;

import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public enum RemovalCause {
   EXPLICIT {
      boolean wasEvicted() {
         return false;
      }
   },
   REPLACED {
      boolean wasEvicted() {
         return false;
      }
   },
   COLLECTED {
      boolean wasEvicted() {
         return true;
      }
   },
   EXPIRED {
      boolean wasEvicted() {
         return true;
      }
   },
   SIZE {
      boolean wasEvicted() {
         return true;
      }
   };

   private RemovalCause() {
   }

   abstract boolean wasEvicted();

   // $FF: synthetic method
   RemovalCause(Object x2) {
      this();
   }
}
