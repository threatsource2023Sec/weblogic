package com.ning.compress.lzf.parallel;

import com.ning.compress.lzf.LZFChunk;
import java.io.OutputStream;
import java.util.concurrent.Future;

class WriteTask implements Runnable {
   private final OutputStream output;
   private final Future lzfFuture;
   private final PLZFOutputStream caller;

   public WriteTask(OutputStream output, Future lzfFuture, PLZFOutputStream caller) {
      this.output = output;
      this.lzfFuture = lzfFuture;
      this.caller = caller;
   }

   public void run() {
      try {
         for(LZFChunk lzfChunk = (LZFChunk)this.lzfFuture.get(); lzfChunk != null; lzfChunk = lzfChunk.next()) {
            this.output.write(lzfChunk.getData());
         }
      } catch (Exception var2) {
         this.caller.writeException = var2;
      }

   }
}
