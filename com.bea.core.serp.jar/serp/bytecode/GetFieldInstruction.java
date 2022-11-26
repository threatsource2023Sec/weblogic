package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class GetFieldInstruction extends FieldInstruction {
   GetFieldInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   public int getLogicalStackChange() {
      return this.getOpcode() == 178 ? 1 : 0;
   }

   public int getStackChange() {
      String type = this.getFieldTypeName();
      if (type == null) {
         return 0;
      } else {
         int stack = 0;
         if (Long.TYPE.getName().equals(type) || Double.TYPE.getName().equals(type)) {
            ++stack;
         }

         if (this.getOpcode() == 178) {
            ++stack;
         }

         return stack;
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterGetFieldInstruction(this);
      visit.exitGetFieldInstruction(this);
   }
}
