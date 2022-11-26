package antlr;

class TreeElement extends AlternativeBlock {
   GrammarAtom root;

   public TreeElement(Grammar var1, Token var2) {
      super(var1, var2, false);
   }

   public void generate() {
      this.grammar.generator.gen(this);
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }

   public String toString() {
      String var1 = " #(" + this.root;
      Alternative var2 = (Alternative)this.alternatives.elementAt(0);

      for(AlternativeElement var3 = var2.head; var3 != null; var3 = var3.next) {
         var1 = var1 + var3;
      }

      return var1 + " )";
   }
}
