package weblogic.diagnostics.querytree;

public interface QueryNode {
   QueryNodeType getType();

   int getChildCount();

   QueryNode getChild(int var1) throws ArrayIndexOutOfBoundsException;

   void displayNode(int var1, StringBuffer var2);
}
