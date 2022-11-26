package antlr;

class TreeBlockContext extends BlockContext {
   protected boolean nextElementIsRoot = true;

   public void addAlternativeElement(AlternativeElement var1) {
      TreeElement var2 = (TreeElement)this.block;
      if (this.nextElementIsRoot) {
         var2.root = (GrammarAtom)var1;
         this.nextElementIsRoot = false;
      } else {
         super.addAlternativeElement(var1);
      }

   }
}
