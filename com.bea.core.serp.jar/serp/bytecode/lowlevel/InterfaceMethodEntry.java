package serp.bytecode.lowlevel;

import serp.bytecode.visitor.BCVisitor;

public class InterfaceMethodEntry extends ComplexEntry {
   public InterfaceMethodEntry() {
   }

   public InterfaceMethodEntry(int classIndex, int nameAndTypeIndex) {
      super(classIndex, nameAndTypeIndex);
   }

   public int getType() {
      return 11;
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterInterfaceMethodEntry(this);
      visit.exitInterfaceMethodEntry(this);
   }
}
