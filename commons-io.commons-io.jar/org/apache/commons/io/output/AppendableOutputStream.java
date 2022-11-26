package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class AppendableOutputStream extends OutputStream {
   private final Appendable appendable;

   public AppendableOutputStream(Appendable appendable) {
      this.appendable = appendable;
   }

   public void write(int b) throws IOException {
      this.appendable.append((char)b);
   }

   public Appendable getAppendable() {
      return this.appendable;
   }
}
