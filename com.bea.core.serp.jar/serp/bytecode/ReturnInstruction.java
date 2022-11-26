package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class ReturnInstruction extends TypedInstruction {
   private static final Class[][] _mappings;

   ReturnInstruction(Code owner) {
      super(owner);
   }

   ReturnInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   public String getTypeName() {
      switch (this.getOpcode()) {
         case 172:
            return Integer.TYPE.getName();
         case 173:
            return Long.TYPE.getName();
         case 174:
            return Float.TYPE.getName();
         case 175:
            return Double.TYPE.getName();
         case 176:
            return Object.class.getName();
         case 177:
            return Void.TYPE.getName();
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
            case 'd':
               return (TypedInstruction)this.setOpcode(175);
            case 'f':
               return (TypedInstruction)this.setOpcode(174);
            case 'i':
               return (TypedInstruction)this.setOpcode(172);
            case 'l':
               return (TypedInstruction)this.setOpcode(173);
            case 'v':
               return (TypedInstruction)this.setOpcode(177);
            default:
               return (TypedInstruction)this.setOpcode(176);
         }
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
         case 177:
            return 0;
         case 173:
         case 175:
            return -2;
         default:
            return -1;
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterReturnInstruction(this);
      visit.exitReturnInstruction(this);
   }

   static {
      _mappings = new Class[][]{{Byte.TYPE, Integer.TYPE}, {Character.TYPE, Integer.TYPE}, {Short.TYPE, Integer.TYPE}, {Boolean.TYPE, Integer.TYPE}};
   }
}
