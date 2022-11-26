package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class NewArrayInstruction extends TypedInstruction {
   private static final Class[][] _mappings;
   private int _code = -1;

   NewArrayInstruction(Code owner) {
      super(owner, 188);
   }

   int getLength() {
      return super.getLength() + 1;
   }

   public String getTypeName() {
      switch (this.getTypeCode()) {
         case 4:
            return Boolean.TYPE.getName();
         case 5:
            return Character.TYPE.getName();
         case 6:
            return Float.TYPE.getName();
         case 7:
            return Double.TYPE.getName();
         case 8:
            return Byte.TYPE.getName();
         case 9:
            return Short.TYPE.getName();
         case 10:
            return Integer.TYPE.getName();
         case 11:
            return Long.TYPE.getName();
         default:
            return null;
      }
   }

   public TypedInstruction setType(String type) {
      type = this.mapType(type, _mappings, true);
      if (type == null) {
         return this.setTypeCode(-1);
      } else {
         switch (type.charAt(0)) {
            case 'b':
               if (Boolean.TYPE.getName().equals(type)) {
                  return this.setTypeCode(4);
               }

               return this.setTypeCode(8);
            case 'c':
               return this.setTypeCode(5);
            case 'd':
               return this.setTypeCode(7);
            case 'e':
            case 'g':
            case 'h':
            case 'j':
            case 'k':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            default:
               throw new IllegalStateException();
            case 'f':
               return this.setTypeCode(6);
            case 'i':
               return this.setTypeCode(10);
            case 'l':
               return this.setTypeCode(11);
            case 's':
               return this.setTypeCode(9);
         }
      }
   }

   public int getTypeCode() {
      return this._code;
   }

   public NewArrayInstruction setTypeCode(int code) {
      this._code = code;
      return this;
   }

   public boolean equalsInstruction(Instruction other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof NewArrayInstruction)) {
         return false;
      } else {
         NewArrayInstruction ins = (NewArrayInstruction)other;
         int code = this.getTypeCode();
         int otherCode = ins.getTypeCode();
         return code == -1 || otherCode == -1 || code == otherCode;
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterNewArrayInstruction(this);
      visit.exitNewArrayInstruction(this);
   }

   void read(Instruction orig) {
      super.read(orig);
      this._code = ((NewArrayInstruction)orig).getTypeCode();
   }

   void read(DataInput in) throws IOException {
      super.read(in);
      this._code = in.readUnsignedByte();
   }

   void write(DataOutput out) throws IOException {
      super.write(out);
      out.writeByte(this._code);
   }

   static {
      _mappings = new Class[][]{{Void.TYPE, Integer.TYPE}, {Object.class, Integer.TYPE}};
   }
}
