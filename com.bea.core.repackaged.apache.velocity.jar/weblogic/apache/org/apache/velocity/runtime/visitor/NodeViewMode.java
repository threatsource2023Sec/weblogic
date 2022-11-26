package weblogic.apache.org.apache.velocity.runtime.visitor;

import weblogic.apache.org.apache.velocity.runtime.parser.Token;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTAddNode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTAndNode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTAssignment;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTBlock;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTDirective;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTDivNode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTEQNode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTElseIfStatement;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTElseStatement;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTExpression;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTFalse;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTGENode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTGTNode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTIdentifier;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTIfStatement;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTLENode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTLTNode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTMethod;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTModNode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTMulNode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTNENode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTNotNode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTNumberLiteral;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTObjectArray;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTOrNode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTReference;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTSetDirective;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTStringLiteral;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTSubtractNode;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTText;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTTrue;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTWord;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTprocess;
import weblogic.apache.org.apache.velocity.runtime.parser.node.Node;
import weblogic.apache.org.apache.velocity.runtime.parser.node.SimpleNode;

public class NodeViewMode extends BaseVisitor {
   private int indent = 0;
   private boolean showTokens = true;

   private String indentString() {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.indent; ++i) {
         sb.append("  ");
      }

      return sb.toString();
   }

   private Object showNode(Node node, Object data) {
      String tokens = "";
      String special = "";
      if (this.showTokens) {
         Token t = node.getFirstToken();
         if (t.specialToken != null && !t.specialToken.image.startsWith("##")) {
            special = t.specialToken.image;
         }

         tokens = " -> " + special + t.image;
      }

      System.out.println(this.indentString() + node + tokens);
      ++this.indent;
      data = node.childrenAccept(this, data);
      --this.indent;
      return data;
   }

   public Object visit(SimpleNode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTprocess node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTExpression node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTAssignment node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTOrNode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTAndNode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTEQNode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTNENode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTLTNode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTGTNode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTLENode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTGENode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTAddNode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTSubtractNode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTMulNode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTDivNode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTModNode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTNotNode node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTNumberLiteral node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTStringLiteral node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTIdentifier node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTMethod node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTReference node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTTrue node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTFalse node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTBlock node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTText node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTIfStatement node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTElseStatement node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTElseIfStatement node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTObjectArray node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTDirective node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTWord node, Object data) {
      return this.showNode(node, data);
   }

   public Object visit(ASTSetDirective node, Object data) {
      return this.showNode(node, data);
   }
}
