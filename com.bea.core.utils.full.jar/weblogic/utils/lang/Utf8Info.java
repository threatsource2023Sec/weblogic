package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utf8Info implements Writable {
   int length;
   byte[] bytes;

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeShort(this.length);
      dos.write(this.bytes, 0, this.length);
   }

   public Utf8Info read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.length = dis.readUnsignedShort();
      this.bytes = new byte[this.length];
      dis.readFully(this.bytes);
      return this;
   }
}
