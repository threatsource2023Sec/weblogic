package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Label extends Op {
   public Label() {
      super(0);
   }

   public void read(DataInput in) throws IOException {
   }

   public void write(DataOutput out) throws IOException {
   }

   public int length() {
      return 0;
   }
}
