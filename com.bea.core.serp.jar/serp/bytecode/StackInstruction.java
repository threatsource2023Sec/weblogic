package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class StackInstruction extends TypedInstruction {
   StackInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   public int getStackChange() {
      switch (this.getOpcode()) {
         case 87:
            return -1;
         case 88:
            return -2;
         case 89:
         case 90:
         case 91:
            return 1;
         case 92:
         case 93:
         case 94:
            return 2;
         default:
            return 0;
      }
   }

   public String getTypeName() {
      return null;
   }

   public TypedInstruction setType(String type) {
      type = this.getProject().getNameCache().getExternalForm(type, false);
      return this.setWide(Long.TYPE.getName().equals(type) || Double.TYPE.getName().equals(type));
   }

   public boolean isWide() {
      switch (this.getOpcode()) {
         case 88:
         case 92:
         case 93:
         case 94:
            return true;
         case 89:
         case 90:
         case 91:
         default:
            return false;
      }
   }

   public StackInstruction setWide(boolean wide) {
      switch (this.getOpcode()) {
         case 87:
            if (wide) {
               this.setOpcode(88);
            }
            break;
         case 88:
            if (!wide) {
               this.setOpcode(87);
            }
            break;
         case 89:
            if (wide) {
               this.setOpcode(92);
            }
            break;
         case 90:
            if (wide) {
               this.setOpcode(93);
            }
            break;
         case 91:
            if (wide) {
               this.setOpcode(94);
            }
            break;
         case 92:
            if (!wide) {
               this.setOpcode(89);
            }
            break;
         case 93:
            if (!wide) {
               this.setOpcode(90);
            }
            break;
         case 94:
            if (!wide) {
               this.setOpcode(91);
            }
      }

      return this;
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterStackInstruction(this);
      visit.exitStackInstruction(this);
   }
}
