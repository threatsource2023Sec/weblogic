package org.python.google.common.io;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.Queue;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public final class LineReader {
   private final Readable readable;
   private final Reader reader;
   private final CharBuffer cbuf = CharStreams.createBuffer();
   private final char[] buf;
   private final Queue lines;
   private final LineBuffer lineBuf;

   public LineReader(Readable readable) {
      this.buf = this.cbuf.array();
      this.lines = new LinkedList();
      this.lineBuf = new LineBuffer() {
         protected void handleLine(String line, String end) {
            LineReader.this.lines.add(line);
         }
      };
      this.readable = (Readable)Preconditions.checkNotNull(readable);
      this.reader = readable instanceof Reader ? (Reader)readable : null;
   }

   @CanIgnoreReturnValue
   public String readLine() throws IOException {
      while(true) {
         if (this.lines.peek() == null) {
            this.cbuf.clear();
            int read = this.reader != null ? this.reader.read(this.buf, 0, this.buf.length) : this.readable.read(this.cbuf);
            if (read != -1) {
               this.lineBuf.add(this.buf, 0, read);
               continue;
            }

            this.lineBuf.finish();
         }

         return (String)this.lines.poll();
      }
   }
}
