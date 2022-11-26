package org.mozilla.javascript.debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class DebugReader extends Reader {
   private BufferedReader reader;
   private StringBuffer saved;

   public DebugReader(Reader var1) {
      this.reader = new BufferedReader(var1);
      this.saved = new StringBuffer();
   }

   public void close() throws IOException {
      this.reader.close();
   }

   protected void finalize() throws Throwable {
      this.reader = null;
   }

   public StringBuffer getSaved() {
      return this.saved;
   }

   public void mark(int var1) throws IOException {
      this.reader.mark(var1);
   }

   public boolean markSupported() {
      return this.reader.markSupported();
   }

   public int read() throws IOException {
      int var1 = this.reader.read();
      if (var1 != -1) {
         this.saved.append((char)var1);
      }

      return var1;
   }

   public int read(char[] var1) throws IOException {
      int var2 = this.reader.read(var1);
      if (var2 != -1) {
         this.saved.append(var1, 0, var2);
      }

      return var2;
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      int var4 = this.reader.read(var1, var2, var3);
      if (var4 > 0) {
         this.saved.append(var1, var2, var4);
      }

      return var4;
   }

   public boolean ready() throws IOException {
      return this.reader.ready();
   }

   public void reset() throws IOException {
      this.reader.reset();
   }

   public long skip(long var1) throws IOException {
      return this.reader.skip(var1);
   }
}
