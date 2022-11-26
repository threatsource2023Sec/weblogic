package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NameAndTypeInfo implements Writable {
   short name_index;
   short descriptor_index;

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeShort(this.name_index);
      dos.writeShort(this.descriptor_index);
   }

   public NameAndTypeInfo read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.name_index = dis.readShort();
      this.descriptor_index = dis.readShort();
      return this;
   }
}
