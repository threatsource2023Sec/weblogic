package weblogic.application.descriptor;

class VariableAssignment {
   static final int OP_DEFAULT = -1;
   static final int OP_ADD = 1;
   static final int OP_REMOVE = 2;
   static final int OP_REPLACE = 3;
   String name;
   String value;
   int op;

   public VariableAssignment(String name, String value, String operation) {
      this.name = name;
      this.value = value;
      this.setOperation(operation);
   }

   public void setOperation(String value) {
      if (value.equals("add")) {
         this.op = 1;
      } else if (value.equals("remove")) {
         this.op = 2;
      } else if (value.equals("replace")) {
         this.op = 3;
      } else {
         this.op = -1;
      }

   }

   public String getName() {
      return this.name;
   }

   public String getValue() {
      return this.value;
   }

   public int getOperation() {
      return this.op;
   }
}
