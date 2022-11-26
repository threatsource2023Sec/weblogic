package antlr;

public abstract class InputBuffer {
   protected int nMarkers = 0;
   protected int markerOffset = 0;
   protected int numToConsume = 0;
   protected CharQueue queue = new CharQueue(1);

   public void commit() {
      --this.nMarkers;
   }

   public void consume() {
      ++this.numToConsume;
   }

   public abstract void fill(int var1) throws CharStreamException;

   public String getLAChars() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = this.markerOffset; var2 < this.queue.nbrEntries; ++var2) {
         var1.append(this.queue.elementAt(var2));
      }

      return var1.toString();
   }

   public String getMarkedChars() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < this.markerOffset; ++var2) {
         var1.append(this.queue.elementAt(var2));
      }

      return var1.toString();
   }

   public boolean isMarked() {
      return this.nMarkers != 0;
   }

   public char LA(int var1) throws CharStreamException {
      this.fill(var1);
      return this.queue.elementAt(this.markerOffset + var1 - 1);
   }

   public int mark() {
      this.syncConsume();
      ++this.nMarkers;
      return this.markerOffset;
   }

   public void rewind(int var1) {
      this.syncConsume();
      this.markerOffset = var1;
      --this.nMarkers;
   }

   public void reset() {
      this.nMarkers = 0;
      this.markerOffset = 0;
      this.numToConsume = 0;
      this.queue.reset();
   }

   protected void syncConsume() {
      for(; this.numToConsume > 0; --this.numToConsume) {
         if (this.nMarkers > 0) {
            ++this.markerOffset;
         } else {
            this.queue.removeFirst();
         }
      }

   }
}
