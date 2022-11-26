package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Ref implements Writable {
   short class_index;
   short name_and_type_index;

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeShort(this.class_index);
      dos.writeShort(this.name_and_type_index);
   }

   public Ref read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.class_index = dis.readShort();
      this.name_and_type_index = dis.readShort();
      return this;
   }
}
