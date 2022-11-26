package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WLField implements Writable {
   short access_flags;
   short name_index;
   short descriptor_index;
   short attributes_count;
   WLAttribute[] attributes;

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeShort(this.access_flags);
      dos.writeShort(this.name_index);
      dos.writeShort(this.descriptor_index);
      dos.writeShort(this.attributes_count);

      for(int i = 0; i < this.attributes_count; ++i) {
         this.attributes[i].write(os);
      }

   }

   public WLField read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.access_flags = dis.readShort();
      this.name_index = dis.readShort();
      this.descriptor_index = dis.readShort();
      this.attributes_count = dis.readShort();
      this.attributes = new WLAttribute[this.attributes_count];

      for(int i = 0; i < this.attributes_count; ++i) {
         this.attributes[i] = (new WLAttribute()).read(is);
      }

      return this;
   }
}
