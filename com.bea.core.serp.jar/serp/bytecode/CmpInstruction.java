package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class CmpInstruction extends TypedInstruction {
   private static Class[][] _mappings;

   CmpInstruction(Code owner) {
      super(owner);
   }

   CmpInstruction(Code owner, int opcode) {
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
            return 0;
         case 148:
         case 151:
         case 152:
            return -3;
         default:
            return -1;
      }
   }

   public String getTypeName() {
      switch (this.getOpcode()) {
         case 148:
            return Long.TYPE.getName();
         case 149:
         case 150:
            return Float.TYPE.getName();
         case 151:
         case 152:
            return Double.TYPE.getName();
         default:
            return null;
      }
   }

   public TypedInstruction setType(String type) {
      type = this.mapType(type, _mappings, true);
      if (type == null) {
         return (TypedInstruction)this.setOpcode(0);
      } else {
         int opcode = this.getOpcode();
         switch (type.charAt(0)) {
            case 'd':
               if (opcode != 149 && opcode != 151) {
                  return (TypedInstruction)this.setOpcode(152);
               }

               return (TypedInstruction)this.setOpcode(151);
            case 'f':
               if (opcode != 149 && opcode != 151) {
                  return (TypedInstruction)this.setOpcode(150);
               }

               return (TypedInstruction)this.setOpcode(149);
            case 'l':
               return (TypedInstruction)this.setOpcode(148);
            default:
               throw new IllegalStateException();
         }
      }
   }

   public int getNaNValue() {
      switch (this.getOpcode()) {
         case 149:
         case 151:
            return -1;
         case 150:
         case 152:
            return 1;
         default:
            return 0;
      }
   }

   public CmpInstruction setNaNValue(int nan) {
      switch (this.getOpcode()) {
         case 149:
         case 150:
            if (nan == 1) {
               this.setOpcode(150);
            } else if (nan != -1) {
               throw new IllegalArgumentException("Invalid nan for type");
            } else {
               this.setOpcode(149);
            }
         case 151:
         case 152:
            if (nan == 1) {
               this.setOpcode(152);
            } else if (nan != -1) {
               throw new IllegalArgumentException("Invalid nan for type");
            } else {
               this.setOpcode(151);
            }
         default:
            if (nan != 0) {
               throw new IllegalArgumentException("Invalid nan for type");
            } else {
               return this;
            }
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterCmpInstruction(this);
      visit.exitCmpInstruction(this);
   }

   static {
      _mappings = new Class[][]{{Integer.TYPE, Long.TYPE}, {Byte.TYPE, Long.TYPE}, {Character.TYPE, Long.TYPE}, {Short.TYPE, Long.TYPE}, {Boolean.TYPE, Long.TYPE}, {Void.TYPE, Long.TYPE}, {Object.class, Long.TYPE}};
   }
}
