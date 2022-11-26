package weblogic.xml.xpath.stream.nodetests;

import weblogic.xml.xpath.stream.NodeTest;
import weblogic.xml.xpath.stream.StreamNode;

public final class NamespaceNodeTest implements NodeTest {
   public static final NodeTest INSTANCE = new NamespaceNodeTest();

   private NamespaceNodeTest() {
   }

   public boolean isMatch(StreamNode node) {
      return node.getNodeType() == -2;
   }

   public boolean isStringConvertible() {
      return true;
   }

   public String toString() {
      return "*";
   }
}
