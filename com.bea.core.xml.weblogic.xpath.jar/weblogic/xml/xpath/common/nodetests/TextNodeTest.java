package weblogic.xml.xpath.common.nodetests;

import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.NodeTest;

public final class TextNodeTest implements NodeTest {
   private Interrogator mInterrogator;

   public TextNodeTest(Interrogator i) {
      if (i == null) {
         throw new IllegalArgumentException("null interrogator");
      } else {
         this.mInterrogator = i;
      }
   }

   public boolean isMatch(Object node) {
      return this.mInterrogator.isText(node);
   }
}
