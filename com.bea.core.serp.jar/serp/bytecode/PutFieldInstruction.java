package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class PutFieldInstruction extends FieldInstruction {
   PutFieldInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   public int getLogicalStackChange() {
      if (this.getFieldTypeName() == null) {
         return 0;
      } else {
         return this.getOpcode() == 179 ? -1 : -2;
      }
   }

   public int getStackChange() {
      String type = this.getFieldTypeName();
      if (type == null) {
         return 0;
      } else {
         int stack = -2;
         if (Long.TYPE.getName().equals(type) || Double.TYPE.getName().equals(type)) {
            ++stack;
         }

         if (this.getOpcode() == 179) {
            ++stack;
         }

         return stack;
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterPutFieldInstruction(this);
      visit.exitPutFieldInstruction(this);
   }
}
