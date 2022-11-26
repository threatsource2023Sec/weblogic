package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import weblogic.utils.classfile.cp.CPUtf8;
import weblogic.utils.classfile.cp.ConstantPool;

public class attribute_info {
   protected AttributeParent parent;
   public CPUtf8 attribute_name;
   public int attribute_length;
   protected ConstantPool constant_pool;
   private static final boolean debug = false;

   protected attribute_info() {
   }

   public void setAttributeName(CPUtf8 attribute_name) {
      this.attribute_name = attribute_name;
   }

   public CPUtf8 getAttributeName() {
      return this.attribute_name;
   }

   protected void setConstantPool(ConstantPool constant_pool) {
      this.constant_pool = constant_pool;
   }

   protected void setParent(AttributeParent parent) {
      this.parent = parent;
   }

   public void read(DataInput in) throws IOException, BadBytecodesException {
      this.attribute_length = in.readInt();
   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      out.writeShort(this.attribute_name.getIndex());
      out.writeInt(this.attribute_length);
   }

   public void changeAttributeLength(int delta) {
      this.attribute_length += delta;
   }

   public void dump(PrintStream out) throws BadBytecodesException {
      out.println("name = " + this.attribute_name.getValue());
      out.println("length = " + this.attribute_length);
   }

   public static void debug(String msg) {
   }

   private static void say(String s) {
      System.out.println(s);
   }
}
