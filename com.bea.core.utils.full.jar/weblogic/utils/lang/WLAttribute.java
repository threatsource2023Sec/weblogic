package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WLAttribute implements Writable {
   protected short attribute_name_index;
   protected int attribute_length;
   byte[] info;

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeShort(this.attribute_name_index);
      dos.writeInt(this.attribute_length);
      dos.write(this.info, 0, this.attribute_length);
   }

   public WLAttribute read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.attribute_name_index = dis.readShort();
      this.attribute_length = dis.readInt();
      this.info = new byte[this.attribute_length];
      dis.readFully(this.info);
      return this;
   }
}
