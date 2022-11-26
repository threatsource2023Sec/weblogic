package weblogic.wtc.jatmi;

import java.io.ByteArrayOutputStream;

public final class TuxedoArrayOutputStream extends ByteArrayOutputStream {
   public TuxedoArrayOutputStream() {
   }

   public TuxedoArrayOutputStream(int size) {
      super(size);
   }

   public byte[] getByteArrayReference() {
      return this.buf;
   }
}
