package antlr;

import antlr.collections.AST;

public class DumpASTVisitor implements ASTVisitor {
   protected int level = 0;

   private void tabs() {
      for(int var1 = 0; var1 < this.level; ++var1) {
         System.out.print("   ");
      }

   }

   public void visit(AST var1) {
      boolean var2 = false;

      AST var3;
      for(var3 = var1; var3 != null; var3 = var3.getNextSibling()) {
         if (var3.getFirstChild() != null) {
            var2 = false;
            break;
         }
      }

      for(var3 = var1; var3 != null; var3 = var3.getNextSibling()) {
         if (!var2 || var3 == var1) {
            this.tabs();
         }

         if (var3.getText() == null) {
            System.out.print("nil");
         } else {
            System.out.print(var3.getText());
         }

         System.out.print(" [" + var3.getType() + "] ");
         if (var2) {
            System.out.print(" ");
         } else {
            System.out.println("");
         }

         if (var3.getFirstChild() != null) {
            ++this.level;
            this.visit(var3.getFirstChild());
            --this.level;
         }
      }

      if (var2) {
         System.out.println("");
      }

   }
}
