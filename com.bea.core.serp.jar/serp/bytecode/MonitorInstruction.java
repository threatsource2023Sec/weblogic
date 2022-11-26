package serp.bytecode;

public abstract class MonitorInstruction extends Instruction {
   MonitorInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   public int getStackChange() {
      return -1;
   }
}
