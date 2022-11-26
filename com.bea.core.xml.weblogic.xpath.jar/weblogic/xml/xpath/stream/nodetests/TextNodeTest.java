package weblogic.xml.xpath.stream.nodetests;

import weblogic.xml.xpath.stream.NodeTest;
import weblogic.xml.xpath.stream.StreamNode;

public final class TextNodeTest implements NodeTest {
   public static final NodeTest INSTANCE = new TextNodeTest();

   private TextNodeTest() {
   }

   public boolean isMatch(StreamNode node) {
      return node.getNodeType() == 16 || node.getNodeType() == 64;
   }

   public boolean isStringConvertible() {
      return true;
   }

   public String toString() {
      return "text()";
   }
}
