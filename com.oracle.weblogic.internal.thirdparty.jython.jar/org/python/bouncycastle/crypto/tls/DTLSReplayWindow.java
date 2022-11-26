package org.python.bouncycastle.crypto.tls;

class DTLSReplayWindow {
   private static final long VALID_SEQ_MASK = 281474976710655L;
   private static final long WINDOW_SIZE = 64L;
   private long latestConfirmedSeq = -1L;
   private long bitmap = 0L;

   boolean shouldDiscard(long var1) {
      if ((var1 & 281474976710655L) != var1) {
         return true;
      } else {
         if (var1 <= this.latestConfirmedSeq) {
            long var3 = this.latestConfirmedSeq - var1;
            if (var3 >= 64L) {
               return true;
            }

            if ((this.bitmap & 1L << (int)var3) != 0L) {
               return true;
            }
         }

         return false;
      }
   }

   void reportAuthenticated(long var1) {
      if ((var1 & 281474976710655L) != var1) {
         throw new IllegalArgumentException("'seq' out of range");
      } else {
         long var3;
         if (var1 <= this.latestConfirmedSeq) {
            var3 = this.latestConfirmedSeq - var1;
            if (var3 < 64L) {
               this.bitmap |= 1L << (int)var3;
            }
         } else {
            var3 = var1 - this.latestConfirmedSeq;
            if (var3 >= 64L) {
               this.bitmap = 1L;
            } else {
               this.bitmap <<= (int)var3;
               this.bitmap |= 1L;
            }

            this.latestConfirmedSeq = var1;
         }

      }
   }

   void reset() {
      this.latestConfirmedSeq = -1L;
      this.bitmap = 0L;
   }
}
