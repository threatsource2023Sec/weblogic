package weblogic.io.common.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public final class DiscardPipe extends OutputStream {
   public void write(int b) throws IOException {
   }

   public void write(byte[] b) throws IOException {
   }

   public void write(byte[] b, int off, int len) throws IOException {
   }

   public PrintStream getPrintStream() {
      return new PrintStream(this);
   }
}
