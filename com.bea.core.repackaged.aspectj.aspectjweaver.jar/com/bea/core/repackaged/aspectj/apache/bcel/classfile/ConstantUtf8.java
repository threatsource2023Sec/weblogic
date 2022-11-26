package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ConstantUtf8 extends Constant implements SimpleConstant {
   private String string;

   ConstantUtf8(DataInputStream file) throws IOException {
      super((byte)1);
      this.string = file.readUTF();
   }

   public ConstantUtf8(String string) {
      super((byte)1);

      assert string != null;

      this.string = string;
   }

   public void accept(ClassVisitor v) {
      v.visitConstantUtf8(this);
   }

   public final void dump(DataOutputStream file) throws IOException {
      file.writeByte(this.tag);
      file.writeUTF(this.string);
   }

   public final String toString() {
      return super.toString() + "(\"" + Utility.replace(this.string, "\n", "\\n") + "\")";
   }

   public String getValue() {
      return this.string;
   }

   public String getStringValue() {
      return this.string;
   }
}
