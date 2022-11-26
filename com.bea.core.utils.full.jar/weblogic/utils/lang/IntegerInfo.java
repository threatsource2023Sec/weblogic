package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IntegerInfo implements Writable {
   int value;

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeInt(this.value);
   }

   public IntegerInfo read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.value = dis.readInt();
      return this;
   }
}
