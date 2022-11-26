package antlr;

import antlr.collections.AST;

public class ASTIterator {
   protected AST cursor = null;
   protected AST original = null;

   public ASTIterator(AST var1) {
      this.original = this.cursor = var1;
   }

   public boolean isSubtree(AST var1, AST var2) {
      if (var2 == null) {
         return true;
      } else if (var1 == null) {
         return var2 == null;
      } else {
         for(AST var3 = var1; var3 != null && var2 != null; var2 = var2.getNextSibling()) {
            if (var3.getType() != var2.getType()) {
               return false;
            }

            if (var3.getFirstChild() != null && !this.isSubtree(var3.getFirstChild(), var2.getFirstChild())) {
               return false;
            }

            var3 = var3.getNextSibling();
         }

         return true;
      }
   }

   public AST next(AST var1) {
      Object var2 = null;
      Object var3 = null;
      if (this.cursor == null) {
         return null;
      } else {
         while(this.cursor != null) {
            if (this.cursor.getType() == var1.getType() && this.cursor.getFirstChild() != null && this.isSubtree(this.cursor.getFirstChild(), var1.getFirstChild())) {
               return this.cursor;
            }

            this.cursor = this.cursor.getNextSibling();
         }

         return (AST)var2;
      }
   }
}
