package antlr;

import antlr.collections.AST;
import antlr.collections.ASTEnumeration;
import antlr.collections.impl.ASTEnumerator;
import antlr.collections.impl.Vector;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public abstract class BaseAST implements AST, Serializable {
   protected BaseAST down;
   protected BaseAST right;
   private static boolean verboseStringConversion = false;
   private static String[] tokenNames = null;

   public void addChild(AST var1) {
      if (var1 != null) {
         BaseAST var2 = this.down;
         if (var2 == null) {
            this.down = (BaseAST)var1;
         } else {
            while(var2.right != null) {
               var2 = var2.right;
            }

            var2.right = (BaseAST)var1;
         }

      }
   }

   public int getNumberOfChildren() {
      BaseAST var1 = this.down;
      int var2 = 0;
      if (var1 == null) {
         return var2;
      } else {
         for(var2 = 1; var1.right != null; ++var2) {
            var1 = var1.right;
         }

         return var2;
      }
   }

   private static void doWorkForFindAll(AST var0, Vector var1, AST var2, boolean var3) {
      for(AST var4 = var0; var4 != null; var4 = var4.getNextSibling()) {
         if (var3 && var4.equalsTreePartial(var2) || !var3 && var4.equalsTree(var2)) {
            var1.appendElement(var4);
         }

         if (var4.getFirstChild() != null) {
            doWorkForFindAll(var4.getFirstChild(), var1, var2, var3);
         }
      }

   }

   public boolean equals(AST var1) {
      if (var1 == null) {
         return false;
      } else if (this.getText() == null && var1.getText() != null || this.getText() != null && var1.getText() == null) {
         return false;
      } else if (this.getText() == null && var1.getText() == null) {
         return this.getType() == var1.getType();
      } else {
         return this.getText().equals(var1.getText()) && this.getType() == var1.getType();
      }
   }

   public boolean equalsList(AST var1) {
      if (var1 == null) {
         return false;
      } else {
         Object var2;
         for(var2 = this; var2 != null && var1 != null; var1 = var1.getNextSibling()) {
            if (!((AST)var2).equals(var1)) {
               return false;
            }

            if (((AST)var2).getFirstChild() != null) {
               if (!((AST)var2).getFirstChild().equalsList(var1.getFirstChild())) {
                  return false;
               }
            } else if (var1.getFirstChild() != null) {
               return false;
            }

            var2 = ((AST)var2).getNextSibling();
         }

         return var2 == null && var1 == null;
      }
   }

   public boolean equalsListPartial(AST var1) {
      if (var1 == null) {
         return true;
      } else {
         Object var2;
         for(var2 = this; var2 != null && var1 != null; var1 = var1.getNextSibling()) {
            if (!((AST)var2).equals(var1)) {
               return false;
            }

            if (((AST)var2).getFirstChild() != null && !((AST)var2).getFirstChild().equalsListPartial(var1.getFirstChild())) {
               return false;
            }

            var2 = ((AST)var2).getNextSibling();
         }

         return var2 != null || var1 == null;
      }
   }

   public boolean equalsTree(AST var1) {
      if (!this.equals(var1)) {
         return false;
      } else {
         if (this.getFirstChild() != null) {
            if (!this.getFirstChild().equalsList(var1.getFirstChild())) {
               return false;
            }
         } else if (var1.getFirstChild() != null) {
            return false;
         }

         return true;
      }
   }

   public boolean equalsTreePartial(AST var1) {
      if (var1 == null) {
         return true;
      } else if (!this.equals(var1)) {
         return false;
      } else {
         return this.getFirstChild() == null || this.getFirstChild().equalsListPartial(var1.getFirstChild());
      }
   }

   public ASTEnumeration findAll(AST var1) {
      Vector var2 = new Vector(10);
      if (var1 == null) {
         return null;
      } else {
         doWorkForFindAll(this, var2, var1, false);
         return new ASTEnumerator(var2);
      }
   }

   public ASTEnumeration findAllPartial(AST var1) {
      Vector var2 = new Vector(10);
      if (var1 == null) {
         return null;
      } else {
         doWorkForFindAll(this, var2, var1, true);
         return new ASTEnumerator(var2);
      }
   }

   public AST getFirstChild() {
      return this.down;
   }

   public AST getNextSibling() {
      return this.right;
   }

   public String getText() {
      return "";
   }

   public int getType() {
      return 0;
   }

   public int getLine() {
      return 0;
   }

   public int getColumn() {
      return 0;
   }

   public abstract void initialize(int var1, String var2);

   public abstract void initialize(AST var1);

   public abstract void initialize(Token var1);

   public void removeChildren() {
      this.down = null;
   }

   public void setFirstChild(AST var1) {
      this.down = (BaseAST)var1;
   }

   public void setNextSibling(AST var1) {
      this.right = (BaseAST)var1;
   }

   public void setText(String var1) {
   }

   public void setType(int var1) {
   }

   public static void setVerboseStringConversion(boolean var0, String[] var1) {
      verboseStringConversion = var0;
      tokenNames = var1;
   }

   public static String[] getTokenNames() {
      return tokenNames;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (verboseStringConversion && this.getText() != null && !this.getText().equalsIgnoreCase(tokenNames[this.getType()]) && !this.getText().equalsIgnoreCase(StringUtils.stripFrontBack(tokenNames[this.getType()], "\"", "\""))) {
         var1.append('[');
         var1.append(this.getText());
         var1.append(",<");
         var1.append(tokenNames[this.getType()]);
         var1.append(">]");
         return var1.toString();
      } else {
         return this.getText();
      }
   }

   public String toStringList() {
      String var2 = "";
      if (this.getFirstChild() != null) {
         var2 = var2 + " (";
      }

      var2 = var2 + " " + this.toString();
      if (this.getFirstChild() != null) {
         var2 = var2 + ((BaseAST)this.getFirstChild()).toStringList();
      }

      if (this.getFirstChild() != null) {
         var2 = var2 + " )";
      }

      if (this.getNextSibling() != null) {
         var2 = var2 + ((BaseAST)this.getNextSibling()).toStringList();
      }

      return var2;
   }

   public String toStringTree() {
      String var2 = "";
      if (this.getFirstChild() != null) {
         var2 = var2 + " (";
      }

      var2 = var2 + " " + this.toString();
      if (this.getFirstChild() != null) {
         var2 = var2 + ((BaseAST)this.getFirstChild()).toStringList();
      }

      if (this.getFirstChild() != null) {
         var2 = var2 + " )";
      }

      return var2;
   }

   public static String decode(String var0) {
      StringBuffer var7 = new StringBuffer();

      for(int var8 = 0; var8 < var0.length(); ++var8) {
         char var1 = var0.charAt(var8);
         if (var1 == '&') {
            char var2 = var0.charAt(var8 + 1);
            char var3 = var0.charAt(var8 + 2);
            char var4 = var0.charAt(var8 + 3);
            char var5 = var0.charAt(var8 + 4);
            char var6 = var0.charAt(var8 + 5);
            if (var2 == 'a' && var3 == 'm' && var4 == 'p' && var5 == ';') {
               var7.append("&");
               var8 += 5;
            } else if (var2 == 'l' && var3 == 't' && var4 == ';') {
               var7.append("<");
               var8 += 4;
            } else if (var2 == 'g' && var3 == 't' && var4 == ';') {
               var7.append(">");
               var8 += 4;
            } else if (var2 == 'q' && var3 == 'u' && var4 == 'o' && var5 == 't' && var6 == ';') {
               var7.append("\"");
               var8 += 6;
            } else if (var2 == 'a' && var3 == 'p' && var4 == 'o' && var5 == 's' && var6 == ';') {
               var7.append("'");
               var8 += 6;
            } else {
               var7.append("&");
            }
         } else {
            var7.append(var1);
         }
      }

      return new String(var7);
   }

   public static String encode(String var0) {
      StringBuffer var2 = new StringBuffer();

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         char var1 = var0.charAt(var3);
         switch (var1) {
            case '"':
               var2.append("&quot;");
               break;
            case '&':
               var2.append("&amp;");
               break;
            case '\'':
               var2.append("&apos;");
               break;
            case '<':
               var2.append("&lt;");
               break;
            case '>':
               var2.append("&gt;");
               break;
            default:
               var2.append(var1);
         }
      }

      return new String(var2);
   }

   public void xmlSerializeNode(Writer var1) throws IOException {
      StringBuffer var2 = new StringBuffer(100);
      var2.append("<");
      var2.append(this.getClass().getName() + " ");
      var2.append("text=\"" + encode(this.getText()) + "\" type=\"" + this.getType() + "\"/>");
      var1.write(var2.toString());
   }

   public void xmlSerializeRootOpen(Writer var1) throws IOException {
      StringBuffer var2 = new StringBuffer(100);
      var2.append("<");
      var2.append(this.getClass().getName() + " ");
      var2.append("text=\"" + encode(this.getText()) + "\" type=\"" + this.getType() + "\">\n");
      var1.write(var2.toString());
   }

   public void xmlSerializeRootClose(Writer var1) throws IOException {
      var1.write("</" + this.getClass().getName() + ">\n");
   }

   public void xmlSerialize(Writer var1) throws IOException {
      for(Object var2 = this; var2 != null; var2 = ((AST)var2).getNextSibling()) {
         if (((AST)var2).getFirstChild() == null) {
            ((BaseAST)var2).xmlSerializeNode(var1);
         } else {
            ((BaseAST)var2).xmlSerializeRootOpen(var1);
            ((BaseAST)((AST)var2).getFirstChild()).xmlSerialize(var1);
            ((BaseAST)var2).xmlSerializeRootClose(var1);
         }
      }

   }
}
