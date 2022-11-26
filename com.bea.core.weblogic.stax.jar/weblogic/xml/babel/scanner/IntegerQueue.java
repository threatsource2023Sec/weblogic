package weblogic.xml.babel.scanner;

final class IntegerQueue {
   private int[] items;
   private int capacity;
   private int head;
   private int tail;
   private int numItems;

   IntegerQueue() {
      this.capacity = 128;
      this.head = 0;
      this.tail = 0;
      this.numItems = 0;
      this.items = new int[this.capacity];
   }

   IntegerQueue(int capacity) {
      this.capacity = capacity;
      this.numItems = 0;
      this.head = 0;
      this.tail = 0;
      this.items = new int[capacity];
   }

   private int[] reallocate(int[] oldItems) {
      this.capacity = oldItems.length * 2;
      int[] newItems = new int[oldItems.length * 2];

      for(int i = 0; i < oldItems.length; ++i) {
         newItems[i] = oldItems[i];
      }

      return newItems;
   }

   private void shift() {
      for(int i = 0; i < this.numItems; ++i) {
         this.items[i] = this.items[i + this.head];
      }

      this.head = 0;
      this.tail = this.head + this.numItems;
   }

   final void add(int i) {
      if (this.tail >= this.capacity) {
         this.shift();
      }

      if (this.numItems >= this.capacity) {
         this.items = this.reallocate(this.items);
      }

      this.items[this.tail] = i;
      ++this.tail;
      ++this.numItems;
   }

   final int remove() {
      if (this.numItems == 0) {
         return -1;
      } else {
         int item = this.items[this.head];
         ++this.head;
         --this.numItems;
         return item;
      }
   }

   final int get() {
      return this.numItems == 0 ? -1 : this.items[this.head];
   }

   final void clear() {
      this.numItems = 0;
      this.head = 0;
      this.tail = 0;
   }

   public static final void main(String[] args) {
      IntegerQueue iq = new IntegerQueue(8);

      int i;
      for(i = 0; i < 20; ++i) {
         iq.add(i);
         System.out.println("Putting:" + i);
      }

      for(i = 0; i < 20; ++i) {
         System.out.println("Getting:" + iq.remove());
      }

   }
}
