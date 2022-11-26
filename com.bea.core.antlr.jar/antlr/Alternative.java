package antlr;

class Alternative {
   AlternativeElement head;
   AlternativeElement tail;
   protected SynPredBlock synPred;
   protected String semPred;
   protected ExceptionSpec exceptionSpec;
   protected Lookahead[] cache;
   protected int lookaheadDepth;
   protected Token treeSpecifier = null;
   private boolean doAutoGen;

   public Alternative() {
   }

   public Alternative(AlternativeElement var1) {
      this.addElement(var1);
   }

   public void addElement(AlternativeElement var1) {
      if (this.head == null) {
         this.head = this.tail = var1;
      } else {
         this.tail.next = var1;
         this.tail = var1;
      }

   }

   public boolean atStart() {
      return this.head == null;
   }

   public boolean getAutoGen() {
      return this.doAutoGen && this.treeSpecifier == null;
   }

   public Token getTreeSpecifier() {
      return this.treeSpecifier;
   }

   public void setAutoGen(boolean var1) {
      this.doAutoGen = var1;
   }
}
