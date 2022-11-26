package serp.bytecode.lowlevel;

import serp.bytecode.visitor.BCVisitor;

public class MethodEntry extends ComplexEntry {
   public MethodEntry() {
   }

   public MethodEntry(int classIndex, int nameAndTypeIndex) {
      super(classIndex, nameAndTypeIndex);
   }

   public int getType() {
      return 10;
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterMethodEntry(this);
      visit.exitMethodEntry(this);
   }
}
