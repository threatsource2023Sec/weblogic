package org.python.google.common.collect;

import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public enum BoundType {
   OPEN {
      BoundType flip() {
         return CLOSED;
      }
   },
   CLOSED {
      BoundType flip() {
         return OPEN;
      }
   };

   private BoundType() {
   }

   static BoundType forBoolean(boolean inclusive) {
      return inclusive ? CLOSED : OPEN;
   }

   abstract BoundType flip();

   // $FF: synthetic method
   BoundType(Object x2) {
      this();
   }
}
