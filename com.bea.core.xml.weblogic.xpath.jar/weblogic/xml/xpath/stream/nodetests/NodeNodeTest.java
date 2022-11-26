package weblogic.xml.xpath.stream.nodetests;

import weblogic.xml.xpath.stream.NodeTest;
import weblogic.xml.xpath.stream.StreamNode;

public final class NodeNodeTest implements NodeTest {
   public static final NodeTest INSTANCE = new NodeNodeTest();

   private NodeNodeTest() {
   }

   public boolean isMatch(StreamNode node) {
      return node.getNodeType() != 128;
   }

   public boolean isStringConvertible() {
      return false;
   }

   public String toString() {
      return "node()";
   }
}
