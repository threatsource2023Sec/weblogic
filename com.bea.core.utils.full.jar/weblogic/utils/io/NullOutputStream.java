package weblogic.utils.io;

import java.io.OutputStream;

public final class NullOutputStream extends OutputStream {
   public void write(int b) {
   }

   public void write(byte[] b, int off, int len) {
   }
}
