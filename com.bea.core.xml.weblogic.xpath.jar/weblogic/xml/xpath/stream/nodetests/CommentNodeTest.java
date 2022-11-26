package weblogic.xml.xpath.stream.nodetests;

import weblogic.xml.xpath.stream.NodeTest;
import weblogic.xml.xpath.stream.StreamNode;

public final class CommentNodeTest implements NodeTest {
   public static final NodeTest INSTANCE = new CommentNodeTest();

   private CommentNodeTest() {
   }

   public boolean isMatch(StreamNode node) {
      return node.getNodeType() == 32;
   }

   public boolean isStringConvertible() {
      return true;
   }

   public String toString() {
      return "comment()";
   }
}
