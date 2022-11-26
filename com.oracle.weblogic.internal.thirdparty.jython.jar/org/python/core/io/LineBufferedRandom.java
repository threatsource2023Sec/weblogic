package org.python.core.io;

public class LineBufferedRandom extends BufferedRandom {
   public LineBufferedRandom(RawIOBase rawIO) {
      super(rawIO, 1);
   }

   protected void initChildBuffers() {
      this.reader = new BufferedReader(this.rawIO, 0);
      this.writer = new LineBufferedWriter(this.rawIO);
   }
}
