package weblogic.xml.xpath.common.nodetests;

import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.NodeTest;

public final class ProcessingInstructionNodeTest implements NodeTest {
   private Interrogator mInterrogator;
   private String mLiteral = null;

   public ProcessingInstructionNodeTest(Interrogator i) {
      if (i == null) {
         throw new IllegalArgumentException("null interrogator");
      } else {
         this.mInterrogator = i;
      }
   }

   public ProcessingInstructionNodeTest(Interrogator i, String l) {
      if (i == null) {
         throw new IllegalArgumentException("null interrogator");
      } else if (l == null) {
         throw new IllegalArgumentException("null literal");
      } else {
         this.mInterrogator = i;
         this.mLiteral = l;
      }
   }

   public boolean isMatch(Object node) {
      return this.mInterrogator.isProcessingInstruction(node) && (this.mLiteral == null || this.mLiteral.equals(this.mInterrogator.getLocalName(node)));
   }
}
