package antlr.debug.misc;

import antlr.collections.AST;
import java.util.NoSuchElementException;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class JTreeASTModel implements TreeModel {
   AST root = null;

   public JTreeASTModel(AST var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("root is null");
      } else {
         this.root = var1;
      }
   }

   public void addTreeModelListener(TreeModelListener var1) {
   }

   public Object getChild(Object var1, int var2) {
      if (var1 == null) {
         return null;
      } else {
         AST var3 = (AST)var1;
         AST var4 = var3.getFirstChild();
         if (var4 == null) {
            throw new ArrayIndexOutOfBoundsException("node has no children");
         } else {
            for(int var5 = 0; var4 != null && var5 < var2; ++var5) {
               var4 = var4.getNextSibling();
            }

            return var4;
         }
      }
   }

   public int getChildCount(Object var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("root is null");
      } else {
         AST var2 = (AST)var1;
         AST var3 = var2.getFirstChild();

         int var4;
         for(var4 = 0; var3 != null; ++var4) {
            var3 = var3.getNextSibling();
         }

         return var4;
      }
   }

   public int getIndexOfChild(Object var1, Object var2) {
      if (var1 != null && var2 != null) {
         AST var3 = (AST)var1;
         AST var4 = var3.getFirstChild();
         if (var4 == null) {
            throw new ArrayIndexOutOfBoundsException("node has no children");
         } else {
            int var5;
            for(var5 = 0; var4 != null && var4 != var2; ++var5) {
               var4 = var4.getNextSibling();
            }

            if (var4 == var2) {
               return var5;
            } else {
               throw new NoSuchElementException("node is not a child");
            }
         }
      } else {
         throw new IllegalArgumentException("root or child is null");
      }
   }

   public Object getRoot() {
      return this.root;
   }

   public boolean isLeaf(Object var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("node is null");
      } else {
         AST var2 = (AST)var1;
         return var2.getFirstChild() == null;
      }
   }

   public void removeTreeModelListener(TreeModelListener var1) {
   }

   public void valueForPathChanged(TreePath var1, Object var2) {
      System.out.println("heh, who is calling this mystery method?");
   }
}
