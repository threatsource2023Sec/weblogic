package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.cp.CPInfo;

public class ConstantValue_attribute extends attribute_info {
   public static final String NAME = "ConstantValue";
   public CPInfo constantvalue;

   public void read(DataInput in) throws IOException, BadBytecodesException {
      super.read(in);
      this.constantvalue = this.constant_pool.cpInfoAt(in.readUnsignedShort());
   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      super.write(out);
      out.writeShort(this.constantvalue.getIndex());
   }
}
