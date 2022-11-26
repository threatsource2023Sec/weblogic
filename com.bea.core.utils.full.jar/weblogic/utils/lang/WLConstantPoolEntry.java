package weblogic.utils.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WLConstantPoolEntry implements Writable {
   private static final int CONSTANT_Class = 7;
   private static final int CONSTANT_Fieldref = 9;
   private static final int CONSTANT_Methodref = 10;
   private static final int CONSTANT_InterfaceMethodref = 11;
   private static final int CONSTANT_String = 8;
   private static final int CONSTANT_Integer = 3;
   private static final int CONSTANT_Float = 4;
   private static final int CONSTANT_Long = 5;
   private static final int CONSTANT_Double = 6;
   private static final int CONSTANT_NameAndType = 12;
   private static final int CONSTANT_Utf8 = 1;
   byte tag;
   Writable info;

   public void write(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeByte(this.tag);
      this.info.write(os);
   }

   public WLConstantPoolEntry read(InputStream is) throws IOException {
      DataInputStream dis = new DataInputStream(is);
      this.tag = (byte)dis.read();
      switch (this.tag) {
         case 1:
            this.info = (new Utf8Info()).read(is);
            break;
         case 2:
         default:
            throw new Error("Unexpected tag: " + this.tag);
         case 3:
            this.info = (new IntegerInfo()).read(is);
            break;
         case 4:
            this.info = (new FloatInfo()).read(is);
            break;
         case 5:
            this.info = (new LongInfo()).read(is);
            break;
         case 6:
            this.info = (new DoubleInfo()).read(is);
            break;
         case 7:
            this.info = (new ClassInfo()).read(is);
            break;
         case 8:
            this.info = (new StringInfo()).read(is);
            break;
         case 9:
            this.info = (new FieldRef()).read(is);
            break;
         case 10:
            this.info = (new MethodRef()).read(is);
            break;
         case 11:
            this.info = (new InterfaceMethodRef()).read(is);
            break;
         case 12:
            this.info = (new NameAndTypeInfo()).read(is);
      }

      return this;
   }
}
