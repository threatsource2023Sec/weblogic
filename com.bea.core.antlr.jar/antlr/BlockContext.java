package antlr;

class BlockContext {
   AlternativeBlock block;
   int altNum;
   BlockEndElement blockEnd;

   public void addAlternativeElement(AlternativeElement var1) {
      this.currentAlt().addElement(var1);
   }

   public Alternative currentAlt() {
      return (Alternative)this.block.alternatives.elementAt(this.altNum);
   }

   public AlternativeElement currentElement() {
      return this.currentAlt().tail;
   }
}
