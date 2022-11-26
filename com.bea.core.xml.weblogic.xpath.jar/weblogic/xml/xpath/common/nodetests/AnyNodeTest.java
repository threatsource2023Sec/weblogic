package weblogic.xml.xpath.common.nodetests;

import weblogic.xml.xpath.common.NodeTest;

public final class AnyNodeTest implements NodeTest {
   public static final NodeTest INSTANCE = new AnyNodeTest();

   private AnyNodeTest() {
   }

   public boolean isMatch(Object node) {
      return true;
   }
}
