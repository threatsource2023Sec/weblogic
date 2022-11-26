package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class StoreInstruction extends LocalVariableInstruction {
   private static final Class[][] _mappings;
   String _type = null;

   StoreInstruction(Code owner) {
      super(owner);
   }

   StoreInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   int getLength() {
      switch (this.getOpcode()) {
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
            return super.getLength() + 1;
         default:
            return super.getLength();
      }
   }

   public int getLogicalStackChange() {
      switch (this.getOpcode()) {
         case 0:
            return 0;
         default:
            return -1;
      }
   }

   public int getStackChange() {
      switch (this.getOpcode()) {
         case 0:
            return 0;
         case 55:
         case 57:
         case 63:
         case 64:
         case 65:
         case 66:
         case 71:
         case 72:
         case 73:
         case 74:
            return -2;
         default:
            return -1;
      }
   }

   public String getTypeName() {
      switch (this.getOpcode()) {
         case 54:
         case 59:
         case 60:
         case 61:
         case 62:
            return Integer.TYPE.getName();
         case 55:
         case 63:
         case 64:
         case 65:
         case 66:
            return Long.TYPE.getName();
         case 56:
         case 67:
         case 68:
         case 69:
         case 70:
            return Float.TYPE.getName();
         case 57:
         case 71:
         case 72:
         case 73:
         case 74:
            return Double.TYPE.getName();
         case 58:
         case 75:
         case 76:
         case 77:
         case 78:
            return Object.class.getName();
         default:
            return this._type;
      }
   }

   public TypedInstruction setType(String type) {
      type = this.mapType(type, _mappings, true);
      int local = this.getLocal();
      int len = this.getLength();
      if (type != null && local >= 0) {
         this._type = null;
         switch (type.charAt(0)) {
            case 'd':
               this.setOpcode(local > 3 ? 57 : 71 + local);
               break;
            case 'e':
            case 'g':
            case 'h':
            case 'j':
            case 'k':
            default:
               this.setOpcode(local > 3 ? 58 : 75 + local);
               break;
            case 'f':
               this.setOpcode(local > 3 ? 56 : 67 + local);
               break;
            case 'i':
               this.setOpcode(local > 3 ? 54 : 59 + local);
               break;
            case 'l':
               this.setOpcode(local > 3 ? 55 : 63 + local);
         }
      } else {
         this._type = type;
         this.setOpcode(0);
      }

      if (len != this.getLength()) {
         this.invalidateByteIndexes();
      }

      return this;
   }

   public boolean equalsInstruction(Instruction other) {
      if (other == this) {
         return true;
      } else if (!super.equalsInstruction(other)) {
         return false;
      } else {
         String type = this.getTypeName();
         String otherType = ((StoreInstruction)other).getTypeName();
         return type == null || otherType == null || type.equals(otherType);
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterStoreInstruction(this);
      visit.exitStoreInstruction(this);
   }

   void read(Instruction orig) {
      super.read(orig);
      StoreInstruction ins = (StoreInstruction)orig;
      this._type = ins._type;
   }

   void read(DataInput in) throws IOException {
      super.read(in);
      switch (this.getOpcode()) {
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
            this.setLocal(in.readUnsignedByte());
         default:
      }
   }

   void write(DataOutput out) throws IOException {
      super.write(out);
      switch (this.getOpcode()) {
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
            out.writeByte(this.getLocal());
         default:
      }
   }

   void calculateOpcode() {
      this.setType(this.getTypeName());
   }

   void calculateLocal() {
      switch (this.getOpcode()) {
         case 59:
         case 63:
         case 67:
         case 71:
         case 75:
            this.setLocal(0);
            break;
         case 60:
         case 64:
         case 68:
         case 72:
         case 76:
            this.setLocal(1);
            break;
         case 61:
         case 65:
         case 69:
         case 73:
         case 77:
            this.setLocal(2);
            break;
         case 62:
         case 66:
         case 70:
         case 74:
         case 78:
            this.setLocal(3);
      }

   }

   static {
      _mappings = new Class[][]{{Byte.TYPE, Integer.TYPE}, {Boolean.TYPE, Integer.TYPE}, {Character.TYPE, Integer.TYPE}, {Short.TYPE, Integer.TYPE}, {Void.TYPE, Integer.TYPE}};
   }
}
