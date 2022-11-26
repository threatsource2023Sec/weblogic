package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.BCVisitor;

public class WideInstruction extends LocalVariableInstruction {
   private static final Class[][] _mappings;
   private int _ins = 0;
   private int _inc = -1;

   WideInstruction(Code owner) {
      super(owner, 196);
   }

   int getLength() {
      int length = super.getLength() + 1 + 2;
      if (this._ins == 132) {
         length += 2;
      }

      return length;
   }

   public int getStackChange() {
      switch (this._ins) {
         case 21:
         case 23:
         case 25:
            return 1;
         case 22:
         case 24:
            return 2;
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         default:
            return 0;
         case 54:
         case 56:
         case 58:
            return -1;
         case 55:
         case 57:
            return -2;
      }
   }

   public int getLogicalStackChange() {
      switch (this._ins) {
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
            return 1;
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         default:
            return 0;
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
            return -1;
      }
   }

   public String getTypeName() {
      switch (this._ins) {
         case 21:
         case 54:
            return Integer.TYPE.getName();
         case 22:
         case 55:
            return Long.TYPE.getName();
         case 23:
         case 56:
            return Float.TYPE.getName();
         case 24:
         case 57:
            return Double.TYPE.getName();
         case 25:
         case 58:
            return Object.class.getName();
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         default:
            return null;
      }
   }

   public TypedInstruction setType(String type) {
      type = this.mapType(type, _mappings, true);
      switch (this._ins) {
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
            if (type == null) {
               throw new IllegalStateException();
            } else {
               switch (type.charAt(0)) {
                  case 'd':
                     return this.setInstruction(24);
                  case 'e':
                  case 'g':
                  case 'h':
                  case 'j':
                  case 'k':
                  default:
                     return this.setInstruction(25);
                  case 'f':
                     return this.setInstruction(23);
                  case 'i':
                     return this.setInstruction(21);
                  case 'l':
                     return this.setInstruction(22);
               }
            }
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         default:
            if (type != null) {
               throw new IllegalStateException("Augmented instruction not typed");
            }

            return this;
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
            if (type == null) {
               throw new IllegalStateException();
            } else {
               switch (type.charAt(0)) {
                  case 'd':
                     return this.setInstruction(57);
                  case 'e':
                  case 'g':
                  case 'h':
                  case 'j':
                  case 'k':
                  default:
                     return this.setInstruction(58);
                  case 'f':
                     return this.setInstruction(56);
                  case 'i':
                     return this.setInstruction(54);
                  case 'l':
                     return this.setInstruction(55);
               }
            }
      }
   }

   public int getInstruction() {
      return this._ins;
   }

   public WideInstruction setInstruction(Instruction ins) {
      if (ins == null) {
         return this.setInstruction(0);
      } else {
         this.setInstruction(ins.getOpcode());
         if (this._ins == 132) {
            this._inc = ((IIncInstruction)ins).getIncrement();
         }

         return this;
      }
   }

   public WideInstruction setInstruction(int opcode) {
      int len = this.getLength();
      this._ins = opcode;
      if (len != this.getLength()) {
         this.invalidateByteIndexes();
      }

      return this;
   }

   public WideInstruction iinc() {
      return this.setInstruction(132);
   }

   public WideInstruction ret() {
      return this.setInstruction(169);
   }

   public WideInstruction iload() {
      return this.setInstruction(21);
   }

   public WideInstruction fload() {
      return this.setInstruction(23);
   }

   public WideInstruction aload() {
      return this.setInstruction(25);
   }

   public WideInstruction lload() {
      return this.setInstruction(22);
   }

   public WideInstruction dload() {
      return this.setInstruction(24);
   }

   public WideInstruction istore() {
      return this.setInstruction(54);
   }

   public WideInstruction fstore() {
      return this.setInstruction(56);
   }

   public WideInstruction astore() {
      return this.setInstruction(58);
   }

   public WideInstruction lstore() {
      return this.setInstruction(55);
   }

   public WideInstruction dstore() {
      return this.setInstruction(57);
   }

   public int getIncrement() {
      return this._inc;
   }

   public WideInstruction setIncrement(int val) {
      this._inc = val;
      return this;
   }

   public boolean equalsInstruction(Instruction other) {
      if (other == this) {
         return true;
      } else if (!super.equalsInstruction(other)) {
         return false;
      } else if (!(other instanceof WideInstruction)) {
         return false;
      } else {
         WideInstruction ins = (WideInstruction)other;
         int code = this.getInstruction();
         int otherCode = ins.getInstruction();
         if (code != otherCode) {
            return false;
         } else if (code != 132) {
            return true;
         } else {
            int inc = this.getIncrement();
            int otherInc = ins.getIncrement();
            return inc == -1 || otherInc == -1 || inc == otherInc;
         }
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterWideInstruction(this);
      visit.exitWideInstruction(this);
   }

   void read(Instruction orig) {
      super.read(orig);
      this.setInstruction(((WideInstruction)orig).getInstruction());
      this.setIncrement(((WideInstruction)orig).getIncrement());
   }

   void read(DataInput in) throws IOException {
      super.read(in);
      this._ins = in.readUnsignedByte();
      this.setLocal(in.readUnsignedShort());
      if (this._ins == 132) {
         this._inc = in.readUnsignedShort();
      }

   }

   void write(DataOutput out) throws IOException {
      super.write(out);
      out.writeByte(this._ins);
      out.writeShort(this.getLocal());
      if (this._ins == 132) {
         out.writeShort(this._inc);
      }

   }

   static {
      _mappings = new Class[][]{{Byte.TYPE, Integer.TYPE}, {Boolean.TYPE, Integer.TYPE}, {Character.TYPE, Integer.TYPE}, {Short.TYPE, Integer.TYPE}, {Void.TYPE, Integer.TYPE}};
   }
}
