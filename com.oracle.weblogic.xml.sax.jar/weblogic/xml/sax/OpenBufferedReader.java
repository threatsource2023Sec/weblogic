package weblogic.xml.sax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class OpenBufferedReader extends BufferedReader {
   private boolean allowClose = false;
   private long nChar;

   public OpenBufferedReader(Reader in) {
      super(in);
   }

   public OpenBufferedReader(Reader in, int size) {
      super(in, size);
   }

   public void allowClose(boolean value) {
      this.allowClose = value;
   }

   public void close() throws IOException {
      if (this.allowClose) {
         super.close();
      }

   }

   public int read() throws IOException {
      ++this.nChar;
      return super.read();
   }

   public long getNChar() {
      return this.nChar;
   }
}
