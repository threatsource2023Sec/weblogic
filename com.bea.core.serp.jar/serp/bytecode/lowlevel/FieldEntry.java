package serp.bytecode.lowlevel;

import serp.bytecode.visitor.BCVisitor;

public class FieldEntry extends ComplexEntry {
   public FieldEntry() {
   }

   public FieldEntry(int classIndex, int nameAndTypeIndex) {
      super(classIndex, nameAndTypeIndex);
   }

   public int getType() {
      return 9;
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterFieldEntry(this);
      visit.exitFieldEntry(this);
   }
}
