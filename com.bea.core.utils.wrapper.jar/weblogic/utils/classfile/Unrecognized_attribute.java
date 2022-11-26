package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Unrecognized_attribute extends attribute_info {
   byte[] info;

   public void read(DataInput in) throws IOException, BadBytecodesException {
      super.read(in);
      this.info = new byte[this.attribute_length];
      int total_read = false;
      in.readFully(this.info);
   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      out.writeShort(this.attribute_name.getIndex());
      out.writeInt(this.attribute_length);
      out.write(this.info);
   }
}
