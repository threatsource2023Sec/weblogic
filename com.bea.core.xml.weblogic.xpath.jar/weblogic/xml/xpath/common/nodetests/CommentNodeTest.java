package weblogic.xml.xpath.common.nodetests;

import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.NodeTest;

public final class CommentNodeTest implements NodeTest {
   private Interrogator mInterrogator;

   public CommentNodeTest(Interrogator i) {
      if (i == null) {
         throw new IllegalArgumentException("null interrogator");
      } else {
         this.mInterrogator = i;
      }
   }

   public boolean isMatch(Object node) {
      return this.mInterrogator.isComment(node);
   }
}
