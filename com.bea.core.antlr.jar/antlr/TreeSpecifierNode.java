package antlr;

class TreeSpecifierNode {
   private TreeSpecifierNode parent = null;
   private TreeSpecifierNode firstChild = null;
   private TreeSpecifierNode nextSibling = null;
   private Token tok;

   TreeSpecifierNode(Token var1) {
      this.tok = var1;
   }

   public TreeSpecifierNode getFirstChild() {
      return this.firstChild;
   }

   public TreeSpecifierNode getNextSibling() {
      return this.nextSibling;
   }

   public TreeSpecifierNode getParent() {
      return this.parent;
   }

   public Token getToken() {
      return this.tok;
   }

   public void setFirstChild(TreeSpecifierNode var1) {
      this.firstChild = var1;
      var1.parent = this;
   }

   public void setNextSibling(TreeSpecifierNode var1) {
      this.nextSibling = var1;
      var1.parent = this.parent;
   }
}
