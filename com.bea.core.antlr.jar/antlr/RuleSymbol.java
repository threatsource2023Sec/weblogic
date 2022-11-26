package antlr;

import antlr.collections.impl.Vector;

class RuleSymbol extends GrammarSymbol {
   RuleBlock block;
   boolean defined;
   Vector references = new Vector();
   String access;
   String comment;

   public RuleSymbol(String var1) {
      super(var1);
   }

   public void addReference(RuleRefElement var1) {
      this.references.appendElement(var1);
   }

   public RuleBlock getBlock() {
      return this.block;
   }

   public RuleRefElement getReference(int var1) {
      return (RuleRefElement)this.references.elementAt(var1);
   }

   public boolean isDefined() {
      return this.defined;
   }

   public int numReferences() {
      return this.references.size();
   }

   public void setBlock(RuleBlock var1) {
      this.block = var1;
   }

   public void setDefined() {
      this.defined = true;
   }
}
