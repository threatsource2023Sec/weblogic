package weblogic.xml.xpath.stream.nodetests;

import weblogic.xml.stream.ProcessingInstruction;
import weblogic.xml.xpath.stream.NodeTest;
import weblogic.xml.xpath.stream.StreamNode;

public final class ProcessingInstructionNodeTest implements NodeTest {
   public static final NodeTest INSTANCE = new ProcessingInstructionNodeTest();
   private String mLiteral;

   private ProcessingInstructionNodeTest() {
   }

   public ProcessingInstructionNodeTest(String literal) {
      if (literal == null) {
         throw new IllegalArgumentException("null literal");
      } else {
         this.mLiteral = literal;
      }
   }

   public boolean isMatch(StreamNode node) {
      return node.getNodeType() == 8 && (this.mLiteral == null || this.mLiteral.equals(((ProcessingInstruction)node).getData()));
   }

   public boolean isStringConvertible() {
      return true;
   }

   public String toString() {
      return "processing-instruction()";
   }
}
