package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class ArrayLoadInstruction extends ArrayInstruction {
   private static final Class[][] _mappings;

   ArrayLoadInstruction(Code owner) {
      super(owner);
   }

   ArrayLoadInstruction(Code owner, int opcode) {
      super(owner, opcode);
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
         case 47:
         case 49:
            return 0;
         default:
            return -1;
      }
   }

   public String getTypeName() {
      switch (this.getOpcode()) {
         case 46:
            return Integer.TYPE.getName();
         case 47:
            return Long.TYPE.getName();
         case 48:
            return Float.TYPE.getName();
         case 49:
            return Double.TYPE.getName();
         case 50:
            return Object.class.getName();
         case 51:
            return Byte.TYPE.getName();
         case 52:
            return Character.TYPE.getName();
         case 53:
            return Short.TYPE.getName();
         default:
            return null;
      }
   }

   public TypedInstruction setType(String type) {
      type = this.mapType(type, _mappings, true);
      if (type == null) {
         return (TypedInstruction)this.setOpcode(0);
      } else {
         switch (type.charAt(0)) {
            case 'b':
               return (TypedInstruction)this.setOpcode(51);
            case 'c':
               return (TypedInstruction)this.setOpcode(52);
            case 'd':
               return (TypedInstruction)this.setOpcode(49);
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
               return (TypedInstruction)this.setOpcode(50);
            case 'f':
               return (TypedInstruction)this.setOpcode(48);
            case 'i':
               return (TypedInstruction)this.setOpcode(46);
            case 'l':
               return (TypedInstruction)this.setOpcode(47);
            case 's':
               return (TypedInstruction)this.setOpcode(53);
         }
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterArrayLoadInstruction(this);
      visit.exitArrayLoadInstruction(this);
   }

   static {
      _mappings = new Class[][]{{Boolean.TYPE, Integer.TYPE}, {Void.TYPE, Integer.TYPE}};
   }
}
