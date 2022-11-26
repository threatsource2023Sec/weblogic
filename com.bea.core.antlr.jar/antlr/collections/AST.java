package antlr.collections;

import antlr.Token;

public interface AST {
   void addChild(AST var1);

   boolean equals(AST var1);

   boolean equalsList(AST var1);

   boolean equalsListPartial(AST var1);

   boolean equalsTree(AST var1);

   boolean equalsTreePartial(AST var1);

   ASTEnumeration findAll(AST var1);

   ASTEnumeration findAllPartial(AST var1);

   AST getFirstChild();

   AST getNextSibling();

   String getText();

   int getType();

   int getLine();

   int getColumn();

   int getNumberOfChildren();

   void initialize(int var1, String var2);

   void initialize(AST var1);

   void initialize(Token var1);

   void setFirstChild(AST var1);

   void setNextSibling(AST var1);

   void setText(String var1);

   void setType(int var1);

   String toString();

   String toStringList();

   String toStringTree();
}
