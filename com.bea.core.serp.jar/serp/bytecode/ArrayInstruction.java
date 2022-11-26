package serp.bytecode;

public abstract class ArrayInstruction extends TypedInstruction {
   ArrayInstruction(Code owner) {
      super(owner);
   }

   ArrayInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }
}
