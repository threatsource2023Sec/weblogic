package weblogic.xml.xpath.stream.nodetests;

import weblogic.xml.stream.XMLName;
import weblogic.xml.xpath.stream.NodeTest;
import weblogic.xml.xpath.stream.StreamNode;

public final class QNameNodeTest implements NodeTest {
   private String mPrefix;
   private String mLocalName;

   public QNameNodeTest(String prefix, String localName) {
      if (prefix == null) {
         throw new IllegalArgumentException("null prefix");
      } else if (localName == null) {
         throw new IllegalArgumentException("null name");
      } else {
         this.mPrefix = prefix;
         this.mLocalName = localName;
      }
   }

   public boolean isMatch(StreamNode node) {
      XMLName name = node.getNodeName();
      return name != null && this.mLocalName.equals(name.getLocalName()) && this.mPrefix.equals(name.getPrefix());
   }

   public boolean isStringConvertible() {
      return false;
   }

   public String toString() {
      return this.mPrefix + ":" + this.mLocalName;
   }
}
