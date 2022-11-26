package weblogic.xml.xpath.common.utils;

public final class IntStack {
   private static final int EMPTY_OFFSET = -1;
   private static final int DEFAULT_INITIAL_CAPACITY = 10;
   private static final int DEFAULT_INCREMENT = 10;
   private int mCapacityIncrement;
   private int[] mArray;
   private int mOffset;

   public IntStack() {
      this(10, 10);
   }

   public IntStack(int initialCapacity, int capacityIncrement) {
      this.mOffset = -1;
      if (initialCapacity < 0) {
         throw new IllegalArgumentException();
      } else if (capacityIncrement < 1) {
         throw new IllegalArgumentException();
      } else {
         this.mCapacityIncrement = capacityIncrement;
         this.mArray = new int[initialCapacity];
      }
   }

   public boolean isEmpty() {
      return this.mOffset == -1;
   }

   public int peek() {
      return this.mArray[this.mOffset];
   }

   public int pop() {
      return this.mArray[this.mOffset--];
   }

   public void push(int v) {
      ++this.mOffset;
      if (this.mOffset == this.mArray.length) {
         int[] old = this.mArray;
         this.mArray = new int[old.length + this.mCapacityIncrement];
         System.arraycopy(old, 0, this.mArray, 0, old.length);
      }

      this.mArray[this.mOffset] = v;
   }
}
