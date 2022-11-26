package org.stringtemplate.v4;

import java.io.IOException;
import java.io.Writer;

public class NoIndentWriter extends AutoIndentWriter {
   public NoIndentWriter(Writer out) {
      super(out);
   }

   public int write(String str) throws IOException {
      this.out.write(str);
      return str.length();
   }
}
