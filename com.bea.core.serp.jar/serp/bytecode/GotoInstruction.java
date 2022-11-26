package serp.bytecode;

public class GotoInstruction extends JumpInstruction {
   GotoInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   public int getStackChange() {
      return this.getOpcode() == 168 ? 1 : 0;
   }

   int getLength() {
      switch (this.getOpcode()) {
         case 200:
         case 201:
            return super.getLength() + 4;
         default:
            return super.getLength() + 2;
      }
   }

   public void setOffset(int offset) {
      super.setOffset(offset);
      this.calculateOpcode();
   }

   private void calculateOpcode() {
      int len = this.getLength();
      int offset;
      switch (this.getOpcode()) {
         case 167:
         case 200:
            offset = this.getOffset();
            if (offset < 131072) {
               this.setOpcode(167);
            } else {
               this.setOpcode(200);
            }
            break;
         case 168:
         case 201:
            offset = this.getOffset();
            if (offset < 131072) {
               this.setOpcode(168);
            } else {
               this.setOpcode(201);
            }
      }

      if (len != this.getLength()) {
         this.invalidateByteIndexes();
      }

   }
}
