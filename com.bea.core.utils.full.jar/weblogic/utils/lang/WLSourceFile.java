package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WLSourceFile extends WLAttribute {
   short sourcefile_index;

   WLSourceFile(short attribute_name_index, int attribute_length) {
      this.attribute_name_index = attribute_name_index;
      this.attribute_length = attribute_length;
   }

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeShort(this.attribute_name_index);
      dos.writeInt(this.attribute_length);
      dos.writeShort(this.sourcefile_index);
   }

   public WLAttribute read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.sourcefile_index = dis.readShort();
      return this;
   }
}
