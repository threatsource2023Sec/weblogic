package org.mozilla.javascript.optimizer;

public class TypeEvent {
   public static final int EventBitLength = 2;
   public static final int AnyType = 3;
   public static final int NumberType = 1;
   public static final int NoType = 0;
   private int itsEvent;

   public TypeEvent(int var1) {
      this.itsEvent = var1;
   }

   public boolean add(int var1) {
      return (this.itsEvent |= var1) != var1;
   }

   public int getEvent() {
      return this.itsEvent;
   }
}
