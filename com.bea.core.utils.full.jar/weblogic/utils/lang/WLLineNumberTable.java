package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WLLineNumberTable extends WLAttribute {
   short line_number_table_length;
   short[][] line_number_table;

   WLLineNumberTable(short attribute_name_index, int attribute_length) {
      this.attribute_name_index = attribute_name_index;
      this.attribute_length = attribute_length;
   }

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeShort(this.attribute_name_index);
      dos.writeInt(this.attribute_length);
      dos.writeShort(this.line_number_table_length);

      for(int i = 0; i < this.line_number_table_length; ++i) {
         dos.writeShort(this.line_number_table[i][0]);
         dos.writeShort(this.line_number_table[i][1]);
      }

   }

   public WLAttribute read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.line_number_table_length = dis.readShort();
      this.line_number_table = new short[this.line_number_table_length][2];

      for(int i = 0; i < this.line_number_table_length; ++i) {
         this.line_number_table[i][0] = dis.readShort();
         this.line_number_table[i][1] = dis.readShort();
      }

      return this;
   }
}
