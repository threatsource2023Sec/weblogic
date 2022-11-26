package org.glassfish.grizzly.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class ServletReaderImpl extends BufferedReader {
   private static final char[] LINE_SEP = new char[]{'\r', '\n'};
   private static final int MAX_LINE_LENGTH = 4096;
   protected Reader ib;
   protected char[] lineBuffer = null;

   public ServletReaderImpl(Reader ib) {
      super(ib, 1);
      this.ib = ib;
   }

   protected Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   void clear() {
      this.ib = null;
   }

   public void close() throws IOException {
      this.ib.close();
   }

   public int read() throws IOException {
      return this.ib.read();
   }

   public int read(char[] cbuf) throws IOException {
      return this.ib.read(cbuf, 0, cbuf.length);
   }

   public int read(char[] cbuf, int off, int len) throws IOException {
      return this.ib.read(cbuf, off, len);
   }

   public long skip(long n) throws IOException {
      return this.ib.skip(n);
   }

   public boolean ready() throws IOException {
      return this.ib.ready();
   }

   public boolean markSupported() {
      return true;
   }

   public void mark(int readAheadLimit) throws IOException {
      this.ib.mark(readAheadLimit);
   }

   public void reset() throws IOException {
      this.ib.reset();
   }

   public String readLine() throws IOException {
      if (this.lineBuffer == null) {
         this.lineBuffer = new char[4096];
      }

      int pos = 0;
      int end = -1;
      int skip = -1;
      StringBuilder aggregator = null;

      while(end < 0) {
         this.mark(4096);

         while(pos < 4096 && end < 0) {
            int nRead = this.read(this.lineBuffer, pos, 4096 - pos);
            if (nRead < 0) {
               if (pos == 0 && aggregator == null) {
                  return null;
               }

               end = pos;
               skip = pos;
            }

            for(int i = pos; i < pos + nRead && end < 0; ++i) {
               if (this.lineBuffer[i] == LINE_SEP[0]) {
                  end = i;
                  skip = i + 1;
                  char nextchar;
                  if (i == pos + nRead - 1) {
                     nextchar = (char)this.read();
                  } else {
                     nextchar = this.lineBuffer[i + 1];
                  }

                  if (nextchar == LINE_SEP[1]) {
                     ++skip;
                  }
               } else if (this.lineBuffer[i] == LINE_SEP[1]) {
                  end = i;
                  skip = i + 1;
               }
            }

            if (nRead > 0) {
               pos += nRead;
            }
         }

         if (end < 0) {
            if (aggregator == null) {
               aggregator = new StringBuilder();
            }

            aggregator.append(this.lineBuffer);
            pos = 0;
         } else {
            this.reset();
            long skipped = this.skip((long)skip);

            assert (long)skip == skipped;
         }
      }

      String result;
      if (aggregator == null) {
         result = new String(this.lineBuffer, 0, end);
      } else {
         aggregator.append(this.lineBuffer, 0, end);
         result = aggregator.toString();
      }

      return result;
   }
}
