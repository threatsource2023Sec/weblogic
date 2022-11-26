package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LongInfo implements Writable {
   int high_bytes;
   int low_bytes;

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeInt(this.high_bytes);
      dos.writeInt(this.low_bytes);
   }

   public LongInfo read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.high_bytes = dis.readInt();
      this.low_bytes = dis.readInt();
      return this;
   }
}
