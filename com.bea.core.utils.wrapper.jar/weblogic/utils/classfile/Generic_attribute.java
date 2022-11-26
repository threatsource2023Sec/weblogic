package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.cp.CPUtf8;

public class Generic_attribute extends attribute_info {
   public static final String NAME = "GenericData";
   protected byte[] data;

   public Generic_attribute() {
   }

   public Generic_attribute(CPUtf8 attribute_name, byte[] data) {
      this.attribute_name = attribute_name;
      this.attribute_length = data.length;
      this.data = data;
   }

   public void read(DataInput in) throws IOException, BadBytecodesException {
      super.read(in);
      this.data = new byte[this.attribute_length];
      in.readFully(this.data);
   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      super.write(out);
      out.write(this.data);
   }
}
