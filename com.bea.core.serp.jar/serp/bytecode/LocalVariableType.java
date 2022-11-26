package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class LocalVariableType extends Local {
   LocalVariableType(LocalVariableTypeTable owner) {
      super(owner);
   }

   public LocalVariableTypeTable getLocalVariableTypeTable() {
      return (LocalVariableTypeTable)this.getTable();
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterLocalVariableType(this);
      visit.exitLocalVariableType(this);
   }
}
