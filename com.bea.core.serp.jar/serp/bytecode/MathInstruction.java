package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class MathInstruction extends TypedInstruction {
   private static final Class[][] _mappings;
   private int _op = -1;
   private String _type = null;

   MathInstruction(Code owner) {
      super(owner);
   }

   MathInstruction(Code owner, int opcode) {
      super(owner, opcode);
      this._op = this.getOperation();
   }

   public int getStackChange() {
      int op = this.getOperation();
      if (op != 116 && this.getOpcode() != 0) {
         String type = this.getTypeName();
         if (!Long.TYPE.getName().equals(type) && !Double.TYPE.getName().equals(type)) {
            return -1;
         } else {
            switch (this.getOpcode()) {
               case 121:
               case 123:
               case 125:
                  return -1;
               case 122:
               case 124:
               default:
                  return -2;
            }
         }
      } else {
         return 0;
      }
   }

   public int getLogicalStackChange() {
      int op = this.getOperation();
      return op != 116 && this.getOpcode() != 0 ? -1 : 0;
   }

   public String getTypeName() {
      switch (this.getOpcode()) {
         case 96:
         case 100:
         case 104:
         case 108:
         case 112:
         case 116:
         case 120:
         case 122:
         case 124:
         case 126:
         case 128:
         case 130:
            return Integer.TYPE.getName();
         case 97:
         case 101:
         case 105:
         case 109:
         case 113:
         case 117:
         case 121:
         case 123:
         case 125:
         case 127:
         case 129:
         case 131:
            return Long.TYPE.getName();
         case 98:
         case 102:
         case 106:
         case 110:
         case 114:
         case 118:
            return Float.TYPE.getName();
         case 99:
         case 103:
         case 107:
         case 111:
         case 115:
         case 119:
            return Double.TYPE.getName();
         default:
            return this._type;
      }
   }

   public TypedInstruction setType(String type) {
      type = this.mapType(type, _mappings, true);
      if (type != null && this._op >= 0) {
         this._type = null;
         switch (type.charAt(0)) {
            case 'd':
               return (TypedInstruction)this.setOpcode(this._op + 3);
            case 'e':
            case 'g':
            case 'h':
            case 'j':
            case 'k':
            default:
               throw new IllegalStateException();
            case 'f':
               return (TypedInstruction)this.setOpcode(this._op + 2);
            case 'i':
               return (TypedInstruction)this.setOpcode(this._op);
            case 'l':
               return (TypedInstruction)this.setOpcode(this._op + 1);
         }
      } else {
         this._type = type;
         return (TypedInstruction)this.setOpcode(0);
      }
   }

   public MathInstruction setOperation(int operation) {
      this._op = operation;
      this.setType(this.getTypeName());
      return this;
   }

   public int getOperation() {
      switch (this.getOpcode()) {
         case 96:
         case 97:
         case 98:
         case 99:
            return 96;
         case 100:
         case 101:
         case 102:
         case 103:
            return 100;
         case 104:
         case 105:
         case 106:
         case 107:
            return 104;
         case 108:
         case 109:
         case 110:
         case 111:
            return 108;
         case 112:
         case 113:
         case 114:
         case 115:
            return 112;
         case 116:
         case 117:
         case 118:
         case 119:
            return 116;
         case 120:
         case 121:
            return 120;
         case 122:
         case 123:
            return 122;
         case 124:
         case 125:
            return 124;
         case 126:
         case 127:
            return 126;
         case 128:
         case 129:
            return 128;
         case 130:
         case 131:
            return 130;
         default:
            return this._op;
      }
   }

   public boolean equalsInstruction(Instruction other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof MathInstruction)) {
         return false;
      } else {
         MathInstruction ins = (MathInstruction)other;
         int op = this.getOperation();
         int otherOp = ins.getOperation();
         boolean opEq = op == -1 || otherOp == -1 || op == otherOp;
         String type = this.getTypeName();
         String otherType = ins.getTypeName();
         boolean typeEq = type == null || otherType == null || type.equals(otherType);
         return opEq && typeEq;
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterMathInstruction(this);
      visit.exitMathInstruction(this);
   }

   void read(Instruction orig) {
      super.read(orig);
      MathInstruction ins = (MathInstruction)orig;
      this._type = ins._type;
      this._op = ins._op;
   }

   static {
      _mappings = new Class[][]{{Byte.TYPE, Integer.TYPE}, {Boolean.TYPE, Integer.TYPE}, {Character.TYPE, Integer.TYPE}, {Short.TYPE, Integer.TYPE}, {Void.TYPE, Integer.TYPE}, {Object.class, Integer.TYPE}};
   }
}
