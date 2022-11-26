package weblogic.xml.xpath.stream.nodetests;

import weblogic.xml.xpath.stream.NodeTest;
import weblogic.xml.xpath.stream.StreamNode;

public final class ElementNodeTest implements NodeTest {
   public static final NodeTest INSTANCE = new ElementNodeTest();

   private ElementNodeTest() {
   }

   public boolean isMatch(StreamNode node) {
      return node.getNodeType() == 2 || node.getNodeType() == 256;
   }

   public boolean isStringConvertible() {
      return false;
   }

   public String toString() {
      return "*";
   }
}
