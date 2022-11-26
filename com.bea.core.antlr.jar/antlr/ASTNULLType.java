package antlr;

import antlr.collections.AST;
import antlr.collections.ASTEnumeration;

public class ASTNULLType implements AST {
   public void addChild(AST var1) {
   }

   public boolean equals(AST var1) {
      return false;
   }

   public boolean equalsList(AST var1) {
      return false;
   }

   public boolean equalsListPartial(AST var1) {
      return false;
   }

   public boolean equalsTree(AST var1) {
      return false;
   }

   public boolean equalsTreePartial(AST var1) {
      return false;
   }

   public ASTEnumeration findAll(AST var1) {
      return null;
   }

   public ASTEnumeration findAllPartial(AST var1) {
      return null;
   }

   public AST getFirstChild() {
      return this;
   }

   public AST getNextSibling() {
      return this;
   }

   public String getText() {
      return "<ASTNULL>";
   }

   public int getType() {
      return 3;
   }

   public int getLine() {
      return 0;
   }

   public int getColumn() {
      return 0;
   }

   public int getNumberOfChildren() {
      return 0;
   }

   public void initialize(int var1, String var2) {
   }

   public void initialize(AST var1) {
   }

   public void initialize(Token var1) {
   }

   public void setFirstChild(AST var1) {
   }

   public void setNextSibling(AST var1) {
   }

   public void setText(String var1) {
   }

   public void setType(int var1) {
   }

   public String toString() {
      return this.getText();
   }

   public String toStringList() {
      return this.getText();
   }

   public String toStringTree() {
      return this.getText();
   }
}
