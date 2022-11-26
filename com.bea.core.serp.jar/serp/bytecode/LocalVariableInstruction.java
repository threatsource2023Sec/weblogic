package serp.bytecode;

public abstract class LocalVariableInstruction extends TypedInstruction {
   private int _index = -1;

   LocalVariableInstruction(Code owner) {
      super(owner);
   }

   LocalVariableInstruction(Code owner, int opcode) {
      super(owner, opcode);
      this.calculateLocal();
   }

   public String getTypeName() {
      return null;
   }

   public TypedInstruction setType(String type) {
      throw new UnsupportedOperationException();
   }

   public int getLocal() {
      return this._index;
   }

   public LocalVariableInstruction setLocal(int index) {
      this._index = index;
      this.calculateOpcode();
      return this;
   }

   public int getParam() {
      return this.getCode().getParamsIndex(this.getLocal());
   }

   public LocalVariableInstruction setParam(int param) {
      int local = this.getCode().getLocalsIndex(param);
      if (local != -1) {
         BCMethod method = this.getCode().getMethod();
         this.setType(method.getParamNames()[param]);
      }

      return this.setLocal(local);
   }

   public LocalVariable getLocalVariable() {
      LocalVariableTable table = this.getCode().getLocalVariableTable(false);
      return table == null ? null : table.getLocalVariable(this.getLocal());
   }

   public LocalVariableInstruction setLocalVariable(LocalVariable local) {
      if (local == null) {
         return this.setLocal(-1);
      } else {
         String type = local.getTypeName();
         if (type != null) {
            this.setType(type);
         }

         return this.setLocal(local.getLocal());
      }
   }

   public boolean equalsInstruction(Instruction other) {
      if (this == other) {
         return true;
      } else if (!this.getClass().equals(other.getClass())) {
         return false;
      } else {
         LocalVariableInstruction ins = (LocalVariableInstruction)other;
         int index = this.getLocal();
         int insIndex = ins.getLocal();
         return index == -1 || insIndex == -1 || index == insIndex;
      }
   }

   void read(Instruction orig) {
      super.read(orig);
      this.setLocal(((LocalVariableInstruction)orig).getLocal());
   }

   void calculateOpcode() {
   }

   void calculateLocal() {
   }
}
