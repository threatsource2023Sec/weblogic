package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Modifier;
import weblogic.utils.classfile.cp.CPUtf8;
import weblogic.utils.classfile.cp.ConstantPool;

public abstract class ClassMember implements AttributeParent {
   protected ConstantPool cp;
   public int accessFlags;
   public CPUtf8 name;
   public CPUtf8 descriptor;
   public AttributeTable attributes;
   private ClassFile classFile;
   private static final boolean debug = false;

   protected ClassMember(ClassFile classFile) {
      this.classFile = classFile;
      this.cp = classFile.getConstantPool();
   }

   public ClassMember(ClassFile classFile, String name, String descriptor, int accessFlags) {
      this(classFile);
      this.name = this.cp.getUtf8(name);
      this.descriptor = this.cp.getUtf8(descriptor);
      this.attributes = new AttributeTable(this, classFile);
      this.accessFlags = accessFlags;
   }

   public String getName() {
      return this.name.toString();
   }

   public String getDescriptor() {
      return this.descriptor.toString();
   }

   public String toString() {
      return this.getName() + "(" + this.getDescriptor() + ")";
   }

   public ClassFile getClassFile() {
      return this.classFile;
   }

   /** @deprecated */
   @Deprecated
   public int getAccessFlags() {
      return this.accessFlags;
   }

   public abstract String getType();

   public ConstantPool getConstantPool() {
      return this.cp;
   }

   public AttributeTable getAttributes() {
      return this.attributes;
   }

   public void read(DataInput in) throws IOException, BadBytecodesException {
      try {
         this.accessFlags = in.readUnsignedShort();
         this.name = this.cp.utf8At(in.readUnsignedShort());
         this.descriptor = this.cp.utf8At(in.readUnsignedShort());
         debug("ClassMember = " + this.name.getValue() + "/" + this.descriptor.getValue());
         this.attributes = new AttributeTable(this, this.classFile);
         this.attributes.read(in);
      } catch (MalformedClassException var3) {
         throw new IOException(String.valueOf(var3));
      }
   }

   public void write(DataOutput out) throws IOException, BadBytecodesException {
      debug("write()");
      out.writeShort(this.accessFlags);
      out.writeShort(this.name.getIndex());
      out.writeShort(this.descriptor.getIndex());
      this.attributes.write(out);
   }

   public void dump(PrintStream out) throws BadBytecodesException {
      out.println(Modifier.toString(this.accessFlags) + " " + this.name.getValue() + "(" + this.descriptor.getValue() + ");");
      this.attributes.dump(out);
   }

   public static void debug(String msg) {
   }

   private static void say(String s) {
      System.out.println(s);
   }
}
