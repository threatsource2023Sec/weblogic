package weblogic.xml.xpath.stream.nodetests;

import weblogic.xml.xpath.stream.NodeTest;
import weblogic.xml.xpath.stream.StreamNode;

public final class AttributeNodeTest implements NodeTest {
   public static final NodeTest INSTANCE = new AttributeNodeTest();

   private AttributeNodeTest() {
   }

   public boolean isMatch(StreamNode node) {
      return node.getNodeType() == -1;
   }

   public boolean isStringConvertible() {
      return true;
   }

   public String toString() {
      return "*";
   }
}
