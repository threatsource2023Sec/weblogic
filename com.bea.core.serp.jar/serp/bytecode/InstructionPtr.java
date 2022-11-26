package serp.bytecode;

public interface InstructionPtr {
   void updateTargets();

   void replaceTarget(Instruction var1, Instruction var2);

   Code getCode();
}
