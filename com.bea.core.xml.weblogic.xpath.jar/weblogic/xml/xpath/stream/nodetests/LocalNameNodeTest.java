package weblogic.xml.xpath.stream.nodetests;

import weblogic.xml.stream.XMLName;
import weblogic.xml.xpath.stream.NodeTest;
import weblogic.xml.xpath.stream.StreamNode;

public class LocalNameNodeTest implements NodeTest {
   private String mLocalName;

   public LocalNameNodeTest(String name) {
      this.mLocalName = name;
   }

   public boolean isMatch(StreamNode node) {
      XMLName name = node.getNodeName();
      return name != null && this.mLocalName.equals(name.getLocalName()) && name.getPrefix() == null;
   }

   public boolean isStringConvertible() {
      return false;
   }

   public String toString() {
      return this.mLocalName;
   }
}
