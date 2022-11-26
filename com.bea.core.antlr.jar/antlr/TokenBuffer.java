package antlr;

public class TokenBuffer {
   protected TokenStream input;
   int nMarkers = 0;
   int markerOffset = 0;
   int numToConsume = 0;
   TokenQueue queue;

   public TokenBuffer(TokenStream var1) {
      this.input = var1;
      this.queue = new TokenQueue(1);
   }

   public final void reset() {
      this.nMarkers = 0;
      this.markerOffset = 0;
      this.numToConsume = 0;
      this.queue.reset();
   }

   public final void consume() {
      ++this.numToConsume;
   }

   private final void fill(int var1) throws TokenStreamException {
      this.syncConsume();

      while(this.queue.nbrEntries < var1 + this.markerOffset) {
         this.queue.append(this.input.nextToken());
      }

   }

   public TokenStream getInput() {
      return this.input;
   }

   public final int LA(int var1) throws TokenStreamException {
      this.fill(var1);
      return this.queue.elementAt(this.markerOffset + var1 - 1).getType();
   }

   public final Token LT(int var1) throws TokenStreamException {
      this.fill(var1);
      return this.queue.elementAt(this.markerOffset + var1 - 1);
   }

   public final int mark() {
      this.syncConsume();
      ++this.nMarkers;
      return this.markerOffset;
   }

   public final void rewind(int var1) {
      this.syncConsume();
      this.markerOffset = var1;
      --this.nMarkers;
   }

   private final void syncConsume() {
      for(; this.numToConsume > 0; --this.numToConsume) {
         if (this.nMarkers > 0) {
            ++this.markerOffset;
         } else {
            this.queue.removeFirst();
         }
      }

   }
}
