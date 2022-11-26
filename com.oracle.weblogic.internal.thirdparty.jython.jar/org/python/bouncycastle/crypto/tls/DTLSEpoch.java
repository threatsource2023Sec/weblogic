package org.python.bouncycastle.crypto.tls;

class DTLSEpoch {
   private final DTLSReplayWindow replayWindow = new DTLSReplayWindow();
   private final int epoch;
   private final TlsCipher cipher;
   private long sequenceNumber = 0L;

   DTLSEpoch(int var1, TlsCipher var2) {
      if (var1 < 0) {
         throw new IllegalArgumentException("'epoch' must be >= 0");
      } else if (var2 == null) {
         throw new IllegalArgumentException("'cipher' cannot be null");
      } else {
         this.epoch = var1;
         this.cipher = var2;
      }
   }

   long allocateSequenceNumber() {
      return (long)(this.sequenceNumber++);
   }

   TlsCipher getCipher() {
      return this.cipher;
   }

   int getEpoch() {
      return this.epoch;
   }

   DTLSReplayWindow getReplayWindow() {
      return this.replayWindow;
   }

   long getSequenceNumber() {
      return this.sequenceNumber;
   }
}
