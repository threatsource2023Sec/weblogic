package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ClassInfo implements Writable {
   short name_index;

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeShort(this.name_index);
   }

   public ClassInfo read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.name_index = dis.readShort();
      return this;
   }
}
