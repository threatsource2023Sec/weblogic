package antlr;

public class CharQueue {
   protected char[] buffer;
   private int sizeLessOne;
   private int offset;
   protected int nbrEntries;

   public CharQueue(int var1) {
      if (var1 < 0) {
         this.init(16);
      } else if (var1 >= 1073741823) {
         this.init(Integer.MAX_VALUE);
      } else {
         int var2;
         for(var2 = 2; var2 < var1; var2 *= 2) {
         }

         this.init(var2);
      }
   }

   public final void append(char var1) {
      if (this.nbrEntries == this.buffer.length) {
         this.expand();
      }

      this.buffer[this.offset + this.nbrEntries & this.sizeLessOne] = var1;
      ++this.nbrEntries;
   }

   public final char elementAt(int var1) {
      return this.buffer[this.offset + var1 & this.sizeLessOne];
   }

   private final void expand() {
      char[] var1 = new char[this.buffer.length * 2];

      for(int var2 = 0; var2 < this.buffer.length; ++var2) {
         var1[var2] = this.elementAt(var2);
      }

      this.buffer = var1;
      this.sizeLessOne = this.buffer.length - 1;
      this.offset = 0;
   }

   public void init(int var1) {
      this.buffer = new char[var1];
      this.sizeLessOne = var1 - 1;
      this.offset = 0;
      this.nbrEntries = 0;
   }

   public final void reset() {
      this.offset = 0;
      this.nbrEntries = 0;
   }

   public final void removeFirst() {
      this.offset = this.offset + 1 & this.sizeLessOne;
      --this.nbrEntries;
   }
}
