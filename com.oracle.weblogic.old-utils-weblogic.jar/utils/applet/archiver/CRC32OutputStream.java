package utils.applet.archiver;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.CRC32;

final class CRC32OutputStream extends OutputStream {
   CRC32 crc = new CRC32();
   int n = 0;

   public void write(int r) throws IOException {
      this.crc.update(r);
      ++this.n;
   }

   public void write(byte[] b) throws IOException {
      this.crc.update(b, 0, b.length);
      this.n += b.length;
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this.crc.update(b, off, len);
      this.n += len - off;
   }
}
