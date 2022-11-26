package weblogic.xml.xpath.common.nodetests;

import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.NodeTest;

public final class NodeNodeTest implements NodeTest {
   private Interrogator mInterrogator;

   public NodeNodeTest(Interrogator i) {
      if (i == null) {
         throw new IllegalArgumentException("null interrogator");
      } else {
         this.mInterrogator = i;
      }
   }

   public boolean isMatch(Object node) {
      return this.mInterrogator.isNode(node);
   }
}
