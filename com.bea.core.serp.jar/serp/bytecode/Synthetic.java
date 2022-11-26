package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class Synthetic extends Attribute {
   Synthetic(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterSynthetic(this);
      visit.exitSynthetic(this);
   }
}
