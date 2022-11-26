package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class Deprecated extends Attribute {
   Deprecated(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterDeprecated(this);
      visit.exitDeprecated(this);
   }
}
