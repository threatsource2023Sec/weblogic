package weblogic.xml.xpath.common.nodetests;

import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.NodeTest;

public final class NameNodeTest implements NodeTest {
   private static final boolean DEBUG = false;
   private String mPrefix;
   private String mLocalName;
   private Interrogator mInterrogator;

   public NameNodeTest(Interrogator i, String localName) {
      if (localName == null) {
         throw new IllegalArgumentException("null name");
      } else if (i == null) {
         throw new IllegalArgumentException("null interrogator");
      } else {
         this.mPrefix = null;
         this.mLocalName = localName;
         this.mInterrogator = i;
      }
   }

   public NameNodeTest(Interrogator i, String prefix, String localName) {
      if (localName == null) {
         throw new IllegalArgumentException("null name");
      } else if (prefix == null) {
         throw new IllegalArgumentException("null prefix");
      } else if (i == null) {
         throw new IllegalArgumentException("null interrogator");
      } else {
         this.mPrefix = prefix;
         this.mLocalName = localName;
         this.mInterrogator = i;
      }
   }

   public boolean isMatch(Object node) {
      String prefix = this.mInterrogator.getPrefix(node);
      if (this.mPrefix == null) {
         if (prefix != null) {
            return false;
         }
      } else if (!this.mPrefix.equals(prefix)) {
         return false;
      }

      return this.mLocalName.equals(this.mInterrogator.getLocalName(node));
   }
}
