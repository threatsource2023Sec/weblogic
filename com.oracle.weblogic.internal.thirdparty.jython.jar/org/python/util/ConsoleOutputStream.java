package org.python.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ConsoleOutputStream extends FilterOutputStream {
   protected ByteBuffer buf;

   public ConsoleOutputStream(OutputStream out, int promptCapacity) {
      super(out);
      this.buf = ByteBuffer.allocate(Math.max(4, promptCapacity));
   }

   public void write(int b) throws IOException {
      this.buf.put((byte)b);
      this.out.write(b);
      if (b == 10 || this.buf.remaining() == 0) {
         this.buf.position(0);
      }

   }

   public void flush() throws IOException {
      this.out.flush();
   }

   public void close() throws IOException {
      super.close();
      this.out.close();
   }

   protected CharSequence getPrompt(Charset encoding) {
      this.buf.flip();
      CharSequence prompt = encoding.decode(this.buf);
      this.buf.compact();
      return prompt;
   }
}
