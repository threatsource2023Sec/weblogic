package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import weblogic.utils.classfile.cp.CPUtf8;

public class SourceFile_attribute extends attribute_info {
   public static final String NAME = "SourceFile";
   public CPUtf8 sourcefile;

   public void read(DataInput in) throws IOException, BadBytecodesException {
      try {
         super.read(in);
         this.sourcefile = this.constant_pool.utf8At(in.readUnsignedShort());
      } catch (MalformedClassException var3) {
         throw new IOException(String.valueOf(var3));
      }
   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      super.write(out);
      out.writeShort(this.sourcefile.getIndex());
   }

   public String toString() {
      return this.sourcefile.toString();
   }
}
