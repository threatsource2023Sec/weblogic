package weblogic.xml.util;

import java.io.IOException;
import java.io.InputStream;

public class StringInputStream extends InputStream {
   int size;
   int index;
   String buf;

   public StringInputStream(String text) {
      this.size = text.length();
      this.index = 0;
      this.buf = text;
   }

   public int read() throws IOException {
      return this.index < this.size ? this.buf.charAt(this.index++) : -1;
   }
}
