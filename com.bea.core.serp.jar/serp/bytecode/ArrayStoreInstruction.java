package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class ArrayStoreInstruction extends ArrayInstruction {
   private static final Class[][] _mappings;

   ArrayStoreInstruction(Code owner) {
      super(owner);
   }

   ArrayStoreInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   public int getLogicalStackChange() {
      switch (this.getOpcode()) {
         case 0:
            return 0;
         default:
            return -3;
      }
   }

   public int getStackChange() {
      switch (this.getOpcode()) {
         case 0:
            return 0;
         case 80:
         case 82:
            return -4;
         default:
            return -3;
      }
   }

   public String getTypeName() {
      switch (this.getOpcode()) {
         case 79:
            return Integer.TYPE.getName();
         case 80:
            return Long.TYPE.getName();
         case 81:
            return Float.TYPE.getName();
         case 82:
            return Double.TYPE.getName();
         case 83:
            return Object.class.getName();
         case 84:
            return Byte.TYPE.getName();
         case 85:
            return Character.TYPE.getName();
         case 86:
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
               return (TypedInstruction)this.setOpcode(84);
            case 'c':
               return (TypedInstruction)this.setOpcode(85);
            case 'd':
               return (TypedInstruction)this.setOpcode(82);
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
               return (TypedInstruction)this.setOpcode(83);
            case 'f':
               return (TypedInstruction)this.setOpcode(81);
            case 'i':
               return (TypedInstruction)this.setOpcode(79);
            case 'l':
               return (TypedInstruction)this.setOpcode(80);
            case 's':
               return (TypedInstruction)this.setOpcode(86);
         }
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterArrayStoreInstruction(this);
      visit.exitArrayStoreInstruction(this);
   }

   static {
      _mappings = new Class[][]{{Boolean.TYPE, Integer.TYPE}, {Void.TYPE, Integer.TYPE}};
   }
}
