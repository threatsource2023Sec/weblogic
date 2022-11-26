package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class LoadInstruction extends LocalVariableInstruction {
   private static final Class[][] _mappings;
   String _type = null;

   LoadInstruction(Code owner) {
      super(owner);
   }

   LoadInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   int getLength() {
      switch (this.getOpcode()) {
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
            return super.getLength() + 1;
         default:
            return super.getLength();
      }
   }

   public int getStackChange() {
      switch (this.getOpcode()) {
         case 0:
            return 0;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 23:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 34:
         case 35:
         case 36:
         case 37:
         default:
            return 1;
         case 22:
         case 24:
         case 30:
         case 31:
         case 32:
         case 33:
         case 38:
         case 39:
         case 40:
         case 41:
            return 2;
      }
   }

   public int getLogicalStackChange() {
      switch (this.getOpcode()) {
         case 0:
            return 0;
         default:
            return 1;
      }
   }

   public String getTypeName() {
      switch (this.getOpcode()) {
         case 21:
         case 26:
         case 27:
         case 28:
         case 29:
            return Integer.TYPE.getName();
         case 22:
         case 30:
         case 31:
         case 32:
         case 33:
            return Long.TYPE.getName();
         case 23:
         case 34:
         case 35:
         case 36:
         case 37:
            return Float.TYPE.getName();
         case 24:
         case 38:
         case 39:
         case 40:
         case 41:
            return Double.TYPE.getName();
         case 25:
         case 42:
         case 43:
         case 44:
         case 45:
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
               this.setOpcode(local > 3 ? 24 : 38 + local);
               break;
            case 'e':
            case 'g':
            case 'h':
            case 'j':
            case 'k':
            default:
               this.setOpcode(local > 3 ? 25 : 42 + local);
               break;
            case 'f':
               this.setOpcode(local > 3 ? 23 : 34 + local);
               break;
            case 'i':
               this.setOpcode(local > 3 ? 21 : 26 + local);
               break;
            case 'l':
               this.setOpcode(local > 3 ? 22 : 30 + local);
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

   public LoadInstruction setThis() {
      return (LoadInstruction)this.setLocal(0).setType(Object.class);
   }

   public boolean isThis() {
      return this.getLocal() == 0 && this.getType() == Object.class;
   }

   public boolean equalsInstruction(Instruction other) {
      if (other == this) {
         return true;
      } else if (!super.equalsInstruction(other)) {
         return false;
      } else {
         String type = this.getTypeName();
         String otherType = ((LoadInstruction)other).getTypeName();
         return type == null || otherType == null || type.equals(otherType);
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterLoadInstruction(this);
      visit.exitLoadInstruction(this);
   }

   void read(Instruction orig) {
      super.read(orig);
      LoadInstruction ins = (LoadInstruction)orig;
      this._type = ins._type;
   }

   void read(DataInput in) throws IOException {
      super.read(in);
      switch (this.getOpcode()) {
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
            this.setLocal(in.readUnsignedByte());
         default:
      }
   }

   void write(DataOutput out) throws IOException {
      super.write(out);
      switch (this.getOpcode()) {
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
            out.writeByte(this.getLocal());
         default:
      }
   }

   void calculateOpcode() {
      this.setType(this.getTypeName());
   }

   void calculateLocal() {
      switch (this.getOpcode()) {
         case 26:
         case 30:
         case 34:
         case 38:
         case 42:
            this.setLocal(0);
            break;
         case 27:
         case 31:
         case 35:
         case 39:
         case 43:
            this.setLocal(1);
            break;
         case 28:
         case 32:
         case 36:
         case 40:
         case 44:
            this.setLocal(2);
            break;
         case 29:
         case 33:
         case 37:
         case 41:
         case 45:
            this.setLocal(3);
      }

   }

   static {
      _mappings = new Class[][]{{Byte.TYPE, Integer.TYPE}, {Boolean.TYPE, Integer.TYPE}, {Character.TYPE, Integer.TYPE}, {Short.TYPE, Integer.TYPE}, {Void.TYPE, Integer.TYPE}};
   }
}
